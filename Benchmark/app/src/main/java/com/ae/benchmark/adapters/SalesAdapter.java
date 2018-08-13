package com.ae.benchmark.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.InputDailogActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;

import java.util.List;
import java.util.Random;

/*
 * RecyclerView Adapter that allows to add a header view.
 * */
public class SalesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Item> mItemList;
    String isCoupon = "", isNewCust = "";
    DBManager db;
    Customer customer;

    public SalesAdapter(List<Item> itemList, Context context, String isCoupon, String isNewCust, Customer customer) {
        this.mItemList = itemList;
        mContext = context;
        this.isCoupon = isCoupon;
        this.isNewCust = isNewCust;
        db = new DBManager(context);
        db.open();
        this.customer = customer;
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
        String price = db.getBottlePrice(item.item_code);
        holder.txt_qty.setText("Qty: " + item.item_qty + " | " + "Price: " + price);
        holder.txt_item_code.setText(item.item_code);


        final boolean flag;
        if (item.item_type.equalsIgnoreCase("Coupon")) {
            holder.txt_uom.setText("Pcs");
            flag = true;
            holder.rl_tag.setBackgroundResource(R.drawable.ic_bg_new_green);
        } else if (item.item_type.equalsIgnoreCase("Empty")) {
            holder.txt_uom.setText("Bottles");
            flag = false;
            holder.rl_tag.setBackgroundResource(R.drawable.ic_bg_new);
        } else {
            holder.txt_uom.setText("Pcs");
            flag = false;
            holder.rl_tag.setBackgroundResource(R.drawable.ic_bg_new_green);
        }

        holder.txt_uom.setText(item.item_uom);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, InputDailogActivity.class);

                i.putExtra("isScan", flag);
                i.putExtra("isCoupon", isCoupon);
                i.putExtra("item", item);
                i.putExtra("tag", isNewCust);
                i.putExtra("item_type", item.item_type);
                i.putExtra("cust_num", customer.cust_num);
                i.putExtra("cust_name", customer.cust_name_en);

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
                txt_uom,
                txt_item_code, txt_tag, txt_qty;

        public RelativeLayout rl_tag;
        public ImageView imgVerified;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_name = (TextView) parent.findViewById(R.id.txt_name);
            txt_desc = (TextView) parent.findViewById(R.id.txt_desc);
            txt_price = (TextView) parent.findViewById(R.id.txt_price);
            txt_uom = (TextView) parent.findViewById(R.id.txt_uom);
            imgVerified = (ImageView) parent.findViewById(R.id.imgVerified);
            txt_item_code = (TextView) parent.findViewById(R.id.txt_item_code);
            txt_tag = (TextView) parent.findViewById(R.id.txt_tag);
            rl_tag = (RelativeLayout) parent.findViewById(R.id.rl_tag);
            txt_qty = (TextView) parent.findViewById(R.id.txt_qty);
        }
    }
}
