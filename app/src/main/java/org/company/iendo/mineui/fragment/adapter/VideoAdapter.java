package org.company.iendo.mineui.fragment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.hjq.base.BaseAdapter;

import org.company.iendo.R;
import org.company.iendo.common.MyAdapter;

/**
 * LoveLin
 * <p>
 * Describe 视频
 */
public class VideoAdapter extends MyAdapter<String> {
    public VideoAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends BaseAdapter.ViewHolder {
        private TextView mVideo;

        public ViewHolder() {
            super(R.layout.item_case_video);
            mVideo = (TextView) findViewById(R.id.tv_case_video);
        }

        @Override
        public void onBindView(int position) {
            mVideo.setText(getItem(position) + "");
            Log.e("tag", "" + getItem(position) + "");
        }
    }


}
