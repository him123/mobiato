package com.ae.benchmark.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Himm on 3/10/2018.
 */

public class AppUtils {

    public static void setRegularFace(Context context, TextView textView) {
        Typeface mFontRegular = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Regular.ttf");

        textView.setTypeface(mFontRegular);
    }

    public static void setMediumFace(Context context, TextView textView) {
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Medium.ttf");

        textView.setTypeface(mFont);
    }

    public static void setLightFace(Context context, TextView textView) {
        Typeface mFontLight = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Light.ttf");

        textView.setTypeface(mFontLight);
    }
}
