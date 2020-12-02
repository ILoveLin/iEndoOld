package org.company.iendo.mineui.activity.live;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.vlc.lib.VlcVideoView;
import com.vlc.lib.listener.MediaListenerEvent;

import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
import org.company.iendo.widget.MyVlcVideoView;

import java.util.Formatter;
import java.util.Locale;

import moe.codeest.enviews.ENDownloadView;

/**
 * LoveLin
 * <p>
 * Describe   查看SMB的视频
 */
public class SMBPlayerActivity extends MyActivity {
    // public static final String path = "http://121.18.168.149/cache.ott.ystenlive.itv.cmvideo.cn:80/000000001000/1000000001000010606/1.m3u8?stbId=005301FF001589101611549359B92C46&channel-id=ystenlive&Contentid=1000000001000010606&mos=jbjhhzstsl&livemode=1&version=1.0&owaccmark=1000000001000010606&owchid=ystenlive&owsid=5474771579530255373&AuthInfo=2TOfGIahP4HrGWrHbpJXVOhAZZf%2B%2BRvFCOimr7PCGr%2Bu3lLj0NrV6tPDBIsVEpn3QZdNn969VxaznG4qedKIxPvWqo6nkyvxK0SnJLSEP%2FF4Wxm5gCchMH9VO%2BhWyofF";
    //public static final String path = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
    //public static final String path = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
//    public static final String path = "rtmp://58.200.131.2:1935/livetv/jxhd";
    public String path = "http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4";
    //    public String path = "smb://cmeftproot:lzjdzh19861207@192.168.128.146/ImageData/Videos/2448/伏尔思20200810103559952.mp4";
    //        public String path = "smb://cmeftproot:lzjdzh19861207@192.168.128.146/ImageData/Videos/4027/220201116141454985.mp4";
//    public String path = "rtmp://58.200.131.2:1935/livetv/jxhd";
//smb://cmeftproot:lzjdzh19861207@192.168.128.146/ImageData/Videos/4027/220201116141454985.mp4
    private MyVlcVideoView player;
    private VlcVideoView vlcVideoView;
    private TextView mTitleView;
    private View mLeftView;
    private RelativeLayout mTopLayout;
    private ImageView mLockView;
    private AppCompatImageView mControlView;
    private ENDownloadView mLoadingView;
    private ImageView mTopBack;
    private TextView mTopTitle;
    private TextView mTopChange;
    private RelativeLayout mAll;
    private LinearLayout mBottomLayout;
    private AppCompatTextView mTotalTime;
    private AppCompatTextView mPlayTime;
    private SeekBar mProgressView;
    private int currentPosition;
    private int currentProgress;
    private RelativeLayout mAllRelat;
    public boolean isFullscreen = true;
    private TextView mErrorView;
    private boolean locked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置沉浸式观影模式体验
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vlc_smb_player;
    }

    @Override
    protected void initView() {
        player = findViewById(R.id.player);
        mAllRelat = findViewById(R.id.activity_vlc_player);
        vlcVideoView = player.findViewById(R.id.vlc_video_view);

        //控制按钮
        mLockView = player.findViewById(R.id.lock_screen);
        mControlView = player.findViewById(R.id.iv_player_view_control);
        mLoadingView = player.findViewById(R.id.loading);
        mErrorView = player.findViewById(R.id.error_text);

        //titlebar
        mTopLayout = player.findViewById(R.id.layout_top);
        mTopBack = player.findViewById(R.id.top_back);
        mTopTitle = player.findViewById(R.id.top_title);
        mTopChange = player.findViewById(R.id.top_change);
        mBottomLayout = player.findViewById(R.id.ll_player_view_bottom);
        mPlayTime = player.findViewById(R.id.tv_player_view_play_time);
        mTotalTime = player.findViewById(R.id.tv_player_view_total_time);
        mProgressView = player.findViewById(R.id.sb_player_view_progress);
        RelativeLayout playout = player.findViewById(R.id.activity_vlc_player_layout);
        FrameLayout ff_all = player.findViewById(R.id.ff_all);

        mLockView.setTag("unLock");

        mTopBack.setOnClickListener(this);
        mTopBack.setOnClickListener(this);
        mControlView.setOnClickListener(this);
        mLockView.setOnClickListener(this);

        player.setOnClickListener(this);
        playout.setOnClickListener(this);
        mAllRelat.setOnClickListener(this);
        mTopChange.setOnClickListener(this);
        ff_all.setOnClickListener(this);
        player.setOnLockStatueListener(new MyVlcVideoView.onLockStatueListener() {
            @Override
            public void onLockStatueChangeListener() {
                if (mLockView.getVisibility() == View.VISIBLE) {
                    lockScreenAll(true);
                    Log.e("path=====01:=====", "mLockView");

                } else {
                    lockScreenAll(false);
                    Log.e("path=====02:=====", "mLockView");

                }
            }
        });
        vlcVideoView.setMediaListenerEvent(new MediaListenerEvent() {
            @Override
            public void eventBuffing(int event, float buffing) {
                Log.e("path=====Start:=====", "我是当前播放的url======buffing======" + buffing);
                if (buffing > 3) {
//                    isPlayering = true;
                    mLoadingView.release();
                    mErrorView.setVisibility(View.INVISIBLE);
                    mLoadingView.setVisibility(View.INVISIBLE);
                }


                if (buffing>50){
                    mControlView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void eventStop(boolean isPlayError) {
                Log.e("path=====Start:=====", "eventStop");
            }

            @Override
            public void eventError(int event, boolean show) {
                Log.e("path=====Start:=====", "eventError");
                mErrorView.setVisibility(View.VISIBLE);

            }

            @Override
            public void eventPlay(boolean isPlaying) {
                Log.e("path=====Start:=====", "eventPlay");
                String CurrentPosition = conversionTime(vlcVideoView.getCurrentPosition());
                String Duration = conversionTime(vlcVideoView.getDuration());
                mPlayTime.setText(conversionTime(vlcVideoView.getCurrentPosition()));
                mTotalTime.setText(conversionTime(vlcVideoView.getDuration()));
                mProgressView.setMax(vlcVideoView.getDuration());

            }

            @Override
            public void eventPlayInit(boolean openClose) {
                Log.e("path=====Start:=====", "openClose");

            }

        });
    }

    @Override
    protected void initData() {

    }


    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:   //后退
                finish();
                break;
            case R.id.top_change:   //是否全屏
                Log.e("path=====01:=====", "是否全屏");
                isFullscreen = !isFullscreen;
                if (isFullscreen) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); //横屏动态转换
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏动态转换
                }
                break;

            case R.id.iv_player_view_control:   //点击播放暂停
                if (mControlView.getVisibility() == View.VISIBLE) {
                    if (vlcVideoView.isPlaying()) {
                        onMyPause();
                        currentPosition = vlcVideoView.getCurrentPosition();
                        currentProgress = mProgressView.getProgress();
                    } else {
                        onMyStart();
                    }
                }
                break;
            case R.id.lock_screen:   //小锁
                if (mLockView.getTag().equals("unLock")) {
                    mLockView.setTag("Lock");
                    mLockView.setBackgroundResource(R.drawable.video_lock_close_ic);
                    mLockView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    lockScreen(true);

                } else {
                    mLockView.setTag("unLock");
                    mLockView.setBackgroundResource(R.drawable.video_lock_open_ic);
                    mLockView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    lockScreen(false);

                }

        }


    }


    public void lockScreen(Boolean Lock) {
        if (Lock) {   //当前是显示，所以隐藏
            mTopLayout.setVisibility(View.INVISIBLE);
            mBottomLayout.setVisibility(View.INVISIBLE);
            mControlView.setVisibility(View.INVISIBLE);
            mLockView.setVisibility(View.VISIBLE);
            mLockView.setImageResource(R.drawable.video_lock_close_ic);

        } else {      //显示
            mTopLayout.setVisibility(View.VISIBLE);
            mBottomLayout.setVisibility(View.VISIBLE);
            mControlView.setVisibility(View.VISIBLE);
            mLockView.setVisibility(View.VISIBLE);
            mLockView.setImageResource(R.drawable.video_lock_open_ic);

        }
    }

    public void lockScreenAll(Boolean Lock) {
        if (Lock) {   //当前是显示，所以隐藏
            mTopLayout.setVisibility(View.INVISIBLE);
            mBottomLayout.setVisibility(View.INVISIBLE);
            mControlView.setVisibility(View.INVISIBLE);
            mLockView.setVisibility(View.INVISIBLE);

        } else {      //显示
            mTopLayout.setVisibility(View.VISIBLE);
            mBottomLayout.setVisibility(View.VISIBLE);
            if(mLockView.getTag().equals("unLock")){
                mControlView.setVisibility(View.VISIBLE);
            }else {
                mControlView.setVisibility(View.INVISIBLE);

            }
            mLockView.setVisibility(View.VISIBLE);

        }


    }

    /**
     * 时间转换
     */
    public static String conversionTime(int time) {
        Formatter formatter = new Formatter(Locale.getDefault());
        // 总秒数
        int totalSeconds = time / 1000;
        // 小时数
        int hours = totalSeconds / 3600;
        // 分钟数
        int minutes = (totalSeconds / 60) % 60;
        // 秒数
        int seconds = totalSeconds % 60;
        if (hours > 0) {
            return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return formatter.format("%02d:%02d", minutes, seconds).toString();
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
    }


    @Override
    public void onResume() {
        super.onResume();
//        startLive(path);
        mErrorView.setVisibility(View.INVISIBLE);
        mControlView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.start();
        player.setVideoSource(path);
        player.start();
    }
    @Override
    public void onPause() {
        super.onPause();
        player.pause();
        mControlView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
        mLoadingView.release();
//        vlcVideoView.pause();

    }
    public void onMyStart() {
        mControlView.setImageResource(R.drawable.video_play_pause_ic);
        player.start(currentPosition, currentProgress);
//        mControlView.setVisibility(View.INVISIBLE);

    }

    private boolean isFirstin = true;

    public void onMyPause() {
        if (isFirstin) {
            isFirstin = false;
            mControlView.setImageResource(R.drawable.video_play_pause_ic);

        } else {
            mControlView.setImageResource(R.drawable.video_play_start_ic);

        }
        player.pause();
    }



    @Override
    protected void onStop() {
        super.onStop();
        //手动清空字幕
        vlcVideoView.setAddSlave(null);
        vlcVideoView.onStop();
    }


}
