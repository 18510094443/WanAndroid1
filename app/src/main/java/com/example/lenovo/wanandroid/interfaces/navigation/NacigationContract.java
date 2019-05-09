package com.example.lenovo.wanandroid.interfaces.navigation;

import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.model.bean.navigation.NavigationBean;

public interface NacigationContract {

    interface NacigationView extends IBaseView{
        void getNacigation(NavigationBean navigationBean);
    }

    interface NacigationPresenter extends IBasePresenter<NacigationView>{
        void getNacigation();
    }

}
