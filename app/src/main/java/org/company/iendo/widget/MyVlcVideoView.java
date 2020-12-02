package org.company.iendo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vlc.lib.VlcVideoView;

import org.company.iendo.R;

import java.io.File;
import java.util.Formatter;
import java.util.Locale;


/**
 * LoveLin
 * <p>
 * Describe
 */
public class MyVlcVideoView extends RelativeLayout implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private Context mContext;
    private RelativeLayout mRootLayout;
    private LinearLayout mVideoGestureLayout;
    private ImageView mVideoGestureImg;
    private TextView mVideoGestureText;
    private ViewGroup mBottomLayout;
    private TextView mPlayTime;
    private TextView mTotalTime;
    private SeekBar mProgressView;
    private VlcVideoView mVideoView;
    //    private ImageView mControlView;
//    private ImageView mLockView;
    //    private ViewGroup mTopLayout;
//    private TextView mTitleView;
//    private View mLeftView;
    private int currentPosition;
    private int currentProgress;
    private ImageView mLockView;


    public MyVlcVideoView(Context context) {
        super(context);
        initView(context);
    }

    public MyVlcVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyVlcVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    /**
     * {@link OnClickListener}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:   //后退
                mGoBackListener.onClickGoBack();
                break;
//
            case R.id.vlc_video_view:   //后退
                Log.e("path=====0000:=====", "eventBuffing");
                mOnLockStatueListener.onLockStatueChangeListener();
                break;


        }


    }


    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.vlc_videoview_layout, this);
//        mRootLayout = findViewById(R.id.root_layout_vlc);
        mVideoView = findViewById(R.id.vlc_video_view);
        FrameLayout ff_all = findViewById(R.id.ff_all);
        mBottomLayout = findViewById(R.id.ll_player_view_bottom);
        mLockView = findViewById(R.id.lock_screen);
        mPlayTime = findViewById(R.id.tv_player_view_play_time);
        mTotalTime = findViewById(R.id.tv_player_view_total_time);
        mProgressView = findViewById(R.id.sb_player_view_progress);


        mProgressView.setOnSeekBarChangeListener(this);
        mVideoView.setOnClickListener(this);
        ff_all.setOnClickListener(this);
        this.setOnClickListener(this);


    }

    /**
     * 当前播放进度
     */
    private int mCurrentProgress;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mPlayTime.setText(conversionTime(progress));
        } else {
            if (progress != 0) {
                // 记录当前播放进度
                mCurrentProgress = progress;
            } else {
                // 如果 Activity 返回到后台，progress 会等于 0，而 mVideoView.getDuration 会等于 -1
                // 所以要避免在这种情况下记录当前的播放进度，以便用户从后台返回到前台的时候恢复正确的播放进度
                if (mVideoView.getDuration() > 0) {
                    mCurrentProgress = progress;
                }
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        removeCallbacks(mRefreshRunnable);
    }

    /**
     * 面板隐藏间隔
     */
    private static final int CONTROLLER_TIME = 3000;

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        postDelayed(mRefreshRunnable, REFRESH_TIME);
        // 设置选择的播放进度
        setProgress(seekBar.getProgress());
    }

    /**
     * 设置视频播放进度
     */
    public void setProgress(int progress) {
        if (progress > mVideoView.getDuration()) {
            progress = mVideoView.getDuration();
        }
        if (Math.abs(progress - mVideoView.getCurrentPosition()) > 1000) {
            mVideoView.seekTo(progress);
            mProgressView.setProgress(progress);
        }
    }

    /**
     * 显示面板
     */
    private boolean mControllerShow = true;


    /**
     * 刷新任务
     */
    private Runnable mRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            int progress = mVideoView.getCurrentPosition();
            // 这里优化了播放的秒数计算，将 800 毫秒估算成 1 秒
            if (progress + 1000 < mVideoView.getDuration()) {
                progress = Math.round(progress / 1000f) * 1000;
            }
            mPlayTime.setText(conversionTime(progress));
            mProgressView.setProgress(progress);
            mProgressView.setSecondaryProgress((int) (mVideoView.getBufferPercentage() / 100f * mVideoView.getDuration()));
            if (mVideoView.isPlaying()) {
                if (!mLockMode && mBottomLayout.getVisibility() == GONE) {
                    mBottomLayout.setVisibility(VISIBLE);
                }
            } else {
                if (mBottomLayout.getVisibility() == VISIBLE) {
                    mBottomLayout.setVisibility(GONE);
                }
            }
            postDelayed(this, REFRESH_TIME);
        }
    };
    /**
     * 刷新间隔
     */
    private static final int REFRESH_TIME = 1000;

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        // 这里解释一下 onWindowVisibilityChanged 方法调用的时机
        // 从前台返回到后台：先调用 onWindowVisibilityChanged(View.INVISIBLE) 后调用 onWindowVisibilityChanged(View.GONE)
        // 从后台返回到前台：先调用 onWindowVisibilityChanged(View.INVISIBLE) 后调用 onWindowVisibilityChanged(View.VISIBLE)
        super.onWindowVisibilityChanged(visibility);
        // 这里修复了 Activity 从后台返回到前台时视频重播的问题
        if (visibility == VISIBLE) {
            mVideoView.seekTo(mCurrentProgress);
            mProgressView.setProgress(mCurrentProgress);
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
     * 设置视频标题
     */
    public void setVideoTitle(CharSequence title) {
//        mTitleView.setText(title);
    }

    /**
     * 设置视频源
     */
    public void setVideoSource(File file) {
//        mVideoView.setVideoPath(file.getPath());
    }

    private String url = "http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4";

    public void setVideoSource(String url) {
        this.url = url;
        mVideoView.setPath(url);
        mVideoView.startPlay();
//        postDelayed(mRefreshRunnable, REFRESH_TIME);

//        mVideoView.setVideoURI(Uri.parse(url));
    }

    /**
     * 开始播放 默认开始
     */
    public void start() {
        Log.e("TAG===", "start");
        Log.e("TAG===", "url===" + url);
        Log.e("TAG===", "currentPosition===" + currentPosition);   //currentPosition===4651
        Log.e("TAG===", "mCurrentProgress===" + mCurrentProgress);  //mCurrentProgress===5000
//        mVideoView.setPath(url);
//        String s = conversionTime(mVideoView.getCurrentPosition());
        mVideoView.start();
        mVideoView.seekTo(currentPosition);
//        setProgress(currentProgress);
        mProgressView.setProgress(currentProgress);
        postDelayed(mRefreshRunnable, REFRESH_TIME);

    }

    /**
     * 开始播放 暂停之后
     */
    public void start(int currentPosition, int currentProgress) {
        Log.e("TAG===", "start");
        Log.e("TAG===", "url===" + url);
        Log.e("TAG===", "currentPosition===" + currentPosition);   //currentPosition===4651
        Log.e("TAG===", "mCurrentProgress===" + mCurrentProgress);  //mCurrentProgress===5000
//        mVideoView.setPath(url);
//        String s = conversionTime(mVideoView.getCurrentPosition());
        mVideoView.start();
        mVideoView.seekTo(currentPosition);
//        setProgress(currentProgress);
        mProgressView.setProgress(currentProgress);
        postDelayed(mRefreshRunnable, REFRESH_TIME);

    }

    /**
     * 暂停播放
     */
    public void pause() {
        mVideoView.pause();
        // 延迟隐藏控制面板

    }

    /**
     * 锁定面板
     */
    private boolean mLockMode;


    public int getViewStatue() {
        return mBottomLayout.getVisibility();
    }


    /**
     * 是否正在播放
     */
    public boolean isPlaying() {
        return mVideoView.isPlaying();
    }


    private onLockStatueListener mOnLockStatueListener;

    public interface onLockStatueListener {
        /**
         * statue  true:unlock   flase:lock
         *
         * @param
         */
        void onLockStatueChangeListener();

    }

    public void setOnLockStatueListener(onLockStatueListener listener) {
        mOnLockStatueListener = listener;

    }

    /**
     * 返回监听器
     */
    private onGoBackListener mGoBackListener;

    /**
     * 设置返回监听
     */
    public void setOnGoBackListener(onGoBackListener listener) {
        mGoBackListener = listener;
//        mLeftView.setVisibility(mGoBackListener != null ? VISIBLE : INVISIBLE);
    }

    /**
     * 点击返回监听器
     */
    public interface onGoBackListener {

        /**
         * 点击了返回按钮
         */
        void onClickGoBack();
    }

}