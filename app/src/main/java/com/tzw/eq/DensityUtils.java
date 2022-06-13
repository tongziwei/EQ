package com.tzw.eq;

import android.content.Context;

public class DensityUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp单位值
     * @return 转换后的px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue px单位值
     * @return 转换后的dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getApplicationContext().getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度px
     *
     * @param context 上下文
     * @return 屏幕密度px值
     */
    public static int deviceWidthPX(Context context) {
        return context.getApplicationContext().getResources()
                .getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度px
     *
     * @param context 上下文
     * @return 屏幕高度px
     */
    public static int deviceHeightPX(Context context) {
        return context.getApplicationContext().getResources()
                .getDisplayMetrics().heightPixels;
    }

    /**
     * sp 转成为 px
     *
     * @param context 上下文
     * @param sp      sp值
     * @return 转换后的px值
     */
    public static int sp2px(Context context, int sp) {
        // metric 度量
        // density 密度
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5f);
    }

}
