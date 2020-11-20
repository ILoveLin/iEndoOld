package org.company.iendo.mineui.activity.user;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.user.adapter.UserSearchListAdapter;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 病例管理
 */
public class UserSearchActivity extends MyActivity implements StatusAction {

    private HintLayout mHintLayout;
    private WrapRecyclerView mRecyclerView;
    private UserSearchListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_search;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_status_userlist);
        mHintLayout = findViewById(R.id.hl_status_hint);

        mAdapter = new UserSearchListAdapter(this);
        mAdapter.setData(analogData());

        mAdapter.setOnChildClickListener(R.id.user_search_change_password, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                toast("修改====第：" + position + "的密码?");
            }
        });
        mAdapter.setOnChildClickListener(R.id.user_search_delBtn, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                toast("马上,删除第:" + position + "条item数据");
            }
        });

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
