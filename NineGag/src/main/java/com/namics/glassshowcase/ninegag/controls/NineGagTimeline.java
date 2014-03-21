package com.namics.glassshowcase.ninegag.controls;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

/**
 * Created by chbuehler on 21.03.14.
 */
public class NineGagTimeline extends CardScrollView {

    public NineGagTimeline(Context context){
        super(context);
    }

    private class CardAdapter extends CardScrollAdapter{
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
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
