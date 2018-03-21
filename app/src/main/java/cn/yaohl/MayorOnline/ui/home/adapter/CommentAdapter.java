package cn.yaohl.MayorOnline.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.home.beans.CommentResp;

import static android.graphics.Color.parseColor;

/**
 * Created by Administrator on 2018/3/21 0021.
 * 评论区
 */

public class CommentAdapter extends
        RecyclerView.Adapter<CommentAdapter.ViewHolder>
        implements View.OnClickListener {

    private List<CommentResp> mData = new ArrayList<>();

    private Context mContext;
    private LayoutInflater mInflater;

    public CommentAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setmData(List<CommentResp> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comment_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentResp resp = mData.get(position);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(resp.getMayorContent());
        stringBuilder.setSpan(new ForegroundColorSpan(parseColor("#f0686c")), 0, 5,
                              Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.commentImg.setImageResource(resp.getResourceId());
        holder.commentNameTxt.setText(resp.getVipName());
        holder.commentDateTxt.setText(resp.getDate());
        holder.commentContentTxt.setText(resp.getVipContent());
        holder.mayorContentTxt.setText(stringBuilder);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView commentImg;
        TextView commentNameTxt;
        TextView commentDateTxt;
        TextView commentContentTxt;
        TextView mayorContentTxt;

        ViewHolder(View v) {
            super(v);
            commentImg = (ImageView) v.findViewById(R.id.commentImg);
            commentNameTxt = (TextView) v.findViewById(R.id.commentNameTxt);
            commentDateTxt = (TextView) v.findViewById(R.id.commentDateTxt);
            commentContentTxt = (TextView) v.findViewById(R.id.commentContentTxt);
            mayorContentTxt = (TextView) v.findViewById(R.id.mayorContentTxt);
        }
    }
}
