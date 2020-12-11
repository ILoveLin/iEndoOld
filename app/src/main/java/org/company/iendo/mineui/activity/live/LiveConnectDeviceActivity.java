package org.company.iendo.mineui.activity.live;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.vlc.lib.VlcVideoView;
import com.vlc.lib.listener.MediaListenerEvent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.bean.LiveConnectIDBean;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.util.LogUtils;
import org.company.iendo.widget.MyConnectVlcVideoView;

import java.lang.reflect.Type;

import moe.codeest.enviews.ENDownloadView;
import moe.codeest.enviews.ENPlayView;
import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe 链接设备--直播界面
 */
public class LiveConnectDeviceActivity extends MyActivity {
    public String path = "rtmp://58.200.131.2:1935/livetv/jxhd";
    private VlcVideoView vlcVideoView;
    private TextView recordStart;
    private TextView change_ice;
    private TextView change;
    private LinearLayout layout_top;
    private ImageView lock_screen;
    private TextView error_text;
    private ENPlayView start;
    private ENDownloadView loading;
    private boolean isFirstLoading = true;
    public boolean isFullscreen = true;
    private boolean isStarting = true;
    //数据源
    private TextView snapShot;
    private MyConnectVlcVideoView player;
    private ImageView back;
    private String url01;
    private String url02;
    private boolean isPlayering = false;   //视频是否播放的标识符
    private String id;

    private boolean isLock = false;    // buffing 里面只走一次的开关
    public static final int EVENT_Buffing = 11;
    public static final int EVENT_Stop = 22;
    public static final int EVENT_Init = 33;
    public static final int EVENT_Error = 44;
    private String currentDeviceID;
    //    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case EVENT_Buffing:
//                    isPlayering = true;
//                    loading.release();
//                    loading.setVisibility(View.INVISIBLE);
//                    break;
//                case EVENT_Stop:
//                    break;
//                case EVENT_Init:
//                    isLock = true;
//                    break;
//                case EVENT_Error:
//                    break;
//            }
//        }
//    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_connect_device_live;
    }

    @Override
    protected void initView() {
        player = findViewById(R.id.player);
        vlcVideoView = findViewById(R.id.vlc_video_view);
        change = findViewById(R.id.change);
        lock_screen = findViewById(R.id.lock_screen);
        recordStart = findViewById(R.id.recordStart);
        change_ice = findViewById(R.id.change_ice);
        layout_top = findViewById(R.id.layout_top);
        error_text = findViewById(R.id.error_text);
        snapShot = findViewById(R.id.snapShot);
//        start = findViewById(R.id.start);
        loading = findViewById(R.id.loading);
        back = findViewById(R.id.back);
        lock_screen.setTag("unLock");
        id = getIntent().getStringExtra("ID");
        path = getLiveConnectUrl();
        LogUtils.e("path====" + path);
        setBGErrorUrl(false);

    }

    private void responseListener() {
        setOnClickListener(R.id.player, R.id.lock_screen, R.id.change, R.id.back, R.id.change_ice, R.id.recordStart,
                R.id.snapShot);
        VlcVideoView vlc_video_view = vlcVideoView.findViewById(R.id.vlc_video_view);
        vlc_video_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //控制功能按钮的显示和隐藏
                if (lock_screen.getVisibility() == View.VISIBLE) {
                    lockScreen(true);
                } else {
                    lockScreen(false);
                }
            }
        });

        vlcVideoView.setMediaListenerEvent(new MediaListenerEvent() {
            @Override
            public void eventBuffing(int event, float buffing) {
                LogUtils.e("我是当前播放的url====" + path);
                LogUtils.e("TAG====00==buffing===" + buffing);
                if (isLock) {
                    LogUtils.e("TAG===01===buffing===" + buffing);
                    if (buffing > 3) {
                        LogUtils.e("TAG===02===buffing===" + buffing);
                        isPlayering = true;
                        loading.release();
                        loading.setVisibility(View.INVISIBLE);
                        isLock = false;

                    }
                }


            }

            @Override
            public void eventStop(boolean isPlayError) {
                LogUtils.e("event======Stop===" + isPlayError);
                if (isPlayError) {
                    isPlayering = false;
                    loading.setVisibility(View.INVISIBLE);
                    error_text.setVisibility(View.VISIBLE);
//                    start.setVisibility(View.VISIBLE);
                    //冻结，录像，截图 隐藏
                    setBGErrorUrl(true);

                }
            }

            @Override
            public void eventError(int event, boolean show) {
                LogUtils.e("event======error===" + show);

                isPlayering = false;
            }

            @Override
            public void eventPlay(boolean isPlaying) {
                LogUtils.e("event======play===" + isPlaying);

            }

            //openClose true加载视频中  false回到初始化  比如显示封面图
            @Override
            public void eventPlayInit(boolean openClose) {
                LogUtils.e("event======init===" + openClose);
                if (openClose) {
                    isLock = true;

                } else {
//                    start.setVisibility(View.INVISIBLE);
                }


            }

        });
        //获取当前机器操作对象的id
        sendGetDeviceUserIdRequest();
    }


    private void setBGErrorUrl(boolean b) {
        if (b) {
            change_ice.setVisibility(View.INVISIBLE);
            recordStart.setVisibility(View.INVISIBLE);
            snapShot.setVisibility(View.INVISIBLE);
        } else {
            change_ice.setVisibility(View.VISIBLE);
            recordStart.setVisibility(View.VISIBLE);
            snapShot.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {
        responseListener();

    }

    public void lockScreen(Boolean Lock) {
        if (Lock) {   //当前是显示，所以隐藏
            layout_top.setVisibility(View.INVISIBLE);
            lock_screen.setVisibility(View.INVISIBLE);
        } else {      //隐藏
            layout_top.setVisibility(View.VISIBLE);
            lock_screen.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.start:  //开始播放
//                startLive(path);
//                break;
            case R.id.player:  //锁屏控制
                if (lock_screen.getVisibility() == View.VISIBLE) {
                    lockScreen(false);
                } else {
                    lockScreen(true);
                }
                break;

            case R.id.lock_screen:
                LogUtils.e("url====" + path);
                if (lock_screen.getTag().equals("unLock")) {
                    lock_screen.setTag("Lock");
                    lock_screen.setBackgroundResource(R.drawable.video_lock_close_ic);
                    lock_screen.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    layout_top.setVisibility(View.INVISIBLE);
                } else {
                    lock_screen.setTag("unLock");
                    lock_screen.setBackgroundResource(R.drawable.video_lock_open_ic);
                    lock_screen.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    layout_top.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.change:       //全屏
                LogUtils.e("path====" + path);
                isFullscreen = !isFullscreen;
                if (isFullscreen) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); //横屏动态转换
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏动态转换
                }
                break;
            case R.id.back:
                vlcVideoView.setAddSlave(null);
                vlcVideoView.onStop();
                finish();
                break;
            case R.id.change_ice:   //冻结
                if (id.equals(currentDeviceID+"")) {
                    if (isStarting && vlcVideoView.isPrepare()) {
                        sendRequest("frozenImage");
                        setIceTextColor(getResources().getColor(R.color.colorAccent), "冻结中！", false);
                    } else {
                        sendRequest("endfrozenImage");
                        setIceTextColor(getResources().getColor(R.color.white), "冻结", true);
                    }
                } else {
                    toast("当前病人与一体机选中病人不一致");
                }
                break;
            case R.id.recordStart:  //录像
                if (id.equals(currentDeviceID+"")) {
                    if (isStarting && vlcVideoView.isPrepare()) {
                        sendRequest("startCapture");
                        setTextColor(getResources().getColor(R.color.colorAccent), "录像中...", false);
                    } else {
                        sendRequest("endCapture");
                        setTextColor(getResources().getColor(R.color.white), "录像", true);
                    }
                } else {
                    toast("当前病人与一体机选中病人不一致");
                }

                break;
            case R.id.snapShot:  //截图
                if (id.equals(currentDeviceID+"")) {
                    sendRequest("captureImage");
                } else {
                    toast("当前病人与一体机选中病人不一致");
                }

                break;
        }
    }
//    http://ip:port/add_command.aspx?Name=&Param=
//    Name:
//    录像
//    startCapture、endCapture
//            采图
//    captureImage
//            冻结
//    frozenImage、endfrozenImage
//
//    Param: 病例ID

    private void sendGetDeviceUserIdRequest() {
        showDialog();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_Live_Connect_GetID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                        LogUtils.e("path==get==onError===" + e);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideDialog();
                        LogUtils.e("path==get==response===" + response);
                        Type type = new TypeToken<LiveConnectIDBean>() {
                        }.getType();
                        LiveConnectIDBean mBean = mGson.fromJson(response, type);
                        if (mBean.getDs().size() == 0) {
                            currentDeviceID = mBean.getDs().get(0).getParam();
                            LogUtils.e("path==get==response==currentDeviceID=" + currentDeviceID);

                        }

                    }
                });
    }

    /**
     * 录像请求
     *
     * @param type 录像
     *             startCapture、endCapture
     *             采图
     *             captureImage
     *             冻结
     *             frozenImage、endfrozenImage
     */
    private void sendRequest(String type) {

        LogUtils.e("path===path===" + getCurrentHost() + HttpConstant.CaseManager_Live_Connect + "===name===" + type + "===Param===" + id);
        LogUtils.e("path===url===" + getCurrentHost() + HttpConstant.CaseManager_Live_Connect);
        LogUtils.e("path===Name===" + type);
        LogUtils.e("path===Param===" + id);
        LogUtils.e("" + getCurrentHost() + HttpConstant.CaseManager_Live_Connect);

        showDialog();
        OkHttpUtils.post()
                .url(getCurrentHost() + HttpConstant.CaseManager_Live_Connect)
                .addParams("Name", type)
                .addParams("Param", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideDialog();
                        LogUtils.e("path===onError===" + e);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideDialog();
                        LogUtils.e("path===onResponse===" + response);


                    }
                });
    }


    /**
     * 开始直播
     *
     * @param path
     */
    private void startLive(String path) {
        vlcVideoView.setPath(path);
        vlcVideoView.startPlay();
        error_text.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        loading.start();
    }


    @Override
    public void onResume() {
        super.onResume();
        startLive(path);
    }

    @Override
    public void onPause() {
        super.onPause();
        vlcVideoView.pause();
        loading.setVisibility(View.INVISIBLE);
        loading.release();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //手动清空字幕
        vlcVideoView.setAddSlave(null);
        vlcVideoView.onStop();
        loading.setVisibility(View.INVISIBLE);
        loading.release();
    }

    public void setTextColor(int color, String message, boolean isStarting) {
        recordStart.setText(message);
        recordStart.setTextColor(color);
        this.isStarting = isStarting;
    }

    public void setIceTextColor(int color, String message, boolean isStarting) {
        change_ice.setText(message);
        change_ice.setTextColor(color);
        this.isStarting = isStarting;
    }

}
