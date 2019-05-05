package com.graduation.hp.core.repository.entity;

public class Dimension {

    private final int mWidth;
    private final int mHeight;

    public Dimension(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
