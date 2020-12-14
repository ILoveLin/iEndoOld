package org.company.iendo.mineui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.bean.CaseManagerListBean;
import org.company.iendo.bean.UserListBean;
import org.company.iendo.bean.event.AddDeleteEvent;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.casemsg.CaseManageListActivity;
import org.company.iendo.mineui.activity.casemsg.SearchActivity;
import org.company.iendo.mineui.activity.user.adapter.UserSearchListAdapter;
import org.company.iendo.ui.dialog.InputDialog;
import org.company.iendo.ui.dialog.InputDialogOne;
import org.company.iendo.ui.dialog.MessageDialog;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.MD5ChangeUtil;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.anim.EasyTransition;
import org.company.iendo.util.anim.EasyTransitionOptions;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe 用户管理
 */
public class UserListActivity extends MyActivity implements StatusAction, OnRefreshLoadMoreListener {

    private HintLayout mHintLayout;
    private WrapRecyclerView mRecyclerView;
    private UserSearchListAdapter mAdapter;
    private TitleBar mTitlebar;
    private Boolean currentOnlineType;
    private String endoType;
    private UserListBean mBean;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_search;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_status_userlist);
        mSmartRefreshLayout = findViewById(R.id.rl_status_refresh);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mTitlebar = findViewById(R.id.titlebar);
        mAdapter = new UserSearchListAdapter(this);
        endoType = (String) SharePreferenceUtil.get(UserListActivity.this, SharePreferenceUtil.Current_Case_Num, "3");
        responseListener();
        currentOnlineType = getCurrentOnlineType();
        mRecyclerView.setAdapter(mAdapter);
        // 禁用动画效果
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), 1, R.drawable.shape_divideritem_decoration));
        LinearLayout mHeaderView = mRecyclerView.addHeaderView(R.layout.header_user_search);
        mHeaderView.findViewById(R.id.cet_user_search).setOnClickListener(v -> {
            toast("点击搜索");
            EasyTransitionOptions options = EasyTransitionOptions.makeTransitionOptions(UserListActivity.this, mHeaderView);
            Intent intent = new Intent(UserListActivity.this, SearchUserResultActivity.class);
            EasyTransition.startActivity(intent, options);

        });
        mHeaderView.findViewById(R.id.iv_user_search).setOnClickListener(v -> {
            toast("开始搜索啦~");
            EasyTransitionOptions options = EasyTransitionOptions.makeTransitionOptions(UserListActivity.this, mHeaderView);
            Intent intent = new Intent(UserListActivity.this, SearchUserResultActivity.class);
            EasyTransition.startActivity(intent, options);
        });
    }


    private void responseListener() {
        sendRequest();

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        mSmartRefreshLayout.setEnableLoadMore(false);    //是否启用上拉加载功能
        mAdapter.setOnChildClickListener(R.id.tv_item_delete_user, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                if (currentOnlineType) {  //在线
                    if (getCurrentUserPower().equals("0")) {
                        String des = mAdapter.getItem(position).getDes();
                        if (des.equals("超级管理员")) {
                            toast("超级管理员不能被删除!");
                        } else {
                            showDeleteDialog(mAdapter.getItem(position));
                        }
                    } else {
                        toast("普通用户不能修改密码");
                    }

                } else {
                    toast("离线用户不能修改密码");

                }
            }
        });
        mAdapter.setOnChildClickListener(R.id.tv_item_change_password, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                if (currentOnlineType) {  //在线
                    showChangePasswordDialog(mAdapter.getItem(position));

                } else {
                    toast("离线用户不能修改密码");

                }
            }
        });
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
                // 消息提示对话框
                if (currentOnlineType) {  //在线
                    if (getCurrentUserPower().equals("0")) {
                        showAddUserDialog();
                    } else {
                        toast("普通用户无法添加用户");
                    }

                } else {
                    toast("离线用户无法添加用户");
                }


            }
        });
    }

    private void showDeleteDialog(UserListBean.DsDTO item) {
        new MessageDialog.Builder(UserListActivity.this)
                // 标题可以不用填写
                .setTitle("提示!")
                // 内容必须要填写
                .setMessage("确定删除该用户吗?")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        sendDeleteRequest(item.getUserID(), item);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                    }
                })
                .show();

    }

    //删除用户请求
    private void sendDeleteRequest(String UserID, UserListBean.DsDTO item) {
//        User_ID  当前登入的id
        String User_ID = (String) SharePreferenceUtil.get(UserListActivity.this, SharePreferenceUtil.UserId, "");

        showDialog();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.User_Delete)
                .addParams("UserID", UserID)
                .addParams("User_ID", User_ID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                        toast("请求错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideDialog();
                        LogUtils.e("=修改密码=onResponse==" + response);
//                        返回值 1成功   0传入参数为空 -1用户id不存在
                        if (response.equals("1")) {
                            mAdapter.removeItem(item);
                            mAdapter.notifyDataSetChanged();
                        } else if (response.equals("0")) {
                            toast("传入参数为空");
                        } else if (response.equals("-1")) {
                            toast("用户不存在");
                        }

                    }
                });


    }

    private void showChangePasswordDialog(UserListBean.DsDTO item) {
        new InputDialogOne.Builder(this)
                // 标题可以不用填写
                .setTitle("修改密码")
                // 提示可以不用填写
                .setHint("请输入新密码")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new InputDialogOne.OnListener() {
                    @Override
                    public void onConfirm(BaseDialog dialog, String content) {
                        sendChangePasswordRequest(MD5ChangeUtil.Md5_32(content), item.getUserID(), item.getUserName());
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                    }
                })
                .show();
    }

    private void showAddUserDialog() {
        new InputDialog.Builder(this)
                // 标题可以不用填写
                .setTitle("添加用户")
                // 提示可以不用填写
                .setHint("请输入用户名")
                .setHintTow("请输入密码")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new InputDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog, String str) {
                        LogUtils.e("TAG===String=" + str);
                        String[] split = str.split("&&");
                        String username = split[0];
                        String password = split[1];
                        if (username.equals("oldP")) {
                            username = "";
                        }
                        if (password.equals("newP")) {
                            password = "";
                        }
                        toast("旧密码：" + username + "===新密码：" + password);
                        toast("旧密码：" + username + "===新密码：" + password);
                        String UserId = (String) SharePreferenceUtil.get(UserListActivity.this, SharePreferenceUtil.UserId, "");
                        LogUtils.e("TAG===oldPassword=" + username);
                        LogUtils.e("TAG===newPassword=" + password);
                        if (!username.equals("")) {
                            sendAddUserRequest(MD5ChangeUtil.Md5_32(password), UserId, username);
                        } else {
                            toast("用户名不能为空!");
                        }
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                    }
                })
                .show();
    }

    /**
     * 修改密码的请求
     *
     * @param password
     */
    private void sendChangePasswordRequest(String password, String userID, String UserName) {
        showDialog();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.User_ChangePassword)
                .addParams("UserName", UserName)
                .addParams("Password", password)
                .addParams("userid", userID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                        toast("请求错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideDialog();
//                        返回值 1成功   0传入参数为空 -1用户id不存在
                        if (response.equals("1")) {
                            toast("密码修改成功");
                        } else if (response.equals("0")) {
                            toast("传入参数为空");
                        } else if (response.equals("-1")) {
                            toast("用户不存在");
                        }

                    }
                });

    }

    //添加一个用户
    private void sendAddUserRequest(String password, String userId, String username) {
        showDialog();
        OkHttpUtils.post()
                .url(getCurrentHost() + HttpConstant.User_Add)
                .addParams("UserName", username)
                .addParams("userpassword", password)
                .addParams("UserID", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                        toast("请求错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideDialog();
                        LogUtils.e("=修改11111密码=onResponse==" + response);
//                        返回值 1成功   0传入参数为空 -1用户id不存在
                        if (response.equals("1")) {
                            UserListBean.DsDTO mItemBean = new UserListBean.DsDTO();
                            mItemBean.setUserName(username);
                            mItemBean.setDes("普通用户");
                            mAdapter.addItem(mItemBean);
                            mAdapter.notifyDataSetChanged();
                        } else if (response.equals("0")) {
                            toast("请求失败");
                        }

                    }
                });
    }

    private void sendRequest() {
        LogUtils.e("=TAG=hy=onError==" + endoType);
        showLoading();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.User_List)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showComplete();

                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response, int id) {
                        showComplete();
                        LogUtils.e("=TAG=hy=onSucceed==" + response.toString());
                        Type type = new TypeToken<UserListBean>() {
                        }.getType();
                        mBean = mGson.fromJson(response, type);

                        List<UserListBean.DsDTO> mDataListBean = mBean.getDs();
                        mAdapter.setData(mDataListBean);
                        mSmartRefreshLayout.finishRefresh();


                    }
                });


    }

    @Override
    protected void initData() {

    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mSmartRefreshLayout.finishLoadMoreWithNoMoreData();

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        sendRequest();
    }
}
