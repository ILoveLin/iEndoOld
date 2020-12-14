package org.company.iendo.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseActivity;
import com.hjq.base.BaseDialog;

import org.company.iendo.R;
import org.company.iendo.action.SwipeAction;
import org.company.iendo.action.TitleBarAction;
import org.company.iendo.action.ToastAction;
import org.company.iendo.bean.CaseManagerListBean;
import org.company.iendo.bean.beandb.UserDetailMSGDBBean;
import org.company.iendo.http.model.HttpData;
import org.company.iendo.ui.dialog.WaitDialog;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.db.UserDetailMSGDBUtils;

import com.hjq.http.listener.OnHttpListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目中的 Activity 基类
 */
public abstract class MyActivity extends BaseActivity
        implements ToastAction, TitleBarAction,
        SwipeAction, OnHttpListener {


    /**
     * 标题栏对象
     */
    private TitleBar mTitleBar;
    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;

    /**
     * 加载对话框
     */
    private BaseDialog mDialog;
    /**
     * 对话框数量
     */
    private int mDialogTotal;
    public Gson mGson;

    public List getUserDBList() {
        List<UserDetailMSGDBBean> list = UserDetailMSGDBUtils.queryAll(UserDetailMSGDBBean.class);
        ArrayList<CaseManagerListBean.DsDTO> mData = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CaseManagerListBean.DsDTO mBean = new CaseManagerListBean.DsDTO();
            mBean.setID(list.get(i).getID());
            mBean.setEndoType(list.get(i).getEndoType());
            mBean.setRecordDate(list.get(i).getRecordDate());
            mBean.setPathology(list.get(i).getPathology());
            mBean.setName(list.get(i).getName());
            mData.add(mBean);
        }
        return mData;
    }

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return mDialog != null && mDialog.isShowing();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        mDialogTotal++;
        postDelayed(() -> {
            if (mDialogTotal > 0 && !isFinishing()) {
                if (mDialog == null) {
                    mDialog = new WaitDialog.Builder(this)
                            .setCancelable(false)
                            .create();
                }
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        }, 300);
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (mDialogTotal > 0) {
            mDialogTotal--;
        }

        if (mDialogTotal == 0 && mDialog != null && mDialog.isShowing() && !isFinishing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        mGson = new Gson();
        if (getTitleBar() != null) {
            getTitleBar().setOnTitleBarListener(this);
        }

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();

            // 设置标题栏沉浸
            if (getTitleBar() != null) {
                ImmersionBar.setTitleBar(this, getTitleBar());
            }
        }

        // activity的管理类,管理全局的activity
        ActivityCollector.getInstance().addActivity(this);

    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 状态栏字体深色模式
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 初始化沉浸式状态栏
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont());
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(@StringRes int id) {
        setTitle(getString(id));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (getTitleBar() != null) {
            getTitleBar().setTitle(title);
        }
    }

    @Override
    @Nullable
    public TitleBar getTitleBar() {
        if (mTitleBar == null) {
            mTitleBar = obtainTitleBar(getContentView());
        }
        return mTitleBar;
    }

    @Override
    public void onLeftClick(View v) {
        onBackPressed();
    }

    /**
     * 获取当前用户权限
     *
     * @return
     */
    public String getCurrentUserPower() {
        return (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Current_UserType, "");
    }

    /**
     * 获取userId
     *
     * @return
     */
    public String getUserId() {
        return (String) SharePreferenceUtil.get(this, SharePreferenceUtil.UserId, "");
    }

    /**
     * 设置登录模式
     *
     * @return
     */
    public void setCurrentOnlineType(Boolean type) {
        SharePreferenceUtil.put(this, SharePreferenceUtil.isOnline, type);
    }

    /**
     * 获取当前url
     *
     * @return
     */
    public String getLiveConnectUrl() {
//        rtsp://root:root@ip:port/session1.mpg

        String ip = (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Current_IP, "");
        return "rtsp://root:root@" + ip + ":7788" + "/session1.mpg";

    }

    /**
     * 获取登录模式
     * 默认在线登录
     *
     * @return
     */
    public Boolean getCurrentOnlineType() {
        Boolean isOnline = (Boolean) SharePreferenceUtil.get(this, SharePreferenceUtil.isOnline, true);
        return isOnline;

    }

    /**
     * 获取登录ip和port
     *
     * @return
     */
    public String getCurrentHost() {
        String Host = (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Current_Host, "");
        return Host;

    }

    /**
     * 获取当前登入的用户名
     *
     * @return
     */
    public String getCurrentUserName() {
        String Host = (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Current_Username, "");
        return Host;

    }

    /**
     * 获取登录模式
     *
     * @return
     */
    public String getCurrentSectionNum() {
        String section = (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Current_Case_Num, "");
        return section;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.right_in_activity, R.anim.right_out_activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in_activity, R.anim.left_out_activity);
    }

    /**
     * {@link OnHttpListener}
     */

    @Override
    public void onStart(Call call) {
        showDialog();
    }

    @Override
    public void onSucceed(Object result) {
        if (result instanceof HttpData) {
            toast(((HttpData) result).getMessage());
        }
    }

    @Override
    public void onFail(Exception e) {
        toast(e.getMessage());
    }

    @Override
    public void onEnd(Call call) {
        hideDialog();
    }

    @Override
    protected void onDestroy() {
        if (isShowDialog()) {
            hideDialog();
        }
        mDialog = null;
        //移除activity 管理器
        ActivityCollector.getInstance().removeActivity(this);
        super.onDestroy();
    }
}