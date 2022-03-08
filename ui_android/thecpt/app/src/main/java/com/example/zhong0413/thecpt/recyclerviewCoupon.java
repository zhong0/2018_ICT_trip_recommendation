package com.example.zhong0413.thecpt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recyclerviewCoupon extends RecyclerView.Adapter<recyclerviewCoupon.MyViewHolderCoupon>{
    private Context mContext;
    private List<couponlist> mData;

    public recyclerviewCoupon(Context mContext, List<couponlist>mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public recyclerviewCoupon.MyViewHolderCoupon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.couponlist,parent,false);
        return new recyclerviewCoupon.MyViewHolderCoupon(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCoupon holder, final int position) {

        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.description.setText(mData.get(position).getDiscription());
        holder.num.setText(mData.get(position).getLeafCostText());
        Log.i("checkRe1",mData.get(position).getLeafCostText());
        Log.i("checkRe",mData.get(position).getCouponNo());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,coupondetail.class);
                //passing data to the activity
                intent.putExtra("title",mData.get(position).getTitle());
                intent.putExtra("description",mData.get(position).getDiscription());
                intent.putExtra("leafNum",mData.get(position).getLeafCostText());
                intent.putExtra("couponNo",mData.get(position).getCouponNo());

                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolderCoupon extends  RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView description;
        TextView num;
        CardView cardView;

        public MyViewHolderCoupon(View itemView){
            super(itemView);

            tvTitle = (TextView)itemView.findViewById(R.id.leafActName);
            description = (TextView)itemView.findViewById(R.id.leafActDescript);
            num = (TextView)itemView.findViewById(R.id.numOfLeaf);
            cardView = (CardView)itemView.findViewById(R.id.leafView);
        }
    }
}
