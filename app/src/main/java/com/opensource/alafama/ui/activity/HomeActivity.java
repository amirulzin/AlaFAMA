package com.opensource.alafama.ui.activity;

import android.os.Bundle;

import com.opensource.alafama.R;

public class HomeActivity extends BaseDrawerActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base_tabbed);
    }

    @Override
    public int getDrawerItemId()
    {
        return R.id.md_nav_home;
    }
}
