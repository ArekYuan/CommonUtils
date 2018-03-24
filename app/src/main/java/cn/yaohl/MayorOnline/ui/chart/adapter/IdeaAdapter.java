package cn.yaohl.MayorOnline.ui.chart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.chart.beans.IdeasResp;

/**
 * Created by ygy on 2018/3/24 0024.
 * 施政理念 适配器
 */

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolder> {


    private List<IdeasResp> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;


    public void setmData(List<IdeasResp> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public IdeaAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.idea_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        IdeasResp resp = mData.get(position);
        holder.itemContentTxt.setText(resp.getContent());
        holder.praiseNumTxt.setText(resp.getCommentNum());
        holder.messageNumTxt.setText(resp.getMessageNum());
        holder.praiseRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("点赞" + position);
            }
        });
        holder.messageRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("回复" + position);
            }
        });

        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("我要点评" + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mData == null ? 0 : this.mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemContentTxt;
        RelativeLayout praiseRLayout;
        TextView praiseNumTxt;
        RelativeLayout messageRLayout;
        TextView messageNumTxt;
        RelativeLayout forwardRLayout;
        Button commentBtn;

        ViewHolder(View v) {
            super(v);
            itemContentTxt = (TextView) v.findViewById(R.id.itemContentTxt);
            praiseRLayout = (RelativeLayout) v.findViewById(R.id.praiseRLayout);
            praiseNumTxt = (TextView) v.findViewById(R.id.praiseNumTxt);
            messageRLayout = (RelativeLayout) v.findViewById(R.id.messageRLayout);
            messageNumTxt = (TextView) v.findViewById(R.id.messageNumTxt);
            forwardRLayout = (RelativeLayout) v.findViewById(R.id.forwardRLayout);
            commentBtn = (Button) v.findViewById(R.id.commentBtn);
        }
    }

    private void showToast(String txt) {
        Toast.makeText(mContext, txt, Toast.LENGTH_SHORT).show();
    }
}
