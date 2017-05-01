package com.vijay.kisannetwork.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vijay.kisannetwork.fragments.ContactTab;
import com.vijay.kisannetwork.fragments.HistoryTab;

/**
 * Created by vijay on 16/04/17.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */

/**
 * An adapter for ViewPager at very first instance
 * containng fragments
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    final int PAGE_COUNT = 2;

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new ContactTab();
//            Bundle args = new Bundle();

        switch (i) {
            case 0:
                fragment = new ContactTab();
                return fragment;
            case 1:
                fragment = new HistoryTab();
                return fragment;
            default:
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return ContactTab.TITLE;
            case 1:
                return HistoryTab.TITLE;
            default:
                return "unknown";
        }
    }
}
