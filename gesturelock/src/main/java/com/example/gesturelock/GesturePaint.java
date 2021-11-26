package com.example.gesturelock;

import android.graphics.Paint;

/**
 * @author nmynmy
 * @title: GesturePaint
 * @projectName NGestureLock
 * @description: TODO
 * @date 2021-11-14 22:36
 */
public class GesturePaint {

    public GesturePaint() {

    }

    public Paint createCirclePaint(Paint.Style style, float strockWidth, int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(style);
        paint.setStrokeWidth(strockWidth);
        paint.setColor(color);
        return paint;
    }

    public Paint createLinePaint(float strockWidth, int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(strockWidth);
        return paint;
    }
}
