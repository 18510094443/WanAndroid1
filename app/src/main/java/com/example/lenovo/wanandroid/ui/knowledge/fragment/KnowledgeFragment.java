package com.example.lenovo.wanandroid.ui.knowledge.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.example.lenovo.wanandroid.ui.knowledge.acrivity.KnowActivity;
import com.example.lenovo.wanandroid.ui.knowledge.adapter.KnowAdapter;
import com.example.lenovo.wanandroid.utils.CircularAnimUtil;
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
    @BindView(R.id.btn_main)
    FloatingActionButton btnMain;
    @BindView(R.id.main_sml)
    SmartRefreshLayout mainSml;
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
                intent.setClass(getContext(),KnowActivity.class);
                intent.putExtra("pos", mPos);
                intent.putExtra("title", knowledgeBean.getData().get(mPos).getName());
                CircularAnimUtil.startActivity(getActivity(),intent,rv,R.color.colorAccent);
            }
        });
    }

    @Override
    public void showKnowListBean(KnowLedgeListBean knowLedgeListBean, int page, int cid) {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
