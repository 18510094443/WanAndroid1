package com.example.lenovo.wanandroid.presenter.project;

import com.example.lenovo.wanandroid.base.BasePresenter;
import com.example.lenovo.wanandroid.component.CommonSubscriber;
import com.example.lenovo.wanandroid.interfaces.project.ProjectContract;
import com.example.lenovo.wanandroid.model.bean.project.ProjectBean;
import com.example.lenovo.wanandroid.model.bean.project.ProjectListBean;
import com.example.lenovo.wanandroid.model.http.HttpManger;
import com.example.lenovo.wanandroid.utils.RxUtils;

public class ProjectPresenter extends BasePresenter<ProjectContract.ProjectView> implements ProjectContract.ProjectPresenter {
    @Override
    public void showProjectBean() {
        addSubscribe(HttpManger.getProjectServer().getProjectBean()
        .compose(RxUtils.<ProjectBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<ProjectBean>(view) {
            @Override
            public void onNext(ProjectBean projectBean) {
                view.showProjectBean(projectBean);
            }
        }));
    }

    @Override
    public void showProjectListBean(final int page, final int id) {
        addSubscribe(HttpManger.getProjectServer().getProjectListBean(page,id)
        .compose(RxUtils.<ProjectListBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<ProjectListBean>(view) {
            @Override
            public void onNext(ProjectListBean projectListBean) {
                view.showProjectListBean(projectListBean,page,id);
            }
        }));
    }
}
