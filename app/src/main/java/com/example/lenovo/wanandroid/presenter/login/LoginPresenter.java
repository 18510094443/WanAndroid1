package com.example.lenovo.wanandroid.presenter.login;

import com.example.lenovo.wanandroid.base.BasePresenter;
import com.example.lenovo.wanandroid.component.CommonSubscriber;
import com.example.lenovo.wanandroid.interfaces.login.LoginContract;
import com.example.lenovo.wanandroid.model.bean.Login.LoginBean;
import com.example.lenovo.wanandroid.model.bean.Login.RegisterBean;
import com.example.lenovo.wanandroid.model.bean.Login.VerifyBean;
import com.example.lenovo.wanandroid.model.http.HttpManger;
import com.example.lenovo.wanandroid.utils.RxUtils;

import java.util.Map;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {
    @Override
    public void setVerify() {
        addSubscribe(HttpManger.getLoginServer().getVerify()
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<VerifyBean>(view) {
            @Override
            public void onNext(VerifyBean verifyBean) {
                view.showVerify(verifyBean);
            }
        }));
    }

    @Override
    public void setLogin(Map<String, String> map) {
        addSubscribe(HttpManger.getLoginServer().getLogin(map)
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<LoginBean>(view) {
            @Override
            public void onNext(LoginBean loginBean) {
                view.showLogin(loginBean,map);
            }
        }));
    }

    @Override
    public void setRegister(Map<String, String> map) {
        addSubscribe(HttpManger.getLoginServer()
        .getRegister(map)
        .subscribeWith(new CommonSubscriber<RegisterBean>(view) {
            @Override
            public void onNext(RegisterBean registerBean) {
                view.showRegister(registerBean,map);
            }
        }));
    }
}
