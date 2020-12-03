package org.company.iendo.mineui.activity.casemsg;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hjq.widget.view.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.bean.EditDataBean;
import org.company.iendo.bean.EditItemBean;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.ui.dialog.MenuDialog;
import org.company.iendo.ui.dialog.SelectDialog;
import org.company.iendo.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;

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
    private ClearEditText m01CaseDevice;
    private ImageView mIVCaseDevice;
    private ClearEditText m02CaseOffice;
    private ImageView mIVCaseOffice;
    private ClearEditText m03CaseSay;
    private ImageView mIVCaseSay;
    private ClearEditText m04CaseBedSea;
    private ImageView mIVCaseBed;
    private ClearEditText m05CaseMirrorSee;
    private ImageView mIVCaseMirrorSee;
    private ClearEditText m06CaseMirrorSeeResult;
    private ImageView mIVCaseSeeResult;
    private ClearEditText m07CaseLiveSee;
    private ImageView mIVCaseLive_see;
    private ClearEditText m08CaseTest;
    private ImageView mIVCaseTest;
    private ClearEditText m09CaseCytology;
    private ImageView mIVCaseCytology;
    private ClearEditText m10CasePathology;
    private ImageView mIVCasePathology;
    private ClearEditText m11CaseAdvise;
    private ImageView mIVCaseAdvise;
    private List<List<EditItemBean>> mDialogList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initView() {
        mCaseNumber = findViewById(R.id.case03_case_number);
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
        m01CaseDevice = findViewById(R.id.case03_device);
        mIVCaseDevice = findViewById(R.id.iv_case_device);
        m02CaseOffice = findViewById(R.id.case03_office);
        mIVCaseOffice = findViewById(R.id.iv_case_office);
        m03CaseSay = findViewById(R.id.case03_say);
        mIVCaseSay = findViewById(R.id.iv_case_say);
        m04CaseBedSea = findViewById(R.id.case03_bed_sea);
        mIVCaseBed = findViewById(R.id.iv_case_bed);
        m05CaseMirrorSee = findViewById(R.id.case03_mirror_see);
        mIVCaseMirrorSee = findViewById(R.id.iv_case_mirror_see);
        m06CaseMirrorSeeResult = findViewById(R.id.case03_mirror_see_result);
        mIVCaseSeeResult = findViewById(R.id.iv_case_see_result);
        m07CaseLiveSee = findViewById(R.id.case03_live_see);
        mIVCaseLive_see = findViewById(R.id.iv_case_live_see);
        m08CaseTest = findViewById(R.id.case03_test);
        mIVCaseTest = findViewById(R.id.iv_case_test);
        m09CaseCytology = findViewById(R.id.case03_cytology);
        mIVCaseCytology = findViewById(R.id.iv_case_cytology);
        m10CasePathology = findViewById(R.id.case03_pathology);
        mIVCasePathology = findViewById(R.id.iv_case_pathology);
        m11CaseAdvise = findViewById(R.id.case03_advise);
        mIVCaseAdvise = findViewById(R.id.iv_case_advise);
    }


    @Override
    protected void initData() {
        responseListener();
    }

    private void responseListener() {
        setOnClickListener(R.id.case03_sex, R.id.case03_see_again, R.id.iv_case_device, R.id.iv_case_office, R.id.iv_case_say
                , R.id.iv_case_bed, R.id.iv_case_mirror_see, R.id.iv_case_see_result, R.id.iv_case_live_see, R.id.iv_case_test
                , R.id.iv_case_cytology, R.id.iv_case_pathology, R.id.iv_case_advise);
        LogUtils.e("edit====" + getCurrentHost() + HttpConstant.CaseManager_Case_Edit);
        LogUtils.e("edit==endotype==" + getCurrentSectionNum());
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
                        LogUtils.e("edit====" + response);
                        String json = response.replaceAll("\\p{Cntrl}", "");
                        EditDataBean mBean = new EditDataBean();
                        mBean.getData(response);
                        mDialogList = mBean.getM00List();
                        LogUtils.e("edit==s==02===");
                        LogUtils.e("edit==s==03===" + mDialogList.size());

                        for (int i = 0; i < mDialogList.size(); i++) {
                            List<EditItemBean> itemList = mDialogList.get(i);
                            LogUtils.e("edit==s============================第==" + i + "个List");

                            List<EditItemBean> collect = itemList.stream().skip(1).collect(Collectors.toList());
                            for (int i1 = 0; i1 < collect.size(); i1++) {
                                LogUtils.e("edit==s==itemList==dictItem==" + collect.get(i1).getDictItem());

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
            case R.id.case03_see_again:         //复诊
                selectedSeeAgain();
                break;
            case R.id.iv_case_device:           //设备    1
                showCurrentSelectedDialog(getPositionDataList(1), "1");

                break;
            case R.id.iv_case_office:           //科室    2
                showCurrentSelectedDialog(getPositionDataList(2), "2");
                break;
            case R.id.iv_case_say:              //主述    3
                showCurrentSelectedDialog(getPositionDataList(3), "3");
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
                showCurrentSelectedDialog(getPositionDataList(8), "8");
                break;
            case R.id.iv_case_cytology:         //细胞学   9
                showCurrentSelectedDialog(getPositionDataList(9), "9");
                break;
            case R.id.iv_case_pathology:        //病理学   10
                showCurrentSelectedDialog(getPositionDataList(10), "10");
                break;
            case R.id.iv_case_advise:            //建议
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
            case "1":
                m01CaseDevice.setText(m01CaseDevice.getText().toString().trim() + string);
                break;
            case "2":
                m02CaseOffice.setText(m02CaseOffice.getText().toString().trim() + string);
                break;
            case "3":
                m03CaseSay.setText(m03CaseSay.getText().toString().trim() + string);
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
        List<EditItemBean> mDataList = mDialogList.get(position).stream().skip(1).collect(Collectors.toList());
        ArrayList<String> list = new ArrayList<>();
        mDataList.stream().forEach((EditItemBean bean) -> {
            list.add(bean.getDictItem());
        });
        return list;
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
