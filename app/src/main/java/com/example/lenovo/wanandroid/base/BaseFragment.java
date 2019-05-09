package com.example.lenovo.wanandroid.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.utils.SystemUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements IBaseView {

    protected Context context;
    protected P presenter;
    private Unbinder unbinder;
    public Activity activity;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (presenter!=null&&isVisibleToUser){
            initData();
            if (activity==null){
                activity=getActivity();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(getLayout(),null);
        if (activity==null){
            activity=getActivity();
        }
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context=getContext();
        unbinder=ButterKnife.bind(this,view);
        if (!SystemUtils.checkNetWork()){
            showNetWorkError();
        }else{
            initView(view);
            presenter=createpresenter();
            if (presenter!=null){
                presenter.attachView(this);
            }
            initData();
        }
    }

    protected abstract P createpresenter();

    protected abstract void initView(View view);

    protected abstract int getLayout();

    protected abstract void initData();


    @Override
    public void showDataError(String error) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNetWorkError() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter!=null){
            presenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.deatchView();
            presenter=null;
        }

        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
