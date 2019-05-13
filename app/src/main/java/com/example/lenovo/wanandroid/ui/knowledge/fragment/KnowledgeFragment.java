package com.example.lenovo.wanandroid.ui.knowledge.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseFragment;
import com.example.lenovo.wanandroid.interfaces.knowledge.KnowledgeContract;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowLedgeListBean;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowledgeBean;
import com.example.lenovo.wanandroid.presenter.knowledge.KnowledgePresenter;
import com.example.lenovo.wanandroid.ui.knowledge.acrivity.KnowActivity;
import com.example.lenovo.wanandroid.ui.knowledge.adapter.KnowAdapter;
import com.example.lenovo.wanandroid.utils.CircularAnimUtil;
import com.example.lenovo.wanandroid.utils.HindMain;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class KnowledgeFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContract.KnowView {


    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    @BindView(R.id.main_sml)
    SmartRefreshLayout mainSml;
    @BindView(R.id.btn_main)
    FloatingActionButton btnMain;
    Unbinder unbinder1;
    private List<KnowledgeBean.DataBean> list;
    private KnowAdapter knowAdapter;
    private int mPos;

    @Override
    protected KnowledgePresenter createpresenter() {
        return new KnowledgePresenter();
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        knowAdapter = new KnowAdapter(list, getContext());
        rv.setAdapter(knowAdapter);
        //点击悬浮按钮回到顶部并显示隐藏的toolbar与底部导航栏

        TabLayout tab = getActivity().findViewById(R.id.tabLayout);
//        FloatingActionButton flb = getActivity().findViewById(R.id.main_ft);
        HindMain.hind(rv, tab, btnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.smoothScrollToPosition(0);
                getActivity().findViewById(R.id.toolBar).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
            }
        });
//        initRecy();

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initData() {
        presenter.showKnowBean();
    }

    @Override
    public void showKnowBean(final KnowledgeBean knowledgeBean) {
        knowAdapter.addData(knowledgeBean.getData());
        knowAdapter.OnClick(new KnowAdapter.OnItemClick() {
            @Override
            public void OnItem(int pos) {
                mPos = pos;
                Intent intent = new Intent(getContext(), KnowActivity.class);
                intent.setClass(getContext(), KnowActivity.class);
                intent.putExtra("pos", mPos);
                intent.putExtra("title", knowledgeBean.getData().get(mPos).getName());
                CircularAnimUtil.startActivity(getActivity(), intent, rv, R.color.colorAccent);
            }
        });
    }

    @Override
    public void showKnowListBean(KnowLedgeListBean knowLedgeListBean, int page, int cid) {

    }
}
