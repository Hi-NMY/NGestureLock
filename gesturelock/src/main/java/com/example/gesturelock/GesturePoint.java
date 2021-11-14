package com.example.gesturelock;

public class GesturePoint {

    public static final int DEFAULT = 1;

    public static final int SELECT = 2;

    public static final int ERROR = 3;

    public static final int SUCCESS = 4;

    private int NOW_CODE = 1;

    private int centerX;

    private int centerY;

    private int value;

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getValue() {
        return value;
    }

    public GesturePoint(int centerX, int centerY, int value) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.value = value;
    }

    public void setCode(int code) {
        this.NOW_CODE = code;
    }

    public boolean isDefault() {
        return NOW_CODE == DEFAULT;
    }

    public boolean isSelected() {
        return NOW_CODE == SELECT;
    }

    public boolean isError() {
        return NOW_CODE == ERROR;
    }

    public boolean isSuccess() {
        return NOW_CODE == SUCCESS;
    }
}
