package com.future.sharelibrary.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.future.sharelibrary.R;


public class RoundProgressBar extends View {
    private Paint circlePaint;
    private Paint txtPaint;

    /**
     * 圈颜色
     */
    private int roundColor;
    /**
     * 圈总大小
     */
    private float roundSize;
    /**
     * 弧颜色
     */
    private int roundProgressColor;
    /**
     * 外圈弧大小
     */
    private float roundBorderWidth;
    /**
     * 内圈颜色
     */
    private int roundInsideColor;
    /**
     * 内圈大小
     */
    private float roundInsideSize;
    private STYLE style=STYLE.FILL;
    private int textColor;

    private float textSize;
    /**
     * 是否显示文字
     */
    private boolean textIsDisplayable;

    private long max = 100;
    private long value = 0;
    private int progress = 0;
    private float[] positions;

    private Shader mShader;
    private Matrix mMatrix;
    private float x;
    private float y;

    public enum STYLE {

        STROKE(0), FILL(1);

        STYLE(int value) {
            this.value = value;
        }

        public int value;
    }

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.WHITE);
        roundSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundSize, 60);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, 0xFFFFFFFF);
        roundBorderWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundBorderWidth, 10);
        roundInsideSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundInsideSize, roundSize - roundBorderWidth);
        roundInsideColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundInsideColor, Color.TRANSPARENT);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 40);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style.value = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        mTypedArray.recycle();
        init();
    }

    private void init() {
        positions = new float[]{1.0f, 1.0f};
        mMatrix = new Matrix();
        circlePaint = new Paint();
        circlePaint.setColor(roundColor);
        circlePaint.setStrokeWidth(roundBorderWidth);
        circlePaint.setAntiAlias(true);

        txtPaint = new Paint();
        txtPaint.setColor(textColor);
        txtPaint.setTextSize(textSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = (float) (w / 2.0);
        y = (float) (h / 2.0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mShader = new SweepGradient(x, y, new int[]{roundColor, roundColor}, positions);
        mMatrix.setRotate(-90, x, y);
        mShader.setLocalMatrix(mMatrix);
        circlePaint.setShader(mShader);
        circlePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, roundSize, circlePaint);
        //内圈
        if (roundInsideSize > 0 && roundInsideColor != Color.TRANSPARENT) {
            circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            circlePaint.setShader(new SweepGradient(x, y, new int[]{roundInsideColor, roundInsideColor}, positions));
            if (roundInsideSize > roundSize - roundBorderWidth) {
                roundInsideSize = roundSize - roundBorderWidth;
            }
            canvas.drawCircle(x, y, roundInsideSize, circlePaint);
        }
        txtPaint.setTypeface(Typeface.DEFAULT_BOLD);
        progress = (int) (((float) value / (float) max) * max);
        float textWidth = txtPaint.measureText(progress + "%");
        if (textIsDisplayable) {
            if (progress < max) {
                canvas.drawText(progress + "%", x - textWidth / 2, y + textSize / 2, txtPaint);
            } else {
                canvas.drawText("Finish", x - textWidth / 2, y + textSize / 2, txtPaint);
            }
        }

        RectF oval = new RectF(x - roundSize, y - roundSize, x + roundSize, y + roundSize);
        circlePaint.setShader(new SweepGradient(x, y, new int[]{roundProgressColor, roundProgressColor}, positions));
//        circlePaint.setShader (new LinearGradient ( 0,0,100,100, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
//                                                                           Color.LTGRAY}, null,Shader.TileMode.REPEAT) );
        circlePaint.setStyle ( Paint.Style.FILL_AND_STROKE );
        int degrees = ( int ) (progress * 360 / max);
        switch (style.value) {
            case 0: {
                circlePaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 0, degrees, false, circlePaint);//false : 弧形
                break;
            }
            case 1: {
                circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (value >= 0)
                    canvas.drawArc(oval, 0, degrees, true, circlePaint);//true:扇形
                break;
            }
        }
    }

    public synchronized long getMax() {
        return max;
    }

    public synchronized void setMax(long max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public synchronized int getProgress() {
        return progress;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress >= 100) {
            progress = 100;
        }
        this.progress = progress;
        postInvalidate();
    }

    public synchronized void setValue(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (value > max) {
            this.value = max;
        }
        this.value = value;
        postInvalidate();
    }

    public long getValue(){
        return this.value;
    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
        postInvalidate();
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
        postInvalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        postInvalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        postInvalidate();
    }

    public float getRoundBorderWidth() {
        return roundBorderWidth;
    }

    public void setRoundBorderWidth(float roundBorderWidth) {
        this.roundBorderWidth = roundBorderWidth;
        postInvalidate();
    }

    public void setStyle(STYLE style) {
        this.style = style;
        postInvalidate();
    }

}
