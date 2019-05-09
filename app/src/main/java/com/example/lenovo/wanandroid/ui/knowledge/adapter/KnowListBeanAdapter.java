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
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowLedgeListBean;
import com.example.lenovo.wanandroid.model.bean.knowledge.KnowledgeBean;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class KnowListBeanAdapter extends RecyclerView.Adapter {

    List<KnowLedgeListBean.DataBean.DatasBean> list;
    private List<KnowledgeBean.DataBean> dataBeans;
    Context context;
    private OnItemClick li;

    public KnowListBeanAdapter(List<KnowLedgeListBean.DataBean.DatasBean> list, List<KnowledgeBean.DataBean> dataBeans, Context context) {
        this.list = list;
        this.dataBeans = dataBeans;
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

//        if (i % 4 == 0) {
//            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
//        } else if (i % 4 == 1) {
//            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.gold_title));
//        } else if (i % 4 == 2) {
//            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.light_red));
//        } else if (i % 4 == 3) {
            holder.mainTv1.setTextColor(ContextCompat.getColor(context, R.color.blue));
//        }

        holder.mainTv4.setText(list.get(i).getNiceDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                li.OnItem(i);
            }
        });
//        List<KnowledgeBean.DataBean.ChildrenBean> children = dataBeans.get(i).getChildren();
//        holder.mainTv1.setText(dataBeans.get(i).getName()+"/"+children.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<KnowledgeBean.DataBean> data) {
        dataBeans.addAll(data);
        notifyDataSetChanged();
    }

    public void addData1(List<KnowLedgeListBean.DataBean.DatasBean> datas) {
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
