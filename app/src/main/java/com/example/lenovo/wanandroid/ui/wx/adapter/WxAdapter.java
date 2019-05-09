package com.example.lenovo.wanandroid.ui.wx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.model.bean.wx.WxListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WxAdapter extends RecyclerView.Adapter {

    public List<WxListBean.DataBean.DatasBean> list;
    Context context;
    private OnItemClick li ;

    public WxAdapter(List<WxListBean.DataBean.DatasBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.know_item, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mainTv.setText(list.get(i).getAuthor());
        holder.mainTv1.setText(list.get(i).getChapterName()+"/"+list.get(i).getSuperChapterName());
        holder.mainTv3.setText(list.get(i).getTitle());
        holder.mainTv4.setText(list.get(i).getNiceDate());
        holder.mainTv1.setTextColor(ContextCompat.getColor(context, R.color.blue));
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

    public void addData(List<WxListBean.DataBean.DatasBean> datas) {
        list.addAll(datas);
        notifyDataSetChanged();
    }

    public void addSerarch(List<WxListBean.DataBean.DatasBean> datas) {
        list.addAll(datas);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
