package cn.yaohl.MayorOnline.ui.personal.beans;

import cn.yaohl.MayorOnline.ui.BaseResp;

/**
 * Created by ygy on 2018\3\27 0027.
 */

public class AnswerResp extends BaseResp {
    private int resourcesId;
    private String title;
    private String mayorName;
    private String praiseNum;

    public int getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(int resourcesId) {
        this.resourcesId = resourcesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMayorName() {
        return mayorName;
    }

    public void setMayorName(String mayorName) {
        this.mayorName = mayorName;
    }

    public String getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }

    public AnswerResp(int resourcesId, String title, String mayorName, String praiseNum) {
        this.resourcesId = resourcesId;
        this.title = title;
        this.mayorName = mayorName;
        this.praiseNum = praiseNum;
    }
}
