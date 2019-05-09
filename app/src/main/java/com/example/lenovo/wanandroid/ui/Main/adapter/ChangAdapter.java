package com.example.lenovo.wanandroid.ui.Main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.model.bean.main.ChangBean;
import com.example.lenovo.wanandroid.widget.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangAdapter extends RecyclerView.Adapter {


    private List<ChangBean.DataBean> list;
    private Context context;

    public ChangAdapter(List<ChangBean.DataBean> list, Context context) {


        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.chang_item, null);

        ViewHolder viewHolder = new ViewHolder(inflate);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.fl.removeAllViews();
        TextView tv = new TextView(context);
        tv.setText(list.get(i).getName());
        holder.fl.addView(tv);
        if (i % 4 == 0) {
            tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else if (i % 4 == 1) {
            tv.setTextColor(ContextCompat.getColor(context, R.color.gold_title));
        } else if (i % 4 == 2) {
            tv.setTextColor(ContextCompat.getColor(context, R.color.light_red));
        } else if (i % 4 == 3) {
            tv.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<ChangBean.DataBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fl)
        FlowLayout fl;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
