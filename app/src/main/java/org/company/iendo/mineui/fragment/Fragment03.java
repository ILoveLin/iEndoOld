package org.company.iendo.mineui.fragment;


import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.common.MyFragment;
import org.company.iendo.mineui.MainActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.activity.casemsg.inter.CaseOperatorAction;
import org.company.iendo.mineui.fragment.adapter.PictureAdapter;
import org.company.iendo.ui.activity.ImagePreviewActivity;
import org.company.iendo.widget.HintLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 第三个fragment
 */


public class Fragment03 extends MyFragment<MainActivity> implements StatusAction, CaseOperatorAction, BaseAdapter.OnItemClickListener {
    private HintLayout mHintLayout;
    private CaseDetailMsgActivity mActivity;
    private WrapRecyclerView mRecyclerView;
    private PictureAdapter mAdapter;

    public Fragment03(CaseDetailMsgActivity Activity) {
        this.mActivity = Activity;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_03;
    }

    @Override
    protected void initView() {
        mHintLayout = findViewById(R.id.hl_status_hint);
        mRecyclerView = findViewById(R.id.recycleview_case03);
        mAdapter = new PictureAdapter(getActivity());
        mActivity.setCaseOperatorAction(this);
        mAdapter.setOnItemClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        /**
         * setAdapter之前设置点击监听,不然报错
         */
        mAdapter.setData(analogData());

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {

    }

    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = mAdapter.getItemCount(); i < mAdapter.getItemCount() + 20; i++) {
            data.add("我是第" + i + "条目");
        }
        return data;
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    public void onResume() {
        super.onResume();
        Log.e("TAG", "fragment03");
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    /**
     * itemClick
     *
     * @param recyclerView RecyclerView 对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast(position);
        ArrayList<String> images = new ArrayList<>();
//        Environment.getExternalStorageDirectory()
//    /storage/emulated/0/Image/4027    1604308388100.jpg     1604308389673.jpg  1604308398878.jpg

        String s = Environment.getExternalStorageDirectory() + "/Image/4027/1604308398878.jpg";
        String s1 = Environment.getExternalStorageDirectory() + "/Image/4027/1604308388100.jpg";
        String s2 = Environment.getExternalStorageDirectory() + "/Image/4027/1604308389673.jpg";
        String s3 = Environment.getExternalStorageDirectory() + "/Image/4027/1604308398878.jpg";

        images.add("https://www.baidu.com/img/bd_logo.png");
        images.add(s);
        images.add(s1);
        images.add(s2);
        images.add(s3);
        images.add(s);
        ImagePreviewActivity.start(getAttachActivity(), images, images.size() - 1);
    }

    @Override
    public void onLive() {

    }

    @Override
    public void onPrint() {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDownload() {

    }

    @Override
    public void onEdit() {

    }


}
