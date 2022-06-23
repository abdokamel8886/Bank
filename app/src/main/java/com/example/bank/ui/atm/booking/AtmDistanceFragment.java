package com.example.bank.ui.atm.booking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.databinding.FragmentAtmDistanceBinding;
import com.example.bank.ui.atm.result.AtmResultFragment;
import com.example.bank.ui.atm.result.AtmViewModel;
import com.example.bank.utils.SharedModel;


public class AtmDistanceFragment extends Fragment {

    FragmentAtmDistanceBinding binding;
    AtmViewModel viewModel;
    String [] distances = {"1","5","10","15","20","25","30","60"};
    ArrayAdapter<String> Gadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atm_distance, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAtmDistanceBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(AtmViewModel.class);

        if (SharedModel.getAtmtype().equals("cashWithdraw")){
            binding.note.setVisibility(View.VISIBLE);
        }


        Gadapter = new ArrayAdapter(getContext(),R.layout.drop_item , distances);
        binding.distEdit.setAdapter(Gadapter);
        Gadapter.notifyDataSetChanged();

        onClicks();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void onClicks(){
        binding.atmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }
    private void Validation(){

        String d = binding.distEdit.getText().toString().trim();
        String m  = binding.moneyEdit.getText().toString().trim();

        if (d.equals("")){
            Toast.makeText(getContext(), "No Distance Chosen", Toast.LENGTH_SHORT).show();
        }
        else if (m.equals("")){
            binding.moneyEdit.setError("required");
        }
        else if (SharedModel.getAtmtype().equals("cashWithdraw") && Integer.parseInt(m) > 8000){
            Toast.makeText(getContext(), "8000.LE is the limit of WithDrawing", Toast.LENGTH_SHORT).show();
        }
        else{
            SharedModel.setAtmdistance(Integer.parseInt(d));
            SharedModel.setAtmmoney(Integer.parseInt(m));

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new AtmResultFragment(), "atm")
                    .addToBackStack("atm").commit();

        }

    }
}