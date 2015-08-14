package com.opensource.alafama.ui.activity.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.opensource.alafama.R;

public abstract class BaseTabbedActivity extends BaseDrawerActivity
{

    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    public ViewPager getViewPager()
    {
        return mViewPager;
    }

    public AppBarLayout getAppBarLayout()
    {
        return mAppBarLayout;
    }

    public TabLayout getTabLayout()
    {
        return mTabLayout;
    }

    public Toolbar getToolbar()
    {
        return mToolbar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final View contentView = inflateContent(R.layout.layout_base_tabbed);
        setViews(contentView);
        setupViews(savedInstanceState, mAppBarLayout, mToolbar, mTabLayout, mViewPager);
    }

    private void setViews(View contentView)
    {
        mViewPager = (ViewPager) contentView.findViewById(R.id.lbt_viewpager);
        mAppBarLayout = (AppBarLayout) contentView.findViewById(R.id.c_awt_appbar);
        mTabLayout = (TabLayout) mAppBarLayout.findViewById(R.id.c_awt_tablayout);
        mToolbar = (Toolbar) mAppBarLayout.findViewById(R.id.c_awt_toolbar);
        super.setDrawerNavigationButton(mToolbar);
    }

    /**
     * Called at the end of {@link #onCreate(Bundle)}. Except the {@param savedInstanceState}, other parameters are guaranteed non null.
     *
     * @param savedInstanceState The bundle passed from {@link #onCreate(Bundle)}
     * @param appBarLayout
     * @param toolbar
     * @param tabLayout
     * @param viewPager
     */
    public abstract void setupViews(@Nullable final Bundle savedInstanceState, final AppBarLayout appBarLayout, final Toolbar toolbar, final TabLayout tabLayout, final ViewPager viewPager);
}
