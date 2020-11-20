package org.company.iendo.mineui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
public class Fragment02 extends MyFragment<MainActivity> implements StatusAction {


    private HintLayout mHintLayout;

    public static Fragment02 newInstance() {
        return new Fragment02();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_02;
    }

    @Override
    protected void initView() {
        TextView tv_02 = findViewById(R.id.tv_02);
        mHintLayout = findViewById(R.id.hl_status_hint);

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
        Log.e("TAG","fragment02");
    }
}
