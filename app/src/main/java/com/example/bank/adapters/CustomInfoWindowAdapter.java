package com.example.bank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.bank.R;
import com.example.bank.models.AtmResultModel;
import com.example.bank.utils.SharedModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by User on 10/2/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;



    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View view){

        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title);

        if(!title.equals("")){
            tvTitle.setText(title);
        }
        else if(title.equals("")) {
            tvTitle.setText("error");
        }

        TextView queue =(TextView) view.findViewById(R.id.queue);
        TextView waiting =(TextView) view.findViewById(R.id.waiting);
        TextView distance =(TextView) view.findViewById(R.id.distance);

        for (AtmResultModel m : SharedModel.atmResultModels) {
            if (title.equals(m.getAddress())){
                queue.setText("Queue : "+m.getQueue()+"");
                waiting.setText("Waiting time : "+m.getWaiting()+"m");
                distance.setText("Distance : "+m.getDistance()+"Km");
                break;
            }
        }





    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}