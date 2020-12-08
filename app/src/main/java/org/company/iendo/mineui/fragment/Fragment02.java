package org.company.iendo.mineui.fragment;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.bean.CaseDetailMsgBean;
import org.company.iendo.bean.event.RefreshFragmentEvent;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.util.LogUtils;
import org.company.iendo.widget.HintLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe 第二个fragment
 */
public class Fragment02 extends MyFragment<MainActivity> implements StatusAction, CaseOperatorAction {


    private HintLayout mHintLayout;
    private CaseDetailMsgActivity mActivity;
    private TextView case02_check_number;
    private TextView case02_again_see;
    private TextView case02_charge;
    private TextView case02_doctor;
    private TextView case02_device;
    private TextView case02_case;
    private TextView case02_say;
    private TextView case02_bed_see;
    private TextView case02_mirror_see;
    private TextView case02_mirror_see_result;
    private TextView case02_live_see;
    private TextView case02_test;
    private TextView case02_cytology;
    private TextView case02_pathology;
    private TextView case02_advise;
    private TextView case02_check_doctor;
    private CaseDetailMsgBean.DsDTO mBean;

    public Fragment02(CaseDetailMsgActivity Activity) {
        this.mActivity = Activity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_02;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        mHintLayout = findViewById(R.id.hl_status_hint);
        case02_check_number = findViewById(R.id.case02_check_number);
        case02_again_see = findViewById(R.id.case02_again_see);
        case02_charge = findViewById(R.id.case02_charge);
        case02_doctor = findViewById(R.id.case02_doctor);
        case02_device = findViewById(R.id.case02_device);
        case02_case = findViewById(R.id.case02_case);
        case02_say = findViewById(R.id.case02_say);
        case02_bed_see = findViewById(R.id.case02_bed_see);
        case02_mirror_see = findViewById(R.id.case02_mirror_see);
        case02_mirror_see_result = findViewById(R.id.case02_mirror_see_result);
        case02_live_see = findViewById(R.id.case02_live_see);
        case02_test = findViewById(R.id.case02_test);
        case02_cytology = findViewById(R.id.case02_cytology);
        case02_pathology = findViewById(R.id.case02_pathology);
        case02_advise = findViewById(R.id.case02_advise);
        case02_check_doctor = findViewById(R.id.case02_check_doctor);
        mActivity.setCaseOperatorAction(this);
    }

    @Override
    protected void initData() {
        sendRequest();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshFragmentEvent(RefreshFragmentEvent event){
        LogUtils.e("TAG--onRefreshFragmentEvent" + "event===="+event.getType());

        if (event.getType().equals("refresh")) {
            LogUtils.e("TAG--onRefreshFragmentEvent" + "onRefreshFragmentEvent");
            sendRequest();
        }
    }

    private void sendRequest() {
        showDialog();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_Case_Detail)
                .addParams("patientsid", mActivity.getCurrentID())
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
                            LogUtils.e("TAG--02" + response);
                            Type type = new TypeToken<CaseDetailMsgBean>() {
                            }.getType();
                            CaseDetailMsgBean bean = mGson.fromJson(response, type);
                            if (bean.getDs().size() >= 0) {
                                mBean = bean.getDs().get(0);
                                if (mBean.getReturnVisit().equals("False")) {
                                    case02_again_see.setText("否");
                                } else {
                                    case02_again_see.setText("是");
                                }
                                case02_check_number.setText(mBean.getCaseNo() + "");
                                case02_charge.setText(mBean.getFee() + "");
                                case02_doctor.setText(mBean.getSubmitDoctor() + "");
                                case02_device.setText("");  //设备
                                case02_case.setText(mBean.getDepartment() + "");
                                case02_say.setText(mBean.getChiefComplaint() + "");
                                case02_bed_see.setText(mBean.getClinicalDiagnosis() + "");
                                case02_mirror_see.setText(mBean.getCheckContent() + "");
                                case02_mirror_see_result.setText(mBean.getCheckDiagnosis() + "");
                                case02_live_see.setText(mBean.getBiopsy() + "");
                                case02_test.setText(mBean.getTest() + "");
                                case02_cytology.setText(mBean.getCtology() + "");
                                case02_pathology.setText(mBean.getPathology() + "");
                                case02_advise.setText(mBean.getAdvice() + "");
                                case02_check_doctor.setText(mBean.getExaminingPhysician() + "");


//                                private TextView ;
//                                private TextView ;

                            }
                        }


                    }
                });
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "fragment02");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLive() {
        Log.e("TAG", "fragment02=====onLive");

    }

    @Override
    public void onPrint() {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDownload() {

    }

    @Override
    public void onEdit() {

    }
}
