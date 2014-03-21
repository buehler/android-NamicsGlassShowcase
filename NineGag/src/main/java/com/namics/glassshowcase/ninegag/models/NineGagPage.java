package com.namics.glassshowcase.ninegag.models;

import com.namics.glassshowcase.ninegag.helpers.JsonModel;

import java.util.ArrayList;

/**
 * Created by chbuehler on 21.03.14.
 */
public class NineGagPage implements JsonModel {

    private ArrayList<NineGagJoke> data;
    private Pagination pagination;

    public class Pagination {
        private String next;

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }
    }
}
