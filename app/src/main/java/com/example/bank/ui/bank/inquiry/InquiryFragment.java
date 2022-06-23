package com.example.bank.ui.bank.inquiry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bank.R;
import com.example.bank.databinding.FragmentInquiryBinding;
import com.example.bank.ui.bank.inquiry.questions.QuestionsFragment;
import com.example.bank.utils.SharedModel;


public class InquiryFragment extends Fragment {



    FragmentInquiryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inquiry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentInquiryBinding.bind(view);

        onClicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onClicks(){
        binding.CurrenciesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setQtype("Currencies");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new QuestionsFragment(), "q")
                        .addToBackStack("q").commit();
            }
        });
        binding.AccountsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setQtype("account");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new QuestionsFragment(), "q")
                        .addToBackStack("q").commit();
            }
        });
        binding.LoansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setQtype("Loans");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new QuestionsFragment(), "q")
                        .addToBackStack("q").commit();
            }
        });

        binding.DepositsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setQtype("deposit");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new QuestionsFragment(), "q")
                        .addToBackStack("q").commit();
            }
        });




    }
}