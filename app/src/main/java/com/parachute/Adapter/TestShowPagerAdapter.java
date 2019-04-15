package com.parachute.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TestShowPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public TestShowPagerAdapter(FragmentManager fm , List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
