package com.example.loginsignup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataUserAdapter extends RecyclerView.Adapter<DataUserAdapter.MyViewHolder>  {

    Context context;
    ArrayList<DataUser> userList;
    private FirebaseServices fbs;



    public DataUserAdapter(Context context, ArrayList<DataUser> userList) {
        this.context = context;
        this.userList = userList;
        this.fbs = FirebaseServices.getInstance();
    }



    @NonNull

    public DataUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return  new DataUserAdapter.MyViewHolder(v);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        DataUser user = userList.get(position);
        holder.tvMusicGenre.setText(user.getMusicGenre());
        holder.tvTaskDays.setText(user.getTaskDays());
    }


    public int getItemCount(){
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMusicGenre, tvTaskDays , tvGoalItem;
        ImageView imgItemlogin ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMusicGenre=itemView.findViewById(R.id.tvMusicGenreUserItem);
            tvTaskDays=itemView.findViewById(R.id.tvTaskDaysUserItem);
            tvGoalItem=itemView.findViewById(R.id.tvGoalItem);
            imgItemlogin =itemView.findViewById(R.id.imgItem);


        }
    }
}
