package org.company.iendo.bean;

import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe  http格式下的 视频数据bean
 */
public class HttpVideoBean {


    private List<DsDTO> ds;

    public List<DsDTO> getDs() {
        return ds;
    }

    public void setDs(List<DsDTO> ds) {
        this.ds = ds;
    }

    public static class DsDTO {
        /**
         * ID : 21
         * RecordID : 881
         * FtpPath : 1
         * Title :
         * Description :
         * FilePath : 120200609155631560.mp4
         * ThumbPath :
         * RecordedAt :
         * CreatedAt : 2020/6/9 15:56:31
         * ViewTimes : 0
         */

        private String ID;
        private String RecordID;
        private String FtpPath;
        private String Title;
        private String Description;
        private String FilePath;
        private String ThumbPath;
        private String RecordedAt;
        private String CreatedAt;
        private String ViewTimes;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getRecordID() {
            return RecordID;
        }

        public void setRecordID(String RecordID) {
            this.RecordID = RecordID;
        }

        public String getFtpPath() {
            return FtpPath;
        }

        public void setFtpPath(String FtpPath) {
            this.FtpPath = FtpPath;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public String getThumbPath() {
            return ThumbPath;
        }

        public void setThumbPath(String ThumbPath) {
            this.ThumbPath = ThumbPath;
        }

        public String getRecordedAt() {
            return RecordedAt;
        }

        public void setRecordedAt(String RecordedAt) {
            this.RecordedAt = RecordedAt;
        }

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String CreatedAt) {
            this.CreatedAt = CreatedAt;
        }

        public String getViewTimes() {
            return ViewTimes;
        }

        public void setViewTimes(String ViewTimes) {
            this.ViewTimes = ViewTimes;
        }
    }
}
