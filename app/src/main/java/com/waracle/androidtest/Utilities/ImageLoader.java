package com.waracle.androidtest.Utilities;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.waracle.androidtest.Interfaces.LoadCakeDataTaskListener;
import com.waracle.androidtest.Tasks.LoadCakeImageTask;

import java.security.InvalidParameterException;

/**
 * Created by Riad on 20/05/2015.
 */
public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    public ImageLoader() { /**/ }

    /**
     * Simple function for loading a bitmap image from the web
     *
     * @param url       image url
     * @param imageView view to set image too.
     * @param loadCakeDataTaskListener listener to access image cache methods
     */
    public void load(String url, ImageView imageView, LoadCakeDataTaskListener loadCakeDataTaskListener) {
        if (TextUtils.isEmpty(url)) {
            throw new InvalidParameterException("URL is empty!");
        }

        final Bitmap bitmap = loadCakeDataTaskListener.getBitmapFromMemCache(url);
        if (bitmap != null)
        {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            new LoadCakeImageTask(imageView, url, loadCakeDataTaskListener).execute();
        }
    }
}
