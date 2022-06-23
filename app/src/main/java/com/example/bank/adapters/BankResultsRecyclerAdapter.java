package com.example.bank.adapters;


import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.R;
import com.example.bank.models.AtmResultModel;
import com.example.bank.models.BankModel;
import com.example.bank.utils.SharedModel;

import java.util.ArrayList;

public class BankResultsRecyclerAdapter extends RecyclerView.Adapter<BankResultsRecyclerAdapter.Holder> {


    ArrayList<BankModel> list = new ArrayList<>();
    ArrayList<BankModel> list1 = new ArrayList<>();

    public void setList(ArrayList<BankModel> list) {
        this.list = list;
        for (BankModel m : list) {

            Double la = m.getLocation().getLatitude();
            Double lo = m.getLocation().getLongitude();

            Location my=new Location("my");
            my.setLatitude(SharedModel.getLatitude());
            my.setLongitude(SharedModel.getLongitude());

            Location b=new Location("b");
            b.setLatitude(la);
            b.setLongitude(lo);

            double distance1=my.distanceTo(b);
            int d = (int) distance1/1000 ;

            if (d <= SharedModel.getAtmdistance()){
                list1.add(m);
            }

        }
        SharedModel.setBankresults(list1);
    }

    private BankResultsRecyclerAdapter.OnItemClick onItemClick ;

    public void setOnItemClick (BankResultsRecyclerAdapter.OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank , parent , false);

        return new BankResultsRecyclerAdapter.Holder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {


        Double la = list1.get(position).getLocation().getLatitude();
        Double lo = list1.get(position).getLocation().getLongitude();

        Location my=new Location("my");
        my.setLatitude(SharedModel.getLatitude());
        my.setLongitude(SharedModel.getLongitude());

        Location b=new Location("b");
        b.setLatitude(la);
        b.setLongitude(lo);

        double distance1=my.distanceTo(b);
        int d = (int) distance1/1000 ;


        holder.n.setText(list1.get(position).getAddress()+"");
        holder.w.setText(list1.get(position).getWaiting()+"m");
        holder.d.setText(d+"Km");


        if(SharedModel.getBanktype().equals("Loans Services")){
            holder.q.setText(list1.get(position).getLoanTeller()+"");
        }
        else if(SharedModel.getBanktype().equals("Customer Services")){
            holder.q.setText(list1.get(position).getCustomerTeller()+"");
        }
        else{
            holder.q.setText(list1.get(position).getBankingTeller()+"");
        }



    }

    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView n , q ,w , d;
        public Holder(@NonNull View itemView) {
            super(itemView);
            q = itemView.findViewById(R.id.teller2);
            w = itemView.findViewById(R.id.waiting_time2);
            n = itemView.findViewById(R.id.name2);
            d = itemView.findViewById(R.id.distance2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null){

                        onItemClick.OnClick(list1.get(getLayoutPosition()));
                    }
                }
            });





    }
    }

    public interface OnItemClick{
        void OnClick(BankModel model);

    }


}
