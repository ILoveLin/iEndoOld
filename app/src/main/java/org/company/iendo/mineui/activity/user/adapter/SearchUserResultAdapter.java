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
 * Describe
 */
public class SearchUserResultAdapter extends MyAdapter<UserListBean.DsDTO> {
    public SearchUserResultAdapter(@NonNull Context context) {
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
            super(R.layout.item_user_search_result);
            mDeleteButton = (Button) findViewById(R.id.user_search_delBtn);
            mTitleName = (TextView) findViewById(R.id.tv_item_name);
            mDec = (TextView) findViewById(R.id.tv_item_power);
            mID = (TextView) findViewById(R.id.tv_id);
            mChange = (TextView) findViewById(R.id.tv_item_change_password);

        }

        @Override
        public void onBindView(int position) {
            UserListBean.DsDTO item = getItem(position);
            mID.setText("" + position);
            if (position % 2 == 0) {
                mID.setBackgroundResource(R.drawable.shape_bg_item_id_2);
            } else {
                mID.setBackgroundResource(R.drawable.shape_bg_item_id);
            }
            mTitleName.setText("" + item.getUserName());
            mDec.setText("" + item.getDes());
        }
    }


}
