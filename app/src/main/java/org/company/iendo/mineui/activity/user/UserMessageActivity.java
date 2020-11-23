package org.company.iendo.mineui.activity.user;

import android.view.View;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.login.LoginActivity;
import org.company.iendo.mineui.activity.user.UserSearchActivity;
import org.company.iendo.util.SharePreferenceUtil;

/**
 * LoveLin
 * <p>
 * Describe 用户管理
 */
public class UserMessageActivity extends MyActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_message;
    }

    @Override
    protected void initView() {
        setOnClickListener(R.id.btn_user_msg_leave_user, R.id.btn_user_msg_change_password,
                R.id.btn_user_control_else_user, R.id.btn_user_exit);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_msg_leave_user:
                break;
            case R.id.btn_user_msg_change_password:
                toast("弹出修改密码POP");
                break;
            case R.id.btn_user_control_else_user:
                startActivity(UserSearchActivity.class);

                break;
            case R.id.btn_user_exit:
                SharePreferenceUtil.put(getActivity(), SharePreferenceUtil.is_login, false);
                startActivity(LoginActivity.class);
                break;
        }
    }


}
