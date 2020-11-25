package org.company.iendo.mineui.fragment;


import android.util.Log;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.widget.HintLayout;

/**
 * LoveLin
 * <p>
 * Describe 第一个fragment
 */


public class Fragment03 extends MyFragment<MainActivity> implements StatusAction, CaseOperatorAction {

    private HintLayout mHintLayout;

    private CaseDetailMsgActivity mActivity;
    public Fragment03(CaseDetailMsgActivity Activity){
        this.mActivity= Activity;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_03;
    }

    @Override
    protected void initView() {
        mHintLayout = findViewById(R.id.hl_status_hint);
        mActivity.setCaseOperatorAction(this);





    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
    public void onResume() {
        super.onResume();
        Log.e("TAG","fragment03");
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onLive() {

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
