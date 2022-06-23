package com.example.bank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bank.databinding.ActivityMainBinding;
import com.example.bank.local.MyRoomDatabase;
import com.example.bank.models.ModelAuthCache;
import com.example.bank.ui.auth.login.LoginFragment;
import com.example.bank.ui.home.HomeFragment;
import com.example.bank.ui.screenshot.ScreenFragment;
import com.example.bank.utils.SharedModel;
import com.google.android.gms.location.LocationRequest;


import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getcache();
    }


    @Override
    public void onBackPressed() {

        if (HomeFragment.listener != null){
            HomeFragment.listener.onBackPressed();
        }
        else if (ScreenFragment.listener != null){
            ScreenFragment.listener.onBackPressed();
        }
        else{

           super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void splash(){

        if(SharedModel.cache == false){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame , new LoginFragment()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame , new HomeFragment()).commit();
        }

    }

    private   void getcache(){
        MyRoomDatabase.getInstance().getDao().getall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ModelAuthCache>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ModelAuthCache> modelAuthCaches) {


                        if(modelAuthCaches.size()>0){
                            SharedModel.cache = true;
                            SharedModel.setId(modelAuthCaches.get(0).getUser_id());
                            SharedModel.setPhone(modelAuthCaches.get(0).getUser_phone());
                            SharedModel.setEmail(modelAuthCaches.get(0).getEmail());
                            splash();
                        }
                        else{
                            splash();
                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        splash();
                    }
                });
    }
}