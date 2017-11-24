package com.studiomjt.jupiterslider.model;

import com.studiomjt.jupiterslider.util.ImageProvider;

import java.io.Serializable;

public class Slide implements Serializable {

    private ImageProvider imageProvider;
    private String backColor;
    private Object uri;

    public Slide(ImageProvider imageProvider, Object uri, String backColor) {
        this.imageProvider = imageProvider;
        this.uri = uri;
        this.backColor = backColor;
    }

    public String getBackColor() {
        return backColor;
    }

    public Object getUri() {
        return uri;
    }

    public ImageProvider getImageProvider() {
        return imageProvider;
    }

}
