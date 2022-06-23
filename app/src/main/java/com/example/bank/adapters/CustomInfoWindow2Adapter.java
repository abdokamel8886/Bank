package com.example.bank.adapters;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.bank.R;
import com.example.bank.models.AtmResultModel;
import com.example.bank.models.BankModel;
import com.example.bank.utils.SharedModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by User on 10/2/2017.
 */

public class CustomInfoWindow2Adapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;



    public CustomInfoWindow2Adapter(Context context) {
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

        for (BankModel m : SharedModel.bankresults) {
            if (title.equals(m.getAddress())){



                if(SharedModel.getBanktype().equals("Loans Services")){
                    queue.setText("Teller : "+m.getLoanTeller()+"");
                }
                else if(SharedModel.getBanktype().equals("Customer Services")){
                    queue.setText("Teller : "+m.getCustomerTeller()+"");
                }
                else{
                    queue.setText("Teller : "+m.getBankingTeller()+"");
                }

                waiting.setText("Waiting time : "+m.getWaiting()+"m");


                Double la = m.getLocation().getLatitude();
                Double lo = m.getLocation().getLongitude();

                Location my=new Location("my");
                my.setLatitude(SharedModel.getLatitude());
                my.setLongitude(SharedModel.getLongitude());

                Location b=new Location("b");
                b.setLatitude(la);
                b.setLongitude(lo);

                double distance1=my.distanceTo(b);
                int d = (int) distance1/1000 ;

                distance.setText("Distance : "+d+"Km");
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