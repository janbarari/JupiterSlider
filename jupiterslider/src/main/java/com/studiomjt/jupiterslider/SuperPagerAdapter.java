package com.studiomjt.jupiterslider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.studiomjt.jupiterslider.listener.OnSlideClickListener;
import com.studiomjt.jupiterslider.model.SlideModel;
import com.studiomjt.jupiterslider.util.Util;

import java.util.List;

class SuperPagerAdapter extends PagerAdapter {

    private Context context;
    private List<SlideModel> slideCollection;
    private OnSlideClickListener listener;
    private boolean isTwoSideForTablet = true;

    SuperPagerAdapter(Context context, List<SlideModel> slideGroup, boolean isTwoSideForTablet) {
        this.context = context;
        this.slideCollection = slideGroup;
        this.isTwoSideForTablet = isTwoSideForTablet;
    }

    @Override
    public int getCount() {
        return slideCollection.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public void clearSlider() {
        slideCollection.clear();
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.slide_child, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.slideImageView);
        int backColor = Color.parseColor("#fafafa");
        try {
            backColor = Color.parseColor(slideCollection.get(position).getBackColor());
        } catch (Exception ignored) {
        }
        Glide.with(context)
                .load(slideCollection.get(position).getUrl())
                .placeholder(new ColorDrawable(backColor))
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position, slideCollection.get(position).getUri());
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        if (isTwoSideForTablet) {
            if (Util.isTablet(context)) {
                return 0.5f;
            } else {
                return super.getPageWidth(position);
            }
        } else {
            return super.getPageWidth(position);
        }
    }

    void addOnSlideListener(OnSlideClickListener listener) {
        this.listener = listener;
    }
}
