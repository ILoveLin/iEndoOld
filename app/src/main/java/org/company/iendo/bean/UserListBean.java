package org.company.iendo.bean;

import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 用户列表Bean
 */
public class UserListBean {

    private List<DsDTO> ds;

    public List<DsDTO> getDs() {
        return ds;
    }

    public void setDs(List<DsDTO> ds) {
        this.ds = ds;
    }

    public static class DsDTO {
        /**
         * UserID : 1
         * UserName : Admin
         * NickName :
         * Password : d41d8cd98f00b204e9800998ecf8427e
         * DepartmentID : 0
         * Des : 超级管理员
         * CanUSE : True
         * CreatedAt : 2018/9/5 18:20:12
         * LastLoginAt : 2020/12/10 14:31:04
         * LoginTimes : 1568
         */

        private String UserID;
        private String UserName;
        private String NickName;
        private String Password;
        private String DepartmentID;
        private String Des;
        private String CanUSE;
        private String CreatedAt;
        private String LastLoginAt;
        private String LoginTimes;

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getDepartmentID() {
            return DepartmentID;
        }

        public void setDepartmentID(String DepartmentID) {
            this.DepartmentID = DepartmentID;
        }

        public String getDes() {
            return Des;
        }

        public void setDes(String Des) {
            this.Des = Des;
        }

        public String getCanUSE() {
            return CanUSE;
        }

        public void setCanUSE(String CanUSE) {
            this.CanUSE = CanUSE;
        }

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String CreatedAt) {
            this.CreatedAt = CreatedAt;
        }

        public String getLastLoginAt() {
            return LastLoginAt;
        }

        public void setLastLoginAt(String LastLoginAt) {
            this.LastLoginAt = LastLoginAt;
        }

        public String getLoginTimes() {
            return LoginTimes;
        }

        public void setLoginTimes(String LoginTimes) {
            this.LoginTimes = LoginTimes;
        }
    }
}
