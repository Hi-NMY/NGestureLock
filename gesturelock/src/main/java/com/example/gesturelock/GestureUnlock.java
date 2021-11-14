package com.example.gesturelock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GestureUnlock extends View {

    private static final int DEFAULT_COLOR = Color.BLACK;

    private static final int SELECT_COLOR = Color.BLUE;

    private static final int ERROR_COLOR = Color.RED;

    private static final int SUCCESS_COLOR = Color.GREEN;

    private static final float DEFAULT_DETERMINE_TIME = (float) 0.5;

    private static final int DEFAULT_MIN_SELECT = 1;

    private static final boolean DEFAULT_LOOK = true;

    private static final boolean DEFAULT_ISSETUP = false;

    private Context context;

    private int circleErrorColor;
    private int circleSuccessColor;
    private int circleSelectColor;
    private int lineSelectColor;
    private int lineErrorColor;
    private int lineSuccessColor;
    private float determineTime;
    private boolean lookLocus;
    private int errorView;
    private int defaultView;
    private int minSelect;
    private int selectView;
    private int successView;
    private int circleDefaultColor;
    private boolean isSetUp;

    private Paint defaultCirclePaint;
    private Paint nowCirclePaint;
    private Paint errorCirclePaint;
    private Paint defaultlinePaint;
    private Paint errorlinePaint;
    private Paint successlinePaint;
    private Paint successCirclePaint;

    private int cicleRadius = 10;

    private boolean firstInit = false;

    private List<List<GesturePoint>> points = new ArrayList<>();
    private List<GesturePoint> selectP = new ArrayList<>();
    private boolean alreadyTouch = false;
    private float moveX;
    private float moveY;
    private boolean isUp = false;
    private boolean lockTouch = false;

    private int returnFun = 0;

    private String defaultKey = "01234";

    private String setUpKey = "";

    private String errorKey = "";

    private Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                switch (msg.arg1) {
                    case 1:
                        if (gestureListener != null){
                            gestureListener.isSuccessful(defaultKey);
                        }
                        break;
                    case 2:
                        if (gestureListener != null){
                            gestureListener.isError(errorKey);
                        }
                        break;
                    case 3:
                        if (gestureListener != null){
                            gestureListener.isSetUp(setUpKey);
                        }
                        break;
                    case 4:
                        Toast.makeText(context,"请连接至少" + minSelect + "个点" ,Toast.LENGTH_SHORT).show();
                        break;
                }
                Thread.sleep((long) (determineTime * 1000));
                selectP.clear();
                for (List<GesturePoint> point : points) {
                    for (GesturePoint myPoint : point) {
                        myPoint.setCode(GesturePoint.DEFAULT);
                    }
                }
                invalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    });

    private IGestureListener gestureListener;

    public int getCircleErrorColor() {
        return circleErrorColor;
    }

    public void setCircleErrorColor(int circleErrorColor) {
        this.circleErrorColor = circleErrorColor;
    }

    public int getCircleSuccessColor() {
        return circleSuccessColor;
    }

    public void setCircleSuccessColor(int circleSuccessColor) {
        this.circleSuccessColor = circleSuccessColor;
    }

    public int getCircleSelectColor() {
        return circleSelectColor;
    }

    public void setCircleSelectColor(int circleSelectColor) {
        this.circleSelectColor = circleSelectColor;
    }

    public int getLineSelectColor() {
        return lineSelectColor;
    }

    public void setLineSelectColor(int lineSelectColor) {
        this.lineSelectColor = lineSelectColor;
    }

    public int getLineErrorColor() {
        return lineErrorColor;
    }

    public void setLineErrorColor(int lineErrorColor) {
        this.lineErrorColor = lineErrorColor;
    }

    public int getLineSuccessColor() {
        return lineSuccessColor;
    }

    public void setLineSuccessColor(int lineSuccessColor) {
        this.lineSuccessColor = lineSuccessColor;
    }

    public float getDetermineTime() {
        return determineTime;
    }

    public void setDetermineTime(float determineTime) {
        this.determineTime = determineTime;
    }

    public boolean isLookLocus() {
        return lookLocus;
    }

    public void setLookLocus(boolean lookLocus) {
        this.lookLocus = lookLocus;
    }

    public int getErrorView() {
        return errorView;
    }

    public void setErrorView(int errorView) {
        this.errorView = errorView;
    }

    public int getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(int defaultView) {
        this.defaultView = defaultView;
    }

    public int getMinSelect() {
        return minSelect;
    }

    public void setMinSelect(int minSelect) {
        this.minSelect = minSelect;
    }

    public int getSelectView() {
        return selectView;
    }

    public void setSelectView(int selectView) {
        this.selectView = selectView;
    }

    public int getSuccessView() {
        return successView;
    }

    public void setSuccessView(int successView) {
        this.successView = successView;
    }

    public int getCircleDefaultColor() {
        return circleDefaultColor;
    }

    public void setCircleDefaultColor(int circleDefaultColor) {
        this.circleDefaultColor = circleDefaultColor;
    }

    public boolean isSetUp() {
        return isSetUp;
    }

    public void setSetUp(boolean setUp) {
        isSetUp = setUp;
    }

    public GestureUnlock(@NonNull Context context) {
        this(context, null);
    }

    public GestureUnlock(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureUnlock(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
    }

    private void initAttrs(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GestureUnlock);
        circleDefaultColor = typedArray.getColor(R.styleable.GestureUnlock_circleDefaultColor, DEFAULT_COLOR);
        circleErrorColor = typedArray.getColor(R.styleable.GestureUnlock_circleErrorColor, ERROR_COLOR);
        circleSuccessColor = typedArray.getColor(R.styleable.GestureUnlock_circleSuccessColor, SUCCESS_COLOR);
        circleSelectColor = typedArray.getColor(R.styleable.GestureUnlock_circleSelectColor, SELECT_COLOR);
        lineSelectColor = typedArray.getColor(R.styleable.GestureUnlock_lineSelectColor, SELECT_COLOR);
        lineErrorColor = typedArray.getColor(R.styleable.GestureUnlock_lineErrorColor, ERROR_COLOR);
        lineSuccessColor = typedArray.getColor(R.styleable.GestureUnlock_lineSuccessColor, SUCCESS_COLOR);
        determineTime = typedArray.getFloat(R.styleable.GestureUnlock_determineTime, DEFAULT_DETERMINE_TIME);
        minSelect = typedArray.getInt(R.styleable.GestureUnlock_minSelect, DEFAULT_MIN_SELECT);
        lookLocus = typedArray.getBoolean(R.styleable.GestureUnlock_lookLocus, DEFAULT_LOOK);
        selectView = typedArray.getResourceId(R.styleable.GestureUnlock_selectView, 0);
        errorView = typedArray.getColor(R.styleable.GestureUnlock_errorView, 0);
        successView = typedArray.getColor(R.styleable.GestureUnlock_successView, 0);
        defaultView = typedArray.getColor(R.styleable.GestureUnlock_defaultView, 0);
        isSetUp = typedArray.getBoolean(R.styleable.GestureUnlock_isSetUp, DEFAULT_ISSETUP);
        typedArray.recycle();
    }

    private void initPoint() {
        int a = 0;
        int b = 1;
        int c = 2;

        int inWidth = getWidth() / 6;
        int scHeight = (getHeight() - getWidth()) / 2;
        int inHeight = 0;
        for (int i = 1; i <= 3; i++) {
            if (i == 1) {
                inHeight = scHeight + inWidth;
            } else {
                inHeight = inHeight + inWidth * 2;
            }

            List<GesturePoint> p = new ArrayList<>();
            p.add(new GesturePoint(inWidth, inHeight, a));
            p.add(new GesturePoint(inWidth * 3, inHeight, b));
            p.add(new GesturePoint(inWidth * 5, inHeight, c));
            a += 3;
            b += 3;
            c += 3;
            points.add(p);
        }
    }

    private void initMyPaints() {
        defaultCirclePaint = new Paint();
        defaultCirclePaint.setAntiAlias(true);
        defaultCirclePaint.setStyle(Paint.Style.STROKE);
        defaultCirclePaint.setStrokeWidth(10);
        defaultCirclePaint.setColor(getCircleDefaultColor());
        nowCirclePaint = new Paint();
        nowCirclePaint.setAntiAlias(true);
        nowCirclePaint.setStyle(Paint.Style.STROKE);
        nowCirclePaint.setStrokeWidth(10);
        nowCirclePaint.setColor(getCircleSelectColor());
        errorCirclePaint = new Paint();
        errorCirclePaint.setAntiAlias(true);
        errorCirclePaint.setStyle(Paint.Style.STROKE);
        errorCirclePaint.setStrokeWidth(10);
        errorCirclePaint.setColor(getCircleErrorColor());
        successCirclePaint = new Paint();
        successCirclePaint.setAntiAlias(true);
        successCirclePaint.setStyle(Paint.Style.STROKE);
        successCirclePaint.setStrokeWidth(10);
        successCirclePaint.setColor(getCircleSuccessColor());
        defaultlinePaint = new Paint();
        defaultlinePaint.setAntiAlias(true);
        defaultlinePaint.setStyle(Paint.Style.STROKE);
        defaultlinePaint.setColor(getLineSelectColor());
        defaultlinePaint.setStrokeWidth(10);
        errorlinePaint = new Paint();
        errorlinePaint.setAntiAlias(true);
        errorlinePaint.setStyle(Paint.Style.STROKE);
        errorlinePaint.setColor(getLineErrorColor());
        errorlinePaint.setStrokeWidth(10);
        successlinePaint = new Paint();
        successlinePaint.setAntiAlias(true);
        successlinePaint.setStyle(Paint.Style.STROKE);
        successlinePaint.setColor(getLineSuccessColor());
        successlinePaint.setStrokeWidth(10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!lockTouch) {
            moveX = event.getX();
            moveY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isUp = false;
                    toNowTouch();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (alreadyTouch) {
                        toNowTouch();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (alreadyTouch) {
                        isUp = true;
                        StringBuffer stringBuffer = new StringBuffer();
                        for (GesturePoint point : selectP) {
                            stringBuffer.append(point.getValue());
                        }
                        if (!isSetUp){
                            if (defaultKey.equals(stringBuffer.toString())) {
                                toSuccessTouch();
                            } else {
                                errorKey = stringBuffer.toString();
                                toErrorTouch();
                            }
                        }else {
                            setUpKey = stringBuffer.toString();
                        }
                    }
                    break;
            }
            invalidate();
        }
        return true;
    }

    private void toSuccessTouch() {
        for (GesturePoint point : selectP) {
            point.setCode(GesturePoint.SUCCESS);
        }
    }

    private void toNowTouch() {
        GesturePoint point = touchMyPoint();
        if (point != null) {
            alreadyTouch = true;
            point.setCode(GesturePoint.SELECT);
            selectP.add(point);
        }
    }

    private void toErrorTouch() {
        for (GesturePoint point : selectP) {
            point.setCode(GesturePoint.ERROR);
        }
    }

    private GesturePoint touchMyPoint() {
        for (List<GesturePoint> point : points) {
            for (GesturePoint myPoint : point) {
                if (toMath(myPoint.getCenterX(), myPoint.getCenterY(), moveX, moveY, cicleRadius * 9) && !myPoint.isSelected() && !myPoint.isError() && !myPoint.isSuccess()) {
                    return myPoint;
                }
            }
        }
        return null;
    }

    private boolean toMath(int centerX, int centerY, float moveX, float moveY, int i) {
        return Math.sqrt((centerX - moveX) * (centerX - moveX) + (centerY - moveY) * (centerY - moveY)) < i;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!firstInit) {
            initMyPaints();
            initPoint();
            firstInit = true;
        }
        drawMyCircle(canvas);
        drawMyLine(canvas);
    }

    private void drawMyLine(Canvas canvas) {
        if (lookLocus){
            if (selectP.size() != 0) {
                if (isUp) {
                    line(0, 0, 0, 0, canvas, defaultlinePaint);
                    if (isSetUp){
                        if (selectP.size() < minSelect){
                            sendHandler(4);
                        }else {
                            sendHandler(3);
                        }
                    }
                } else {
                    int i = selectP.size() - 1;
                    line(selectP.get(i).getCenterX(), selectP.get(i).getCenterY(), moveX
                            , moveY, canvas, defaultlinePaint);
                }
            }
            for (int i = 0; i < selectP.size(); i++) {
                if (i != selectP.size() - 1) {
                    if (selectP.get(i).isSelected()) {
                        line(selectP.get(i).getCenterX(), selectP.get(i).getCenterY(), selectP.get(i + 1).getCenterX(), selectP.get(i + 1).getCenterY(), canvas, defaultlinePaint);
                    }

                    if (selectP.get(i).isError()) {
                        line(selectP.get(i).getCenterX(), selectP.get(i).getCenterY(), selectP.get(i + 1).getCenterX(), selectP.get(i + 1).getCenterY(), canvas, errorlinePaint);
                    }

                    if (selectP.get(i).isSuccess()) {
                        line(selectP.get(i).getCenterX(), selectP.get(i).getCenterY(), selectP.get(i + 1).getCenterX(), selectP.get(i + 1).getCenterY(), canvas, successlinePaint);
                    }
                }
            }
        }
    }

    private void line(float startX, float startY, float endX, float endY, Canvas canvas, Paint paint) {
        double slash = Math.sqrt((startX - endX) * (startX - endX) + (startY - endY) * (startY - endY));

        float oneX = (float) (startX + (cicleRadius / slash) * (endX - startX));
        float oneY = (float) (startY + (cicleRadius / slash) * (endY - startY));

        float twoX = (float) (endX - (cicleRadius / slash) * (endX - startX));
        float twoY = (float) (endY - (cicleRadius / slash) * (endY - startY));

        canvas.drawLine(oneX, oneY, twoX, twoY, paint);
    }

    private void drawMyCircle(Canvas canvas) {
        for (List<GesturePoint> point : points) {
            for (GesturePoint myPoint : point) {
                if (myPoint.isDefault()) {
                    drawMyCircle(canvas,myPoint,cicleRadius * 9, defaultCirclePaint);
                    drawMyCircle(canvas,myPoint, cicleRadius, defaultCirclePaint);
                }

                if (myPoint.isError()) {
                    drawMyCircle(canvas,myPoint,cicleRadius * 9, errorCirclePaint);
                    drawMyCircle(canvas,myPoint,cicleRadius, errorCirclePaint);
                    lockTouch = true;
                    returnFun = 2;
                }

                if (myPoint.isSelected()) {
                    drawMyCircle(canvas,myPoint,cicleRadius * 9, nowCirclePaint);
                    drawMyCircle(canvas,myPoint,cicleRadius, nowCirclePaint);
                }

                if (myPoint.isSuccess()) {
                    drawMyCircle(canvas,myPoint,cicleRadius * 9, successCirclePaint);
                    drawMyCircle(canvas,myPoint,cicleRadius, successCirclePaint);
                    lockTouch = true;
                    returnFun = 1;
                }
            }
        }
        if (lockTouch) {
            sendHandler(returnFun);
        }
    }

    private void drawMyCircle(Canvas c,GesturePoint p, int i, Paint paint) {
        if (lookLocus){
            c.drawCircle(p.getCenterX(), p.getCenterY(), i, paint);
        }else {
            c.drawCircle(p.getCenterX(), p.getCenterY(), i, defaultCirclePaint);
        }
    }

    private void sendHandler(int fun) {
        Message message = new Message();
        message.arg1 = fun;
        handler.sendMessage(message);
        lockTouch = false;
    }

    public void setDefaultKey(String key){
        this.defaultKey = key;
    }

    public void setIGestureListener(IGestureListener listener){
        this.gestureListener = listener;
    }

}
