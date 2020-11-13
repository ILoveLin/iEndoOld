package org.company.iendo.mineui.fragment;


import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.widget.HintLayout;

/**
 * LoveLin
 * <p>
 * Describe 第一个fragment
 */
public class Fragment04 extends MyFragment<MainActivity> implements StatusAction {

    private HintLayout mHintLayout;

    public static Fragment04 newInstance() {
        return new Fragment04();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_04;
    }

    @Override
    protected void initView() {
        mHintLayout = findViewById(R.id.hl_status_hint);
        showError(this);
    }

    @Override
    protected void initData() {

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
}
