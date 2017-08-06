package com.waracle.androidtest.Activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.waracle.androidtest.Fragments.CakeListFragment;
import com.waracle.androidtest.R;


public class MainActivity extends AppCompatActivity {

    private static final String TAG_CAKE_LIST_FRAGMENT = "cakeListFragment";

    private CakeListFragment mCakeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the retained fragment on activity restarts
        FragmentManager fm = getSupportFragmentManager();
        mCakeListFragment = (CakeListFragment) fm.findFragmentByTag( TAG_CAKE_LIST_FRAGMENT);

        // create the fragment and data the first time
        if (mCakeListFragment == null)
        {
            // add the fragment
            mCakeListFragment = new CakeListFragment();
            fm.beginTransaction().add(R.id.container, mCakeListFragment,  TAG_CAKE_LIST_FRAGMENT).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
