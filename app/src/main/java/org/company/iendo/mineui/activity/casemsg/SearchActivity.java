package org.company.iendo.mineui.activity.casemsg;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.util.anim.EasyTransition;

/**
 * LoveLin
 * <p>
 * Describe搜索界面
 */
public class SearchActivity extends MyActivity {
    private LinearLayout mTitleBar;
    private LinearLayout linear_all;
    private TextView mBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_manage_search;
    }

    @Override
    protected void initView() {
         mTitleBar = findViewById(R.id.linear_sheare);
//        linear_all = findViewById(R.id.linear_sheare);
        mBack = findViewById(R.id.tv_back);
        int statusBarHeight = getStatusBarHeight();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = statusBarHeight + 23;
        mTitleBar.setLayoutParams(params);

        setOnClickListener(R.id.tv_back);
        long time = 1000;
        EasyTransition.enter(SearchActivity.this);


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
//                finish();
                EasyTransition.exit(SearchActivity.this);

                break;
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
