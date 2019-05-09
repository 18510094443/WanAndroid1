package com.example.lenovo.wanandroid.ui.project.fragment;


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
import com.example.lenovo.wanandroid.interfaces.project.ProjectContract;
import com.example.lenovo.wanandroid.model.bean.project.ProjectBean;
import com.example.lenovo.wanandroid.model.bean.project.ProjectListBean;
import com.example.lenovo.wanandroid.presenter.project.ProjectPresenter;
import com.example.lenovo.wanandroid.ui.Main.acrivity.MainPageActivity;
import com.example.lenovo.wanandroid.ui.project.acrivity.ProjectActivity;
import com.example.lenovo.wanandroid.ui.project.adapter.ProjectAdapter;
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
@SuppressLint("ValidFragment")
public class ProjectListFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.ProjectView {


    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    Unbinder unbinder;
    @BindView(R.id.btn_main)
    FloatingActionButton btnMain;
    Unbinder unbinder1;
    private int page = 1;
    private int id;
    private List<ProjectListBean.DataBean.DatasBean> list;
    private ProjectAdapter projectAdapter;

    public ProjectListFragment(int id) {
        this.id = id;
    }

    @Override
    protected ProjectPresenter createpresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        projectAdapter = new ProjectAdapter(list, getContext());
        rv.setAdapter(projectAdapter);
        sml.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
//                projectAdapter.list.clear();
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                projectAdapter.list.clear();
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
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initData() {
        presenter.showProjectBean();
        presenter.showProjectListBean(page, id);
    }

    @Override
    public void showProjectBean(ProjectBean projectBean) {

    }

    @Override
    public void showProjectListBean(ProjectListBean projectListBean, int page, int cid) {
        final List<ProjectListBean.DataBean.DatasBean> datas = projectListBean.getData().getDatas();
        projectAdapter.addData(datas);

        projectAdapter.OnClick(new ProjectAdapter.OnItemClick() {
            @Override
            public void OnItem(int pos) {
                Intent intent = new Intent(getContext(), ProjectActivity.class);
                intent.setClass(getContext(),ProjectActivity.class);
                intent.putExtra("title", datas.get(pos).getTitle());
                intent.putExtra("link", datas.get(pos).getLink());
                CircularAnimUtil.startActivity((Activity) getContext(),intent,rv,R.color.colorAccent);
            }
        });

        sml.finishLoadMore();
        sml.finishRefresh();
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
