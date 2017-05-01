package com.vijay.kisannetwork.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.vijay.kisannetwork.R;
import com.vijay.kisannetwork.adapters.HomePagerAdapter;

public class MainActivity extends AppCompatActivity {


    ViewPager homeViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        homeViewPager = (ViewPager) findViewById(R.id.pager_home);
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        homeViewPager.setAdapter(homePagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(homeViewPager);
    }


}
