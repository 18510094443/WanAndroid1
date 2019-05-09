package com.example.lenovo.wanandroid.ui.Main.acrivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.interfaces.Main.MainContract;
import com.example.lenovo.wanandroid.model.bean.dao.DbBean;
import com.example.lenovo.wanandroid.model.bean.main.BannerBean;
import com.example.lenovo.wanandroid.model.bean.main.ChangBean;
import com.example.lenovo.wanandroid.model.bean.main.ListBean;
import com.example.lenovo.wanandroid.presenter.Main.MainPresenter;
import com.example.lenovo.wanandroid.utils.MyDbHelper;
import com.example.lenovo.wanandroid.utils.StatusBarManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageActivity extends BaseActivity<MainPresenter> implements MainContract.MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.main_checkbox)
    CheckBox mainCheckbox;
    @BindView(R.id.web)
    WebView web;
    private String link;
    private String title;
    private String niceDate;
    private String chapterName;
    private String author;

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        StatusBarManager.setStatusBarColor(this, color);
        Intent intent = getIntent();
//         intent.putExtra("author",datas.get(pos).getAuthor());
//                intent.putExtra("chapterName",datas.get(pos).getChapterName()+"/"+datas.get(pos).getSuperChapterName());
//                intent.putExtra("niceDate",datas.get(pos).getNiceDate());
        title = intent.getStringExtra("title");
        link = intent.getStringExtra("link");
        author = intent.getStringExtra("author");
        chapterName = intent.getStringExtra("chapterName");
        niceDate = intent.getStringExtra("niceDate");
        toolbar.setTitle("");
        textView5.setText(title);
        setSupportActionBar(toolbar);
        web.loadDataWithBaseURL(null, link, "text/html", "utf-8", null);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebChromeClient(new WebChromeClient());
        web.loadUrl(link);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        List<DbBean> dbBeans = MyDbHelper.getMyDbHelper().query1(author, title);
        if (dbBeans!=null&&dbBeans.size()>0){
            mainCheckbox.setBackgroundResource(R.drawable.follow);
        }else{
            mainCheckbox.setBackgroundResource(R.drawable.follow_unselected);
        }
        DbBean dbBean = new DbBean();
        dbBean.setId(null);
        dbBean.setImg(null);
        dbBean.setName(author);
        dbBean.setTime(niceDate);
        dbBean.setChanlename(chapterName);
        dbBean.setTitle(title);
        mainCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    mainCheckbox.setBackgroundResource(R.drawable.follow);
                    MyDbHelper.getMyDbHelper().insert(dbBean);
                }else if (b==false){
                    mainCheckbox.setBackgroundResource(R.drawable.follow_unselected);
                    MyDbHelper.getMyDbHelper().delete(dbBean);
                }
            }
        });

    }

    @Override
    protected MainPresenter createpresenter() {
        return null;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_main_page;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_article_common,null);
        getMenuInflater().inflate(R.menu.menu_article_common, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_share:
               /* UMImage thumb =  new UMImage(this, R.drawable.umeng_socialize_qq);
                UMWeb web = new UMWeb(link);
                web.setTitle(title);//标题
                web.setThumb(thumb);  //缩略图
                web.setDescription("分享描述");//描述
                new ShareAction(this).withMedia(web).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener).open();*/
                //分享文本
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, link);
                startActivity(Intent.createChooser(textIntent, "分享"));
                //分享图片
/*String path = bean.getEnvelopePic();
Intent imageIntent = new Intent(Intent.ACTION_SEND);
imageIntent.setType("image/png");
imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
startActivity(Intent.createChooser(imageIntent, "分享"));*/
                break;
            case R.id.item_system_browser:
                /*UMImage thumb =  new UMImage(this, R.drawable.umeng_socialize_qq);
                UMWeb web = new UMWeb(link);
                web.setTitle(title);//标题
                web.setThumb(thumb);  //缩略图
                web.setDescription("分享描述");//描述
                new ShareAction(this).withMedia(web).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener).open();*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showBanner(BannerBean bannerBean) {

    }

    @Override
    public void showList(ListBean listBean, int page) {

    }

    @Override
    public void showChang(ChangBean changBean) {

    }
}
