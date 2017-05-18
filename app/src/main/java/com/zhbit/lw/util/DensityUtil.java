package com.zhbit.lw.util;

import android.content.Context;

/**
 * Created by wjh on 17-5-17.
 */

public class DensityUtil {

    // dip 转 px
    public static int dip2px(Context context, float dpValue) {
        // 获取屏幕密度
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // dip 转 px
    public static int px2dip(Context context, float pxValue) {
        // 获取屏幕密度
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
