package org.company.iendo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import org.company.iendo.R;
import org.company.iendo.aop.CheckNet;
import org.company.iendo.aop.DebugLog;
import org.company.iendo.common.MyActivity;
import org.company.iendo.other.IntentKey;
import org.company.iendo.ui.pager.ImagePagerAdapter;
import org.company.iendo.util.LogUtils;

import com.rd.PageIndicatorView;

import java.util.ArrayList;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/03/05
 * desc   : 查看大图
 */
public final class ImagePreviewActivity extends MyActivity implements ViewPager.OnPageChangeListener {

    private TextView mTitle;
    private ArrayList<String> imagesList;

    public static void start(Context context, String url) {
        ArrayList<String> images = new ArrayList<>(1);
        images.add(url);
        start(context, images);
    }

    public static void start(Context context, ArrayList<String> urls) {
        start(context, urls, 0);
    }

    @DebugLog
    public static void start(Context context, ArrayList<String> urls, int index) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra(IntentKey.IMAGE, urls);
        intent.putExtra(IntentKey.INDEX, index);
        context.startActivity(intent);
    }

    private ViewPager mViewPager;
    private PageIndicatorView mIndicatorView;

    @Override
    protected int getLayoutId() {
        return R.layout.image_preview_activity;
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_image_preview_pager);
        mTitle = findViewById(R.id.vp_title);
        mIndicatorView = findViewById(R.id.pv_image_preview_indicator);
        mIndicatorView.setViewPager(mViewPager);

    }

    @Override
    protected void initData() {
        imagesList = getStringArrayList(IntentKey.IMAGE);
        int index = getInt(IntentKey.INDEX);
        if (imagesList != null && imagesList.size() > 0) {
            mViewPager.setAdapter(new ImagePagerAdapter(this, imagesList));
            if (index != 0 && index <= imagesList.size()) {
                mViewPager.setCurrentItem(index);
            }
        } else {
            finish();
        }
        mViewPager.addOnPageChangeListener(this);

        //默认最后一条数据,设置标题
        String[] split = imagesList.get(imagesList.size() - 1).split("/");
        String s = split[split.length - 1];
        mTitle.setText("" + s);

        //Indicator图片过多，就隐藏
        if (imagesList.size() > 6) {
            toast("666666666666666666!");

            mIndicatorView.setVisibility(View.GONE);
        } else {
            toast("00000000000000000000!");

            mIndicatorView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        String[] split = imagesList.get(position).split("/");
        String s = split[split.length - 1];
        mTitle.setText("" + s);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 隐藏状态栏和导航栏
                .hideBar(BarHide.FLAG_HIDE_BAR);
    }

    @Override
    public boolean isStatusBarDarkFont() {
        return false;
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }


}