package org.company.iendo.mineui.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.ui.adapter.StatusAdapter;
import org.company.iendo.widget.HintLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 第一个fragment
 */
public class Fragment01 extends MyFragment<MainActivity> implements BaseAdapter.OnItemClickListener,
        OnRefreshLoadMoreListener, StatusAction {
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;
    private HintLayout mHintLayout;
    private StatusAdapter mAdapter;

    public static Fragment01 newInstance() {
        return new Fragment01();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_01;
    }

    @Override
    protected void initView() {
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);
        mHintLayout = findViewById(R.id.hl_status_hint);

        mAdapter = new StatusAdapter(getAttachActivity());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        TextView headerView = mRecyclerView.addHeaderView(R.layout.picker_item);
        headerView.setText("我是头部");
        headerView.setOnClickListener(v -> toast("点击了头部"));

        TextView footerView = mRecyclerView.addFooterView(R.layout.picker_item);
        footerView.setText("我是尾部");
        footerView.setOnClickListener(v -> toast("点击了尾部"));

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

//        StatusManager mStatusManager = new StatusManager();
//        mStatusManager.showLoading(getActivity());

    }

    @Override
    protected void initData() {
//        new MenuDialog.Builder(getActivity())
//                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
//                .setList("加载中", "请求错误", "空数据提示", "自定义提示")
//                .setListener((dialog, position, object) -> {
//                    switch (position) {
//                        case 0:
//                            showLoading();
//                            postDelayed(this::showComplete, 2500);
//                            break;
//                        case 1:
//                            showError(v -> {
//                                showLoading();
//                                postDelayed(this::showEmpty, 2500);
//                            });
//                            break;
//                        case 2:
//                            showEmpty();
//                            break;
//                        case 3:
//                            showLayout(ContextCompat.getDrawable(getActivity(), R.drawable.hint_order_ic), "暂无订单", null);
//                            break;
//                        default:
//                            break;
//                    }
//                })
//                .show();
        mAdapter.setData(analogData());
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
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }


    /**
     * {@link BaseAdapter.OnItemClickListener}
     *
     * @param recyclerView RecyclerView对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast(mAdapter.getItem(position));
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */

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
    public HintLayout getHintLayout() {
        return mHintLayout;
    }
}
