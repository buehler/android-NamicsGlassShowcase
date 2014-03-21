package com.namics.glassshowcase.ninegag.controls;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.namics.glassshowcase.ninegag.models.NineGagPage;

/**
 * Created by chbuehler on 21.03.14.
 */
public class NineGagTimeline extends CardScrollView {

    private static final String SERVICE_URL = "http://infinigag.eu01.aws.af.cm/hot/";
    private NineGagPage page;

    public NineGagTimeline(Context context){
        super(context);
    }

    public void getNextPage(){

    }

    private class CardAdapter extends CardScrollAdapter{
        @Override
        public int getCount() {
            return page != null ? page.getJokesCount() : 0;
        }

        @Override
        public Object getItem(int i) {
            return page != null ? page.getJokeAtIndex(i) : null;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public int findIdPosition(Object o) {
            return 0;
        }

        @Override
        public int findItemPosition(Object o) {
            return 0;
        }
    }
}
