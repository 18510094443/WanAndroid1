package com.example.lenovo.wanandroid.presenter.knowledge;

import com.example.lenovo.wanandroid.base.BasePresenter;
import com.example.lenovo.wanandroid.component.CommonSubscriber;
import com.example.lenovo.wanandroid.interfaces.knowledge.KnowledgeContract;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowLedgeListBean;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowledgeBean;
import com.example.lenovo.wanandroid.model.http.HttpManger;
import com.example.lenovo.wanandroid.utils.RxUtils;

public class KnowledgePresenter extends BasePresenter<KnowledgeContract.KnowView> implements KnowledgeContract.KnowPresenter {
    @Override
    public void showKnowBean() {
        addSubscribe(HttpManger.getKnowledgeServer().getKnowBean()
        .compose(RxUtils.<KnowledgeBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<KnowledgeBean>(view) {
            @Override
            public void onNext(KnowledgeBean knowledgeBean) {
                view.showKnowBean(knowledgeBean);
            }
        }));
    }

    @Override
    public void showKnowListBean(final int page, final int cid) {
        addSubscribe(HttpManger.getKnowledgeServer().getKnowListBean(page,cid)
        .compose(RxUtils.<KnowLedgeListBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<KnowLedgeListBean>(view) {
            @Override
            public void onNext(KnowLedgeListBean knowLedgeListBean) {
                view.showKnowListBean(knowLedgeListBean,page,cid);
            }
        }));
    }
}
