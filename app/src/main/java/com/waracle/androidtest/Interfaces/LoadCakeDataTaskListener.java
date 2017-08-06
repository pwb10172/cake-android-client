package com.waracle.androidtest.Interfaces;

import android.graphics.Bitmap;

import org.json.JSONArray;


public interface LoadCakeDataTaskListener
{
    void onLoadDataSuccess(JSONArray jsonArray);
    void onLoadDataFailed();
    void addBitmapToMemoryCache(String key, Bitmap bitmap);
    Bitmap getBitmapFromMemCache(String key);
}
