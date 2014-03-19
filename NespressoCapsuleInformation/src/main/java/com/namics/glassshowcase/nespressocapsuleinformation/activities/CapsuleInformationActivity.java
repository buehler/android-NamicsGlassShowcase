package com.namics.glassshowcase.nespressocapsuleinformation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.namics.glassshowcase.nespressocapsuleinformation.controls.CapsuleInformationScrollView;

public class CapsuleInformationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int color = intent.getIntExtra("PixelColor", -1);

        setContentView(new CapsuleInformationScrollView(this, color));

    }


}
