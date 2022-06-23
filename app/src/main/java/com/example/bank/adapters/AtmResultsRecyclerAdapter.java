package com.example.bank.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.R;
import com.example.bank.models.AtmResultModel;
import com.example.bank.models.QModel;

import java.util.ArrayList;

public class AtmResultsRecyclerAdapter extends RecyclerView.Adapter<AtmResultsRecyclerAdapter.Holder> {


    ArrayList<AtmResultModel> list = new ArrayList<>();

    public void setList(ArrayList<AtmResultModel> list) {
        this.list = list;
    }

    private AtmResultsRecyclerAdapter.OnItemClick onItemClick ;

    public void setOnItemClick (AtmResultsRecyclerAdapter.OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_branch , parent , false);

        return new AtmResultsRecyclerAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.n.setText(list.get(position).getAddress()+"");
        holder.w.setText(list.get(position).getWaiting()+"m");
        holder.q.setText(list.get(position).getQueue()+"");
        holder.d.setText(list.get(position).getDistance()+"Km");


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView n , q ,w , d;
        public Holder(@NonNull View itemView) {
            super(itemView);
            q = itemView.findViewById(R.id.queue2);
            w = itemView.findViewById(R.id.waiting_time2);
            n = itemView.findViewById(R.id.name2);
            d = itemView.findViewById(R.id.distance2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null){

                        onItemClick.OnClick(list.get(getLayoutPosition()));
                    }
                }
            });





    }
    }

    public interface OnItemClick{
        void OnClick(AtmResultModel model);

    }


}
