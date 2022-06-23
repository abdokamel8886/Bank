package com.example.bank.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bank.R;
import com.example.bank.adapters.HistoryRecyclerAdabter;
import com.example.bank.databinding.FragmentHistoryBinding;
import com.example.bank.models.UpcomingModel;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {


    FragmentHistoryBinding binding;
    HistoryRecyclerAdabter adabter = new HistoryRecyclerAdabter();
    HistoryViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHistoryBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getData(){
        binding.bar.setVisibility(View.VISIBLE);
        viewModel.getData();
        viewModel.list.observe(getViewLifecycleOwner(), new Observer<ArrayList<UpcomingModel>>() {
            @Override
            public void onChanged(ArrayList<UpcomingModel> upcomingModels) {
                adabter.setList(upcomingModels);
                binding.recyclerResult.setAdapter(adabter);
                binding.bar.setVisibility(View.GONE);
            }
        });
    }


}