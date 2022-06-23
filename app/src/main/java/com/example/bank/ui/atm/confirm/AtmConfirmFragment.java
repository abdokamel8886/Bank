package com.example.bank.ui.atm.confirm;

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
import com.example.bank.models.UpcomingModel;
import com.example.bank.ui.screenshot.ScreenFragment;
import com.example.bank.ui.upcoming.UpcomingViewModel;
import com.example.bank.utils.SharedModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class AtmConfirmFragment extends Fragment {

    FragmentAtmConfirmBinding binding;
    UpcomingViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atm_confirm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAtmConfirmBinding.bind(view);
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
        Date d = null;
        try {
            d = df.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, SharedModel.getSelectedAtm().getWaiting());
        String newTime = df.format(cal.getTime());

        binding.time.setText(newTime);

        binding.adressConfirming.setText(SharedModel.getSelectedAtm().getAddress()+"");
        binding.queueConfirming.setText(SharedModel.getSelectedAtm().getQueue()+"");
        binding.waiting.setText(SharedModel.getSelectedAtm().getWaiting()+"m");
        binding.distanceConfirming.setText(SharedModel.getSelectedAtm().getDistance()+"Km");
        binding.type.setText(SharedModel.getAtmtype()+"");
        binding.money.setText(SharedModel.getAtmmoney()+"LE");

        String des  = SharedModel.getAtmtype() + " Money:" + SharedModel.getAtmmoney()+"LE" ;


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