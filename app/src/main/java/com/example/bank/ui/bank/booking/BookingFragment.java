package com.example.bank.ui.bank.booking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.databinding.FragmentBookingBinding;
import com.example.bank.ui.bank.result.BankResultFragment;
import com.example.bank.utils.SharedModel;


public class BookingFragment extends Fragment {

    FragmentBookingBinding binding;

    String [] distances = {"1","5","10","15","20","25","30","60"};

    ArrayAdapter<String> Gadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBookingBinding.bind(view);

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

        if (d.equals("")){
            Toast.makeText(getContext(), "No Distance Chosen", Toast.LENGTH_SHORT).show();
        }

        else{
            SharedModel.setAtmdistance(Integer.parseInt(d));
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new BankResultFragment(), "ban").addToBackStack("ban").commit();

        }

    }
}