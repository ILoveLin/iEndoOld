package org.company.iendo.mineui.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.widget.HintLayout;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public HashMap<String, String> mPicMap = new HashMap<>();
    public ArrayList<String> mDataList = new ArrayList<>();
    public static final int REFRESH = 110;
    public static final int EMPTY = 120;
    private File toLocalFile;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH:
                    showComplete();
                    mAdapter.setData(getLocalImagePathList());
                    startGetReallyPic();
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
        ip = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_IP, "");
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
                    ip = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_IP, "");
                    String path = "smb://" + ip + "/ImageData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/";
                    initSmb(path);
                    if (mRootFolder.exists()) {
                        SmbFile[] smbFiles = mRootFolder.listFiles(); // smb://192.168.128.146/ImageData/Images/4033/thumb/
                        for (int i = 0; i < smbFiles.length; i++) {
                            //添加每个图片名字的前缀
                            mDataList.add(smbFiles[i].getName().split("\\.")[0]);
                            toLocalFile = new File(Environment.getExternalStorageDirectory() +
                                    "/MyData/Images/" + CaseDetailMsgActivity.getCurrentID());
                            File toFile = new File(Environment.getExternalStorageDirectory() +
                                    "/MyData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/");
                            if (!toFile.exists()) {   //不存在创建
                                toFile.mkdirs();
                                String remoteUrl = "smb://cmeftproot:lzjdzh19861207@" + ip + "/";
                                downloadFileToFolder(remoteUrl, "ImageData/Images/"
                                                + CaseDetailMsgActivity.getCurrentID() + "/thumb/",
                                        smbFiles[i].getName(), toFile.getAbsolutePath());

                            } else {
                                String remoteUrl = "smb://cmeftproot:lzjdzh19861207@" + ip + "/";
                                downloadFileToFolder(remoteUrl, "ImageData/Images/"
                                                + CaseDetailMsgActivity.getCurrentID() + "/thumb/",
                                        smbFiles[i].getName(), toFile.getAbsolutePath());
                            }
                        }
                        mHandler.sendEmptyMessage(REFRESH);
                    } else {
                        mHandler.sendEmptyMessage(EMPTY);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    private void startGetReallyPic() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    ip = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_IP, "");
                    String picRootPath = "smb://" + ip + "/ImageData/Images/" + CaseDetailMsgActivity.getCurrentID() + File.separator;
                    LogUtils.e("====picture======length=" + picRootPath);
                    initSmb(picRootPath);
                    SmbFile[] smbFiles = mRootFolder.listFiles();
                    for (int i = 0; i < smbFiles.length; i++) {
                        if (!smbFiles[i].isDirectory()) {
                            String PicName = smbFiles[i].getName();
                            File toFile = new File(Environment.getExternalStorageDirectory() +
                                    "/MyData/Images/" + CaseDetailMsgActivity.getCurrentID());
                            String mPicKeyName = PicName.split("\\.")[0];
                            String mPicValuePath = toFile + "/" + PicName;
                            mPicMap.put(mPicKeyName, mPicValuePath);
                            String remoteUrl = "smb://cmeftproot:lzjdzh19861207@" + ip + "/";
                            downloadReallyFileToFolder(remoteUrl, "ImageData/Images/"
                                            + CaseDetailMsgActivity.getCurrentID() + "/",
                                    PicName, toFile.getAbsolutePath());


                        }


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void initSmb(String rootPath) throws UnknownHostException, SmbException, MalformedURLException {
        ip = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_IP, "");
        System.setProperty("jcifs.smb.client.dfs.disabled", "true"); //true false
        System.setProperty("jcifs.smb.client.soTimeout", "1000000");
        System.setProperty("jcifs.smb.client.responseTimeout", "30000");
        String username = "cmeftproot";
        String password = "lzjdzh19861207";

//        String rootPath = "smb://" + ip + "/ImageData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/";
//        String rootPath = "smb://" + ip + "/ImageData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/";
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
     * @param fileName
     * @param localDir
     */
    public static void downloadReallyFileToFolder(String remoteUrl, String shareFolderPath, String fileName, String localDir) {
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl + shareFolderPath + fileName);
            File localFile = new File(localDir + "/" + fileName);
            BufferedInputStream in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] buffer = new byte[10 * 1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载文件到指定文件夹
     *
     * @param remoteUrl
     * @param shareFolderPath
     * @param fileName
     * @param localDir
     */
    public static void downloadFileToFolder(String remoteUrl, String shareFolderPath, String fileName,
                                            String localDir) {
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl + shareFolderPath + fileName);
            File localFile = new File(localDir + "/" + fileName);
            BufferedInputStream in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] buffer = new byte[8 * 1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);

            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        ArrayList viewPagerDataList = getViewPagerPicturePath();
        if (viewPagerDataList.size() != 0) {
            ImagePreviewActivity.start(getAttachActivity(), viewPagerDataList, viewPagerDataList.size() - 1);
        } else {
            toast("暂无数据!");
        }


    }


    @SuppressLint("NewApi")
    public ArrayList getViewPagerPicturePath() {
        ArrayList<String> pathList = new ArrayList<>();

        if (mPicMap != null && mPicMap.size() != 0 || mDataList != null && mDataList.size() != 0) {
            Set<Map.Entry<String, String>> entriesList = mPicMap.entrySet();
            entriesList.stream().forEach((entry) -> {
                for (int i = 0; i < mDataList.size(); i++) {
                    if (mDataList.get(i).equals(entry.getKey())) {
                        pathList.add(entry.getValue());
                        Log.e("========root=====", "local==entry.getValue()==" + "" + entry.getValue());
                    }
                }
            });

            return pathList;
        }
        return pathList;

    }


    @SuppressLint("NewApi")
    public ArrayList<String> getLocalImagePathList() {
        ArrayList<String> images = new ArrayList<>();
        File localFile = new File(Environment.getExternalStorageDirectory() +
                "/MyData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb");
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        File[] files = localFile.listFiles();
        Stream.of(files).forEach(f -> images.add(f.getAbsolutePath()));
        for (int i = 0; i < images.size(); i++) {
        }
        return images;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
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
