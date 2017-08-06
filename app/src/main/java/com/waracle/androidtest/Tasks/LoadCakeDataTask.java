package com.waracle.androidtest.Tasks;

import android.os.AsyncTask;

import com.waracle.androidtest.Interfaces.LoadCakeDataTaskListener;
import com.waracle.androidtest.Utilities.StreamUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;



public class LoadCakeDataTask extends AsyncTask<URL,Void,JSONArray>
{
    private static String JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    private LoadCakeDataTaskListener listener;

    public LoadCakeDataTask(LoadCakeDataTaskListener listener)
    {
        this.listener = listener;
    }

    // Do the long-running work in here
    protected JSONArray doInBackground(URL... urls)
    {
        HttpsURLConnection urlConnection = null;
        try
        {
            URL url = new URL(JSON_URL);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            // Check the connection status
            if (urlConnection.getResponseCode() == 200)
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                byte[] bytes = StreamUtils.readUnknownFully(in);

                // Read in charset of HTTP content.
                String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));

                // Convert byte array to appropriate encoded string.
                String jsonText = new String(bytes, charset);
                return new JSONArray(jsonText);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
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

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(String contentType)
    {
        if (contentType != null)
        {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++)
            {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2)
                {
                    if (pair[0].equals("charset"))
                    {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }


    @Override
    protected void onPostExecute(JSONArray result)
    {
        super.onPostExecute(result);
        if(result != null)
        {
            // callback method that sets mAdapter.setItems(array);
            listener.onLoadDataSuccess(result);
        }
        else
        {
            listener.onLoadDataFailed();
        }
    }

}
