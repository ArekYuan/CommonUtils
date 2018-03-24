package cn.yaohl.MayorOnline.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseFragment;
import cn.yaohl.MayorOnline.ui.home.adapter.CommentAdapter;
import cn.yaohl.MayorOnline.ui.home.adapter.HistoryAdapter;
import cn.yaohl.MayorOnline.ui.home.beans.CommentResp;
import cn.yaohl.MayorOnline.ui.home.beans.HistoryVideoResp;

/**
 * 作者：袁光跃
 * 日期：2018/1/18
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    //点赞
    private RelativeLayout praiseRLayout;
    private TextView praiseNumTxt;

    //信息
    private RelativeLayout messageRLayout;
    private TextView messageNumTxt;

    //禮物
    private RelativeLayout giftRLayout;
    private TextView giftNumTxt;

    //转发
    private RelativeLayout forwardRLayout;

    //更多往期内容
    private TextView moreVideoTxt;

    private RecyclerView historyViewListView;
    private HistoryAdapter histroyAdapter;

    int spanCount = 1; // 只显示一行


    //评论区
    private TextView moreVCommentTxt;
    private RecyclerView commentRclView;
    private CommentAdapter commentAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    public void onViewInitialized(View view, Bundle savedInstanceState) {
        initView(view);
        initListener();
        initHisData();
        initCommentData();
    }

    private void initView(View v) {
        praiseRLayout = (RelativeLayout) v.findViewById(R.id.praiseRLayout);
        praiseNumTxt = (TextView) v.findViewById(R.id.praiseNumTxt);

        messageRLayout = (RelativeLayout) v.findViewById(R.id.messageRLayout);
        messageNumTxt = (TextView) v.findViewById(R.id.messageNumTxt);

        giftRLayout = (RelativeLayout) v.findViewById(R.id.giftRLayout);
        giftNumTxt = (TextView) v.findViewById(R.id.giftNumTxt);

        forwardRLayout = (RelativeLayout) v.findViewById(R.id.forwardRLayout);

        moreVideoTxt = (TextView) v.findViewById(R.id.moreVideoTxt);
        historyViewListView = (RecyclerView) v.findViewById(R.id.historyViewListView);

        commentRclView = (RecyclerView) v.findViewById(R.id.commentRclView);
        moreVCommentTxt = (TextView) v.findViewById(R.id.moreVCommentTxt);

    }

    private void initListener() {
        praiseRLayout.setOnClickListener(this);
        messageRLayout.setOnClickListener(this);
        forwardRLayout.setOnClickListener(this);
        giftRLayout.setOnClickListener(this);
        moreVideoTxt.setOnClickListener(this);
    }

    private void initHisData() {
        histroyAdapter = new HistoryAdapter(mContext);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(spanCount,
                                               StaggeredGridLayoutManager.HORIZONTAL);
        historyViewListView.setLayoutManager(layoutManager);
        historyViewListView.setFocusable(false);
        historyViewListView.setAdapter(histroyAdapter);
        List<HistoryVideoResp> mData = new ArrayList<>();
        mData.add(new HistoryVideoResp(R.mipmap.pic2, "藍紹敏", "南京市長藍紹敏做客市長在線", "12,360"));
        mData.add(new HistoryVideoResp(R.mipmap.pic3, "藍紹敏", "常州市長藍紹敏做客市長在線", "12,361"));
        mData.add(new HistoryVideoResp(R.mipmap.pic2, "藍紹敏", "武漢市長藍紹敏做客市長在線", "12,362"));

        histroyAdapter.setmData(mData);
        histroyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initCommentData() {
        commentRclView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
        commentAdapter = new CommentAdapter(mContext);
//        commentRclView.addItemDecoration(
//                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        commentRclView.setAdapter(commentAdapter);
        List<CommentResp> mDataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mDataList.add(new CommentResp(R.mipmap.tx2, "南京用户xxxx6" + i, "2017-9-1" + i,
                                          getResources().getString(R.string.comment_content_txt),
                                          getResources().getString(R.string.mayor_content_txt)));
        }

        commentAdapter.setmData(mDataList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.praiseRLayout:
                Toast.makeText(mContext, "点赞能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.messageRLayout:
                Toast.makeText(mContext, "消息能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.giftRLayout:
                Toast.makeText(mContext, "禮物能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.forwardRLayout:
                Toast.makeText(mContext, "转发功能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.moreVideoTxt:
                Toast.makeText(mContext, "更多", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
