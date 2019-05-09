package com.example.lenovo.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
    public static SharePreferenceUtil spUtil;
    private static String SP_NAME = "mysp";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sp;

    private SharePreferenceUtil(){}
    public static SharePreferenceUtil getInstance() {
        if(spUtil==null){
            synchronized (SharePreferenceUtil.class){
                if (spUtil == null) {
                    spUtil = new SharePreferenceUtil();
                }
            }
        }
        return spUtil;
    }
    /**
     * 创建sp储存文件
     */
    public static void createSp(Context context){
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 获取存到SP里面的数据
     */
    public static Object get(String key,Object defaultObject){
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }
    /**
     * 把数据存放到sp存储文件里面
     */
    public static void put(String key,Object object){
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

    }
    /**
     * 提交sp
     * */
    public static void spCommit(){
        editor.commit();
    }
}
