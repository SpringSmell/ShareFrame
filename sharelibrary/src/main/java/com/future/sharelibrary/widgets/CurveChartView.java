package com.future.sharelibrary.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;


import com.future.sharelibrary.model.Params;
import com.future.sharelibrary.tools.FormatUtils;
import com.future.sharelibrary.tools.ImageUtils;

import java.util.List;

/**
 * 弧线图表
 * Created by chris on 2016/6/20.
 */
public class CurveChartView extends HorizontalScrollView {

    public static final String TAG = "CurveChartView";
    private Context mContext;
    public int parentWidth, parentHeight;
    /**
     * 一屏上的总列数
     */
    private int eachScreenTotal = 7;
    private int lineColor;
    private int lineSize;
    private int shapeColor;
    private int shapeSize;
    private int txtColor;
    private int txtSize;
    private int space;
    private int imgSize;


    /**
     * 每一小列大小
     */
    private float itemWidthChat;
    private float itemWidthHead;
    private float itemWidthFoot;
    /**
     * 线条数据
     */
    private List<ChartBean> chartBeen;
    /**
     * head
     */
    private List<List<ParamsBean>> topBeen;
    /**
     * foot
     */
    private List<List<ParamsBean>> bottomBeen;

    private float marginLeft;
    private float marginRight;
    private float marginTop;
    private float marginBottom;

    private String testStr = "";


    public CurveChartView(Context context) {
        this(context, null);
    }

    public CurveChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure: " + testStr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.parentWidth = w;
        this.parentHeight = h;

    }

    private void initView() {
        setHorizontalScrollBarEnabled(false);
        lineSize = FormatUtils.dip2px(mContext, 3);
        lineColor = Color.WHITE;

        shapeSize = FormatUtils.dip2px(mContext, 6);
        shapeColor = Color.WHITE;

        txtSize = FormatUtils.dip2px(mContext, 12f);
        txtColor = Color.WHITE;

        space = FormatUtils.dip2px(mContext, 2f);

        imgSize = FormatUtils.dip2px(mContext, 50);

        removeAllViews();
        addView(new CurveView(mContext));
    }

    public void setData(List<ChartBean> arcBeen) {
        this.setData(arcBeen, null, null);
    }

    public void setData(List<ChartBean> chartBeen, List<List<ParamsBean>> topBeen, List<List<ParamsBean>> bottomBeen) {
        if (chartBeen != null) {
            this.chartBeen = chartBeen;
        } else {
            throw new NullPointerException("The data is null,please check");
        }

        if (topBeen != null) {
            this.topBeen = topBeen;
        }
        if (bottomBeen != null) {
            this.bottomBeen = bottomBeen;
        }
        invalidate();
    }

    public void setMargin(int margin) {
        this.setMargin(margin, margin, margin, margin);
    }

    public void setMargin(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        invalidate();
    }

    public void setLine(int lineColor, int lineSize) {
        this.lineColor = lineColor;
        this.lineSize = lineSize;
        invalidate();
    }

    public void setShapeColor(int shapeColor, int shapeSize) {
        this.shapeColor = shapeColor;
        this.shapeSize = shapeSize;
        invalidate();
    }

    public void setEachScreenTotal(int eachScreenTotal) {
        this.eachScreenTotal = eachScreenTotal;
        invalidate();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        testStr = "invalidate";
        Log.e(TAG, "invalidate: " + testStr);
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
            if (chartBean.index > max) {
                max = chartBean.index;
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
        float min = chartBeen.get(0).index;
        for (ChartBean chartBean : chartBeen) {
            if (chartBean.index < min) {
                min = chartBean.index;
            }
        }
        return min;
    }

    /**
     * -------------模型-----------------------------------------------------------------
     */
    public static class ChartBean {
        public float index = -1;//指数
        public String hint;
    }

    public static class ParamsBean {
        public int icon = -1;
        public String titleName = "";
    }

    /**
     * -------------曲线图表-----------------------------------------------------------------
     */
    public class CurveView extends View {

        /**
         * 线条画笔
         */
        private Paint linePaint;
        /**
         * 小圆点画笔
         */
        private Paint shapePaint;
        private Paint txtPaint;
        private Paint imgPaint;

        private int txtHeight;
        /**
         * 总大小
         */
        private float width;
        /**
         * 总高度
         */
        private float height;
        /**
         * 上下刻度,刻度*值=Y轴值
         */
        private float scale;
        /**
         * 为了居中的padding
         */
        float padding = 1;

        public CurveView(Context context) {
            this(context, null);
        }

        public CurveView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            shapePaint = new Paint();
            shapePaint.setColor(shapeColor);
            shapePaint.setAntiAlias(true);
            shapePaint.setDither(true);
            shapePaint.setStrokeWidth(lineSize);

            linePaint = new Paint();
            linePaint.setColor(lineColor);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(shapeSize / 2);

            txtPaint = new Paint();
            txtPaint.setColor(txtColor);
            txtPaint.setTextSize(txtSize);
            txtPaint.setAntiAlias(true);

            imgPaint = new Paint();


        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (shapeSize > lineSize) {
                padding = (float) (shapeSize / 2.0);
            } else {
                padding = (float) (lineSize / 2.0);
            }
            itemWidthChat = (parentWidth - marginLeft - marginRight) / eachScreenTotal;
            this.width = itemWidthChat * (chartBeen.size() - 1) + marginLeft + marginRight + padding * 2;
            this.height = parentHeight;//
            setMeasuredDimension((int) width, (int) height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (chartBeen != null) {
                shapePaint.setColor(shapeColor);
                shapePaint.setStrokeWidth(shapeSize);

                linePaint.setColor(lineColor);
                linePaint.setStrokeWidth(lineSize);

                txtPaint.setColor(txtColor);
                txtPaint.setTextSize(txtSize);

                txtHeight = FormatUtils.getFontHeight(txtPaint);

                drawDatas(canvas);
            }

        }

        /**
         * 画数据
         *
         * @param canvas
         */
        private void drawDatas(Canvas canvas) {
            float headHeight = 0;
            float marginWidth = (marginLeft + marginRight) / 2;
            float marginHeight = (marginBottom + marginTop) / 2;
            if (topBeen != null && topBeen.size() > 0) {
                headHeight += drawHead(canvas, marginWidth, topBeen);
            }
            if (bottomBeen != null && bottomBeen.size() > 0) {
                headHeight += drawFoot(canvas, marginWidth, topBeen);
            }
            float chartHeight = this.height - headHeight;
            scale = getScale(chartHeight);
            float min = getMinValue();
            Point[] linePoints = new Point[chartBeen.size()];
            for (int i = 0; i < chartBeen.size(); i++) {
                ChartBean arcBean = chartBeen.get(i);
                float x = 0, y = 0;
                x = itemWidthChat * i + marginWidth;
                y = chartHeight - (arcBean.index - min) * scale - marginHeight;

                drawShape(canvas, x, y - padding * 2);

                Point point = new Point();
                point.x = (int) (x + padding);//线条在圆点宽度居中
                point.y = (int) (y - padding);//圆点高度居中
                linePoints[i] = point;

            }
            drawSingleCurve(canvas, linePoints);

        }

        /**
         * 画单根曲线
         */
        private void drawSingleCurve(Canvas canvas, Point[] points) {
            Point startPoint;
            Point endPoint;
            for (int i = 0; i < points.length - 1; i++) {
                startPoint = points[i];
                endPoint = points[i + 1];
                int wt = (startPoint.x + endPoint.x) / 2;
                Point p1 = new Point();
                Point p2 = new Point();
                p1.y = startPoint.y;
                p1.x = wt;
                p2.y = endPoint.y;
                p2.x = wt;

                Path path = new Path();
                path.moveTo(startPoint.x, startPoint.y);
                path.cubicTo(p1.x, p1.y, p2.x, p2.y, endPoint.x, endPoint.y);//曲线
                canvas.drawPath(path, linePaint);
            }
        }

        /**
         * 画多曲线
         */
        private void drawDoubleCurve(Canvas canvas, List<Point[]> pointsList) {
            Point startPoint;
            Point endPoint;
            for (Point[] points : pointsList) {
                for (int i = 0; i < points.length - 1; i++) {
                    startPoint = points[i];
                    endPoint = points[i + 1];
                    int wt = (startPoint.x + endPoint.x) / 2;
                    Point p1 = new Point();
                    Point p2 = new Point();
                    p1.y = startPoint.y;
                    p1.x = wt;
                    p2.y = endPoint.y;
                    p2.x = wt;

                    Path path = new Path();
                    path.moveTo(startPoint.x, startPoint.y);
                    path.cubicTo(p1.x, p1.y, p2.x, p2.y, endPoint.x, endPoint.y);//曲线
                    canvas.drawPath(path, linePaint);
                }
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
         * 画头
         *
         * @param canvas
         */
        private float drawHead(Canvas canvas, float marginWidth, List<List<ParamsBean>> headParams) {
            float lineCount = 0;//第N行Y值
            float x = 0, y = 0;
            boolean flag = true;
            for (int i = 0; i < headParams.size(); i++) {
                for (int j = 0; j < headParams.get(i).size(); j++) {

                    ParamsBean bean = headParams.get(i).get(j);

                    x = itemWidthChat * j + marginWidth;
                    y = txtHeight * i + space;//第几行的Y值

                    String titleName = bean.titleName;
                    int iconId = bean.icon;
                    if (!TextUtils.isEmpty(titleName)) {
                        drawTxt(canvas, x, y, titleName);
                    }
                    if (iconId != -1) {
                        if (flag) {
                            lineCount++;
                            flag = false;
                        }
                        drawImg(canvas, x, y + txtHeight + space, iconId);
                    }
                }
            }
            lineCount += headParams.size();
            return lineCount * txtHeight + lineCount * space;
        }

        /**
         * 画尾
         *
         * @param canvas
         */
        private float drawFoot(Canvas canvas, float marginWidth, List<List<ParamsBean>> headParams) {
            float lineCount = 0;//第N行Y值
            float x = 0, y = 0;
            boolean flag = true;
            float imgHeight = 0;
            for (int i = headParams.size() - 1; i >= 0; i--) {
                flag = true;
                for (int j = 0; j < headParams.get(i).size(); j++) {

                    ParamsBean bean = headParams.get(i).get(j);

                    x = itemWidthChat * j + marginWidth;
                    y = height - (txtHeight * i + space);//第几行的Y值

                    String titleName = bean.titleName;
                    int iconId = bean.icon;
                    if (!TextUtils.isEmpty(titleName)) {
                        drawTxt(canvas, x, y, titleName);
                    }
                    if (iconId != -1) {
                        if (flag) {
                            lineCount++;
                            flag = false;
                        }
                        imgHeight = drawImg(canvas, x, y + txtHeight + space, iconId);
                    }
                }
            }
            return headParams.size() * txtHeight + headParams.size() * space + lineCount * imgHeight;
        }

        /**
         * 画文字
         *
         * @param canvas
         * @param x
         * @param y
         * @param txt
         */
        private void drawTxt(Canvas canvas, float x, float y, String txt) {
            int txtWidth = FormatUtils.getFontWidth(txtPaint, txt);
            x += padding - txtWidth / 2;//居中显示
            canvas.drawText(txt, x, y, txtPaint);
        }

        /**
         * 画图片
         *
         * @param canvas
         * @param x
         * @param y
         * @param iconId
         */
        private float drawImg(Canvas canvas, float x, float y, int iconId) {
            Bitmap bitmap = ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), iconId, imgSize, imgSize);
            x += padding - bitmap.getWidth() / 2;
            canvas.drawBitmap(bitmap, x, y, imgPaint);
            return bitmap.getHeight();
        }

        /**
         * 获取刻度
         *
         * @return
         */
        private float getScale(float height) {
            float min = getMinValue();
            float max = getMaxValue();
            float scale = (height - padding * 2 - marginTop - marginBottom) / (max - min);
            return scale;
        }
    }
}
