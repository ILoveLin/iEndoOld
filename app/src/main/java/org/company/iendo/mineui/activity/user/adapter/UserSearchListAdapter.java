package org.company.iendo.mineui.activity.user.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.BaseAdapter;

import org.company.iendo.R;
import org.company.iendo.common.MyAdapter;

/**
 * LoveLin
 * <p>
 * Describe用户搜索列表
 */
public class UserSearchListAdapter extends MyAdapter<String> {


    public UserSearchListAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }


    private final class ViewHolder extends BaseAdapter.ViewHolder {
        private TextView mChangePassword;
        private Button mDeleteButton;
        private TextView mTitle;

        public ViewHolder() {
            super(R.layout.item_user_serach_result);
            mDeleteButton = (Button) findViewById(R.id.user_search_delBtn);
            mTitle = (TextView) findViewById(R.id.tv_text);
            mChangePassword = (TextView) findViewById(R.id.user_search_change_password);
        }

        @Override
        public void onBindView(int position) {
            mTitle.setText("         " + position + ":              " + getItem(position));
        }
    }


}
