package org.company.iendo.mineui.fragment.adapter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.hjq.base.BaseAdapter;

import org.company.iendo.R;
import org.company.iendo.common.MyAdapter;
import org.company.iendo.mineui.MainActivity;

import java.io.File;

/**
 * LoveLin
 * <p>
 * Describe 图片
 */
public class PictureAdapter extends MyAdapter<String> {
    public PictureAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends BaseAdapter.ViewHolder {
        private AppCompatImageView mImage;

        public ViewHolder() {
            super(R.layout.item_case_picture);
            mImage = (AppCompatImageView) findViewById(R.id.iv_case_image);
        }

        @Override
        public void onBindView(int position) {
            File file = new File(getItem(position));
            Glide.with(getContext())
                    .load(file)
                    .into(mImage);
            Log.e("adapter", "item==path==" + file.getAbsolutePath() + "");
        }
    }


}
