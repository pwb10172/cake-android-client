package com.waracle.androidtest.Tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.waracle.androidtest.Interfaces.LoadCakeDataTaskListener;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoadCakeImageTask extends AsyncTask<String, Void, Bitmap> {

    private String urlStr;
    private final WeakReference<ImageView> imageViewReference;
    private LoadCakeDataTaskListener listener;

    public LoadCakeImageTask(ImageView imageView, String url, LoadCakeDataTaskListener loadCakeDataTaskListener)
    {
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.urlStr = url;
        this.listener = loadCakeDataTaskListener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() != 200)
            {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null)
            {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if(bitmap != null)
                {
                    listener.addBitmapToMemoryCache(urlStr, bitmap);
                    return bitmap;
                }
            }
        }
        catch (Exception e)
        {
            Log.e("ImageDownloader", "Error downloading image from " + urlStr);
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null)
        {
            ImageView imageView = imageViewReference.get();
            if (imageView != null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
