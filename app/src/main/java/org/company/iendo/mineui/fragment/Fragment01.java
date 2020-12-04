package org.company.iendo.mineui.fragment;

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
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.mineui.activity.live.LiveConnectDeviceActivity;
import org.company.iendo.ui.adapter.StatusAdapter;
import org.company.iendo.util.LogUtils;
import org.company.iendo.widget.HintLayout;

import java.lang.reflect.Type;

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

                            Type type = new TypeToken<CaseDetailMsgBean>() {
                            }.getType();
                            CaseDetailMsgBean bean = mGson.fromJson(response, type);
                            if (bean.getDs().size() >= 0) {
                                mBean = bean.getDs().get(0);
                                mName.setText("" + mBean.getName());
                                mAge.setText("" + mBean.getPatientAge());
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

    }

    @Override
    public void onEdit() {

    }
}
