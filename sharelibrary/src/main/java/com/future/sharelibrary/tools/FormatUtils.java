package com.future.sharelibrary.tools;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by chris on 2016/5/4.
 */
public class FormatUtils {

    private static FormatUtils instance;

    public static FormatUtils getInstance(){
        if(instance==null){
            instance=new FormatUtils();
        }
        return instance;
    }


    /**
     * 密度转像素
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 像素转密度
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 格式化时间
     * @param currtimeStyle
     * @param time
     * @param timeStyle
     * @return
     */
    public static String getFormatTime(String currtimeStyle, String time, String timeStyle) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(currtimeStyle);
            java.util.Date date = format.parse(time);
            return new SimpleDateFormat(timeStyle, Locale.getDefault()).format(date);
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * 修剪浮点类型
     * @param value value
     * @param rules 规则(如:0.00保留2位小数)
     * @return string or "" or value
     */
    public static String getTrim(String value, String rules)
    {
        if(value == null || value.length() == 0 || rules == null || rules.length() == 0)
        {
            return "";
        }
        try
        {
            return getTrim(Double.parseDouble(value), rules);
        }
        catch(Exception e)
        {
            return value;
        }
    }

    /**
     * 修剪浮点类型
     * @param value
     * @param rules
     * @return
     */
    public static String getTrim(double value, String rules) {
        DecimalFormat df = new DecimalFormat(rules);
        return df.format(value);
    }
}
