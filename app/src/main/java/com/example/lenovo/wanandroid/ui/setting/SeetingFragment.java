package com.example.lenovo.wanandroid.ui.setting;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.wanandroid.MainActivity;
import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.app.Constants;
import com.example.lenovo.wanandroid.base.BaseFragment;
import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.utils.SpUtil;
import com.example.lenovo.wanandroid.utils.UIModeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeetingFragment extends BaseFragment {


    @BindView(R.id.setting_usage_tv)
    TextView settingUsageTv;
    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox cbSettingCache;
    @BindView(R.id.setting_auto_cache_group)
    LinearLayout settingAutoCacheGroup;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox cbSettingImage;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox cbSettingNight;
    @BindView(R.id.setting_usage_card)
    CardView settingUsageCard;
    @BindView(R.id.setting_other_tv)
    TextView settingOtherTv;
    @BindView(R.id.ll_setting_feedback)
    TextView llSettingFeedback;
    @BindView(R.id.tv_setting_clear)
    TextView tvSettingClear;
    @BindView(R.id.ll_setting_clear)
    LinearLayout llSettingClear;
    @BindView(R.id.setting_other_group)
    CardView settingOtherGroup;
    Unbinder unbinder;

    @Override
    protected IBasePresenter createpresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {
        int currentNightMode = getActivity().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            //判断当前是日间模式
            cbSettingNight.setChecked(false);
        }else {
            cbSettingNight.setChecked(true);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_seeting;
    }

    @Override
    protected void initData() {
        cbSettingNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //切换模式
                //切换日夜间模式的时候Activity会重新创建
                //对应的这个碎片也会重建,重建的时候SwitchCompat会设置默认值
                //设置默认值的时候这个回调会被调用
                //if (用户点击的情况下){
                if (buttonView.isPressed()){
                    //切换并保存模式
                    UIModeUtil.changeModeUI((MainActivity) getActivity());
                    //保存当前碎片的type
                    SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS,MainActivity.TYPE_SETTINGS);
                }
            }
        });

        Wutu();

    }

    private void Wutu() {
        cbSettingImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SpUtil.setParam("img",true);
                }else{
                    SpUtil.setParam("img",false);
                }
            }
        });
    }
}
