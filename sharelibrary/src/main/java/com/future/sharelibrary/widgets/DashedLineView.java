package com.future.sharelibrary.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.future.sharelibrary.R;


/**
 * 虚线
 * Created by Administrator on 2016/8/1.
 */
public class DashedLineView extends View {

    private int lineColor;
    private float lineHeight;
    private float dashWidth;
    private Paint paint = new Paint();

    public DashedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.DashedLineView);
        lineColor=ta.getColor(R.styleable.DashedLineView_lineColor,Color.DKGRAY);
        lineHeight=ta.getDimension(R.styleable.DashedLineView_lineHeight,5);
        dashWidth=ta.getDimension(R.styleable.DashedLineView_dashWidth,5);
        init();
    }

    private void init(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineHeight);

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "phase", 0.0f, 1.0f);
        animator.setDuration(3000);
        animator.start();
    }

    public void setPhase(float phase)
    {
        Log.d("pathview", "setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(dashWidth, phase, 0.0f));
        invalidate();//will calll onDraw
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset)
    {
        return new DashPathEffect(new float[] { pathLength, pathLength },
                pathLength - phase * pathLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(0, getHeight()/2);
        path.lineTo(getWidth(),getHeight()/2);
        PathEffect effects = new DashPathEffect(new float[]{dashWidth,dashWidth,dashWidth,dashWidth},0);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}
