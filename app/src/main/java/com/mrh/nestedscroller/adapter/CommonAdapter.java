package com.mrh.nestedscroller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mrh.nestedscroller.R;
import com.mrh.nestedscroller.adapter.vh.CommonVh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoxinlei on 2019-12-09.
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonVh> {
    List<String> mDataSet = generateData();

    @NonNull
    @Override
    public CommonVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommonVh(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.common_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommonVh commonVh, int i) {
        commonVh.mTextView.setText(mDataSet.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private List<String> generateData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("This is item for : " + i);
        }
        return data;
    }
}
