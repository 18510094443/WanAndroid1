package com.example.lenovo.wanandroid.ui.wx.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseFragment;
import com.example.lenovo.wanandroid.interfaces.wx.WxContract;
import com.example.lenovo.wanandroid.model.bean.wx.WxListBean;
import com.example.lenovo.wanandroid.model.bean.wx.WxTabBean;
import com.example.lenovo.wanandroid.presenter.wx.WxPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WxFragment extends BaseFragment<WxPresenter> implements WxContract.WxView {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    Unbinder unbinder;

    @Override
    protected WxPresenter createpresenter() {
        return new WxPresenter();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_wx;
    }

    @Override
    protected void initData() {
        presenter.getWxTab();
    }

    @Override
    public void getWxTab(WxTabBean wxTabBean) {
        final List<WxTabBean.DataBean> data = wxTabBean.getData();
        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            tab.addTab(tab.newTab().setText(data.get(i).getName()));
            fragments.add(new WxListFragment(data.get(i).getId(),data.get(i).getName()));
        }
        vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
        });

        tab.setupWithViewPager(vp);
    }

    @Override
    public void getWxList(WxListBean wxListBean, int id, int page) {

    }

    @Override
    public void getSearch(WxListBean wxListBean, int id, int page, String k) {

    }
}
