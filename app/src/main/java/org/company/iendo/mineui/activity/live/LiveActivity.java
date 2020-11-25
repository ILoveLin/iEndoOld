package org.company.iendo.mineui.activity.live;

import android.view.View;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;

/**
 * LoveLin
 * <p>
 * Describe 直播界面
 */
public class LiveActivity extends MyActivity {

    private TitleBar mTitlebar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    protected void initView() {
        mTitlebar = findViewById(R.id.titlebar);
        responseListener();
    }

    private void responseListener() {
        mTitlebar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
