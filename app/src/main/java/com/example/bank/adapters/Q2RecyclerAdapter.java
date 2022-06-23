package com.example.bank.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.R;
import com.example.bank.models.QModel;

import java.util.ArrayList;

public class Q2RecyclerAdapter extends RecyclerView.Adapter<Q2RecyclerAdapter.Holder> {


    ArrayList<QModel> list = new ArrayList<>();

    public void setList(ArrayList<QModel> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_q2 , parent , false);

        return new Q2RecyclerAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.q.setText(list.get(position).getQues());
        holder.a.setText(list.get(position).getAns());


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView q , a;
        public Holder(@NonNull View itemView) {
            super(itemView);
            q = itemView.findViewById(R.id.q2_txt);
            a = itemView.findViewById(R.id.a2_txt);


        }
    }


}
