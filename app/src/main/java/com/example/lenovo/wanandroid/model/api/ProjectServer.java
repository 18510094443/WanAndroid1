package com.example.lenovo.wanandroid.model.api;

import com.example.lenovo.wanandroid.model.bean.project.ProjectBean;
import com.example.lenovo.wanandroid.model.bean.project.ProjectListBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectServer {

    /*	4.1 项目分类
	http://www.wanandroid.com/project/tree/json
	方法： GET
	参数： 无

	4.2 项目列表数据
	http://www.wanandroid.com/project/list/1/json?cid=294

	方法：GET
	参数：
		cid 分类的id，上面项目分类接口
		页码：拼接在链接中，从1开始。*/
   String url = "http://www.wanandroid.com/project/";
   @GET("tree/json")
    Flowable<ProjectBean> getProjectBean();

   @GET("list/{page}/json")
    Flowable<ProjectListBean> getProjectListBean(@Path("page") int page, @Query("cid") int cid);

}
