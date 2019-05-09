package com.example.lenovo.wanandroid.interfaces.project;

import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.model.bean.project.ProjectBean;
import com.example.lenovo.wanandroid.model.bean.project.ProjectListBean;

public interface ProjectContract {

    interface ProjectView extends IBaseView{
        void showProjectBean(ProjectBean projectBean);
        void showProjectListBean(ProjectListBean projectListBean,int page,int cid);
    }

    interface ProjectPresenter extends IBasePresenter<ProjectView>{
        void showProjectBean();
        void showProjectListBean(int page,int id);
    }



}
