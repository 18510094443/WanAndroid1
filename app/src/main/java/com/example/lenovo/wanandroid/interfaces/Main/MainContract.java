package com.example.lenovo.wanandroid.interfaces.Main;

import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.model.bean.main.BannerBean;
import com.example.lenovo.wanandroid.model.bean.main.ChangBean;
import com.example.lenovo.wanandroid.model.bean.main.ListBean;
import com.example.lenovo.wanandroid.model.bean.main.SearchBean;

public interface MainContract {
    interface MainView extends IBaseView{
        void showBanner(BannerBean bannerBean);
        void showList(ListBean listBean,int page);
        void showChang(ChangBean changBean);
    }

    interface MainPresenter extends IBasePresenter<MainView>{
        void showBanner();
        void showList(int page);
        void showChang();
    }
}
