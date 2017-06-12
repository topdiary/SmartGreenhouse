package com.example.nailamundev.smartgreenhouse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nailamundev.smartgreenhouse.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ChartValueFragment extends Fragment {

    TabLayout slidingTabsChart;
    ViewPager viewPagerChart;

    public ChartValueFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ChartValueFragment newInstance() {
        ChartValueFragment fragment = new ChartValueFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart_value, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        viewPagerChart = (ViewPager) rootView.findViewById(R.id.viewPagerChart);
        viewPagerChart.setAdapter( new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return GreenhouseChartDayFragment.newInstance();
                    case 1:
                        return GreenhouseChartWeekFragment.newInstance();
                    case 2:
                        return GreenhouseChartMonthFragment.newInstance();
                    case 3:
                        return GreenhouseChartYearFragment.newInstance();
                    default:
                        return null;
                }


            }

            @Override
            public int getCount() {
                return 4;
            }


            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.day);
                    case 1:
                        return getString(R.string.week);
                    case 2:
                        return getString(R.string.month);
                    case 3:
                        return getString(R.string.year);
                    default:
                        return "";

                }
            }
        });


        slidingTabsChart = (TabLayout) rootView.findViewById(R.id.slidingTabsChart);
        slidingTabsChart.setupWithViewPager(viewPagerChart);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
