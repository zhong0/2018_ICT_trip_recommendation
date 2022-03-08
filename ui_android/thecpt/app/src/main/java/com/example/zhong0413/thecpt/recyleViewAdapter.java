package com.example.zhong0413.thecpt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recyleViewAdapter extends RecyclerView.Adapter<recyleViewAdapter.MyViewHolderAda> {

    private Context mContext;
    private List<historylist> mData;

    public recyleViewAdapter(Context mContext, List<historylist>mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolderAda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.historylist,parent,false);
        return new MyViewHolderAda(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderAda holder, final int position) {

        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvDate.setText(mData.get(position).getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,actdiary.class);

                //passing data to the activity
                intent.putExtra("ActTitle",mData.get(position).getTitle());
                intent.putExtra("ActDate",mData.get(position).getDate());
                intent.putExtra("ActNo",mData.get(position).getActNo());

                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolderAda extends  RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvDate;
        CardView cardView;

        public MyViewHolderAda(View itemView){
            super(itemView);

            tvTitle = (TextView)itemView.findViewById(R.id.tvRVactName);
            tvDate = (TextView)itemView.findViewById(R.id.tvRVactTime);
            cardView = (CardView)itemView.findViewById(R.id.cardView_id);
        }
    }


}
