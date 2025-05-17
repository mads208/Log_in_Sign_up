package com.example.loginsignup.journaling;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.DailyRating.Rating;
import com.example.loginsignup.DailyRating.ratingAdapter;
import com.example.loginsignup.R;
import com.example.loginsignup.general.FirebaseServices;

import java.util.ArrayList;

public class journalAdapter extends RecyclerView.Adapter<journalAdapter.MyViewHolder> {
    Context context;
    ArrayList<Journal> journalsList;
    private FirebaseServices fbs;
    public journalAdapter(Context context, ArrayList<Journal> journalsList) {
        this.context = context;
        this.journalsList = journalsList;
        this.fbs = FirebaseServices.getInstance();
    }



    @NonNull

    public journalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.journal_item,parent,false);
        return  new journalAdapter.MyViewHolder(v);
    }


    public void onBindViewHolder(@NonNull journalAdapter.MyViewHolder holder, int position)
    {
        Journal journal = journalsList.get(position);
        holder.TitleA.setText(journal.getTitle2());
        holder.TextA.setText(journal.getText2());
        holder.createdDateA.setText(journal.getCreatedDateJournal());

    }


    public int getItemCount(){
        return journalsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TitleA, TextA ,createdDateA;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleA=itemView.findViewById(R.id.TitleItem);
            TextA=itemView.findViewById(R.id.TextItem);
            createdDateA=itemView.findViewById(R.id.createdDatejoItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String fullTask = TextA.getText().toString();

                        new AlertDialog.Builder(itemView.getContext())
                                .setTitle("")
                                .setMessage(fullTask)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
            });
        }
    }
}
