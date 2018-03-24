package cn.yaohl.MayorOnline.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class ProListAdapter extends BaseAdapter {

    private List<String> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public ProListAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setmData(List<String> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.mData == null ? 0 : this.mData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.citys_item_layout, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cityNameTxt.setText(mData.get(position));
        return convertView;
    }

    static class ViewHolder {

        TextView cityNameTxt;

        ViewHolder(View v) {
            cityNameTxt = (TextView) v.findViewById(R.id.cityNameTxt);
            v.setTag(this);
        }
    }
}
