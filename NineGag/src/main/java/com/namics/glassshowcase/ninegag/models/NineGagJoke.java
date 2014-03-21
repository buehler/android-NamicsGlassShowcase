package com.namics.glassshowcase.ninegag.models;

import org.json.JSONObject;

/**
 * Created by chbuehler on 21.03.14.
 */
public class NineGagJoke {
    private String id;
    private String caption;
    private String imageUrl;

    public NineGagJoke(JSONObject jsonObject){
        try{
            id = jsonObject.getString("id");
            caption = jsonObject.getString("caption");
            imageUrl = jsonObject.getJSONObject("images").getString("normal");
        } catch (Exception e){
            e.printStackTrace();
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
}
