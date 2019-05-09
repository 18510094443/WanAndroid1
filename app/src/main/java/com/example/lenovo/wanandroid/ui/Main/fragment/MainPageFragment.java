package com.example.lenovo.wanandroid.ui.Main.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    @BindView(R.id.btn_main)
    FloatingActionButton btnMain;
    Unbinder unbinder;
    private MainAdapter mainAdapter;
    private int page = 0;
    private List<BannerBean.DataBean> banner_list;
    private List<ListBean.DataBean.DatasBean> list;
    private List<BannerBean.DataBean> data;

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
                mainAdapter.banner_list.clear();
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mainAdapter.list.clear();
                mainAdapter.banner_list.clear();
                initData();
            }
        });
        //点击悬浮按钮回到顶部并显示隐藏的toolbar与底部导航栏
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.smoothScrollToPosition(0);
                getActivity().findViewById(R.id.toolBar).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
            }
        });
        initRecy();
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
                /*
                *  holder1.mainTv.setText(list.get(i).getAuthor());
            holder1.mainTv1.setText(list.get(i).getChapterName()+"/"+list.get(i).getSuperChapterName());
            holder1.mainTv3.setText(list.get(i).getTitle());
            holder1.mainTv4.setText(list.get(i).getNiceDate());*/
                Intent intent = new Intent(getContext(), MainPageActivity.class);
                intent.setClass(getContext(),MainPageActivity.class);
                intent.putExtra("title", datas.get(pos).getTitle());
                intent.putExtra("link", datas.get(pos).getLink());
                intent.putExtra("author",datas.get(pos).getAuthor());
                intent.putExtra("chapterName",datas.get(pos).getChapterName()+"/"+datas.get(pos).getSuperChapterName());
                intent.putExtra("niceDate",datas.get(pos).getNiceDate());
                CircularAnimUtil.startActivity((Activity) getContext(),intent,rv,R.color.colorAccent);
            }
        });
        mainSml.finishRefresh();
        mainSml.finishLoadMore();
    }

    @Override
    public void showChang(ChangBean changBean) {

    }

        //下拉隐藏底部导航栏
        private void initRecy(){
            rv.setOnTouchListener(new View.OnTouchListener() {
                public float mEndY;
                public float mStartY;

                @SuppressLint("RestrictedApi")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mStartY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            mEndY = event.getY();
                            float v1 = mEndY - mStartY;
                            if (v1 > 1) {
                                //我这个是在fragment中的操作 这个是获取activity中的布局
                                getActivity().findViewById(R.id.toolBar).setVisibility(View.VISIBLE);
                                getActivity().findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
                                //这个就是当前页面的头布局id
                                btnMain.setVisibility(View.VISIBLE);
                            } else if (v1 < -1) {
                                getActivity().findViewById(R.id.tabLayout).setVisibility(View.GONE);
                                getActivity().findViewById(R.id.toolBar).setVisibility(View.GONE);
                                btnMain.setVisibility(View.GONE);
                            }
                            break;
                    }
                    return gestureDetector.onTouchEvent(event);
                }
            });

        }

    GestureDetector gestureDetector = new GestureDetector(getContext(),
            new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return false;
                }
                @Override
                public void onShowPress(MotionEvent e) {
                }
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    // do something
                    return true;
                }
                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                }
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return false;
                }
            });
}
