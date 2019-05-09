package com.example.lenovo.wanandroid.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.wanandroid.MainActivity;
import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.interfaces.login.LoginContract;
import com.example.lenovo.wanandroid.model.bean.Login.LoginBean;
import com.example.lenovo.wanandroid.model.bean.Login.RegisterBean;
import com.example.lenovo.wanandroid.model.bean.Login.VerifyBean;
import com.example.lenovo.wanandroid.presenter.login.LoginPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {

    @BindView(R.id.register_password_edit)
    EditText registerPasswordEdit;
    @BindView(R.id.register_account_edit)
    EditText registerAccountEdit;
    @BindView(R.id.register_confirm_password_edit)
    EditText registerConfirmPasswordEdit;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {
        presenter.setVerify();
        HashMap<String, String> map = new HashMap<>();
        map.put("username", registerAccountEdit.getText().toString());
        map.put("password", registerPasswordEdit.getText().toString());
        map.put("phone", registerConfirmPasswordEdit.getText().toString());
        presenter.setRegister(map);

    }

    @Override
    protected void initView() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected LoginPresenter createpresenter() {
        return new LoginPresenter();
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void showVerify(VerifyBean info) {
        String data = info.getData();
        Log.e("TAG","=="+data);
        tv.setText(data);
    }

    @Override
    public void showLogin(LoginBean info, Map<String, String> map) {

    }

    @Override
    public void showRegister(RegisterBean info, Map<String, String> map) {
        int code = info.getCode();
        Log.e("TAG", "-----" + code);
        String data = info.getRet();
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        if (data.equals("注册成功")) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
