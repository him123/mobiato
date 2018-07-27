package com.ae.benchmark.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapterAudit;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Transaction;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FragmentAudit extends Fragment {

    @InjectView(R.id.recyclerAudit)
    RecyclerView recyclerAudit;
    ArrayList<Transaction> transactions = new ArrayList<>();
    DBManager db;
    Context context;
    RecyclerAdapterAudit adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_audit, container, false);
        ButterKnife.inject(this, v);
        context = getContext();

        db = new DBManager(context);
        transactions = db.getAllTransactions();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerAudit.setLayoutManager(mLayoutManager);

        adapter = new RecyclerAdapterAudit(transactions , context);
        recyclerAudit.setAdapter(adapter);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
