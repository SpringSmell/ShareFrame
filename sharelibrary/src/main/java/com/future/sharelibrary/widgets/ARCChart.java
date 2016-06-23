package com.future.sharelibrary.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.util.List;

/**
 * 弧线图表
 * Created by chris on 2016/6/20.
 */
public class ARCChart extends HorizontalScrollView {

    public static final String TAG="ARCChart";
    private Context mContext;
    public int width,height;

    private ARC arc;
    private List<ARCBean> arcBeen;

    public ARCChart(Context context) {
        this(context,null);
    }

    public ARCChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        Log.e(TAG, "onLayout: ");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
        Log.e(TAG, "onSizeChanged: ");
        this.width=w;
        this.height=h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure: " );
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode){
            case MeasureSpec.EXACTLY://match_parent
                Log.e(TAG, "onMeasure: match_parent" );
                break;
            case MeasureSpec.AT_MOST://wrap_content
                Log.e(TAG, "onMeasure: wrap_content" );
                break;

            case MeasureSpec.UNSPECIFIED://any size
                Log.e(TAG, "onMeasure: any size" );
                break;
        }
        switch (heightMode){
            case MeasureSpec.EXACTLY://match_parent
                Log.e(TAG, "onMeasure: match_parent" );
                break;
            case MeasureSpec.AT_MOST://wrap_content
                Log.e(TAG, "onMeasure: wrap_content" );
                break;
            case MeasureSpec.UNSPECIFIED://any size
                Log.e(TAG, "onMeasure: any size" );
                break;
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    private void initView(){
        arc=new ARC(mContext);
        addView(arc);
    }

    public void setData(List<ARCBean> arcBeen){
        this.arcBeen=arcBeen;
        if(arc==null){
            arc=new ARC(mContext);
        }
        arc.invalidate();
    }

    public static class ARCBean{
        private int value;
        private String hint;
    }

    public class ARC extends View{

        public ARC(Context context) {
            this(context, null);
        }

        public ARC(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        private void initView(){

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }
    }



}
