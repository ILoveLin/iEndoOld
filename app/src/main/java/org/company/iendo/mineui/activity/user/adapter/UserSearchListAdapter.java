package org.company.iendo.mineui.activity.user.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.BaseAdapter;

import org.company.iendo.R;
import org.company.iendo.bean.UserListBean;
import org.company.iendo.common.MyAdapter;

/**
 * LoveLin
 * <p>
 * Describe用户搜索列表
 */
public class UserSearchListAdapter extends MyAdapter<UserListBean.DsDTO> {


    public UserSearchListAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }


    private final class ViewHolder extends BaseAdapter.ViewHolder {
        private Button mDeleteButton;
        private TextView mTitleName;
        private TextView mDec;
        private TextView mChange;
        private TextView mID;
        private TextView mCreatorData;

        public ViewHolder() {
            super(R.layout.item_user_serach_list);
            mDeleteButton = (Button) findViewById(R.id.user_search_delBtn);
            mTitleName = (TextView) findViewById(R.id.tv_item_user_name);
            mDec = (TextView) findViewById(R.id.tv_item_dec);
            mID = (TextView) findViewById(R.id.tv_id);
            mChange = (TextView) findViewById(R.id.tv_item_change);
            mCreatorData = (TextView) findViewById(R.id.tv_item_creator_data);
        }

        @Override
        public void onBindView(int position) {
            UserListBean.DsDTO item = getItem(position);
            mID.setText("" + position);
            mTitleName.setText("用户名:" + item.getUserName());
            mDec.setText("权  限:" + item.getDes());
            mCreatorData.setText("" + item.getCreatedAt());


        }
    }

}
