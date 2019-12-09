package com.mrh.nestedscroller.ns;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;

import com.mrh.nestedscroller.R;

public class NestedScrollViewActivity extends AppCompatActivity {

    private NestedScrollView mNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_view);
        mNestedScrollView = findViewById(R.id.nested_scroll);
        mNestedScrollView.setNestedScrollingEnabled(true);
    }
}
