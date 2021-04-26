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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.bean.HttpImageBean;
import org.company.iendo.bean.beandb.image.DimImageBean;
import org.company.iendo.bean.beandb.image.ImageListDownDBBean;
import org.company.iendo.bean.beandb.image.ReallyImageBean;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.fragment.adapter.PictureAdapter;
import org.company.iendo.ui.activity.ImagePreviewActivity;
import org.company.iendo.util.BeanToUtils;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.SDFileUtil;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.db.ImageDBUtils;
import org.company.iendo.widget.HintLayout;

import java.io.File;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;
import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe 第三个fragment   读取SMB 共享图片界面
 * //图片视频查询
 * images.aspx?UserID=  获取指定病人的图片信息       get           返回值 0传入参数为空  -1病人id不存在
 * video.aspx？RecordID=    病人视频信息            get                 返回值 0传入参数为空  -1病人id不存在
 */
public class Fragment03 extends MyFragment<MainActivity> implements StatusAction, BaseAdapter.OnItemClickListener {
    public HashMap<String, String> mPicMap = new HashMap<>();
    public ArrayList<String> mDataList = new ArrayList<>();
    private ImageListDownDBBean mImageListBean;
    private ArrayList<String> dimImageList;
    private ArrayList<String> reallyPathList = new ArrayList<>();
    private CaseDetailMsgActivity mActivity;
    private WrapRecyclerView mRecyclerView;
    public static final int REFRESH = 110;
    public static final int EMPTY = 120;
    public static final int REALLYOK = 130;
    private boolean canClick = false;     //原图下载完毕才可以进入查看
    private HintLayout mHintLayout;
    private PictureAdapter mAdapter;
    private SmbFile mRootFolder;
    private String ip;
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
                case REALLYOK://读取SMB原图成功,打开可以查看原图的开关
                    toast("原图缓存成功！");
                    canClick = true;
                    break;
                case EMPTY:

                    showEmpty();
                    break;
            }
        }
    };
    private ArrayList viewPagerDataList;
    private ArrayList<String> mThumbImageList;
    private ArrayList<String> mReallyImageList;


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
        mAdapter.setOnItemClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        /**
         * setAdapter之前设置点击监听,不然报错
         */
        mRecyclerView.setAdapter(mAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        String mIP = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_IP, "");
        String mPort = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_Port, "");
        String mHost = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_Host, "");
        SharePreferenceUtil.put(getActivity(), SharePreferenceUtil.Current_BaseUrl, "http://" + mIP + ":" + mPort);  //末尾不加斜杠
        //http://192.168.66.42:8008
        String mBaseUrl = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_BaseUrl, "");
        LogUtils.e("mIP===" + mIP + "mPort====" + mPort + "mHost=====" + mHost);
        String url = mBaseUrl + "/images.aspx?";
        LogUtils.e("url===" + url);
        OkHttpUtils.get()
                .url(mBaseUrl + "/images.aspx?")
                .addParams("UserID", CaseDetailMsgActivity.getCurrentID())
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast("图片请求错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson mGson = new Gson();
                        Type type = new TypeToken<HttpImageBean>() {
                        }.getType();
                        HttpImageBean mHttpImageBean = mGson.fromJson(response, type);
                        /**
                         * http格式下图片和视频访问路径
                         * http://192.168.66.42:8008/Images/3915/thumb/001.jpg    //模糊图
                         * http://192.168.66.42:8008/Images/3915/001.jpg   //高清图
                         * http://192.168.66.42:8008/Videos/3874/444420210318164613550.mp4
                         */


                        String mThumbHeader = "http://192.168.66.42:8008" + "/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/";
                        String mReallyHeader = "http://192.168.66.42:8008" + "/Images/" + CaseDetailMsgActivity.getCurrentID() + "/";
                        mThumbImageList = new ArrayList<>();
                        mReallyImageList = new ArrayList<>();
                        for (int i = 0; i < mHttpImageBean.getDs().size(); i++) {
                            //ThumbPath 模糊图        ImagePath  原图
                            mThumbImageList.add(mThumbHeader + mHttpImageBean.getDs().get(i).getThumbPath());
                            mReallyImageList.add(mReallyHeader + mHttpImageBean.getDs().get(i).getImagePath());
                        }

                        mAdapter.setData(mThumbImageList);

                    }
                });


//        //验证是否许可权限
//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            //验证是否许可权限
//            for (String str : permissions) {
//                if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    //申请权限
//                    getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                    return;
//                } else {
//                    //这里就是权限打开之后自己要操作的逻辑
//                    if (mActivity.getCurrentOnlineType()) {
//                        responseListener();
//                    } else {
//                        List<String> dimImagePathList = BeanToUtils.getDimImagePathList(mActivity.getItemBeanID());
//                        mAdapter.setData(dimImagePathList);
//                    }
//
//                }
//            }
//        }
    }

    private void responseListener() {
        showLoading();
        new Thread() {
            @Override
            public void run() {
                super.run();
                //获取模糊图
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
                                SDFileUtil.downLoadFileToFolder(remoteUrl, "ImageData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/",
                                        smbFiles[i].getName(), toFile.getAbsolutePath());
                            } else {
                                //存在，在判断smb文件数量和本地文件数量，不同就同步文件
                                if (toFile.listFiles().length != smbFiles.length) {
                                    String remoteUrl = "smb://cmeftproot:lzjdzh19861207@" + ip + "/";
                                    SDFileUtil.downLoadFileToFolder(remoteUrl, "ImageData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb/",
                                            smbFiles[i].getName(), toFile.getAbsolutePath());
                                }
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
                    LogUtils.e("====DP==DimPath==开始下载=====");
                    for (int i = 0; i < smbFiles.length; i++) {
                        if (!smbFiles[i].isDirectory()) {   //不是文件夹,是原图,才读取
                            String localPath = Environment.getExternalStorageDirectory() +
                                    "/MyData/Images/" + CaseDetailMsgActivity.getCurrentID();
                            String PicName = smbFiles[i].getName();
                            String mPicKeyName = PicName.split("\\.")[0];
                            String mPicValuePath = localPath + "/" + PicName;
                            //存入map,key是name,value是文件路径
                            mPicMap.put(mPicKeyName, mPicValuePath);
                            File file = new File(localPath + "/" + PicName);
                            if (!file.exists()) { //存在就不用读取
                                String remoteUrl = "smb://cmeftproot:lzjdzh19861207@" + ip + "/";
                                SDFileUtil.downLoadReallyFileToFolder(remoteUrl, "ImageData/Images/"
                                                + CaseDetailMsgActivity.getCurrentID() + "/",
                                        PicName, localPath);
                                LogUtils.e("====DP==DimPath===这条数据=不存在==下载一条===PicName==" + PicName);
                            } else {
                                LogUtils.e("====DP==DimPath==这条数据=存在=本地取===PicName==" + PicName);
                            }
                        }
                    }
                    LogUtils.e("====DP==DimPath==全部===下载读取完毕=====");
                    mHandler.sendEmptyMessage(REALLYOK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @SuppressLint("NewApi")
    public ArrayList getViewPagerPicturePath() {
        reallyPathList = new ArrayList<String>();
        if (mPicMap != null && mPicMap.size() != 0 || mDataList != null && mDataList.size() != 0) {
            Set<Map.Entry<String, String>> entriesList = mPicMap.entrySet();
            entriesList.stream().forEach((entry) -> {
                for (int i = 0; i < mDataList.size(); i++) {
                    if (mDataList.get(i).equals(entry.getKey())) {
                        reallyPathList.add(entry.getValue() + "");
                        Log.e("========root=====", "local==entry.getValue()==" + "" + entry.getValue());
                    }
                }
            });
            return reallyPathList;
        }
        return reallyPathList;
    }

    @SuppressLint("NewApi")
    public ArrayList<String> getLocalImagePathList() {
        dimImageList = new ArrayList<>();
        File localFile = new File(Environment.getExternalStorageDirectory() +
                "/MyData/Images/" + CaseDetailMsgActivity.getCurrentID() + "/thumb");
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        File[] files = localFile.listFiles();
        Stream.of(files).forEach(f -> dimImageList.add(f.getAbsolutePath()));
        for (int i = 0; i < dimImageList.size(); i++) {
        }
        return dimImageList;
    }

    private void initSmb(String rootPath) throws UnknownHostException, SmbException, MalformedURLException {
        ip = (String) SharePreferenceUtil.get(getActivity(), SharePreferenceUtil.Current_IP, "");
        System.setProperty("jcifs.smb.client.dfs.disabled", "true"); //true false
        System.setProperty("jcifs.smb.client.soTimeout", "1000000");
        System.setProperty("jcifs.smb.client.responseTimeout", "30000");
        String username = "cmeftproot";
        String password = "lzjdzh19861207";
        UniAddress mDomain = UniAddress.getByName(ip);
        NtlmPasswordAuthentication mAuthentication = new NtlmPasswordAuthentication(ip, username, password);
        SmbSession.logon(mDomain, mAuthentication);
        mRootFolder = new SmbFile(rootPath, mAuthentication);
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

        if (mActivity.getCurrentOnlineType()) {

            if (mReallyImageList.size() != 0) {
                ImagePreviewActivity.start(getAttachActivity(), mReallyImageList, mReallyImageList.size() - 1);
            } else {
                toast("暂无数据!");
            }
        } else {
//            ArrayList<String> reallyImagePathList = (ArrayList<String>) BeanToUtils.getReallyImagePathList(mActivity.getItemBeanID());
//            Log.e("========root=====", "local==reallyImagePathList==" + "" + reallyImagePathList.size());
//            for (int i = 0; i < reallyImagePathList.size(); i++) {
//                Log.e("========root=====", "local==reallyImagePathList==" + "" + reallyImagePathList.get(i));
//            }
//            if (reallyImagePathList.size() != 0) {
//                ImagePreviewActivity.start(getAttachActivity(), reallyImagePathList, reallyImagePathList.size() - 1);
//            } else {
//                toast("暂无数据!");
//            }
        }

//          原来SMB  服务下载原图的方法
//        if (mActivity.getCurrentOnlineType()) {
//            if (canClick) { //跳转原图界面的开关
//                viewPagerDataList = getViewPagerPicturePath();
//                if (viewPagerDataList.size() != 0) {
//                    ImagePreviewActivity.start(getAttachActivity(), viewPagerDataList, viewPagerDataList.size() - 1);
//                } else {
//                    toast("暂无数据!");
//                }
//            } else {
//                toast("高清图读取中...,请稍后再试!");
//            }
//        } else {
//            ArrayList<String> reallyImagePathList = (ArrayList<String>) BeanToUtils.getReallyImagePathList(mActivity.getItemBeanID());
//            Log.e("========root=====", "local==reallyImagePathList==" + "" + reallyImagePathList.size());
//            for (int i = 0; i < reallyImagePathList.size(); i++) {
//                Log.e("========root=====", "local==reallyImagePathList==" + "" + reallyImagePathList.get(i));
//            }
//            if (reallyImagePathList.size() != 0) {
//                ImagePreviewActivity.start(getAttachActivity(), reallyImagePathList, reallyImagePathList.size() - 1);
//            } else {
//                toast("暂无数据!");
//            }
//        }

//   文件下载
//        OkHttpUtils//
//                .get()//
//                .url("url")//
//                .build()//传入文件需要保存的文件夹以及文件名。
//                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar")
//                {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(File response, int id) {
//
//                    }
//
//                });
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
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    /**
     * 把文件路径写入数据库
     */
    private void insertFilePathToDB() {

        //开启线程，把模糊图存入数据库，并且绑定ID
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建Bean对象
                mImageListBean = new ImageListDownDBBean();
                //设置存入图片对象的 用户ID
                LogUtils.e("====DP==DimPath==开启DB数据库线程=====");
                LogUtils.e("====DP==DimPath==模糊===之前=用户ID=" + CaseDetailMsgActivity.getCurrentID());
                mImageListBean.setItemID(CaseDetailMsgActivity.getCurrentID());
                mImageListBean.setTag(CaseDetailMsgActivity.getCurrentID());
                mImageListBean.setDownTag("0");  //只要进入03，我就全部下载存入数据库，downTag为0，如果是用户手动下载则为1
                ArrayList<DimImageBean> list00 = new ArrayList<>();
                for (int i = 0; i < dimImageList.size(); i++) {
                    DimImageBean dimBean = new DimImageBean();
                    dimBean.setDimFilePath(dimImageList.get(i));
                    LogUtils.e("====DP==DimPath==模糊===之前==" + dimImageList.get(i));
                    list00.add(dimBean);
                }
                //存入模糊图片路径的list
                mImageListBean.setMDimImageList(list00);
                //然后开始存原图
                ArrayList ReallyPathList = getViewPagerPicturePath();
                ArrayList<ReallyImageBean> list01 = new ArrayList<>();
                LogUtils.e("====DP==ReallyPath=====ReallyPathList==" + ReallyPathList.size());
                for (int i = 0; i < ReallyPathList.size(); i++) {
                    ReallyImageBean reallyImageBean = new ReallyImageBean();
                    reallyImageBean.setReallyFilePath((String) ReallyPathList.get(i));
                    LogUtils.e("====DP==ReallyPath==原图===之前==" + (String) ReallyPathList.get(i));
                    list01.add(reallyImageBean);
                }
                //存入原图片路径的list
                mImageListBean.setMReallyImageList(list01);
                //存入数据库
                ImageDBUtils.insertOrReplaceData(mImageListBean);
                boolean isExist = ImageDBUtils.queryListIsExist(CaseDetailMsgActivity.getCurrentID());
                LogUtils.e("====DP==ReallyPath==DB===是否存在==" + isExist);

                List<ImageListDownDBBean> ImageListDownDBBeans = ImageDBUtils.queryListByTag(CaseDetailMsgActivity.getCurrentID());
                for (int i = 0; i < ImageListDownDBBeans.size(); i++) {
                    List<DimImageBean> mDimImageList = ImageListDownDBBeans.get(i).getMDimImageList();
                    List<ReallyImageBean> mReallyImageList = ImageListDownDBBeans.get(i).getMReallyImageList();
                    LogUtils.e("====DP==ReallyPath=====DB之后==" + ImageListDownDBBeans.get(i).getItemID());
                    LogUtils.e("====DP==ReallyPath=====DB之后==" + ImageListDownDBBeans.get(i).getMDimImageList().size());
                    LogUtils.e("====DP==ReallyPath=====DB之后==" + ImageListDownDBBeans.get(i).getMReallyImageList().size());


                    for (int x = 0; x < ImageListDownDBBeans.get(i).getMDimImageList().size(); x++) {
                        LogUtils.e("====DP==ReallyPath=====DB之后===模糊图====" +
                                ImageListDownDBBeans.get(i).getMDimImageList().get(x).getDimFilePath());
                    }
                    for (int y = 0; y < ImageListDownDBBeans.get(i).getMReallyImageList().size(); y++) {
                        LogUtils.e("====DP==ReallyPath=====DB之后===原图====" +
                                ImageListDownDBBeans.get(i).getMReallyImageList().get(y).getReallyFilePath());
                    }
                }
            }
        }).start();


    }

}
