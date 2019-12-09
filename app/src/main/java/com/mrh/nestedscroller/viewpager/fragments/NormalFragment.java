package com.mrh.nestedscroller.viewpager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrh.nestedscroller.R;

/**
 * 普通布局
 * Created by haoxinlei on 2019-12-09.
 */
public class NormalFragment extends Fragment {

    public static NormalFragment newInstance() {
        return new NormalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.normal_fragmet_layout, container, false);
    }
}
