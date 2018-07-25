package com.ae.benchmark.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapterCustomer;
import com.ae.benchmark.adapters.RecyclerAdapterCustomerMain;
import com.ae.benchmark.adapters.RecyclerAdapterCustomerRecent;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentCustSeq extends Fragment {

    @InjectView(R.id.recyclerview_cust)
    RecyclerView recyclerview_cust;


    RecyclerAdapterCustomerMain recyclerAdapter;

    List<Customer> itemList;
    Customer customer;
    LinearLayoutManager mLayoutManager;
    @InjectView(R.id.pg)
    ProgressBar pg;
    DBManager dbManager;

    public FragmentCustSeq() {
        // Required empty public constructor
    }

    public static FragmentCustSeq createInstance() {
        FragmentCustSeq partThreeFragment = new FragmentCustSeq();
        return partThreeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_select_cust_list, container, false);
        ButterKnife.inject(this, v);

        LayoutInflater mInflater = LayoutInflater.from(getActivity());
//
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
//        mTitleTextView.setText(Util.capitalFirstLatter(user.first_name) + " " + Util.capitalFirstLatter(user.last_name));

        new AsyncTaskRunner().execute();

        return v;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
//        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()

            try {
                itemList = new ArrayList<>();

                dbManager = new DBManager(getActivity());
                dbManager.open();
                itemList = dbManager.getAllCust();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
//            progressDialog.dismiss();

//            finalResult.setText(result);
            mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerview_cust.setLayoutManager(mLayoutManager);
            recyclerAdapter = new RecyclerAdapterCustomerMain(itemList, getActivity());
            recyclerview_cust.setAdapter(recyclerAdapter);

            pg.setVisibility(View.GONE);
        }


        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(getActivity(),
//                    "ProgressDialog",
//                    "Preparing Journey Plan");

            pg.setVisibility(View.VISIBLE);
        }


        @Override
        protected void onProgressUpdate(String... text) {
//            finalResult.setText(text[0]);

        }
    }

}