package com.opensource.alafama.ui.factory;

import android.content.Context;
import android.content.Intent;

import com.opensource.alafama.R;
import com.opensource.alafama.ui.activity.HomeActivity;

public final class ActivityFactory
{
    public static void changeActivityFromDrawer(Context context, int navId)
    {
        Intent intent = new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        switch (navId){
            case R.id.md_nav_home:
                intent.setClass(context, HomeActivity.class);
        }

        context.startActivity(intent);
    }
}