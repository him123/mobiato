package com.ae.benchmark.fragments;
/*
 * 
 * Copyright (C) 2014 Krishna Kumar Sharma
 * 
 *  */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.SelectedOrderItemActivity;
import com.ae.benchmark.model.Item;

import org.w3c.dom.Text;

public final class TestFragment extends Fragment {

    private TextView title;
    private RelativeLayout card;
    String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
            "iPhone", "WindowsMobile", "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
            "iPhone", "WindowsMobile", "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
            "iPhone", "WindowsMobile", "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
            "iPhone", "WindowsMobile"};
    int col[] = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN};

    public static TestFragment newInstance(Item item, Context c) {

        TestFragment fragment = new TestFragment();

        fragment.item = item;
        return fragment;
    }

    private String mContent = "???";
    Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup Rootview = (ViewGroup) inflater.inflate(R.layout.row_past_orders,
                container, false);
//        int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
//                (int) Math.floor(Math.random() * 128) + 64,
//                (int) Math.floor(Math.random() * 128) + 64);
        TextView txt_order_no = (TextView) Rootview.findViewById(R.id.txt_order_no);
        TextView txt_order_amt = (TextView) Rootview.findViewById(R.id.txt_order_amt);
//

        txt_order_amt.setText(item.item_price);
        txt_order_no.setText(item.order_id);

        Rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , SelectedOrderItemActivity.class);
                intent.putExtra("order_num",item.order_id);
                getActivity().startActivity(intent);
            }
        });

//        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, values){
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                TextView textView = (TextView) super.getView(position, convertView, parent);
//                textView.setTextColor(Color.WHITE);
//                return textView;
//            }
//
//        });
//        card = (RelativeLayout) Rootview.findViewById(R.id.card);
//        card.setBackgroundColor(bg);
//        title = (TextView) Rootview.findViewById(R.id.title);
//        title.setText("" + mContent);
//        title.setTextColor(Color.WHITE);
//        Button show = (Button) Rootview.findViewById(R.id.viewSwitcher);
//        listView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                Toast.makeText(getActivity(), values[arg2], Toast.LENGTH_SHORT)
//                        .show();
//
//            }
//        });
//        show.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
////                Intent i = new Intent(getActivity(), DetailViewActivity.class);
////                startActivity(i);
//
//            }
//        });
        return Rootview;
    }
}
