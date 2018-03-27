package cn.yaohl.MayorOnline.ui.personal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.personal.beans.AnswerResp;

/**
 * Created by ygy on 2018\3\27 0027.
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private List<AnswerResp> mData = new ArrayList<>();

    public AnswerAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }


    public void setmData(List<AnswerResp> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.answer_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        AnswerResp resp = mData.get(position);
        holder.contentIv.setImageResource(resp.getResourcesId());
        holder.answerTitleTxt.setText(resp.getTitle());
        holder.nameTxt.setText(resp.getMayorName());
        holder.praiseNumTxt.setText(resp.getPraiseNum());

    }

    @Override
    public int getItemCount() {
        return this.mData == null ? 0 : this.mData.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contentIv;
        TextView answerTitleTxt;
        TextView nameTxt;
        TextView praiseNumTxt;

        ViewHolder(View v) {
            super(v);
            contentIv = (ImageView) v.findViewById(R.id.contentIv);
            answerTitleTxt = (TextView) v.findViewById(R.id.answerTitleTxt);
            nameTxt = (TextView) v.findViewById(R.id.nameTxt);
            praiseNumTxt = (TextView) v.findViewById(R.id.praiseNumTxt);
        }
    }
}
