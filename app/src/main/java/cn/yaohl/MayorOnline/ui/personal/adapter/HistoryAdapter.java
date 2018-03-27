package cn.yaohl.MayorOnline.ui.personal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.personal.beans.HistoryResp;

import static android.graphics.Color.parseColor;

/**
 * Created by ygy on 2018\3\27 0027.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private List<HistoryResp> mData = new ArrayList<>();

    public HistoryAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }


    public void setmData(List<HistoryResp> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        HistoryResp resp = mData.get(position);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(resp.getMayorContentStr());
        stringBuilder.setSpan(new ForegroundColorSpan(parseColor("#f0686c")), 0, 5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.dateTxt.setText(resp.getDateStr());
        holder.weekTxt.setText(resp.getWeekStr());
        holder.commentContentTxt.setText(resp.getCommentStr());
        holder.mayorContentTxt.setText(stringBuilder);

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
        TextView dateTxt;
        TextView weekTxt;
        TextView commentContentTxt;
        TextView mayorContentTxt;

        ViewHolder(View v) {
            super(v);
            dateTxt = (TextView) v.findViewById(R.id.dateTxt);
            weekTxt = (TextView) v.findViewById(R.id.weekTxt);
            commentContentTxt = (TextView) v.findViewById(R.id.commentContentTxt);
            mayorContentTxt = (TextView) v.findViewById(R.id.mayorContentTxt);
        }
    }
}
