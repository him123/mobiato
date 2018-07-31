package com.ae.benchmark.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;

import java.util.List;

public class SalesHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Transaction> mItemList;
    Context mContext;
    String type;

    public SalesHistoryAdapter(List<Transaction> itemList, Context context , String type) {
        this.mItemList = itemList;
        mContext = context;
        this.type = type;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_sales_history, parent, false);
        return new RecyclerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        final Transaction item = mItemList.get(position);

        holder.txtCustNo.setText(item.tr_customer_num);
        holder.txtCustName.setText(item.tr_customer_name);
        holder.txtInvNo.setText(item.tr_invoice_id);
        holder.txtDate.setText(item.tr_date_time);

    }

    @Override
    public int getItemCount() {
        return mItemList.size(); // header
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCustNo , txtCustName , txtInvNo , txtDate ;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txtCustNo = (TextView) parent.findViewById(R.id.txtCustNo);
            txtCustName = (TextView) parent.findViewById(R.id.txtCustName);
            txtInvNo = (TextView) parent.findViewById(R.id.txtInvNo);
            txtDate = (TextView) parent.findViewById(R.id.txtDate);

        }
    }
}