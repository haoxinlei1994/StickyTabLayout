package com.mrh.nestedscroller.adapter.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by haoxinlei on 2019-12-09.
 */
public class CommonVh extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public CommonVh(@NonNull View itemView) {
        super(itemView);
        mTextView = (TextView) itemView;
    }
}
