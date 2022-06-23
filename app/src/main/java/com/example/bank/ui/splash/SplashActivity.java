package com.example.bank.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.example.bank.ui.onboard.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import com.example.bank.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    Handler h=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, onboarding.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}




