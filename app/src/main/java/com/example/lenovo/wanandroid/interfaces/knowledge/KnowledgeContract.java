package com.example.lenovo.wanandroid.interfaces.knowledge;

import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowLedgeListBean;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowledgeBean;

public interface KnowledgeContract {

    interface KnowView extends IBaseView{

        void showKnowBean(KnowledgeBean knowledgeBean);
        void showKnowListBean(KnowLedgeListBean knowLedgeListBean,int page,int cid);
    }

    interface KnowPresenter extends IBasePresenter<KnowView>{
        void showKnowBean();
        void showKnowListBean(int page,int cid);
    }

}
