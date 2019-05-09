package com.example.lenovo.wanandroid.ui.Main.acrivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.utils.CircularAnimUtil;
import com.example.lenovo.wanandroid.utils.StatusBarManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tool)
    Toolbar tool;
    @BindView(R.id.web)
    WebView web;
    private String url;
    private String title;

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
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        tool.setTitle("");
        tv.setText(title);
        setSupportActionBar(tool);
        web.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebChromeClient(new WebChromeClient());
        web.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_article_common,null);
        getMenuInflater().inflate(R.menu.menu_article_common,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
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
                textIntent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(textIntent, "分享"));
                //分享图片
/*String path = bean.getEnvelopePic();
Intent imageIntent = new Intent(Intent.ACTION_SEND);
imageIntent.setType("image/png");
imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
startActivity(Intent.createChooser(imageIntent, "分享"));*/
                break;
            case R.id.item_system_browser:
                web.loadUrl(url);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected IBasePresenter createpresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_banner;
    }
}
