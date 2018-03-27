package cn.yaohl.MayorOnline.ui.personal.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseFragment;
import cn.yaohl.MayorOnline.ui.personal.adapter.HistoryAdapter;
import cn.yaohl.MayorOnline.ui.personal.beans.HistoryResp;

/**
 * Created by ygy on 2018\3\27 0027.
 * 回复历史
 */

public class HistoryFragment extends BaseFragment {
    private RecyclerView historyRLView;
    private HistoryAdapter adapter;
//    private List<HistoryResp> mData = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_history_layout;
    }

    @Override
    public void onViewInitialized(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        historyRLView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new HistoryAdapter(mContext);
//        commentRclView.addItemDecoration(
//                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        historyRLView.setAdapter(adapter);
        List<HistoryResp> mDataList = new ArrayList<>();

        mDataList.add(new HistoryResp("11", "周一", getResources().getString(R.string.comment_content_txt),
                getResources().getString(R.string.mayor_content_txt)));
        mDataList.add(new HistoryResp("12", "周二", getResources().getString(R.string.comment_content_txt),
                getResources().getString(R.string.mayor_content_txt)));
        mDataList.add(new HistoryResp("13", "周三", getResources().getString(R.string.comment_content_txt),
                getResources().getString(R.string.mayor_content_txt)));
        mDataList.add(new HistoryResp("14", "周四", getResources().getString(R.string.comment_content_txt),
                getResources().getString(R.string.mayor_content_txt)));
        mDataList.add(new HistoryResp("15", "周五", getResources().getString(R.string.comment_content_txt),
                getResources().getString(R.string.mayor_content_txt)));

        adapter.setmData(mDataList);
        adapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                showShortToast("" + position);
            }
        });
    }

    private void initView(View view) {
        historyRLView = (RecyclerView) view.findViewById(R.id.historyRLView);
    }
}
