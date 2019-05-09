package com.example.lenovo.wanandroid.ui.Main.acrivity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.interfaces.Main.SearchContract;
import com.example.lenovo.wanandroid.model.bean.main.SearchBean;
import com.example.lenovo.wanandroid.presenter.Main.SearchPresenter;
import com.example.lenovo.wanandroid.widget.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SerachActivity extends BaseActivity<SearchPresenter> implements SearchContract.SearchView {

    @BindView(R.id.search_back_ib)
    ImageButton searchBackIb;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_tint_tv)
    TextView searchTintTv;
    @BindView(R.id.fl)
    FlowLayout fl;

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {
        presenter.showSearch();
    }

    @Override
    protected void initView() {
        searchBackIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected SearchPresenter createpresenter() {
        return new SearchPresenter();
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_serach;
    }

    @Override
    public void showSearch(SearchBean searchBean) {
        List<SearchBean.DataBean> data = searchBean.getData();
        fl.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_flow, null);
            tv.setText(data.get(i).getName());
            if (i % 4 == 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else if (i % 4 == 1) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.gold_title));
            } else if (i % 4 == 2) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_red));
            } else if (i % 4 == 3) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.blue));
            }
            fl.addView(tv);
        }
        
    }
}
