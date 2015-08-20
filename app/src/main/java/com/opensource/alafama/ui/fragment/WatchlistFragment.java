package com.opensource.alafama.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.alafama.R;
import com.opensource.alafama.ui.activity.common.BaseDrawerActivity;

public class WatchlistFragment extends BaseDrawerActivity.DrawerFragment
{

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null)
        {

        }
        else
        {

        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_watchlist, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null)
        {
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.fw_recyclerview);
            recyclerView.setAdapter(new WatchlistAdapter());
        }
    }

    private static final class WatchlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
        {
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
        {

        }

        @Override
        public int getItemCount()
        {
            return 0;
        }
    }

    private static final class WatchlistItemHolder extends RecyclerView.ViewHolder
    {
        public WatchlistItemHolder(final View itemView)
        {
            super(itemView);
        }
    }
}
