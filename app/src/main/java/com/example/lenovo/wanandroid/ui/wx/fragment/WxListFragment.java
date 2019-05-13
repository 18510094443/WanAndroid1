package com.example.lenovo.wanandroid.ui.wx.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseFragment;
import com.example.lenovo.wanandroid.interfaces.wx.WxContract;
import com.example.lenovo.wanandroid.model.bean.wx.WxListBean;
import com.example.lenovo.wanandroid.model.bean.wx.WxTabBean;
import com.example.lenovo.wanandroid.presenter.wx.WxPresenter;
import com.example.lenovo.wanandroid.ui.Main.acrivity.MainPageActivity;
import com.example.lenovo.wanandroid.ui.wx.adapter.WxAdapter;
import com.example.lenovo.wanandroid.utils.CircularAnimUtil;
import com.example.lenovo.wanandroid.utils.HindMain;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class WxListFragment extends BaseFragment<WxPresenter> implements WxContract.WxView {


    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    Unbinder unbinder;
    @BindView(R.id.search_name)
    EditText searchName;
    @BindView(R.id.bt_search)
    Button btSearch;
    @BindView(R.id.btn_main)
    FloatingActionButton btnMain;
    Unbinder unbinder1;
    private int id;
    private String name;
    private int page = 1;
    private List<WxListBean.DataBean.DatasBean> list;
    private WxAdapter wxAdapter;
    private String k;

    public WxListFragment(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    protected WxPresenter createpresenter() {
        return new WxPresenter();
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        wxAdapter = new WxAdapter(list, getContext());
        rv.setAdapter(wxAdapter);

        sml.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                wxAdapter.list.clear();
                initData();
            }
        });
        TabLayout tab = getActivity().findViewById(R.id.tabLayout);
//        FloatingActionButton flb = getActivity().findViewById(R.id.main_ft);
        HindMain.hind(rv, tab, btnMain);
        //点击悬浮按钮回到顶部并显示隐藏的toolbar与底部导航栏
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.smoothScrollToPosition(0);
                getActivity().findViewById(R.id.toolBar).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_wx_list;
    }

    @Override
    protected void initData() {
        searchName.setHint(name + "带你看干货");
        presenter.getWxList(id, page);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getSearch(id, page, searchName.getText().toString().trim());
            }
        });
    }

    @Override
    public void getWxTab(WxTabBean wxTabBean) {

    }

    @Override
    public void getWxList(final WxListBean wxListBean, int id, int page) {
        List<WxListBean.DataBean.DatasBean> datas = wxListBean.getData().getDatas();
        wxAdapter.addData(datas);
        wxAdapter.OnClick(new WxAdapter.OnItemClick() {
            @Override
            public void OnItem(int pos) {
                Intent intent = new Intent(getContext(), MainPageActivity.class);
                intent.setClass(getContext(), MainPageActivity.class);
                intent.putExtra("title", datas.get(pos).getTitle());
                intent.putExtra("link", datas.get(pos).getLink());
                intent.putExtra("author", datas.get(pos).getAuthor());
                intent.putExtra("chapterName", datas.get(pos).getChapterName() + "/" + datas.get(pos).getSuperChapterName());
                intent.putExtra("niceDate", datas.get(pos).getNiceDate());
                CircularAnimUtil.startActivity(getActivity(), intent, rv, R.color.colorPrimary);
            }
        });
        sml.finishRefresh();
        sml.finishLoadMore();
    }

    @Override
    public void getSearch(WxListBean wxListBean, int id, int page, String k) {
        wxAdapter.list.clear();
        wxAdapter.addSerarch(wxListBean.getData().getDatas());
    }
}
