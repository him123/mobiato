package com.ae.benchmark.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.benchmark.R;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class DashboardFragment extends Fragment {

    static android.support.v4.app.FragmentManager fragmentManager;
    View rootView;
    private String str_Post_Status;

    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    final int NUM_PAGES = 3;
    boolean flag;

    ViewPager viewPager;
    MaterialShowcaseSequence sequence;

    public static Fragment newInstance(FragmentManager fragmentManager1) {
        DashboardFragment fragment = new DashboardFragment();
        fragmentManager = fragmentManager1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        sequence = new MaterialShowcaseSequence(getActivity(), "Dash");
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        sequence.setConfig(config);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_part_one, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        sequence.addSequenceItem(tabLayout,
                "Swipe left for more screen.", "GOT IT");

        sequence.start();

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    FragmentDashboardOne fragmentAnimation1 = new FragmentDashboardOne();
                    return fragmentAnimation1;

                case 1:
                    FragmentDashboardTwo fragmentAnimation2 = new FragmentDashboardTwo();
                    return fragmentAnimation2;

                case 2:
                    FragmentDashboardThree fragmentAnimation3 = new FragmentDashboardThree();
                    return fragmentAnimation3;

            }
            return null;
        }

    }

}
