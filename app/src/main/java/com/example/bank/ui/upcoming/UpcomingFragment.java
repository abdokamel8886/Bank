package com.example.bank.ui.upcoming;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.databinding.FragmentUpcomingBinding;
import com.example.bank.utils.SharedModel;


public class UpcomingFragment extends Fragment {

    FragmentUpcomingBinding binding;
    UpcomingViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentUpcomingBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(UpcomingViewModel.class);

        binding.code.setText(SharedModel.getUpcomingModel().getCode());
        binding.date.setText(SharedModel.getUpcomingModel().getDate());
        binding.time.setText(SharedModel.getUpcomingModel().getTime());
        binding.des.setText(SharedModel.getUpcomingModel().getDes());

        binding.des.setVisibility(View.GONE);


        onClicks();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onClicks(){
        binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.des.getVisibility() == View.VISIBLE){

                    binding.des.setVisibility(View.GONE);
                }
                else {
                    binding.des.setVisibility(View.VISIBLE);
                }


            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


    }


    private void showDialog() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        Button no = dialog.findViewById(R.id.stay);
        Button yes = dialog.findViewById(R.id.exit);


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bar.setVisibility(View.VISIBLE);
                dialog.dismiss();
                Cancel();
            }
        });

        dialog.show();
    }

    private void Cancel(){
        viewModel.close();
        viewModel.cancel_msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                binding.bar.setVisibility(View.GONE);
                requireActivity().onBackPressed();
            }
        });



    }







}