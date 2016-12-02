package com.future.sharelibrary.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.future.sharelibrary.tools.CommonUtils;
import com.future.sharelibrary.tools.FormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 弧线图表
 * Created by chris on 2016/6/20.
 */
public class HistogramGridChartView extends HorizontalScrollView {

    public static final String TAG = "ARCChart";
    private Context mContext;
    public int parentWidth, parentHeight;
    /**
     * 总列数
     */
    private int total = 24;
    private int color;

    private HistogramView mHistogramView;
    private List<ChartBean> chartBeen;
    private int padding;

    public HistogramGridChartView(Context context, int color) {
        this(context, null);
        this.color = color;
    }

    public HistogramGridChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.parentWidth = w;
        this.parentHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        this.parentWidth = CommonUtils.getScreenWidth(mContext) - FormatUtils.dip2px(mContext, 10);
        this.parentHeight = CommonUtils.getScreenHeight(mContext)/5;
//        switch (widthMode) {
//            case MeasureSpec.EXACTLY://match_parent
//                Log.e(TAG, "onMeasure: match_parent");
//                break;
//            case MeasureSpec.AT_MOST://wrap_content
//                Log.e(TAG, "onMeasure: wrap_content");
//                break;
//
//            case MeasureSpec.UNSPECIFIED://any size
//                Log.e(TAG, "onMeasure: any size");
//                break;
//        }
//        switch (heightMode) {
//            case MeasureSpec.EXACTLY://match_parent
//                Log.e(TAG, "onMeasure: match_parent");
//                break;
//            case MeasureSpec.AT_MOST://wrap_content
//                Log.e(TAG, "onMeasure: wrap_content");
//                break;
//            case MeasureSpec.UNSPECIFIED://any size
//                Log.e(TAG, "onMeasure: any size");
//                break;
//        }
        setMeasuredDimension(parentWidth, parentHeight);
    }

    private void initView() {
        padding = FormatUtils.dip2px(mContext, 10);
        setHorizontalScrollBarEnabled(false);
        mHistogramView = new HistogramView(mContext);
        addView(mHistogramView);
    }

    public void setData(List<ChartBean> arcBeen) {
        if (arcBeen != null) {
            if (mHistogramView == null) {
                mHistogramView = new HistogramView(mContext);
                addView(mHistogramView);
            }
            this.chartBeen = arcBeen;
            invalidate();
            mHistogramView.invalidate();
        } else {
            throw new NullPointerException("The data is null,please check");
        }
    }


    public void setColor(int color) {
        this.color = color;
        mHistogramView.invalidate();
    }

    public void setTotal(int total) {
        this.total = total;
        invalidate();
    }


    public static class ChartBean {
        public float value;
        public String hint;
    }

    public class HistogramView extends View {

        /**
         * 线条画笔
         */
        private Paint linePaint;
        private int lineSize;
        /**
         * 小圆点画笔
         */
        private Paint shapePaint;
        /**
         * 小圆点大小
         */
        private int shapeSize;
        /**
         * 每一小列大小
         */
        private int itemWidth;
        private int width;
        private int height;
        /**
         * 上下刻度,刻度*值=Y轴值
         */
        private int scale;

        public HistogramView(Context context) {
            this(context, null);
        }

        public HistogramView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            lineSize = padding;
            shapeSize = FormatUtils.dip2px(mContext, 6);

            shapePaint = new Paint();
            shapePaint.setColor(color);
            shapePaint.setAntiAlias(true);
            shapePaint.setDither(true);
            shapePaint.setStrokeWidth(lineSize);

            linePaint = new Paint();
            linePaint.setColor(color);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(shapeSize / 2);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = CommonUtils.getScreenWidth(mContext) - FormatUtils.dip2px(mContext, 10);
            int h = FormatUtils.dip2px(mContext, 50);
            setMeasuredDimension(w, h);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            this.width = parentWidth - padding;
            this.height = parentHeight - padding;
            itemWidth = this.width / total;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            shapePaint.setColor(color);
            linePaint.setColor(color);
            if (chartBeen != null && chartBeen.size() > 0)
                drawDatas(canvas);
        }

        /**
         * 画数据
         *
         * @param canvas
         */
        private void drawDatas(Canvas canvas) {
            scale = getScale();
            float min = getMinValue();
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < chartBeen.size(); i++) {
                ChartBean arcBean = chartBeen.get(i);
                float x, y;
                x = itemWidth * i + itemWidth / 2;
                y = parentHeight - padding - (arcBean.value - min) * scale;
                drawShape(canvas, x, y);
                Point point = new Point();
                point.x = (int) x + shapeSize / 2;
                point.y = (int) y + shapeSize / 2;
                points.add(point);
            }
            drawCurve(canvas, points);
        }

        /**
         * 画曲线
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void drawCurve(Canvas canvas, List<Point> points) {
            Point startPoint;
            Point endPoint;
            for (int i = 0; i < points.size() - 1; i++) {
                startPoint = points.get(i);
                endPoint = points.get(i + 1);
                int wt = (startPoint.x + endPoint.x) / 2;
                Point p1 = new Point();
                Point p2 = new Point();
                p1.y = startPoint.y;
                p1.x = wt;
                p2.y = endPoint.y;
                p2.x = wt;

                Path path = new Path();
                path.moveTo(startPoint.x, startPoint.y);
                path.cubicTo(p1.x, p1.y, p2.x, p2.y, endPoint.x, endPoint.y);
                canvas.drawPath(path, linePaint);
            }
        }

        /**
         * 画小圆点
         */
        private void drawShape(Canvas canvas, float x, float y) {
            RectF rectF = new RectF(x, y, x + shapeSize, y + shapeSize);
            canvas.drawOval(rectF, shapePaint);
        }

        /**
         * 获取数据最大值
         *
         * @return
         */
        private float getMaxValue() {
            if (chartBeen == null) {
                return 1;
            }
            float max = 0;
            for (ChartBean chartBean : chartBeen) {
                if (chartBean.value > max) {
                    max = chartBean.value;
                }
            }
            return max;
        }

        /**
         * 获取数据最小值
         *
         * @return
         */
        private float getMinValue() {
            if (chartBeen == null) {
                return 1;
            }
            float min = chartBeen.get(0).value;
            for (ChartBean arcBean : chartBeen) {
                if (arcBean.value < min) {
                    min = arcBean.value;
                }
            }
            return min;
        }

        /**
         * 获取刻度
         *
         * @return
         */
        private int getScale() {
            float min = getMinValue();
            float max = getMaxValue();
            int scale = (int) (this.height / (max - min));
            return scale;
        }

    }
}
