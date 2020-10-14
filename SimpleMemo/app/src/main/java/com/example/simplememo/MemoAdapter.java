package com.example.simplememo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemoAdapter extends RecyclerView.Adapter<MemoViewHolder> {
    private ArrayList<MemoBean> data;
    private MemoItemClickListener listener;

    public MemoAdapter(ArrayList<MemoBean> data, MemoItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_memo, viewGroup, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder memoViewHolder, int i) {
        MemoBean memo = data.get(i);

        memoViewHolder.textViewTitle.setText(memo.getTitle());
        Date date = new Date(memo.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String time = format.format(date);
        memoViewHolder.textViewTime.setText(time);

        memoViewHolder.itemView.setOnClickListener(v->{
            listener.onMemoItemClick(v, i);
        });
    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;
        else return data.size();
    }
}
