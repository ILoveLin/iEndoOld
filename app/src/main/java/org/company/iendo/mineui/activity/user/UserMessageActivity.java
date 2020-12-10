package org.company.iendo.mineui.activity.user;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hjq.base.BaseDialog;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.login.LoginActivity;
import org.company.iendo.ui.dialog.DayDialog;
import org.company.iendo.ui.dialog.MessageDialog;
import org.company.iendo.util.SharePreferenceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * LoveLin
 * <p>
 * Describe 用户信息
 */
public class UserMessageActivity extends MyActivity {

    private TextView mUsername;
    private TextView mDescribe;
    private TextView mCreateTime;
    private TextView mLastOnlineTime;
    private TextView mLoginOnlineTimes;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_message;
    }

    @Override
    protected void initView() {
        mUsername = findViewById(R.id.user_username);
        mDescribe = findViewById(R.id.user_describe);
        mCreateTime = findViewById(R.id.user_create_time);
        mLastOnlineTime = findViewById(R.id.user_lasttime_online);
        mLoginOnlineTimes = findViewById(R.id.user_online_times);
        setOnClickListener(R.id.btn_user_msg_leave_user, R.id.btn_user_msg_change_password,
                R.id.btn_user_control_else_user, R.id.btn_user_exit, R.id.copy, R.id.text);
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

        //根据不同的在线模式显示 创建时间,登录次数,上次登录时间
        if (getCurrentOnlineType()) {   //在线登录
            String loginTimes = (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Current_LoginOnlineTime, "");
            String createTime = (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Current_CreateTime, "");
            String lastTime = (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Current_LastOnlineTime, "");
            mCreateTime.setText("" + createTime);
            mLastOnlineTime.setText("" + lastTime);
            mLoginOnlineTimes.setText("" + loginTimes);
        } else {                        //离线模式
            mCreateTime.setText("");
            mLastOnlineTime.setText("");
            mLoginOnlineTimes.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy:  //离线用户
                try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        int REQUEST_CODE_CONTACT = 101;
                        String[] permissions = {
                                Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //验证是否许可权限
                        for (String str : permissions) {
                            if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                                //申请权限
                                requestPermissions(permissions, REQUEST_CODE_CONTACT);
                                return;
                            } else {
                                //这里就是权限打开之后自己要操作的逻辑
                                writPic();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_user_msg_leave_user:  //离线用户
                break;
            case R.id.btn_user_msg_change_password:
                changePassword();
                break;
            case R.id.btn_user_control_else_user:
                startActivity(UserListActivity.class);
                break;
            case R.id.btn_user_exit:   //退出
                exit();
                break;
            case R.id.text:
                // 日期选择对话框
                new DayDialog.Builder(this)
                        .setTitle(getString(R.string.age_title))
                        // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel))
                        // 设置日期
                        //.setDate("2018-12-31")
                        //.setDate("20181231")
                        //.setDate(1546263036137)
                        // 设置年份
                        .setYear(20)
                        // 设置月份
                        .setMonth(6)
                        // 设置天数
                        .setDay(18)
                        // 不选择天数
                        //.setIgnoreDay()
                        .setListener(new DayDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int year, int month, int day) {
                                toast(year + "岁" + month + getString(R.string.common_month) + day + getString(R.string.common_day));

//                                // 如果不指定时分秒则默认为现在的时间
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.set(Calendar.YEAR, year);
//                                // 月份从零开始，所以需要减 1
//                                calendar.set(Calendar.MONTH, month - 1);
//                                calendar.set(Calendar.DAY_OF_MONTH, day);
//                                toast("时间戳：" + calendar.getTimeInMillis());
                                //toast(new SimpleDateFormat("yyyy年MM月dd日 kk:mm:ss").format(calendar.getTime()));
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                toast("取消了");
                            }
                        })
                        .show();
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
                        SharePreferenceUtil.put(getActivity(), SharePreferenceUtil.UserId, "");
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

    /**
     * 本地App文件的拷贝,比如SD卡Image的图片拷贝到SD卡MyImage目录中去
     *
     * @throws IOException
     */
    private void writPic() throws IOException {
        try {
            Log.e("========rootfile=====", "Environment==out=" + Environment.getExternalStorageDirectory());
            File toFile = new File(Environment.getExternalStorageDirectory() + "/Image/4027" + File.separator);
            if (!toFile.exists()) {
                toFile.mkdirs();
            }

            File localFile = new File(Environment.getExternalStorageDirectory() + "/Pictures" + File.separator);
            Log.e("========rootfile=====", "Environment==localFile=" + localFile.getAbsolutePath());
            copy(localFile.getAbsolutePath(), toFile.getAbsolutePath());
        } catch (Exception e) {
            Log.e("========root=====", "Exception===");

            e.printStackTrace();
        }
    }

    public int copy(String fromFile, String toFile) {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if (!root.exists()) {
            return -1;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();

        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                Log.e("========root=====", "目录==from===" + currentFiles[i].getPath() + "/");
                Log.e("========root=====", "目录==toFile=" + toFile + currentFiles[i].getName() + "/");

                copy(currentFiles[i].getPath() + "/", toFile + "/" + currentFiles[i].getName());
            } else//如果当前项为文件则进行文件拷贝
            {
                CopySdcardFile(currentFiles[i].getPath(), toFile + "/" + currentFiles[i].getName());
            }
        }
        return 0;
    }

    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public int CopySdcardFile(String fromFile, String toFile) {
        Log.e("========root=====", "文件拷贝==fromFile===" + fromFile);
        Log.e("========root=====", "文件拷贝==toFile=====" + toFile);
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            return -1;
        }
    }

}
