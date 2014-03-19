package com.namics.glassshowcase.nespressocapsuleinformation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.namics.glassshowcase.nespressocapsuleinformation.R;
import com.namics.glassshowcase.nespressocapsuleinformation.controls.CapsuleInformationScrollView;
import com.namics.glassshowcase.nespressocapsuleinformation.controls.TuggableView;

public class CapsuleInformationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.os.Debug.waitForDebugger();
        Intent intent = getIntent();
        int color = intent.getIntExtra("PixelColor", -1);

        setContentView(new CapsuleInformationScrollView(this, color));

    }


}
