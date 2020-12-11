package org.company.iendo.mineui.activity.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.hjq.bar.TitleBar;
import com.hjq.umeng.UmengClient;
import com.hjq.widget.view.SwitchButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.company.iendo.R;
import org.company.iendo.aop.DebugLog;
import org.company.iendo.aop.SingleClick;
import org.company.iendo.bean.LoginBean;
import org.company.iendo.common.ActivityCollector;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.bean.beandb.UserDBBean;
import org.company.iendo.other.IntentKey;
import org.company.iendo.other.KeyboardWatcher;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.MD5ChangeUtil;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.db.UserDBUtils;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 登录界面
 */
public final class LoginActivity extends MyActivity implements KeyboardWatcher.SoftKeyboardStateListener {
    private TitleBar mTitleBar;
    private SwitchButton mSwithRemeber;
    private ImageView mAdd;
    private NiceSpinner mIfOnLine;
    private NiceSpinner mSection;
    private ImageView mLogoView;
    private ViewGroup mBodyLayout;
    private EditText mPhoneView;
    private EditText mPasswordView;
    private Button mCommitView;
    private View mBlankView;
    private View mOtherView;
    private Boolean ifOnline;
    private String mChooseSection;
    private boolean isRemember;
    private String username;
    private String password;
    private String endoType = "3";


    @DebugLog
    public static void start(Context context, String phone, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IntentKey.PHONE, phone);
        intent.putExtra(IntentKey.PASSWORD, password);
        context.startActivity(intent);
    }


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
        mIfOnLine = findViewById(R.id.niceSpinnerGetOnLine);
        mSection = findViewById(R.id.niceSpinnerSection);
        mAdd = findViewById(R.id.iv_add);

        //系统默认不记住密码,在线登录,耳鼻喉科
        SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.isOnline, true);
        SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Choose_Section, "耳鼻喉科");
        isRemember = (boolean) SharePreferenceUtil.get(LoginActivity.this, SharePreferenceUtil.is_Remember_Password, false);
        ifOnline = (boolean) SharePreferenceUtil.get(LoginActivity.this, SharePreferenceUtil.isOnline, true);
        mChooseSection = (String) SharePreferenceUtil.get(LoginActivity.this, SharePreferenceUtil.Choose_Section, "耳鼻喉科");

        setOnClickListener(mCommitView, mTitleBar.getLeftView(), mAdd, mSwithRemeber);
        if (isRemember) {
            //记住密码直接sp里面取,注意更改当前用户信息,需要实时更新Sp
            String rememberName = (String) SharePreferenceUtil.get(LoginActivity.this, SharePreferenceUtil.Current_Username, "");
            String rememberPassword = (String) SharePreferenceUtil.get(LoginActivity.this, SharePreferenceUtil.Current_Password, "");
            mPhoneView.setText(rememberName);
            mPasswordView.setText(rememberPassword);
            mSwithRemeber.setChecked(true);
        }
        responseListener();


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
//        mPhoneView.setText(getString(IntentKey.PHONE));
//        mPasswordView.setText(getString(IntentKey.PASSWORD));
    }


    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View v) {

        if (v == mTitleBar.getLeftView()) { //退出
            ActivityCollector.removeAll();
            finish();
        }

        switch (v.getId()) {
            case R.id.iv_add:////跳转到添加服务器界面
                startActivity(new Intent(this, AddServersActivity.class));
                break;
            case R.id.sb_setting_switch://是否记住密码
                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.is_Remember_Password, mSwithRemeber.isChecked());  //是否记住密码
                if (mSwithRemeber.isChecked()) {
                    mSwithRemeber.setChecked(true);
                } else {
                    mSwithRemeber.setChecked(false);
                }
                break;
            case R.id.btn_login_commit://登入
                checkData();  //检查数据 如果是离线直接登录其次才是网络请求在线登录
                LogUtils.e("==ifOnline====000==" + ifOnline);
                if (ifOnline) {
                    LogUtils.e("==ifOnline==1==" + ifOnline);
                    try {
                        LogUtils.e("==ifOnline==url==" + getCurrentHost() + HttpConstant.Login);
                        showDialog();
                        OkHttpUtils
                                .get()
                                .url(getCurrentHost() + HttpConstant.Login)
                                .addParams("username", username)
                                .addParams("userpassword", MD5ChangeUtil.Md5_32(password))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        LogUtils.e("=TAG=password=hy=onFail==" + e.toString());
                                        toast(e.toString());
                                        hideDialog();
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
//                                        返回值 0传入参数为空或用户名不存在, -1密码不正确 1登陆成功
                                        LogUtils.e("=TAGpassword=hy=onSucceed==" + response.toString());
                                        hideDialog();
                                        if ("0".equals(response)) {
                                            toast("用户名不存在或者参数为空");
                                        } else if ("-1".equals(response)) {
                                            toast("密码不正确");
                                        } else {
                                            Type type = new TypeToken<LoginBean>() {
                                            }.getType();
                                            LoginBean mBean = mGson.fromJson(response, type);
                                            for (int i = 0; i < mBean.getDs().size(); i++) {

                                                String userType = mBean.getDs().get(mBean.getDs().size()-1).getRole();
                                                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_UserType, userType);

                                                if (i == 0) {
                                                    String createdAt = mBean.getDs().get(i).getCreatedAt();
                                                    String lastLoginAt = mBean.getDs().get(i).getLastLoginAt();
                                                    String loginTimes = mBean.getDs().get(i).getLoginTimes();
                                                    String userid = mBean.getDs().get(i).getUserID();
                                                    String canUSE = mBean.getDs().get(i).getCanUSE();
                                                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_LoginOnlineTime, loginTimes + "");
                                                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_CreateTime, createdAt + "");
                                                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_LastOnlineTime, lastLoginAt + "");
                                                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_Case_Num, endoType);
                                                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_Password, password);
                                                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_Can_Delete, canUSE);
                                                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.UserId, userid);
                                                    //存入当前科室
                                                    setCurrentOnlineType(true);
                                                    startActivity(MainActivity.class);

                                                }
                                            }
                                        }

                                    }
                                });
                    } catch (Exception e) {
                        toast("请求地址有错,请确认无误后再试！");
                        hideDialog();
                    }

                } else {//离线登录
                    LogUtils.e("==ifOnline==2==" + ifOnline);

                    checkDBDataToChangeCurrentUserMsg();

                }
                break;

        }
    }

    /**
     * 离线登录 查找数据库
     */
    private void checkDBDataToChangeCurrentUserMsg() {

        boolean isExist = UserDBUtils.queryListIsExist(username);
        LogUtils.e("isExist===" + isExist);
        if (isExist) {  //存在
            List<UserDBBean> mList = UserDBUtils.queryListByMessage(username);
            String dbusername = mList.get(0).getUsername().toString().trim();
            String dbpassword = mList.get(0).getPassword().toString().trim();
            String dbusertype = mList.get(0).getUserType().toString().trim();
            Long id = mList.get(0).getId();
            LogUtils.e("TAG==登录--dbusername===" + dbusername + "====dbpassword==" + dbpassword + "====dbusertype==" + dbusertype + "====id==" + id);
            LogUtils.e("TAG==登录--dbusername===" + dbusername + "====dbpassword==" + dbpassword + "====dbusertype==" + dbusertype + "====id==" + id);
            if (password.equals(dbpassword)) {  //判断数据库密码和输入密码是否一致,之后更新SP的当前用户信息
                //登录 就要存入当前用户名,密码,用户权限类型,是否记住密码,
                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_Username, dbusername + "");
                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_Password, dbpassword + "");
                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_UserType, dbusertype + "");
//                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.isOnline, ifOnline);
                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.is_Remember_Password, mSwithRemeber.isChecked());  //是否记住密码
                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.UserId, id);
                //登陆模式,登录的科室 --->动态你监听添加了

                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.is_First_in, false);   //false 表示不是第一次登入了  因为默认是第一次登入(true)
                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.is_login, true);   // 是否登录
                setCurrentOnlineType(false);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                toast("密码输入错误");
            }
        } else {
            toast("账户不存在");
        }


    }

    private void checkData() {
        username = mPhoneView.getText().toString().trim();
        password = mPasswordView.getText().toString().trim();
        if (username.equals("")) {
            toast("账号不能为空");
            return;
        }
        if (password.equals("")) {
//            toast("密码不能为空");
//            return;
        }


    }


    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    private void responseListener() {

        mIfOnLine.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                toast(item);
                if ("在线登录".equals(item)) {
                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.isOnline, true);
                    ifOnline = (boolean) SharePreferenceUtil.get(LoginActivity.this, SharePreferenceUtil.isOnline, true);


                } else {
                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.isOnline, false);
                    ifOnline = (boolean) SharePreferenceUtil.get(LoginActivity.this, SharePreferenceUtil.isOnline, false);

                }

            }
        });
        mSection.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                toast(item);
                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Choose_Section, item);
                mChooseSection = (String) SharePreferenceUtil.get(LoginActivity.this, SharePreferenceUtil.Choose_Section, "耳鼻喉科");
                switch (item) {
                    case "耳鼻喉科":
                        endoType = "3";
                        SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Choose_Section, item);
                        SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_Case_Num, endoType);
                        break;
                    case "妇科":
                        endoType = "4";
                        SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Choose_Section, item);
                        SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_Case_Num, endoType);
                        break;
                    case "泌尿科":
                        endoType = "6";
                        SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Choose_Section, item);
                        SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.Current_Case_Num, endoType);
                        break;
                }
            }
        });

//
//        InputTextHelper.with(this)
//                .addView(mPhoneView)
//                .addView(mPhoneView)
//                .addView(mPasswordView)
//                .setMain(mCommitView)
//                .setListener(new InputTextHelper.OnInputTextListener() {
//                    @Override
//                    public boolean onInputChange(InputTextHelper helper) {
//
//                        return mPhoneView.getText().toString().length() >= 1 &&
//                                mPasswordView.getText().toString().length() >= 1;
//                    }
//                })
//                .build();
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

}