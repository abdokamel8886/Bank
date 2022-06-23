package com.example.bank.ui.bank.inquiry.questions;

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
import com.example.bank.adapters.Q2RecyclerAdapter;
import com.example.bank.adapters.QRecyclerAdapter;
import com.example.bank.databinding.FragmentQuestionsBinding;
import com.example.bank.models.QModel;
import com.example.bank.utils.SharedModel;

import java.util.ArrayList;


public class QuestionsFragment extends Fragment {



    FragmentQuestionsBinding binding;
    ArrayList<QModel> list = new ArrayList<>();
    ArrayList<QModel> list2 = new ArrayList<>();

    QRecyclerAdapter adapter = new QRecyclerAdapter();
    Q2RecyclerAdapter adapter2 = new Q2RecyclerAdapter();

    QViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentQuestionsBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(QViewModel.class);

        if(SharedModel.getQtype().equals("Currencies")){
            binding.img.setVisibility(View.VISIBLE);
        }

        getQuestions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getQuestions(){

        viewModel.getData();
        viewModel.list.observe(getViewLifecycleOwner(), new Observer<ArrayList<QModel>>() {
            @Override
            public void onChanged(ArrayList<QModel> qModels) {
                list = qModels;
                show1();
            }
        });

    }

    private void show1(){
        adapter.setList(list);
        binding.list2.setAdapter(adapter);
        adapter.setOnItemClick(new QRecyclerAdapter.OnItemClick() {
            @Override
            public void OnClick(String q, String a) {
                list2.add(new QModel(a,q));
                show2();

            }
        });
    }
    private void show2(){
        adapter2.setList(list2);
        binding.list1.setAdapter(adapter2);
        binding.list1.smoothScrollToPosition(adapter2.getItemCount());
    }

}