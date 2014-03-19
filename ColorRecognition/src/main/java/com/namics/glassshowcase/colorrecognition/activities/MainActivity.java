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
    private TextView colorName;
    private TextView colorRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preview = new CameraPreview(this);

        FrameLayout layout = (FrameLayout) findViewById(R.id.camera_preview);
        color = (TextView) findViewById(R.id.textView);
        colorName = (TextView) findViewById(R.id.textView2);
        colorRect = (TextView) findViewById(R.id.editText);
        layout.addView(preview);

        preview.setFrameCallback(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        color.setText("color is: " + Integer.toHexString(preview.pixelColor));

                        colorRect.setBackgroundColor(preview.pixelColor);

                        int r = (preview.pixelColor >> 16) & 0xff;
                        int g = (preview.pixelColor >> 8) & 0xff;
                        int b = preview.pixelColor & 0xff;

                        if(isInRange(r, 11) && isInRange(g, 17) && isInRange(b, 17)) {
                            colorName.setText("black");
                        } else if (isInRange(r, 114) && isInRange(g, 104) && isInRange(b, 72)){
                            colorName.setText("white");
                        } else if (isInRange(r, 25) && isInRange(g, 55) && isInRange(b, 97)){
                            colorName.setText("blue");
                        } else if (isInRange(r, 125) && isInRange(g, 24) && isInRange(b, 16)){
                            colorName.setText("red");
                        } else {
                            colorName.setText("not recognized");
                        }
                    }
                });
                return null;
            }
        });
    }

    private boolean isInRange(int is, int should){
        int min = should - (255 / 100 * 20);
        int max = should + (255 / 100 * 20);
        return (is >= min && is <= max);
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
