package com.namics.glassshowcase.ninegag.models;

import com.namics.glassshowcase.ninegag.helpers.JsonModel;

/**
 * Created by chbuehler on 21.03.14.
 */
public class NineGagJoke implements JsonModel {
    private String id;
    private String caption;
    private String imageUrl;



    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
