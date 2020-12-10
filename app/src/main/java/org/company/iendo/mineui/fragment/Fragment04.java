package org.company.iendo.mineui.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.activity.live.SMBPlayerActivity;
import org.company.iendo.mineui.fragment.adapter.VideoAdapter;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;

/**
 * LoveLin
 * <p>
 * Describe 第四个fragment   读取SMB 共享视频界面
 */
public class Fragment04 extends MyFragment<MainActivity> implements StatusAction, BaseAdapter.OnItemClickListener {
    private HintLayout mHintLayout;
    private CaseDetailMsgActivity mActivity;
    private WrapRecyclerView mRecyclerView;
    private VideoAdapter mAdapter;
    private SmbFile mRootFolder;
    private String ip;
    public static final int REFRESH_04 = 110;
    public static final int EMPTY_04 = 120;
    private ArrayList<String> mVideoList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_04:
                    showComplete();
                    mAdapter.setData(mVideoList);
                    break;
                case EMPTY_04:
                    showEmpty();
                    break;
            }
        }
    };

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
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getAttachActivity(), 1, R.drawable.shape_divideritem_decoration));
    }

    @Override
    protected void initData() {
        //验证是否许可权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    //这里就是权限打开之后自己要操作的逻辑
                    responseListener();

                }
            }
        }
    }

    private void responseListener() {
        showLoading();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    initSmb();
                    Log.e("========root===exists==", mRootFolder.exists() + "");
                    if (mRootFolder.exists()) {
                        SmbFile[] smbFiles = mRootFolder.listFiles(); // smb://192.168.128.146
                        // /ImageData/Videos/4033/thumb/
                        mVideoList = new ArrayList<>();
                        for (int i = 0; i < smbFiles.length; i++) {
                            Log.e("========root=====", "第" + i + "条数据");
                            Log.e("========root=====", smbFiles[i].getName());
                            Log.e("===root=idddd==", CaseDetailMsgActivity.getCurrentID());
                            Log.e("===root=cuet==idddd==", CaseDetailMsgActivity.getCurrentID());
                            Log.e("===root=ip==", getCurrentIP());
                            String url = "smb://cmeftproot:lzjdzh19861207@" + getCurrentIP() + "/ImageData/Videos/"
                                    + CaseDetailMsgActivity.getCurrentID() + "/" + smbFiles[i].getName();
//                            String url = mRootFolder + smbFiles[i].getName();
                            Log.e("========root=====", url);
//                            smb://cmeftproot:lzjdzh19861207@192.168.128.96/ImageData/Videos/3764/祝柳思20200827165247927.mp4
                            mVideoList.add(smbFiles[i].getName());
                            mHandler.sendEmptyMessage(REFRESH_04);
                        }
                    } else {
                        mHandler.sendEmptyMessage(EMPTY_04);
                    }
                } catch (Exception e) {
                    Log.e("========root==Ex=", "");
                    e.printStackTrace();
                }

            }
        }.start();


    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast(position);
        String title = mAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), SMBPlayerActivity.class);
        String url = "smb://cmeftproot:lzjdzh19861207@" + getCurrentIP() + "/ImageData/Videos/"
                + CaseDetailMsgActivity.getCurrentID() + "/" + title;
        intent.putExtra("url", url + "");
        intent.putExtra("title", title + "");
        toast(url);
        startActivity(intent);
    }

    private void initSmb() throws UnknownHostException, SmbException, MalformedURLException {
        System.setProperty("jcifs.smb.client.dfs.disabled", "true"); //true false
        System.setProperty("jcifs.smb.client.soTimeout", "1000000");
        System.setProperty("jcifs.smb.client.responseTimeout", "30000");
        String username = "cmeftproot";
        String password = "lzjdzh19861207";
        ip = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_IP, "");
        Log.e("========root==ip=", ip);

        String rootPath = "smb://" + ip + "/ImageData/Videos/" + CaseDetailMsgActivity.getCurrentID() + "/";
        Log.e("========root==rootPath=", rootPath);

        UniAddress mDomain = UniAddress.getByName(ip);
        NtlmPasswordAuthentication mAuthentication = new NtlmPasswordAuthentication(ip, username, password);
        SmbSession.logon(mDomain, mAuthentication);
        mRootFolder = new SmbFile(rootPath, mAuthentication);
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
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
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
