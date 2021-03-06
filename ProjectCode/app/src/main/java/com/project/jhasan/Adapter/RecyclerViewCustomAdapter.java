package com.project.jhasan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.jhasan.servicemodel.serviceInfo;
import com.project.jhasan.soudagor.R;

import java.util.List;

/**
 * Created by jhasan on 29/6/19.
 */

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.AdapterViewHolder> {


    Context context;
    List<serviceInfo> serviceList;
    LayoutInflater layoutInflater;


    public RecyclerViewCustomAdapter(Context c, List<serviceInfo> house) {

        context = c;
        serviceList = house;
        layoutInflater = LayoutInflater.from(context);


    }

    public void setServiceList(List<serviceInfo> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.serviceinfo_item, parent, false);

        return new AdapterViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {


        serviceInfo serviceInfo = serviceList.get(position);
        Glide.with(context)
                .load(serviceInfo.getImage())
                .into(holder.imageView);
        holder.titleTextView.setText(serviceInfo.getserviceCategory());
        holder.feeTextView.setText(serviceInfo.getServiceFees() + " BDT ");
        holder.ratingBar.setRating((float) serviceInfo.getServiceRating());
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {

        Context context;
        Button callButton;
        ImageView imageView;
        TextView feeTextView;
        TextView titleTextView;
        RatingBar ratingBar;
        CardView cardView;
        ImageView favorite;

        public AdapterViewHolder(View itemView, Context c) {
            super(itemView);
            context = c;

            imageView = itemView.findViewById(R.id.serviceImageview);
            feeTextView = itemView.findViewById(R.id.feeTextView);
            titleTextView = itemView.findViewById(R.id.serviceTitle);
            ratingBar = itemView.findViewById(R.id.ratigService);
            favorite = itemView.findViewById(R.id.favoriteImage);
            cardView = itemView.findViewById(R.id.cardView);


        }




        }

    }
