package org.company.iendo.mineui.activity.casemsg;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hjq.widget.view.ClearEditText;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.ui.dialog.SelectDialog;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LoveLin
 * <p>
 * Describe  编辑界面
 */
public class EditActivity extends MyActivity {

    private ClearEditText mCaseNumber;
    private ClearEditText mCaseName;
    private TextView mCaseSex;
    private ClearEditText mCaseAge;
    private ClearEditText mCaseProfession;
    private ClearEditText mCasePhone;
    private ClearEditText mCaseAddress;
    private ClearEditText mCaseHistory;
    private ClearEditText mCaseCheckNum;
    private TextView mCaseSeeAgain;
    private ClearEditText mCaseSeeCharge;
    private ClearEditText mCaseDoctor;
    private ClearEditText mCaseDevice;
    private ImageView mIVCaseDevice;
    private ClearEditText mCaseOffice;
    private ImageView mIVCaseOffice;
    private ClearEditText mCaseSay;
    private ImageView mIVCaseSay;
    private ClearEditText mCaseBedSea;
    private ImageView mIVCaseBed;
    private ClearEditText mCaseMirrorSee;
    private ImageView mIVCaseMirrorSee;
    private ClearEditText mCaseMirrorSeeResult;
    private ImageView mIVCaseSeeResult;
    private ClearEditText mCaseLiveSee;
    private ImageView mIVCaseLive_see;
    private ClearEditText mCaseTest;
    private ImageView mIVCaseTest;
    private ClearEditText mCaseCytology;
    private ImageView mIVCaseCytology;
    private ClearEditText mCasePathology;
    private ImageView mIVCasePathology;
    private ClearEditText mCaseAdvise;
    private ImageView mIVCaseAdvise;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initView() {
        ClearEditText mCaseNumber = findViewById(R.id.case03_case_number);
        mCaseName = findViewById(R.id.case03_name);
        //弹出单选框
        mCaseSex = findViewById(R.id.case03_sex);
        mCaseAge = findViewById(R.id.case03_age);
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
        mCaseDevice = findViewById(R.id.case03_device);
        mIVCaseDevice = findViewById(R.id.iv_case_device);
        mCaseOffice = findViewById(R.id.case03_office);
        mIVCaseOffice = findViewById(R.id.iv_case_office);
        mCaseSay = findViewById(R.id.case03_say);
        mIVCaseSay = findViewById(R.id.iv_case_say);
        mCaseBedSea = findViewById(R.id.case03_bed_sea);
        mIVCaseBed = findViewById(R.id.iv_case_bed);


        mCaseMirrorSee = findViewById(R.id.case03_mirror_see);
        mIVCaseMirrorSee = findViewById(R.id.iv_case_mirror_see);

        mCaseMirrorSeeResult = findViewById(R.id.case03_mirror_see_result);
        mIVCaseSeeResult = findViewById(R.id.iv_case_see_result);

        mCaseLiveSee = findViewById(R.id.case03_live_see);
        mIVCaseLive_see = findViewById(R.id.iv_case_live_see);

        mCaseTest = findViewById(R.id.case03_test);
        mIVCaseTest = findViewById(R.id.iv_case_test);


        mCaseCytology = findViewById(R.id.case03_cytology);
        mIVCaseCytology = findViewById(R.id.iv_case_cytology);

        mCasePathology = findViewById(R.id.case03_pathology);
        mIVCasePathology = findViewById(R.id.iv_case_pathology);

        mCaseAdvise = findViewById(R.id.case03_advise);
        mIVCaseAdvise = findViewById(R.id.iv_case_advise);

        responseListener();


    }


    @Override
    protected void initData() {

    }


    private void responseListener() {
        setOnClickListener(R.id.case03_sex, R.id.case03_see_again, R.id.iv_case_device, R.id.iv_case_office, R.id.iv_case_say
                , R.id.iv_case_bed, R.id.iv_case_mirror_see, R.id.iv_case_see_result, R.id.iv_case_live_see, R.id.iv_case_test
                , R.id.iv_case_cytology, R.id.iv_case_pathology, R.id.iv_case_advise);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.case03_sex:               //性别
                selectedSex();
                break;
            case R.id.case03_see_again:         //复诊
                selectedSeeAgain();
                break;
            case R.id.iv_case_device:           //设备
                break;
            case R.id.iv_case_office:           //科室
                break;
            case R.id.iv_case_say:              //主述
                break;
            case R.id.iv_case_bed:              //临床诊断
                break;
            case R.id.iv_case_mirror_see:       //镜检所见
                break;
            case R.id.iv_case_see_result:       //镜检诊断
                break;
            case R.id.iv_case_live_see:         //活检
                break;
            case R.id.iv_case_test:             //试验
                break;
            case R.id.iv_case_cytology:         //细胞学
                break;
            case R.id.iv_case_pathology:        //病理学
                break;
            case R.id.iv_case_advise:            //建议
                break;
        }
    }

    private void selectedSeeAgain() {

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
                        toast("确定了：" + data.toString());
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消了");
                    }
                })
                .show();
    }
}
