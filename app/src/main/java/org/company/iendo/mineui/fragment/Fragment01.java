package org.company.iendo.mineui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
import org.company.iendo.mineui.activity.casemsg.EditActivity;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.mineui.activity.live.LiveConnectDeviceActivity;
import org.company.iendo.ui.adapter.StatusAdapter;
import org.company.iendo.util.LogUtils;
import org.company.iendo.widget.HintLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe 第一个fragment
 */
public class Fragment01 extends MyFragment<MainActivity> implements
        StatusAction, CaseOperatorAction {
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;
    private HintLayout mHintLayout;
    private StatusAdapter mAdapter;
    private CaseDetailMsgActivity mActivity;
    private CaseDetailMsgBean.DsDTO mBean;
    private TextView mNumber;
    private TextView mName;
    private TextView mSex;
    private TextView mAge;
    private TextView mProfession;
    private TextView mPhone;
    private TextView mAddress;
    private TextView mHistory;

    public Fragment01(CaseDetailMsgActivity Activity) {
        this.mActivity = Activity;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_01;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mNumber = findViewById(R.id.case01_number);
        mName = findViewById(R.id.case01_name);
        mSex = findViewById(R.id.case01_sex);
        mAge = findViewById(R.id.case01_age);
        mProfession = findViewById(R.id.case01_profession);
        mPhone = findViewById(R.id.case01_phone);
        mAddress = findViewById(R.id.case01_address);
        mHistory = findViewById(R.id.case01_history);
        mActivity.setCaseOperatorAction(this);

    }

    @Override
    protected void initData() {
        sendRequest();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshFragmentEvent(RefreshFragmentEvent event) {
        LogUtils.e("TAG--onRefreshFragmentEvent" + "event====" + event.getType());

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
                            LogUtils.e("TAG--01" + response);
//                            @"\n", @"\r\n", @"\t", @"\\"

                            String regEx = "[\n  \\r\\n \\t  \\\\]";
//                            String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
                            String aa = " ";//这里是将特殊字符换为aa字符串," "代表直接去掉
                            Pattern p = Pattern.compile(regEx);
                            Matcher m = p.matcher(response);//这里把想要替换的字符串传进来
                            String newString = m.replaceAll(aa).trim();
                            LogUtils.e("TAG--01" + newString);
//                            String myJson=   mGson.toJson(response);//将gson转化为json
                            Type type = new TypeToken<CaseDetailMsgBean>() {
                            }.getType();
                            CaseDetailMsgBean bean = mGson.fromJson(newString, type);
                            if (bean.getDs().size() >= 0) {
                                mBean = bean.getDs().get(0);
                                mNumber.setText("" + mBean.getCaseID());
                                mName.setText("" + mBean.getName());
                                mAge.setText("" + mBean.getPatientAge() + mBean.getAgeUnit());
                                mSex.setText("" + mBean.getSex());
                                mProfession.setText("" + mBean.getOccupatior());
                                mPhone.setText("" + mBean.getTel());
                                mHistory.setText("" + mBean.getMedHistory());
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
    public void onResume() {
        super.onResume();
        Log.e("TAG", "fragment01");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onLive() {

//        startActivity(LiveConnectDeviceActivity.class);

    }

    @Override
    public void onPrint() {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDownload() {
        //下载数据存入DB




    }

    @Override
    public void onEdit() {
        Intent intent1 = new Intent(getAttachActivity(), EditActivity.class);
        intent1.putExtra("bean", mBean);
        LogUtils.e("EditActivity发送前=====bean=======" + mBean.toString());
        startActivity(intent1);
    }
}
