package com.studiomjt.jupiterslider.model;

import java.io.Serializable;

public class SlideModel implements Serializable {

    private String url, backColor;
    private Object uri;

    public SlideModel(String url, Object uri, String backColor) {
        this.url = url;
        this.uri = uri;
        this.backColor = backColor;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public Object getUri() {
        return uri;
    }

    void setUri(Object uri) {
        this.uri = uri;
    }
}
