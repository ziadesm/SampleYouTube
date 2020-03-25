package android.examples.sampleyoutube.adapter;

import android.examples.sampleyoutube.fragments.ChannelFragment;
import android.examples.sampleyoutube.fragments.PlaylistFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int numOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int num) {
        super(fm);
        this.numOfTabs = num;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ChannelFragment channelFragment = new ChannelFragment();
                return channelFragment;
            case 1:
                PlaylistFragment playlistFragment = new PlaylistFragment();
                return playlistFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
