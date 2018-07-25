package com.ae.benchmark.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.CustomerDetailOperationActivity;
import com.ae.benchmark.model.Customer;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
* RecyclerView Adapter that allows to add a header view.
* */
public class RecyclerAdapterCustomerRecent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private static final Random RANDOM = new Random();
    private List<Customer> mItemList;
    private static final String[] desuNoto = {
            "Alane Avey", "Belen Brewster", "Brandon Brochu", "Carli Carrol", "Della Delrio",
            "Esther Echavarria", "Etha Edinger", "Felipe Flecha", "Ilse Island", "Kecia Keltz",
            "Lourie Lucas", "Lucille Leachman", "Mandi Mcqueeney", "Murray Matchett", "Nadia Nero",
            "Nannie Nipp", "Ozella Otis", "Pauletta Poehler", "Roderick Rippy", "Sherril Sager",
            "Taneka Tenorio", "Treena Trentham", "Ulrike Uhlman", "Virgina Viau", " Willis Wysocki "
    };
    private int[] mMaterialColors;

    public RecyclerAdapterCustomerRecent(List<Customer> itemList, Context context) {
        this.mItemList = itemList;
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_recent_cust, parent, false);
        return new RecyclerItemViewHolderCustomer(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolderCustomer holder = (RecyclerItemViewHolderCustomer) viewHolder;

        final Customer item = mItemList.get(position);

        holder.txt_name.setText("CUST 1");
//        holder.txt_address.setText(item.address);
//        holder.txt_cust_id.setText(item.cust_id);
//
        holder.ll_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CustomerDetailOperationActivity.class));
//                mContext.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

//        binderHelper.bind(holder.swipeLayout, "");

        holder.icon.setLetter(desuNoto[position]);

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
        MaterialLetterIcon icon;
        LinearLayout ll_mail;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolderCustomer(View parent, final Context context) {
            super(parent);

            txt_name = (TextView) parent.findViewById(R.id.txt_name);
            icon = (MaterialLetterIcon) parent.findViewById(R.id.icon);
            ll_mail = (LinearLayout) parent.findViewById(R.id.ll_mail);

            icon.setInitials(true);
            icon.setInitialsNumber(2);
            icon.setLetterSize(18);

            mMaterialColors = context.getResources().getIntArray(R.array.colors);
            icon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);


            Typeface mFontMedium = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Quicksand-Medium.ttf");
            Typeface mFontRegular = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Quicksand-Regular.ttf");
            Typeface mFontBold = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Quicksand-Bold.ttf");
            Typeface mFontLight = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Quicksand-Light.ttf");

            txt_name.setTypeface(mFontMedium);
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
