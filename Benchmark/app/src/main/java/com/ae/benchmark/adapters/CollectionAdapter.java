package com.ae.benchmark.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.CustomerDetailOperationActivity;
import com.ae.benchmark.activities.PaymentActivity;
import com.ae.benchmark.model.Collection;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;

import java.util.List;
import java.util.Random;

/*
 * RecyclerView Adapter that allows to add a header view.
 * */
public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Collection> mItemList;
    Customer customer;

    public CollectionAdapter(List<Collection> itemList, Context context, Customer customer) {
        this.mItemList = itemList;
        mContext = context;
        this.customer = customer;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_collection, parent, false);
        return new RecyclerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        final Collection collection = mItemList.get(position);

        holder.txt_inv_no.setText(collection.coll_inv_no);
        holder.edt_col_amout.setText(collection.coll_amount);
        holder.txt_due_date.setText(collection.coll_due_date);
        holder.txt_due_amt.setText(collection.coll_due_amt);
        holder.txt_inv_date.setText(collection.coll_inv_date);


        holder.txt_due_amt.setEnabled(false);

        holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collection
                        .coll_cust_pay_method.equals("credit")) {

                    Intent i = new Intent(mContext, PaymentActivity.class);
                    i.putExtra("amt", collection.coll_amount);
                    i.putExtra("cust", customer);
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
                txt_inv_no,
                txt_inv_date,
                txt_due_date,
                txt_due_amt,
                edt_col_amout;

        public RelativeLayout rl_main;


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_inv_no = (TextView) parent.findViewById(R.id.txt_inv_no);
            txt_inv_date = (TextView) parent.findViewById(R.id.txt_inv_date);
            txt_due_date = (TextView) parent.findViewById(R.id.txt_due_date);
            txt_due_amt = (TextView) parent.findViewById(R.id.txt_due_amt);

            edt_col_amout = (EditText) parent.findViewById(R.id.edt_col_amout);
            rl_main = (RelativeLayout) parent.findViewById(R.id.rl_main);

        }
    }
}
