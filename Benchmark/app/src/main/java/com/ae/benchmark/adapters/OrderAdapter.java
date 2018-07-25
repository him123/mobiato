package com.ae.benchmark.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.InputDailogActivity;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

/*
* RecyclerView Adapter that allows to add a header view.
* */
public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Item> mItemList;
    String isCoupon = "", isNewCust = "";


    public OrderAdapter(List<Item> itemList, Context context, String isCoupon, String isNewCust) {
        this.mItemList = itemList;
        mContext = context;
        this.isCoupon = isCoupon;
        this.isNewCust = isNewCust;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_order_list, parent, false);
        return new RecyclerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        final Item item = mItemList.get(position);

        holder.txt_name.setText(item.item_name_en);
        holder.txt_item_code.setText(item.item_code);
//        holder.txt_desc.setText(item.desc);

        final boolean flag;
        final String var;
        if (item.item_name_en.contains("Coupon")) {
            holder.txt_cash.setText("Pcs");
            flag = true;
            holder.txt_tag.setText("SALE");
            holder.rl_tag.setBackgroundResource(R.drawable.ic_bg_new_green);
            var = "coupon";
        } else if (item.item_name_en.contains("Empty")) {
            holder.txt_cash.setText("Bottles");
            flag = false;
            holder.txt_tag.setText("SALE");
            holder.rl_tag.setBackgroundResource(R.drawable.ic_bg_new);
            var = "empty";
        } else {
            holder.txt_cash.setText("Bottles");
            flag = false;
            holder.rl_tag.setBackgroundResource(R.drawable.ic_bg_new_green);
            holder.txt_tag.setText("SALE");
            var = "bottle";
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, InputDailogActivity.class);

                i.putExtra("isScan", isCoupon);
                i.putExtra("isCoupon", flag);
                i.putExtra("item", item);
                i.putExtra("tag", isNewCust);
                i.putExtra("var", var);

                mContext.startActivity(i);
//                makeDilog(mContext);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mItemList.size(); // header
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    interface OnLoadMoreListener {
        void onLoadMore();
    }


    class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public TextView
                txt_name,
                txt_desc,
                txt_sub_dept,
                txt_price,
                txt_cash,
                txt_item_code, txt_tag;

        public RelativeLayout rl_tag;
        public ImageView imgVerified;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_name = (TextView) parent.findViewById(R.id.txt_name);
            txt_desc = (TextView) parent.findViewById(R.id.txt_desc);
            txt_price = (TextView) parent.findViewById(R.id.txt_price);
            txt_cash = (TextView) parent.findViewById(R.id.txt_cash);
            imgVerified = (ImageView) parent.findViewById(R.id.imgVerified);
            txt_item_code = (TextView) parent.findViewById(R.id.txt_item_code);
            txt_tag = (TextView) parent.findViewById(R.id.txt_tag);
            rl_tag = (RelativeLayout) parent.findViewById(R.id.rl_tag);
        }
    }
}
