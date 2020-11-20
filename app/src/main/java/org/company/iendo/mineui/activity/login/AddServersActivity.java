package org.company.iendo.mineui.activity.login;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseActivity;
import com.hjq.base.BaseAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.login.adapter.AddServersAdapter;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe添加服务器界面
 */
public class AddServersActivity extends MyActivity implements BaseAdapter.OnItemClickListener, OnRefreshLoadMoreListener, StatusAction {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private AddServersAdapter mAdapter;
    private HintLayout mHintLayout;

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
//        Toolbar mToolbar = findViewById(R.id.tb_home_title);
//        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
//        ImmersionBar.setTitleBar(this, mToolbar);

        mRecyclerView = findViewById(R.id.recycleview);
        mRefreshLayout = findViewById(R.id.smartRefresh);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mAdapter = new AddServersAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setData(analogData());
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
        // 禁用动画效果
        mRecyclerView.setItemAnimator(null);
        // 添加分割线
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), 1, R.drawable.shape_divideritem_decoration));

    }

    @Override
    protected void initData() {

    }

    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = mAdapter.getItemCount(); i < mAdapter.getItemCount() + 10; i++) {
            data.add("我是第" + i + "条目");
        }
        return data;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.clearData();
            mAdapter.setData(analogData());
            mRefreshLayout.finishRefresh();
            toast("刷新完成");
        }, 1000);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.addData(analogData());
            mRefreshLayout.finishLoadMore();
            toast("加载完成");

        }, 1000);

    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast("第===" + position + "个,被点击了~~");
    }


    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
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
