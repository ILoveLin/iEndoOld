package org.company.iendo.mineui.activity.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.hjq.bar.TitleBar;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.umeng.UmengClient;
import com.hjq.widget.view.SwitchButton;

import org.company.iendo.R;
import org.company.iendo.aop.DebugLog;
import org.company.iendo.aop.SingleClick;
import org.company.iendo.common.MyActivity;
import org.company.iendo.helper.InputTextHelper;
import org.company.iendo.http.model.HttpData;
import org.company.iendo.http.request.LoginApi;
import org.company.iendo.http.response.LoginBean;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.other.IntentKey;
import org.company.iendo.other.KeyboardWatcher;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 登录界面
 */
public final class LoginActivity extends MyActivity
        implements KeyboardWatcher.SoftKeyboardStateListener {

    private TitleBar mTitleBar;
    private SwitchButton mSwithRemeber;
    private ImageView mAdd;

    @DebugLog
    public static void start(Context context, String phone, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IntentKey.PHONE, phone);
        intent.putExtra(IntentKey.PASSWORD, password);
        context.startActivity(intent);
    }

    private ImageView mLogoView;
    private ViewGroup mBodyLayout;
    private EditText mPhoneView;
    private EditText mPasswordView;
    private Button mCommitView;
    private View mBlankView;
    private View mOtherView;


    /**
     * logo 缩放比例
     */
    private final float mLogoScale = 0.8f;
    /**
     * 动画时间
     */
    private final int mAnimTime = 300;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        mLogoView = findViewById(R.id.iv_login_logo);
        mBodyLayout = findViewById(R.id.ll_login_body);
        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mCommitView = findViewById(R.id.btn_login_commit);
        mBlankView = findViewById(R.id.v_login_blank);
        mOtherView = findViewById(R.id.ll_login_other);
        mTitleBar = findViewById(R.id.titlebar);
        mSwithRemeber = findViewById(R.id.sb_setting_switch);
        mAdd = findViewById(R.id.iv_add);
        setOnClickListener(mCommitView, mTitleBar.getLeftView(),mAdd,mSwithRemeber);



        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .setListener(new InputTextHelper.OnInputTextListener() {
                    @Override
                    public boolean onInputChange(InputTextHelper helper) {

                        return mPhoneView.getText().toString().length() >= 0 &&
                                mPasswordView.getText().toString().length() >= 0;
                    }
                })
                .build();
    }

    @Override
    protected void initData() {

//        byte[] bytt =new byte[1024];
        postDelayed(() -> {
            // 因为在小屏幕手机上面，因为计算规则的因素会导致动画效果特别夸张，所以不在小屏幕手机上面展示这个动画效果
            if (mBlankView.getHeight() > mBodyLayout.getHeight()) {
                // 只有空白区域的高度大于登录框区域的高度才展示动画
                KeyboardWatcher.with(LoginActivity.this)
                        .setListener(LoginActivity.this);
            }
        }, 500);

        // 填充传入的手机号和密码
        mPhoneView.setText(getString(IntentKey.PHONE));
        mPasswordView.setText(getString(IntentKey.PASSWORD));
    }


    @SingleClick
    @Override
    public void onClick(View v) {

        if (v == mTitleBar.getLeftView()) { //退出
            finish();
        } else if (v == mSwithRemeber) { //是否记住密码
        } else if (v == mAdd) {  //添加服务器
            startActivity(new Intent(this, AddServersActivity.class));

//            AddServersActivity.start(this, new AddServersActivity.OnServersSelectedListener() {
//                @Override
//                public void onServersSelected(String deviceType, String ip, String port) {
//                    //TODO 拿到会写的数据  设备类型,ip,端口号
//                }
//            });
        } else if (v == mCommitView) {  //提交
            if (mPhoneView.getText().toString().equals("")) {
                toast("账号不能为空");
                return;
            }
            if (mPasswordView.getText().toString().equals("")) {
                toast("密码不能为空");
                return;
            }
            if (true) {
                showDialog();
                postDelayed(() -> {
                    hideDialog();
                    startActivity(MainActivity.class);
                    finish();
                }, 2000);
                return;
            }

            EasyHttp.post(this)
                    .api(new LoginApi()
                            .setPhone(mPhoneView.getText().toString())
                            .setPassword(mPasswordView.getText().toString()))
                    .request(new HttpCallback<HttpData<LoginBean>>(this) {

                        @Override
                        public void onSucceed(HttpData<LoginBean> data) {
                            // 更新 Token
                            EasyConfig.getInstance()
                                    .addParam("token", data.getData().getToken());
                            // 跳转到主页
//                            startActivity(HomeActivity.class);
                            finish();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 友盟登录回调
        UmengClient.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int[] location = new int[2];
        // 获取这个 View 在屏幕中的坐标（左上角）
        mBodyLayout.getLocationOnScreen(location);
        //int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + mBodyLayout.getHeight());
        if (keyboardHeight > bottom) {
            // 执行位移动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", 0, -(keyboardHeight - bottom));
            objectAnimator.setDuration(mAnimTime);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();

            // 执行缩小动画
            mLogoView.setPivotX(mLogoView.getWidth() / 2f);
            mLogoView.setPivotY(mLogoView.getHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", 1.0f, mLogoScale);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", 1.0f, mLogoScale);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", 0.0f, -(keyboardHeight - bottom));
            animatorSet.play(translationY).with(scaleX).with(scaleY);
            animatorSet.setDuration(mAnimTime);
            animatorSet.start();
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        // 执行位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", mBodyLayout.getTranslationY(), 0);
        objectAnimator.setDuration(mAnimTime);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

        if (mLogoView.getTranslationY() == 0) {
            return;
        }
        // 执行放大动画
        mLogoView.setPivotX(mLogoView.getWidth() / 2f);
        mLogoView.setPivotY(mLogoView.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", mLogoScale, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", mLogoScale, 1.0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", mLogoView.getTranslationY(), 0);
        animatorSet.play(translationY).with(scaleX).with(scaleY);
        animatorSet.setDuration(mAnimTime);
        animatorSet.start();
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }
}