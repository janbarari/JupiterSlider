package com.studiomjt.jupiterslider.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class Util {
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int getLayoutHeightImageSize(Context context, int width, int height) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float imageWidth = width / displayMetrics.density;
        float imageHeight = height / displayMetrics.density;
        float deviceWidth = displayMetrics.widthPixels / displayMetrics.density;
        float totalHeight = (deviceWidth * imageHeight) / imageWidth;
        return (int) totalHeight;
    }
}
