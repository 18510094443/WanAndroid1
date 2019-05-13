package com.example.lenovo.wanandroid.base;
import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.utils.SystemUtils;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<P extends IBasePresenter> extends BasePermissionActivity implements IBaseView {

    protected AppCompatActivity appCompatActivity;
    protected Context context;
    protected Unbinder unbinder;
    protected P presenter;
    /*protected UMShareListener shareListener = new UMShareListener() {
        *//**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        *//**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(context,"成功                                        了",Toast.LENGTH_LONG).show();
        }

        *//**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         *//*
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context,"失                                            败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        *//**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context,"取消                                          了",Toast.LENGTH_LONG).show();

        }
    };*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        appCompatActivity=this;
        context=this;
        initToolbar();

        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
        if (!SystemUtils.checkNetWork()){
            showNetWorkError();
        }else{
            unbinder=ButterKnife.bind(this);
            presenter=createpresenter();
            initView();
            if (presenter!=null){
                presenter.attachView(this);
            }
            initData();
        }

    }

    protected abstract void initToolbar();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract P createpresenter();

    protected abstract int getLayout();

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter!=null){
            presenter.attachView(this);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showDataError(String error) {

    }

    @Override
    public void showNetWorkError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.deatchView();
            presenter=null;
        }
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin)&&date.before(end)){
            return true;
        }else{
            return false;
        }

    }
}
