package com.namics.glassshowcase.ninegag.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chbuehler on 21.03.14.
 */
public class NineGagPage {

    private ArrayList<NineGagJoke> data;
    private String nextId;

    public NineGagPage(JSONObject json){
        try{
            nextId = json.getJSONObject("paging").getString("next");
            JSONArray dataArray = json.getJSONArray("data");
            for (int i=0; i < dataArray.length(); i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                data.add(new NineGagJoke(dataObj));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public NineGagJoke getJokeAtIndex(int index){
        return data.get(index);
    }

    public int getJokesCount(){
        return data.size();
    }
}
