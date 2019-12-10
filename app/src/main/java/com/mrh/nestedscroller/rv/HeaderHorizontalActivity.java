package com.mrh.nestedscroller.rv;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mrh.nestedscroller.R;
import com.mrh.nestedscroller.adapter.ImageAdapter;

public class HeaderHorizontalActivity extends RecyclerViewActivity {

    private RecyclerView mHeaderRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderRv = findViewById(R.id.rv_img);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        mHeaderRv.setLayoutManager(layoutManager);
        mHeaderRv.setAdapter(new ImageAdapter());
        mHeaderRv.setNestedScrollingEnabled(false);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_header_horizontal;
    }
}
