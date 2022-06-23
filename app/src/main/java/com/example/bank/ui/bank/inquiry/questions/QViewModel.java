package com.example.bank.ui.bank.inquiry.questions;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bank.models.QModel;
import com.example.bank.utils.Consts;
import com.example.bank.utils.SharedModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QViewModel extends ViewModel {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    MutableLiveData<ArrayList<QModel>> list = new MutableLiveData<>();
    ArrayList<QModel> l = new ArrayList<>();



    public void getData(){
        ref.child(Consts.Q_REF).child(SharedModel.getQtype()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    l.add(snapshot1 .getValue(QModel.class));
                }
                list.setValue(l);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}
