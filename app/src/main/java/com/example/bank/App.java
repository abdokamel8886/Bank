package com.example.bank;

import android.app.Application;

import com.example.bank.local.MyRoomDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyRoomDatabase.initRoom(this);
    }
}
