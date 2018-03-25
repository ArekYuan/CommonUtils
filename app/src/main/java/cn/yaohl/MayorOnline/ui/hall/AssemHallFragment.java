package cn.yaohl.MayorOnline.ui.hall;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseFragment;
import cn.yaohl.MayorOnline.ui.hall.adapter.IdeaAdapter;
import cn.yaohl.MayorOnline.ui.hall.adapter.MeasureAdapter;
import cn.yaohl.MayorOnline.ui.hall.beans.IdeasResp;
import cn.yaohl.MayorOnline.ui.hall.beans.MeasureResp;

/**
 * Created by 袁光跃 on 2018/3/19 0019.
 * 市长参议厅
 */

public class AssemHallFragment extends BaseFragment implements View.OnClickListener {

    //内容标题
    private TextView MessageContentTxt;

    //banner
    private ImageView bannerImg;

    //更多理念
    private TextView moreIdeaTxt;

    //理念 recyclerView
    private RecyclerView ideaRLView;
    private IdeaAdapter ideaAdapter;

    //举措
    private TextView moreMeasureTxt;
    private RecyclerView measureRLView;
    private MeasureAdapter measureAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_assem_hall_layout;
    }

    @Override
    public void onViewInitialized(View view, Bundle savedInstanceState) {
        initView(view);
        initListener();
        initIdeaData();
        initMeasureData();
    }


    private void initView(View v) {
        MessageContentTxt = (TextView) v.findViewById(R.id.MessageContentTxt);
        bannerImg = (ImageView) v.findViewById(R.id.bannerImg);

        moreIdeaTxt = (TextView) v.findViewById(R.id.moreIdeaTxt);
        ideaRLView = (RecyclerView) v.findViewById(R.id.ideaRLView);

        moreMeasureTxt = (TextView) v.findViewById(R.id.moreMeasureTxt);
        measureRLView = (RecyclerView) v.findViewById(R.id.measureRLView);
    }


    private void initListener() {
        moreIdeaTxt.setOnClickListener(this);
        moreMeasureTxt.setOnClickListener(this);
    }


    /**
     * 初始化 理念 数据
     */
    private void initIdeaData() {
        ideaAdapter = new IdeaAdapter(mContext);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(mContext,
                                          LinearLayoutManager.VERTICAL,
                                          false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ideaRLView.setLayoutManager(linearLayoutManager);
        ideaRLView.setFocusable(false);
        ideaRLView.setAdapter(ideaAdapter);
        List<IdeasResp> respList = new ArrayList<>();
        respList.add(new IdeasResp("强化城市治理，为民众谋福祉！", "1,290", "390"));
        respList.add(new IdeasResp("人民群众无小事，落实提高办事效率与结果满意度相结合", "2,350", "1,121"));
        respList.add(new IdeasResp("强化城市治理，为民众谋福祉！", "1,290", "390"));
        respList.add(new IdeasResp("人民群众无小事，落实提高办事效率与结果满意度相结合", "2,350", "1,121"));
        ideaAdapter.setmData(respList);

    }


    /**
     * 初始化 举措 数据
     */
    private void initMeasureData() {

        measureAdapter = new MeasureAdapter(mContext);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(mContext,
                                          LinearLayoutManager.VERTICAL,
                                          false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        measureRLView.setLayoutManager(linearLayoutManager);
        measureRLView.setAdapter(measureAdapter);
        List<MeasureResp> respList = new ArrayList<>();
        respList.add(new MeasureResp("大力发展双创产业，落实相关配套政策！", "1,290", "390"));
        respList.add(new MeasureResp("引进高新技术企业的落户与政策的完善", "2,350", "1,121"));
        respList.add(new MeasureResp("针对老年服务政策的配套落实与推进", "1,290", "390"));
        respList.add(new MeasureResp("狠抓治安乱像，坚决打击", "2,350", "1,121"));
        respList.add(new MeasureResp("严厉处理拖欠农名工工资现象", "2,350", "1,121"));
        measureAdapter.setmData(respList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moreIdeaTxt:
                break;
            case R.id.moreMeasureTxt:
                break;
        }
    }
}
