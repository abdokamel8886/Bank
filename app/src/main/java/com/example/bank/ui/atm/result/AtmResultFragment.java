package com.example.bank.ui.atm.result;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.adapters.AtmResultsRecyclerAdapter;
import com.example.bank.databinding.FragmentAtmResultBinding;
import com.example.bank.models.ATML;
import com.example.bank.models.AtmModel;
import com.example.bank.models.AtmResultModel;
import com.example.bank.ui.atm.confirm.AtmConfirmFragment;
import com.example.bank.ui.maps.AtmMapFragment;
import com.example.bank.utils.SharedModel;

import java.util.ArrayList;


public class AtmResultFragment extends Fragment {

    FragmentAtmResultBinding binding;
    AtmViewModel viewModel;
    ArrayList<Integer> distances = new ArrayList<>();
    ArrayList<AtmModel> infos = new ArrayList<>();
    ArrayList<AtmResultModel> list = new ArrayList<>();
    AtmResultsRecyclerAdapter adapter = new AtmResultsRecyclerAdapter();

    ArrayList<Location> locations = new ArrayList<>();

    ArrayList<ATML> atmlss = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atm_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAtmResultBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(AtmViewModel.class);
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getData(){
        distances.clear();
        infos.clear();

        viewModel.getData1();
        viewModel.list2.observe(getViewLifecycleOwner(), new Observer<ArrayList<ATML>>() {
            @Override
            public void onChanged(ArrayList<ATML> atmls) {


                binding.bar.setVisibility(View.VISIBLE);
                for (ATML m : atmls ) {
                    atmlss.add(m);
                    Double la = Double.parseDouble(m.getLatitude());
                    Double lo = Double.parseDouble(m.getLongitude());

                    Location my=new Location("my");
                    my.setLatitude(SharedModel.getLatitude());
                    my.setLongitude(SharedModel.getLongitude());

                    Location atm=new Location("atm");
                    atm.setLatitude(la);
                    atm.setLongitude(lo);

                    double distance1=my.distanceTo(atm);
                    distances.add((int)distance1/1000);


                }
                viewModel.list.observe(getViewLifecycleOwner(), new Observer<ArrayList<AtmModel>>() {
                    @Override
                    public void onChanged(ArrayList<AtmModel> atmModels) {

                        for (AtmModel m : atmModels) {
                            infos.add(m);
                        }
                        if (!distances.isEmpty()){
                            filtter();
                        }


                    }
                });
            }
        });


    }

    private void filtter(){
        //SharedModel.atmResultModels.clear();
        locations.clear();
        list.clear();
        int size = distances.size();
        Log.e("TAG", "size: "+size );
        for (int i = 0; i < size; i++ ){
            if( distances.get(i) <= SharedModel.getAtmdistance()){

                 if (SharedModel.getAtmtype().equals("deposit")){
                    if (!infos.get(i).getType().equalsIgnoreCase("cash withdraw")){
                        list.add(new AtmResultModel(infos.get(i).getId() , infos.get(i).getAddress() , infos.get(i).getQueue() , infos.get(i).getStatus(),infos.get(i).getType()
                                ,infos.get(i).getMoney() , infos.get(i).getWaiting() , distances.get(i) ));

                        Double la = Double.parseDouble(atmlss.get(i).getLatitude());
                        Double lo = Double.parseDouble(atmlss.get(i).getLongitude());
                        Location atm=new Location("atm");
                        atm.setLatitude(la);
                        atm.setLongitude(lo);
                        locations.add(atm);
                    }
                }
                 else{
                     list.add(new AtmResultModel(infos.get(i).getId() , infos.get(i).getAddress() , infos.get(i).getQueue() , infos.get(i).getStatus(),infos.get(i).getType()
                             ,infos.get(i).getMoney() , infos.get(i).getWaiting() , distances.get(i) ));

                     Double la = Double.parseDouble(atmlss.get(i).getLatitude());
                     Double lo = Double.parseDouble(atmlss.get(i).getLongitude());
                     Location atm=new Location("atm");
                     atm.setLatitude(la);
                     atm.setLongitude(lo);
                     locations.add(atm);
                 }


            }
        }
        SharedModel.setAtmResultModels(list);
        binding.bar.setVisibility(View.GONE);

        if (list.size()==0){
            Toast.makeText(getContext(), "No Atm's Founded in this Distance ", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }

        else{
            show();
        }

    }

    private void show(){
        adapter.setList(SharedModel.getAtmResultModels());
        binding.recyclerResult.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClick(new AtmResultsRecyclerAdapter.OnItemClick() {
            @Override
            public void OnClick(AtmResultModel model) {
                if (!model.getStatus().equals("active")){
                    Toast.makeText(getContext(), "This ATM is Under maintenance", Toast.LENGTH_LONG).show();
                }
                else if (SharedModel.getAtmtype().equals("cashWithdraw") && SharedModel.getAtmmoney()>model.getMoney() ){

                    Toast.makeText(getContext(), "This ATM Have No Enough Money", Toast.LENGTH_LONG).show();
                }
                else {
                    SharedModel.setSelectedAtm(model);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new AtmConfirmFragment(), "c")
                            .addToBackStack("c").commit();
                }
            }
        });

        binding.showOnMapAtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setLocations(locations);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new AtmMapFragment(), "map")
                        .addToBackStack("map").commit();
            }
        });

    }
}