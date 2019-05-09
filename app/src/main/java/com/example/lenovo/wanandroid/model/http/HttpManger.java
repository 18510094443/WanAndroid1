package com.example.lenovo.wanandroid.model.http;

import com.example.lenovo.wanandroid.constance.Constance;
import com.example.lenovo.wanandroid.model.api.KnowledgeServer;
import com.example.lenovo.wanandroid.model.api.LoginServer;
import com.example.lenovo.wanandroid.model.api.MainServer;
import com.example.lenovo.wanandroid.model.api.ProjectServer;
import com.example.lenovo.wanandroid.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManger {

    //首页
    private static MainServer mainServer;
    //项目
    private static ProjectServer projectServer;

    private static KnowledgeServer knowledgeServer;

    private static LoginServer loginServer;

    //创建Retrofit对象
    private static Retrofit getRetrofit(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
    /**
     * 创建带缓存的HttpClient对象
     * @return
     */
    private static OkHttpClient getOkhttpClient() {
        //本地缓存文件
        File file = new File(Constance.PATH_CACHE);
        //设置缓存文件的大小100M
        Cache cache = new Cache(file, 1024 * 1024 * 100);
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Myintercepter())
                .addNetworkInterceptor(new Myintercepter())
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    //获取相关的网络接口
    private static synchronized <T> T getServerApis(String baseUrl,Class<T> tCla){
        return getRetrofit(baseUrl).create(tCla);
    }

    //拦截器的实现
    private static class Myintercepter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!SystemUtils.checkNetWork()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (!SystemUtils.checkNetWork()) {
                int maxAge=0;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public ,max-age="+maxAge).build();
            }else{
                int maxStale = 60*60*24*28; //设置缓存数据的保存时间
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, onlyif-cached, max-stale="+maxStale).build();
            }
        }
    }

    //获取首页Api
    public static MainServer getMainServer(){
        synchronized (HttpManger.class){
            if (mainServer == null){
                synchronized (HttpManger.class){
                    mainServer=getServerApis(Constance.STR_WWW,MainServer.class);
                }
            }
        }
        return mainServer;
    }

    public static ProjectServer getProjectServer(){
        synchronized (HttpManger.class){
            if (projectServer == null){
                synchronized (HttpManger.class){
                    projectServer=getServerApis(ProjectServer.url,ProjectServer.class);
                }
            }
        }
        return projectServer;
    }

    public static KnowledgeServer getKnowledgeServer(){
        synchronized (KnowledgeServer.class){
            if (knowledgeServer==null){
                synchronized (KnowledgeServer.class){
                    knowledgeServer=getServerApis(KnowledgeServer.url,KnowledgeServer.class);
                }
            }
        }
        return knowledgeServer;
    }

    public static LoginServer getLoginServer(){
        synchronized (LoginServer.class){
            if (loginServer==null){
                synchronized (LoginServer.class){
                    loginServer=getServerApis(LoginServer.url,LoginServer.class);
                }
            }
        }
        return loginServer;
    }


}
