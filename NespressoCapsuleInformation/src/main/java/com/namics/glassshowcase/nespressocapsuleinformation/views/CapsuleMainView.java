package com.namics.glassshowcase.nespressocapsuleinformation.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namics.glassshowcase.nespressocapsuleinformation.R;
import com.namics.glassshowcase.nespressocapsuleinformation.capsuleinformation.CapsuleInformation;

/**
 * TODO: document your custom view class.
 */
public class CapsuleMainView extends View {

    public CapsuleMainView(Context context, ViewGroup group, CapsuleInformation capsule) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            //layoutInflater.inflate(R.layout.main, this, true);
        }
        //((TextView)findViewById(R.id.capsule_main_header)).setText(capsule.getTitle());
    }

}
