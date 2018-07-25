package com.ae.benchmark.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.CustomerDetailOperationActivity;
import com.ae.benchmark.activities.FragmentContainActivity;

import butterknife.ButterKnife;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentCOMerchandising extends Fragment {


    private com.github.clans.fab.FloatingActionMenu fam;
    private com.github.clans.fab.FloatingActionButton fab1, fab2, fab3, fab4, fab5, fab6, fab7, fab8;

    public FragmentCOMerchandising() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_co_merchant, container, false);
        ButterKnife.inject(this, v);

        fab1 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab1);
        fab2 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab2);
        fab3 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab3);
        fab4 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab4);
        fab5 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab5);
        fab6 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab6);
        fab7 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab7);
        fab8 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab8);

        fam = (com.github.clans.fab.FloatingActionMenu) v.findViewById(R.id.fab_menu);

        //handling menu status (open or close)
        fam.setOnMenuToggleListener(new com.github.clans.fab.FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
//                if (opened) {
//                    showToast("Menu is opened");
//                } else {
//                    showToast("Menu is closed");
//                }
            }
        });

        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FragmentContainActivity.class);
                i.putExtra("flag", "DEL");
                startActivity(i);
                fam.close(true);
            }
        });


        return v;
    }

}