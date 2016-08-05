package com.future.sharelibrary.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.future.sharelibrary.R;

/**
 * Created by chris Zou on 2016/7/5.
 */
public class ShadowRelativeLayout extends RelativeLayout {

    private int width, height;
    private int shadowSize;
    private int shadowDx, shadowDy;
    private int shadowColor;
    private float shadowRadius;
    private Context mContext;
    private Paint mPaint;

    public ShadowRelativeLayout(Context context) {
        this(context, null);
    }

    public ShadowRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShadowRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a=mContext.obtainStyledAttributes(attrs, R.styleable.ShadowRelativeLayout);
        shadowColor=a.getColor(R.styleable.ShadowRelativeLayout_shadowColor,-1);
        shadowDx=a.getInt(R.styleable.ShadowRelativeLayout_shadowDx,0);
        shadowDy=a.getInt(R.styleable.ShadowRelativeLayout_shadowDy,0);
        shadowRadius=a.getFloat(R.styleable.ShadowRelativeLayout_shadowRadius,0);
        shadowSize=a.getInt(R.styleable.ShadowRelativeLayout_shadowSize,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
