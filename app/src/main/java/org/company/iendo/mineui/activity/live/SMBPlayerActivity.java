package org.company.iendo.mineui.activity.live;

import android.content.pm.ActivityInfo;
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
 * Describe   MainActivity跳转过来的，查看SMB的视频
 */
public class SMBPlayerActivity extends MyActivity {
    //public static final String path = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
    //public static final String path = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
//    public static final String path = "rtmp://58.200.131.2:1935/livetv/jxhd";
    public String path = "http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4";
    //    public String path = "smb://cmeftproot:lzjdzh19861207@192.168.128.96/ImageData/Videos/3771/祝期玲20200827172726951.mp4";
    //    public String path = "rtmp://58.200.131.2:1935/livetv/jxhd";
//smb://cmeftproot:lzjdzh19861207@192.168.128.146/ImageData/Videos/4027/220201116141454985.mp4
    private MyVlcVideoView player;
    private VlcVideoView vlcVideoView;
    private RelativeLayout mTopLayout;
    private ImageView mLockView;
    private AppCompatImageView mControlView;
    private ENDownloadView mLoadingView;
    private ImageView mTopBack;
    private TextView mTopChange;
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
    private FrameLayout ff_all;
    private RelativeLayout playout;
    private boolean isFirstin = true;
    private boolean isError = false;
    private TextView mTopTitle;

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
        playout = player.findViewById(R.id.activity_vlc_player_layout);
        ff_all = player.findViewById(R.id.ff_all);
        mLockView.setTag("unLock");
        path = getIntent().getStringExtra("url");
//        path = getIntent().getStringExtra("url");
        mTopTitle.setText("" + getIntent().getStringExtra("title"));
        path = getLiveConnectUrl();
    }

    @Override
    protected void initData() {
        responseListener();
    }

    private void responseListener() {
        mTopBack.setOnClickListener(this);
        mTopBack.setOnClickListener(this);
        mControlView.setOnClickListener(this);
        mLockView.setOnClickListener(this);
        player.setOnClickListener(this);
        playout.setOnClickListener(this);
        mAllRelat.setOnClickListener(this);
        mTopChange.setOnClickListener(this);
        ff_all.setOnClickListener(this);

        player.setOnSeekBarChangeListener(new MyVlcVideoView.onSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch() {
                getLoadingView();

            }

            @Override
            public void onStartTrackingTouch() {

            }

            @Override
            public void onChangeTrackingTouch(SeekBar seekBar, int progress, boolean fromUser) {
                //fromUser 用户滑动或者拖动就是true 其他时候false
                if (fromUser) {
                    currentPosition = vlcVideoView.getCurrentPosition();
                    currentProgress = mProgressView.getProgress();
                    onMyStart();
                }

            }
        });

        player.setOnLockStatueListener(new MyVlcVideoView.onLockStatueListener() {
            @Override
            public void onLockStatueChangeListener() {
                //mLoadingView   4隐藏  0显示
                //一开始是隐藏的
                Log.e("path=====Start:=====", "mLoadingView==type====" + mLoadingView.getVisibility());
                if (mLoadingView.getVisibility() == View.VISIBLE) { //显示
                    mControlView.setVisibility(View.INVISIBLE);
                } else {

                    if (!isError) {  //url错误的时候不响应点击事件
                        if (mLockView.getVisibility() == View.VISIBLE) {
                            lockScreenAll(true);
                        } else {
                            lockScreenAll(false);
                        }
                    }
                }

            }
        });
        vlcVideoView.setMediaListenerEvent(new MediaListenerEvent() {
            @Override
            public void eventBuffing(int event, float buffing) {
                Log.e("path=====Start:=====", "mLoadingView==buffing====" + buffing);

                if (buffing > 50) {
                    hindLoadingView();
                    mErrorView.setVisibility(View.INVISIBLE);
                    mControlView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void eventStop(boolean isPlayError) {
            }

            @Override
            public void eventError(int event, boolean show) {
                mErrorView.setVisibility(View.VISIBLE);
                isError = true;
                mBottomLayout.setVisibility(View.INVISIBLE);
                mControlView.setVisibility(View.INVISIBLE);
                mLockView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void eventPlay(boolean isPlaying) {
                String CurrentPosition = conversionTime(vlcVideoView.getCurrentPosition());
                String Duration = conversionTime(vlcVideoView.getDuration());
                mPlayTime.setText(conversionTime(vlcVideoView.getCurrentPosition()));
                mTotalTime.setText(conversionTime(vlcVideoView.getDuration()));
                mProgressView.setMax(vlcVideoView.getDuration());

            }

            @Override
            public void eventPlayInit(boolean openClose) {

            }

        });
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
        if (Lock) {   //当前是显示，马上要去隐藏
            mTopLayout.setVisibility(View.INVISIBLE);
            mBottomLayout.setVisibility(View.INVISIBLE);
            mControlView.setVisibility(View.INVISIBLE);
            mLockView.setVisibility(View.INVISIBLE);
        } else {      //显示
            mTopLayout.setVisibility(View.VISIBLE);
            mBottomLayout.setVisibility(View.VISIBLE);
            mControlView.setVisibility(View.VISIBLE);
            mLockView.setVisibility(View.VISIBLE);
            if (mLockView.getTag().equals("Lock")) {
                mTopLayout.setVisibility(View.INVISIBLE);
                mBottomLayout.setVisibility(View.INVISIBLE);
                mControlView.setVisibility(View.INVISIBLE);
            }

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


    private void getLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
        mControlView.setVisibility(View.INVISIBLE);
        mLoadingView.start();
    }

    private void hindLoadingView() {
        mLoadingView.setVisibility(View.INVISIBLE);
        mLoadingView.release();

    }

    /**
     * 开始直播
     *
     * @param
     */
    @Override
    public void onResume() {
        super.onResume();
        mErrorView.setVisibility(View.INVISIBLE);
        mControlView.setVisibility(View.INVISIBLE);
        getLoadingView();
        player.setVideoSource(path);
        Log.e("========root=====", "播放的==url==" + "" + path);
        player.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        player.pause();
        mControlView.setVisibility(View.VISIBLE);
    }

    public void onMyStart() {
        getLoadingView();
        mControlView.setImageResource(R.drawable.video_play_pause_ic);
        player.start(currentPosition, currentProgress);
    }


    public void onMyPause() {
        if (isFirstin) {
            isFirstin = false;
            mControlView.setImageResource(R.drawable.video_play_pause_ic);
        } else {
            mControlView.setImageResource(R.drawable.video_play_start_ic);
        }
        mControlView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
        player.pause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //手动清空字幕
        vlcVideoView.setAddSlave(null);
        vlcVideoView.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        vlcVideoView.onDestroy();
//        mHandler.removeCallbacksAndMessages(null);
//        mHandler = null;
    }
}
