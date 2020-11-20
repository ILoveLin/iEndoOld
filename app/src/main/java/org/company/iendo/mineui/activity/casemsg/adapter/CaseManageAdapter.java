package org.company.iendo.mineui.activity.casemsg.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.BaseAdapter;

import org.company.iendo.R;
import org.company.iendo.common.MyAdapter;

/**
 * LoveLin
 * <p>
 * Describe 病例管理的adapter
 */
public class CaseManageAdapter extends MyAdapter<String> {
    public CaseManageAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends BaseAdapter.ViewHolder {

        private TextView mChangePassword;
        private TextView mId;

        public ViewHolder() {
            super(R.layout.item_case_msg);
            mChangePassword = (TextView) findViewById(R.id.user_search_change_password);
            mId = (TextView) findViewById(R.id.tv_text);
        }

        @Override
        public void onBindView(int position) {
            mId.setText(position + "");
            mChangePassword.setText(getItem(position));
        }
    }


}
