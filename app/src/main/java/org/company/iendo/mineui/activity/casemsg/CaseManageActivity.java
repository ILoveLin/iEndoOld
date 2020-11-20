package org.company.iendo.mineui.activity.casemsg;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.casemsg.adapter.CaseManageAdapter;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 病例管理
 */
public class CaseManageActivity extends MyActivity implements StatusAction, BaseAdapter.OnItemClickListener {

    private SmartRefreshLayout mSmartRefreshLayout;
    private WrapRecyclerView mRecyclerView;
    private HintLayout mHintLayout;
    private CaseManageAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_manage;
    }

    @Override
    protected void initView() {
        mHintLayout = findViewById(R.id.hl_status_hint);
        mSmartRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_caselist);

        mAdapter = new CaseManageAdapter(this);
        mAdapter.setData(analogData());
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAnimation(null);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, 1, R.drawable.shape_divideritem_decoration));

        LinearLayout mHeaderView = mRecyclerView.addHeaderView(R.layout.header_user_search);
        mHeaderView.findViewById(R.id.cet_user_search).setOnClickListener(this);
        mHeaderView.findViewById(R.id.iv_user_search).setOnClickListener(this);


//        mHeaderView.findViewById(R.id.cet_user_search).setOnClickListener(v -> {
//            toast("点击搜索");
//
//        });
//        mHeaderView.findViewById(R.id.iv_user_search).setOnClickListener(v -> {
//            toast("开始搜索啦~");
//
//        });

    }

    @Override
    protected void initData() {

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
        startActivity(CaseDetailMsgActivity.class);
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
