package com.future.sharelibrary.widgets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.future.sharelibrary.tools.FormatUtils;
import com.future.sharelibrary.tools.SortFindUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 日历控件 功能：获得点选的日期区间
 */
public class CalendarView extends View implements View.OnTouchListener {
    private final static String TAG = "anCalendar";
    private Date selectedStartDate;
    private Date selectedEndDate;
    private Date curDate; // 当前日历显示的月
    private Date today; // 今天的日期文字显示红色
    private Date downDate; // 手指按下状态时临时日期
    private Date showFirstDate, showLastDate; // 日历显示的第一个日期和最后一个日期
    private Date nowToDay;// 当前日期
    private int downIndex; // 按下的格子索引
    private Calendar calendar;
    private Surface surface;
    private int[] date = new int[42]; // 日历显示数字
    private int curStartIndex, curEndIndex; // 当前显示的日历起始的索引
    private boolean completed = false; // 为false表示只选择了开始日期，true表示结束日期也选择了
    private boolean isSelectMore = false;
    private Boolean touchFlag = false;
    // 加点
    private List<LinkedList<Long>> competitionDate;
    private List<ArrayList<Integer>> mothList;
    private int nowDateIndex = 0;
    private int nextDateIndex = 0;
    private int titleColor;
    private int backGroundColor;
    private int selectorColor;
    private int selectorFocusColor;
    private int txtColor;
    private int nowColor;

    // 给控件设置监听事件
    private OnItemSingleClickListener onItemSingleClickListener;
    private OnCalendarLoadedListener onCalendarLoadedListener;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        nowColor = Color.parseColor("#FF4081");
        titleColor = Color.parseColor("#80000000");
        backGroundColor = Color.WHITE;
        selectorColor = Color.parseColor("#01465d");
        selectorFocusColor = Color.parseColor("#50000000");
        txtColor = Color.BLACK;
        nowToDay = new Date();
        competitionDate = new ArrayList<>();
        mothList = new ArrayList<>();
        curDate = selectedStartDate = selectedEndDate = today = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        surface = new Surface();
        surface.density = getResources().getDisplayMetrics().density;
        setBackgroundColor(backGroundColor);
        setOnTouchListener(this);
    }

    /**
     * 总共应该分为三行 1：当前月份的上一个月。2：当前月份。3：当前月份的下一个月
     *
     * @param competitionDate
     */
    public void setCompetitionDate(List<LinkedList<Long>> competitionDate) {
        this.competitionDate = competitionDate;
        new SortThread(competitionDate).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        surface.width = getResources().getDisplayMetrics().widthPixels;
        surface.height = getResources().getDisplayMetrics().heightPixels * 2 / 5;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(surface.width,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(surface.height,
                MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        Log.d(TAG, "[onLayout] changed:"
                + (changed ? "new size" : "not change") + " left:" + left
                + " top:" + top + " right:" + right + " bottom:" + bottom);
        if (changed) {
            surface.init();
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
//      //画框
//         canvas.drawPath(surface.boxPath, surface.borderPaint);

        // 画title下的分割线
//        canvas.drawLine(0, surface.weekHeight, surface.width,surface.weekHeight, surface.weekBorderBottomPaint);
        // 画星期
        float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;

        for (int i = 0; i < surface.weekText.length; i++) {
            float weekTextX = i * surface.cellWidth + (surface.cellWidth - surface.weekPaint.measureText(surface.weekText[i])) / 2f;
            canvas.drawText(surface.weekText[i], weekTextX, weekTextY, surface.weekPaint);
        }
        // 计算日期
        calculateDate();
        // 按下状态，选择状态背景色
        int[] section = drawDownOrSelectedBg(canvas);
//        画月份
//        if(downDate==null){
//        }
//        String currentDate = FormatUtils.getInstance().getFormatDate(downDate, "yyyy-MM-dd");
//        int monthX = (surface.width / 2) - (FormatUtils.getFontWidth(surface.weekPaint, currentDate) / 2);
//        int monthY = (int) ((surface.monthHeight))-(FormatUtils.getFontHeight(surface.weekPaint)/2);
//        canvas.drawText(currentDate, monthX, monthY, surface.weekPaint);
        // write date number
        // today index
        int todayIndex = -1;
        calendar.setTime(curDate);
        String curYearAndMonth = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH);
        calendar.setTime(today);
        String todayYearAndMonth = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH);
        if (curYearAndMonth.equals(todayYearAndMonth)) {
            int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
            todayIndex = curStartIndex + todayNumber - 1;
        }
        for (int i = 0; i < 42; i++) {
            int color = surface.textColor;
            if (isLastMonth(i)) {//上一个月颜色
                color = surface.borderColor;
            } else if (isNextMonth(i)) {//下一个月颜色
                color = surface.borderColor;
            }
            if (i >= section[0] && i <= section[1]) {//选中的范围
                color = backGroundColor;
            }
            if (todayIndex != -1 && i == todayIndex) {
                color = surface.todayNumberColor;
            }
            drawCellText(canvas, i, date[i] + "", color);
        }

        super.onDraw(canvas);
    }

    private void calculateDate() {
        calendar.setTime(curDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // System.out.println(cal.get(Calendar.YEAR) + "年" +
        // (cal.get(Calendar.MONTH)+1) + "月" + cal.get(Calendar.DAY_OF_MONTH) +
        // "日");
        // System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        // //输出当前日期对象cal是当前月份的第几天

        int monthStart = dayInWeek;
        if (monthStart == 1) {
            monthStart = 8;
        }
        monthStart -= 1; // 以日为开头-1，以星期一为开头-2
        curStartIndex = nowDateIndex = monthStart;
        date[monthStart] = 1;
        // last month
        if (monthStart > 0) {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
            for (int i = monthStart - 1; i >= 0; i--) {
                date[i] = dayInmonth;
                dayInmonth--;
            }
            calendar.set(Calendar.DAY_OF_MONTH, date[0]);
        }
        showFirstDate = calendar.getTime();
        // this month
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        // Log.d(TAG, "m:" + calendar.get(Calendar.MONTH) + " d:" +
        // calendar.get(Calendar.DAY_OF_MONTH));
        int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
        nextDateIndex = monthDay + nowDateIndex;
        for (int i = 1; i < monthDay; i++) {
            date[monthStart + i] = i + 1;
        }
        curEndIndex = monthStart + monthDay;
        // next month
        for (int i = curEndIndex; i < 42; i++) {
            date[i] = i - curEndIndex + 1;
        }
        if (curEndIndex < 42) {
            // 显示了下一月的
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendar.set(Calendar.DAY_OF_MONTH, date[41]);
        showLastDate = calendar.getTime();
        if (!touchFlag) {
            touchFlag = true;
            if (onCalendarLoadedListener != null)
                onCalendarLoadedListener.OnCalendarLoaded(showFirstDate, showLastDate, curDate);
        }
//        printfDate();
    }

    private void printfDate() {
        Log.i(TAG, "lastDate:");
        for (int i = 0; i < nowDateIndex; i++) {
            Log.i(TAG, date[i] + "");
        }
        Log.i(TAG, "nowDate:");
        for (int i = nowDateIndex; i < nextDateIndex; i++) {
            Log.i(TAG, date[i] + "");
        }
        Log.i(TAG, "nextDate:");
        for (int i = nextDateIndex; i < 42; i++) {
            Log.i(TAG, date[i] + "");
        }
    }

    /**
     * @param canvas
     * @param index
     * @param text
     */
    private void drawCellText(Canvas canvas, int index, String text, int color) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.datePaint.setColor(color);
        float cellY = surface.monthHeight + surface.weekHeight + (y - 1) * surface.cellHeight + surface.cellHeight / 1.5f;
        float cellX = (surface.cellWidth * (x - 1)) + (surface.cellWidth - surface.datePaint.measureText(text)) / 2f;
        canvas.drawText(text, cellX, cellY, surface.datePaint);
        if (null != mothList && mothList.size() >= 3) {
            if (index < nowDateIndex) {
                if (null != competitionDate.get(0) && competitionDate.get(0).size() > 0) {
                    handleFindExist(index, competitionDate.get(0).get(0), canvas, mothList.get(0));
                }
            } else if (index < nextDateIndex) {
                if (null != competitionDate.get(1) && competitionDate.get(1).size() > 0) {
                    handleFindExist(index, competitionDate.get(1).get(0), canvas, mothList.get(1));
                }
            } else {
                if (null != competitionDate.get(2) && competitionDate.get(2).size() > 0) {
                    handleFindExist(index, competitionDate.get(2).get(0), canvas, mothList.get(2));
                }
            }
        }
    }

    /**
     * @param index
     * @param time   时间戳
     * @param canvas
     * @param moths  月份列表
     */
    private void handleFindExist(int index, long time, Canvas canvas, ArrayList<Integer> moths) {
        if (SortFindUtil.dichotomization(moths, date[index])) {//查找
            String date = FormatUtils.getInstance().getFormatDate(time, "yyyy-MM-dd hh:mm:ss")
                    .substring(0, 8) + this.date[index] + " 23:59:59";
            if (compareDate(date)) {// nowToDay之前
                drawBitDots(canvas, index, surface.dayBottomDotColor);
            } else {
                drawBitDots(canvas, index, Color.parseColor("#80FF0000"));
            }
        }
    }

    private Boolean compareDate(String date) {
        Boolean flag = false;
        Date d = FormatUtils.getInstance().getFormatDateStr(date);
        if (d.before(nowToDay)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 画有任务日期的小红点
     *
     * @param canvas
     * @param index
     * @param color
     */
    private void drawBitDots(Canvas canvas, int index, int color) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.cellBgPaint.setColor(color);
        float left = surface.cellWidth * (x - 1) + surface.borderWidth;
        float top = surface.monthHeight + surface.weekHeight + (y - 1) * surface.cellHeight + surface.borderWidth;
        float cx = left + (surface.cellWidth - surface.borderWidth) / 2;
        float cy = top + (surface.cellHeight - surface.borderWidth) / 1.1f;
        float radius = Math.min(surface.cellWidth, surface.cellHeight) / 20;
        canvas.drawCircle(cx, cy, radius, surface.cellBgPaint);
    }

    /**
     * @param canvas
     * @param index
     * @param color
     */
    private void drawCellBg(Canvas canvas, int index, int color) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.cellBgPaint.setColor(color);
        float left = surface.cellWidth * (x - 1) + surface.borderWidth;
        float top = surface.monthHeight + surface.weekHeight + (y - 1) * surface.cellHeight + surface.borderWidth;
        float cx = left + (surface.cellWidth - surface.borderWidth) / 2;
        float cy = top + (surface.cellHeight - surface.borderWidth) / 2;
        float radius = Math.min(surface.cellWidth, surface.cellHeight) / 2;
        canvas.drawCircle(cx, cy, radius, surface.cellBgPaint);
    }

    private int[] drawDownOrSelectedBg(Canvas canvas) {
        // down and not up
        if (downDate != null) {
            drawCellBg(canvas, downIndex, surface.cellDownColor);
        }

        int[] section = new int[]{-1, -1};
        // selected bg color
        if (!selectedEndDate.before(showFirstDate) && !selectedStartDate.after(showLastDate)) {
            calendar.setTime(curDate);
            calendar.add(Calendar.MONTH, -1);
            findSelectedIndex(0, curStartIndex, calendar, section);
            if (section[1] == -1) {
                calendar.setTime(curDate);
                findSelectedIndex(curStartIndex, curEndIndex, calendar, section);
            }
            if (section[1] == -1) {
                calendar.setTime(curDate);
                calendar.add(Calendar.MONTH, 1);
                findSelectedIndex(curEndIndex, 42, calendar, section);
            }
            if (section[0] == -1) {
                section[0] = 0;
            }
            if (section[1] == -1) {
                section[1] = 41;
            }
            for (int i = section[0]; i <= section[1]; i++) {
                drawCellBg(canvas, i, surface.cellSelectedColor);
            }
        }
        return section;
    }

    private void findSelectedIndex(int startIndex, int endIndex,
                                   Calendar calendar, int[] section) {
        for (int i = startIndex; i < endIndex; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, date[i]);
            Date temp = calendar.getTime();
            // Log.d(TAG, "temp:" + temp.toLocaleString());
            if (temp.compareTo(selectedStartDate) == 0) {
                section[0] = i;
            }
            if (temp.compareTo(selectedEndDate) == 0) {
                section[1] = i;
                return;
            }
        }
    }

    public Date getSelectedStartDate() {
        return selectedStartDate;
    }

    public Date getSelectedEndDate() {
        return selectedEndDate;
    }

    private boolean isLastMonth(int i) {
        if (i < curStartIndex) {
            return true;
        }
        return false;
    }

    private boolean isNextMonth(int i) {
        if (i >= curEndIndex) {
            return true;
        }
        return false;
    }

    private int getXByIndex(int i) {
        return i % 7 + 1; // 1 2 3 4 5 6 7
    }

    private int getYByIndex(int i) {
        return i / 7 + 1; // 1 2 3 4 5 6
    }

    /**
     * 获得当前显示的年月
     *
     * @return
     */
    public String getYearAndMonth() {
        calendar.setTime(curDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    /**
     * 显示并返回上一月
     *
     * @return
     */
    public String showLastMonth() {
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, -1);
        curDate = calendar.getTime();
        touchFlag = false;
        invalidate();
        return getYearAndMonth();
    }

    /**
     * 显示并返回下一月
     *
     * @return
     */
    public String showNextMonth() {
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, 1);
        curDate = calendar.getTime();
        touchFlag = false;
        invalidate();
        return getYearAndMonth();
    }

    /**
     * 设置日历时间
     *
     * @param date
     */
    public void setCalendarData(Date date) {
        calendar.setTime(date);
        invalidate();
    }

    /**
     * 获取当前日历时间
     */
    public Date getCalendarDate() {
        return calendar.getTime();
    }

    /**
     * 当前是否多选
     *
     * @return
     */
    public boolean isSelectMore() {
        return isSelectMore;
    }

    /**
     * 设置是否多选
     *
     * @param isSelectMore
     */
    public void setSelectMore(boolean isSelectMore) {
        this.isSelectMore = isSelectMore;
    }

    /**
     * 设置按下的效果
     *
     * @param x
     * @param y
     */
    private void setSelectedDateByColor(float x, float y) {
        // cell click down
        if (y > surface.monthHeight + surface.weekHeight) {
            int m = (int) (Math.floor(x / surface.cellWidth) + 1);
            int n = (int) (Math.floor((y - (surface.monthHeight + surface.weekHeight)) / Float.valueOf(surface.cellHeight)) + 1);
            downIndex = (n - 1) * 7 + m - 1;
            Log.d(TAG, "downIndex:" + downIndex);
            calendar.setTime(curDate);
            if (isLastMonth(downIndex)) {
                calendar.add(Calendar.MONTH, -1);
            } else if (isNextMonth(downIndex)) {
                calendar.add(Calendar.MONTH, 1);
            }
            calendar.set(Calendar.DAY_OF_MONTH, date[downIndex]);
            downDate = calendar.getTime();
            invalidate();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setSelectedDateByColor(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (downDate != null) {
                    if (isSelectMore) {
                        if (!completed) {
                            if (downDate.before(selectedStartDate)) {
                                selectedEndDate = selectedStartDate;
                                selectedStartDate = downDate;
                            } else {
                                selectedEndDate = downDate;
                            }
                            completed = true;
                            // 响应监听事件
                            if (onItemSingleClickListener != null)
                                onItemSingleClickListener.OnItemSingleClick(selectedStartDate, selectedEndDate, downDate);
                        } else {
                            selectedStartDate = selectedEndDate = downDate;
                            completed = false;
                        }
                    } else {
                        selectedStartDate = selectedEndDate = downDate;
                        // 响应监听事件
                        if (onItemSingleClickListener != null)
                            onItemSingleClickListener.OnItemSingleClick(selectedStartDate, selectedEndDate, downDate);
                    }
                    touchFlag = true;
                    invalidate();
                }

                break;
        }
        return true;
    }

    // 给控件设置监听事件
    public void setOnItemSingleClickListener(OnItemSingleClickListener onItemSingleClickListener) {
        this.onItemSingleClickListener = onItemSingleClickListener;
    }

    // 监听接口
    public interface OnItemSingleClickListener {
        void OnItemSingleClick(Date selectedStartDate, Date selectedEndDate,
                               Date downDate);
    }

    public void setOnCalendarLoadedListener(
            OnCalendarLoadedListener onCalendarLoadedListener) {
        this.onCalendarLoadedListener = onCalendarLoadedListener;
    }

    // 加载完成监听
    public interface OnCalendarLoadedListener {
        void OnCalendarLoaded(Date fistDate, Date lastDate, Date nowDate);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            mothList = (List<ArrayList<Integer>>) msg.obj;
            if (mothList != null && mothList.size() > 0) {
                invalidate();
            }
        }

        ;
    };

    class SortThread extends Thread {

        private List<LinkedList<Long>> sortGroup;
        private List<List<Integer>> completeMothList;

        public SortThread(List<LinkedList<Long>> sortGroup) {
            this.sortGroup = sortGroup;
        }

        @Override
        public void run() {
            Message msg = new Message();
            msg.obj = sort(sortGroup);
            handler.sendMessage(msg);
            super.run();
        }

        private List<List<Integer>> sort(List<LinkedList<Long>> competitionDate) {
            completeMothList = new ArrayList<>();
            for (int i = 0; i < competitionDate.size(); i++) {
                List<Integer> moth = new ArrayList<Integer>();
                for (int k = 0; k < competitionDate.get(i).size(); k++) {
                    long time = competitionDate.get(i).get(k);
                    int day = Integer.parseInt(FormatUtils.getInstance().getFormatDate(time, "yyyy-MM-dd hh:mm:ss").substring(8, 10));
                    moth.add(day);
                }
                completeMothList.add(SortFindUtil.repeatedlyKill(SortFindUtil.sort(moth, null)));
            }
            return completeMothList;
        }
    }

    /**
     * 1. 布局尺寸 2. 文字颜色，大小 3. 当前日期的颜色，选择的日期颜色
     */
    private class Surface {
        public float density;
        public int width; // 整个控件的宽度
        public int height; // 整个控件的高度
        public float monthHeight; // 显示月的高度
        // public float monthChangeWidth; // 上一月、下一月按钮宽度
        public float weekHeight; // 显示星期的高度
        public float cellWidth; // 日期方框宽度
        public float cellHeight; // 日期方框高度
        public float borderWidth;

        private int textColor = txtColor;
        private int borderColor = Color.parseColor("#CCCCCC");
        public int todayNumberColor = nowColor;
        public int weekBottomColor = titleColor;
        public int dayBottomDotColor = titleColor;

        public int cellDownColor = selectorFocusColor;
        public int cellSelectedColor = selectorColor;

        public Paint borderPaint;
        public Paint monthPaint;
        public Paint weekPaint;
        public Paint datePaint;
        public Paint cellBgPaint;
        public Path boxPath; // 边框路径
        public Paint weekBorderBottomPaint;

        public String[] weekText = {"日", "一", "二", "三", "四", "五", "六"};

        public void init() {
            float temp = height / 7f;
            monthHeight = 0;//(float) ((temp + temp * 0.3f) * 0.6);

            weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
            cellHeight = (height - monthHeight - weekHeight) / 6f;
            cellWidth = width / 7f;
            borderPaint = new Paint();
            borderPaint.setColor(weekBottomColor);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderWidth = (float) (0.5 * density);
            borderWidth = borderWidth < 1 ? 1 : borderWidth;
            borderPaint.setStrokeWidth(borderWidth);
            monthPaint = new Paint();
            monthPaint.setColor(textColor);
            monthPaint.setAntiAlias(true);
            float textSize = cellHeight * 0.1f;
            monthPaint.setTextSize(textSize);
            monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
            weekPaint = new Paint();
            weekPaint.setColor(textColor);
            weekPaint.setAntiAlias(true);
            float weekTextSize = weekHeight * 0.5f;
            weekPaint.setTextSize(weekTextSize);
            weekPaint.setTypeface(Typeface.DEFAULT);
            datePaint = new Paint();
            datePaint.setColor(textColor);
            datePaint.setAntiAlias(true);
            float cellTextSize = cellHeight * 0.4f;
            datePaint.setTextSize(cellTextSize);
            datePaint.setTypeface(Typeface.DEFAULT);

            boxPath = new Path();
            boxPath.addRect(0, 0, width, height, Direction.CW);
            boxPath.moveTo(0, monthHeight);

            boxPath.rLineTo(width, 0);
            boxPath.moveTo(0, monthHeight + weekHeight);
            boxPath.rLineTo(width, 0);
            for (int i = 1; i < 6; i++) {
                boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);
                boxPath.rLineTo(width, 0);
                boxPath.moveTo(i * cellWidth, monthHeight);
                boxPath.rLineTo(0, height - monthHeight);
            }
            boxPath.moveTo(6 * cellWidth, monthHeight);
            boxPath.rLineTo(0, height - monthHeight);
            cellBgPaint = new Paint();
            cellBgPaint.setAntiAlias(true);
            cellBgPaint.setStyle(Paint.Style.FILL);
            cellBgPaint.setColor(cellSelectedColor);
            weekBorderBottomPaint = new Paint();
            weekBorderBottomPaint.setAntiAlias(true);
            weekBorderBottomPaint.setStyle(Paint.Style.FILL);
            weekBorderBottomPaint.setColor(weekBottomColor);
        }
    }
}
