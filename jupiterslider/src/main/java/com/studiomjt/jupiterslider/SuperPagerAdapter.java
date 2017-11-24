package com.studiomjt.jupiterslider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.studiomjt.jupiterslider.listener.JupiterSliderListener;
import com.studiomjt.jupiterslider.model.Slide;
import com.studiomjt.jupiterslider.util.ImageProvider;
import com.studiomjt.jupiterslider.util.Util;

import java.util.List;

class SuperPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Slide> slideCollection;
    private JupiterSliderListener listener;
    private boolean isTwoSideForTablet = true;

    SuperPagerAdapter(Context context, List<Slide> slideGroup, boolean isTwoSideForTablet) {
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
        assert inflater != null;
        View itemView = inflater.inflate(R.layout.slide_child, container, false);
        ImageView imageView = itemView.findViewById(R.id.slideImageView);
        int backColor = Color.parseColor("#fafafa");
        try {
            backColor = Color.parseColor(slideCollection.get(position).getBackColor());
        } catch (Exception ignored) {
            Log.w(getClass().getSimpleName(), "Failed to Parse HexColor");
        }
        if (slideCollection.get(position).getImageProvider().getType() == ImageProvider.SourceType.BITMAP) {
            imageView.setImageBitmap(slideCollection.get(position).getImageProvider().getBitmap());
        } else if (slideCollection.get(position).getImageProvider().getType() == ImageProvider.SourceType.ID) {
            imageView.setImageDrawable(context.getResources().getDrawable(slideCollection.get(position).getImageProvider().getId()));
        } else {
            Glide.with(context)
                    .load(slideCollection.get(position).getImageProvider().getUrl())
                    .placeholder(new ColorDrawable(backColor))
                    .into(imageView);
        }
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

    void addListener(JupiterSliderListener listener) {
        this.listener = listener;
    }
}
