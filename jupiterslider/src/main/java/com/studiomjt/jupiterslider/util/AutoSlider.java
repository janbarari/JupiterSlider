package com.studiomjt.jupiterslider.util;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class AutoSlider {

    private Timer timer = new Timer();
    private Handler handler = new Handler();
    private SuperSlider slider;
    private int currentPage = 0;
    private boolean isRightToLeft = true;

    public AutoSlider(final SuperSlider slider) {
        this.slider = slider;
    }

    private int getLastPage() {
        return slider.getAdapter().getCount() - 1;
    }

    private Runnable guiRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentPage == 0) {
                isRightToLeft = false;
            }
            if (currentPage == getLastPage()) {
                isRightToLeft = true;
            }
            slider.setCurrentItem(currentPage, true);
            if (currentPage < 0) {
                currentPage++;
            }
            if (currentPage > getLastPage()) {
                currentPage--;
            }
            if (isRightToLeft) {
                currentPage--;
            } else {
                currentPage++;
            }
        }
    };

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            handler.post(guiRunnable);
        }
    };

    public void start(int duration) {
        if (slider.getAdapter().getCount() <= 0) {
            return;
        }
        slider.setCurrentItem(getLastPage());
        currentPage = getLastPage();
        timer.scheduleAtFixedRate(task, 0, duration);
    }

    public void pageChanged(int position) {
        currentPage = position;
        isRightToLeft = position != 0 && (position == getLastPage() || isRightToLeft);
    }

    public void restoreState(int savedPosition, boolean savedWayState) {
        slider.setCurrentItem(savedPosition);
        currentPage = savedPosition;
        isRightToLeft = savedWayState;
    }

    public int getPosition() {
        return this.currentPage;
    }

    public boolean getWay() {
        return this.isRightToLeft;
    }

    public void destroy() {
        timer.cancel();
        timer = null;
    }
}
