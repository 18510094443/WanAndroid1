package com.example.lenovo.wanandroid.presenter.wx;

import com.example.lenovo.wanandroid.base.BasePresenter;
import com.example.lenovo.wanandroid.component.CommonSubscriber;
import com.example.lenovo.wanandroid.interfaces.wx.WxContract;
import com.example.lenovo.wanandroid.model.bean.wx.WxListBean;
import com.example.lenovo.wanandroid.model.bean.wx.WxTabBean;
import com.example.lenovo.wanandroid.model.http.HttpManger;
import com.example.lenovo.wanandroid.utils.RxUtils;

public class WxPresenter extends BasePresenter<WxContract.WxView> implements WxContract.WxPresenter{
    @Override
    public void getWxTab() {
        addSubscribe(HttpManger.getMainServer().getTabBean()
        .compose(RxUtils.<WxTabBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<WxTabBean>(view) {
            @Override
            public void onNext(WxTabBean wxTabBean) {
                view.getWxTab(wxTabBean);
            }
        }));
    }

    @Override
    public void getWxList(final int id, final int page) {
        addSubscribe(HttpManger.getMainServer().getListBean(id,page)
        .compose(RxUtils.<WxListBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<WxListBean>(view) {
            @Override
            public void onNext(WxListBean wxListBean) {
                view.getWxList(wxListBean,id,page);
            }
        }));
    }

    @Override
    public void getSearch(int id, int page, String k) {
        addSubscribe(HttpManger.getMainServer().getWxSearch(id,page,k)
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<WxListBean>(view) {
            @Override
            public void onNext(WxListBean wxListBean) {
                view.getSearch(wxListBean,id,page,k);
            }
        }));
    }
}
