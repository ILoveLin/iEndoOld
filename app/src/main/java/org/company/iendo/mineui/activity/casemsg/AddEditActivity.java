package org.company.iendo.mineui.activity.casemsg;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.google.gson.reflect.TypeToken;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.hjq.widget.view.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.bean.CaseDetailMsgBean;
import org.company.iendo.bean.CaseManagerListBean;
import org.company.iendo.bean.EditDataBean;
import org.company.iendo.bean.EditItemBean;
import org.company.iendo.bean.event.AddDeleteEvent;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.ui.dialog.MenuDialog;
import org.company.iendo.ui.dialog.SelectDialog;
import org.company.iendo.util.LogUtils;
import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe  添加界面
 */
public class AddEditActivity extends MyActivity {
    private ClearEditText mCaseNumber, mCaseName, mCaseAge, mCaseProfession, mCasePhone, mCaseAddress, mCaseHistory,
            mCaseSeeCharge, mCaseDoctor, m01CaseDevice, m02CaseOffice, m03CaseSay, m04CaseBedSea, m05CaseMirrorSee,
            m06CaseMirrorSeeResult, m07CaseLiveSee, m08CaseTest, m09CaseCytology, m10CasePathology, m11CaseAdvise;
    private TextView mAgeType, mCaseSex, mCaseCheckNum, mCaseSeeAgain;
    private List<List<EditItemBean>> mDialogList;
    private NestedScrollView mScrollView;
    private TitleBar mTitleBar;
    private String currentNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_edit;
    }

    @Override
    protected void initView() {
        mCaseNumber = findViewById(R.id.case03_case_number);
        mScrollView = findViewById(R.id.nestedScrollView);
        mTitleBar = findViewById(R.id.titlebar);
        mCaseName = findViewById(R.id.case03_name);
        //弹出单选框
        mCaseSex = findViewById(R.id.case03_sex);
        mCaseAge = findViewById(R.id.case03_age);
        mAgeType = findViewById(R.id.tv_age_type);
        mCaseProfession = findViewById(R.id.case03_profession);
        mCasePhone = findViewById(R.id.case03_phone);
        mCaseAddress = findViewById(R.id.case03_address);
        mCaseHistory = findViewById(R.id.case03_history);
        mCaseCheckNum = findViewById(R.id.case03_check_num);
        //弹出单选框
        mCaseSeeAgain = findViewById(R.id.case03_see_again);
        mCaseSeeCharge = findViewById(R.id.case03_sea_charge);
        mCaseDoctor = findViewById(R.id.case03_doctor);
        //从这里开始 下拉选择框和edit一起使用
        m01CaseDevice = findViewById(R.id.case03_device);
        m02CaseOffice = findViewById(R.id.case03_office);
        m03CaseSay = findViewById(R.id.case03_say);
        m04CaseBedSea = findViewById(R.id.case03_bed_sea);
        m05CaseMirrorSee = findViewById(R.id.case03_mirror_see);
        m06CaseMirrorSeeResult = findViewById(R.id.case03_mirror_see_result);
        m07CaseLiveSee = findViewById(R.id.case03_live_see);
        m08CaseTest = findViewById(R.id.case03_test);
        m09CaseCytology = findViewById(R.id.case03_cytology);
        m10CasePathology = findViewById(R.id.case03_pathology);
        m11CaseAdvise = findViewById(R.id.case03_advise);
    }

    @Override
    protected void initData() {
        responseListener();
    }

    private void responseListener() {
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    hideInput();
                }
                if (scrollY < oldScrollY) {
                    hideInput();
                }
            }
        });

        setOnClickListener(R.id.case03_sex, R.id.tv_age_type, R.id.case03_see_again, R.id.iv_case_device, R.id.iv_case_office, R.id.iv_case_say
                , R.id.iv_case_bed, R.id.iv_case_mirror_see, R.id.iv_case_see_result, R.id.iv_case_live_see, R.id.iv_case_test
                , R.id.iv_case_cytology, R.id.iv_case_pathology, R.id.iv_case_advise);

        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
                sendAddCaseRequest();

            }
        });

        //获取数据库检查号
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_Add_GetCaseID)
                .addParams("EndoType", getCurrentSectionNum())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("request01==onResponse==onResponse==" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("request01==onResponse==onResponse==" + response);
                        currentNum = response;
                        mCaseCheckNum.setText("" + currentNum);
                    }
                });

        //获取Dialog item的数据
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_Case_Edit)
                .addParams("endotype", getCurrentSectionNum())
                .addParams("dictname", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("edit====" + e);
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void onResponse(String response, int id) {
                        EditDataBean mBean = new EditDataBean();
                        mBean.getData(response);
                        mDialogList = mBean.getM00List();
                        List<EditItemBean> collect = null;
                        for (int i = 0; i < mDialogList.size(); i++) {
                            List<EditItemBean> itemList = mDialogList.get(i);
                            if (7 == i) {
                                collect = itemList.stream().collect(Collectors.toList());
                            } else {
                                collect = itemList.stream().skip(1).collect(Collectors.toList());
                            }
                            for (int i1 = 0; i1 < collect.size(); i1++) {

                            }
                        }
                    }
                });
    }

    //添加病例
    private void sendAddCaseRequest() {
        showDialog();
        String mCaseNo = mCaseNumber.getText().toString();
        String mName = mCaseName.getText().toString();
        String mSex = mCaseSex.getText().toString();
        String mAge = mCaseAge.getText().toString();
        String mAgeTypeData = mAgeType.getText().toString();
        String mProfession = mCaseProfession.getText().toString();
        String mPhone = mCasePhone.getText().toString();
        String mAddress = mCaseAddress.getText().toString();
        String mHistory = mCaseHistory.getText().toString();
        String mSeeAgain = mCaseSeeAgain.getText().toString();
        String mSeeCharge = mCaseSeeCharge.getText().toString();
        String mDoctor = mCaseDoctor.getText().toString();
        String m01Device = m01CaseDevice.getText().toString();
        String m02Office = m02CaseOffice.getText().toString();
        String m03Say = m03CaseSay.getText().toString();
        String m04BedSea = m04CaseBedSea.getText().toString();
        String m05MirrorSee = m05CaseMirrorSee.getText().toString();
        String m06MirrorSeeResult = m06CaseMirrorSeeResult.getText().toString();
        String m07LiveSee = m07CaseLiveSee.getText().toString();
        String m08Test = m08CaseTest.getText().toString();
        String m09Cytology = m09CaseCytology.getText().toString();
        String m10Pathology = m10CasePathology.getText().toString();
        String m11Advise = m11CaseAdvise.getText().toString();
        //添加天月岁  AgeUnit
        OkHttpUtils.post()
                .url(getCurrentHost() + HttpConstant.CaseManager_Add_Patients)
                .addParams("CaseID", mCaseNo).addParams("Name", mName)    //UserName  当前系统用户  Name字段是当前病人用户
                .addParams("Sex", mSex).addParams("PatientAge", mAge)
                .addParams("Occupatior", mProfession).addParams("Tel", mPhone)
                .addParams("Address", mAddress).addParams("MedHistory", mHistory)  //医疗病史   FamilyHistory ---家族病史
                .addParams("CaseNo", currentNum).addParams("ReturnVisit", mSeeAgain)
                .addParams("Fee", mSeeCharge).addParams("ExaminingPhysician", mDoctor)   //检查医生（中文）
                .addParams("Device", m01Device).addParams("Department", m02Office)
                .addParams("ChiefComplaint", m03Say).addParams("ClinicalDiagnosis", m04BedSea)
                .addParams("CheckContent", m05MirrorSee).addParams("CheckDiagnosis", m06MirrorSeeResult)
                .addParams("Biopsy", m07LiveSee).addParams("Test", m08Test)
                .addParams("Ctology", m09Cytology).addParams("Pathology", m10Pathology)
                .addParams("Advice", m11Advise).addParams("AgeUnit", mAgeTypeData)
                .addParams("BedID", "").addParams("CardID", "")
                .addParams("DOB", "").addParams("EndoType", getCurrentSectionNum())
                .addParams("FamilyHistory", "").addParams("InsuranceID", "")
                .addParams("IsInHospital", "").addParams("Married", "")
                .addParams("NativePlace", "").addParams("PatientNo", "")
                .addParams("Race", "").addParams("RecordType", "endoscopy_check")
                .addParams("UserName", getCurrentUserName()).addParams("PatientID", "0")
                .addParams("WardID", "").addParams("SubmitDoctor", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                        toast("请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if ("1".equals(response)) {
                            toast("添加成功");
                            sendGetLastCaseNORequest();
                        } else if ("-1".equals(response)) {
                            toast("检查号失效，请重新进入该页面进行添加操作");
                        } else {
                            toast("添加失败");
                        }
                    }
                });
    }

    /**
     * 获取最后一个病例的ID号,然后添加到recycleview中 避免重复请求
     */
    private void sendGetLastCaseNORequest() {
        LogUtils.e("last==Request==endotype==02=" + getCurrentSectionNum());
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_Get_LastNo)
                .addParams("endotype", getCurrentSectionNum())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("last==Request==onError===" + e);
                        hideDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("last==Request==onResponse===" + response);
                        // patients.aspx
                        sendGetLastBeanDataRequest(response);
                    }
                });
    }

    //获取Bean刷新上一个Activity数据列表
    private void sendGetLastBeanDataRequest(String id) {
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_Case_Detail)
                .addParams("patientsid", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideDialog();
                        if ("0".equals(response)) {
                            toast("请求参数有误");
                        } else {
                            LogUtils.e("TAG--01" + response);
                            String regEx = "[\n  \\r\\n \\t  \\\\]";
                            String aa = " ";//这里是将特殊字符换为aa字符串," "代表直接去掉
                            Pattern p = Pattern.compile(regEx);
                            Matcher m = p.matcher(response);//这里把想要替换的字符串传进来
                            String newString = m.replaceAll(aa).trim();
                            LogUtils.e("TAG--01" + newString);
                            Type type = new TypeToken<CaseDetailMsgBean>() {
                            }.getType();
                            CaseDetailMsgBean bean = mGson.fromJson(newString, type);
                            if (bean.getDs().size() >= 0) {
                                CaseDetailMsgBean.DsDTO mBean = bean.getDs().get(0);
                                CaseManagerListBean.DsDTO dataBean = new CaseManagerListBean.DsDTO();
                                String id1 = mBean.getID();
                                String name = mBean.getName();
                                String pathology = mBean.getPathology();
                                String recorddate = mBean.getRecordDate();
                                String endotype = mBean.getEndoType();
                                dataBean.setID(id1 + "");
                                dataBean.setName(name + "");
                                dataBean.setPathology(pathology + "");
                                dataBean.setRecordDate(recorddate + "");
                                dataBean.setEndoType(endotype + "");
                                EventBus.getDefault().post(new AddDeleteEvent(dataBean, "add", 0));
                                finish();
                            }
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.case03_sex:               //性别
                selectedSex();
                break;
            case R.id.tv_age_type:               //年龄表示方式==     //添加天,月,岁  AgeUnit
                selectedAgeType();
                break;
            case R.id.case03_see_again:         //复诊
                selectedSeeAgain();
                break;
            case R.id.iv_case_device:           //设备    1
                showCurrentSelectedDialog(getPositionDataList(3), "3");
                break;
            case R.id.iv_case_office:           //科室    2
                showCurrentSelectedDialog(getPositionDataList(2), "2");
                break;
            case R.id.iv_case_say:              //主述    3
                showCurrentSelectedDialog(getPositionDataList(1), "1");
                break;
            case R.id.iv_case_bed:              //临床诊断  4
                showCurrentSelectedDialog(getPositionDataList(4), "4");
                break;
            case R.id.iv_case_mirror_see:       //镜检所见  5
                showCurrentSelectedDialog(getPositionDataList(5), "5");
                break;
            case R.id.iv_case_see_result:       //镜检诊断  6
                showCurrentSelectedDialog(getPositionDataList(6), "6");
                break;
            case R.id.iv_case_live_see:         //活检    7
                showCurrentSelectedDialog(getPositionDataList(7), "7");
                break;
            case R.id.iv_case_test:             //试验    8
                showCurrentSelectedDialog(getPositionDataList(9), "9");
                break;
            case R.id.iv_case_cytology:         //细胞学   9
                showCurrentSelectedDialog(getPositionDataList(8), "8");
                break;
            case R.id.iv_case_pathology:        //病理学   10
                showCurrentSelectedDialog(getPositionDataList(10), "10");
                break;
            case R.id.iv_case_advise:            //建议    11
                showCurrentSelectedDialog(getPositionDataList(11), "11");
                break;
        }
    }


    private void showCurrentSelectedDialog(List<String> data, String type) {
        if (data.size() == 0) {
            data.add("暂无数据!");
        }
        // 底部选择框
        new MenuDialog.Builder(this)
                // 设置 null 表示不显示取消按钮
                //.setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setList(data)
                .setListener(new MenuDialog.OnListener<String>() {

                    @Override
                    public void onSelected(BaseDialog dialog, int position, String string) {
                        toast(string);
                        setCurrentEditText(string, type);

                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消了");
                    }
                })
                .show();

    }

    /**
     * @param string
     * @param type
     */
    private void setCurrentEditText(String string, String type) {
        if ("暂无数据!".equals(string)) {
            string = "";
        }
        switch (type) {
            case "1":  //因为返回的数据错乱所以将错就错
                m03CaseSay.setText(m03CaseSay.getText().toString().trim() + string);
                break;
            case "2":
                m02CaseOffice.setText(m02CaseOffice.getText().toString().trim() + string);
                break;
            case "3":
                m01CaseDevice.setText(m01CaseDevice.getText().toString().trim() + string);
                break;
            case "4":
                m04CaseBedSea.setText(m04CaseBedSea.getText().toString().trim() + string);
                break;
            case "5":
                m05CaseMirrorSee.setText(m05CaseMirrorSee.getText().toString().trim() + string);
                break;
            case "6":
                m06CaseMirrorSeeResult.setText(m06CaseMirrorSeeResult.getText().toString().trim() + string);
                break;
            case "7":
                m07CaseLiveSee.setText(m07CaseLiveSee.getText().toString().trim() + string);
                break;
            case "8":
                m08CaseTest.setText(m08CaseTest.getText().toString().trim() + string);
                break;
            case "9":
                m09CaseCytology.setText(m09CaseCytology.getText().toString().trim() + string);
                break;
            case "10":
                m10CasePathology.setText(m10CasePathology.getText().toString().trim() + string);
                break;
            case "11":
                m11CaseAdvise.setText(m11CaseAdvise.getText().toString().trim() + string);
                break;
        }

    }


    /**
     * 对List数据处理获取自己要的List
     *
     * @param position
     * @return
     */
    @SuppressLint("NewApi")
    private List<String> getPositionDataList(int position) {
        List<EditItemBean> mDataList = null;
        if (position == 7) {
            mDataList = mDialogList.get(position).stream().collect(Collectors.toList());
        } else {
            mDataList = mDialogList.get(position).stream().skip(1).collect(Collectors.toList());
        }
        ArrayList<String> list = new ArrayList<>();
        mDataList.stream().forEach((EditItemBean bean) -> {
            list.add(bean.getDictItem());
        });
        return list;
    }

    private void selectedSeeAgain() {

        new SelectDialog.Builder(this)
                .setTitle("提示")
                .setList("是", "否")
                // 设置单选模式
                .setSingleSelect()
                // 设置默认选中
                .setSelect(0)
                .setListener(new SelectDialog.OnListener<String>() {

                    @Override
                    public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                        String str = data.toString();
                        String substring = str.substring(str.length() - 2, str.length() - 1);
                        mCaseSeeAgain.setText("" + substring);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消了");
                    }
                })
                .show();

    }

    private void selectedSex() {
        new SelectDialog.Builder(this)
                .setTitle("请选择你的性别")
                .setList("男", "女")
                // 设置单选模式
                .setSingleSelect()
                // 设置默认选中
                .setSelect(0)
                .setListener(new SelectDialog.OnListener<String>() {

                    @Override
                    public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                        String str = data.toString();
                        String substring = str.substring(str.length() - 2, str.length() - 1);
                        mCaseSex.setText("" + substring);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消了");
                    }
                })
                .show();
    }

    /**
     * 获取不同类型的时间显示方式
     * //添加天,月,岁
     */
    private void selectedAgeType() {
        new SelectDialog.Builder(this)
                .setTitle("请选择你的性别")
                .setList("天", "月", "岁")
                // 设置单选模式
                .setSingleSelect()
                // 设置默认选中
                .setSelect(0)
                .setListener(new SelectDialog.OnListener<String>() {
                    @Override
                    public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                        String str = data.toString();
                        String substring = str.substring(str.length() - 2, str.length() - 1);
                        //{1=月}
                        mAgeType.setText("" + substring);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消了");
                    }
                })
                .show();
    }

    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
