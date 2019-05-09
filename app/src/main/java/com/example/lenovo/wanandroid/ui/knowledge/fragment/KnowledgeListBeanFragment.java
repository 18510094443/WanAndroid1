package com.example.lenovo.wanandroid.ui.knowledge.fragment;


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
import com.example.lenovo.wanandroid.interfaces.knowledge.KnowledgeContract;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowLedgeListBean;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowledgeBean;
import com.example.lenovo.wanandroid.presenter.knowledge.KnowledgePresenter;
import com.example.lenovo.wanandroid.ui.Main.acrivity.MainPageActivity;
import com.example.lenovo.wanandroid.ui.knowledge.acrivity.KnowledgeActivity;
import com.example.lenovo.wanandroid.ui.knowledge.adapter.KnowListBeanAdapter;
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
public class KnowledgeListBeanFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContract.KnowView {


    @BindView(R.id.btn_main)
    FloatingActionButton btnMain;
    Unbinder unbinder1;
    private int id;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    Unbinder unbinder;
    private int page = 0;
    private List<KnowledgeBean.DataBean> dataBeans;
    private List<KnowLedgeListBean.DataBean.DatasBean> list;
    private KnowListBeanAdapter knowListBeanAdapter;

    public KnowledgeListBeanFragment(int id) {
        // Required empty public constructor
        this.id = id;
    }

    @Override
    protected KnowledgePresenter createpresenter() {
        return new KnowledgePresenter();
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        dataBeans = new ArrayList<>();
        knowListBeanAdapter = new KnowListBeanAdapter(list, dataBeans, getContext());
        rv.setAdapter(knowListBeanAdapter);
        sml.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                initData();
            }
        });
        //点击悬浮按钮回到顶部并显示隐藏的toolbar与底部导航栏
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.smoothScrollToPosition(0);
                getActivity().findViewById(R.id.tool).setVisibility(View.VISIBLE);
//                getActivity().findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
            }
        });
        initRecy();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_knowledge_list_bean;
    }

    @Override
    protected void initData() {
        presenter.showKnowBean();
        presenter.showKnowListBean(page, id);
    }

    @Override
    public void showKnowBean(KnowledgeBean knowledgeBean) {

    }

    @Override
    public void showKnowListBean(KnowLedgeListBean knowLedgeListBean, int page, int cid) {
        final List<KnowLedgeListBean.DataBean.DatasBean> datas = knowLedgeListBean.getData().getDatas();
        knowListBeanAdapter.addData1(datas);

        knowListBeanAdapter.OnClick(new KnowListBeanAdapter.OnItemClick() {
            @Override
            public void OnItem(int pos) {
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
        sml.finishLoadMore();
        sml.finishRefresh();

    }

    //下拉隐藏底部导航栏
    private void initRecy() {
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
                            getActivity().findViewById(R.id.tool).setVisibility(View.VISIBLE);
                            getActivity().findViewById(R.id.tab).setVisibility(View.VISIBLE);
                            //这个就是当前页面的头布局id
                            btnMain.setVisibility(View.VISIBLE);
                        } else if (v1 < -1) {
                            getActivity().findViewById(R.id.tab).setVisibility(View.GONE);
                            getActivity().findViewById(R.id.tool).setVisibility(View.GONE);
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
