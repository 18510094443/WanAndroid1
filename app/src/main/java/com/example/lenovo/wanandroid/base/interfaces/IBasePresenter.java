package com.example.lenovo.wanandroid.base.interfaces;

public interface IBasePresenter<T extends IBaseView>  {

    void attachView(T view);
    void deatchView();

}
