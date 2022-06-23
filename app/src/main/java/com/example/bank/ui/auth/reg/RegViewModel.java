package com.example.bank.ui.auth.reg;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.bank.models.RegModel;
import com.example.bank.utils.Consts;
import com.example.bank.utils.SharedModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegViewModel extends ViewModel {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    MutableLiveData<Integer> loged = new MutableLiveData<>();
    MutableLiveData<String> notloged = new MutableLiveData<>();


    public void Sign(String email ,String phone, String password){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                SendData(email,phone,password);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                notloged.setValue(e.getLocalizedMessage());

            }
        });
    }


    private void SendData(String email ,String phone, String password){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (id != null){
            SharedModel.setId(id);
            ref.child(Consts.USERS_REF).child(id).setValue(new RegModel(email,phone , password))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    login(email , password);
                }
            });
        }


    }
    private void login(String email ,String password ){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                loged.setValue(1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


}
