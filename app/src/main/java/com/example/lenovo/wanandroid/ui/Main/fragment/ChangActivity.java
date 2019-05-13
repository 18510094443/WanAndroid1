package com.example.lenovo.wanandroid.ui.Main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.interfaces.Main.MainContract;
import com.example.lenovo.wanandroid.model.bean.main.BannerBean;
import com.example.lenovo.wanandroid.model.bean.main.ChangBean;
import com.example.lenovo.wanandroid.model.bean.main.ListBean;
import com.example.lenovo.wanandroid.presenter.Main.MainPresenter;
import com.example.lenovo.wanandroid.ui.Main.acrivity.MainPageActivity;
import com.example.lenovo.wanandroid.ui.Main.adapter.ChangAdapter;
import com.example.lenovo.wanandroid.ui.project.acrivity.ProjectActivity;
import com.example.lenovo.wanandroid.utils.CircularAnimUtil;
import com.example.lenovo.wanandroid.utils.CommonUtils;
import com.example.lenovo.wanandroid.utils.StatusBarManager;
import com.example.lenovo.wanandroid.widget.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangActivity extends BaseActivity<MainPresenter> implements MainContract.MainView {

    @BindView(R.id.tv)
    FlowLayout fl;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tool)
    Toolbar tool;

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {
        presenter.showChang();
    }

    @Override
    protected void initView() {

        tool.setTitle("");
        setSupportActionBar(tool);
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        StatusBarManager.setStatusBarColor(this, color);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected MainPresenter createpresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_chang;
    }

    @Override
    public void showBanner(BannerBean bannerBean) {

    }

    @Override
    public void showList(ListBean listBean, int page) {

    }

    @Override
    public void showChang(ChangBean changBean) {
//        changAdapter.addData(changBean.getData());

        fl.removeAllViews();
        for (int i = 0; i < changBean.getData().size(); i++) {
//            TextView tv = new TextView(this);
            TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.chang_shape, null);
            tv.setText(changBean.getData().get(i).getName());
           /* if (i % 4 == 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else if (i % 4 == 1) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.gold_title));
            } else if (i % 4 == 2) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_red));
            } else if (i % 4 == 3) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.blue));
            }*/
//           tv.setBackgroundColor(CommonUtils.randomColor());
            if (i % 4 == 0) {
                tv.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            } else if (i % 4 == 1) {
                tv.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
            } else if (i % 4 == 2) {
                tv.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            } else if (i % 4 == 3) {
                tv.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
            }
            int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ChangActivity.this, ProjectActivity.class);
                    intent.setClass(ChangActivity.this,ProjectActivity.class);
                    intent.putExtra("title",changBean.getData().get(finalI).getName());
                    intent.putExtra("link",changBean.getData().get(finalI).getLink());
                    CircularAnimUtil.startActivity(ChangActivity.this,intent,tv,R.color.colorAccent);
                }
            });
            fl.addView(tv);
        }


    }

}
