package org.company.iendo.ui.pager;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import org.company.iendo.R;
import org.company.iendo.aop.SingleClick;
import org.company.iendo.http.glide.GlideApp;
import org.company.iendo.mineui.MainActivity;

import java.io.File;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/03/05
 *    desc   : 图片加载适配器
 */
public final class ImagePagerAdapter extends PagerAdapter
        implements View.OnClickListener {

    private final Activity mActivity;
    private final List<String> mData;

    public ImagePagerAdapter(Activity activity, List<String> data) {
        mActivity = activity;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView view = new PhotoView(mActivity);
        view.setOnClickListener(this);
//          加载远程图片
//        GlideApp.with(container.getContext())
//                .load(mData.get(position))
//                .into(view);
//            http格式下的显示图片
        Glide.with(container.getContext())
                .load(mData.get(position))
                .error(R.mipmap.icon_case_btn)
                .into(view);
        Log.e("Item","======mData.get(position)===="+mData.get(position));
        container.addView(view);


//        SMB协议下 加载本地图片
//        File file = new File(mData.get(position));
//        Log.e("Item","======file==path=="+mData.get(position));
//        Log.e("Item","======file===="+file.exists());
//        Glide.with(container.getContext())
//                .load(file)
//                .into(view);
//        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        // 单击图片退出当前的 Activity
        if (!mActivity.isFinishing()) {
            mActivity.finish();
        }
    }
}