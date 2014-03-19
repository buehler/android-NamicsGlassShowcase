package com.namics.glassshowcase.namicslivewordmark;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;
import com.google.android.glass.timeline.LiveCard.PublishMode;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

public class WordmarkService extends Service {

	public static final String TAG = "LiveWordmarkService";
	public static final String LIVECARD_TAG = "livewordmark";
	
	private TimelineManager timelineManager;
	private RemoteViews remoteViews;
	private LiveCard liveCard;
	private Timer updateTimer;
	private Handler uiHandler;
	
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate called.");
		timelineManager = TimelineManager.from(this);
		updateTimer = new Timer("tileupdater");
		uiHandler = new Handler(Looper.getMainLooper());
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind called.");
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand called.");
		//android.os.Debug.waitForDebugger();
		
		if(liveCard == null){
			liveCard = timelineManager.createLiveCard(LIVECARD_TAG);
			
			remoteViews = new RemoteViews(this.getPackageName(), R.layout.wordmark_tile);
			liveCard.setViews(remoteViews);
			
			updateTimer.scheduleAtFixedRate(new TimerTask() {
				private int updateCounter = -1;
				@Override
				public void run() {
					updateCounter++;
					if(updateCounter % 10 == 0){
						updateCounter = 0;
						
						try{
							HttpClient httpClient = new DefaultHttpClient();
							HttpGet request = new HttpGet("http://gdw.namics.com/");
							HttpResponse response = httpClient.execute(request);
							
							final String mark = EntityUtils.toString(response.getEntity());
							
							uiHandler.post(new Runnable() {
								
								@Override
								public void run() {
									remoteViews.setProgressBar(R.id.wordmark_showtime, 10, updateCounter, false);
									remoteViews.setTextViewText(R.id.wordmark_text, mark);
									liveCard.setViews(remoteViews);	
								}
							});
						} catch (Exception e){
							e.printStackTrace();
						}
					} else {
						remoteViews.setProgressBar(R.id.wordmark_showtime, 10, updateCounter, false);
						liveCard.setViews(remoteViews);
					}
				}
			}, 1000, 1000);
			
			Intent menuIntent = new Intent(this, MenuActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            liveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));

            liveCard.publish(PublishMode.REVEAL);
            Log.d(TAG, "Done publishing LiveCard");
		} else {
			//TODO: jump to livecard
		}
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy called.");
		if (liveCard != null && liveCard.isPublished()) {
            Log.d(TAG, "Unpublishing LiveCard");
            liveCard.unpublish();
            liveCard = null;
        }
		if(updateTimer != null){
			updateTimer.cancel();
			updateTimer = null;
		}
		super.onDestroy();
	}

}
