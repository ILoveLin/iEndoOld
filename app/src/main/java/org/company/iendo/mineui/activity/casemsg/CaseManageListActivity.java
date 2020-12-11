package org.company.iendo.mineui.activity.casemsg;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
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
import org.company.iendo.bean.event.AddDeleteEvent;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.casemsg.adapter.CaseManageAdapter;
import org.company.iendo.mineui.activity.user.SearchUserResultActivity;
import org.company.iendo.ui.dialog.MessageDialog;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.anim.EasyTransition;
import org.company.iendo.util.anim.EasyTransitionOptions;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe 病例管理--列表
 */
public class CaseManageListActivity extends MyActivity implements StatusAction, BaseAdapter.OnItemClickListener, OnRefreshLoadMoreListener {
    public static List<CaseManagerListBean.DsDTO> mList;
    private SmartRefreshLayout mSmartRefreshLayout;
    private WrapRecyclerView mRecyclerView;
    private CaseManageAdapter mAdapter;
    private HintLayout mHintLayout;
    private TitleBar mTitleBar;
    private String endoType;
    private MessageDialog.Builder builder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_manage;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mSmartRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_caselist);
        mTitleBar = findViewById(R.id.titlebar);
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        mSmartRefreshLayout.setEnableLoadMore(false);    //是否启用上拉加载功能
        mAdapter = new CaseManageAdapter(this);
        responseListener();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAnimation(null);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, 1, R.drawable.shape_divideritem_decoration));
        LinearLayout mHeaderView = mRecyclerView.addHeaderView(R.layout.header_user_search);
        mHeaderView.findViewById(R.id.cet_user_search).setOnClickListener(this);
        mHeaderView.findViewById(R.id.iv_user_search).setOnClickListener(this);
        mHeaderView.findViewById(R.id.cet_user_search).setOnClickListener(v -> {
            toast("点击搜索");
            EasyTransitionOptions options = EasyTransitionOptions.makeTransitionOptions(CaseManageListActivity.this, mHeaderView);
            Intent intent = new Intent(CaseManageListActivity.this, SearchUserResultActivity.class);
            EasyTransition.startActivity(intent, options);


        });
        mHeaderView.findViewById(R.id.iv_user_search).setOnClickListener(v -> {
            toast("点击搜索");
            EasyTransitionOptions options = EasyTransitionOptions.makeTransitionOptions(CaseManageListActivity.this, mHeaderView);
            Intent intent = new Intent(CaseManageListActivity.this, SearchUserResultActivity.class);
            EasyTransition.startActivity(intent, options);
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddDeleteEvent(AddDeleteEvent event) {
        if (mAdapter != null) {
            LogUtils.e("=TAG=onAddDeleteEvent==" + event.getType());
            CaseManagerListBean.DsDTO bean = event.getBean();
            if (event.getType().equals("delete")) {
                mAdapter.removeItem(event.getPosition());
            } else if (event.getType().equals("edit")) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.addItem(0, event.getBean());

            }
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (builder != null) {
            builder.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        endoType = (String) SharePreferenceUtil.get(CaseManageListActivity.this, SharePreferenceUtil.Current_Case_Num, "3");
        sendRequest();

    }

    /**
     * 发送请求
     *
     * @param
     */
    private void sendRequest() {
        showLoading();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_List)
                .addParams("endoType", endoType)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showComplete();
                        LogUtils.e("=TAG=hy=onError==" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        showComplete();
                        if ("0".equals(response)) {
                            toast("用户名不存在或者参数为空");
                        } else {
                            Type type = new TypeToken<CaseManagerListBean>() {
                            }.getType();
                            CaseManagerListBean mBean = mGson.fromJson(response, type);
                            mList = mBean.getDs();
                            mAdapter.setData(mList);
                            mSmartRefreshLayout.finishRefresh();
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cet_user_search:
                toast("点击搜索");
                break;
            case R.id.iv_user_search:
                toast("开始搜索啦~");
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast("第" + position + "条目被点击了");
        LogUtils.e("=TAG=hy=position==" + mAdapter.getItem(position).toString());
        Intent intent = new Intent(CaseManageListActivity.this, CaseDetailMsgActivity.class);
        intent.putExtra("ID", mAdapter.getItem(position).getID());
        intent.putExtra("bean", mAdapter.getItem(position));
        intent.putExtra("position", position + "");
        startActivity(intent);
    }

    private void responseListener() {
        mAdapter.setOnItemClickListener(this);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                if (getCurrentOnlineType()) {
                    toast("添加");
                    Intent intent = new Intent(CaseManageListActivity.this, AddEditActivity.class);
                    startActivity(intent);
                } else {
                    if ("0".equals(getCurrentUserPower())) {
                        builder = new MessageDialog.Builder(CaseManageListActivity.this);
                        builder.setTitle("提示")// 标题可以不用填写
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
                    } else {
                        toast("普通用户暂无该权限");
                    }

                }
            }
        });


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
