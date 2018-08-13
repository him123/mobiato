package com.ae.benchmark.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.rest.RestClient;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.MyFirebaseMessagingService;
import com.ae.benchmark.util.UtilApp;

import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Himm on 3/13/2018.
 */

public class SuperVisorApproveActivity extends AppCompatActivity {


    @InjectView(R.id.rl_main)
    RelativeLayout rl_main;

    @InjectView(R.id.rl_nothing)
    RelativeLayout rl_nothing;

    @InjectView(R.id.txt_sm)
    TextView txt_sm;

    @InjectView(R.id.txt_cust)
    TextView txt_cust;

    @InjectView(R.id.txt_no_app)
    TextView txt_no_app;

    @InjectView(R.id.txt_title)
    TextView txt_title;

    @InjectView(R.id.btn_approve)
    Button btn_approve;

    @InjectView(R.id.btn_reject)
    Button btn_reject;

    @InjectView(R.id.edt_btl)
    EditText edt_btl;

    private Toolbar toolbar;
    TextView mTitle;

    String supervisor_id,
            cust_id,
            salesman_id,
            no_of_bottles, cust_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_approval);
        ButterKnife.inject(this);

        Bundle extras = getIntent().getExtras();

        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseMessagingService.BROADCAST_ACTION));

        if (extras != null) {

            rl_main.setVisibility(View.VISIBLE);
            rl_nothing.setVisibility(View.GONE);

            supervisor_id = extras.getString("supervisor_id");
            cust_id = extras.getString("cust_id");
            salesman_id = extras.getString("salesman_id");
            no_of_bottles = extras.getString("no_of_bottles");
//            cust_name = extras.getString("cust_name");

            txt_title.setText("Approval request comes from " + salesman_id);

//            txt_btl_num.setText(no_of_bottles);
            txt_cust.setText(cust_id);
            txt_sm.setText(salesman_id);
            edt_btl.setText(no_of_bottles);

            if (no_of_bottles == null || no_of_bottles.equals("")) {
                rl_main.setVisibility(View.GONE);
                rl_nothing.setVisibility(View.VISIBLE);
            }

        } else {
            rl_main.setVisibility(View.GONE);
            rl_nothing.setVisibility(View.VISIBLE);
        }

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText("Supevisor");

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_reject_approval(salesman_id, "yes", edt_btl.getText().toString());
            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_reject_approval(salesman_id, "no", edt_btl.getText().toString());
            }
        });
    }

    private void accept_reject_approval(final String id, final String status, String num_of_bottles) {
        RestClient.getMutualTransfer().accept_reject_approval(id, num_of_bottles,
                status,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Log.v("", "Response: " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(UtilApp.getString(response.getBody().in()));
                            Log.v("", "==== Json: " + jsonObject.toString());

                            if (jsonObject.getString("STATUS").equals("1")) {
                                rl_main.setVisibility(View.GONE);
                                rl_nothing.setVisibility(View.VISIBLE);
                                if (status.equals("yes")) {
                                    txt_no_app.setText("Approval accepted sent successfully!");
                                } else {
                                    txt_no_app.setText("Approval rejected sent successfully!");
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.v("", "Error: " + error);
                    }
                });
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("", "=========== broadcast receiver : " + intent.getStringExtra("cust_id"));

            rl_main.setVisibility(View.VISIBLE);
            rl_nothing.setVisibility(View.GONE);

            txt_cust.setText(intent.getStringExtra("cust_id"));
            edt_btl.setText(intent.getStringExtra("no_of_bottles"));
//            txt_btl_num.setText("Totle number extran bottles: " + intent.getStringExtra("no_of_bottles"));
            txt_sm.setText(intent.getStringExtra("salesman_id"));
            txt_title.setText("Approval request comes from " + intent.getStringExtra("salesman_id"));

            salesman_id = intent.getStringExtra("salesman_id");
            no_of_bottles = intent.getStringExtra("no_of_bottles");

        }
    };
}
