package cn.yaohl.MayorOnline.ui.lmessage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yaohl.retrofitlib.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseFragment;
import cn.yaohl.MayorOnline.ui.lmessage.adapter.MessageListAdapter;
import cn.yaohl.MayorOnline.ui.lmessage.beans.MessageListResp;

/**
 * Created by 袁光跃 on 2018/3/19 0019.
 * 给市长留言
 */

public class LMessageFragment extends BaseFragment implements View.OnClickListener {

    //加载更多 留言列表
    private TextView moreMessageTxt;

    //我的留言 列表
    private RecyclerView messageListRLView;
    private MessageListAdapter listAdapter;

    //参与留言
    //标题
    private EditText messageTitleEdTxt;

    //类型
    private EditText contentTypeEdTxt;

    //相关部门
    private EditText reDepartEdTxt;

    //内容
    private EditText contentEdTxt;

    //提交按钮
    private Button commitBtn;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_lmessage_layout;
    }

    @Override
    public void onViewInitialized(View view, Bundle savedInstanceState) {

        initView(view);

        initListData();
    }


    private void initView(View v) {
        moreMessageTxt = (TextView) v.findViewById(R.id.moreMessageTxt);
        messageListRLView = (RecyclerView) v.findViewById(R.id.messageListRLView);

        messageTitleEdTxt = (EditText) v.findViewById(R.id.messageTitleEdTxt);
        contentTypeEdTxt = (EditText) v.findViewById(R.id.contentTypeEdTxt);
        reDepartEdTxt = (EditText) v.findViewById(R.id.reDepartEdTxt);
        contentEdTxt = (EditText) v.findViewById(R.id.contentEdTxt);
        commitBtn = (Button) v.findViewById(R.id.commitBtn);

        commitBtn.setOnClickListener(this);
        moreMessageTxt.setOnClickListener(this);
    }

    private void initListData() {
        listAdapter = new MessageListAdapter(mContext);
        messageListRLView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
        messageListRLView.setFocusable(false);
        listAdapter = new MessageListAdapter(mContext);
//        commentRclView.addItemDecoration(
//                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        messageListRLView.setAdapter(listAdapter);
        List<MessageListResp> mDataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mDataList.add(new MessageListResp(R.mipmap.tx2, "南京用户xxxx61", "2017-9-1" + i,
                                              getResources().getString(
                                                      R.string.comment_content_txt),
                                              getResources().getString(
                                                      R.string.mayor_content_txt)));
        }

        listAdapter.setmData(mDataList);
    }

    @Override
    public void onClick(View v) {
        String titleStr = messageTitleEdTxt.getText().toString();
        String typeStr = contentTypeEdTxt.getText().toString();
        String reDepartStr = reDepartEdTxt.getText().toString();
        String contentStr = contentEdTxt.getText().toString();

        switch (v.getId()) {
            case R.id.moreMessageTxt:
                showShortToast("没有更多了");
                break;

            case R.id.commitBtn:
                if (isChecked(titleStr, typeStr, reDepartStr, contentStr)) {
                    doCommit(titleStr, typeStr, reDepartStr, contentStr);
                }
                break;
        }
    }

    /**
     * 对接服务器 请求数据
     *
     * @param titleStr
     * @param typeStr
     * @param reDepartStr
     * @param contentStr
     */
    private void doCommit(String titleStr, String typeStr, String reDepartStr, String contentStr) {
        showShortToast("已提交，请耐心等候！");
    }


    /**
     * 检查 是否都有数据
     *
     * @param titleStr
     * @param typeStr
     * @param reDepartStr
     * @param contentStr
     * @return
     */
    private boolean isChecked(String titleStr, String typeStr, String reDepartStr, String contentStr) {
        boolean b;
        if (StringUtil.isEmpty(titleStr)) {
            showShortToast("请输入主题！");
            b = false;
        } else if (StringUtil.isEmail(typeStr)) {
            showShortToast("请输入类型！");
            b = false;
        } else if (StringUtil.isEmail(reDepartStr)) {
            showShortToast("请输入部门！");
            b = false;
        } else if (StringUtil.isEmail(contentStr)) {
            showShortToast("请输入内容！");
            b = false;
        } else {
            b = true;
        }

        return b;
    }
}
