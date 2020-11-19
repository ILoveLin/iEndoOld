package org.company.iendo.mineui.activity;

import android.content.Intent;

import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseActivity;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.ui.activity.ImageSelectActivity;

/**
 * LoveLin
 * <p>
 * Describe添加服务器界面
 */
public class AddServersActivity extends MyActivity {
    public static void start(BaseActivity activity, OnServersSelectedListener mListener) {
        //这里做服务器被选中的监听,数据会写到LoginActivity中去
        Intent intent = new Intent(activity, AddServersActivity.class);
        activity.startActivityForResult(intent, (OnActivityCallback) mListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_servers;
    }

    @Override
    protected void initView() {
        Toolbar mToolbar = findViewById(R.id.tb_home_title);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(this, mToolbar);
    }

    @Override
    protected void initData() {

    }


    /**
     * 服务器选择的监听
     */

    private OnServersSelectedListener mListener;

    public interface OnServersSelectedListener {
        void onServersSelected(String deviceType, String ip, String port);
    }

    public void setOnServersSelectedListener(OnServersSelectedListener mListener) {
        this.mListener = mListener;
    }
}
