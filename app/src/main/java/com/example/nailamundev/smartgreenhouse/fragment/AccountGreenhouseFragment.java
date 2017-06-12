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
public class AccountGreenhouseFragment extends Fragment {

    private static final String KEY_ITEM = "keyItem";
    private TabLayout tabsAccount;
    private ViewPager viewPagerAccount;
    private String keyItem;


    /*****************
     * Functions
     ****************/

    public AccountGreenhouseFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static AccountGreenhouseFragment newInstance(String keyItem) {
        AccountGreenhouseFragment fragment = new AccountGreenhouseFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ITEM, keyItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        keyItem = getArguments().getString(KEY_ITEM);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_greenhouse, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        viewPagerAccount = (ViewPager) rootView.findViewById(R.id.viewPagerAccount);
        viewPagerAccount.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return IncomeFragment.newInstance(keyItem);
                    case 1:
                        return ExpenseFragment.newInstance(keyItem);
                    case 2:
                        return ProductFragment.newInstance(keyItem);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.income);
                    case 1:
                        return getString(R.string.expenses);
                    case 2:
                        return getString(R.string.product);
                    default:
                        return null;
                }
            }
        });

        tabsAccount = (TabLayout) rootView.findViewById(R.id.slidingTabsAccount);
        tabsAccount.setupWithViewPager(viewPagerAccount);


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


    /***********************
     * Listener Zone
     **********************/

}
