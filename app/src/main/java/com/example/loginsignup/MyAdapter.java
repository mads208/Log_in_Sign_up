package com.example.loginsignup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.ViewHolder  {
    private String[] myDataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder (View v){
            super(v);
            textView = v.findViewById(R.id.tvNameRow);
        }
    }

    public MyAdapter(String[] myDataSet)
    {
        this.myDataSet = myDataSet;
    }

    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_row, parent, false);

        return null;
    }

    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.textView.setText(myDataSet[position]);
    }

    public int getItemCount(){
        return myDataSet.length;
    }
}

