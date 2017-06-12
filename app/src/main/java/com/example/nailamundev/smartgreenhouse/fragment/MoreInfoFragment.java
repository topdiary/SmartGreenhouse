package com.example.nailamundev.smartgreenhouse.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.activity.AccountGreenhouseActivity;
import com.example.nailamundev.smartgreenhouse.activity.NotificationGreenhouseActivity;
import com.example.nailamundev.smartgreenhouse.activity.SettingGreenhouseActivity;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemDao;
import com.inthecheesefactory.thecheeselibrary.view.SlidingTabLayout;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MoreInfoFragment extends Fragment {


    // Variable
    private static final String KEY_ITEM = "keyItem";
    private static final String KEY_BUNDLE = "keyBundle";
    private static final String DAO = "dao";
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private TabLayout tabLayout;
    private GreenhousesItemDao dao;
    private String keyItem;

    /*****************
     * Functions
     ****************/

    public MoreInfoFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MoreInfoFragment newInstance(String keyItem, GreenhousesItemDao dao) {
        MoreInfoFragment fragment = new MoreInfoFragment();
        Bundle args = new Bundle();

        args.putString(KEY_ITEM, keyItem);
        args.putParcelable(DAO, dao);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        dao = getArguments().getParcelable(DAO);
        keyItem = getArguments().getString(KEY_ITEM);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_more_info, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        setHasOptionsMenu(true);
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return GreenhouseDataFragment.newInstance(keyItem, dao);
                    case 1:
                        return GreenhouseControlFragment.newInstance(keyItem);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.datatab);
                    case 1:
                        return getString(R.string.controltab);
                    default:
                        return "";

                }
            }
        });

        tabLayout = (TabLayout) rootView.findViewById(R.id.slidingTabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void doFunctionSetting() {
        Intent intentSetting = new Intent(getContext(), SettingGreenhouseActivity.class);
        intentSetting.putExtra(DAO, dao);
        Bundle bundleSetting = new Bundle();
        bundleSetting.putString(KEY_ITEM, keyItem);
        intentSetting.putExtra(KEY_BUNDLE, bundleSetting);
        startActivity(intentSetting);
    }

    private void doFunctionNotification() {
        Intent intentNotification = new Intent(getContext(), NotificationGreenhouseActivity.class);
        intentNotification.putExtra("dao", dao);
        Bundle bundleNotification = new Bundle();
        bundleNotification.putString(KEY_ITEM, keyItem);
        intentNotification.putExtra(KEY_BUNDLE, bundleNotification);
        startActivity(intentNotification);
    }

    private void doFunctionAdd() {
        Intent intent = new Intent(getContext(), AccountGreenhouseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ITEM, keyItem);
        intent.putExtra(KEY_BUNDLE, bundle);
        startActivity(intent);
        return;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_more_info, menu);
    }


    /***********************
     * Listener Zone
     **********************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_add:
                doFunctionAdd();
                break;

            case R.id.action_notificationGreenhouse:
                doFunctionNotification();
                break;

            case R.id.action_settingsGreenhouse:
                doFunctionSetting();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
