package com.example.lenovo.wanandroid.ui.knowledge.acrivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.interfaces.knowledge.KnowledgeContract;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowLedgeListBean;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowledgeBean;
import com.example.lenovo.wanandroid.presenter.knowledge.KnowledgePresenter;
import com.example.lenovo.wanandroid.ui.knowledge.adapter.KnowListBeanAdapter;
import com.example.lenovo.wanandroid.ui.knowledge.fragment.KnowledgeListBeanFragment;
import com.example.lenovo.wanandroid.utils.StatusBarManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KnowActivity extends BaseActivity<KnowledgePresenter> implements KnowledgeContract.KnowView {

    @BindView(R.id.tool)
    Toolbar tool;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv)
    TextView tv;
    private int pos;

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {
        presenter.showKnowBean();
    }

    @Override
    protected void initView() {
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        StatusBarManager.setStatusBarColor(this, color);
        Intent intent = getIntent();
        pos = intent.getIntExtra("pos", 0);
        String title = intent.getStringExtra("title");
        tool.setTitle("");
        tv.setText(title);
        setSupportActionBar(tool);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected KnowledgePresenter createpresenter() {
        return new KnowledgePresenter();
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_know;
    }

    @Override
    public void showKnowBean(KnowledgeBean knowledgeBean) {
        final List<Fragment> fragments = new ArrayList<>();
        final List<KnowledgeBean.DataBean> data = knowledgeBean.getData();
        final List<KnowledgeBean.DataBean.ChildrenBean> children = data.get(pos).getChildren();
        for (int i = 0; i < children.size(); i++) {
            tab.addTab(tab.newTab().setText(children.get(i).getName()));
            fragments.add(new KnowledgeListBeanFragment(children.get(i).getId()));
        }
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return children.get(position).getName();
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tab.setupWithViewPager(vp);
    }

    @Override
    public void showKnowListBean(KnowLedgeListBean knowLedgeListBean, int page, int cid) {

    }
}
