package com.example.bank.ui.auth.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.databinding.FragmentLoginBinding;
import com.example.bank.ui.auth.reg.RegFragment;
import com.example.bank.ui.home.HomeFragment;
import com.example.bank.utils.SharedModel;

public class LoginFragment extends Fragment {


    FragmentLoginBinding binding;
    LoginViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        onClicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void onClicks(){
        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame ,new RegFragment() , "reg").addToBackStack("reg").commit();
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }

    private void Validation(){

        String email = binding.inputEmaiil.getText().toString().trim();
        String pass = binding.inputPasswoord.getText().toString().trim();

        if (email.equals("")){
            binding.inputEmaiil.setError("required");
        }
        else if (pass.equals("")){
            binding.inputPasswoord.setError("required");
        }
        else{

            binding.bar.setVisibility(View.VISIBLE);
            login(email,pass);

        }



    }

    private void login (String email ,String password){
        viewModel.login(email,password);

        viewModel.loged.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame ,new HomeFragment(), "h").commit();
                binding.bar.setVisibility(View.GONE);
            }
        });

        viewModel.notloged.observe(getViewLifecycleOwner(), new Observer<Exception>() {
            @Override
            public void onChanged(Exception e) {
                Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                binding.bar.setVisibility(View.GONE);
            }
        });
    }

}