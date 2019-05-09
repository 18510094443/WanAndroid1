package com.example.lenovo.wanandroid.constance;

import com.example.lenovo.wanandroid.app.MyApp;

import java.io.File;

public class Constance {
    //网络缓存的地址
    public static final String PATH_DATA = MyApp.app.getCacheDir().getAbsolutePath() + File.separator + "data";
    //缓存文件
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    public static final String STR_WWW = "http://www.wanandroid.com/";
    public static final String STR_API = "http://api.zgmsbweb.com/";
    public static boolean ISDISPLAY = true;
    public static String V = "V";
    public static String D = "D";
    public static String E = "E";
    public static String I = "I";
    public static String W = "W";

}
