package org.company.iendo.bean;

import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 登录
 */
public class LoginBean {

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
         * LastLoginAt : 2020/11/26 9:38:05
         * LoginTimes : 720
         * UserMan : True
         * CanPsw : True
         * CanNew : True
         * CanEdit : True
         * CanDelete : True
         * CanPrint : True
         * ReportStyle : True
         * DictsMan : True
         * GlossaryMan : True
         * TempletMan : True
         * HospitalInfo : True
         * CanBackup : True
         * ViewBackup : True
         * VideoSet : True
         * OnlySelf : False
         * UnPrinted : False
         * FtpSet : False
         * ChangeDepartment : False
         * ExportRecord : False
         * ExportImage : False
         * ExportVideo : False
         * Role : 0
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
        private String UserMan;
        private String CanPsw;
        private String CanNew;
        private String CanEdit;
        private String CanDelete;
        private String CanPrint;
        private String ReportStyle;
        private String DictsMan;
        private String GlossaryMan;
        private String TempletMan;
        private String HospitalInfo;
        private String CanBackup;
        private String ViewBackup;
        private String VideoSet;
        private String OnlySelf;
        private String UnPrinted;
        private String FtpSet;
        private String ChangeDepartment;
        private String ExportRecord;
        private String ExportImage;
        private String ExportVideo;
        private String Role;

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

        public String getUserMan() {
            return UserMan;
        }

        public void setUserMan(String UserMan) {
            this.UserMan = UserMan;
        }

        public String getCanPsw() {
            return CanPsw;
        }

        public void setCanPsw(String CanPsw) {
            this.CanPsw = CanPsw;
        }

        public String getCanNew() {
            return CanNew;
        }

        public void setCanNew(String CanNew) {
            this.CanNew = CanNew;
        }

        public String getCanEdit() {
            return CanEdit;
        }

        public void setCanEdit(String CanEdit) {
            this.CanEdit = CanEdit;
        }

        public String getCanDelete() {
            return CanDelete;
        }

        public void setCanDelete(String CanDelete) {
            this.CanDelete = CanDelete;
        }

        public String getCanPrint() {
            return CanPrint;
        }

        public void setCanPrint(String CanPrint) {
            this.CanPrint = CanPrint;
        }

        public String getReportStyle() {
            return ReportStyle;
        }

        public void setReportStyle(String ReportStyle) {
            this.ReportStyle = ReportStyle;
        }

        public String getDictsMan() {
            return DictsMan;
        }

        public void setDictsMan(String DictsMan) {
            this.DictsMan = DictsMan;
        }

        public String getGlossaryMan() {
            return GlossaryMan;
        }

        public void setGlossaryMan(String GlossaryMan) {
            this.GlossaryMan = GlossaryMan;
        }

        public String getTempletMan() {
            return TempletMan;
        }

        public void setTempletMan(String TempletMan) {
            this.TempletMan = TempletMan;
        }

        public String getHospitalInfo() {
            return HospitalInfo;
        }

        public void setHospitalInfo(String HospitalInfo) {
            this.HospitalInfo = HospitalInfo;
        }

        public String getCanBackup() {
            return CanBackup;
        }

        public void setCanBackup(String CanBackup) {
            this.CanBackup = CanBackup;
        }

        public String getViewBackup() {
            return ViewBackup;
        }

        public void setViewBackup(String ViewBackup) {
            this.ViewBackup = ViewBackup;
        }

        public String getVideoSet() {
            return VideoSet;
        }

        public void setVideoSet(String VideoSet) {
            this.VideoSet = VideoSet;
        }

        public String getOnlySelf() {
            return OnlySelf;
        }

        public void setOnlySelf(String OnlySelf) {
            this.OnlySelf = OnlySelf;
        }

        public String getUnPrinted() {
            return UnPrinted;
        }

        public void setUnPrinted(String UnPrinted) {
            this.UnPrinted = UnPrinted;
        }

        public String getFtpSet() {
            return FtpSet;
        }

        public void setFtpSet(String FtpSet) {
            this.FtpSet = FtpSet;
        }

        public String getChangeDepartment() {
            return ChangeDepartment;
        }

        public void setChangeDepartment(String ChangeDepartment) {
            this.ChangeDepartment = ChangeDepartment;
        }

        public String getExportRecord() {
            return ExportRecord;
        }

        public void setExportRecord(String ExportRecord) {
            this.ExportRecord = ExportRecord;
        }

        public String getExportImage() {
            return ExportImage;
        }

        public void setExportImage(String ExportImage) {
            this.ExportImage = ExportImage;
        }

        public String getExportVideo() {
            return ExportVideo;
        }

        public void setExportVideo(String ExportVideo) {
            this.ExportVideo = ExportVideo;
        }

        public String getRole() {
            return Role;
        }

        public void setRole(String Role) {
            this.Role = Role;
        }
    }
}
