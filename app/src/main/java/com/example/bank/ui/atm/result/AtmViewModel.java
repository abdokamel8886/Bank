package com.example.bank.ui.atm.result;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bank.models.ATML;
import com.example.bank.models.AtmModel;
import com.example.bank.models.QModel;
import com.example.bank.utils.Consts;
import com.example.bank.utils.SharedModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AtmViewModel extends ViewModel {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    MutableLiveData<ArrayList<AtmModel>> list = new MutableLiveData<>();
    MutableLiveData<ArrayList<ATML>> list2 = new MutableLiveData<>();

    ArrayList<AtmModel> l = new ArrayList<>();
    int i =0;
    ArrayList<ATML> l2 = new ArrayList<>();



    public void getData(){
        l.clear();
        ref.child("ATM").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    l.add(snapshot1 .getValue(AtmModel.class));
                }
                list.setValue(l);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }






    public void getData1(){
        l2.clear();
        ref.child("ATML").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    l2.add(snapshot1 .getValue(ATML.class));
                }
                list2.setValue(l2);
                getData();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    public void getData2(){
        l2.add(new ATML("30.673214573568572","31.592177718295254"));
        l2.add(new ATML("30.416588794643943","31.567871392354167"));
        l2.add(new ATML("30.59073305866727","31.508278724586795"));
        l2.add(new ATML("30.58530234831453","31.496541378791182"));

        l2.add(new ATML("30.583455098623688","31.505725261294106"));
        l2.add(new ATML("30.59671826793222","31.49657874236597"));
        l2.add(new ATML("30.590586571277118","31.523229171695306"));
        l2.add(new ATML("30.375994437750126","31.450566900688624"));

        l2.add(new ATML("30.361555318681262","31.38471508630389"));
        l2.add(new ATML("30.422173583807336","31.567370985990053"));
        l2.add(new ATML("30.725897109680112","31.668082710856453"));
        l2.add(new ATML("30.297719142598453","31.74576966870389"));

        l2.add(new ATML("30.5870394711604","31.497093746671776"));
        l2.add(new ATML("30.591473233247815","31.516963551707757"));
        l2.add(new ATML("30.58567307717071","31.50065570237291"));
        l2.add(new ATML("30.570488264241405","31.508177160686152"));

        l2.add(new ATML("30.557351345918594","31.67613657844508"));
        l2.add(new ATML("30.510204463169092","31.342780377272042"));


        for (ATML m : l2) {
            i++;
            ref.child("ATML").child("atm"+i).setValue(m);
        }

    }
}
