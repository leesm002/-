package com.example.testapp;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ItemViewHolder> {

    private ArrayList<Bean> userData;
    private ItemClickListener listener;

    public Adapter(ArrayList<Bean> data, ItemClickListener listener){
        userData = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.linear_item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
        Bean userBean = userData.get(i);

        viewHolder.item_contents.setText(userBean.getContents());
        viewHolder.item_number.setText(String.valueOf(userBean.get_ID()));
        final int index = i;
        viewHolder.itemView.setOnClickListener(v->{
            listener.onItemClick(v, index);
        });



    }

    @Override
    public int getItemCount() {
        if(userData == null)
            return 0;
        else return userData.size();
    }

}
