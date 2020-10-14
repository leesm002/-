package com.example.testapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView item_number;    //기본값 번호
    TextView item_contents;  //문제 내용

    public ItemViewHolder(@NonNull View itemView){
        super(itemView);
        item_number = itemView.findViewById(R.id.item_number);
        item_contents = itemView.findViewById(R.id.item_contents);

    }

}
