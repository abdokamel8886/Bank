package com.example.bank.utils;

import android.location.Location;

import androidx.annotation.NonNull;

import com.example.bank.local.MyRoomDatabase;
import com.example.bank.models.AtmResultModel;
import com.example.bank.models.BankModel;
import com.example.bank.models.ModelAuthCache;
import com.example.bank.models.UpcomingModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SharedModel {



    public static boolean cache =false;

    private static String id;
    private static String phone;
    private static String email;

    private static String qtype;

    private static String atmtype;

    private static String banktype;


    public static double latitude ;
    public static double longitude;

    public static int atmdistance;
    public static int atmmoney;

    public static BankModel selectedBank;

    public static UpcomingModel upcomingModel;


    public static UpcomingModel getUpcomingModel() {
        return upcomingModel;
    }

    public static void setUpcomingModel(UpcomingModel upcomingModel) {
        SharedModel.upcomingModel = upcomingModel;
    }

    public static BankModel getSelectedBank() {
        return selectedBank;
    }

    public static void setSelectedBank(BankModel selectedBank) {
        SharedModel.selectedBank = selectedBank;
    }

    public static String getBanktype() {
        return banktype;
    }

    public static void setBanktype(String banktype) {
        SharedModel.banktype = banktype;
    }

    public static ArrayList<Location> locations = new ArrayList<>();

    public static ArrayList<BankModel> bankresults = new ArrayList<>();

    public static ArrayList<BankModel> getBankresults() {
        return bankresults;
    }

    public static void setBankresults(ArrayList<BankModel> bankresults) {
        SharedModel.bankresults = bankresults;
    }

    public static ArrayList<Location> getLocations() {
        return locations;
    }

    public static void setLocations(ArrayList<Location> locations) {
        SharedModel.locations = locations;
    }

    public static AtmResultModel selectedAtm;

    public static AtmResultModel getSelectedAtm() {
        return selectedAtm;
    }

    public static void setSelectedAtm(AtmResultModel selectedAtm) {
        SharedModel.selectedAtm = selectedAtm;
    }

    public static ArrayList<AtmResultModel> atmResultModels = new ArrayList<>();


    public static ArrayList<AtmResultModel> getAtmResultModels() {
        return atmResultModels;
    }


    public static void setAtmResultModels(ArrayList<AtmResultModel> atmResultModels) {
        SharedModel.atmResultModels = atmResultModels;
    }

    public static int getAtmdistance() {
        return atmdistance;
    }

    public static void setAtmdistance(int atmdistance) {
        SharedModel.atmdistance = atmdistance;
    }

    public static int getAtmmoney() {
        return atmmoney;
    }

    public static void setAtmmoney(int atmmoney) {
        SharedModel.atmmoney = atmmoney;
    }

    public static String getAtmtype() {
        return atmtype;
    }

    public static void setAtmtype(String atmtype) {
        SharedModel.atmtype = atmtype;
    }

    public static String getQtype() {
        return qtype;
    }

    public static void setQtype(String qtype) {
        SharedModel.qtype = qtype;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        SharedModel.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        SharedModel.longitude = longitude;
    }


    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        SharedModel.phone = phone;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SharedModel.email = email;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        SharedModel.id = id;
    }
    public static void cache(List<ModelAuthCache> list){

        MyRoomDatabase.getInstance().getDao().insert(list).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public static void delete(ModelAuthCache model){

        MyRoomDatabase.getInstance().getDao().delete(model).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
}
