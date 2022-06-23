package com.example.bank.ui.bank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bank.R;
import com.example.bank.databinding.FragmentTypeBinding;
import com.example.bank.ui.bank.booking.BookingFragment;
import com.example.bank.ui.bank.inquiry.InquiryFragment;
import com.example.bank.utils.SharedModel;


public class TypeFragment extends Fragment {

    FragmentTypeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentTypeBinding.bind(view);
        onClicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void onClicks(){

        binding.inquiryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new InquiryFragment(), "i")
                        .addToBackStack("i").commit();
            }
        });

        binding.loansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setBanktype("Loans Services");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new BookingFragment(), "b")
                        .addToBackStack("b").commit();
            }
        });

        binding.customerServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setBanktype("Customer Services");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new BookingFragment(), "b")
                        .addToBackStack("b").commit();
            }
        });

        binding.bankingServicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setBanktype("Banking Services");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new BookingFragment(), "b")
                        .addToBackStack("b").commit();
            }
        });

    }
}