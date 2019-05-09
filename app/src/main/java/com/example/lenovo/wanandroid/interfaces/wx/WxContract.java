package com.example.lenovo.wanandroid.interfaces.wx;

import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.model.bean.wx.WxListBean;
import com.example.lenovo.wanandroid.model.bean.wx.WxTabBean;

public interface WxContract {

    interface WxView extends IBaseView{
        void getWxTab(WxTabBean wxTabBean);
        void getWxList(WxListBean wxListBean,int id,int page);
        void getSearch(WxListBean wxListBean,int id,int page,String k);
    }

    interface WxPresenter extends IBasePresenter<WxView>{
        void getWxTab();
        void getWxList(int id,int page);
        void getSearch(int id,int page,String k);
    }

}
