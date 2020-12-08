package org.company.iendo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 病例管理
 */
public class CaseManagerListBean implements Serializable {


    private List<DsDTO> ds;

    public List<DsDTO> getDs() {
        return ds;
    }

    public void setDs(List<DsDTO> ds) {
        this.ds = ds;
    }

    public static class DsDTO implements Serializable {
        /**
         * ID : 4037
         * Name : qq
         * Pathology :
         * RecordDate : 2020/11/27 16:06:08
         * EndoType : 3
         */

        private String ID;
        private String Name;
        private String Pathology;
        private String RecordDate;
        private String EndoType;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPathology() {
            return Pathology;
        }

        public void setPathology(String Pathology) {
            this.Pathology = Pathology;
        }

        public String getRecordDate() {
            return RecordDate;
        }

        public void setRecordDate(String RecordDate) {
            this.RecordDate = RecordDate;
        }

        public String getEndoType() {
            return EndoType;
        }

        public void setEndoType(String EndoType) {
            this.EndoType = EndoType;
        }

        @Override
        public String toString() {
            return "DsDTO{" +
                    "ID='" + ID + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Pathology='" + Pathology + '\'' +
                    ", RecordDate='" + RecordDate + '\'' +
                    ", EndoType='" + EndoType + '\'' +
                    '}';
        }
    }
}
