package com.example.lenovo.wanandroid.ui.wx.adapter;

import android.content.Context;
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

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.app.Constants;
import com.example.lenovo.wanandroid.model.bean.dao.DbBean;
import com.example.lenovo.wanandroid.model.bean.wx.WxListBean;
import com.example.lenovo.wanandroid.utils.MyDbHelper;
import com.example.lenovo.wanandroid.utils.SpUtil;

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

        List<DbBean> dbBeans = MyDbHelper.getMyDbHelper().query1(list.get(i).getAuthor(), list.get(i).getTitle());
        SpUtil.setParam(Constants.COLLECT,dbBeans);
        if (dbBeans!=null && dbBeans.size()>0){
            holder.cb.setChecked(true);
        }else{
            holder.cb.setChecked(false);
        }

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        @BindView(R.id.cb)
        CheckBox cb;

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
