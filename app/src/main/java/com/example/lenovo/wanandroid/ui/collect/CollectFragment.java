package com.example.lenovo.wanandroid.ui.collect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseFragment;
import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.model.bean.dao.DbBean;
import com.example.lenovo.wanandroid.utils.MyDbHelper;

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

    @Override
    protected IBasePresenter createpresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        List<DbBean> list = MyDbHelper.getMyDbHelper().query();
        CollectAdapter collectAdapter = new CollectAdapter(list, getContext());
        rv.setAdapter(collectAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initData() {

    }
}
