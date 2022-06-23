package com.example.bank.ui.bank.confirm;

import android.location.Location;
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
import com.example.bank.databinding.FragmentAtmConfirmBinding;
import com.example.bank.databinding.FragmentBankConfirmBinding;
import com.example.bank.models.UpcomingModel;
import com.example.bank.ui.screenshot.ScreenFragment;
import com.example.bank.ui.upcoming.UpcomingViewModel;
import com.example.bank.utils.SharedModel;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class BankConfirmFragment extends Fragment {

  FragmentBankConfirmBinding binding;
  UpcomingViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank_confirm, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBankConfirmBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(UpcomingViewModel.class);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        binding.date.setText(currentDate);

        final int min = 1000;
        final int max = 9999;
        final int randomNumber = new Random().nextInt((max - min) + 1) + min;

        binding.code.setText(randomNumber+"");

        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm  a");
        String  currentTime = df1.format(new Date().getTime());


        SimpleDateFormat df = new SimpleDateFormat("HH:mm  a");
        Date d1 = null;
        try {
            d1 = df.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        cal.add(Calendar.MINUTE, SharedModel.getSelectedBank().getWaiting());
        String newTime = df.format(cal.getTime());

        binding.time.setText(newTime);

        binding.adressConfirming.setText(SharedModel.getSelectedBank().getAddress()+"");

        if(SharedModel.getBanktype().equals("Loans Services")){
            binding.teller.setText(SharedModel.getSelectedBank().getLoanTeller()+"");
        }
        else if(SharedModel.getBanktype().equals("Customer Services")){
            binding.teller.setText(SharedModel.getSelectedBank().getCustomerTeller()+"");
        }
        else{
            binding.teller.setText(SharedModel.getSelectedBank().getBankingTeller()+"");
        }

        binding.waiting.setText(SharedModel.getSelectedBank().getWaiting()+"m");
        binding.type.setText(SharedModel.getBanktype()+"");

        Double la = SharedModel.getSelectedBank().getLocation().getLatitude();
        Double lo = SharedModel.getSelectedBank().getLocation().getLongitude();

        Location my=new Location("my");
        my.setLatitude(SharedModel.getLatitude());
        my.setLongitude(SharedModel.getLongitude());

        Location b=new Location("b");
        b.setLatitude(la);
        b.setLongitude(lo);

        double distance1=my.distanceTo(b);
        int d = (int) distance1/1000 ;

        binding.distanceConfirming.setText(d+"Km");



        String des  = SharedModel.getBanktype() ;


        binding.confrmReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setUpcomingModel(new UpcomingModel(newTime,randomNumber+"",currentDate,des));
                Check();

            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void Check(){

        viewModel.check();

        viewModel.msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Success")){
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new ScreenFragment() , "s")
                            .addToBackStack("s").commit();
                }
                else{
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}