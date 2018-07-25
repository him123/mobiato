package com.ae.benchmark.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ae.benchmark.R;

/**
 * Created by Rakshit on 12-Jan-17.
 */
/*
@ This activity will be called when the + button is clicked on the visit list or all customer screen.
@ This will create a customer in the backend.
*/
public class TransitionAnimActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;

    private static final String TAG = TransitionAnimActivity.class.getSimpleName();

    private CoordinatorLayout mCLayout;
    private Button mButtonAuto;
    private Button mButtonFade;
    private Button mButtonSlide;
    private Button mButtonExplode;
    private ImageView mImageView;
    private ImageView mImageViewSecond;
    LinearLayout ll_iv_container;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_animations);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = TransitionAnimActivity.this;

        // Get the widget reference from XML layout
        mCLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mButtonAuto = (Button) findViewById(R.id.btn_auto);
        mButtonFade = (Button) findViewById(R.id.btn_fade);
        mButtonSlide = (Button) findViewById(R.id.btn_slide);
        mButtonExplode = (Button) findViewById(R.id.btn_explode);
        mImageView = (ImageView) findViewById(R.id.iv);
        mImageViewSecond = (ImageView) findViewById(R.id.iv_second);
        ll_iv_container = (LinearLayout) findViewById(R.id.ll_iv_container);

        // Set a click listener for button widget
        mButtonAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(mCLayout, makeAutoTransition());
                toggleVisibility(mImageView, mImageViewSecond);
            }
        });
        mButtonFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(ll_iv_container, makeFadeTransition());
                toggleVisibility(mImageView, mImageViewSecond);
            }
        });
        mButtonSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(mCLayout, makeSlideTransition());
                toggleVisibility(mImageView, mImageViewSecond);
            }
        });

        mButtonExplode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(mCLayout, makeExplodeTransition());
                toggleVisibility(mImageView, mImageViewSecond);
            }
        });
    }

    private Fade makeFadeTransition() {
        Fade fade = new Fade();
        fade.setDuration(3000);
        fade.setInterpolator(new AccelerateInterpolator());
        return fade;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Slide makeSlideTransition() {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.BOTTOM);
        slide.setInterpolator(new BounceInterpolator());
        slide.setDuration(3000);
        return slide;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Explode makeExplodeTransition() {
        Explode explode = new Explode();
        explode.setDuration(3000);
        explode.setInterpolator(new AnticipateOvershootInterpolator());
        return explode;
    }

    private AutoTransition makeAutoTransition() {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(3000);
        autoTransition.setInterpolator(new BounceInterpolator());
        return autoTransition;
    }

    // Custom method to toggle visibility of views
    private void toggleVisibility(View... views) {
        // Loop through the views
        for (View v : views) {
            if (v.getVisibility() == View.VISIBLE) {
                v.setVisibility(View.INVISIBLE);
            } else {
                v.setVisibility(View.VISIBLE);
            }
        }
    }
}