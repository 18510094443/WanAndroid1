package com.example.lenovo.wanandroid.ui.Main.fragment;


import android.app.Activity;
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

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseFragment;
import com.example.lenovo.wanandroid.interfaces.Main.MainContract;
import com.example.lenovo.wanandroid.model.bean.main.BannerBean;
import com.example.lenovo.wanandroid.model.bean.main.ChangBean;
import com.example.lenovo.wanandroid.model.bean.main.ListBean;
import com.example.lenovo.wanandroid.presenter.Main.MainPresenter;
import com.example.lenovo.wanandroid.ui.Main.acrivity.MainPageActivity;
import com.example.lenovo.wanandroid.ui.Main.adapter.MainAdapter;
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
public class MainPageFragment extends BaseFragment<MainPresenter> implements MainContract.MainView {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.main_sml)
    SmartRefreshLayout mainSml;
    Unbinder unbinder;
    @BindView(R.id.btn_main)
    FloatingActionButton btnMain;
    private MainAdapter mainAdapter;
    private int page = 0;
    private List<BannerBean.DataBean> banner_list;
    private List<ListBean.DataBean.DatasBean> list;
    private List<BannerBean.DataBean> data;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initData();
    }

    @Override
    protected MainPresenter createpresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        banner_list = new ArrayList<>();
        list = new ArrayList<>();

        mainAdapter = new MainAdapter(banner_list, list, getContext());
        rv.setAdapter(mainAdapter);

        mainSml.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    page++;
                    initData();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    page = 0;
                    mainAdapter.banner_list.clear();
                    initData();
                }
        });
        TabLayout tab = getActivity().findViewById(R.id.tabLayout);
//        FloatingActionButton flb = getActivity().findViewById(R.id.main_ft);
        HindMain.hind(rv, tab, btnMain);
        //点击悬浮按钮回到顶部并 显示隐藏的toolbar与底部导航栏
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
        return R.layout.fragment_main_page;
    }

    @Override
    protected void initData() {
        presenter.showBanner();
        presenter.showList(page);
    }

    @Override
    public void showBanner(BannerBean bannerBean) {
        data = bannerBean.getData();
        mainAdapter.addBanner(data);
    }

    @Override
    public void showList(ListBean listBean, int page) {
        final List<ListBean.DataBean.DatasBean> datas = listBean.getData().getDatas();
        mainAdapter.addData(datas);
        mainAdapter.OnClick(new MainAdapter.OnItemClick() {
            @Override
            public void OnItem(int pos) {
                Intent intent = new Intent(getContext(), MainPageActivity.class);
                intent.setClass(getContext(), MainPageActivity.class);
                intent.putExtra("title", datas.get(pos).getTitle());
                intent.putExtra("link", datas.get(pos).getLink());
                intent.putExtra("author", datas.get(pos).getAuthor());
                intent.putExtra("chapterName", datas.get(pos).getChapterName() + "/" + datas.get(pos).getSuperChapterName());
                intent.putExtra("niceDate", datas.get(pos).getNiceDate());
                CircularAnimUtil.startActivity((Activity) getContext(), intent, rv, R.color.colorAccent);
            }
        });
        mainSml.finishRefresh();
        mainSml.finishLoadMore();
    }

    @Override
    public void showChang(ChangBean changBean) {

    }

}
