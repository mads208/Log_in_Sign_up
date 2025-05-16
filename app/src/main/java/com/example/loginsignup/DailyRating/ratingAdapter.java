package com.example.loginsignup.DailyRating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.R;
import com.example.loginsignup.general.FirebaseServices;
import com.example.loginsignup.user.DataUser;
import com.example.loginsignup.user.DataUserAdapter;

import java.util.ArrayList;

public class ratingAdapter extends RecyclerView.Adapter<ratingAdapter.MyViewHolder> {
    Context context;
    ArrayList<Rating> ratingList;
    private FirebaseServices fbs;
    public ratingAdapter(Context context, ArrayList<Rating> ratingList) {
        this.context = context;
        this.ratingList = ratingList;
        this.fbs = FirebaseServices.getInstance();
    }



    @NonNull

    public ratingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.rating_item,parent,false);
        return  new ratingAdapter.MyViewHolder(v);
    }


    public void onBindViewHolder(@NonNull ratingAdapter.MyViewHolder holder, int position)
    {
        Rating rate = ratingList.get(position);
        holder.seekbarPercent.setText(rate.getSeekbarPercent());
        holder.currentDate1.setText(rate.getCurrentDate());
    }


    public int getItemCount(){
        return ratingList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView seekbarPercent, currentDate1 ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            seekbarPercent=itemView.findViewById(R.id.seekbarPercentItem);
            currentDate1=itemView.findViewById(R.id.currentDate1item);
        }
    }
}
