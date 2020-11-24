package org.company.iendo.mineui.activity.casemsg;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.casemsg.adapter.CaseManageAdapter;
import org.company.iendo.mineui.activity.user.UserSearchActivity;
import org.company.iendo.ui.dialog.MessageDialog;
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
    private TitleBar mTitleBar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_manage;
    }

    @Override
    protected void initView() {
        mHintLayout = findViewById(R.id.hl_status_hint);
        mSmartRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_caselist);
        mTitleBar = findViewById(R.id.titlebar);

        mAdapter = new CaseManageAdapter(this);
        mAdapter.setData(analogData());
        responseListener();

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
                if(getCurrentOnlineType()){

                }else{
                    new MessageDialog.Builder(CaseManageActivity.this)
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
