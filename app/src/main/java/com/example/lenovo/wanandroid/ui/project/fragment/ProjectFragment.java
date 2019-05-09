package com.example.lenovo.wanandroid.ui.project.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseFragment;
import com.example.lenovo.wanandroid.interfaces.project.ProjectContract;
import com.example.lenovo.wanandroid.model.bean.project.ProjectBean;
import com.example.lenovo.wanandroid.model.bean.project.ProjectListBean;
import com.example.lenovo.wanandroid.presenter.project.ProjectPresenter;
import com.example.lenovo.wanandroid.ui.project.adapter.FragmentAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.ProjectView {


    Unbinder unbinder;
    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected ProjectPresenter createpresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initData() {
        presenter.showProjectBean();
    }

    @Override
    public void showProjectBean(ProjectBean projectBean) {
        final List<ProjectBean.DataBean> data = projectBean.getData();

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            fragments.add(new ProjectListFragment(data.get(i).getId()));
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(),data,fragments);
        vp.setAdapter(fragmentAdapter);
        tab.setViewPager(vp);
        tab.notifyDataSetChanged();
    }

    @Override
    public void showProjectListBean(ProjectListBean projectListBean, int page, int cid) {

    }
}
