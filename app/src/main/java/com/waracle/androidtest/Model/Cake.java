package com.waracle.androidtest.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cake
{
    private String mCakeTitle;
    private String mCakeDescription;
    private String mCakeImageUrl;

    public void Cake(String title, String description, String imageUrl)
    {
        mCakeTitle = title;
        mCakeDescription = description;
        mCakeImageUrl = imageUrl;
    }

    // Constructor to convert JSON object into a Java class instance
    public Cake(JSONObject object)
    {
        try
        {
            this.mCakeTitle = object.getString("title");
            this.mCakeDescription = object.getString("desc");
            this.mCakeImageUrl = object.getString("image");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // Cake.fromJson(jsonArray);
    public static ArrayList<Cake> fromJson(JSONArray jsonObjects) {
        ArrayList<Cake> cakes = new ArrayList<Cake>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                cakes.add(new Cake(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cakes;
    }


    public String getCakeTitle(){
        return mCakeTitle;
    }

    public String getCakeDescription(){
        return mCakeDescription;
    }

    public String getCakeImageUrl(){
        return mCakeImageUrl;
    }


}
