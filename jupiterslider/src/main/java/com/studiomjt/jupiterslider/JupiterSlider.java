package com.studiomjt.jupiterslider;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.studiomjt.jupiterslider.listener.OnChangeListener;
import com.studiomjt.jupiterslider.listener.OnSlideClickListener;
import com.studiomjt.jupiterslider.model.SlideModel;
import com.studiomjt.jupiterslider.util.AutoSlider;
import com.studiomjt.jupiterslider.util.Util;

import java.util.List;

public class JupiterSlider extends FrameLayout implements ViewPager.OnPageChangeListener {

    private SuperSlider slider;
    private LinearLayout pageIndicator;
    private SuperPagerAdapter sliderAdapter;
    private int dotCount;
    private ImageView[] dots;
    private AutoSlider autoSlider;
    private int savedPosition;
    private boolean savedWayState;
    private int splitCount;
    ScaleAnimation animHideIndicator = new ScaleAnimation(2f, 1f, 2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    ScaleAnimation animShowIndicator = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private OnChangeListener onChangeListener;
    private boolean isAutoSlide = true;
    private boolean isTwoSideForTablet;

    public JupiterSlider(Context context) {
        super(context);
        init(context);
    }

    public JupiterSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JupiterSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JupiterSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context) {
        inflate(context, R.layout.jupiter, this);
        slider = (SuperSlider) findViewById(R.id.superSlider);
        pageIndicator = (LinearLayout) findViewById(R.id.pagerIndicator);
        animHideIndicator.setDuration(200);
        animShowIndicator.setDuration(200);
    }

    /**
     * call it before call the load method
     */
    public void disableAutoSlide() {
        this.isAutoSlide = false;
    }

    public void load(int defaultSlide, boolean isTwoSideForTablet, boolean scrollWay, List<SlideModel> slideCollection, int pageTransformDuration, int pageDuration, int splitCount, OnSlideClickListener onClickListener, OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
        this.isTwoSideForTablet = isTwoSideForTablet;
        slider.setPageDuration(pageTransformDuration);
        sliderAdapter = new SuperPagerAdapter(getContext(), slideCollection, isTwoSideForTablet);
        slider.setAdapter(sliderAdapter);
        sliderAdapter.addOnSlideListener(onClickListener);
        slider.addOnPageChangeListener(this);
        this.splitCount = splitCount;
        if (slider.getAdapter().getCount() > splitCount) {
            if (!isTwoSideForTablet) {
                dotCount = sliderAdapter.getCount();
                dots = new ImageView[dotCount];
                pageIndicator.removeAllViews();
                for (int i = 0; i < dotCount; i++) {
                    dots[i] = new ImageView(getContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_unselected));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(5, 0, 5, 0);
                    pageIndicator.addView(dots[i], params);
                }
                dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_selected));
            } else {
                if (!Util.isTablet(slider.getContext())) {
                    dotCount = sliderAdapter.getCount();
                    dots = new ImageView[dotCount];
                    pageIndicator.removeAllViews();
                    for (int i = 0; i < dotCount; i++) {
                        dots[i] = new ImageView(getContext());
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_unselected));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(5, 0, 5, 0);
                        pageIndicator.addView(dots[i], params);
                    }
                    dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_selected));
                }
            }
            if (isAutoSlide) {
                autoSlider = new AutoSlider(slider);
                autoSlider.start(pageDuration);
                autoSlider.restoreState(defaultSlide, scrollWay);
            }
        }
    }

    public SuperPagerAdapter getSliderAdapter() {
        return this.sliderAdapter;
    }

    public SuperSlider getSlider() {
        return this.slider;
    }

    public LinearLayout getPageIndicator() {
        return this.pageIndicator;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (autoSlider != null) {
            autoSlider.destroy();
            autoSlider = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        if (autoSlider != null) {
            this.savedPosition = autoSlider.getPosition();
            this.savedWayState = autoSlider.getWay();
            Bundle bundle = new Bundle();
            bundle.putParcelable("superState", super.onSaveInstanceState());
            bundle.putInt("position", this.savedPosition);
            bundle.putBoolean("way", this.savedWayState);
            return bundle;
        } else {
            Bundle bundle = new Bundle();
            return bundle;
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        try {
            if (state instanceof Bundle && autoSlider != null) {
                Bundle bundle = (Bundle) state;
                this.savedPosition = bundle.getInt("position");
                this.savedWayState = bundle.getBoolean("way");
                state = bundle.getParcelable("superState");
                autoSlider.restoreState(savedPosition, savedWayState);
            }
            super.onRestoreInstanceState(state);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (autoSlider != null) {
            autoSlider.pageChanged(position);
            onChangeListener.onChange(position, autoSlider.getWay());
        }
        if (slider.getAdapter().getCount() > splitCount) {
            if (!isTwoSideForTablet) {
                for (int i = 0; i < dotCount; i++) {
                    if (dots[i].getTag() == "selected") {
                        dots[i].startAnimation(animHideIndicator);
                    }
                    dots[i].setTag(null);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_unselected));
                }
                dots[position].startAnimation(animShowIndicator);
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_selected));
                dots[position].setTag("selected");
            } else {
                if (!Util.isTablet(slider.getContext())) {
                    for (int i = 0; i < dotCount; i++) {
                        if (dots[i].getTag() == "selected") {
                            dots[i].startAnimation(animHideIndicator);
                        }
                        dots[i].setTag(null);
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_unselected));
                    }
                    dots[position].startAnimation(animShowIndicator);
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_selected));
                    dots[position].setTag("selected");
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
