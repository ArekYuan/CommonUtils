package cn.yaohl.MayorOnline.ui.personal.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseFragment;
import cn.yaohl.MayorOnline.ui.personal.adapter.AnswerAdapter;
import cn.yaohl.MayorOnline.ui.personal.beans.AnswerResp;

/**
 * Created by ygy on 2018\3\27 0027.
 * 回复
 */

public class AnswerFragment extends BaseFragment {

    private RecyclerView answerRLView;
    private List<AnswerResp> mData = new ArrayList<>();
    private AnswerAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragmengt_answer_layout;
    }

    @Override
    public void onViewInitialized(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initView(View v) {
        answerRLView = (RecyclerView) v.findViewById(R.id.answerRLView);
    }

    private void initData() {
        answerRLView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new AnswerAdapter(mContext);
//        commentRclView.addItemDecoration(
//                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        answerRLView.setAdapter(adapter);
        List<AnswerResp> mDataList = new ArrayList<>();

        mDataList.add(new AnswerResp(R.mipmap.pic2, "南京市长做客市长在线!", "蓝绍敏", "1,231"));
        mDataList.add(new AnswerResp(R.mipmap.pic3, "南京市长做客市长在线!", "蓝绍敏", "1,130"));
        mDataList.add(new AnswerResp(R.mipmap.pic2, "南京市长做客市长在线!", "蓝绍敏", "1,030"));
        mDataList.add(new AnswerResp(R.mipmap.pic3, "南京市长做客市长在线!", "蓝绍敏", "1,110"));
        mDataList.add(new AnswerResp(R.mipmap.pic2, "南京市长做客市长在线!", "蓝绍敏", "1,191"));
        mDataList.add(new AnswerResp(R.mipmap.pic3, "南京市长做客市长在线!", "蓝绍敏", "1,050"));
        mDataList.add(new AnswerResp(R.mipmap.pic2, "南京市长做客市长在线!", "蓝绍敏", "1,389"));

        adapter.setmData(mDataList);
        adapter.setOnItemClickListener(new AnswerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                showShortToast("" + position);
            }
        });
    }

}
