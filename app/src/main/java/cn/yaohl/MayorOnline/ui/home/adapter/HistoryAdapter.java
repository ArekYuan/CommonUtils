package cn.yaohl.MayorOnline.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class HistoryAdapter extends BaseAdapter {

    private List<HistoryVideoResp> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

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
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.history_video_item_layout, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HistoryVideoResp resp = mData.get(position);
        holder.videoImg.setImageResource(resp.getResourceId());
        holder.nameTxt.setText(resp.getName());
        holder.contentTxt.setText(resp.getContent());
        holder.praiseNumTxt.setText(resp.getNumStr());
        return convertView;
    }

    static class ViewHolder {
        ImageView videoImg;
        TextView nameTxt;
        TextView contentTxt;
        TextView praiseNumTxt;

        ViewHolder(View v) {
            videoImg = (ImageView) v.findViewById(R.id.videoImg);
            nameTxt = (TextView) v.findViewById(R.id.nameTxt);
            contentTxt = (TextView) v.findViewById(R.id.contentTxt);
            praiseNumTxt = (TextView) v.findViewById(R.id.praiseNumTxt);
            v.setTag(this);
        }
    }
}
