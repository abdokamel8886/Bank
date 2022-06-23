package com.example.bank.ui.atm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bank.R;
import com.example.bank.databinding.FragmentSelectAtmBinding;
import com.example.bank.ui.atm.booking.AtmDistanceFragment;
import com.example.bank.utils.SharedModel;


public class SelectAtmFragment extends Fragment {

    FragmentSelectAtmBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_atm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSelectAtmBinding.bind(view);
        onClicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void onClicks(){
        binding.depositMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setAtmtype("deposit");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new AtmDistanceFragment(), "dw")
                        .addToBackStack("dw").commit();
            }
        });

        binding.cashWithdrawel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setAtmtype("cashWithdraw");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new AtmDistanceFragment(), "dw")
                        .addToBackStack("dw").commit();
            }
        });
    }
}