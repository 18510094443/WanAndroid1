package com.example.lenovo.wanandroid.ui.login.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.wanandroid.MainActivity;
import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.interfaces.login.LoginContract;
import com.example.lenovo.wanandroid.model.bean.Login.LoginBean;
import com.example.lenovo.wanandroid.model.bean.Login.RegisterBean;
import com.example.lenovo.wanandroid.model.bean.Login.VerifyBean;
import com.example.lenovo.wanandroid.presenter.login.LoginPresenter;
import com.example.lenovo.wanandroid.ui.register.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {


    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.login_account_edit)
    EditText loginAccountEdit;
    @BindView(R.id.login_account_group)
    LinearLayout loginAccountGroup;
    @BindView(R.id.login_divider)
    View loginDivider;
    @BindView(R.id.login_password_edit)
    EditText loginPasswordEdit;
    @BindView(R.id.login_password_group)
    LinearLayout loginPasswordGroup;
    @BindView(R.id.register_divider)
    View registerDivider;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_or_tv)
    TextView loginOrTv;
    @BindView(R.id.login_register_btn)
    Button loginRegisterBtn;
    @BindView(R.id.login_group)
    RelativeLayout loginGroup;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.login_toolbar)
    Toolbar loginToolbar;

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                map.put("username",loginAccountEdit.getText().toString());
                map.put("password",loginPasswordEdit.getText().toString());
                presenter.setLogin(map);
            }
        });
    }

    @Override
    protected void initView() {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected LoginPresenter createpresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void showVerify(VerifyBean info) {

    }

    @Override
    public void showLogin(LoginBean info, Map<String, String> map) {
        Log.e("TAG", "---" + info.getRet());
        String ret = info.getRet();
        String uid = info.getData().get(0).getUid();

        Toast.makeText(this, ret, Toast.LENGTH_SHORT).show();
        if (ret.equals("登录成功")) {
            SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor edit = login.edit();
            edit.putString("name", loginAccountEdit.getText().toString());
            edit.putString("psw", loginPasswordEdit.getText().toString());
            edit.putString("uid", uid);
            edit.commit();
            finish();
        }
    }

    @Override
    public void showRegister(RegisterBean info, Map<String, String> map) {

    }


}
