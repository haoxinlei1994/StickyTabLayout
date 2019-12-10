package com.mrh.nestedscroller.adapter.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by haoxinlei on 2019-12-10.
 */
public class ImageVh extends RecyclerView.ViewHolder {

    public ImageView mImageView;

    public ImageVh(@NonNull View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView;
    }
}
