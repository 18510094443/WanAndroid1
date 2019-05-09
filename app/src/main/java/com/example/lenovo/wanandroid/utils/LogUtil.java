package com.example.lenovo.wanandroid.utils;

import android.util.Log;

import com.example.lenovo.wanandroid.constance.Constance;

/**
 *主要内容：显示全部Log日志
 */

public class LogUtil {

    private static String TAG = "TAG";

    public static void showLog(String type,String tag){
        if(Constance.ISDISPLAY){
            if(type.equals("V")){
                Log.v(TAG,tag);
            } else if(type.equals("D")){
                Log.d(TAG,tag);
            } else if(type.equals("I")){
                Log.i(TAG,tag);
            } else if(type.equals("W")){
                Log.w(TAG,tag);
            }else if (type.equals("E")){
                Log.e(TAG,tag);
            }
        }
    }
}
