package org.company.iendo.mineui.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.mineui.fragment.adapter.PictureAdapter;
import org.company.iendo.other.IntentKey;
import org.company.iendo.ui.activity.ImagePreviewActivity;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.widget.HintLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
 * Describe 第三个fragment   读取SMB 共享图片界面
 */


public class Fragment03 extends MyFragment<MainActivity> implements StatusAction, CaseOperatorAction, BaseAdapter.OnItemClickListener {
    private HintLayout mHintLayout;
    private CaseDetailMsgActivity mActivity;
    private WrapRecyclerView mRecyclerView;
    private PictureAdapter mAdapter;
    private SmbFile mRootFolder;
    private String ip;

    public static final int REFRESH = 110;
    public static final int EMPTY = 120;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH:
                    showComplete();
                    mAdapter.setData(getLocalImagePathList());
                    break;
                case EMPTY:
                    showEmpty();
                    break;
            }
        }
    };


    public Fragment03(CaseDetailMsgActivity Activity) {
        this.mActivity = Activity;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_03;
    }

    @Override
    protected void initView() {
        mHintLayout = findViewById(R.id.hl_status_hint);
        mRecyclerView = findViewById(R.id.recycleview_case03);
        mAdapter = new PictureAdapter(getActivity());
        mActivity.setCaseOperatorAction(this);
        mAdapter.setOnItemClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        /**
         * setAdapter之前设置点击监听,不然报错
         */
        mRecyclerView.setAdapter(mAdapter);

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
                        SmbFile[] smbFiles = mRootFolder.listFiles(); // smb://192.168.128.146/ImageData/Images/4033/thumb/
                        for (int i = 0; i < smbFiles.length; i++) {
                            Log.e("========root=====", "第" + i + "条数据");
                            Log.e("========root=====", smbFiles[i].getName());
                            File toFile = new File(Environment.getExternalStorageDirectory() + "/MyData/Images/" + CaseDetailMsgActivity.getCurrentID());
                            if (!toFile.exists()) {   //不存在创建
                                toFile.mkdirs();
                                Log.e("====root==localDir=不存在=", toFile.getAbsolutePath());
                                String remoteUrl = "smb://cmeftproot:lzjdzh19861207@" + ip + "/";
                                downloadFileToFolder(remoteUrl, "ImageData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/",
                                        smbFiles[i].getName(), toFile.getAbsolutePath());
                            }
                            mHandler.sendEmptyMessage(REFRESH);
                        }
                    } else {
                        mHandler.sendEmptyMessage(EMPTY);
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

        String rootPath = "smb://" + ip + "/ImageData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/";
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
            Log.e("========root=====", "down===src_path===" + remoteUrl + shareFolderPath + fileName);
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

    public void onResume() {
        super.onResume();
        Log.e("TAG", "fragment03");
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    /**
     * itemClick
     *
     * @param recyclerView RecyclerView 对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     */
    @SuppressLint("NewApi")
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast(position);
        ArrayList<String> localImagePathList = getLocalImagePathList();
        ImagePreviewActivity.start(getAttachActivity(), localImagePathList, localImagePathList.size() - 1);
    }

    @SuppressLint("NewApi")
    public ArrayList<String> getLocalImagePathList() {
        ArrayList<String> images = new ArrayList<>();
        File localFile = new File(Environment.getExternalStorageDirectory() + "/MyData/Images/" + CaseDetailMsgActivity.getCurrentID());
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

    ;

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
