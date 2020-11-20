package org.company.iendo.mineui.activity.casemsg;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseFragmentAdapter;
import com.vlc.lib.listener.util.LogUtils;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.fragment.Fragment01;
import org.company.iendo.mineui.fragment.Fragment02;
import org.company.iendo.mineui.fragment.Fragment03;
import org.company.iendo.mineui.fragment.Fragment04;

/**
 * LoveLin
 * <p>
 * Describe 病例信息详情界面
 */
public class CaseDetailMsgActivity extends MyActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentAdapter<MyFragment> mPagerAdapter;
    private RelativeLayout mTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_msg_detail;
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tl_home_tab);
        mTitleBar = findViewById(R.id.rl_top);
        mViewPager = findViewById(R.id.vp_home_pager);
        int statusBarHeight = getStatusBarHeight();
        Log.e("TAG", "statusBarHeight===" + statusBarHeight);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = statusBarHeight+23;
        mTitleBar.setLayoutParams(params);
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(Fragment01.newInstance(), "个人信息");
        mPagerAdapter.addFragment(Fragment02.newInstance(), "病例信息");
        mPagerAdapter.addFragment(Fragment03.newInstance(), "图片");
        mPagerAdapter.addFragment(Fragment04.newInstance(), "视频");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }

    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "fragment01");
    }
}
