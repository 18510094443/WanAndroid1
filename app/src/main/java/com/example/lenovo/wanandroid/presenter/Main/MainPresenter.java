package com.example.lenovo.wanandroid.presenter.Main;

import com.example.lenovo.wanandroid.base.BasePresenter;
import com.example.lenovo.wanandroid.component.CommonSubscriber;
import com.example.lenovo.wanandroid.interfaces.Main.MainContract;
import com.example.lenovo.wanandroid.model.bean.main.BannerBean;
import com.example.lenovo.wanandroid.model.bean.main.ChangBean;
import com.example.lenovo.wanandroid.model.bean.main.ListBean;
import com.example.lenovo.wanandroid.model.http.HttpManger;
import com.example.lenovo.wanandroid.utils.RxUtils;

public class MainPresenter extends BasePresenter<MainContract.MainView> implements MainContract.MainPresenter {
    @Override
    public void showBanner() {
        addSubscribe(HttpManger.getMainServer().getBanner()
        .compose(RxUtils.<BannerBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<BannerBean>(view) {
            @Override
            public void onNext(BannerBean bannerBean) {
                view.showBanner(bannerBean);
            }
        }));
    }

    @Override
    public void showList(final int page) {
        addSubscribe(HttpManger.getMainServer().getList(page)
        .compose(RxUtils.<ListBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<ListBean>(view) {
            @Override
            public void onNext(ListBean listBean) {
                view.showList(listBean,page);
            }
        }));
    }

    @Override
    public void showChang() {
        addSubscribe(HttpManger.getMainServer().getChang()
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<ChangBean>(view) {
            @Override
            public void onNext(ChangBean changBean) {
                view.showChang(changBean);
            }
        }));
    }
}
