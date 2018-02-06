package cn.yaohl.MayorOnline.ui.login.beans;

import cn.yaohl.MayorOnline.ui.BaseResp;

/**
 * 作者：袁光跃
 * 日期：2018/2/6
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class LoginResp extends BaseResp {
    /**
     * data : {"position":"文员","icon":"","birthday":"","sex":"0","nickname":"文海生01","indexShowType":"MAIN","lastlogin":1517310937000,"TGC":"5A2D9DC851004BC681BF80B0E5EE8D32","id":23694,"username":"wenhs01","email":"whs03142719@126.com","name":"文海生01","realname":"","qq":"","mobile":"15851483271"}
     * code : 0
     * msg : 登录成功！
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * position : 文员
         * icon :
         * birthday :
         * sex : 0
         * nickname : 文海生01
         * indexShowType : MAIN
         * lastlogin : 1517310937000
         * TGC : 5A2D9DC851004BC681BF80B0E5EE8D32
         * id : 23694
         * username : wenhs01
         * email : whs03142719@126.com
         * name : 文海生01
         * realname :
         * qq :
         * mobile : 15851483271
         */

        private String position;
        private String icon;
        private String birthday;
        private String sex;
        private String nickname;
        private String indexShowType;
        private long lastlogin;
        private String TGC;
        private int id;
        private String username;
        private String email;
        private String name;
        private String realname;
        private String qq;
        private String mobile;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIndexShowType() {
            return indexShowType;
        }

        public void setIndexShowType(String indexShowType) {
            this.indexShowType = indexShowType;
        }

        public long getLastlogin() {
            return lastlogin;
        }

        public void setLastlogin(long lastlogin) {
            this.lastlogin = lastlogin;
        }

        public String getTGC() {
            return TGC;
        }

        public void setTGC(String TGC) {
            this.TGC = TGC;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
