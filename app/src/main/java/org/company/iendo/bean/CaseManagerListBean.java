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

//        private String ID;
//        private String Name;
//        private String Pathology;
//        private String RecordDate;
//        private String EndoType;
        private String ID;

        private String tag;          //这个是itemID  当前用户的id，通过这个查询
        private String RecordType;
        private String PatientID;
        private String Married;
        private String Name;
        private String Sex;
        private String Tel;
        private String Address;
        private String PatientNo;
        private String CardID;
        private String MedHistory;
        private String FamilyHistory;
        private String Race;
        private String Occupatior;
        private String InsuranceID;
        private String NativePlace;
        private String IsInHospital;
        private String LastCheckUserID;
        private String DOB;
        private String PatientAge;
        private String AgeUnit;
        private String CaseNo;
        private String ReturnVisit;
        private String BedID;
        private String WardID;
        private String CaseID;
        private String SubmitDoctor;
        private String Department;
        private String Device;
        private String Fee;
        private String FeeType;
        private String ChiefComplaint;
        private String Test;
        private String Advice;
        private String InpatientID;
        private String OutpatientID;
        private String Others;
        private String Await1;
        private String Await2;
        private String Await3;
        private String Await4;
        private String Await5;
        private String Biopsy;
        private String Ctology;
        private String Pathology;
        private String CheckDate;
        private String RecordDate;
        private String Printed;
        private String Upload;
        private String Bespeak;
        private String Images;
        private String ReportStyle;
        private String UserName;
        private String EndoType;
        private String StudyInstanceUID;
        private String SeriesInstanceUID;
        private String ExaminingPhysician;
        private String ClinicalDiagnosis;
        private String CheckContent;
        private String CheckDiagnosis;

        @Override
        public String toString() {
            return "DsDTO{" +
                    "ID='" + ID + '\'' +
                    ", tag='" + tag + '\'' +
                    ", RecordType='" + RecordType + '\'' +
                    ", PatientID='" + PatientID + '\'' +
                    ", Married='" + Married + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Sex='" + Sex + '\'' +
                    ", Tel='" + Tel + '\'' +
                    ", Address='" + Address + '\'' +
                    ", PatientNo='" + PatientNo + '\'' +
                    ", CardID='" + CardID + '\'' +
                    ", MedHistory='" + MedHistory + '\'' +
                    ", FamilyHistory='" + FamilyHistory + '\'' +
                    ", Race='" + Race + '\'' +
                    ", Occupatior='" + Occupatior + '\'' +
                    ", InsuranceID='" + InsuranceID + '\'' +
                    ", NativePlace='" + NativePlace + '\'' +
                    ", IsInHospital='" + IsInHospital + '\'' +
                    ", LastCheckUserID='" + LastCheckUserID + '\'' +
                    ", DOB='" + DOB + '\'' +
                    ", PatientAge='" + PatientAge + '\'' +
                    ", AgeUnit='" + AgeUnit + '\'' +
                    ", CaseNo='" + CaseNo + '\'' +
                    ", ReturnVisit='" + ReturnVisit + '\'' +
                    ", BedID='" + BedID + '\'' +
                    ", WardID='" + WardID + '\'' +
                    ", CaseID='" + CaseID + '\'' +
                    ", SubmitDoctor='" + SubmitDoctor + '\'' +
                    ", Department='" + Department + '\'' +
                    ", Device='" + Device + '\'' +
                    ", Fee='" + Fee + '\'' +
                    ", FeeType='" + FeeType + '\'' +
                    ", ChiefComplaint='" + ChiefComplaint + '\'' +
                    ", Test='" + Test + '\'' +
                    ", Advice='" + Advice + '\'' +
                    ", InpatientID='" + InpatientID + '\'' +
                    ", OutpatientID='" + OutpatientID + '\'' +
                    ", Others='" + Others + '\'' +
                    ", Await1='" + Await1 + '\'' +
                    ", Await2='" + Await2 + '\'' +
                    ", Await3='" + Await3 + '\'' +
                    ", Await4='" + Await4 + '\'' +
                    ", Await5='" + Await5 + '\'' +
                    ", Biopsy='" + Biopsy + '\'' +
                    ", Ctology='" + Ctology + '\'' +
                    ", Pathology='" + Pathology + '\'' +
                    ", CheckDate='" + CheckDate + '\'' +
                    ", RecordDate='" + RecordDate + '\'' +
                    ", Printed='" + Printed + '\'' +
                    ", Upload='" + Upload + '\'' +
                    ", Bespeak='" + Bespeak + '\'' +
                    ", Images='" + Images + '\'' +
                    ", ReportStyle='" + ReportStyle + '\'' +
                    ", UserName='" + UserName + '\'' +
                    ", EndoType='" + EndoType + '\'' +
                    ", StudyInstanceUID='" + StudyInstanceUID + '\'' +
                    ", SeriesInstanceUID='" + SeriesInstanceUID + '\'' +
                    ", ExaminingPhysician='" + ExaminingPhysician + '\'' +
                    ", ClinicalDiagnosis='" + ClinicalDiagnosis + '\'' +
                    ", CheckContent='" + CheckContent + '\'' +
                    ", CheckDiagnosis='" + CheckDiagnosis + '\'' +
                    '}';
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getRecordType() {
            return RecordType;
        }

        public void setRecordType(String recordType) {
            RecordType = recordType;
        }

        public String getPatientID() {
            return PatientID;
        }

        public void setPatientID(String patientID) {
            PatientID = patientID;
        }

        public String getMarried() {
            return Married;
        }

        public void setMarried(String married) {
            Married = married;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getPatientNo() {
            return PatientNo;
        }

        public void setPatientNo(String patientNo) {
            PatientNo = patientNo;
        }

        public String getCardID() {
            return CardID;
        }

        public void setCardID(String cardID) {
            CardID = cardID;
        }

        public String getMedHistory() {
            return MedHistory;
        }

        public void setMedHistory(String medHistory) {
            MedHistory = medHistory;
        }

        public String getFamilyHistory() {
            return FamilyHistory;
        }

        public void setFamilyHistory(String familyHistory) {
            FamilyHistory = familyHistory;
        }

        public String getRace() {
            return Race;
        }

        public void setRace(String race) {
            Race = race;
        }

        public String getOccupatior() {
            return Occupatior;
        }

        public void setOccupatior(String occupatior) {
            Occupatior = occupatior;
        }

        public String getInsuranceID() {
            return InsuranceID;
        }

        public void setInsuranceID(String insuranceID) {
            InsuranceID = insuranceID;
        }

        public String getNativePlace() {
            return NativePlace;
        }

        public void setNativePlace(String nativePlace) {
            NativePlace = nativePlace;
        }

        public String getIsInHospital() {
            return IsInHospital;
        }

        public void setIsInHospital(String isInHospital) {
            IsInHospital = isInHospital;
        }

        public String getLastCheckUserID() {
            return LastCheckUserID;
        }

        public void setLastCheckUserID(String lastCheckUserID) {
            LastCheckUserID = lastCheckUserID;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getPatientAge() {
            return PatientAge;
        }

        public void setPatientAge(String patientAge) {
            PatientAge = patientAge;
        }

        public String getAgeUnit() {
            return AgeUnit;
        }

        public void setAgeUnit(String ageUnit) {
            AgeUnit = ageUnit;
        }

        public String getCaseNo() {
            return CaseNo;
        }

        public void setCaseNo(String caseNo) {
            CaseNo = caseNo;
        }

        public String getReturnVisit() {
            return ReturnVisit;
        }

        public void setReturnVisit(String returnVisit) {
            ReturnVisit = returnVisit;
        }

        public String getBedID() {
            return BedID;
        }

        public void setBedID(String bedID) {
            BedID = bedID;
        }

        public String getWardID() {
            return WardID;
        }

        public void setWardID(String wardID) {
            WardID = wardID;
        }

        public String getCaseID() {
            return CaseID;
        }

        public void setCaseID(String caseID) {
            CaseID = caseID;
        }

        public String getSubmitDoctor() {
            return SubmitDoctor;
        }

        public void setSubmitDoctor(String submitDoctor) {
            SubmitDoctor = submitDoctor;
        }

        public String getDepartment() {
            return Department;
        }

        public void setDepartment(String department) {
            Department = department;
        }

        public String getDevice() {
            return Device;
        }

        public void setDevice(String device) {
            Device = device;
        }

        public String getFee() {
            return Fee;
        }

        public void setFee(String fee) {
            Fee = fee;
        }

        public String getFeeType() {
            return FeeType;
        }

        public void setFeeType(String feeType) {
            FeeType = feeType;
        }

        public String getChiefComplaint() {
            return ChiefComplaint;
        }

        public void setChiefComplaint(String chiefComplaint) {
            ChiefComplaint = chiefComplaint;
        }

        public String getTest() {
            return Test;
        }

        public void setTest(String test) {
            Test = test;
        }

        public String getAdvice() {
            return Advice;
        }

        public void setAdvice(String advice) {
            Advice = advice;
        }

        public String getInpatientID() {
            return InpatientID;
        }

        public void setInpatientID(String inpatientID) {
            InpatientID = inpatientID;
        }

        public String getOutpatientID() {
            return OutpatientID;
        }

        public void setOutpatientID(String outpatientID) {
            OutpatientID = outpatientID;
        }

        public String getOthers() {
            return Others;
        }

        public void setOthers(String others) {
            Others = others;
        }

        public String getAwait1() {
            return Await1;
        }

        public void setAwait1(String await1) {
            Await1 = await1;
        }

        public String getAwait2() {
            return Await2;
        }

        public void setAwait2(String await2) {
            Await2 = await2;
        }

        public String getAwait3() {
            return Await3;
        }

        public void setAwait3(String await3) {
            Await3 = await3;
        }

        public String getAwait4() {
            return Await4;
        }

        public void setAwait4(String await4) {
            Await4 = await4;
        }

        public String getAwait5() {
            return Await5;
        }

        public void setAwait5(String await5) {
            Await5 = await5;
        }

        public String getBiopsy() {
            return Biopsy;
        }

        public void setBiopsy(String biopsy) {
            Biopsy = biopsy;
        }

        public String getCtology() {
            return Ctology;
        }

        public void setCtology(String ctology) {
            Ctology = ctology;
        }

        public String getCheckDate() {
            return CheckDate;
        }

        public void setCheckDate(String checkDate) {
            CheckDate = checkDate;
        }

        public String getPrinted() {
            return Printed;
        }

        public void setPrinted(String printed) {
            Printed = printed;
        }

        public String getUpload() {
            return Upload;
        }

        public void setUpload(String upload) {
            Upload = upload;
        }

        public String getBespeak() {
            return Bespeak;
        }

        public void setBespeak(String bespeak) {
            Bespeak = bespeak;
        }

        public String getImages() {
            return Images;
        }

        public void setImages(String images) {
            Images = images;
        }

        public String getReportStyle() {
            return ReportStyle;
        }

        public void setReportStyle(String reportStyle) {
            ReportStyle = reportStyle;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getStudyInstanceUID() {
            return StudyInstanceUID;
        }

        public void setStudyInstanceUID(String studyInstanceUID) {
            StudyInstanceUID = studyInstanceUID;
        }

        public String getSeriesInstanceUID() {
            return SeriesInstanceUID;
        }

        public void setSeriesInstanceUID(String seriesInstanceUID) {
            SeriesInstanceUID = seriesInstanceUID;
        }

        public String getExaminingPhysician() {
            return ExaminingPhysician;
        }

        public void setExaminingPhysician(String examiningPhysician) {
            ExaminingPhysician = examiningPhysician;
        }

        public String getClinicalDiagnosis() {
            return ClinicalDiagnosis;
        }

        public void setClinicalDiagnosis(String clinicalDiagnosis) {
            ClinicalDiagnosis = clinicalDiagnosis;
        }

        public String getCheckContent() {
            return CheckContent;
        }

        public void setCheckContent(String checkContent) {
            CheckContent = checkContent;
        }

        public String getCheckDiagnosis() {
            return CheckDiagnosis;
        }

        public void setCheckDiagnosis(String checkDiagnosis) {
            CheckDiagnosis = checkDiagnosis;
        }

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

    }
}
