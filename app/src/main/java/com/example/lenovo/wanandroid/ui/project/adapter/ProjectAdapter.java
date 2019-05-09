package com.example.lenovo.wanandroid.ui.project.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.model.bean.project.ProjectListBean;
import com.example.lenovo.wanandroid.utils.SpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectAdapter extends RecyclerView.Adapter {
    public List<ProjectListBean.DataBean.DatasBean> list;
    private Context context;
    private OnItemClick li;

    public ProjectAdapter(List<ProjectListBean.DataBean.DatasBean> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.project_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
//        Glide.with(context).load(list.get(i).getEnvelopePic()).into(holder.projectImg);
        holder.tv.setText(list.get(i).getTitle());
        holder.tv2.setText(list.get(i).getDesc());
        holder.tv3.setText(list.get(i).getNiceDate());
        holder.tv4.setText(list.get(i).getAuthor());

        Boolean img = (Boolean) SpUtil.getParam("img", false);
        if (img){
            Glide.with(context).load(R.drawable.ic_wan_android).into(holder.projectImg);
        }else {
            Glide.with(context).load(list.get(i).getEnvelopePic()).into(holder.projectImg);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                li.OnItem(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<ProjectListBean.DataBean.DatasBean> datas) {
        list.addAll(datas);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.project_img)
        ImageView projectImg;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv2)
        TextView tv2;
        @BindView(R.id.tv3)
        TextView tv3;
        @BindView(R.id.tv4)
        TextView tv4;

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
