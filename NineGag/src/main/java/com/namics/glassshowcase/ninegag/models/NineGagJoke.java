package com.namics.glassshowcase.ninegag.models;

import android.graphics.drawable.Drawable;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by chbuehler on 21.03.14.
 */
public class NineGagJoke {
    private String id;
    private String caption;
    private String imageUrl;
    private Drawable image;

    public NineGagJoke(JSONObject jsonObject){
        try{
            id = jsonObject.getString("id");
            caption = jsonObject.getString("caption");
            imageUrl = jsonObject.getJSONObject("images").getString("normal");
            image = loadImageFromWebOperations(imageUrl);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public String getId() {

        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Drawable getImage() {
        return image;
    }
}
