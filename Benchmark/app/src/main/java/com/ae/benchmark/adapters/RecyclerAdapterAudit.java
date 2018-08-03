package com.ae.benchmark.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.PreOrderRequestActivity;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.List;

public class RecyclerAdapterAudit extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Transaction> mItemList;
    Context mContext;
    String type;

    public RecyclerAdapterAudit(List<Transaction> itemList, Context context, String type) {
        this.mItemList = itemList;
        mContext = context;
        this.type = type;
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


        item.setTr_is_posted("No");
        switch (item.tr_type) {
            case Constant.TRANSACTION_TYPES.TT_STOCK_CAP:
                holder.txtType.setText("SC");
                break;
            case Constant.TRANSACTION_TYPES.TT_SALES_CREATED:
                holder.txtType.setText("SL");
                holder.txtInvoiceNo.setText("INV " + item.tr_invoice_id);
                break;
            case Constant.TRANSACTION_TYPES.TT_OREDER_CREATED:
                holder.txtType.setText("OR");
                holder.txtInvoiceNo.setText("ORD " + item.tr_invoice_id);
                break;
            case Constant.TRANSACTION_TYPES.TT_LOAD_CONF:
                holder.txtType.setText("LC");
                break;
            case Constant.TRANSACTION_TYPES.TT_LOAD_CREATE:
                holder.txtType.setText("LCR");
                break;
        }

        if (item.getTr_is_posted().equals("No")) {
            holder.imgIsPosted.setBackgroundResource(R.drawable.ic_action_sync);
        } else {
            holder.imgIsPosted.setBackgroundResource(R.drawable.ic_icon_verified_sel);
        }

        if (type.equalsIgnoreCase("")) {
            holder.imgIsPosted.setBackgroundResource(R.drawable.ico_magni_glass);
        }

        holder.imgPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog alertDialog = new Dialog(mContext);
                alertDialog.setCancelable(false);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.dialog_print_donot_print);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                ImageView img_print = alertDialog.findViewById(R.id.img_pring);
                img_print.setColorFilter(ContextCompat.getColor(mContext, R.color.theme_color), android.graphics.PorterDuff.Mode.MULTIPLY);


                alertDialog.findViewById(R.id.rl_print).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic

                        alertDialog.dismiss();
//                context.startActivity(redirectIntentirectIntent);
//                        mContext.finish();

                    }
                });

                alertDialog.findViewById(R.id.rl_donot_print).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        alertDialog.dismiss();
//                context.startActivity(redirectIntent);
//                        contextIntent.finish();

                    }
                });

                alertDialog.show();
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

        public TextView txtType, txtInvoiceNo;

        public ImageView imgIsPosted, imgPrint;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txtType = (TextView) parent.findViewById(R.id.txtType);
            txtInvoiceNo = (TextView) parent.findViewById(R.id.txtInvoiceNo);
            imgIsPosted = (ImageView) parent.findViewById(R.id.imgIsPosted);
            imgPrint = (ImageView) parent.findViewById(R.id.imgPrint);

        }
    }
}
