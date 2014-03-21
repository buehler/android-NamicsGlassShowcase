package com.namics.glassshowcase.ninegag.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.namics.glassshowcase.ninegag.R;
import com.namics.glassshowcase.ninegag.helpers.AsyncDataPostExecutionCallable;
import com.namics.glassshowcase.ninegag.helpers.AsyncDataTask;
import com.namics.glassshowcase.ninegag.models.NineGagJoke;
import com.namics.glassshowcase.ninegag.models.NineGagPage;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.concurrent.Callable;

/**
 * Created by chbuehler on 21.03.14.
 */
public class NineGagTimeline extends CardScrollView {

    private static final String SERVICE_URL = "http://infinigag.eu01.aws.af.cm/hot/";
    private NineGagPage page;
    private LayoutInflater inflater;

    public NineGagTimeline(Context context){
        super(context);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setAdapter(new CardAdapter());
        activate();
        getNextPage();
    }

    public void getNextPage(){
        new AsyncDataTask<NineGagPage>(new Callable<NineGagPage>() {
            @Override
            public NineGagPage call() throws Exception {
                String lastId = page == null ? "" : "";
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet request = new HttpGet(SERVICE_URL + lastId);
                HttpResponse response = httpClient.execute(request);
                JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
                NineGagPage newPage = new NineGagPage(jsonObject);
                return newPage;
            }
        }, new AsyncDataPostExecutionCallable<NineGagPage>() {
            @Override
            public void call(NineGagPage newPage) throws Exception {
                try{
                    page = newPage;
                    updateViews(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).execute();
    }

    private class CardAdapter extends CardScrollAdapter{
        @Override
        public int getCount() {
            return page != null ? page.getJokesCount() : 0;
        }

        @Override
        public Object getItem(int i) {
            return page != null ? page.getJokeAtIndex(i) : null;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            NineGagJoke joke = page.getJokeAtIndex(i);
            View card = inflater.inflate(R.layout.ninegag_card, NineGagTimeline.this, false);
            ((TextView)card.findViewById(R.id.ninegag_card_caption)).setText(joke.getCaption());
            ((ImageView)card.findViewById(R.id.ninegag_card_image)).setImageDrawable(joke.getImage());
            return card;
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
