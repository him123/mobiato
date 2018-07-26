package com.ae.benchmark.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.model.Feed;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.RecycleModel;
import com.ae.benchmark.model.Transaction;

import java.util.List;
import java.util.Random;

import static com.ae.benchmark.R.layout.item;

/*
* RecyclerView Adapter that allows to add a header view.
* */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Feed> mItemList;
    private List<Transaction> mItemListTra;

    public FeedAdapter(List<Feed> itemList, Context context, List<Transaction> mItemListTra) {
        this.mItemList = itemList;
        mContext = context;
        this.mItemListTra = mItemListTra;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case RecycleModel.DATE:
                AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_milestone, parent, false);
                return new MileStoneRecyclerItemViewHolder(view, mContext);
            case RecycleModel.PROGRESS_VIEW:
                AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_progress, parent, false);
                return new RecyclerItemViewHolder(view, mContext);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {


        final Feed feed = mItemList.get(position);
//        final Transaction transaction = mItemListTra.get(position);

//        RecyclerItemViewHolderder holder = null;
        switch (feed.type) {
            case RecycleModel.DATE:
                ((MileStoneRecyclerItemViewHolder) viewHolder).txt_feed_milestone.setText(feed.date);
                break;
            case RecycleModel.PROGRESS_VIEW:
                ((RecyclerItemViewHolder) viewHolder).txt_activity.setText(feed.name);
                ((RecyclerItemViewHolder) viewHolder).txt_timestamp.setText(feed.desc);
                ((RecyclerItemViewHolder) viewHolder).txt_inv_no.setText(feed.inv_no);

                break;
            default:
                break;
        }

//        final Item item = mItemList.get(position);


//        holder.txt_desc.setText(item.desc);
//
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, CustomerDetailOperationActivity.class));
//            }
//        });

    }

    @Override
    public int getItemViewType(int position) {
        switch (mItemList.get(position).type) {
            case 1:
                return RecycleModel.DATE;
            case 2:
                return RecycleModel.PROGRESS_VIEW;
            default:
                return RecycleModel.PROGRESS_VIEW;
        }
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

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_activity, txt_timestamp, txt_inv_no;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_activity = (TextView) parent.findViewById(R.id.txt_activity);
            txt_timestamp = (TextView) parent.findViewById(R.id.txt_timestamp);
            txt_inv_no = (TextView) parent.findViewById(R.id.txt_inv_no);

        }
    }

    public class MileStoneRecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_feed_milestone;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public MileStoneRecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txt_feed_milestone = (TextView) parent.findViewById(R.id.txt_feed_milestone);

        }
    }
}
