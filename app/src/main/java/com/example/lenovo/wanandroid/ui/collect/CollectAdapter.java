package com.example.lenovo.wanandroid.ui.collect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.model.bean.dao.DbBean;
import com.example.lenovo.wanandroid.utils.MyDbHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectAdapter extends RecyclerView.Adapter {
    private List<DbBean> list;
    private Context context;
    private OnItemClick li;

    public CollectAdapter(List<DbBean> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.collect_item, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mainTv.setText(list.get(i).getName());
        holder.mainTv1.setText(list.get(i).getChanlename());
        holder.mainTv3.setText(list.get(i).getTitle());
        holder.mainTv4.setText(list.get(i).getTime());

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b==true){
                   DbBean dbBean = new DbBean();
                   dbBean.setId(list.get(i).getId());
                   dbBean.setId(list.get(i).getId());
                   dbBean.setImg(list.get(i).getImg());
                   dbBean.setName(list.get(i).getName());
                   dbBean.setChanlename(list.get(i).getChanlename());
                   dbBean.setTime(list.get(i).getTime());
                   dbBean.setTitle(list.get(i).getTitle());
                   MyDbHelper.getMyDbHelper().delete(dbBean);
               }
               list.remove(list.get(i));
               notifyDataSetChanged();
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<DbBean> dbBeans) {
        list.addAll(dbBeans);
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
