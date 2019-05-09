package com.example.lenovo.wanandroid.ui.knowledge.adapter;

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
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowledgeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KnowAdapter extends RecyclerView.Adapter {
    private List<KnowledgeBean.DataBean> list;
    private Context context;
    private OnItemClick li;

    public KnowAdapter(List<KnowledgeBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.know, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv.setText(list.get(i).getName());
        if (i % 4 == 0) {
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else if (i % 4 == 1) {
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.gold_title));
        } else if (i % 4 == 2) {
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.light_red));
        } else if (i % 4 == 3) {
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
        List<KnowledgeBean.DataBean.ChildrenBean> children = list.get(i).getChildren();
        StringBuilder stringBuilder = new StringBuilder();

        for (KnowledgeBean.DataBean.ChildrenBean bean:children) {
            stringBuilder.append(bean.getName()).append("     ");
        }
        holder.tv1.setText(stringBuilder.toString());

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

    public void addData(List<KnowledgeBean.DataBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.knowledge_hierarchy_enter_iv)
        ImageView img;
        @BindView(R.id.item_knowledge_hierarchy_title)
        TextView tv;
        @BindView(R.id.item_knowledge_hierarchy_content)
        TextView tv1;

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
