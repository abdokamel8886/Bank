package com.example.bank.ui.maps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bank.R;
import com.example.bank.adapters.CustomInfoWindowAdapter;
import com.example.bank.models.AtmResultModel;
import com.example.bank.ui.atm.confirm.AtmConfirmFragment;
import com.example.bank.utils.SharedModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class AtmMapFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener {


    private GoogleMap mMap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        String title = marker.getTitle();
                        for (AtmResultModel m : SharedModel.atmResultModels) {
                            if (title.equals(m.getAddress())){
                                SharedModel.setSelectedAtm(m);
                                break;
                            }
                        }
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new AtmConfirmFragment(), "c")
                                .addToBackStack("c").commit();


                    }
                });

                List<Marker> markerList = new ArrayList<>();

                int size = SharedModel.locations.size();

                for(int i = 0 ; i<size; i++){

                    LatLng p = new LatLng(SharedModel.locations.get(i).getLatitude(), SharedModel.locations.get(i).getLongitude());

                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(p)
                            .title(SharedModel.atmResultModels.get(i).getAddress()+"")
                            .snippet("Pop: 4,137,40")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    marker.setTag(0);
                    markerList.add(marker);
                }


                for(Marker m : markerList){
                    LatLng latLng = new LatLng(m.getPosition().latitude,m.getPosition().longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));

                }

            }
        });

        return view;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
    }

}