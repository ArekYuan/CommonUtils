package cn.yaohl.MayorOnline.ui.home.adapter;

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
import cn.yaohl.MayorOnline.ui.home.beans.HistoryVideoResp;

/**
 * Created by 袁光跃 on 2018/3/20 0020.
 * 历史 直播记录
 */

public class HistoryAdapter extends
        RecyclerView.Adapter<HistoryAdapter.ViewHolder>
        implements View.OnClickListener {

    private List<HistoryVideoResp> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public HistoryAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }


    public void setmData(List<HistoryVideoResp> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_video_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryVideoResp resp = mData.get(position);
        holder.videoImg.setImageResource(resp.getResourceId());
        holder.nameTxt.setText(resp.getName());
        holder.contentTxt.setText(resp.getContent());
        holder.praiseNumTxt.setText(resp.getNumStr());
        holder.itemView.setTag(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemClick((Integer) v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView videoImg;
        TextView nameTxt;
        TextView contentTxt;
        TextView praiseNumTxt;

        ViewHolder(View v) {
            super(v);
            videoImg = (ImageView) v.findViewById(R.id.videoImg);
            nameTxt = (TextView) v.findViewById(R.id.nameTxt);
            contentTxt = (TextView) v.findViewById(R.id.contentTxt);
            praiseNumTxt = (TextView) v.findViewById(R.id.praiseNumTxt);
        }
    }
}
