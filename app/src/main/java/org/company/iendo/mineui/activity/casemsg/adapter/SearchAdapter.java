package org.company.iendo.mineui.activity.casemsg.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.BaseAdapter;

import org.company.iendo.R;
import org.company.iendo.bean.CaseManagerListBean;
import org.company.iendo.common.MyAdapter;

/**
 * LoveLin
 * <p>
 * Describe
 */
public class SearchAdapter extends MyAdapter<CaseManagerListBean.DsDTO> {
    public SearchAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends BaseAdapter.ViewHolder {
        private TextView tv_id;
        private TextView tv_title;
        private TextView tv_created_time;

        public ViewHolder() {
            super(R.layout.item_case_msg);
            tv_id = (TextView) findViewById(R.id.tv_id);
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_created_time = (TextView) findViewById(R.id.tv_created_time);

        }

        @Override
        public void onBindView(int position) {
            CaseManagerListBean.DsDTO bean = getItem(position);
            tv_id.setText("ID:" + bean.getID());
            tv_title.setText("用户名:" + bean.getName());
            tv_created_time.setText("创建时间:" + bean.getRecordDate());
        }
    }


}
