package com.example.bank.ui.history;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bank.models.UpcomingModel;
import com.example.bank.utils.Consts;
import com.example.bank.utils.SharedModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryViewModel extends ViewModel {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public MutableLiveData<ArrayList<UpcomingModel>> list = new MutableLiveData<>();

    public MutableLiveData<String> msg = new MutableLiveData<>();

    ArrayList<UpcomingModel> l = new ArrayList<>();


    public void Check(){
        ref.child(Consts.H_REF).child(SharedModel.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() == null){
                    msg.setValue("No History Founded");
                }
                else{
                    msg.setValue("History Founded");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void getData(){
        l.clear();
        ref.child(Consts.H_REF).child(SharedModel.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren() ) {

                    l.add(snapshot1.getValue(UpcomingModel.class));

                }
                list.setValue(l);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
