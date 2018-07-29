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
import com.ae.benchmark.activities.CustomerDetailOperationActivity;
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.activities.SelectCustomerListMainActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Load;

import java.util.List;
import java.util.Random;

/*
 * RecyclerView Adapter that allows to add a header view.
 * */
public class LoadHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Load> mItemList;
    DBManager dbManager;
    boolean isReq;

    public LoadHeaderAdapter(List<Load> itemList, Context context, boolean isReq) {
        this.mItemList = itemList;
        mContext = context;
        dbManager = new DBManager(context);
        this.isReq = isReq;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_load_header_list, parent, false);
        return new RecyclerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        final Load load = mItemList.get(position);

        if (load.is_verified.equals("0")) {
            holder.imgVerified.setImageResource(R.drawable.ic_icon_block_sel);
        } else {
            holder.imgVerified.setImageResource(R.drawable.ic_icon_verified_sel);
        }

        holder.txt_load_no.setText(load.load_no);
//        holder.txt_desc.setText(item.desc);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.open();
                if (!isReq)
                    if (dbManager.checkIsNotVerified()) {
                        Intent i = new Intent(mContext, ItemsListActivity.class);
                        i.putExtra("load_no", load.load_no);
                        i.putExtra("isBack", "No");
                        mContext.startActivity(i);
                    } else {
                        Intent i = new Intent(mContext, SelectCustomerListMainActivity.class);
//                    i.putExtra("load_no", load.load_no);
//                    i.putExtra("isBack", "No");
                        mContext.startActivity(i);
                    }
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
                txt_load_no,
                txt_desc,
                txt_sub_dept,
                txt_price,
                txt_up;

        public ImageView imgVerified;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_load_no = (TextView) parent.findViewById(R.id.txt_load_no);
            txt_desc = (TextView) parent.findViewById(R.id.txt_desc);
            txt_price = (TextView) parent.findViewById(R.id.txt_price);
            txt_up = (TextView) parent.findViewById(R.id.txt_up);
            imgVerified = (ImageView) parent.findViewById(R.id.imgVerified);

        }
    }
}
