package org.company.iendo.mineui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
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
import org.company.iendo.bean.event.AddDeleteEvent;
import org.company.iendo.common.HttpConstant;
import org.company.iendo.common.MyActivity;
import org.company.iendo.mineui.activity.casemsg.CaseDetailMsgActivity;
import org.company.iendo.mineui.activity.casemsg.adapter.SearchAdapter;
import org.company.iendo.util.LogUtils;
import org.company.iendo.util.SharePreferenceUtil;
import org.company.iendo.util.anim.EasyTransition;
import org.company.iendo.widget.HintLayout;
import org.company.iendo.widget.RecycleViewDivider;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import okhttp3.Call;

/**
 * LoveLin
 * <p>
 * Describe 搜索用户结果界面
 */
public class SearchUserResultActivity extends MyActivity implements StatusAction, BaseAdapter.OnItemClickListener {
    private List<CaseManagerListBean.DsDTO> mDataList;
    public List<CaseManagerListBean.DsDTO> mList;
    private WrapRecyclerView mRecyclerView;
    private ClearEditText mEditSearch;
    private ClearEditText mCetSearch;
    private LinearLayout linear_all;
    private LinearLayout mTitleBar;
    private HintLayout mHintLayout;
    private SearchAdapter mAdapter;
    private String endoType;
    private TextView mBack;
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
        mEditSearch = findViewById(R.id.cet_user_search);
        int statusBarHeight = getStatusBarHeight();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = statusBarHeight + 23;
        mTitleBar.setLayoutParams(params);
        setOnClickListener(R.id.tv_back, R.id.iv_user_search);
        EasyTransition.enter(SearchUserResultActivity.this);
        mAdapter = new SearchAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAnimation(null);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, 1, R.drawable.shape_divideritem_decoration));
        endoType = (String) SharePreferenceUtil.get(SearchUserResultActivity.this, SharePreferenceUtil.Current_Case_Num, "3");
        showSoftInputFromWindow(this, mEditSearch);
    }


    @Override
    protected void initData() {
        tag = mCetSearch.getText().toString().trim();
        sendRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                EasyTransition.exit(SearchUserResultActivity.this);
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
                            mDataList = stream.filter(bean -> bean.getName().startsWith(tag)).collect(Collectors.toList());
                            if (mDataList.size() == 0) {
                                showEmpty();
                            } else {
                                showComplete();
                                mAdapter.setData(mDataList);
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast("第" + position + "条目被点击了");


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}
