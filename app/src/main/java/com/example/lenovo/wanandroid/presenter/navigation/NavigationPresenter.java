package com.example.lenovo.wanandroid.presenter.navigation;

import com.example.lenovo.wanandroid.base.BasePresenter;
import com.example.lenovo.wanandroid.component.CommonSubscriber;
import com.example.lenovo.wanandroid.interfaces.navigation.NacigationContract;
import com.example.lenovo.wanandroid.model.bean.navigation.NavigationBean;
import com.example.lenovo.wanandroid.model.http.HttpManger;
import com.example.lenovo.wanandroid.utils.RxUtils;

public class NavigationPresenter extends BasePresenter<NacigationContract.NacigationView> implements NacigationContract.NacigationPresenter {
    @Override
    public void getNacigation() {
        addSubscribe(HttpManger.getMainServer().getNavigation()
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<NavigationBean>(view) {
            @Override
            public void onNext(NavigationBean navigationBean) {
                view.getNacigation(navigationBean);
            }
        }));
    }
}
