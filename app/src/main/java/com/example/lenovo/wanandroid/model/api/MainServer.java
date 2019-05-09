package com.example.lenovo.wanandroid.model.api;

import com.example.lenovo.wanandroid.model.bean.main.BannerBean;
import com.example.lenovo.wanandroid.model.bean.main.ChangBean;
import com.example.lenovo.wanandroid.model.bean.main.ListBean;
import com.example.lenovo.wanandroid.model.bean.main.SearchBean;
import com.example.lenovo.wanandroid.model.bean.navigation.NavigationBean;
import com.example.lenovo.wanandroid.model.bean.wx.WxListBean;
import com.example.lenovo.wanandroid.model.bean.wx.WxTabBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainServer {

    /*1.首页相关
	1.1 首页文章列表
	http://www.wanandroid.com/article/list/0/json
	方法：GET
	参数：无

	1.2 首页banner
	http://www.wanandroid.com/banner/json
	方法：GET
	参数：无*/

    String url = "http://www.wanandroid.com/";
    @GET("banner/json")
    Flowable<BannerBean> getBanner();

    @GET("article/list/{page}/json")
    Flowable<ListBean> getList(@Path("page") int page);

//    https://wanandroid.com/wxarticle/chapters/json
//    https://wanandroid.com/wxarticle/list/408/1/json
    @GET("wxarticle/chapters/json")
    Flowable<WxTabBean> getTabBean();

    @GET("wxarticle/list/{cid}/{page}/json")
    Flowable<WxListBean> getListBean(@Path("cid") int id,@Path("page") int page);

//    https://www.wanandroid.com/navi/json

    @GET("navi/json")
    Flowable<NavigationBean> getNavigation();

//    http://wanandroid.com/wxarticle/list/405/1/json?k=Java

    @GET("wxarticle/list/{cid}/{page}/json")
    Flowable<WxListBean> getWxSearch(@Path("cid") int id,@Path("page") int page,@Query("k") String k) ;

//    https://www.wanandroid.com/friend/json
    @GET("friend/json")
    Flowable<ChangBean> getChang();

    /**
     * 热搜
     * http://www.wanandroid.com//hotkey/json
     *
     * @return 热门搜索数据
     */
    @GET("hotkey/json")
    @Headers("Cache-Control: public, max-age=36000")
    Flowable<SearchBean> getSearch();

}
