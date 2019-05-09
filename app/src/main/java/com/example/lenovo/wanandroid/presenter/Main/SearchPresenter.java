package com.example.lenovo.wanandroid.presenter.Main;

import com.example.lenovo.wanandroid.base.BasePresenter;
import com.example.lenovo.wanandroid.component.CommonSubscriber;
import com.example.lenovo.wanandroid.interfaces.Main.SearchContract;
import com.example.lenovo.wanandroid.model.bean.main.SearchBean;
import com.example.lenovo.wanandroid.model.http.HttpManger;
import com.example.lenovo.wanandroid.utils.RxUtils;

public class SearchPresenter extends BasePresenter<SearchContract.SearchView> implements SearchContract.SearchPresenter {
    @Override
    public void showSearch() {
        addSubscribe(HttpManger.getMainServer().getSearch()
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<SearchBean>(view) {
            @Override
            public void onNext(SearchBean searchBean) {
                view.showSearch(searchBean);
            }
        }));
    }
}
