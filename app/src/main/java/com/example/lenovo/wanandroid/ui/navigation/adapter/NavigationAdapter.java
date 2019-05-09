package com.example.lenovo.wanandroid.ui.navigation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.wanandroid.MainActivity;
import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.model.bean.navigation.NavigationBean;
import com.example.lenovo.wanandroid.ui.Main.acrivity.MainPageActivity;
import com.example.lenovo.wanandroid.ui.login.activity.LoginActivity;
import com.example.lenovo.wanandroid.ui.navigation.acrivity.NavigationActivity;
import com.example.lenovo.wanandroid.utils.CircularAnimUtil;
import com.example.lenovo.wanandroid.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationAdapter extends RecyclerView.Adapter {

    private List<NavigationBean.DataBean> list;
    private Context context;
    private OnItemClick li;

    public NavigationAdapter(List<NavigationBean.DataBean> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.navigation_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv.setText(list.get(pos).getName());
        if (pos % 4 == 0) {
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else if (pos % 4 == 1) {
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.gold_title));
        } else if (pos % 4 == 2) {
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.light_red));
        } else if (pos % 4 == 3) {
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
        List<NavigationBean.DataBean.ArticlesBean> bean = list.get(pos).getArticles();
//        holder.fl.removeAllViews();
        for (int i = 0; i < bean.size(); i++) {
//            TextView view = new TextView(context);
            TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.item_flow, null);
            view.setText(bean.get(i).getTitle());
            view.setTextColor(Color.BLUE);
//            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            if (i % 4 == 0) {
                view.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else if (i % 4 == 1) {
                view.setTextColor(ContextCompat.getColor(context, R.color.gold_title));
            } else if (i % 4 == 2) {
                view.setTextColor(ContextCompat.getColor(context, R.color.light_red));
            } else if (i % 4 == 3) {
                view.setTextColor(ContextCompat.getColor(context, R.color.blue));
            }

            int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,MainPageActivity.class);
                    intent.setClass(context, MainPageActivity.class);
                    intent.putExtra("title",bean.get(finalI).getTitle());
                    intent.putExtra("link",bean.get(finalI).getLink());
                    intent.putExtra("author",bean.get(finalI).getAuthor());
                    intent.putExtra("chapterName",bean.get(finalI).getChapterName()+"/"+bean.get(finalI).getSuperChapterName());
                    intent.putExtra("niceDate",bean.get(finalI).getNiceDate());
                    CircularAnimUtil.startActivity((Activity) context, intent,view, R.color.fab_bg);
                }
            });

            holder.fl.addView(view);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<NavigationBean.DataBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }


        static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
            @BindView(R.id.fl)
            FlowLayout fl;

        ViewHolder(View view) {
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
