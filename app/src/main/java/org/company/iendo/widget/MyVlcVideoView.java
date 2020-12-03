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
    private ViewGroup mBottomLayout;
    private TextView mPlayTime;
    private TextView mTotalTime;
    private SeekBar mProgressView;
    private VlcVideoView mVideoView;
    private int currentPosition;
    private int currentProgress;
    private ImageView mLockView;
    /**
     * 当前播放进度
     */
    private int mCurrentProgress;

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
            case R.id.vlc_video_view:   //点击全屏显示隐藏相对于布局
                mOnLockStatueListener.onLockStatueChangeListener();
                break;
        }
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.vlc_videoview_layout, this);
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


    //当拖动条发生变化时调用该方法
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mOnSeekBarChangeListener.onChangeTrackingTouch(seekBar, progress, fromUser);

        //播放的时候一直走001

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

    //当用户开始滑动滑块时调用该方法（即按下鼠调用一次）
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mOnSeekBarChangeListener.onStartTrackingTouch();
        removeCallbacks(mRefreshRunnable);
    }


    //当用户结束对滑块滑动时,调用该方法（即松开鼠标）
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mOnSeekBarChangeListener.onStopTrackingTouch();

        //拖动之后走003 在到001
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
                if (mBottomLayout.getVisibility() == GONE) {
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


//    private String url = "http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4";

    public void setVideoSource(String url) {
//        this.url = url;
        mVideoView.setPath(url);
        mVideoView.startPlay();

    }

    /**
     * 开始播放 默认开始
     */
    public void start() {
        mVideoView.start();
        mVideoView.seekTo(currentPosition);
        mProgressView.setProgress(currentProgress);
        postDelayed(mRefreshRunnable, REFRESH_TIME);

    }

    /**
     * 开始播放 暂停之后
     */
    public void start(int currentPosition, int currentProgress) {
        mVideoView.start();
        mVideoView.seekTo(currentPosition);
        mProgressView.setProgress(currentProgress);
        postDelayed(mRefreshRunnable, REFRESH_TIME);

    }

    /**
     * 暂停播放
     */
    public void pause() {
        mVideoView.pause();
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
     * progress 滑动的监听
     */

    private onSeekBarChangeListener mOnSeekBarChangeListener;

    public interface onSeekBarChangeListener {
        /**
         * statue  true:unlock   flase:lock
         *
         * @param
         */
        void onStopTrackingTouch();

        void onStartTrackingTouch();

        void onChangeTrackingTouch(SeekBar seekBar, int progress, boolean fromUser);


    }

    public void setOnSeekBarChangeListener(onSeekBarChangeListener listener) {
        mOnSeekBarChangeListener = listener;

    }


}