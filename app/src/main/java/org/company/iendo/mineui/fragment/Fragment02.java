package org.company.iendo.mineui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
public class Fragment02 extends MyFragment<MainActivity> implements StatusAction, CaseOperatorAction {


    private HintLayout mHintLayout;
    private CaseDetailMsgActivity mActivity;

    public Fragment02(CaseDetailMsgActivity Activity) {
        this.mActivity = Activity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_02;
    }

    @Override
    protected void initView() {
        TextView tv_02 = findViewById(R.id.tv_02);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mActivity.setCaseOperatorAction(this);
        tv_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_02.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "fragment02");
    }

    @Override
    public void onLive() {
        Log.e("TAG", "fragment02=====onLive");

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
