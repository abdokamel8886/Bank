package com.example.bank.ui.bank.result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.adapters.BankResultsRecyclerAdapter;
import com.example.bank.databinding.FragmentBankResultBinding;
import com.example.bank.models.BankModel;
import com.example.bank.ui.bank.confirm.BankConfirmFragment;
import com.example.bank.ui.maps.BankMapFragment;
import com.example.bank.utils.SharedModel;

import java.util.ArrayList;


public class BankResultFragment extends Fragment {

    FragmentBankResultBinding binding;
    BankResultViewModel viewModel;
    BankResultsRecyclerAdapter adapter = new BankResultsRecyclerAdapter();

    ArrayList<BankModel> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBankResultBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(BankResultViewModel.class);
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
        viewModel.list.observe(getViewLifecycleOwner(), new Observer<ArrayList<BankModel>>() {
            @Override
            public void onChanged(ArrayList<BankModel> bankModels) {
                list.clear();
                list = bankModels;
                show();
            }
        });
    }

    private void show(){
        adapter.setList(list);
        binding.recyclerResult.setAdapter(adapter);
        binding.bar.setVisibility(View.GONE);
        if(adapter.getItemCount() !=0){
            adapter.setOnItemClick(new BankResultsRecyclerAdapter.OnItemClick() {
                @Override
                public void OnClick(BankModel model) {
                    SharedModel.setSelectedBank(model);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new BankConfirmFragment(), "cm")
                            .addToBackStack("cm").commit();
                }
            });

            binding.showOnMapAtm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new BankMapFragment(), "m")
                            .addToBackStack("m").commit();
                }
            });

        }
        else{
            Toast.makeText(getContext(), "No Bank's Founded in this Distance ", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }



    }
}