package com.example.bank.ui.bank.result;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bank.models.ATML;
import com.example.bank.models.AtmModel;
import com.example.bank.models.BankModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BankResultViewModel extends ViewModel {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    MutableLiveData<ArrayList<BankModel>> list = new MutableLiveData<>();

    ArrayList<BankModel> l = new ArrayList<>();




    public void getData(){
        l.clear();
        ref.child("bank").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                l.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    l.add(snapshot1 .getValue(BankModel.class));
                }
                list.setValue(l);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }







}
