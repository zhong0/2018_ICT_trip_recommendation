package com.example.zhong0413.thecpt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class recycleViewMyDiscount extends RecyclerView.Adapter<recycleViewMyDiscount.MyViewHolderDis>{

    private RequestQueue rq;
    private Context mContext;
    private List<myDiscountList> mData;
    public static boolean clicked;
    private static String coupNo;
    private static String coupID;
    private static int p;
    private static boolean[] posi = new boolean[100];

    public recycleViewMyDiscount(Context mContext, List<myDiscountList>mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolderDis onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.mydiscountlist,parent,false);
        return new recycleViewMyDiscount.MyViewHolderDis(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final recycleViewMyDiscount.MyViewHolderDis holder, final int position) {
        if(mydicount.getConsumed().equals("")){
            posi[position]  = false;
        }
        if(mydicount.getConsumed().equals("1")){
            posi[p] = true;
        }

        if(posi[position]){
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.changed));
        }

        holder.cardView.setTag(position);
        holder.discountTitle.setText(mData.get(position).getTitle());
        holder.discountDescript.setText(mData.get(position).getDiscription());
        holder.discountDeadline.setText(mData.get(position).getDeadline());
        holder.img.setImageURI(mData.get(position).getUri());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = (int)v.getTag();
                coupNo = mData.get(p).getCoupNo();
                coupID = mData.get(p).getID();

                int bkground = holder.cardView.getCardBackgroundColor().getDefaultColor();
                if(bkground == mContext.getResources().getColor(R.color.changed)){
                    Toast.makeText(mContext,"此優惠券已兌換！",Toast.LENGTH_SHORT).show();
                }else if(mydicount.getShopID().equals("")){
                    Intent intent = new Intent();
                    intent.setClass(mContext,couponQrcode.class);
                    ((Activity) mContext).startActivityForResult(intent,0);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolderDis extends  RecyclerView.ViewHolder{
        ImageView img;
        TextView discountTitle;
        TextView discountDescript;
        TextView discountDeadline;
        CardView cardView;

        public MyViewHolderDis(View itemView){
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.imgLogo);
            discountTitle = (TextView)itemView.findViewById(R.id.tvDiscountTItle);
            discountDescript = (TextView)itemView.findViewById(R.id.tvDiscountDescript);
            discountDeadline = (TextView)itemView.findViewById(R.id.tvDiscountDeadline);
            cardView = (CardView)itemView.findViewById(R.id.myDiscountView);

        }
    }
    public static String getCoupNo(){
        return coupNo;
    }
    public static String getCoupID(){
        return coupID;
    }
    public static int getPosition(){
        return p;
    }
}
