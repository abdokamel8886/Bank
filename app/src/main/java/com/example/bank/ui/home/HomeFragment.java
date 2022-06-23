package com.example.bank.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.databinding.FragmentHomeBinding;
import com.example.bank.models.ModelAuthCache;
import com.example.bank.models.RegModel;
import com.example.bank.ui.atm.SelectAtmFragment;
import com.example.bank.ui.auth.login.LoginFragment;
import com.example.bank.ui.bank.TypeFragment;
import com.example.bank.ui.history.HistoryFragment;
import com.example.bank.ui.history.HistoryViewModel;
import com.example.bank.ui.screenshot.ScreenFragment;
import com.example.bank.ui.upcoming.UpcomingFragment;
import com.example.bank.ui.upcoming.UpcomingViewModel;
import com.example.bank.utils.SharedModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements BackListener {


    FragmentHomeBinding binding;
    ArrayList<ModelAuthCache> caches = new ArrayList<>();

    HomeViewModel viewModel;
    UpcomingViewModel upcomingViewModel;

    HistoryViewModel historyViewModel;

    public static BackListener listener;


    private LocationRequest locationRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        upcomingViewModel = new ViewModelProvider(this).get(UpcomingViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        getData();
        drawer();
        getCurrentLocation();
        onClicks();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        listener = null;
    }

    private void onClicks(){
        binding.welcomeBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new TypeFragment() , "type")
                        .addToBackStack("type").commit();
            }
        });
        binding.welcomeAtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new SelectAtmFragment(), "atm")
                        .addToBackStack("atm").commit();

            }
        });
    }

    private void getData() {


        viewModel.getData();
        viewModel.model.observe(getViewLifecycleOwner(), new Observer<RegModel>() {
            @Override
            public void onChanged(RegModel regModel) {
                SharedModel.setPhone(regModel.getPhone());
                SharedModel.setEmail(regModel.getEmail());
                View header = binding.navView.getHeaderView(0);
                TextView mail = (TextView) header.findViewById(R.id.header_email);
                mail.setText(""+SharedModel.getEmail());
                TextView phone = (TextView) header.findViewById(R.id.header_phone);
                phone.setText(""+SharedModel.getPhone());
                caches.add(new ModelAuthCache(SharedModel.getId(), regModel.getPhone() , regModel.getEmail()));
                SharedModel.cache(caches);


            }
        });

    }
    private void drawer(){

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity() , binding.drawer , binding.toolbar ,
                R.string.drawer_open , R.string.drawer_close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();


        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.menu_upcoming){
                    binding.bar.setVisibility(View.VISIBLE);
                    Upcoming();
                }
                else if (id == R.id.menu_history){
                    binding.bar.setVisibility(View.VISIBLE);
                    History();
                }
                else if (id == R.id.menu_logout){
                    Toast.makeText(getContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    SharedModel.delete(caches.get(0));
                    SharedModel.cache = false;
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new LoginFragment()).commit();

                }

                binding.drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        binding.drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onPause() {
        super.onPause();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener = this;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)){
            binding.drawer.closeDrawer(GravityCompat.START);
        }
        else {
            listener =null;
            requireActivity().onBackPressed();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(requireActivity())
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(requireActivity())
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        SharedModel.setLatitude(latitude);
                                        SharedModel.setLongitude(longitude);


                                      //  Toast.makeText(getContext(), ""+"Latitude: "+ latitude + "\n" + "Longitude: "+ longitude, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(requireActivity(), "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(requireActivity(), 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    private void Upcoming(){
        upcomingViewModel.check2();
        upcomingViewModel.msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Success")){
                    binding.bar.setVisibility(View.GONE);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new UpcomingFragment() , "s")
                            .addToBackStack("s").commit();
                }
                else{
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    binding.bar.setVisibility(View.GONE);
                }
            }
        });

    }

    private void History(){
        historyViewModel.Check();
        historyViewModel.msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.equals("No History Founded")){
                    binding.bar.setVisibility(View.GONE);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new HistoryFragment() , "s")
                            .addToBackStack("s").commit();
                }
                else{
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    binding.bar.setVisibility(View.GONE);
                }

            }
        });


    }


}