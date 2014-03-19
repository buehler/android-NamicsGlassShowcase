package com.namics.glassshowcase.colorrecognition.activities;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.namics.glassshowcase.colorrecognition.R;
import com.namics.glassshowcase.colorrecognition.controls.CameraPreview;

import java.util.concurrent.Callable;


public class MainActivity extends Activity {

    private CameraPreview preview;
    private TextView color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preview = new CameraPreview(this);

        FrameLayout layout = (FrameLayout) findViewById(R.id.camera_preview);
        color = (TextView) findViewById(R.id.textView);
        layout.addView(preview);

        preview.setFrameCallback(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        color.setText("color is: " + Integer.toHexString(preview.pixelColor));
                    }
                });
                return null;
            }
        });
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

}
