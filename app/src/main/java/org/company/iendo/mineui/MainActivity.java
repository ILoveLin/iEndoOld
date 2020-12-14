package org.company.iendo.mineui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import com.hjq.base.BaseDialog;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.helper.ActivityStackManager;
import org.company.iendo.helper.DoubleClickHelper;
import org.company.iendo.mineui.activity.live.LiveActivity;
import org.company.iendo.mineui.activity.live.LiveConnectDeviceActivity;
import org.company.iendo.mineui.activity.live.SMBPlayerActivity;
import org.company.iendo.mineui.activity.user.UserMessageActivity;
import org.company.iendo.mineui.activity.casemsg.CaseManageListActivity;
import org.company.iendo.other.KeyboardWatcher;
import org.company.iendo.ui.dialog.MessageDialog;

/**
 * LoveLin
 * <p>
 * Describe主页
 */
public class MainActivity extends MyActivity implements KeyboardWatcher.SoftKeyboardStateListener {

    private MessageDialog.Builder builder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_only;
    }


    @Override
    protected void initView() {
        KeyboardWatcher.with(this)
                .setListener(this);
    }

    @Override
    protected void initData() {
        setOnClickListener(R.id.cv_user, R.id.cv_case_manage, R.id.cv_live);
        //验证是否许可权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_user:          //用户管理
                startActivity(UserMessageActivity.class);
                break;
            case R.id.cv_case_manage:   //病例管理
                startActivity(CaseManageListActivity.class);
                break;
            case R.id.cv_live:          //直播

                if (getCurrentOnlineType()) {
                    Intent intent = new Intent(getActivity(), LiveActivity.class);
                    startActivity(intent);
                    showNetLiveDialog();
                } else {
                    showNetLiveDialog();
                }
                break;
        }
    }

    private void showNetLiveDialog() {
        builder = new MessageDialog.Builder(MainActivity.this);
        builder.setTitle("提示")
                .setMessage("离线用户不能查看直播")
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                .setListener(new MessageDialog.OnListener() {
                    @Override
                    public void onConfirm(BaseDialog dialog) {

                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {

                    }
                }).show();

    }


    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            // 移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            postDelayed(() -> {

                // 进行内存优化，销毁掉所有的界面
                ActivityStackManager.getInstance().finishAllActivities();
                // 销毁进程（注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
                // System.exit(0);

            }, 300);
        } else {
            toast(R.string.home_exit_hint);
        }
    }


    /**
     * 软键盘的监听方法
     *
     * @param keyboardHeight 软键盘高度
     */
    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
    }

    @Override
    public void onSoftKeyboardClosed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (builder != null) {
            builder.dismiss();
        }
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

}



