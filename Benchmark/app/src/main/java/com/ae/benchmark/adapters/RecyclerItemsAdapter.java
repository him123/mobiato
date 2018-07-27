package com.ae.benchmark.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.OrderReqeustActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * RecyclerView Adapter that allows to add a header view.
 * */
public class RecyclerItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Item> mItemList;
    DBManager db;
    public static final String BROADCAST_ACTION = "com.benchmark.DIALOG";
    public static final String BROADCAST_ACTION_CHK = "com.benchmark.DIALOG";
    Intent intent;
    boolean isViewOnly;

    public RecyclerItemsAdapter(List<Item> itemList, Context context, boolean isViewOny) {
        this.mItemList = itemList;
        mContext = context;
        db = new DBManager(context);
        intent = new Intent(BROADCAST_ACTION);
        this.isViewOnly = isViewOny;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_item_list, parent, false);
        return new RecyclerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        final Item item = mItemList.get(position);

        holder.txt_uom.setText(item.item_uom);
        holder.txt_name.setText(item.item_name_en);
        holder.txt_price.setText(item.item_price + " SAR");
        holder.txt_qty.setText(item.item_qty);

        if (!isViewOnly)
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeDilog(mContext, item);
//                mContext.startActivity(new Intent(mContext, CustomerDetailOperationActivity.class));
                }
            });

    }

    private void makeDilog(final Context context, final Item item) {
        List<String> list = new ArrayList<String>();

        list.add("list 1");
        list.add("list 2");
        list.add("list 3");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        LayoutInflater dialog = LayoutInflater.from(context);
        final View deleteDialogView = dialog.inflate(R.layout.dialog_input_qty_item, null);
        Spinner spinner2 = (Spinner) deleteDialogView.findViewById(R.id.sp_reason);
        spinner2.setAdapter(dataAdapter);
        TextView txt_name = (TextView) deleteDialogView.findViewById(R.id.txt_name);
        final EditText edt_qty = (EditText) deleteDialogView.findViewById(R.id.edt_qty);
        final EditText edt_act_qty = (EditText) deleteDialogView.findViewById(R.id.edt_act_qty);

        txt_name.setText(item.item_name_en);
        edt_qty.setText("0");
        edt_act_qty.setText(item.item_qty);

        edt_act_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int edtAct = Integer.parseInt(edt_act_qty.getText().toString());
                    int total = Integer.parseInt(item.item_qty);

                    if (edtAct > total) {
                        edt_act_qty.removeTextChangedListener(this);
                        edt_act_qty.setText(item.item_qty);
                        edt_act_qty.addTextChangedListener(this);
                        edt_qty.setText("0");
                    } else {
                        edt_qty.setText(String.valueOf(total - edtAct));
                    }
                } catch (Exception e) {
                    edt_act_qty.removeTextChangedListener(this);
                    edt_act_qty.setText("");
                    edt_act_qty.addTextChangedListener(this);
                    edt_qty.setText(item.item_qty);
                }
            }
        });
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.setView(deleteDialogView);

        deleteDialogView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic

                if (edt_act_qty.getText().toString().equals("")) {
                    edt_act_qty.setError("Please enter actual qty");
                    edt_act_qty.requestFocus();
                } else {
                    if (!item.item_qty.equals(edt_qty.getText().toString())) {
                        Toast.makeText(context, "Update load this load item", Toast.LENGTH_SHORT).show();

                        db.open();
                        db.updateLoadItemQty(item.load_no, item.item_code, edt_act_qty.getText().toString());
//                    db.updateUnLoadItemQty(item.load_no, item.item_code, edt_qty.getText().toString());

                        intent.putExtra("load_no", item.load_no);
                        context.sendBroadcast(intent);

                    }

                    deleteDialog.dismiss();
                }

            }
        });


        deleteDialog.show();
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
                txt_qty,
                txt_price,
                txt_uom;

        public ImageView imgVerified;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_name = (TextView) parent.findViewById(R.id.txt_name);
            txt_qty = (TextView) parent.findViewById(R.id.txt_qty);
            txt_price = (TextView) parent.findViewById(R.id.txt_price);
            txt_uom = (TextView) parent.findViewById(R.id.txt_uom);
            imgVerified = (ImageView) parent.findViewById(R.id.imgVerified);

        }
    }
}
