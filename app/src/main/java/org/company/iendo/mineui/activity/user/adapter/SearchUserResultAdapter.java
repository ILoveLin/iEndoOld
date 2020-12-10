package org.company.iendo.mineui.activity.user.adapter;

import android.content.Context;
import android.view.ViewGroup;
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
        private TextView tv_id;
        private TextView tv_user_name;
        private TextView tv_result_dex;
        private TextView tv_result_creator_data;

        public ViewHolder() {
            super(R.layout.item_user_search_result);
            tv_id = (TextView) findViewById(R.id.tv_id);
            tv_user_name = (TextView) findViewById(R.id.tv_user_name);
            tv_result_dex = (TextView) findViewById(R.id.tv_result_change);
            tv_result_creator_data = (TextView) findViewById(R.id.tv_result_creator_data);

        }

        @Override
        public void onBindView(int position) {
            UserListBean.DsDTO bean = getItem(position);
            tv_id.setText(position);
            tv_user_name.setText("用户名:" + bean.getUserName());
            tv_result_dex.setText("" + bean.getDes());
            tv_result_creator_data.setText("" + bean.getCreatedAt());
        }
    }


}
