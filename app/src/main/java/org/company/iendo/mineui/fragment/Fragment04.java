package org.company.iendo.mineui.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
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
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.mineui.activity.live.SMBPlayerActivity;
import org.company.iendo.mineui.fragment.adapter.VideoAdapter;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbSession;

/**
 * LoveLin
 * <p>
 * Describe 第四个fragment   读取SMB 共享视频界面
 */
public class Fragment04 extends MyFragment<MainActivity> implements StatusAction, CaseOperatorAction, BaseAdapter.OnItemClickListener {

    private HintLayout mHintLayout;
    private CaseDetailMsgActivity mActivity;
    private WrapRecyclerView mRecyclerView;
    private VideoAdapter mAdapter;
    private SmbFile mRootFolder;
    private String ip;
    public static final int REFRESH_04 = 110;
    public static final int EMPTY_04 = 120;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_04:
                    mAdapter.setData(getLocalImagePathList());
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
        mActivity.setCaseOperatorAction(this);
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

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    initSmb();
                    Log.e("========root===exists==", mRootFolder.exists() + "");

                    if (mRootFolder.exists()) {
                        showComplete();
                        SmbFile[] smbFiles = mRootFolder.listFiles(); // smb://192.168.128.146
                        // /ImageData/Videos/4033/thumb/
                        for (int i = 0; i < smbFiles.length; i++) {
                            Log.e("========root=====", "第" + i + "条数据");
                            Log.e("========root=====", smbFiles[i].getName());

                            String url = mRootFolder + smbFiles[i].getName();
                            Log.e("========root=====", url);

//                            File toFile = new File(Environment.getExternalStorageDirectory() + "/MyData/Videos/" + CaseDetailMsgActivity.getCurrentID());
//                            if (!toFile.exists()) {   //不存在创建
//                                toFile.mkdirs();
//
//                                Log.e("====root==localDir=不存在=", toFile.getAbsolutePath());
//                                String remoteUrl = "smb://cmeftproot:lzjdzh19861207@" + ip + "/";
//
//
//                                downloadFileToFolder(remoteUrl, "ImageData/Videos/" + CaseDetailMsgActivity.getCurrentID() ,
//                                        smbFiles[i].getName(), toFile.getAbsolutePath());
//                            }
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

    /**
     * 下载文件到指定文件夹
     *
     * @param remoteUrl
     * @param shareFolderPath
     * @param fileName
     * @param localDir
     */
    public static void downloadFileToFolder(String remoteUrl, String shareFolderPath, String fileName, String localDir) {
        try {
            Log.e("========root=====", "down===remoteUrl===" + remoteUrl);
            Log.e("========root=====", "down===shareFolderPath===" + shareFolderPath);
            Log.e("========root=====", "down===src_path===" + remoteUrl + shareFolderPath + "/" + fileName);
            Log.e("========root=====", "down===localDir===" + localDir);
            Log.e("========root=====", "======================================================");
            SmbFile remoteFile = new SmbFile(remoteUrl + shareFolderPath + fileName);
            File localFile = new File(localDir + File.separator + fileName);
            InputStream in = new SmbFileInputStream(remoteFile);
            OutputStream out = new FileOutputStream(localFile);
            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);

            }
            in.close();
            out.close();
        } catch (Exception e) {
            Log.e("========root=====", "down==Exception" + "");
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    public ArrayList<String> getLocalImagePathList() {
        ArrayList<String> images = new ArrayList<>();
        File localFile = new File(Environment.getExternalStorageDirectory() + "/MyData/Videos/" + CaseDetailMsgActivity.getCurrentID());
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        File[] files = localFile.listFiles();
        Stream.of(files).forEach(f -> images.add(f.getAbsolutePath()));
        for (int i = 0; i < images.size(); i++) {
            Log.e("========root=====", "local==collect==" + "" + images.get(i));
        }
        return images;
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
