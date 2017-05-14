package com.zhbit.lw.Logs;

import android.util.Log;

/**
 * Created by fl5900 on 2017/5/12.
 * 重新定义日期函数，使之能只在调试是才打开日志
 */

public class Logs {
    private static boolean IS_DEBUG = true;
    public static void e(String TAG, String MSG){
        if (IS_DEBUG){
            Log.e(TAG,MSG);
        }
    }
    public static void i(String TAG, String MSG){
        if (IS_DEBUG){
            Log.i(TAG,MSG);
        }
    }
    public static void w(String TAG, String MSG){
        if (IS_DEBUG){
            Log.w(TAG,MSG);
        }
    }
    public static void d(String TAG, String MSG){
        if (IS_DEBUG){
            Log.d(TAG,MSG);
        }
    }
}
