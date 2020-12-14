package org.company.iendo.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.company.iendo.bean.CaseDetailMsgBean;
import org.company.iendo.bean.CaseManagerListBean;
import org.company.iendo.bean.beandb.UserDetailMSGDBBean;
import org.company.iendo.bean.beandb.image.DimImageBean;
import org.company.iendo.bean.beandb.image.ImageListDownDBBean;
import org.company.iendo.bean.beandb.image.ReallyImageBean;
import org.company.iendo.util.db.ImageDBUtils;
import org.company.iendo.util.db.UserDetailMSGDBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe
 */
public class BeanToUtils {
    /**
     * 图片url数据转换
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String> getDimImagePathList(String id) {
        ArrayList<String> list = new ArrayList<>();
        if (ImageDBUtils.queryListIsExist(id)) {
            List<ImageListDownDBBean> ImageListDownDBBeans = ImageDBUtils.queryListByTag(id);
            ImageListDownDBBean imageListDownDBBean = ImageListDownDBBeans.get(0);
            List<DimImageBean> mDimImageList = imageListDownDBBean.getMDimImageList();
            List<ReallyImageBean> mReallyImageList = imageListDownDBBean.getMReallyImageList();
            mDimImageList.stream().forEach(bean -> list.add(bean.getDimFilePath()));
            return list;
        }
        return list;
    }

    /**
     * dbbean中原图图片url数据转换为集合数据给list用
     */


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String> getReallyImagePathList(String id) {
        ArrayList<String> list = new ArrayList<>();
        if (ImageDBUtils.queryListIsExist(id)) {
            List<ImageListDownDBBean> ImageListDownDBBeans = ImageDBUtils.queryListByTag(id);
            ImageListDownDBBean imageListDownDBBean = ImageListDownDBBeans.get(0);
            List<ReallyImageBean> mReallyImageList = imageListDownDBBean.getMReallyImageList();
            mReallyImageList.stream().forEach(bean -> list.add(bean.getReallyFilePath()));
            return list;
        }
        return list;
    }

    /**
     * DBBean转换为jsonBean
     *
     * @param bean
     * @return
     */
    public static CaseManagerListBean.DsDTO getDBBeanToJsonBean(CaseManagerListBean.DsDTO bean) {
        UserDetailMSGDBBean mDBBean = UserDetailMSGDBUtils.queryListByTag(bean.getID()).get(0);
        CaseManagerListBean.DsDTO jsonBean = new CaseManagerListBean.DsDTO();
        //DBbean转bean
        jsonBean.setTag(mDBBean.getID());  //设置查询条件的TAG
        jsonBean.setID(mDBBean.getID());
        jsonBean.setRecordDate(mDBBean.getRecordDate());
        jsonBean.setEndoType(mDBBean.getEndoType());
        jsonBean.setPathology(mDBBean.getPathology());
        jsonBean.setName("" + mDBBean.getName());
        jsonBean.setCaseID("" + mDBBean.getCaseID());
        jsonBean.setPatientAge("" + mDBBean.getPatientAge());
        jsonBean.setSex("" + mDBBean.getSex());
        jsonBean.setAgeUnit("" + mDBBean.getAgeUnit());
        jsonBean.setOccupatior("" + mDBBean.getOccupatior());
        jsonBean.setTel("" + mDBBean.getTel());
        jsonBean.setMedHistory("" + mDBBean.getMedHistory());
        jsonBean.setReturnVisit("" + mDBBean.getReturnVisit());
        jsonBean.setCaseNo("" + mDBBean.getCaseNo());
        jsonBean.setFee("" + mDBBean.getFee());
        jsonBean.setSubmitDoctor("" + mDBBean.getSubmitDoctor());
        jsonBean.setDepartment("" + mDBBean.getDepartment());
        jsonBean.setChiefComplaint("" + mDBBean.getChiefComplaint());
        jsonBean.setClinicalDiagnosis("" + mDBBean.getClinicalDiagnosis());
        jsonBean.setCheckContent("" + mDBBean.getCheckContent());
        jsonBean.setCheckDiagnosis("" + mDBBean.getCheckDiagnosis());
        jsonBean.setBiopsy("" + mDBBean.getBiopsy());
        jsonBean.setTest("" + mDBBean.getTest());
        jsonBean.setCtology("" + mDBBean.getCtology());
        jsonBean.setPathology("" + mDBBean.getPathology());
        jsonBean.setAdvice("" + mDBBean.getAdvice());
        jsonBean.setExaminingPhysician("" + mDBBean.getExaminingPhysician());
        return jsonBean;
    }


    /**
     * jsonBean转换为DBBean
     *
     * @param
     * @return
     */
    public static UserDetailMSGDBBean getJsonBeanToDBBean(CaseDetailMsgBean.DsDTO mBean) {
        UserDetailMSGDBBean mDBDetailBean = new UserDetailMSGDBBean();
        LogUtils.e("选择的数据==db=用户表存=======mBean==" + mBean.toString());
        mDBDetailBean.setTag(mBean.getID());  //设置查询条件的TAG
        mDBDetailBean.setID(mBean.getID());
        mDBDetailBean.setRecordDate(mBean.getRecordDate());
        mDBDetailBean.setEndoType(mBean.getEndoType());
        mDBDetailBean.setPathology(mBean.getPathology());
        mDBDetailBean.setName("" + mBean.getName());
        mDBDetailBean.setCaseID("" + mBean.getCaseID());
        mDBDetailBean.setPatientAge("" + mBean.getPatientAge());
        mDBDetailBean.setSex("" + mBean.getSex());
        mDBDetailBean.setAgeUnit("" + mBean.getAgeUnit());
        mDBDetailBean.setOccupatior("" + mBean.getOccupatior());
        mDBDetailBean.setTel("" + mBean.getTel());
        mDBDetailBean.setMedHistory("" + mBean.getMedHistory());
        if (mBean.getReturnVisit().equals("False")) {
            mDBDetailBean.setReturnVisit("否");
        } else {
            mDBDetailBean.setReturnVisit("是");
        }
        mDBDetailBean.setCaseNo("" + mBean.getCaseNo());
        mDBDetailBean.setFee("" + mBean.getFee());
        mDBDetailBean.setSubmitDoctor("" + mBean.getSubmitDoctor());
        mDBDetailBean.setDepartment("" + mBean.getDepartment());
        mDBDetailBean.setChiefComplaint("" + mBean.getChiefComplaint());
        mDBDetailBean.setClinicalDiagnosis("" + mBean.getClinicalDiagnosis());
        mDBDetailBean.setCheckContent("" + mBean.getCheckContent());
        mDBDetailBean.setCheckDiagnosis("" + mBean.getCheckDiagnosis());
        mDBDetailBean.setBiopsy("" + mBean.getBiopsy());
        mDBDetailBean.setTest("" + mBean.getTest());
        mDBDetailBean.setCtology("" + mBean.getCtology());
        mDBDetailBean.setPathology("" + mBean.getPathology());
        mDBDetailBean.setAdvice("" + mBean.getAdvice());
        mDBDetailBean.setExaminingPhysician("" + mBean.getExaminingPhysician());
        return mDBDetailBean;

    }

}
