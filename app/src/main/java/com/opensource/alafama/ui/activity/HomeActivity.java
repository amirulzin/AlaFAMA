package com.opensource.alafama.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.opensource.alafama.R;
import com.opensource.alafama.ui.activity.common.BaseTabbedActivity;

public class HomeActivity extends BaseTabbedActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupViews(@Nullable final Bundle savedInstanceState, final AppBarLayout appBarLayout, final Toolbar toolbar, final TabLayout tabLayout, final ViewPager viewPager)
    {
        if (savedInstanceState != null)
        {
            toolbar.setTitle(R.string.app_name);
            toolbar.setTitleTextColor(Color.WHITE);
        }
    }

    @Override
    public int getDrawerItemId()
    {
        return R.id.md_nav_home;
    }

    private static class FragmentAdapter extends FragmentStatePagerAdapter
    {

        public FragmentAdapter(final FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position)
        {
            return null;
        }

        @Override
        public int getCount()
        {
            return 0;
        }
    }
}
