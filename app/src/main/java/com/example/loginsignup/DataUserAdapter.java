package com.example.loginsignup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataUserAdapter extends RecyclerView.Adapter<DataUserAdapter.MyViewHolder>  {

    Context context;
    ArrayList<DataUser> restList;
    private FirebaseServices fbs;



    public DataUserAdapter(Context context, ArrayList<DataUser> restList) {
        this.context = context;
        this.restList = restList;
        this.fbs = FirebaseServices.getInstance();
    }



    @NonNull

    public DataUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.rest_item,parent,false);
        return  new DataUserAdapter.MyViewHolder(v);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataUser rest = restList.get(position);
        holder.tvMusicGenre.setText(rest.getMusicGenre());
        holder.tvTaskDays.setText(rest.getTaskDays());
    }


    public int getItemCount(){
        return restList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMusicGenre, tvTaskDays;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMusicGenre=itemView.findViewById(R.id.tvMusicGenreRestItem);
            tvTaskDays=itemView.findViewById(R.id.tvTaskDaysRestItem);

        }
    }
}
