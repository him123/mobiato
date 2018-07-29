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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.PreOrderRequestActivity;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;

import java.util.List;

public class RecyclerAdapterAudit extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Transaction> mItemList;
    Context mContext;

    public RecyclerAdapterAudit(List<Transaction> itemList, Context context) {
        this.mItemList = itemList;
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_audit, parent, false);
        return new RecyclerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        final Transaction item = mItemList.get(position);


        if (item.tr_type.equals(Constant.TRANSACTION_TYPES.TT_STOCK_CAP)){
            holder.txtType.setText("Stock Captured");
            holder.llInvoice.setVisibility(View.GONE);
            holder.txtIsPosted.setText(item.tr_is_posted);
        } else if (item.tr_type.equals(Constant.TRANSACTION_TYPES.TT_SALES_CREATED)){
            holder.txtType.setText("SALE");
            holder.llInvoice.setVisibility(View.VISIBLE);
            holder.txtInvoiceNo.setText("INV " + item.tr_invoice_id);
            holder.txtIsPosted.setText(item.tr_is_posted);
        } else if (item.tr_type.equals(Constant.TRANSACTION_TYPES.TT_OREDER_CREATED)){
            holder.txtType.setText("ORDER");
            holder.llInvoice.setVisibility(View.VISIBLE);
            holder.txtInvoice.setText("Order No.:");
            holder.txtInvoiceNo.setText("ORD " + item.tr_invoice_id);
            holder.txtIsPosted.setText(item.tr_is_posted);
        }  else if (item.tr_type.equals(Constant.TRANSACTION_TYPES.TT_LOAD_CONF)){
            holder.txtType.setText("LOAD CONFIRMED");
            holder.llInvoice.setVisibility(View.GONE);
            holder.txtIsPosted.setText(item.tr_is_posted);
        }  else if (item.tr_type.equals(Constant.TRANSACTION_TYPES.TT_LOAD_CREATE)){
            holder.txtType.setText("LOAD CREATED");
            holder.llInvoice.setVisibility(View.GONE);
            holder.txtIsPosted.setText(item.tr_is_posted);
        }

        holder.txtIsPosted.setText("No");

    }

    @Override
    public int getItemCount() {
        return mItemList.size(); // header
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txtType , txtInvoiceNo , txtIsPosted;
        public LinearLayout llInvoice;
        public TextView txtInvoice;

        public ImageView imgVerified;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txtType = (TextView) parent.findViewById(R.id.txtType);
            txtInvoiceNo = (TextView) parent.findViewById(R.id.txtInvoiceNo);
            txtIsPosted = (TextView) parent.findViewById(R.id.txtIsPosted);
            llInvoice = (LinearLayout) parent.findViewById(R.id.llInvoice);
            txtInvoice = (TextView) parent.findViewById(R.id.txtInvoice);

        }
    }
}
