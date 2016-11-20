package com.example.matt.chromesthesia;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Mikeys_Mac on 11/12/16.
 */

public class FragmentPagerStateAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> fragmentNames = new ArrayList<>();

    public void addFragments(Fragment fragments, String names){
        this.fragments.add(fragments);
        this.fragmentNames.add(names);
    }

    public FragmentPagerStateAdapter(FragmentManager fragManager){

        super(fragManager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle (int position) {
        return fragmentNames.get(position);
    }
}
