package com.project.jhasan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jhasan.Adapter.RecyclerViewCustomAdapter;
import com.project.jhasan.servicemodel.serviceInfo;
import com.project.jhasan.soudagor.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by jhasan on 27/6/19.
 */

public class serviceFeed extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Provider").child("ServiceList");
    public static ArrayList<serviceInfo> serviceList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_service_feed,container,false);
        myRef.setValue("saudabaji");
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {

        {



            addHouse();

            RecyclerView rview = view.findViewById(R.id.toletList);
            LinearLayoutManager layout = new LinearLayoutManager(getActivity());

            RecyclerViewCustomAdapter adapter = new RecyclerViewCustomAdapter(getActivity(), serviceList);
            rview.setAdapter(adapter);
            rview.setLayoutManager(layout);

        }
}

    private void addHouse() {

    }


    }

