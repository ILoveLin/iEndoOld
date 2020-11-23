package org.company.iendo.mineui.activity.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.http.model.HttpData;
import org.company.iendo.http.request.UserInfoApi;
import org.company.iendo.http.response.UserInfoBean;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.other.AppConfig;
import org.company.iendo.util.SharePreferenceUtil;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 闪屏界面
 */
public final class SplashActivity extends MyActivity {

    private LottieAnimationView mLottieView;
    private View mDebugView;
    private Boolean isLogin;
    private Boolean isFirstIn;

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initView() {
        mLottieView = findViewById(R.id.iv_splash_lottie);
        mDebugView = findViewById(R.id.iv_splash_debug);
        isFirstIn = (Boolean) SharePreferenceUtil.get(this, SharePreferenceUtil.is_First_in, true);
        isLogin = (Boolean) SharePreferenceUtil.get(this, SharePreferenceUtil.is_login, false);

        // 设置动画监听
        mLottieView.addAnimatorListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                switchGoing();
            }
        });
    }

    private void switchGoing() {
        //第一次进入-- 走引导页，否则进入MainActivity
        Intent intent = new Intent();
        if (isFirstIn) {
            SharePreferenceUtil.put(SplashActivity.this, SharePreferenceUtil.is_First_in, true);
            intent.setClass(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {  //不是第一次进App,判断是否登陆过
            if (!isLogin) {  // false==未登录
                intent.setClass(SplashActivity.this, LoginActivity.class);
            } else {   //已经登陆
                intent.setClass(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void initData() {
        if (AppConfig.isDebug()) {
            mDebugView.setVisibility(View.VISIBLE);
        } else {
            mDebugView.setVisibility(View.INVISIBLE);
        }

        if (true) {
            return;
        }
        // 刷新用户信息
        EasyHttp.post(this)
                .api(new UserInfoApi())
                .request(new HttpCallback<HttpData<UserInfoBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<UserInfoBean> data) {

                    }
                });
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 隐藏状态栏和导航栏
                .hideBar(BarHide.FLAG_HIDE_BAR);
    }

    @Override
    public void onBackPressed() {
        //禁用返回键
        //super.onBackPressed();
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    protected void onDestroy() {
        mLottieView.removeAllAnimatorListeners();
        super.onDestroy();
    }
}