package org.company.iendo.mineui.activity.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseActivity;
import com.hjq.base.BaseAdapter;
import com.hjq.widget.view.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.login.adapter.AddServersAdapter;
import org.company.iendo.mineui.beandb.ServersDBBean;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.db.ServersDBUtils;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe添加服务器界面
 */
public class AddServersActivity extends MyActivity implements StatusAction {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private AddServersAdapter mAdapter;
    private HintLayout mHintLayout;
    private AppCompatButton mSubmitServers;
    private ClearEditText mDeviceType;
    private ClearEditText mIP;
    private ClearEditText mPort;
    private String deviceType;
    private String ip;
    private String port;

    private static final int Handler_OkCode = 1;
    private static final int Handler_Update = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:              //添加了服务器,删除item,重新加载列表
                    initData();
                    break;
                case 2:
                    mDeviceType.setText(currentItemBean.getName());
                    mIP.setText(currentItemBean.getIp());
                    mPort.setText(currentItemBean.getPort());
                    break;

            }
        }
    };
    private ServersDBBean currentItemBean;
    private TitleBar mTitleBar;


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
        mTitleBar = findViewById(R.id.titlebar);
        mRecyclerView = findViewById(R.id.recycleview);
        mRefreshLayout = findViewById(R.id.smartRefresh);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mDeviceType = findViewById(R.id.et_device_type);
        mIP = findViewById(R.id.et_ip);
        mPort = findViewById(R.id.et_port);
        mSubmitServers = findViewById(R.id.submit_add_servers);
        mAdapter = new AddServersAdapter(this);

//        mAdapter.setData(analogData());
//        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        responseListener();
        mRecyclerView.setAdapter(mAdapter);
        // 禁用动画效果
        mRecyclerView.setItemAnimator(null);
        // 添加分割线
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), 1, R.drawable.shape_divideritem_decoration));

    }

    @Override
    protected void initData() {
        List list = ServersDBUtils.queryAll(ServersDBBean.class);
        if (list != null) {
            showComplete();
            mAdapter.setData(list);
        } else {
            showEmpty();
        }


    }

    private void responseListener() {

        setOnClickListener(R.id.submit_add_servers);

        mAdapter.setOnChildClickListener(R.id.add_servers_delBtn, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                toast("马上,删除第:" + position + "条item数据");
                ServersDBBean itemBean = mAdapter.getItem(position);
                ServersDBUtils.deleteData(itemBean);
                mHandler.sendEmptyMessage(Handler_OkCode);
            }
        });
        mAdapter.setOnChildClickListener(R.id.linear_item_servers, new BaseAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View childView, int position) {
                toast("第:" + position + "条item数据");
                currentItemBean = mAdapter.getItem(position);
                mHandler.sendEmptyMessage(Handler_Update);


            }
        });

        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                //选择当前的服务器,并且存入缓存sp
                checkAddServersDataAndInsertData("config");

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_add_servers:  //提交添加的服务器
                checkAddServersDataAndInsertData("insert");

                break;
        }
    }


    private void checkAddServersDataAndInsertData(String type) {
        deviceType = mDeviceType.getText().toString().trim();
        ip = mIP.getText().toString().trim();
        port = mPort.getText().toString().trim();
        if ("".equals(deviceType)) {
            toast("名称不能为空");
            return;
        } else if ("".equals(ip)) {
            toast("ip不能为空");
            return;
        } else if ("".equals(port)) {
            toast("端口号不能为空");
            return;
        } else if (type.equals("insert")) {
            ServersDBBean serversBeanDB = new ServersDBBean();
            serversBeanDB.setName(deviceType);
            serversBeanDB.setIp(ip);
            serversBeanDB.setPort(port);
            serversBeanDB.setTag(deviceType);
            ServersDBUtils.insertOrReplaceData(serversBeanDB);
            mHandler.sendEmptyMessage(Handler_OkCode);
        } else if (type.equals("config")) {
            SharePreferenceUtil.put(AddServersActivity.this, SharePreferenceUtil.Current_DeviceType, deviceType);
            SharePreferenceUtil.put(AddServersActivity.this, SharePreferenceUtil.Current_IP, ip);
            SharePreferenceUtil.put(AddServersActivity.this, SharePreferenceUtil.Current_Port, port);
            finish();
        }
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
