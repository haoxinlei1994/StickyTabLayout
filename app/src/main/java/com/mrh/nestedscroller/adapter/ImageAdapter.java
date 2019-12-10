package com.mrh.nestedscroller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mrh.nestedscroller.R;
import com.mrh.nestedscroller.adapter.vh.ImageVh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoxinlei on 2019-12-10.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageVh> {

    private List<Integer> mDataSet = generateImages();

    private List<Integer> generateImages() {
        List<Integer> data = new ArrayList<>();
        data.add(R.drawable.girl);
        data.add(R.drawable.image_1);
        data.add(R.drawable.image_2);
        data.add(R.drawable.image_3);
        return data;
    }

    @NonNull
    @Override
    public ImageVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageVh(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVh imageVh, int i) {
        imageVh.mImageView.setImageResource(mDataSet.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
