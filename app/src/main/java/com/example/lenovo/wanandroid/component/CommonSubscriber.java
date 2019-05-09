package com.example.lenovo.wanandroid.component;

import android.text.TextUtils;

import com.example.lenovo.wanandroid.base.interfaces.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    private IBaseView view;
    private String errorMsg;
    private boolean isShowErrorState = false;

    public CommonSubscriber(IBaseView view) {
        this.view = view;
    }

    public CommonSubscriber(IBaseView view, String errorMsg) {
        this.view = view;
        this.errorMsg = errorMsg;
    }

    public CommonSubscriber(IBaseView view, String errorMsg, boolean isShowErrorState) {
        this.view = view;
        this.errorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onError(Throwable t) {
        if (view==null){
            return;
        }
        if (errorMsg!=null && TextUtils.isEmpty(errorMsg)){
            view.showDataError(errorMsg);
        }
    }

    @Override
    public void onComplete() {

    }
}
