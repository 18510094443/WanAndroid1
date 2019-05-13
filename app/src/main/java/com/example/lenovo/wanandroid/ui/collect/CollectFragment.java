package com.example.lenovo.wanandroid.ui.collect;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.app.Constants;
import com.example.lenovo.wanandroid.base.BaseFragment;
import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.model.bean.dao.DbBean;
import com.example.lenovo.wanandroid.ui.Main.acrivity.MainPageActivity;
import com.example.lenovo.wanandroid.utils.CircularAnimUtil;
import com.example.lenovo.wanandroid.utils.MyDbHelper;
import com.example.lenovo.wanandroid.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectFragment extends BaseFragment {


    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private CollectAdapter collectAdapter;
    private List<DbBean> list;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            initData();
        }
    }

    @Override
    protected IBasePresenter createpresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {
        SpUtil.getParam(Constants.COLLECT,true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        collectAdapter = new CollectAdapter(list,getContext());
        rv.setAdapter(collectAdapter);
       /* collectAdapter.OnClick(new CollectAdapter.OnItemClick() {
            @Override
            public void OnItem(int pos) {
                *//*List<DbBean> list = MyDbHelper.getMyDbHelper().query();
                for (int i = 0; i < list.size(); i++) {
                    img = list.get(i).getImg();
                    title = list.get(i).getTitle();
                }*//*
                 Intent intent = new Intent(getContext(), MainPageActivity.class);
                 intent.setClass(getContext(),MainPageActivity.class);
                 intent.putExtra("link", list.get(pos).getImg());
                 intent.putExtra("title", list.get(pos).getTitle());
                 CircularAnimUtil.startActivity((Activity) getContext(),intent,rv,R.color.colorAccent);
            }
        });
*/
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initData() {
        List<DbBean> dbBeans = MyDbHelper.getMyDbHelper().query();
        collectAdapter.addData(dbBeans);
    }
}
