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
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.activities.VerifyLoadActivity;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Transaction;

import java.util.List;
import java.util.Random;

/*
* RecyclerView Adapter that allows to add a header view.
* */
public class RecyclerAdapterLoadUnloadHistory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Transaction> mItemList;

    public RecyclerAdapterLoadUnloadHistory(List<Transaction> itemList, Context context) {
        this.mItemList = itemList;
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_load_list, parent, false);
        return new RecyclerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        final Transaction item = mItemList.get(position);

        holder.txt_name.setText(item.tr_invoice_id);
        holder.txt_desc.setText(item.tr_date_time);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, VerifyLoadActivity.class));
                Intent intent = new Intent(mContext, ItemsListActivity.class);
                intent.putExtra("load_no",item.tr_invoice_id);
                mContext.startActivity(intent);
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
                txt_cash;

        public ImageView imgVerified;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_name = (TextView) parent.findViewById(R.id.txt_name);
            txt_desc = (TextView) parent.findViewById(R.id.txt_desc);
            txt_price = (TextView) parent.findViewById(R.id.txt_price);
//            txt_cash = (TextView) parent.findViewById(R.id.txt_cash);
            imgVerified = (ImageView) parent.findViewById(R.id.imgVerified);

        }
    }
}
