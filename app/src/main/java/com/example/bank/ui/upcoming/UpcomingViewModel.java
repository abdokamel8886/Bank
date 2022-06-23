package com.example.bank.ui.upcoming;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bank.models.QModel;
import com.example.bank.models.UpcomingModel;
import com.example.bank.utils.Consts;
import com.example.bank.utils.SharedModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpcomingViewModel extends ViewModel {


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public MutableLiveData<String> msg = new MutableLiveData<>();
    public MutableLiveData<String> cancel_msg = new MutableLiveData<>();



    public void check(){

        ref.child(Consts.UP_REF).child(SharedModel.getId()).child("Status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null){
                    putData();
                }
                else if (snapshot.getValue().equals("active")){
                    msg.setValue("Complete you Active Service First");
                }
                else{
                    putData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void putData(){
        ref.child(Consts.UP_REF).child(SharedModel.getId()).child("Status").setValue("active").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                ref.child(Consts.UP_REF).child(SharedModel.getId()).child("data").setValue(SharedModel.getUpcomingModel()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        msg.setValue("Success");
                    }
                });
            }
        });

    }


    public void check2(){

        ref.child(Consts.UP_REF).child(SharedModel.getId()).child("Status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null){
                    msg.setValue("No Services are running Currently");
                }
                else if (snapshot.getValue().equals("active")){
                   getData();
                }
                else{
                    msg.setValue("No Services are running Currently");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getData(){
        ref.child(Consts.UP_REF).child(SharedModel.getId()).child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msg.setValue("Success");
                SharedModel.setUpcomingModel(snapshot.getValue(UpcomingModel.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void close(){
        ref.child(Consts.UP_REF).child(SharedModel.getId()).child("Status").setValue("Closed").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                SharedModel.setUpcomingModel(new UpcomingModel("","","",""));
                cancel_msg.setValue("Your appointment deleted successfully");
            }
        });
    }



}
