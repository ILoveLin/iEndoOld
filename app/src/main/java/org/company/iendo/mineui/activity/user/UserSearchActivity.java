package org.company.iendo.mineui.activity.user;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.widget.layout.WrapRecyclerView;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.user.adapter.UserSearchListAdapter;
import org.company.iendo.ui.dialog.MessageDialog;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 用户管理
 */
public class UserSearchActivity extends MyActivity implements StatusAction {

    private HintLayout mHintLayout;
    private WrapRecyclerView mRecyclerView;
    private UserSearchListAdapter mAdapter;
    private TitleBar mTitlebar;
    private Boolean currentOnlineType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_search;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_status_userlist);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mTitlebar = findViewById(R.id.titlebar);

        mAdapter = new UserSearchListAdapter(this);
        mAdapter.setData(analogData());

        responseListener();
        currentOnlineType = getCurrentOnlineType();
        mRecyclerView.setAdapter(mAdapter);
        // 禁用动画效果
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), 1, R.drawable.shape_divideritem_decoration));
        LinearLayout headerView = mRecyclerView.addHeaderView(R.layout.header_user_search);
        headerView.findViewById(R.id.cet_user_search).setOnClickListener(v -> {
            toast("点击搜索");

        });
        headerView.findViewById(R.id.iv_user_search).setOnClickListener(v -> {
            toast("开始搜索啦~");

        });


    }

    private void responseListener() {

        mAdapter.setOnChildClickListener(R.id.user_search_change_password, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                if (currentOnlineType) {  //在线
                    toast("修改====第：" + position + "的密码?");

                } else {
                    toast("离线用户不能修改密码");

                }
            }
        });
        mAdapter.setOnChildClickListener(R.id.user_search_delBtn, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                //todo 超级管理员不能删除自己  这里需要做权限的判断
                if (currentOnlineType) {  //在线
                    toast("马上,删除第:" + position + "条item数据");


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

                } else {
                    new MessageDialog.Builder(UserSearchActivity.this)
                            // 标题可以不用填写
                            .setTitle("提示")
                            // 内容必须要填写
                            .setMessage("离线用户无法添加用户")
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
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = mAdapter.getItemCount(); i < mAdapter.getItemCount() + 20; i++) {
            data.add("我是第" + i + "条目");
        }
        return data;
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }
}
