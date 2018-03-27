package cn.yaohl.MayorOnline.ui.personal.beans;

import cn.yaohl.MayorOnline.ui.BaseResp;

/**
 * Created by ygy on 2018\3\27 0027.
 */

public class HistoryResp extends BaseResp {

    private String dateStr;
    private String weekStr;
    private String commentStr;
    private String mayorContentStr;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getWeekStr() {
        return weekStr;
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public String getCommentStr() {
        return commentStr;
    }

    public void setCommentStr(String commentStr) {
        this.commentStr = commentStr;
    }

    public String getMayorContentStr() {
        return mayorContentStr;
    }

    public void setMayorContentStr(String mayorContentStr) {
        this.mayorContentStr = mayorContentStr;
    }

    public HistoryResp(String dateStr, String weekStr, String commentStr, String mayorContentStr) {
        this.dateStr = dateStr;
        this.weekStr = weekStr;
        this.commentStr = commentStr;
        this.mayorContentStr = mayorContentStr;
    }
}
