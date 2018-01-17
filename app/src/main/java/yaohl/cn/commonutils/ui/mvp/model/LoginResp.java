package yaohl.cn.commonutils.ui.mvp.model;

import yaohl.cn.commonutils.util.http.BaseResp;

/**
 * 作者：LX
 * 日期：2018/1/17
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class LoginResp extends BaseResp {
    private DataBean data;
    private String success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * username : admin
         * realname : 管理员
         * status : 1
         * userIcon : group1/M00/00/9B/CvoLH1fIHvSABUCMAACVoUtUfm0937.png
         * email :
         * mobile : 11111111111
         * sex : 0
         * position : 管理员
         * tgc : 85F9D075A0D14D6CB39BC1D85AB1316F
         */

        private String username;
        private String realname;
        private String status;
        private String userIcon;
        private String email;
        private String mobile;
        private String sex;
        private String position;
        private String tgc;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTgc() {
            return tgc;
        }

        public void setTgc(String tgc) {
            this.tgc = tgc;
        }
    }
}
