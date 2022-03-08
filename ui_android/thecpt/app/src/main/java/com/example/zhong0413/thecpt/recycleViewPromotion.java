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

public class recycleViewPromotion extends RecyclerView.Adapter<recycleViewPromotion.MyViewHolderPro>{
    private Context mContext;
    private List<promotionlist> mData;

    public recycleViewPromotion(Context mContext, List<promotionlist>mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public recycleViewPromotion.MyViewHolderPro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.promotionlist,parent,false);
        return new recycleViewPromotion.MyViewHolderPro(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleViewPromotion.MyViewHolderPro holder, final int position) {

        holder.proTitle.setText(mData.get(position).getTitle());
        holder.proDescript.setText(mData.get(position).getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,actdetailsp.class);

                //passing data to the activity
                intent.putExtra("proTitle",mData.get(position).getTitle());
                intent.putExtra("proDescript",mData.get(position).getDescription());
                intent.putExtra("proNo",mData.get(position).getProNo());

                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolderPro extends  RecyclerView.ViewHolder{

        TextView proTitle;
        TextView proDescript;
        CardView cardView;

        public MyViewHolderPro(View itemView){
            super(itemView);

            proTitle = (TextView)itemView.findViewById(R.id.tvPromotionTItle);
            proDescript = (TextView)itemView.findViewById(R.id.tvPromotionDescript);
            cardView = (CardView)itemView.findViewById(R.id.promotion);
        }
    }
}
