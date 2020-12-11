package org.company.iendo.mineui.activity.login;

import android.content.Intent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.rd.PageIndicatorView;

import org.company.iendo.R;
import org.company.iendo.aop.SingleClick;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.bean.beandb.UserDBBean;
import org.company.iendo.ui.pager.GuidePagerAdapter;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.db.UserDBUtils;

import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/09/21
 * desc   : APP 引导页
 */
public class GuideActivity extends MyActivity
        implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private PageIndicatorView mIndicatorView;
    private View mCompleteView;

    private GuidePagerAdapter mPagerAdapter;
    private Boolean isLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.guide_activity;
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_guide_pager);
        mIndicatorView = findViewById(R.id.pv_guide_indicator);
        mCompleteView = findViewById(R.id.btn_guide_complete);
        setOnClickListener(mCompleteView);
        isLogin = (Boolean) SharePreferenceUtil.get(this, SharePreferenceUtil.is_login, false);
        mIndicatorView.setViewPager(mViewPager);
        setCurrentUserMsg();

    }

    @Override
    protected void initData() {
        mPagerAdapter = new GuidePagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 因为系统默认给的账号密码admin 权限是超级用户:1 普通(默认)是0
     */
    private void setCurrentUserMsg() {
        SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.Current_Username, "Admin");
        SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.Current_Password, "");
        SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.Current_UserType, "0"+"");  //0超级管理员
        SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.UserId, "1");
        //存入数据库
        long ID = 1;
        UserDBBean userDBBean = new UserDBBean();
        userDBBean.setUsername("Admin");
        userDBBean.setPassword("");
        userDBBean.setTag("Admin");
        userDBBean.setUserType("1");
        userDBBean.setId(ID);
        UserDBUtils.insertOrReplaceData(userDBBean);
        boolean isExist = UserDBUtils.queryListIsExist("admin");
        LogUtils.e("DB=====isExist===" + isExist);
        String str = "admin";
        List<UserDBBean> userDBBeanList = UserDBUtils.queryListByMessage(str);
        for (int i = 0; i < userDBBeanList.size(); i++) {
            String username = userDBBeanList.get(i).getUsername();
            String password = userDBBeanList.get(i).getPassword();
            LogUtils.e("DB=====username===" + username + "==password==" + password);
        }
        LogUtils.e("DB=====isExist===" + isExist);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == mCompleteView) {
//            startActivity(HomeActivity.class);
//            finish();
            if (!isLogin) {  //未登入,跳转登入界面
                Intent intent = new Intent();
                intent.setClass(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.is_First_in, false);  //false表示不是第一次登陆
                finish();
            } else {
                SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.is_First_in, false);   //false 不是第一次登入了
                SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.is_login, false);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }

        }
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mViewPager.getCurrentItem() == mPagerAdapter.getCount() - 1 && positionOffsetPixels > 0) {
            mCompleteView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mCompleteView.setVisibility(mViewPager.getCurrentItem() == mPagerAdapter.getCount() - 1 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }
}