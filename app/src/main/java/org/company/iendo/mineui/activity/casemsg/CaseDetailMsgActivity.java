package org.company.iendo.mineui.activity.casemsg;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseDialog;
import com.hjq.base.BaseFragmentAdapter;
import com.vlc.lib.listener.util.LogUtils;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.mineui.activity.user.UserSearchActivity;
import org.company.iendo.mineui.fragment.Fragment01;
import org.company.iendo.mineui.fragment.Fragment02;
import org.company.iendo.mineui.fragment.Fragment03;
import org.company.iendo.mineui.fragment.Fragment04;
import org.company.iendo.ui.dialog.MessageDialog;

/**
 * LoveLin
 * <p>
 * Describe 病例信息详情界面
 */
public class CaseDetailMsgActivity extends MyActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentAdapter<MyFragment> mPagerAdapter;
    private LinearLayout mTitleBar;
    private ImageButton mLeft;
    private TextView mLive;
    private TextView mPrint;
    private TextView mDelete;
    private TextView mDownload;
    private TextView mEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_msg_detail;
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tl_home_tab);
        mTitleBar = findViewById(R.id.rl_top);
        mLeft = findViewById(R.id.ib_left);
        mLive = findViewById(R.id.titile_live);
        mPrint = findViewById(R.id.titile_print);
        mDelete = findViewById(R.id.titile_delete);
        mDownload = findViewById(R.id.titile_download);
        mEdit = findViewById(R.id.titile_edit);
        mViewPager = findViewById(R.id.vp_home_pager);
        int statusBarHeight = getStatusBarHeight();
        Log.e("TAG", "statusBarHeight===" + statusBarHeight);
        setOnClickListener(R.id.titile_live, R.id.titile_print, R.id.titile_delete, R.id.titile_download, R.id.titile_edit);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = statusBarHeight + 23;
        mTitleBar.setLayoutParams(params);
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        Fragment01 fragment01 = new Fragment01(this);
        Fragment02 fragment02 = new Fragment02(this);
        Fragment03 fragment03 = new Fragment03(this);
        Fragment04 fragment04 = new Fragment04(this);

        mPagerAdapter.addFragment(fragment01, "个人信息");
        mPagerAdapter.addFragment(fragment02, "病例信息");
        mPagerAdapter.addFragment(fragment03, "图片");
        mPagerAdapter.addFragment(fragment04, "视频");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titile_live:
                if(mAction!=null){
                    mAction.onLive();
                }
                break;
            case R.id.titile_print:
                break;
            case R.id.titile_delete:
                showDeleteDialog();
                break;
            case R.id.titile_download:
                break;
            case R.id.titile_edit:
                startActivity(EditActivity.class);
                break;

        }
    }

    private void showDeleteDialog() {



        new MessageDialog.Builder(CaseDetailMsgActivity.this)
                // 标题可以不用填写
                .setTitle("提示")
                // 内容必须要填写
                .setMessage("确定删除吗？")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        toast("确定");
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消");

                    }
                })
                .show();

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
        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "fragment01");
    }

    private CaseOperatorAction mAction;

    public void setCaseOperatorAction(CaseOperatorAction action) {
        this.mAction = action;
    }

}
