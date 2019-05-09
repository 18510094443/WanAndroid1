package com.example.lenovo.wanandroid.model.api;

import com.example.lenovo.wanandroid.model.bean.knowledge.KnowLedgeListBean;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowledgeBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KnowledgeServer {

    String url = "https://www.wanandroid.com/";

    @GET("tree/json")
    Flowable<KnowledgeBean> getKnowBean();

    @GET("article/list/{page}/json")
    Flowable<KnowLedgeListBean> getKnowListBean(@Path("page")int page, @Query("cid") int cid);

}
