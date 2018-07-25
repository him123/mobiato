package com.ae.benchmark.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.github.clans.fab.FloatingActionButton;

import butterknife.ButterKnife;

/**
 * Created by Himm on 3/13/2018.
 */

public class FloatingPointExample extends AppCompatActivity {

    FloatingActionButton menu1, menu2, menu3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_exa);
        ButterKnife.inject(this);

        menu1 = (FloatingActionButton) findViewById(R.id.subFloatingMenu1);
        menu2 = (FloatingActionButton) findViewById(R.id.subFloatingMenu2);
        menu3 = (FloatingActionButton) findViewById(R.id.subFloatingMenu3);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingPointExample.this, " Alarm Icon clicked ", Toast.LENGTH_LONG).show();
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingPointExample.this, "BackUp Icon clicked", Toast.LENGTH_LONG).show();
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingPointExample.this, "Settings Icon clicked", Toast.LENGTH_LONG).show();
            }
        });
    }
}
