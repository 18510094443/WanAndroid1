package com.example.lenovo.wanandroid.ui.navigation.fragment;


import android.graphics.Color;
import android.os.Bundle;
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
import com.example.lenovo.wanandroid.interfaces.navigation.NacigationContract;
import com.example.lenovo.wanandroid.model.bean.navigation.NavigationBean;
import com.example.lenovo.wanandroid.presenter.navigation.NavigationPresenter;
import com.example.lenovo.wanandroid.ui.navigation.adapter.NavigationAdapter;
import com.example.lenovo.wanandroid.utils.HindMain;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NacigationContract.NacigationView {


    @BindView(R.id.tablayout)
    VerticalTabLayout tablayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    @BindView(R.id.btn_main)
    FloatingActionButton btnMain;
    Unbinder unbinder1;
    private boolean move;
    private int mIndex;
    private LinearLayoutManager layoutManager;
    private List<NavigationBean.DataBean> list;
    private NavigationAdapter navigationAdapter;
    private boolean isScroll;

    @Override
    protected NavigationPresenter createpresenter() {
        return new NavigationPresenter();
    }

    @Override
    protected void initView(View view) {
        layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        navigationAdapter = new NavigationAdapter(list, getContext());

        rv.setAdapter(navigationAdapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //在这里进行第二次滚动（最后的100米！）
                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - layoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < rv.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = rv.getChildAt(n).getTop();
                        //最后的移动
                        rv.scrollBy(0, top);
                    }
                }
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
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initData() {
        presenter.getNacigation();
    }

    @Override
    public void getNacigation(NavigationBean navigationBean) {
        tablayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(position, 0);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        tablayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                moveToPosition(position);
                mIndex = position;
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //重写该方法主要是判断recyclerview是否在滑动
                //0停止 ，1,2都是滑动
                if (newState == 0) {
                    isScroll = false;
                } else {
                    isScroll = true;
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                //可见的第一条条目
                int top = layoutManager.findFirstVisibleItemPosition();
                tablayout.setTabSelected(top);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //这个主要是recyclerview滑动时让tab定位的方法

                                               /*recyclerView : 当前滚动的view
                                dx : 水平滚动距离
                                dy : 垂直滚动距离
                                dx > 0 时为手指向左滚动,列表滚动显示右面的内容
                                dx < 0 时为手指向右滚动,列表滚动显示左面的内容
                                dy > 0 时为手指向上滚动,列表滚动显示下面的内容
                                dy < 0 时为手指向下滚动,列表滚动显示上面的内容*/
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                //可见的第一条条目
                int top = layoutManager.findFirstVisibleItemPosition();
                //可见的最后一条条目
                int bottom = layoutManager.findLastVisibleItemPosition();
                if (isScroll) {
                    if (dy > 0) {
                        tablayout.setTabSelected(top);
                    }
                }
            }
        });

        List<NavigationBean.DataBean> data = navigationBean.getData();
        for (int i = 0; i < data.size(); i++) {
            tablayout.addTab(new QTabView(getContext()));
        }

        tablayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new QTabView.TabTitle.Builder()
                        .setContent(data.get(position).getName())
                        .setTextColor(Color.BLUE, Color.BLACK)
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }


        });

        navigationAdapter.addData(data);


    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            rv.scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = rv.getChildAt(n - firstItem).getTop();
            rv.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            rv.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }

    }
}
