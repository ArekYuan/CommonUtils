package cn.yaohl.MayorOnline.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseFragment;
import cn.yaohl.MayorOnline.ui.home.adapter.HistoryAdapter;
import cn.yaohl.MayorOnline.ui.home.beans.HistoryVideoResp;
import cn.yaohl.MayorOnline.util.view.HorizontalListView;

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

    private HorizontalListView historyViewListView;
    private HistoryAdapter histroyAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    public void onViewInitialized(View view, Bundle savedInstanceState) {
        initView(view);
        initListener();
        initHisData();
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
        historyViewListView = (HorizontalListView) v.findViewById(R.id.historyViewListView);

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
        historyViewListView.setAdapter(histroyAdapter);
        List<HistoryVideoResp> mData = new ArrayList<>();
        mData.add(new HistoryVideoResp(R.mipmap.pic2, "藍紹敏", "南京市長藍紹敏做客市長在線", "12,360"));
        mData.add(new HistoryVideoResp(R.mipmap.pic3, "藍紹敏", "常州市長藍紹敏做客市長在線", "12,361"));
        mData.add(new HistoryVideoResp(R.mipmap.pic2, "藍紹敏", "武漢市長藍紹敏做客市長在線", "12,362"));

        histroyAdapter.setmData(mData);
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
