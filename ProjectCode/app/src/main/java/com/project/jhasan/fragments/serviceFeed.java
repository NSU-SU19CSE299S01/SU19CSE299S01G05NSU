package com.project.jhasan.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.jhasan.Adapter.RecyclerViewCustomAdapter;
import com.project.jhasan.servicemodel.serviceInfo;
import com.project.jhasan.soudagor.R;

import java.util.ArrayList;

/**
 * Created by jhasan on 27/6/19.
 */

public class serviceFeed extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Provider").child("ServiceList");
    public static ArrayList<serviceInfo> serviceList;
    private RecyclerViewCustomAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_service_feed,container,false);
        myRef.setValue("saudabaji");
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        database.getReference().child("services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Data: ", dataSnapshot.toString());
                 for(DataSnapshot childDataSnapshot: dataSnapshot.getChildren()){
                     serviceInfo forFeedInfo=childDataSnapshot.getValue(serviceInfo.class);
                     serviceList.add(forFeedInfo);
                 }

                 adapter.setServiceList(serviceList);
                 adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView(View view) {

        {



            addService();

            RecyclerView rview = view.findViewById(R.id.serviceList);
            LinearLayoutManager layout = new LinearLayoutManager(getActivity());

             adapter = new RecyclerViewCustomAdapter(getActivity(), serviceList);
            rview.setAdapter(adapter);
            rview.setLayoutManager(layout);

        }
}

    private void addService() {
        serviceList= new ArrayList<>();

        serviceInfo service0=new serviceInfo();


    }


    }

