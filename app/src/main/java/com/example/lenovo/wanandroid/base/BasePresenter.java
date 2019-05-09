package com.example.lenovo.wanandroid.base;


import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {

    protected T view;
    protected WeakReference<T> weakReference;
    protected CompositeDisposable compositeDisposable;

    @Override
    public void attachView(T view) {
        weakReference=new WeakReference<T>(view);
        this.view=weakReference.get();
    }

    @Override
    public void deatchView() {
        this.view=null;
        unSubscribe();
    }

    private void unSubscribe() {
        if (compositeDisposable!=null){
            compositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable disposable){
        if (compositeDisposable==null){
            compositeDisposable=new CompositeDisposable();
            compositeDisposable.add(disposable);
        }
    }
}
