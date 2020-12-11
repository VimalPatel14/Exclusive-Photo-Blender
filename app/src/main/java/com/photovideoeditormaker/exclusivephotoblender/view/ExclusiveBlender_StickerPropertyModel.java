package com.photovideoeditormaker.exclusivephotoblender.view;

import java.io.Serializable;

public class ExclusiveBlender_StickerPropertyModel implements Serializable {
    private static final long serialVersionUID = 3800737478616389410L;
    private float degree;
    private int horizonMirror;
    private int order;
    private float scaling;
    private long stickerId;
    private String stickerURL;
    private String text;
    private float xLocation;
    private float yLocation;

    public float getDegree() {
        return this.degree;
    }

    public int getHorizonMirror() {
        return this.horizonMirror;
    }

    public int getOrder() {
        return this.order;
    }

    public float getScaling() {
        return this.scaling;
    }

    public long getStickerId() {
        return this.stickerId;
    }

    public String getStickerURL() {
        return this.stickerURL;
    }

    public String getText() {
        return this.text;
    }

    public float getxLocation() {
        return this.xLocation;
    }

    public float getyLocation() {
        return this.yLocation;
    }

    public void setDegree(float f) {
        this.degree = f;
    }

    public void setHorizonMirror(int i) {
        this.horizonMirror = i;
    }

    public void setOrder(int i) {
        this.order = i;
    }

    public void setScaling(float f) {
        this.scaling = f;
    }

    public void setStickerId(long j) {
        this.stickerId = j;
    }

    public void setStickerURL(String str) {
        this.stickerURL = str;
    }

    public void setText(String str) {
        this.text = str;
    }

    public void setxLocation(float f) {
        this.xLocation = f;
    }

    public void setyLocation(float f) {
        this.yLocation = f;
    }
}
