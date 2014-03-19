package com.namics.glassshowcase.nespressocapsuleinformation.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.namics.glassshowcase.nespressocapsuleinformation.R;
import com.namics.glassshowcase.nespressocapsuleinformation.capsuleinformation.CapsuleInformation;

import java.util.ArrayList;

/**
 * Created by chbuehler on 17.03.14.
 */
public class CapsuleInformationScrollView extends CardScrollView {

    private CapsuleInformation capsule;
    private ArrayList<View> viewList = new ArrayList<View>();

    public CapsuleInformationScrollView(Context context, int capsuleColor){
        super(context);
        capsule = CapsuleInformation.getCapsuleForColor(capsuleColor);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            viewList.add(createMainView(layoutInflater, capsule));
            viewList.add(createOriginView(layoutInflater, capsule));
            viewList.add(createRoastingView(layoutInflater, capsule));
            viewList.add(createAromaProfileView(layoutInflater, capsule));
        }

        setAdapter(new CapsuleCardAdapter());
        activate();
    }

    private View createMainView(LayoutInflater inflater, CapsuleInformation capsule){
        View view = inflater.inflate(R.layout.capsule_main_view, this, false);

        ((TextView)view.findViewById(R.id.capsule_main_header)).setText(capsule.getTitle());
        ((ProgressBar)view.findViewById(R.id.capsule_intensity)).setProgress(capsule.getIntensity());

        int cupSizes = capsule.getCupSizes();
        if((cupSizes & CapsuleInformation.CUP_SIZE_RISTRETTO) != CapsuleInformation.CUP_SIZE_RISTRETTO){
            ((LinearLayout)view.findViewById(R.id.cup_size_ristretto_layout)).setVisibility(GONE);
        }
        if((cupSizes & CapsuleInformation.CUP_SIZE_ESPRESSO) != CapsuleInformation.CUP_SIZE_ESPRESSO){
            ((LinearLayout)view.findViewById(R.id.cup_size_espresso_layout)).setVisibility(GONE);
        }
        if((cupSizes & CapsuleInformation.CUP_SIZE_LUNGO) != CapsuleInformation.CUP_SIZE_LUNGO){
            ((LinearLayout)view.findViewById(R.id.cup_size_lungo_layout)).setVisibility(GONE);
        }

        ((ImageView)view.findViewById(R.id.capsule_main_image)).setImageResource(capsule.getImageResource());

        return view;
    }

    private View createOriginView(LayoutInflater inflater, CapsuleInformation capsule){
        View view = inflater.inflate(R.layout.capsule_detail_view, this, false);

        ((TextView)view.findViewById(R.id.capsule_detail_title)).setText("Herkunft");
        ((TextView)view.findViewById(R.id.capsule_detail_text)).setText(capsule.getOrigin());

        return view;
    }

    private View createRoastingView(LayoutInflater inflater, CapsuleInformation capsule){
        View view = inflater.inflate(R.layout.capsule_detail_view, this, false);

        ((TextView)view.findViewById(R.id.capsule_detail_title)).setText("Röstung");
        ((TextView)view.findViewById(R.id.capsule_detail_text)).setText(capsule.getRoasting());

        return view;
    }

    private View createAromaProfileView(LayoutInflater inflater, CapsuleInformation capsule){
        View view = inflater.inflate(R.layout.capsule_detail_view, this, false);

        ((TextView)view.findViewById(R.id.capsule_detail_title)).setText("Aromaprofil");
        ((TextView)view.findViewById(R.id.capsule_detail_text)).setText(capsule.getAromaProfile());

        return view;
    }

    private class CapsuleCardAdapter extends CardScrollAdapter{

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object getItem(int i) {
            return capsule;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            return viewList.get(position);
        }

        @Override
        public int findIdPosition(Object id) {
            return 0;
        }

        @Override
        public int findItemPosition(Object item) {
            return 0;
        }
    }
}
