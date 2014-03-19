package com.namics.glassshowcase.nespressocapsuleinformation.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.namics.glassshowcase.nespressocapsuleinformation.R;
import com.namics.glassshowcase.nespressocapsuleinformation.capsuleinformation.CapsuleInformation;
import com.namics.glassshowcase.nespressocapsuleinformation.helpers.Callable;
import com.namics.glassshowcase.nespressocapsuleinformation.imagerecognition.CameraPreview;

public class MainActivity extends Activity {

    private FrameLayout previewContainer;
    private CameraPreview cameraView;
    private GestureDetector gestureDetector;

    private ImageView crosshair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createGestureDetector();

        crosshair = (ImageView)findViewById(R.id.camera_preview_crosshair);

        cameraView = new CameraPreview(this);
        previewContainer = (FrameLayout) findViewById(R.id.camera_preview);

        previewContainer.addView(cameraView);

        cameraView.setCapsuleFoundCallback(new Callable<Boolean, Void>(){
            @Override
            public Void call(Boolean foundCapsule) {
                if(foundCapsule){
                    crosshair.setImageResource(R.drawable.capsule_found);
                } else {
                    crosshair.setImageResource(R.drawable.no_capsule_found);
                }
                return null;
            }
        });
    }

    @Override
    protected void onResume()    {
        super.onResume();

        // Do not hold the camera during onResume
        if (cameraView != null)
        {
            cameraView.releaseCamera();
        }

        // Set the view
        previewContainer.removeAllViews();
        previewContainer.addView(cameraView);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // Do not hold the camera during onPause
        if (cameraView != null)
        {
            cameraView.releaseCamera();
        }
        previewContainer.removeAllViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.capture:
                showInformation();
                return true;
            case R.id.close:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if(gestureDetector != null){
            return gestureDetector.onMotionEvent(event);
        }
        return super.onGenericMotionEvent(event);
    }

    private void createGestureDetector(){
        gestureDetector = new GestureDetector(this);
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if(gesture == Gesture.TAP){
                    showInformation();
                    return true;
                } else if(gesture == Gesture.SWIPE_DOWN){
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void showInformation(){
        final Context that = this;

        cameraView.getPixelColor(new Callable<Integer,Void>(){
            @Override
            public Void call(Integer param) {
                Intent intent = new Intent(that, CapsuleInformationActivity.class);
                intent.putExtra("PixelColor", param);
                startActivity(intent);
                return null;
            }
        });

    }
}
