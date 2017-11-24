package com.studiomjt.jupiterslider.util;

import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

public class ImageProvider {

    public enum SourceType {URL, BITMAP, ID}

    private String url;
    private int id;
    private Bitmap bitmap;
    private SourceType type;

    public ImageProvider(String url) {
        type = SourceType.URL;
        this.url = url;
    }

    public ImageProvider(@IdRes int drawableId) {
        type = SourceType.ID;
        this.id = drawableId;
    }

    public ImageProvider(Bitmap bitmap) {
        type = SourceType.BITMAP;
        this.bitmap = bitmap;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public Bitmap getBitmap() {
        return bitmap;
    }

    public SourceType getType() {
        return type;
    }
}
