package com.namics.glassshowcase.ninegag.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.namics.glassshowcase.ninegag.R;
import com.namics.glassshowcase.ninegag.controls.NineGagTimeline;

public class MainActivity extends Activity {

    private NineGagTimeline timeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeline = new NineGagTimeline(this);
        setContentView(timeline);
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
        if (id == R.id.close) {
            finish();
            return true;
        } else if (id == R.id.next_page){
            timeline.getNextPage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
