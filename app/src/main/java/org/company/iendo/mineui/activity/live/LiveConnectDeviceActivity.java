package org.company.iendo.mineui.activity.live;

import android.content.pm.ActivityInfo;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vlc.lib.VlcVideoView;
import com.vlc.lib.listener.MediaListenerEvent;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.widget.MyConnectVlcVideoView;
import org.company.iendo.widget.MyVlcVideoView;
import org.videolan.libvlc.Media;

import java.io.File;

import moe.codeest.enviews.ENDownloadView;
import moe.codeest.enviews.ENPlayView;

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
    private TextView photos;
    private boolean isPlayering = false;   //视频是否播放的标识符


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
        photos = findViewById(R.id.photos);
        recordStart = findViewById(R.id.recordStart);
        change_ice = findViewById(R.id.change_ice);
        layout_top = findViewById(R.id.layout_top);
        error_text = findViewById(R.id.error_text);
        snapShot = findViewById(R.id.snapShot);
        start = findViewById(R.id.start);
        loading = findViewById(R.id.loading);
        back = findViewById(R.id.back);
        lock_screen.setTag("unLock");
    }

    private void responseListener() {
        setOnClickListener(R.id.start, R.id.player, R.id.lock_screen, R.id.change, R.id.back, R.id.change_ice, R.id.recordStart,
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
//                Log.e("path=====Start:=====", "我是当前播放的url======buffing======" + buffing);
                if (buffing > 50) {
                    isPlayering = true;
                    loading.release();
                    loading.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void eventStop(boolean isPlayError) {
                if (isPlayError) {
                    isPlayering = false;
                    loading.setVisibility(View.INVISIBLE);
                    error_text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void eventError(int event, boolean show) {
                isPlayering = false;
            }

            @Override
            public void eventPlay(boolean isPlaying) {

            }

            @Override
            public void eventPlayInit(boolean openClose) {
                start.setVisibility(View.INVISIBLE);
            }

        });

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
            case R.id.start:  //锁屏控制
                startLive(path);
                break;
            case R.id.player:  //锁屏控制
                if (lock_screen.getVisibility() == View.VISIBLE) {
                    lockScreen(false);
                } else {
                    lockScreen(true);
                }
                break;

            case R.id.lock_screen:
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
                toast("冻结");
                break;
            case R.id.recordStart:  //录像
                toast("录像");
                break;
            case R.id.snapShot:  //截图
                toast("截图");
                break;
        }
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



}
