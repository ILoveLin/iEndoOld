package org.company.iendo.bean.beandb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * LoveLin
 * <p>
 * Describe  离线用户表的Bean
 */
@Entity
public class UserDetailMSGDBBean {
    @Id(autoincrement = true)
    private Long id;

    @Unique
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
    @Generated(hash = 746075152)
    public UserDetailMSGDBBean(Long id, String ID, String tag, String RecordType,
            String PatientID, String Married, String Name, String Sex, String Tel,
            String Address, String PatientNo, String CardID, String MedHistory,
            String FamilyHistory, String Race, String Occupatior,
            String InsuranceID, String NativePlace, String IsInHospital,
            String LastCheckUserID, String DOB, String PatientAge, String AgeUnit,
            String CaseNo, String ReturnVisit, String BedID, String WardID,
            String CaseID, String SubmitDoctor, String Department, String Device,
            String Fee, String FeeType, String ChiefComplaint, String Test,
            String Advice, String InpatientID, String OutpatientID, String Others,
            String Await1, String Await2, String Await3, String Await4,
            String Await5, String Biopsy, String Ctology, String Pathology,
            String CheckDate, String RecordDate, String Printed, String Upload,
            String Bespeak, String Images, String ReportStyle, String UserName,
            String EndoType, String StudyInstanceUID, String SeriesInstanceUID,
            String ExaminingPhysician, String ClinicalDiagnosis,
            String CheckContent, String CheckDiagnosis) {
        this.id = id;
        this.ID = ID;
        this.tag = tag;
        this.RecordType = RecordType;
        this.PatientID = PatientID;
        this.Married = Married;
        this.Name = Name;
        this.Sex = Sex;
        this.Tel = Tel;
        this.Address = Address;
        this.PatientNo = PatientNo;
        this.CardID = CardID;
        this.MedHistory = MedHistory;
        this.FamilyHistory = FamilyHistory;
        this.Race = Race;
        this.Occupatior = Occupatior;
        this.InsuranceID = InsuranceID;
        this.NativePlace = NativePlace;
        this.IsInHospital = IsInHospital;
        this.LastCheckUserID = LastCheckUserID;
        this.DOB = DOB;
        this.PatientAge = PatientAge;
        this.AgeUnit = AgeUnit;
        this.CaseNo = CaseNo;
        this.ReturnVisit = ReturnVisit;
        this.BedID = BedID;
        this.WardID = WardID;
        this.CaseID = CaseID;
        this.SubmitDoctor = SubmitDoctor;
        this.Department = Department;
        this.Device = Device;
        this.Fee = Fee;
        this.FeeType = FeeType;
        this.ChiefComplaint = ChiefComplaint;
        this.Test = Test;
        this.Advice = Advice;
        this.InpatientID = InpatientID;
        this.OutpatientID = OutpatientID;
        this.Others = Others;
        this.Await1 = Await1;
        this.Await2 = Await2;
        this.Await3 = Await3;
        this.Await4 = Await4;
        this.Await5 = Await5;
        this.Biopsy = Biopsy;
        this.Ctology = Ctology;
        this.Pathology = Pathology;
        this.CheckDate = CheckDate;
        this.RecordDate = RecordDate;
        this.Printed = Printed;
        this.Upload = Upload;
        this.Bespeak = Bespeak;
        this.Images = Images;
        this.ReportStyle = ReportStyle;
        this.UserName = UserName;
        this.EndoType = EndoType;
        this.StudyInstanceUID = StudyInstanceUID;
        this.SeriesInstanceUID = SeriesInstanceUID;
        this.ExaminingPhysician = ExaminingPhysician;
        this.ClinicalDiagnosis = ClinicalDiagnosis;
        this.CheckContent = CheckContent;
        this.CheckDiagnosis = CheckDiagnosis;
    }
    @Generated(hash = 407973039)
    public UserDetailMSGDBBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getID() {
        return this.ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getRecordType() {
        return this.RecordType;
    }
    public void setRecordType(String RecordType) {
        this.RecordType = RecordType;
    }
    public String getPatientID() {
        return this.PatientID;
    }
    public void setPatientID(String PatientID) {
        this.PatientID = PatientID;
    }
    public String getMarried() {
        return this.Married;
    }
    public void setMarried(String Married) {
        this.Married = Married;
    }
    public String getName() {
        return this.Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getSex() {
        return this.Sex;
    }
    public void setSex(String Sex) {
        this.Sex = Sex;
    }
    public String getTel() {
        return this.Tel;
    }
    public void setTel(String Tel) {
        this.Tel = Tel;
    }
    public String getAddress() {
        return this.Address;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }
    public String getPatientNo() {
        return this.PatientNo;
    }
    public void setPatientNo(String PatientNo) {
        this.PatientNo = PatientNo;
    }
    public String getCardID() {
        return this.CardID;
    }
    public void setCardID(String CardID) {
        this.CardID = CardID;
    }
    public String getMedHistory() {
        return this.MedHistory;
    }
    public void setMedHistory(String MedHistory) {
        this.MedHistory = MedHistory;
    }
    public String getFamilyHistory() {
        return this.FamilyHistory;
    }
    public void setFamilyHistory(String FamilyHistory) {
        this.FamilyHistory = FamilyHistory;
    }
    public String getRace() {
        return this.Race;
    }
    public void setRace(String Race) {
        this.Race = Race;
    }
    public String getOccupatior() {
        return this.Occupatior;
    }
    public void setOccupatior(String Occupatior) {
        this.Occupatior = Occupatior;
    }
    public String getInsuranceID() {
        return this.InsuranceID;
    }
    public void setInsuranceID(String InsuranceID) {
        this.InsuranceID = InsuranceID;
    }
    public String getNativePlace() {
        return this.NativePlace;
    }
    public void setNativePlace(String NativePlace) {
        this.NativePlace = NativePlace;
    }
    public String getIsInHospital() {
        return this.IsInHospital;
    }
    public void setIsInHospital(String IsInHospital) {
        this.IsInHospital = IsInHospital;
    }
    public String getLastCheckUserID() {
        return this.LastCheckUserID;
    }
    public void setLastCheckUserID(String LastCheckUserID) {
        this.LastCheckUserID = LastCheckUserID;
    }
    public String getDOB() {
        return this.DOB;
    }
    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
    public String getPatientAge() {
        return this.PatientAge;
    }
    public void setPatientAge(String PatientAge) {
        this.PatientAge = PatientAge;
    }
    public String getAgeUnit() {
        return this.AgeUnit;
    }
    public void setAgeUnit(String AgeUnit) {
        this.AgeUnit = AgeUnit;
    }
    public String getCaseNo() {
        return this.CaseNo;
    }
    public void setCaseNo(String CaseNo) {
        this.CaseNo = CaseNo;
    }
    public String getReturnVisit() {
        return this.ReturnVisit;
    }
    public void setReturnVisit(String ReturnVisit) {
        this.ReturnVisit = ReturnVisit;
    }
    public String getBedID() {
        return this.BedID;
    }
    public void setBedID(String BedID) {
        this.BedID = BedID;
    }
    public String getWardID() {
        return this.WardID;
    }
    public void setWardID(String WardID) {
        this.WardID = WardID;
    }
    public String getCaseID() {
        return this.CaseID;
    }
    public void setCaseID(String CaseID) {
        this.CaseID = CaseID;
    }
    public String getSubmitDoctor() {
        return this.SubmitDoctor;
    }
    public void setSubmitDoctor(String SubmitDoctor) {
        this.SubmitDoctor = SubmitDoctor;
    }
    public String getDepartment() {
        return this.Department;
    }
    public void setDepartment(String Department) {
        this.Department = Department;
    }
    public String getDevice() {
        return this.Device;
    }
    public void setDevice(String Device) {
        this.Device = Device;
    }
    public String getFee() {
        return this.Fee;
    }
    public void setFee(String Fee) {
        this.Fee = Fee;
    }
    public String getFeeType() {
        return this.FeeType;
    }
    public void setFeeType(String FeeType) {
        this.FeeType = FeeType;
    }
    public String getChiefComplaint() {
        return this.ChiefComplaint;
    }
    public void setChiefComplaint(String ChiefComplaint) {
        this.ChiefComplaint = ChiefComplaint;
    }
    public String getTest() {
        return this.Test;
    }
    public void setTest(String Test) {
        this.Test = Test;
    }
    public String getAdvice() {
        return this.Advice;
    }
    public void setAdvice(String Advice) {
        this.Advice = Advice;
    }
    public String getInpatientID() {
        return this.InpatientID;
    }
    public void setInpatientID(String InpatientID) {
        this.InpatientID = InpatientID;
    }
    public String getOutpatientID() {
        return this.OutpatientID;
    }
    public void setOutpatientID(String OutpatientID) {
        this.OutpatientID = OutpatientID;
    }
    public String getOthers() {
        return this.Others;
    }
    public void setOthers(String Others) {
        this.Others = Others;
    }
    public String getAwait1() {
        return this.Await1;
    }
    public void setAwait1(String Await1) {
        this.Await1 = Await1;
    }
    public String getAwait2() {
        return this.Await2;
    }
    public void setAwait2(String Await2) {
        this.Await2 = Await2;
    }
    public String getAwait3() {
        return this.Await3;
    }
    public void setAwait3(String Await3) {
        this.Await3 = Await3;
    }
    public String getAwait4() {
        return this.Await4;
    }
    public void setAwait4(String Await4) {
        this.Await4 = Await4;
    }
    public String getAwait5() {
        return this.Await5;
    }
    public void setAwait5(String Await5) {
        this.Await5 = Await5;
    }
    public String getBiopsy() {
        return this.Biopsy;
    }
    public void setBiopsy(String Biopsy) {
        this.Biopsy = Biopsy;
    }
    public String getCtology() {
        return this.Ctology;
    }
    public void setCtology(String Ctology) {
        this.Ctology = Ctology;
    }
    public String getPathology() {
        return this.Pathology;
    }
    public void setPathology(String Pathology) {
        this.Pathology = Pathology;
    }
    public String getCheckDate() {
        return this.CheckDate;
    }
    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
    }
    public String getRecordDate() {
        return this.RecordDate;
    }
    public void setRecordDate(String RecordDate) {
        this.RecordDate = RecordDate;
    }
    public String getPrinted() {
        return this.Printed;
    }
    public void setPrinted(String Printed) {
        this.Printed = Printed;
    }
    public String getUpload() {
        return this.Upload;
    }
    public void setUpload(String Upload) {
        this.Upload = Upload;
    }
    public String getBespeak() {
        return this.Bespeak;
    }
    public void setBespeak(String Bespeak) {
        this.Bespeak = Bespeak;
    }
    public String getImages() {
        return this.Images;
    }
    public void setImages(String Images) {
        this.Images = Images;
    }
    public String getReportStyle() {
        return this.ReportStyle;
    }
    public void setReportStyle(String ReportStyle) {
        this.ReportStyle = ReportStyle;
    }
    public String getUserName() {
        return this.UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    public String getEndoType() {
        return this.EndoType;
    }
    public void setEndoType(String EndoType) {
        this.EndoType = EndoType;
    }
    public String getStudyInstanceUID() {
        return this.StudyInstanceUID;
    }
    public void setStudyInstanceUID(String StudyInstanceUID) {
        this.StudyInstanceUID = StudyInstanceUID;
    }
    public String getSeriesInstanceUID() {
        return this.SeriesInstanceUID;
    }
    public void setSeriesInstanceUID(String SeriesInstanceUID) {
        this.SeriesInstanceUID = SeriesInstanceUID;
    }
    public String getExaminingPhysician() {
        return this.ExaminingPhysician;
    }
    public void setExaminingPhysician(String ExaminingPhysician) {
        this.ExaminingPhysician = ExaminingPhysician;
    }
    public String getClinicalDiagnosis() {
        return this.ClinicalDiagnosis;
    }
    public void setClinicalDiagnosis(String ClinicalDiagnosis) {
        this.ClinicalDiagnosis = ClinicalDiagnosis;
    }
    public String getCheckContent() {
        return this.CheckContent;
    }
    public void setCheckContent(String CheckContent) {
        this.CheckContent = CheckContent;
    }
    public String getCheckDiagnosis() {
        return this.CheckDiagnosis;
    }
    public void setCheckDiagnosis(String CheckDiagnosis) {
        this.CheckDiagnosis = CheckDiagnosis;
    }
}
