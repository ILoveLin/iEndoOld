package org.company.iendo.mineui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hjq.widget.view.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.bean.CaseManagerListBean;
import org.company.iendo.bean.UserListBean;
import org.company.iendo.bean.beandb.UserDBBean;
import org.company.iendo.bean.event.AddDeleteEvent;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.user.adapter.SearchUserResultAdapter;
import org.company.iendo.ui.dialog.InputDialogOne;
import org.company.iendo.ui.dialog.MessageDialog;
import org.company.iendo.util.BeanToUtils;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.MD5ChangeUtil;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.anim.EasyTransition;
import org.company.iendo.util.db.UserDBUtils;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;
import org.greenrobot.eventbus.EventBus;
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
 * Describe 搜索用户结果界面
 */
public class SearchUserResultActivity extends MyActivity implements StatusAction, BaseAdapter.OnItemClickListener {
    private List<UserListBean.DsDTO> mDataList;
    public List<UserListBean.DsDTO> mList;
    private WrapRecyclerView mRecyclerView;
    private ClearEditText mEditSearch;
    private ClearEditText mCetSearch;
    private LinearLayout linear_all;
    private LinearLayout mTitleBar;
    private HintLayout mHintLayout;
    private SearchUserResultAdapter mAdapter;
    private String endoType;
    private TextView mBack;
    private String tag;
    private List<UserListBean.DsDTO> collectList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_manage_search;
    }

    @Override
    protected void initView() {
        mTitleBar = findViewById(R.id.linear_sheare);
        mCetSearch = findViewById(R.id.cet_user_search);
        mBack = findViewById(R.id.tv_back);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mRecyclerView = findViewById(R.id.rv_status_caselist);
        mEditSearch = findViewById(R.id.cet_user_search);
        int statusBarHeight = getStatusBarHeight();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = statusBarHeight + 23;
        mTitleBar.setLayoutParams(params);
        setOnClickListener(R.id.tv_back, R.id.iv_user_search);
        EasyTransition.enter(SearchUserResultActivity.this);
        mAdapter = new SearchUserResultAdapter(this);
        responseListener();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAnimation(null);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, 1, R.drawable.shape_divideritem_decoration));
        endoType = (String) SharePreferenceUtil.get(SearchUserResultActivity.this, SharePreferenceUtil.Current_Case_Num, "3");
        showSoftInputFromWindow(this, mEditSearch);

    }

    private void responseListener() {
        mAdapter.setOnChildClickListener(R.id.tv_item_delete_user, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                if (getCurrentOnlineType()) {  //在线
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
                if (getCurrentOnlineType()) {  //在线
                    showChangePasswordDialog(mAdapter.getItem(position));

                } else {
                    toast("离线用户不能修改密码");

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

    private void showDeleteDialog(UserListBean.DsDTO item) {
        new MessageDialog.Builder(SearchUserResultActivity.this)
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
        String User_ID = (String) SharePreferenceUtil.get(SearchUserResultActivity.this, SharePreferenceUtil.UserId, "");

        showLoadingBySearch();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        tag = mCetSearch.getText().toString().trim();
        if (getCurrentOnlineType()) {
            sendRequest();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                EasyTransition.exit(SearchUserResultActivity.this);
                break;
            case R.id.iv_user_search:   //本地搜索数据
                tag = mCetSearch.getText().toString().trim();
                if ("".startsWith(tag)) {
                    toast("请输入搜索关键字");
                } else {
                    if (getCurrentOnlineType()) {
                        sendRequest();
                    } else {
//                        ArrayList<UserDBBean> list = (ArrayList<UserDBBean>) UserDBUtils.queryAll(UserDBBean.class);
//                        ArrayList<UserListBean.DsDTO> listUserBean = BeanToUtils.getListUserBean(list);
//                        mAdapter.setData(listUserBean);
                        showLoadingBySearch();
                        collectList = BeanToUtils.getListUserBean((ArrayList<UserDBBean>) UserDBUtils.queryAll(UserDBBean.class)).stream().filter(bean -> bean.getUserName().startsWith(tag)).collect(Collectors.toList());
                        mAdapter.setData(collectList);
                        showComplete();
                    }
                }
                break;
        }
    }

    /**
     * 发送请求
     *
     * @param
     */
    private void sendRequest() {
        tag = mCetSearch.getText().toString().trim();
        showLoadingBySearch();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.User_List)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showEmpty();
                        LogUtils.e("=Search=onSucceed==" + e);

                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("=Search=onSucceed==" + response.toString());
                        showComplete();
                        if ("0".equals(response)) {
                            toast("用户名不存在或者参数为空");
                        } else if ("".equals(tag)) {
                            toast("关键字不能为空");
                        } else {
                            Type type = new TypeToken<UserListBean>() {
                            }.getType();
                            UserListBean mBean = mGson.fromJson(response, type);
                            mList = mBean.getDs();
                            Stream<UserListBean.DsDTO> stream = mList.stream();
                            mDataList = stream.filter(bean -> bean.getUserName().startsWith(tag)).collect(Collectors.toList());
                            if (mDataList.size() == 0) {
                                showEmpty();
                            } else {
                                showComplete();
                                mAdapter.setData(mDataList);
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast("第" + position + "条目被点击了");


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

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}
