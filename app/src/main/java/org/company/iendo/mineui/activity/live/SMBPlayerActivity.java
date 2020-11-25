package org.company.iendo.mineui.activity.live;

import android.util.Log;
import android.view.WindowManager;
import com.vlc.lib.VlcVideoView;
import com.vlc.lib.listener.MediaListenerEvent;
import org.company.iendo.R;
import org.company.iendo.common.MyActivity;
/**
 * LoveLin
 * <p>
 * Describe   查看SMB的视频
 */
public class SMBPlayerActivity extends MyActivity {
//    public String path = "smb://cmeftproot:lzjdzh19861207@192.168.128.146/ImageData/Videos/2448/伏尔思20200810103559952.mp4";
    private VlcVideoView vlcVideoView;
    public static final String path = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";

    @Override
    protected int getLayoutId() {
        //设置沉浸式观影模式体验
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_smb_palyer;
    }

    @Override
    protected void initView() {
        vlcVideoView = findViewById(R.id.vlc_video_view);
        responseListener();

    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("url");
//        path = url;
        toast(url);
    }

    private void responseListener() {
        vlcVideoView.setMediaListenerEvent(new MediaListenerEvent() {
            @Override
            public void eventBuffing(int event, float buffing) {
                Log.e("path=====Start:=====", "我是当前播放的url======buffing======" + buffing);
                if (buffing > 3) {
                    Log.e("path=====Start:=====", "eventBuffing");

                }
//                if (buffing > 3 && isFirstLoading) {
//                    isPlayering = true;
//                    isFirstLoading = false;
//                    loading.release();
//                    loading.setVisibility(View.INVISIBLE);
//                }

            }

            @Override
            public void eventStop(boolean isPlayError) {
                Log.e("path=====Start:=====", "eventStop");

                if (isPlayError) {
                }
            }

            @Override
            public void eventError(int event, boolean show) {
                Log.e("path=====Start:=====", "eventError");

            }

            @Override
            public void eventPlay(boolean isPlaying) {
                Log.e("path=====Start:=====", "eventPlay");


            }

            @Override
            public void eventPlayInit(boolean openClose) {
                Log.e("path=====Start:=====", "openClose");

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

    }

    @Override
    protected void onStop() {
        super.onStop();
        //手动清空字幕
        vlcVideoView.setAddSlave(null);
        vlcVideoView.onStop();
    }

}
