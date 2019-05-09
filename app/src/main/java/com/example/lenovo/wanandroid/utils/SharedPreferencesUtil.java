package com.example.lenovo.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;

public class SharedPreferencesUtil {

    private static SharedPreferences.Editor editor;

    //添加ui模式
    public static void addModeUI(Context context,boolean bool){
        editor = context.getSharedPreferences("mode",Context.MODE_PRIVATE).edit();
        editor.putBoolean("mode_ui",bool);
        editor.commit();
    }

    /**
     * 是否是夜间模式
     * @param context
     * @return
     */
    public static int getNight(Context context){
        boolean bool = context.getSharedPreferences("mode",Context.MODE_PRIVATE).getBoolean("mode_ui",false);
        return bool ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
    }

}
