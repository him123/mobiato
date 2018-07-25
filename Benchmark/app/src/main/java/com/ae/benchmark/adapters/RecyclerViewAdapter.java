package com.ae.benchmark.adapters;

/**
 * Created by Himm on 3/9/2018.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ae.benchmark.R;

import java.util.List;

/**
 * Created by Juned on 3/27/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

    private List<Integer> list;
    private Context context;

    public class MyView extends RecyclerView.ViewHolder {

        public View v_bar;

        public MyView(View view) {
            super(view);

            v_bar = (View) view.findViewById(R.id.v_bar);
        }
    }


    public RecyclerViewAdapter(Context context, List<Integer> horizontalList) {
        this.list = horizontalList;
        this.context = context;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bar_chart, parent, false);

        return new MyView(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        ViewGroup.LayoutParams params = holder.v_bar.getLayoutParams();
        params.height = this.list.get(position);
        holder.v_bar.setLayoutParams(params);
        if (this.list.get(position) < 50) {
            holder.v_bar.setBackground(context.getDrawable(R.drawable.rounded_corner_low));
        } else if (this.list.get(position) > 200) {
            holder.v_bar.setBackground(context.getDrawable(R.drawable.rounded_corner));
        }
        Log.v("", "========= Chk position: " + position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}