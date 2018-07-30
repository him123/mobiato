package com.ae.benchmark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.introslider.WelcomeActivity;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.MyFirebaseInstanceIDService;
import com.ae.benchmark.util.UtilApp;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @InjectView(R.id.txt_title)
    TextView txt_title;

    @InjectView(R.id.txt_desc)
    TextView txt_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.inject(this);

        startService(new Intent(SplashActivity.this, MyFirebaseInstanceIDService.class));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                try {

                    if (!UtilApp.ReadSharePrefrence(SplashActivity.this, Constant.SHRED_PR.ISINTRO)) {
                        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (UtilApp.ReadSharePrefrence(SplashActivity.this, Constant.SHRED_PR.ISLOGIN)) {

                        Intent intent;

                        if (UtilApp.ReadSharePrefrenceString(SplashActivity.this, Constant.SHRED_PR.USERNAME).equalsIgnoreCase("SV1")) {
                            intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                        } else {
                            intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                            intent.putExtra("end", "0");
                        }

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
