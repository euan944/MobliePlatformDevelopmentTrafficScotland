package org.me.gcu.traffic_scotland.ui.results_work_out;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.me.gcu.traffic_scotland.R;

/**
 * @author Euan Penman S1821916
 */
public class PagerAdapterforResults extends FragmentPagerAdapter {
    //Sets up priavte varibales for this class
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1_current_incidents, R.string.tab_text_2_on_going_roadworks, R.string.tab_text_3_planned_roadworks};
    private final Context mContext;

    public PagerAdapterforResults(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}