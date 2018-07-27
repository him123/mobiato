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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.EndInventoryRITActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * RecyclerView Adapter that allows to add a header view.
 * */
public class RecyclerItemsUnloadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Item> mItemList;
    DBManager db;
    public static final String BROADCAST_ACTION = "com.benchmark.DIALOG";
    public static final String BROADCAST_ACTION_CHK = "com.benchmark.CHK";
    Intent intent, intete2;

    public RecyclerItemsUnloadAdapter(List<Item> itemList, Context context) {
        this.mItemList = itemList;
        mContext = context;
        db = new DBManager(context);
        intent = new Intent(BROADCAST_ACTION);
        intete2 = new Intent(BROADCAST_ACTION_CHK);
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

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDilog(mContext, item);
//                mContext.startActivity(new Intent(mContext, CustomerDetailOperationActivity.class));
            }
        });

        holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                intete2.putExtra("item_code", item.item_code);
                intete2.putExtra("item_qty", item.item_qty);
                intete2.putExtra("isChecked", isChecked);

                mContext.sendBroadcast(intete2);
                if (!isChecked){
                    Constant.checkBoxFalse = "yes";
                    EndInventoryRITActivity.checkbox.setChecked(false);
                    Constant.checkBoxFalse = "no";
                }

        }
        });

        if (Constant.checkBoxValue.equals("true")){
            holder.chk.setChecked(true);

            intete2.putExtra("item_code", item.item_code);
            intete2.putExtra("item_qty", item.item_qty);
            intete2.putExtra("isChecked", true);

            mContext.sendBroadcast(intete2);
        } else if (Constant.checkBoxValue.equals("false")){
            holder.chk.setChecked(false);

            intete2.putExtra("item_code", item.item_code);
            intete2.putExtra("item_qty", item.item_qty);
            intete2.putExtra("isChecked", false);

            mContext.sendBroadcast(intete2);
        }
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

        txt_name.setText(item.item_name_en);
        edt_qty.setText(item.item_qty);

        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.setView(deleteDialogView);

        deleteDialogView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic

                if (!item.item_qty.equals(edt_qty.getText().toString())) {
//                    Toast.makeText(context, "Update load this load item", Toast.LENGTH_SHORT).show();

                    db.open();
//                    db.updateLoadItemQty(item.load_no, item.item_code, edt_qty.getText().toString());
                    item.item_qty = edt_qty.getText().toString();
                    intent.putExtra("item", item);
                    context.sendBroadcast(intent);
                }

                deleteDialog.dismiss();
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

    public static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public TextView
                txt_name,
                txt_qty,
                txt_price,
                txt_uom;

        public CheckBox chk;

        public ImageView imgVerified;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_name = (TextView) parent.findViewById(R.id.txt_name);
            txt_qty = (TextView) parent.findViewById(R.id.txt_qty);
            txt_price = (TextView) parent.findViewById(R.id.txt_price);
            txt_uom = (TextView) parent.findViewById(R.id.txt_uom);
            imgVerified = (ImageView) parent.findViewById(R.id.imgVerified);
            chk = (CheckBox) parent.findViewById(R.id.chk);
            chk.setVisibility(View.VISIBLE);

        }
    }

    public static void checkBoxChange(){

    }
}
