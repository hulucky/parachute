package com.parachute.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyFragmentPageAadpter extends FragmentPagerAdapter {
		
	private ArrayList<Fragment> fragmentsList;
	public MyFragmentPageAadpter(FragmentManager fm) {super(fm);}
	public MyFragmentPageAadpter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

	@Override
	public Fragment getItem(int index) {
		return fragmentsList.get(index);
	}

	@Override
	public int getCount() {
		return fragmentsList.size();
	}

}
