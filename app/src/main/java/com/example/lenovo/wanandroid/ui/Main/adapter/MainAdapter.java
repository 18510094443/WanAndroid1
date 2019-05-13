package com.example.lenovo.wanandroid.ui.Main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.app.Constants;
import com.example.lenovo.wanandroid.model.bean.dao.DbBean;
import com.example.lenovo.wanandroid.model.bean.main.BannerBean;
import com.example.lenovo.wanandroid.model.bean.main.ListBean;
import com.example.lenovo.wanandroid.model.bean.main.SearchBean;
import com.example.lenovo.wanandroid.ui.Main.acrivity.BannerActivity;
import com.example.lenovo.wanandroid.utils.MyDbHelper;
import com.example.lenovo.wanandroid.utils.SpUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.greendao.DbUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.POST;

public class MainAdapter extends RecyclerView.Adapter {
    public List<BannerBean.DataBean> banner_list;
    public List<ListBean.DataBean.DatasBean> list;
    private Context context;
    private OnItemClick li;

    public MainAdapter(List<BannerBean.DataBean> banner_list, List<ListBean.DataBean.DatasBean> list, Context context) {

        this.banner_list = banner_list;
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.banner_item, viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(inflate);
            return viewHolder;
        } else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.main_item, viewGroup,false);
            ViewHolder1 viewHolder1 = new ViewHolder1(inflate);
            return viewHolder1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ViewHolder){
            ViewHolder holder = (ViewHolder) viewHolder;
            List<String> mBannerTitleList = new ArrayList<>();
            List<String> bannerImageList = new ArrayList<>();
            List<String> mBannerUrlList = new ArrayList<>();
            for (BannerBean.DataBean bannerData : banner_list) {
                mBannerTitleList.add(bannerData.getTitle());
                bannerImageList.add(bannerData.getImagePath());
                mBannerUrlList.add(bannerData.getUrl());
            }
            //设置banner样式
            holder.banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
            //设置图片集合
            holder.banner.setImages(bannerImageList);
            //设置banner动画效果
            holder.banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            holder.banner.setBannerTitles(mBannerTitleList);
            //设置自动轮播，默认为true
            holder.banner.isAutoPlay(true);
            //设置指示器位置（当banner模式中有指示器时）
            holder.banner.setIndicatorGravity(BannerConfig.CENTER);

            holder.banner.setImages(banner_list).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    BannerBean.DataBean bean = (BannerBean.DataBean) path;
                    Glide.with(context).load(bean.getImagePath()).into(imageView);
                }
            }).start();

            holder.banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(context, BannerActivity.class);
                    intent.putExtra("title",banner_list.get(i).getTitle());
                    intent.putExtra("url",banner_list.get(i).getUrl());
                    context.startActivity(intent);
                }
            });
        }else{
            ViewHolder1 holder1 = (ViewHolder1) viewHolder;
            holder1.mainTv.setText(list.get(i).getAuthor());
            holder1.mainTv1.setText(list.get(i).getChapterName()+"/"+list.get(i).getSuperChapterName());
            holder1.mainTv3.setText(list.get(i).getTitle());
            holder1.mainTv4.setText(list.get(i).getNiceDate());
            holder1.mainTv1.setTextColor(ContextCompat.getColor(context, R.color.blue));

            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    li.OnItem(i);
                }
            });
            DbBean dbBean = new DbBean();
            dbBean.setId(null);
            dbBean.setImg(list.get(i).getLink());
            dbBean.setName(list.get(i).getAuthor());
            dbBean.setChanlename(list.get(i).getChapterName()+"/"+list.get(i).getSuperChapterName());
            dbBean.setTitle(list.get(i).getTitle());
            dbBean.setTime(list.get(i).getNiceDate());
            if (MyDbHelper.getMyDbHelper().queryBean(dbBean)!=null){
                holder1.cb.setChecked(true);
            }else{
                holder1.cb.setChecked(false);
            }
            holder1.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    DbBean dbBean = new DbBean();
                    dbBean.setId(null);
                    dbBean.setImg(list.get(i).getLink());
                    dbBean.setName(list.get(i).getAuthor());
                    dbBean.setChanlename(list.get(i).getChapterName()+"/"+list.get(i).getSuperChapterName());
                    dbBean.setTitle(list.get(i).getTitle());
                    dbBean.setTime(list.get(i).getNiceDate());
                    if (b==false){
                        List<DbBean> dbBeans1 = MyDbHelper.getMyDbHelper().query1(list.get(i).getAuthor(), list.get(i).getTitle());
                        MyDbHelper.getMyDbHelper().delete(dbBeans1);
                    }else if (b==true){
                        MyDbHelper.getMyDbHelper().insert(dbBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addBanner(List<BannerBean.DataBean> data) {
        banner_list.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<ListBean.DataBean.DatasBean> datas) {
        list.addAll(datas);
        notifyDataSetChanged();
    }


     class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.main_img)
        ImageView mainImg;
        @BindView(R.id.main_tv)
        TextView mainTv;
        @BindView(R.id.main_tv1)
        TextView mainTv1;
        @BindView(R.id.main_tv3)
        TextView mainTv3;
        @BindView(R.id.main_tv4)
        TextView mainTv4;
        @BindView(R.id.cb)
        CheckBox cb;


        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void OnClick(OnItemClick li){
        this.li=li;
    }

    public interface OnItemClick{
        void OnItem(int pos);
    }
}
