package cn.yaohl.MayorOnline.ui.chart.beans;

import cn.yaohl.MayorOnline.ui.BaseResp;

/**
 * Created by 袁光跃 on 2018/3/24 0024.
 * 举措
 */

public class MeasureResp extends BaseResp {

    private String content;
    private String commentNum;
    private String messageNum;

    public MeasureResp(String content, String commentNum, String messageNum) {
        this.content = content;
        this.commentNum = commentNum;
        this.messageNum = messageNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(String messageNum) {
        this.messageNum = messageNum;
    }


}
