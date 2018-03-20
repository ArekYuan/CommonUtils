package cn.yaohl.MayorOnline.ui.home.beans;

import cn.yaohl.MayorOnline.ui.BaseResp;

/**
 * Created by 袁光跃 on 2018/3/20 0020.
 */

public class HistoryVideoResp extends BaseResp {
    private int resourceId;
    private String name;
    private String content;
    private String numStr;

    public HistoryVideoResp(int resourceId, String name, String content, String numStr) {
        this.resourceId = resourceId;
        this.name = name;
        this.content = content;
        this.numStr = numStr;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNumStr() {
        return numStr;
    }

    public void setNumStr(String numStr) {
        this.numStr = numStr;
    }
}
