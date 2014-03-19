package com.namics.glassshowcase.nespressocapsuleinformation.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

/**
 * Created by chbuehler on 17.03.14.
 */
public class TuggableView extends CardScrollView {

    private final View mContentView;

    /**
     * Initializes a {@code TuggableView} that uses the specified layout
     * resource for its user interface.
     */
    public TuggableView(Context context, int layoutResId) {
        this(context, LayoutInflater.from(context).inflate(layoutResId, null));
    }

    /**
     * Initializes a {@code TuggableView} that uses the specified view for its
     * user interface.
     */
    public TuggableView(Context context, View view) {
        super(context);

        mContentView = view;
        setAdapter(new SingleCardAdapter());
        activate();
    }

    @Override
    protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
        super.dispatchGenericFocusedEvent(event);
        return false;
    }

    /** Holds the single "card" inside the card scroll view. */
    private class SingleCardAdapter extends CardScrollAdapter {

        @Override
        public int findIdPosition(Object id) {
            return 0;
        }

        @Override
        public int findItemPosition(Object item) {
            return 0;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return mContentView;
        }

        @Override
        public View getView(int position, View recycleView, ViewGroup parent) {
            return setItemOnCard(getItem(position), mContentView);
        }
    }
}
