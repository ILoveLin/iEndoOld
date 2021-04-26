package org.company.iendo.bean;

import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe  http格式下的   图片数据bean
 */
public class HttpImageBean {


    private List<DsDTO> ds;

    public List<DsDTO> getDs() {
        return ds;
    }

    public void setDs(List<DsDTO> ds) {
        this.ds = ds;
    }

    public static class DsDTO {
        /**
         * ID : 40
         * RecordID : 10
         * FtpPath : 1
         * ImagePath : 20200415180941303.bmp    //原图
         * ThumbPath : 20200415180941303.jpg    //模糊图
         * ImageDescription :
         * ImageTitle :
         * CreatedAt : 2020/4/15 18:09:41
         * ImageEdit :
         * SketchMap :
         * ImageMarkX : -1
         * ImageMarkY : -1
         * Lesions :
         * Position :
         * SOPInstanceUID :
         */

        private String ID;
        private String RecordID;
        private String FtpPath;
        private String ImagePath;
        private String ThumbPath;
        private String ImageDescription;
        private String ImageTitle;
        private String CreatedAt;
        private String ImageEdit;
        private String SketchMap;
        private String ImageMarkX;
        private String ImageMarkY;
        private String Lesions;
        private String Position;
        private String SOPInstanceUID;

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

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String ImagePath) {
            this.ImagePath = ImagePath;
        }

        public String getThumbPath() {
            return ThumbPath;
        }

        public void setThumbPath(String ThumbPath) {
            this.ThumbPath = ThumbPath;
        }

        public String getImageDescription() {
            return ImageDescription;
        }

        public void setImageDescription(String ImageDescription) {
            this.ImageDescription = ImageDescription;
        }

        public String getImageTitle() {
            return ImageTitle;
        }

        public void setImageTitle(String ImageTitle) {
            this.ImageTitle = ImageTitle;
        }

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String CreatedAt) {
            this.CreatedAt = CreatedAt;
        }

        public String getImageEdit() {
            return ImageEdit;
        }

        public void setImageEdit(String ImageEdit) {
            this.ImageEdit = ImageEdit;
        }

        public String getSketchMap() {
            return SketchMap;
        }

        public void setSketchMap(String SketchMap) {
            this.SketchMap = SketchMap;
        }

        public String getImageMarkX() {
            return ImageMarkX;
        }

        public void setImageMarkX(String ImageMarkX) {
            this.ImageMarkX = ImageMarkX;
        }

        public String getImageMarkY() {
            return ImageMarkY;
        }

        public void setImageMarkY(String ImageMarkY) {
            this.ImageMarkY = ImageMarkY;
        }

        public String getLesions() {
            return Lesions;
        }

        public void setLesions(String Lesions) {
            this.Lesions = Lesions;
        }

        public String getPosition() {
            return Position;
        }

        public void setPosition(String Position) {
            this.Position = Position;
        }

        public String getSOPInstanceUID() {
            return SOPInstanceUID;
        }

        public void setSOPInstanceUID(String SOPInstanceUID) {
            this.SOPInstanceUID = SOPInstanceUID;
        }
    }
}
