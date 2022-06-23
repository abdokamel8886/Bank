package com.example.bank.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.R;
import com.example.bank.models.UpcomingModel;


import java.util.ArrayList;


public class HistoryRecyclerAdabter extends RecyclerView.Adapter<HistoryRecyclerAdabter.Holder> {


    ArrayList<UpcomingModel> list = new ArrayList<>();

    public void setList(ArrayList<UpcomingModel> list) {
        this.list = list;
    }

    private OnItemClick onItemClick ;

    public void setOnItemClick (OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent , false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.code.setText(list.get(position).getCode());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.date.getVisibility() == View.GONE){
                    holder.date.setVisibility(View.VISIBLE);
                    holder.time.setVisibility(View.VISIBLE);
                }
                else{
                    holder.date.setVisibility(View.GONE);
                    holder.time.setVisibility(View.GONE);
                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView code;
        TextView status;
        TextView date;
        TextView time;
        CardView card;


        public Holder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.code);
            status = itemView.findViewById(R.id.status1);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            card = itemView.findViewById(R.id.card1);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null){

                        onItemClick.OnClick();
                    }
                }
            });

        }
    }

    public interface OnItemClick{

        void OnClick();

    }
}
