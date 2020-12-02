package org.company.iendo.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseFragment;

import org.company.iendo.action.TitleBarAction;
import org.company.iendo.action.ToastAction;
import org.company.iendo.http.model.HttpData;
import org.company.iendo.util.SharePreferenceUtil;

import com.hjq.http.listener.OnHttpListener;

import okhttp3.Call;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目中 Fragment 懒加载基类
 */
public abstract class MyFragment<A extends MyActivity> extends BaseFragment<A>
        implements ToastAction, TitleBarAction, OnHttpListener {

    /**
     * 标题栏对象
     */
    private TitleBar mTitleBar;
    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;
    public Gson mGson;

    @Override
    protected void initFragment() {
        super.initFragment();
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
    }

    /**
     * 获取userId
     *
     * @return
     */
    public String getUserId() {
        return (String) SharePreferenceUtil.get(getAttachActivity(), SharePreferenceUtil.UserId, "");
    }

    /**
     * 设置登录模式
     *
     * @return
     */
    public void setCurrentOnlineType(Boolean type) {
        SharePreferenceUtil.put(getAttachActivity(), SharePreferenceUtil.isOnline, type);
    }

    /**
     * 获取登录模式
     *
     * @return
     */
    public Boolean getCurrentOnlineType() {
        Boolean isOnline = (Boolean) SharePreferenceUtil.get(getAttachActivity(), SharePreferenceUtil.isOnline, true);
        return isOnline;

    }

    /**
     * 获取登录模式
     *
     * @return
     */
    public String getCurrentHost() {
        String Host = (String) SharePreferenceUtil.get(getAttachActivity(), SharePreferenceUtil.Current_Host, "");

        return Host;

    }
    public String getCurrentIP() {
        String Host = (String) SharePreferenceUtil.get(getAttachActivity(), SharePreferenceUtil.Current_IP, "");

        return Host;

    }
    /**
     * 是否在 Fragment 使用沉浸式
     */
    public boolean isStatusBarEnabled() {
        return false;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    protected ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(statusBarDarkFont())
                // 解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardEnable(true);
    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarDarkFont() {
        // 返回真表示黑色字体
        return true;
    }

    @Override
    @Nullable
    public TitleBar getTitleBar() {
        if (mTitleBar == null) {
            mTitleBar = obtainTitleBar((ViewGroup) getView());
        }
        return mTitleBar;
    }

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        A activity = getAttachActivity();
        if (activity != null) {
            return activity.isShowDialog();
        } else {
            return false;
        }
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        A activity = getAttachActivity();
        if (activity != null) {
            activity.showDialog();
        }
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        A activity = getAttachActivity();
        if (activity != null) {
            activity.hideDialog();
        }
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
    public void onResume() {
        super.onResume();
        if (isStatusBarEnabled()) {
            // 重新初始化状态栏
            getStatusBarConfig().init();
        }
    }
}