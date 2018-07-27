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
import com.ae.benchmark.activities.PreOrderRequestActivity;
import com.ae.benchmark.model.Transaction;

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

        holder.txtType.setText(item.tr_type);
        holder.txtInvoiceNo.setText(item.tr_invoice_id);
        holder.txtIsPosted.setText(item.tr_is_posted);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PreOrderRequestActivity.class));
            }
        });

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

        public ImageView imgVerified;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txtType = (TextView) parent.findViewById(R.id.txtType);
            txtInvoiceNo = (TextView) parent.findViewById(R.id.txtInvoiceNo);
            txtIsPosted = (TextView) parent.findViewById(R.id.txtIsPosted);

        }
    }
}
