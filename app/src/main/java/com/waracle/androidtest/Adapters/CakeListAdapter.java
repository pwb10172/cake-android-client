package com.waracle.androidtest.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.Interfaces.LoadCakeDataTaskListener;
import com.waracle.androidtest.Model.Cake;
import com.waracle.androidtest.R;
import com.waracle.androidtest.Utilities.ImageLoader;

import java.util.ArrayList;

public class CakeListAdapter extends ArrayAdapter<Cake> {


    private Context mContext;
    private ImageLoader mImageLoader;
    private LoadCakeDataTaskListener listener;

    private ArrayList<Cake> mCakes;

    private static class ViewHolder {
        TextView title;
        TextView desc;
        ImageView image;
    }


    public CakeListAdapter(Context context, ArrayList<Cake> cakes, LoadCakeDataTaskListener loadCakeDataTaskListener) {
        super(context, 0, cakes);
        mContext = context;
        mCakes = cakes;
        mImageLoader = new ImageLoader();
        this.listener = loadCakeDataTaskListener;
    }

    @Override
    public int getCount() {
        return mCakes.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateData(ArrayList<Cake> arrayOfCakes)
    {
        mCakes.clear();
        mCakes = arrayOfCakes;
        notifyDataSetChanged();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the {@link cake} object located at this position in the list
        Cake currentCake = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image) ;
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(currentCake.getCakeTitle());
        viewHolder.desc.setText(currentCake.getCakeDescription());
        if (viewHolder.image != null)
        {
            mImageLoader.load(currentCake.getCakeImageUrl(), viewHolder.image, listener);
        }

        return convertView;
    }
}
