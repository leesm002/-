package com.example.simplememo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MemoViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewTime;

    public MemoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewTime = itemView.findViewById(R.id.textViewTime);
    }
}
