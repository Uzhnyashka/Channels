package com.bobyk.channels;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import com.bobyk.channels.fragments.ChannelFragment;

import java.util.ArrayList;

/**
 * Created by bobyk on 22/07/16.
 */
public class ChannelAdapter extends FragmentPagerAdapter {
    private ArrayList<ChannelFragment> channels;

    public ChannelAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setChannels(ArrayList<ChannelFragment> channels) {
        this.channels = channels;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return channels.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        /*switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return ChannelFragment.newInstance(0, "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return FirstFragment.newInstance(1, "Page # 2");
            case 2: // Fragment # 1 - This will show SecondFragment
                return SecondFragment.newInstance(2, "Page # 3");
            default:
                return null;
        }*/
       // ChannelFragment fragment = ChannelFragment.newInstance(position, "pos " + position);
        return channels.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        return super.instantiateItem(container, position);
    }
}
