package org.company.iendo.mineui.activity.user;

import android.view.View;
import android.widget.TextView;
import com.hjq.base.BaseDialog;
import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.login.LoginActivity;
import org.company.iendo.ui.dialog.MessageDialog;
import org.company.iendo.util.SharePreferenceUtil;

/**
 * LoveLin
 * <p>
 * Describe 用户管理
 */
public class UserMessageActivity extends MyActivity {

    private TextView mUsername;
    private TextView mDescribe;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_message;
    }

    @Override
    protected void initView() {
        mUsername = findViewById(R.id.user_username);
        mDescribe = findViewById(R.id.user_describe);
        setOnClickListener(R.id.btn_user_msg_leave_user, R.id.btn_user_msg_change_password,
                R.id.btn_user_control_else_user, R.id.btn_user_exit);
    }

    @Override
    protected void initData() {
        String currentName = (String) SharePreferenceUtil.get(UserMessageActivity.this, SharePreferenceUtil.Current_Username, "");
        String currentUserType = (String) SharePreferenceUtil.get(UserMessageActivity.this, SharePreferenceUtil.Current_UserType, "");
        mUsername.setText(currentName + "");
        if ("1".equals(currentUserType)) {
            mDescribe.setText("超级管理员");
        } else {
            mDescribe.setText("普通用户");
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_msg_leave_user:  //离线用户
                break;
            case R.id.btn_user_msg_change_password:
                changePassword();
                break;
            case R.id.btn_user_control_else_user:
                startActivity(UserSearchActivity.class);
                break;
            case R.id.btn_user_exit:
                exit();
                break;
        }
    }

    private void exit() {
        new MessageDialog.Builder(UserMessageActivity.this)
                // 标题可以不用填写
                .setTitle("提示")
                // 内容必须要填写
                .setMessage("确定注销登录吗？")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        SharePreferenceUtil.put(getActivity(), SharePreferenceUtil.is_login, false);
                        startActivity(LoginActivity.class);

                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                    }
                })
                .show();

    }

    private void changePassword() {
        if (getCurrentOnlineType()) {//在线用户才可以修改

        } else {
            new MessageDialog.Builder(UserMessageActivity.this)
                    // 标题可以不用填写
                    .setTitle("提示")
                    // 内容必须要填写
                    .setMessage("离线用户无法修改密码")
                    // 确定按钮文本
                    .setConfirm(getString(R.string.common_confirm))
                    // 设置 null 表示不显示取消按钮
                    .setCancel(getString(R.string.common_cancel))
                    // 设置点击按钮后不关闭对话框
                    //.setAutoDismiss(false)
                    .setListener(new MessageDialog.OnListener() {

                        @Override
                        public void onConfirm(BaseDialog dialog) {
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                        }
                    })
                    .show();
        }


    }


}
