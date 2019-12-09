package com.mrh.nestedscroller.viewpager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mrh.nestedscroller.viewpager.fragments.ListFragment;
import com.mrh.nestedscroller.viewpager.fragments.NormalFragment;

/**
 * Created by haoxinlei on 2019-12-09.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return ListFragment.newInstance();
        } else {
            return NormalFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
