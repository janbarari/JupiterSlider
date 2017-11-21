package com.studiomjt.jupiterslider.util;

import android.content.Context;
import android.widget.Scroller;

public class FixedSpeedScroller extends Scroller {

    private int duration = 1000;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, this.duration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, duration);
    }
}