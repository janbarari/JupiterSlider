package com.studiomjt.jupiterslider.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.studiomjt.jupiterslider.util.FixedSpeedScroller;

import java.lang.reflect.Field;

public class SuperSlider extends ViewPager {

    private int pageDuration = 900;

    public SuperSlider(Context context) {
        super(context);
        init();
    }

    public SuperSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setPageDuration(int duration) {
        this.pageDuration = duration;
        init();
    }

    void init() {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            FixedSpeedScroller scrollerSpeed = new FixedSpeedScroller(getContext());
            scrollerSpeed.setDuration(pageDuration);
            scroller.set(this, scrollerSpeed);
        } catch (Exception e) {
            throw new RuntimeException("JupiterSlider Scroller Duration not Initialized");
        }
    }

}
