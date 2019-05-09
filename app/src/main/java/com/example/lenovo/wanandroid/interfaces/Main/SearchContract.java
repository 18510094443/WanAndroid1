package com.example.lenovo.wanandroid.interfaces.Main;

import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.model.bean.main.SearchBean;

public interface SearchContract {

    interface SearchView extends IBaseView{
        void showSearch(SearchBean searchBean);
    }

    interface SearchPresenter extends IBasePresenter<SearchView>{
        void showSearch();
    }

}
