package com.ae.benchmark.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.CustomerDetailOperationActivity;
import com.ae.benchmark.activities.FragmentContainActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/*
 * RecyclerView Adapter that allows to add a header view.
 * */
public class RecyclerAdapterCustomerMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    Random rand = new Random();
    private List<Customer> mItemList;
    private static final Random RANDOM = new Random();
    private int[] mMaterialColors;
//    MaterialShowcaseSequence sequence;
    DBManager db;

    public RecyclerAdapterCustomerMain(List<Customer> itemList, Context context) {
        this.mItemList = itemList;
        mContext = context;

        db = new DBManager(context);
//        sequence = new MaterialShowcaseSequence((Activity) mContext, "Cust");
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
//        sequence.setConfig(config);
    }

    private static final String[] desuNoto = {
            "Alane Avey", "Belen Brewster", "Brandon Brochu", "Carli Carrol", "Della Delrio",
            "Esther Echavarria", "Etha Edinger", "Felipe Flecha", "Ilse Island", "Kecia Keltz",
            "Lourie Lucas", "Lucille Leachman", "Mandi Mcqueeney", "Murray Matchett", "Nadia Nero",
            "Nannie Nipp", "Ozella Otis", "Pauletta Poehler", "Roderick Rippy", "Sherril Sager",
            "Taneka Tenorio", "Treena Trentham", "Ulrike Uhlman", "Virgina Viau", " Willis Wysocki "
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_customer_main, parent, false);
        return new RecyclerItemViewHolderCustomer(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolderCustomer holder = (RecyclerItemViewHolderCustomer) viewHolder;

        final Customer customer = mItemList.get(position);

        holder.txt_name.setText(customer.cust_name_en);
        holder.txt_tel.setText("tel: " + customer.cust_address);
        holder.txt_cust_id.setText(customer.cust_num);

        if (position == 2 || position == 5) {
            holder.iv_color.setImageResource(R.drawable.ic_mark_yellow);
            holder.rl_new.setVisibility(View.VISIBLE);
        } else {
            holder.rl_new.setVisibility(View.GONE);
        }

        if (customer.cust_type.equals("cash")) {
            holder.iv_color.setImageResource(R.drawable.ic_mark_blue);
        } else if (customer.cust_type.equals("credit")) {
            holder.iv_color.setImageResource(R.drawable.ic_mark_red);
        } else
            holder.iv_color.setImageResource(R.drawable.ic_mark_yellow);

        holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDilog(mContext, position, customer);
            }
        });

//        if (position == 1) {
//            sequence.addSequenceItem(holder.rl_main,
//                    "Swipe left for on any Customer.", "GOT IT");
//            sequence.start();
//        }


        if (customer.cust_sale.equals("1")) {
            holder.img_small_sales.setVisibility(View.VISIBLE);
            holder.img_sales.setVisibility(View.VISIBLE);
        } else {
            holder.img_small_sales.setVisibility(View.INVISIBLE);
            holder.img_sales.setVisibility(View.INVISIBLE);
        }

        if (customer.cust_order.equals("1")) {
            holder.img_small_order.setVisibility(View.VISIBLE);
            holder.img_order.setVisibility(View.VISIBLE);
        } else {
            holder.img_small_order.setVisibility(View.INVISIBLE);
            holder.img_order.setVisibility(View.INVISIBLE);
        }

        if (customer.cust_collection.equals("1")) {
            holder.img_small_collection.setVisibility(View.VISIBLE);
            holder.img_collection.setVisibility(View.VISIBLE);
        } else {
            holder.img_small_collection.setVisibility(View.INVISIBLE);
            holder.img_collection.setVisibility(View.INVISIBLE);
        }


        holder.img_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, FragmentContainActivity.class);
                i.putExtra("flag", "ORD");
                mContext.startActivity(i);
            }
        });

        holder.img_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, FragmentContainActivity.class);
                i.putExtra("flag", "SAL");
                mContext.startActivity(i);
            }
        });

        holder.img_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, FragmentContainActivity.class);
                i.putExtra("flag", "COL");
                mContext.startActivity(i);
            }
        });


        holder.img_merchandize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, FragmentContainActivity.class);
                i.putExtra("flag", "MER");
                mContext.startActivity(i);
            }
        });


        holder.img_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, FragmentContainActivity.class);
                i.putExtra("flag", "DEL");
                mContext.startActivity(i);
            }
        });

        holder.icon.setLetter(desuNoto[position]);

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

    public class RecyclerItemViewHolderCustomer extends RecyclerView.ViewHolder {

        public TextView txt_name, txt_tel, txt_cust_id;
        public RelativeLayout rl_main;
        ImageView iv_color;
        public RelativeLayout rl_new, rl_back;
        MaterialLetterIcon icon;
        ImageView img_order, img_sales, img_collection, img_merchandize, img_delivery,
                img_small_order, img_small_sales, img_small_collection;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolderCustomer(View parent, final Context context) {
            super(parent);

            txt_name = (TextView) parent.findViewById(R.id.txt_name);
            txt_tel = (TextView) parent.findViewById(R.id.txt_tel);
            txt_cust_id = (TextView) parent.findViewById(R.id.txt_id);
            rl_main = (RelativeLayout) parent.findViewById(R.id.rl_main);
            rl_back = (RelativeLayout) parent.findViewById(R.id.rl_back);
            iv_color = (ImageView) parent.findViewById(R.id.img_color);
            rl_new = (RelativeLayout) parent.findViewById(R.id.rl_new);
            icon = (MaterialLetterIcon) parent.findViewById(R.id.imageView2);

            img_order = (ImageView) parent.findViewById(R.id.img_order);
            img_sales = (ImageView) parent.findViewById(R.id.img_sales);
            img_collection = (ImageView) parent.findViewById(R.id.img_collection);
            img_merchandize = (ImageView) parent.findViewById(R.id.img_merchandize);
            img_delivery = (ImageView) parent.findViewById(R.id.img_delivery);

            img_small_order = (ImageView) parent.findViewById(R.id.img_small_order);
            img_small_sales = (ImageView) parent.findViewById(R.id.img_small_sales);
            img_small_collection = (ImageView) parent.findViewById(R.id.img_small_collection);


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
            txt_tel.setTypeface(mFontRegular);
            txt_cust_id.setTypeface(mFontBold);


        }
    }

    private void makeDilog(Context context, final int position, final Customer customer) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.dialog_shop_open_close, null);
//        Spinner spinner2 = (Spinner) deleteDialogView.findViewById(R.id.sp_reason);
//        spinner2.setAdapter(dataAdapter);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialogView.findViewById(R.id.rl_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();

                Intent i = new Intent(mContext, CustomerDetailOperationActivity.class);
                if (position == 2 || position == 5) {
                    i.putExtra("tag", "new");
                    i.putExtra("cust", customer);
                } else {
                    i.putExtra("tag", "old");
                    i.putExtra("cust", customer);
                }
                mContext.startActivity(i);

            }
        });

        deleteDialogView.findViewById(R.id.rl_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
            }
        });


        deleteDialog.show();
    }
}
