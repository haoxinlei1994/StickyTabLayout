package com.mrh.nestedscroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mrh.nestedscroller.ns.NestedScrollViewActivity;
import com.mrh.nestedscroller.rv.RecyclerViewActivity;
import com.mrh.nestedscroller.viewpager.ViewPagerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mRvBtn;
    private Button mVpBtn;
    private Button mSvBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvBtn = findViewById(R.id.btn_rv);
        mVpBtn = findViewById(R.id.btn_vp);
        mSvBtn = findViewById(R.id.btn_sv);
        mRvBtn.setOnClickListener(this);
        mVpBtn.setOnClickListener(this);
        mSvBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rv:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.btn_vp:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case R.id.btn_sv:
                startActivity(new Intent(this, NestedScrollViewActivity.class));
                break;
        }
    }
}
