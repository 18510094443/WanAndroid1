package com.example.lenovo.wanandroid.interfaces.login;

import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.base.interfaces.IBaseView;
import com.example.lenovo.wanandroid.model.bean.Login.LoginBean;
import com.example.lenovo.wanandroid.model.bean.Login.RegisterBean;
import com.example.lenovo.wanandroid.model.bean.Login.VerifyBean;

import java.util.Map;

public interface LoginContract {

    interface LoginView extends IBaseView{
        void showVerify(VerifyBean info);

        void showLogin(LoginBean info,Map<String,String> map);

        void showRegister(RegisterBean info,Map<String,String> map);
    }

    interface LoginPresenter extends IBasePresenter<LoginView>{
        void setVerify();

        void setLogin(Map<String,String> map);

        void setRegister(Map<String,String> map);
    }

}
