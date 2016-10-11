package com.example.matt.chromesthesia;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.widget.Adapter;

/**
 * Created by Matt on 9/16/2016.
 *
 *
 * this class isn't needed. Keeping in case it is later.
 */
public class FragmentPagerSupport extends FragmentActivity {
    static final int NUM_ITEMS = 4;

    Adapter mAdapter;

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chromesthesia);
    }
}
