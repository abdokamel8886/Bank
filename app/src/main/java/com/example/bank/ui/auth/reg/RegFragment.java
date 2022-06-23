package com.example.bank.ui.auth.reg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.databinding.FragmentRegBinding;
import com.example.bank.ui.home.HomeFragment;
import com.google.firebase.database.core.utilities.Validation;


public class RegFragment extends Fragment {

    FragmentRegBinding binding;
    RegViewModel regViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRegBinding.bind(view);
        regViewModel =new ViewModelProvider(this).get(RegViewModel.class);
        onClicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onClicks(){
        binding.loginSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        binding.signButtonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }

    private void Validation(){

        String email = binding.inputEmmail.getText().toString().trim();
        String phone = binding.inputPhoone.getText().toString().trim();
        String p1 = binding.inputPaswoordd.getText().toString().trim();
        String p2 = binding.inputConfiirmPassword.getText().toString().trim();


        if (email.equals("")){
            binding.inputEmmail.setError("required");
        }
        else if (p1.equals("")){
            binding.inputPaswoordd.setError("required");
        }
        else if (p2.equals("")){
            binding.inputConfiirmPassword.setError("required");
        }
        else if (phone.equals("")){
            binding.inputPhoone.setError("required");
        }
        else if(!p1.equals(p2)){
            Toast.makeText(getContext(), "password not match!", Toast.LENGTH_SHORT).show();
        }
        else{

            binding.bar.setVisibility(View.VISIBLE);
            sign(email,phone,p1);

        }



    }
    private void sign(String email ,String phone, String password){
        regViewModel.Sign(email,phone , password);

        regViewModel.loged.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                binding.bar.setVisibility(View.GONE);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame,new HomeFragment()).commit();

            }
        });
        regViewModel.notloged.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
                binding.bar.setVisibility(View.GONE);
            }
        });
    }
}