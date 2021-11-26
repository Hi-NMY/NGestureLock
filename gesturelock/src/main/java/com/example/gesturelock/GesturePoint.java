package com.example.gesturelock;

import android.view.View;

/**
 * @author nmynmy
 * @title: GesturePoint
 * @projectName NGestureLock
 * @description: TODO
 * @date 2021-11-14 22:37
 */
public class GesturePoint {

    public static final int DEFAULT = 1;

    public static final int SELECT = 2;

    public static final int ERROR = 3;

    public static final int SUCCESS = 4;

    private int nowCode = 1;

    private final int centerX;

    private final int centerY;

    private final int value;

    private View view = null;

    public View getView() {
        return view;
    }

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

    public GesturePoint(int centerX, int centerY, int value, View view) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.value = value;
        this.view = view;
    }

    public void setCode(int code) {
        this.nowCode = code;
    }

    public boolean isDefault() {
        return nowCode == DEFAULT;
    }

    public boolean isSelected() {
        return nowCode == SELECT;
    }

    public boolean isError() {
        return nowCode == ERROR;
    }

    public boolean isSuccess() {
        return nowCode == SUCCESS;
    }
}
