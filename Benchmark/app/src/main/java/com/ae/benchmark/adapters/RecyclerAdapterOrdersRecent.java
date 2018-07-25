package com.ae.benchmark.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;

import java.util.List;
import java.util.Random;

import butterknife.InjectView;

/*
* RecyclerView Adapter that allows to add a header view.
* */
public class RecyclerAdapterOrdersRecent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Item> mItemList;


    @InjectView(R.id.recyclerview_cust)
    RecyclerView recyclerview_cust;


    RecyclerAdapterCustomerMain recyclerAdapter;

    List<Customer> itemList;
    Customer customer;
    LinearLayoutManager mLayoutManager;

    public RecyclerAdapterOrdersRecent(List<Item> itemList, Context context) {
        this.mItemList = itemList;
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_recent_orders, parent, false);
        return new RecyclerItemViewHolderCustomer(view, mContext);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolderCustomer holder = (RecyclerItemViewHolderCustomer) viewHolder;

//        final Customer item = mItemList.get(position);

//        holder.txt_name.setText("Lorem Ipsum");
//        holder.txt_address.setText(item.address);
//        holder.txt_cust_id.setText(item.cust_id);
//
//        holder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, CustomerDetailOperationActivity.class));
////                mContext.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
//            }
//        });

//        binderHelper.bind(holder.swipeLayout, "");

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

    class RecyclerItemViewHolderCustomer extends RecyclerView.ViewHolder {

        public TextView txt_name;
        public ImageView img_profile;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolderCustomer(View parent, final Context context) {
            super(parent);

            txt_name = (TextView) parent.findViewById(R.id.txt_name);
            img_profile = (ImageView) parent.findViewById(R.id.img_profile);

        }

//        public void bind(final String data) {
//            deleteLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                mDataSet.remove(getAdapterPosition());
////                notifyItemRemoved(getAdapterPosition());
//                    Toast.makeText(mContext, "Clcidlkdf;lksa", Toast.LENGTH_SHORT).show();
//                }
//            });

//        textView.setText(data);

//        frontLayout.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick (View view){
////                mContext.startActivity(new Intent(mContext, CustomerDetailOperationActivity.class));
//            Toast.makeText(mContext, "Clcidlkdf;lksa", Toast.LENGTH_SHORT).show();
//        }
//        });
//    }
    }
}
