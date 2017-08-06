package com.waracle.androidtest.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.waracle.androidtest.Adapters.CakeListAdapter;
import com.waracle.androidtest.Interfaces.LoadCakeDataTaskListener;
import com.waracle.androidtest.Model.Cake;
import com.waracle.androidtest.R;
import com.waracle.androidtest.Tasks.LoadCakeDataTask;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Fragment is responsible for loading in some JSON and
 * then displaying a list of cakes with images.
 * Fix any crashes
 * Improve any performance issues
 * Use good coding practices to make code more secure
 */
public class CakeListFragment extends ListFragment implements LoadCakeDataTaskListener
{

    private static final String TAG = CakeListFragment.class.getSimpleName();

    private LruCache<String, Bitmap> mMemoryCache;
    private LruCache<String, Bitmap> mRetainedCache;

    private ListView mListView;
    private CakeListAdapter mAdapter;

    private ArrayList<Cake> cakes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        setRetainInstance(true);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;


        CakeListFragment retainFragment = CakeListFragment.findOrCreateRetainFragment(getFragmentManager());
        mMemoryCache = retainFragment.mRetainedCache;
        if (mMemoryCache == null)
        {
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize)
            {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };
            retainFragment.mRetainedCache = mMemoryCache;
        }

        // Construct the data source
        ArrayList<Cake> arrayOfCakes = new ArrayList<Cake>();
        mAdapter = new CakeListAdapter(getActivity(), arrayOfCakes, this);
        mListView.setAdapter(mAdapter);

        // Load data from net.
        loadData();
    }

    public void loadData()
    {
        new LoadCakeDataTask(this).execute();
    }


    public static CakeListFragment findOrCreateRetainFragment(FragmentManager fm)
    {
        CakeListFragment fragment = (CakeListFragment) fm.findFragmentByTag(TAG);
        if (fragment == null)
        {
            fragment = new CakeListFragment();
            fm.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    @Override
    public void onLoadDataSuccess(JSONArray jsonArray)
    {
        cakes = Cake.fromJson(jsonArray);
        mAdapter.addAll(cakes);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailed()
    {
        Toast.makeText(getActivity(), "Unable to load data at this time try again later", Toast.LENGTH_SHORT).show();
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }


}