package com.example.profilecomparator;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter
{
    Fragment fragment;
    int NO_OF_SWIPE_SCREENS=6;
    public SwipeAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Bundle bundle=new Bundle();
        switch(position)
        {
            case 0:
                fragment = new AboutFragment();
                break;
            case 1:
                fragment = new InterestsFragment();
                break;
            case 2:
                fragment = new AboutFragment();
                break;
            case 3:
                fragment = new AboutFragment();
                break;
            case 4:
                fragment = new InterestsFragment();
                break;
            case 5:
                fragment = new AboutFragment();
                break;
        }




        bundle.putInt("count",position+1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return NO_OF_SWIPE_SCREENS;
    }
}
