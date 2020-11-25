package org.company.iendo.mineui.fragment;


import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.mineui.activity.live.SMBPlayerActivity;
import org.company.iendo.mineui.fragment.adapter.VideoAdapter;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 第四个fragment
 */
public class Fragment04 extends MyFragment<MainActivity> implements StatusAction, CaseOperatorAction, BaseAdapter.OnItemClickListener {

    private HintLayout mHintLayout;
    private CaseDetailMsgActivity mActivity;
    private WrapRecyclerView mRecyclerView;
    private VideoAdapter mAdapter;

    public Fragment04(CaseDetailMsgActivity Activity) {
        this.mActivity = Activity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_04;
    }

    @Override
    protected void initView() {
        mHintLayout = findViewById(R.id.hl_status_hint);
        mRecyclerView = findViewById(R.id.recycleview_case04);
        mAdapter = new VideoAdapter(getAttachActivity());
        mAdapter.setOnItemClickListener(this);
        mActivity.setCaseOperatorAction(this);
        mAdapter.setData(analogData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getAttachActivity(), 1, R.drawable.shape_divideritem_decoration));
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
            data.add("" + i + ".mp4");
        }
        return data;
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    public void onResume() {
        super.onResume();
        Log.e("TAG", "fragment04");
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast(position);
        String item = mAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), SMBPlayerActivity.class);
        intent.putExtra("url", item + "");
        startActivity(intent);
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onLive() {

    }

    @Override
    public void onPrint() {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDownload() {

    }

    @Override
    public void onEdit() {

    }


}
