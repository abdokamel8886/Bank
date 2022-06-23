package com.example.bank.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.R;
import com.example.bank.models.QModel;

import java.util.ArrayList;

public class QRecyclerAdapter extends RecyclerView.Adapter<QRecyclerAdapter.Holder> {


    ArrayList<QModel> list = new ArrayList<>();

    public void setList(ArrayList<QModel> list) {
        this.list = list;
    }

    private QRecyclerAdapter.OnItemClick onItemClick ;

    public void setOnItemClick (QRecyclerAdapter.OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_q , parent , false);

        return new QRecyclerAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.q.setText(list.get(position).getQues());


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView q;
        public Holder(@NonNull View itemView) {
            super(itemView);
            q = itemView.findViewById(R.id.q_txt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null){

                        onItemClick.OnClick(list.get(getLayoutPosition()).getQues() , list.get(getLayoutPosition()).getAns() );
                    }
                }
            });

        }
    }

    public interface OnItemClick{

        void OnClick(String q , String a);

    }
}
