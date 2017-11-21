package com.dettaa.jupitersliderexample;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.studiomjt.jupiterslider.JupiterSlider;
import com.studiomjt.jupiterslider.listener.OnChangeListener;
import com.studiomjt.jupiterslider.listener.OnSlideClickListener;
import com.studiomjt.jupiterslider.model.SlideModel;
import com.studiomjt.jupiterslider.util.Util;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements OnSlideClickListener, OnChangeListener {

    JupiterSlider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slider = findViewById(R.id.jupiterSlider);

        ArrayList<SlideModel> slides = new ArrayList<>();
        slides.add(new SlideModel("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/8/27/3fc18bbd.jpg", "tel:0123", "#000000"));
        slides.add(new SlideModel("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/8/27/501a655e.jpg", "web:google.com", "#ff9800"));
        slides.add(new SlideModel("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/8/23/e25db401.jpg", "telegram:mehdijanbarari", "#f4f4f4"));
        slides.add(new SlideModel("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/8/23/506c1f48.jpg", "mailto:info@studiomjt.com?subject=helloworld", "#000000"));
        Collections.reverse(slides);
        int layoutHeight = Util.getLayoutHeightImageSize(this, 890, 310);
        if (Util.isTablet(this)) {
            layoutHeight /= 2;
        }
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, layoutHeight,
                        this.getResources().getDisplayMetrics())
        );
        slider.setLayoutParams(params);
        slider.load(2, true,
                JupiterSlider.ScrollWays.Left, slides, 1200,
                4000, this, this);
    }

    @Override
    public void onClick(int slideId, Object uri) {

    }

    @Override
    public void onChange(int slidePosition, boolean scrollWay) {

    }
}
