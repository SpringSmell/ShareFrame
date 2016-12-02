package com.future.sharelibrary.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.future.sharelibrary.tools.CommonUtils;
import com.future.sharelibrary.tools.FormatUtils;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/15.
 */
public class Curve24HourChart extends HorizontalScrollView {

    private Context mContext;
    private int toalCount;
    private int itemWidth;
    private int columnCount;
    private int margin;
    private Paint txtPaint;
    private Paint linePaint;
    private Paint gesturePaint;
    private LinearGradient mLinearGradient;
    private int parentHeight, parentWidth;
    private float txtSize;
    private float lineSize;
    private float min;
    private float scale;
    private float max;
    private float txtHeight;

    private List<ChartBean> chartDataList;

    public Curve24HourChart(Context context) {
        this(context, null);
    }

    public Curve24HourChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        margin = FormatUtils.dip2px(getContext(), 15);
        txtSize = FormatUtils.dip2px(getContext(), 15);
        lineSize = FormatUtils.dip2px(getContext(), 1);
        columnCount = 7;

        mLinearGradient = new LinearGradient(0, 0, 100, 100, new int[]
                {
                        Color.argb(50, 255, 255, 255), Color.argb(50, 255, 255, 255), Color.argb(50, 255, 255, 255)
                }, null, Shader.TileMode.MIRROR);

        linePaint = new Paint();
        linePaint.setColor(0x50FFFFFF);
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(false);

        txtPaint = new Paint();
        txtPaint.setColor(0xFFFFFFFF);
        txtPaint.setTextSize(FormatUtils.dip2px(getContext(), 10f));
        txtPaint.setAntiAlias(true);

        gesturePaint = new Paint();//贝塞尔曲线画笔
        gesturePaint.setColor(Color.WHITE);
        gesturePaint.setStrokeWidth(1f);
        gesturePaint.setStyle(Paint.Style.FILL);
        gesturePaint.setFlags(Paint.ANTI_ALIAS_FLAG);//平滑
        gesturePaint.setShader(mLinearGradient);

        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.parentHeight = CommonUtils.getScreenHeight(mContext) / 5;
        this.parentWidth = CommonUtils.getScreenWidth(mContext);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.parentHeight = h;
        this.parentWidth = w;
        itemWidth = (parentWidth - 2 * margin) / columnCount;

    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    public void setData(List<ChartBean> datas) {
        this.chartDataList = datas;
        removeAllViews();
        CurveChartView curveChartView = new CurveChartView(mContext);
        addView(curveChartView);
        invalidate();
    }

    private float getMaxValue(List<ChartBean> chartDataList) {
        float maxValue = 0;
        for (ChartBean bean : chartDataList) {
            if (bean.index > maxValue) {
                maxValue = bean.index;
            }
        }
        return maxValue;
    }

    public float getScale() {
        float min = getMinValue(chartDataList);
        float max = getMaxValue(chartDataList);
        float height = this.parentHeight - txtSize - margin - txtSize;
        float scale = (int) (height / (max - min));
        return scale;
    }

    /**
     * 获取数据最小值
     *
     * @return
     */
    private float getMinValue(List<ChartBean> chartDataList) {
        if (chartDataList == null) {
            return 1;
        }
        float min = chartDataList.get(0).index;
        for (ChartBean chartBean : chartDataList) {
            if (chartBean.index < min) {
                min = chartBean.index;
            }
        }
        return min;
    }

    public static class ChartBean {
        public float index;
        public String hint = "";
    }

    public class CurveChartView extends View {

        private int width, height;

        public CurveChartView(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            int width = (CommonUtils.getScreenWidth(mContext));
            itemWidth = (parentWidth - 2 * margin) / columnCount;
            setMeasuredDimension((chartDataList.size()) * itemWidth, CommonUtils.getScreenHeight(mContext) / 5);
        }


        @Override
        public void setLayoutParams(ViewGroup.LayoutParams params) {
            super.setLayoutParams(params);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            this.width = w;
            this.height = h;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (chartDataList == null && chartDataList.size() < 0) {
                return;
            }
            toalCount = chartDataList.size();
            min = getMinValue(chartDataList);
            max = getMaxValue(chartDataList);
            scale = getScale();
            txtHeight = FormatUtils.getFontHeight(txtPaint);
            drawData(canvas);
        }

        private void drawData(Canvas canvas) {
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < chartDataList.size(); i++) {
                ChartBean chartBean = chartDataList.get(i);
                float x, y;
                x = itemWidth * i + itemWidth / 2;
                y = height-txtHeight - (chartBean.index - min) * scale-lineSize;//-lineSize最小值时预留高度

                drawTxt(canvas, chartBean, x);
                drawLine(canvas, x);

                Point point = new Point();
                point.x = (int) x;
                point.y = (int) y;
                points.add(point);
            }
            drawCurve(canvas, points);
        }

        /**
         * 画曲线
         * @param canvas
         * @param points
         */
        private void drawCurve(Canvas canvas, List<Point> points) {

            Point startPoint = points.get(0);
            Point endPoint;
            Path path = new Path();
            path.moveTo(startPoint.x, height-txtHeight);
            path.lineTo(startPoint.x, startPoint.y);
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
                path.cubicTo(startPoint.x, startPoint.y, startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            }
            path.lineTo(points.get(points.size()-1).x, height - txtHeight);
            canvas.drawPath(path, gesturePaint);
        }

        private void drawTxt(Canvas canvas, ChartBean bean, float x) {
            int indexWidth = FormatUtils.getFontWidth(txtPaint, bean.index+"");
            int hintWidth = FormatUtils.getFontWidth(txtPaint, bean.hint);

            String index = bean.index + "";
            String hint = bean.hint;
            canvas.drawText(index, x - indexWidth / 2, txtHeight, txtPaint);
            canvas.drawText(hint, x - hintWidth / 2, height, txtPaint);

        }

        private void drawLine(Canvas canvas, float startX) {
            canvas.drawLine(startX, height, startX, 0, linePaint);
        }
    }

}
