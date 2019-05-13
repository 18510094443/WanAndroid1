package com.example.lenovo.wanandroid;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lenovo.wanandroid.app.Constants;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.interfaces.login.LoginContract;
import com.example.lenovo.wanandroid.model.bean.Login.LoginBean;
import com.example.lenovo.wanandroid.model.bean.Login.RegisterBean;
import com.example.lenovo.wanandroid.model.bean.Login.VerifyBean;
import com.example.lenovo.wanandroid.presenter.login.LoginPresenter;
import com.example.lenovo.wanandroid.ui.Main.acrivity.SerachActivity;
import com.example.lenovo.wanandroid.ui.Main.fragment.ChangActivity;
import com.example.lenovo.wanandroid.ui.Main.fragment.MainPageFragment;
import com.example.lenovo.wanandroid.ui.about.AboutActivity;
import com.example.lenovo.wanandroid.ui.collect.CollectFragment;
import com.example.lenovo.wanandroid.ui.knowledge.fragment.KnowledgeFragment;
import com.example.lenovo.wanandroid.ui.login.activity.LoginActivity;
import com.example.lenovo.wanandroid.ui.navigation.fragment.NavigationFragment;
import com.example.lenovo.wanandroid.ui.project.fragment.ProjectFragment;
import com.example.lenovo.wanandroid.ui.setting.SeetingFragment;
import com.example.lenovo.wanandroid.ui.wx.fragment.WxFragment;
import com.example.lenovo.wanandroid.utils.CircularAnimUtil;
import com.example.lenovo.wanandroid.utils.HindMain;
import com.example.lenovo.wanandroid.utils.SpUtil;
import com.example.lenovo.wanandroid.utils.StatusBarManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {


    public static int TYPE_SETTINGS = 6;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.nv)
    NavigationView mNv;
    @BindView(R.id.dl)
    DrawerLayout mDl;
    private String string;
    private String string1;

    private ArrayList<Fragment> mFragments;
    private int mLastPosition;
    private FragmentManager mManager;
    private TextView tv;


    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
//toolBar
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        StatusBarManager.setStatusBarColor(this, color);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDl, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = mDl.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        mDl.addDrawerListener(toggle);


        //解决侧滑菜单menu部分图标不显示
        mNv.setItemIconTintList(null);
        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        string = login.getString("name", "未登录");
        string1 = login.getString("psw", "");
        View headerView = mNv.getHeaderView(0);
        tv = headerView.findViewById(R.id.nav_header_login_tv);
        tv.setText(string);
        if (tv.getText().toString().equals("未登录")) {

        } else {
            Map<String, String> map = new HashMap<>();
            map.put("username", string);
            map.put("password", string1);
            presenter.setLogin(map);
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.nav_item_wan_android:
                        mTv.setText("首页");
                        mTabLayout.setVisibility(View.VISIBLE);
                        transaction.replace(R.id.fl_container, mFragments.get(0)).commit();
                        mDl.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.nav_item_my_collect:
                        /*if (tv.getText().equals("未登录")) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setClass(MainActivity.this, LoginActivity.class);
                            CircularAnimUtil.startActivity(MainActivity.this, intent, mFlContainer, R.color.fab_bg);
                        } else {*/
                            mTv.setText("收藏");
                            mTabLayout.setVisibility(View.GONE);
                            transaction.replace(R.id.fl_container, mFragments.get(5)).commit();
                            mDl.closeDrawers();
//                        }
                        break;
                    case R.id.nav_item_setting:
                        mTv.setText("设置");
                        mTabLayout.setVisibility(View.GONE);
                        transaction.replace(R.id.fl_container, mFragments.get(TYPE_SETTINGS)).commit();
                        mDl.closeDrawers();
                        break;
                    case R.id.nav_item_about_us:
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        intent.setClass(MainActivity.this, AboutActivity.class);
                        CircularAnimUtil.startActivity(MainActivity.this, intent, mFlContainer, R.color.fab_bg);
                        break;
                }
                return false;
            }
        });

        //TabLayout
        View tab1 = getTab(0);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(tab1));
        View tab2 = getTab(1);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(tab2));
        View tab3 = getTab(2);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(tab3));
        View tab4 = getTab(3);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(tab4));
        View tab5 = getTab(4);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(tab5));

        //处理Fragment
        mFragments = new ArrayList<>();
        mFragments.add(new MainPageFragment());
        mFragments.add(new KnowledgeFragment());
        mFragments.add(new WxFragment());
        mFragments.add(new NavigationFragment());
        mFragments.add(new ProjectFragment());
        mFragments.add(new CollectFragment());
        mFragments.add(new SeetingFragment());

        addMainPageFragment();


        //tablayout的tab的选中监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab选中的监听
                int position = tab.getPosition();
                showFragment(position);
                //每次显示的Fragment就是下次点击别的tab的时候需要隐藏的
                mLastPosition = position;
                //动态设置toolbar标题
                setToolBarTitle(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab未选中
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab重新选中监听
            }
        });

       /* //侧滑菜单的显示隐藏监听,实现主页面可随侧滑菜单平移效果
        mDl.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //侧滑菜单的滑动监听,slideOffset菜单的滑动比例
                //重新给主界面设置x轴的起始位置
                ll.setX(mNv.getWidth()*slideOffset);
                Context context = getApplicationContext();
// 获取当前包名
                String packageName = context.getPackageName();
// 获取当前进程名
                String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
                CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
                strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
                CrashReport.initCrashReport(context, "a88aeb13b5", true, strategy);
// 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
// CrashReport.initCrashReport(context, strategy);
//                CrashReport.testJavaCrash();
            }
        });*/
//        addZhihuDailyNewsFragment();

    }

   /* private void addZhihuDailyNewsFragment() {
        //如果是因为切换日夜间模式导致Activyt重建,需要直接进入设置Fragment
        int type = (int) SpUtil.getParam(Constants.DAY_NIGHT_FRAGMENT_POS, mFragments.get(0));
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(R.id.fl_container,mFragments.get(type));
        transaction.commit();
       setToolBarTitle(type);
    }*/

    @Override
    protected LoginPresenter createpresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    private void setToolBarTitle(int position) {
        if (position == 0) {
            mTv.setText(R.string.home);
        } else if (position == 1) {
            mTv.setText(R.string.knowledge);
        } else if (position == 2) {
            mTv.setText(R.string.wx);
        } else if (position == 3) {
            mTv.setText(R.string.navigation);
        } else if (position == 4) {
            mTv.setText(R.string.project);
        }
    }

    private void showFragment(int position) {

        FragmentTransaction transaction = mManager.beginTransaction();
        ///需要显示的Fragment,同一Fragment对象只能添加一次,多次添加就崩了
        Fragment fragment = mFragments.get(position);
        if (!fragment.isAdded()) {
            //如果没有添加过就添加
            transaction.add(R.id.fl_container, fragment);
        }
        //如果已经添加过了,直接显示就可以了
        transaction.show(fragment);
        //原来显示的隐藏掉
        transaction.hide(mFragments.get(mLastPosition));
        transaction.commit();
    }


    private void addMainPageFragment() {
        //1.获取碎片管理器
        mManager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction transaction = mManager.beginTransaction();
        //3.添加Fragment
        transaction.add(R.id.fl_container, mFragments.get(0));
        //4.提交事务
        transaction.commit();
        mTv.setText(R.string.home);
    }

    private View getTab(int i) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.tab, null, false);
        TextView tv = inflate.findViewById(R.id.tv);
        ImageView iv = inflate.findViewById(R.id.iv);
        if (i == 0) {
//            CrashReport.testJavaCrash();
            tv.setText(R.string.home);
            iv.setImageResource(R.drawable.home);
        } else if (i == 1) {
            tv.setText(R.string.knowledge);
            iv.setImageResource(R.drawable.knowledge);
        } else if (i == 2) {
//            CrashReport.testNativeCrash();
            tv.setText(R.string.wx);
            iv.setImageResource(R.drawable.wx);
        } else if (i == 3) {
//            CrashReport.testJavaCrash();
            tv.setText(R.string.navigation);
            iv.setImageResource(R.drawable.navigation);
        } else if (i == 4) {
            tv.setText(R.string.project);
            iv.setImageResource(R.drawable.project);
        }
        return inflate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChangFragment changFragment = new ChangFragment();*/
        switch (item.getItemId()) {
            case R.id.action_usage:
                /*mToolBar.setVisibility(View.GONE);
                mTabLayout.setVisibility(View.GONE);
                transaction.replace(R.id.fl_container,changFragment ).commit();*/
//                intent跳转 带颜色跳
                Intent intent = new Intent(MainActivity.this, ChangActivity.class);
                intent.setClass(MainActivity.this, ChangActivity.class);
                CircularAnimUtil.startActivity(MainActivity.this, intent, mToolBar, R.color.fab_bg);
                break;
            case R.id.action_search:
                Intent intent1 = new Intent(MainActivity.this, SerachActivity.class);
                intent1.setClass(MainActivity.this, SerachActivity.class);
                CircularAnimUtil.startActivity(MainActivity.this, intent1, mToolBar, R.color.fab_bg);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, mFragments.get(0));
    }

    @Override
    public void showVerify(VerifyBean info) {

    }

    @Override
    public void showLogin(LoginBean info, Map<String, String> map) {

    }

    @Override
    public void showRegister(RegisterBean info, Map<String, String> map) {

    }



    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
    private static String getProcessName(int pid) {
    BufferedReader reader = null;
    try {
    reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
    String processName = reader.readLine();
    if (!TextUtils.isEmpty(processName)) {
    processName = processName.trim();
    }
    return processName;
    } catch (Throwable throwable) {
    throwable.printStackTrace();
    } finally {
    try {
    if (reader != null) {
    reader.close();
    }
    } catch (IOException exception) {
    exception.printStackTrace();
    }
    }
    return null;
    }*/
}
