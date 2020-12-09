package org.company.iendo.mineui.activity.casemsg;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.hjq.base.BaseDialog;
import com.hjq.base.BaseFragmentAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.bean.CaseDetailMsgBean;
import org.company.iendo.bean.CaseManagerListBean;
import org.company.iendo.bean.beandb.UserDetailMSGDBBean;
import org.company.iendo.bean.beandb.image.DimImageBean;
import org.company.iendo.bean.beandb.image.ImageListDownDBBean;
import org.company.iendo.bean.beandb.image.ReallyImageBean;
import org.company.iendo.bean.event.AddDeleteEvent;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.mineui.activity.casemsg.thread.DownPictureThread;
import org.company.iendo.mineui.activity.live.LiveConnectDeviceActivity;
import org.company.iendo.mineui.fragment.Fragment01;
import org.company.iendo.mineui.fragment.Fragment02;
import org.company.iendo.mineui.fragment.Fragment03;
import org.company.iendo.mineui.fragment.Fragment04;
import org.company.iendo.ui.dialog.MessageDialog;
import org.company.iendo.ui.dialog.SelectDialog;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.db.ImageDBUtils;
import org.company.iendo.util.db.UserDetailMSGDBUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe 病例信息详情界面
 */
public class CaseDetailMsgActivity extends MyActivity implements DownPictureThread.onThreadStatueListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentAdapter<MyFragment> mPagerAdapter;
    private LinearLayout mTitleBar;
    private ImageButton mLeft;
    private TextView mLive;
    private TextView mPrint;
    private TextView mDelete;
    private TextView mDownload;
    private TextView mEdit;
    private static String itemID;
    private CaseManagerListBean.DsDTO bean;
    private int deletePosition;
    private ArrayList<String> selectedList = new ArrayList<>();
    private CaseDetailMsgBean.DsDTO mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_msg_detail;
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tl_home_tab);
        mTitleBar = findViewById(R.id.rl_top);
        mLeft = findViewById(R.id.ib_left);
        mLive = findViewById(R.id.titile_live);
        mPrint = findViewById(R.id.titile_print);
        mDelete = findViewById(R.id.titile_delete);
        mDownload = findViewById(R.id.titile_download);
        mEdit = findViewById(R.id.titile_edit);
        mViewPager = findViewById(R.id.vp_home_pager);
        int statusBarHeight = getStatusBarHeight();
        Log.e("TAG", "statusBarHeight===" + statusBarHeight);
        setOnClickListener(R.id.titile_live, R.id.titile_print, R.id.titile_delete, R.id.titile_download, R.id.titile_edit);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = statusBarHeight + 23;
        mTitleBar.setLayoutParams(params);

        itemID = getIntent().getStringExtra("ID");
        deletePosition = Integer.parseInt(getIntent().getStringExtra("position"));
        bean = (CaseManagerListBean.DsDTO) getIntent().getSerializableExtra("bean");
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        Fragment01 fragment01 = new Fragment01(this);
        Fragment02 fragment02 = new Fragment02(this);
        Fragment03 fragment03 = new Fragment03(this);
        Fragment04 fragment04 = new Fragment04(this);

        mPagerAdapter.addFragment(fragment01, "个人信息");
        mPagerAdapter.addFragment(fragment02, "病例信息");
        mPagerAdapter.addFragment(fragment03, "图片");
        mPagerAdapter.addFragment(fragment04, "视频");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        sendDetailMSGRequest();

    }

    private void sendDetailMSGRequest() {
        showDialog();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_Case_Detail)
                .addParams("patientsid", getCurrentID())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideDialog();
                        if ("0".equals(response)) {
                            toast("请求参数有误");
                        } else {
                            LogUtils.e("TAG--01" + response);
//                            @"\n", @"\r\n", @"\t", @"\\"

                            String regEx = "[\n  \\r\\n \\t  \\\\]";
//                            String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
                            String aa = " ";//这里是将特殊字符换为aa字符串," "代表直接去掉
                            Pattern p = Pattern.compile(regEx);
                            Matcher m = p.matcher(response);//这里把想要替换的字符串传进来
                            String newString = m.replaceAll(aa).trim();
                            LogUtils.e("TAG--01" + newString);
//                            String myJson=   mGson.toJson(response);//将gson转化为json
                            Type type = new TypeToken<CaseDetailMsgBean>() {
                            }.getType();
                            CaseDetailMsgBean bean = mGson.fromJson(newString, type);
                            if (bean.getDs().size() >= 0) {
                                mBean = bean.getDs().get(0);
                            }
                        }


                    }
                });
    }


    @Override
    public void onClick(View v) {
        Boolean currentOnlineType = getCurrentOnlineType();
        switch (v.getId()) {
            case R.id.titile_live:      //直播
                if (currentOnlineType) {
                    Intent intent = new Intent(CaseDetailMsgActivity.this, LiveConnectDeviceActivity.class);
                    intent.putExtra("ID", itemID);
                    startActivity(intent);
                    if (mAction != null) {
                        mAction.onLive();
                    }
                } else {
                    toast("离线登录无法查看直播");
                }
                break;
            case R.id.titile_print:       //打印
                if (currentOnlineType) {
                    sendRequest("printReport");
                } else {
                    toast("离线登录无法打印病例");
                }
                break;
            case R.id.titile_delete:      //删除
                showDeleteDialog();
                break;
            case R.id.titile_download:     //下载
                if (currentOnlineType) {
                    showDownDialog();
                } else {
                    toast("病例已存在！");
                }
                break;
            case R.id.titile_edit:
                if (currentOnlineType) {
                    mAction.onEdit();
                } else {
                    toast("离线病例无法编辑");
                }
                break;

        }
    }

    private void showDownDialog() {
        new SelectDialog.Builder(this)
                .setTitle("提示!")
                .setList("病例信息", "图片信息")
                // 设置最大选择数
                .setMaxSelect(2)
                // 设置默认选中
                .setSelect(0)
                .setListener(new SelectDialog.OnListener<String>() {
                    @Override
                    public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                        selectedList.clear();
                        for (Map.Entry<Integer, String> entry : data.entrySet()) {
                            LogUtils.e("选择的数据======" + "key = " + entry.getKey() +
                                    ", value = " + entry.getValue());
                            selectedList.add(entry.getValue());
                        }
                        //根据选择进行图片和用户信息的下载
                        getDownData(selectedList);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消了");
                    }
                })
                .show();

    }


    /**
     * 此处下载用户信息或者图片信息
     *
     * @param mList
     */

    private void getDownData(ArrayList<String> mList) {
        LogUtils.e("选择的数据===mList.size()===" + mList.size());
        LogUtils.e("选择的数据===mList.size(0)===" + mList.get(0));
        boolean isExistt = ImageDBUtils.queryListIsExist(getCurrentID());
        LogUtils.e("选择的数据==db=isExist===" + isExistt);
        if (mList.size() == 2) {  //图片和用户信息都要下载
            //先存用户信息  测试过没问题
            downLoadUserMsg();
            //再存图片
            downLoadPicture();
            LogUtils.e("选择的数据==db=isExist==2=");

        } else if (mList.get(0).equals("病例信息")) {
            downLoadUserMsg();
            LogUtils.e("选择的数据==db=isExist==1===病例信息===");

        } else if (mList.get(0).equals("图片信息")) {
            LogUtils.e("选择的数据==db=isExist==1===图片信息===");

            //因为fragment03界面开启了线程下载过模糊图和原图,只是没有写入数据库而已,这里做判断可以优化性能
            downLoadPicture();
        }
    }

    //下载图片信息
    private void downLoadPicture() {
        boolean isExist = ImageDBUtils.queryListIsExist(getCurrentID());
        //是否有本地缓存的标志
        File toFile = new File(Environment.getExternalStorageDirectory() +
                "/MyData/Images/" + getCurrentID() + "/thumb/");
        if (isExist) { //存在,数据库缓存里面读取
            List<ImageListDownDBBean> ImageListDownDBBeans = ImageDBUtils.queryListByTag(CaseDetailMsgActivity.getCurrentID());
            LogUtils.e("选择的数据===数据库存在==里面读取=====");
            LogUtils.e("选择的数据===ImageListDownDBBeans.size()===" + ImageListDownDBBeans.size());
            //因为，只要进入03，我就全部下载存入数据库，downTag为0，这里用户选择了下载所以我们去数据库中获取数据更新为1
            ImageListDownDBBean mCacheBean = ImageListDownDBBeans.get(0);
            mCacheBean.setDownTag("1");
            ImageDBUtils.insertOrReplaceData(mCacheBean);       //手动更新，换当前用户缓存，设置为下载类型


        } else {   //不存在,开启线程取下载SMB文件,并且存入数据库
//                不存在,表示没有进入过03界面,本地没有缓存,所以需要开启线程下载
            LogUtils.e("选择的数据===数据库=不存在==开启线程=====");
            DownPictureThread downThread = new DownPictureThread(this, itemID, "1", toFile.exists());
            downThread.setOnThreadStatueListener(this);
            new Thread(downThread).start();
        }
    }


    /**
     * 开启线城下载图片和用户信息的接口回调
     */
    @Override
    public void onUserMSGDownOK() {
        toast("用户表下载完毕");
    }

    @Override
    public void onDimPictureDownOK() {
        toast("模糊图下载完毕");


    }

    @Override
    public void onReallyPictureDownOK() {
        toast("原图下载完毕");

    }

    @Override
    public void onOverDownOK() {
        toast("图片下载完毕,并且存入数据库之中");

        /**
         * 存入数据库后获取图片。这里只是做数据的打印查看
         */
//        ImageDBUtils
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

    private void sendDeleteRequest() {
        if ("True".equals(getCurrentUserCan())) {  //可以删除
            showDialog();
            OkHttpUtils.get()
                    .url(getCurrentHost() + HttpConstant.CaseManager_Live_Connect_Delete)
                    .addParams("patientsId", itemID)   //要删除的病例id
                    .addParams("UserID", getUserId())        //当前用户id
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            hideDialog();
                            toast("链接服务器失败");

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            hideDialog();
                            //返回值 1成功   0传入参数为空 -1传入病人id不存在
                            if ("1".equals(response)) {
                                toast("删除成功");
                                EventBus.getDefault().post(new AddDeleteEvent(bean, "delete", deletePosition));
                                //删除成功的话需要把数据库的用户信息和图片信息删除
                                //删除用户信息
                                synchronizedDBDataAndFileData();


                            } else if ("-1".equals(response)) {
                                toast("传入病人id不存在");
                            }
                        }
                    });
        } else {

        }

    }

    //删除数据库用户表和图片表,以及SD卡图片
    private void synchronizedDBDataAndFileData() {
        boolean isExist = UserDetailMSGDBUtils.queryListIsExist(itemID);
        if (isExist) {
            //删除数据库用户Bean
            UserDetailMSGDBBean userDetailMSGDBBean = UserDetailMSGDBUtils.queryListByTag(itemID).get(0);
            UserDetailMSGDBUtils.deleteData(userDetailMSGDBBean);
        }
        //删除数据库图片Bean
        ImageListDownDBBean imageListDownDBBean = ImageDBUtils.queryListByTag(itemID).get(0);
        ImageDBUtils.deleteData(imageListDownDBBean);
        //删除SD卡图片文件
        new Thread(new Runnable() {
            @Override
            public void run() {



            }
        }).start();


    }

    private void sendRequest(String type) {
        showDialog();
        OkHttpUtils.post()
                .url(getCurrentHost() + HttpConstant.CaseManager_Live_Connect)
                .addParams("Name", type)
                .addParams("Param", itemID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                        LogUtils.e("path===onError===" + e);
                        toast("链接服务器失败");
                        //todo 联调有问题 和ios一样

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideDialog();
                        LogUtils.e("path===onResponse===" + response);


                    }
                });
    }

    private void showDeleteDialog() {
        new MessageDialog.Builder(CaseDetailMsgActivity.this)
                // 标题可以不用填写
                .setTitle("提示")
                // 内容必须要填写
                .setMessage("确定删除吗？")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        toast("确定");
                        sendDeleteRequest();
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消");

                    }
                })
                .show();

    }


    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void initData() {

        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public static String getCurrentID() {
        return itemID;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "fragment01");
    }

    private CaseOperatorAction mAction;

    public void setCaseOperatorAction(CaseOperatorAction action) {
        this.mAction = action;
    }

    //下载用户信息
    private void downLoadUserMsg() {
        boolean isExistT = UserDetailMSGDBUtils.queryListIsExist(itemID);
        LogUtils.e("选择的数据==db=用户表存在吗==isExist===isExist==00==" + isExistT);
        UserDetailMSGDBBean userDetailMSGDBBean = setUserMSGToDB(mBean);
        UserDetailMSGDBUtils.insertOrReplaceData(userDetailMSGDBBean);
        boolean isExist = UserDetailMSGDBUtils.queryListIsExist(itemID);

        LogUtils.e("选择的数据==db=用户表存在吗==isExist===isExist==01==" + isExist);

        List<UserDetailMSGDBBean> userDetailMSGDBBeans = UserDetailMSGDBUtils.queryListByTag(itemID);
        String sex = userDetailMSGDBBeans.get(0).getSex();
        LogUtils.e("选择的数据==db=用户表存在吗==isExist===数据库中取来的=性别==03==" + sex);

    }

    //设置数据,再获取到能存数据库的Bean
    private UserDetailMSGDBBean setUserMSGToDB(CaseDetailMsgBean.DsDTO mBean) {

        UserDetailMSGDBBean mDBDetailBean = new UserDetailMSGDBBean();

        mDBDetailBean.setTag(getCurrentID());  //设置查询条件的TAG
        mDBDetailBean.setCaseID("" + mBean.getCaseID());
        mDBDetailBean.setName("" + mBean.getName());
        mDBDetailBean.setPatientAge("" + mBean.getPatientAge());
        mDBDetailBean.setSex("" + mBean.getSex());
        mDBDetailBean.setOccupatior("" + mBean.getOccupatior());
        mDBDetailBean.setTel("" + mBean.getTel());
        mDBDetailBean.setMedHistory("" + mBean.getMedHistory());
        if (mBean.getReturnVisit().equals("False")) {
            mDBDetailBean.setReturnVisit("否");
        } else {
            mDBDetailBean.setReturnVisit("是");
        }
        mDBDetailBean.setCaseNo("" + mBean.getCaseNo());
        mDBDetailBean.setFee("" + mBean.getFee());
        mDBDetailBean.setSubmitDoctor("" + mBean.getSubmitDoctor());
        mDBDetailBean.setDepartment("" + mBean.getDepartment());
        mDBDetailBean.setChiefComplaint("" + mBean.getChiefComplaint());
        mDBDetailBean.setClinicalDiagnosis("" + mBean.getClinicalDiagnosis());
        mDBDetailBean.setCheckContent("" + mBean.getCheckContent());
        mDBDetailBean.setCheckDiagnosis("" + mBean.getCheckDiagnosis());
        mDBDetailBean.setBiopsy("" + mBean.getBiopsy());
        mDBDetailBean.setTest("" + mBean.getTest());
        mDBDetailBean.setCtology("" + mBean.getCtology());
        mDBDetailBean.setPathology("" + mBean.getPathology());
        mDBDetailBean.setAdvice("" + mBean.getAdvice());
        mDBDetailBean.setExaminingPhysician("" + mBean.getExaminingPhysician());

        return mDBDetailBean;
    }


}
