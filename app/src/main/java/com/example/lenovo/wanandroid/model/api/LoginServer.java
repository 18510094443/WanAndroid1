package com.example.lenovo.wanandroid.model.api;

import com.example.lenovo.wanandroid.model.bean.Login.LoginBean;
import com.example.lenovo.wanandroid.model.bean.Login.RegisterBean;
import com.example.lenovo.wanandroid.model.bean.Login.VerifyBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginServer {
    String url="http://yun918.cn/study/public/index.php/";

    //获取验证码
    @GET("verify")
    Flowable<VerifyBean> getVerify();

    //登录
    @POST("login")
    @FormUrlEncoded
    Flowable<LoginBean>getLogin(@FieldMap Map<String, String> map);


    //注册
    @POST("register")
    @FormUrlEncoded
    Flowable<RegisterBean>getRegister(@FieldMap Map<String, String> map);
}
