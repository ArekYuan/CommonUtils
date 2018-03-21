package cn.yaohl.MayorOnline.ui.home.beans;

import cn.yaohl.MayorOnline.ui.BaseResp;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class CommentResp extends BaseResp {
    private int resourceId;
    private String vipName;
    private String date;
    private String vipContent;
    private String mayorContent;

    public CommentResp(int resourceId, String vipName, String date, String vipContent, String mayorContent) {
        this.resourceId = resourceId;
        this.vipName = vipName;
        this.date = date;
        this.vipContent = vipContent;
        this.mayorContent = mayorContent;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVipContent() {
        return vipContent;
    }

    public void setVipContent(String vipContent) {
        this.vipContent = vipContent;
    }

    public String getMayorContent() {
        return mayorContent;
    }

    public void setMayorContent(String mayorContent) {
        this.mayorContent = mayorContent;
    }
}
