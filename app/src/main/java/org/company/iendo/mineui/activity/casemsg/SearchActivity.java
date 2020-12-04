package org.company.iendo.mineui.activity.casemsg;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hjq.widget.view.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.company.iendo.R;
import org.company.iendo.action.StatusAction;
import org.company.iendo.bean.CaseManagerListBean;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.casemsg.adapter.SearchAdapter;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.anim.EasyTransition;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe搜索界面
 */
public class SearchActivity extends MyActivity implements StatusAction, BaseAdapter.OnItemClickListener {
    private LinearLayout mTitleBar;
    private LinearLayout linear_all;
    private TextView mBack;
    private HintLayout mHintLayout;
    private ClearEditText mCetSearch;
    private SearchAdapter mAdapter;
    private WrapRecyclerView mRecyclerView;
    public List<CaseManagerListBean.DsDTO> mList;
    private String endoType;
    private String tag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_manage_search;
    }

    @Override
    protected void initView() {
        mTitleBar = findViewById(R.id.linear_sheare);
        mCetSearch = findViewById(R.id.cet_user_search);
        mBack = findViewById(R.id.tv_back);
        mHintLayout = findViewById(R.id.hl_status_hint);
        mRecyclerView = findViewById(R.id.rv_status_caselist);
        int statusBarHeight = getStatusBarHeight();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = statusBarHeight + 23;
        mTitleBar.setLayoutParams(params);
        setOnClickListener(R.id.tv_back, R.id.iv_user_search);
        EasyTransition.enter(SearchActivity.this);
        mAdapter = new SearchAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAnimation(null);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, 1, R.drawable.shape_divideritem_decoration));
        endoType = (String) SharePreferenceUtil.get(SearchActivity.this, SharePreferenceUtil.Current_Case_Num, "3");

    }

    @Override
    protected void initData() {
        tag = mCetSearch.getText().toString().trim();
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast("第" + position + "条目被点击了");
        LogUtils.e("=TAG=hy=position==" + mAdapter.getItem(position).toString());
        Intent intent = new Intent(SearchActivity.this, CaseDetailMsgActivity.class);
        intent.putExtra("ID", mAdapter.getItem(position).getID());
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sendRequest();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                EasyTransition.exit(SearchActivity.this);
                break;
            case R.id.iv_user_search:   //本地搜索数据
                tag = mCetSearch.getText().toString().trim();
                if ("".startsWith(tag)) {
                    toast("请输入搜索关键字");
                } else {
                    sendRequest();
                }
                break;
        }
    }

    /**
     * 发送请求
     *
     * @param
     */
    private void sendRequest() {
        tag = mCetSearch.getText().toString().trim();
        LogUtils.e("=TAG=hy=onError==" + endoType);
        showLoading();
        OkHttpUtils.get()
                .url(getCurrentHost() + HttpConstant.CaseManager_List)
                .addParams("endoType", endoType)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showComplete();

                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response, int id) {
                        showComplete();
                        LogUtils.e("=TAG=hy=onSucceed==" + response.toString());
                        if ("0".equals(response)) {
                            toast("用户名不存在或者参数为空");
                        } else if ("".equals(tag)) {

                        } else {
                            Type type = new TypeToken<CaseManagerListBean>() {
                            }.getType();
                            CaseManagerListBean mBean = mGson.fromJson(response, type);
                            mList = mBean.getDs();
                            Stream<CaseManagerListBean.DsDTO> stream = mList.stream();
                            List<CaseManagerListBean.DsDTO> mDataList = stream
                                    .filter(bean -> bean.getName().startsWith(tag)).collect(Collectors.toList());

                            if (mDataList.size() == 0) {
                                showEmpty();
                            } else {
                                showComplete();
                                mAdapter.setData(mDataList);
                            }
//                            mSmartRefreshLayout.finishRefresh();
                        }
                    }
                });

    }

    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }


}
