package com.example.lenovo.wanandroid.ui.project.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lenovo.wanandroid.model.bean.project.ProjectBean;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<ProjectBean.DataBean> data;
    private List<Fragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<ProjectBean.DataBean> data, List<Fragment> fragments) {
        super(fm);
        this.data = data;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getName();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
