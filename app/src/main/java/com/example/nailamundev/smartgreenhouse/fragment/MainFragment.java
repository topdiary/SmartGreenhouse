package com.example.nailamundev.smartgreenhouse.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.activity.MoreInfoActivity;
import com.example.nailamundev.smartgreenhouse.adapter.GreenhousesListAdapter;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemDao;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemListDao;
import com.example.nailamundev.smartgreenhouse.datatype.MutableInteger;
import com.example.nailamundev.smartgreenhouse.manager.GreenhousesListManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    //Variables

    private static final String UID = "uid";
    private ListView listView;
    private GreenhousesListAdapter listAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GreenhousesItemListDao dao;
    private GreenhousesListManager greenhousesListManager;
    private MutableInteger lastPositionInteger;

    //Firebase Variable
    private DatabaseReference mDatabase;
    private ChildEventListener mChildEventListener;
    private Query mQuery;


    /*****************
     * Functions
     ****************/

    public interface FragmentListener {
        void onGreenhouseClicked(String keyItem, GreenhousesItemDao dao);
    }

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize Fragment's level's Variable

        init(savedInstanceState);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);                        //Restore Instance State
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);

        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        greenhousesListManager = new GreenhousesListManager();
        lastPositionInteger = new MutableInteger(-1);


    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new GreenhousesListAdapter(lastPositionInteger);
        listAdapter.setDao(greenhousesListManager.getDao()); //open App
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(listViewItemClickListener);


        //swipeRefreshLayout reload data
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(swipeListener);

        listView.setOnScrollListener(listViewScrollListener);

        if (savedInstanceState == null) {
            refreshData();
        }

    }

    private void refreshData() {

        if (greenhousesListManager.getCount() == 0) {
            reload();
        }
    }


    private void reload() {

        dao = new GreenhousesItemListDao();
        dao.clearmGreenhousesItemDao();
        dao.clearmGreenhousesIds();


        //Connect Database Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mQuery = mDatabase.child("list").child(UID);
        mChildEventListener = childEventListener;
        mQuery.addChildEventListener(mChildEventListener);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: Check NETWORK

    }

    @Override
    public void onStop() {

        if (mChildEventListener != null) {
            mQuery.removeEventListener(mChildEventListener);

        }

        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        outState.putBundle("greenhouseListManager",
                greenhousesListManager.onSaveInstanceState());
        outState.putBundle("lastPositionInteger",
                lastPositionInteger.onSaveInstanceState());


    }

    private void onRestoreInstanceState(Bundle saveInstanceState) {
        //Restore instance state here
        greenhousesListManager.onRestoreInstanceState(
                saveInstanceState.getBundle("greenhouseListManager"));
        lastPositionInteger.onRestoreInstanceState(
                saveInstanceState.getBundle("lastPositionInteger")
        );

    }


    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    /***********************
     * Listener Zone
     **********************/

    SwipeRefreshLayout.OnRefreshListener swipeListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            reload();
        }
    };


    AbsListView.OnScrollListener listViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            swipeRefreshLayout.setEnabled(i == 0);                                              //open swipeRefreshLayout index listview first
        }
    };


    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            swipeRefreshLayout.setRefreshing(false);
            GreenhousesItemDao itemDao = dataSnapshot.getValue(GreenhousesItemDao.class);
            Log.d("MainFragment", itemDao.map);
            dao.addGreenhousesItemListDao(itemDao);
            dao.addGreenhousesIds(dataSnapshot.getKey());
            greenhousesListManager.setDao(dao);
            listAdapter.setDao(dao);
            listAdapter.notifyDataSetChanged();


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            GreenhousesItemDao itemDao = dataSnapshot.getValue(GreenhousesItemDao.class);
            Log.d("onChildChanged", itemDao.name);

            String greenhouseKey = dataSnapshot.getKey();
            int greenhouseIndex = dao.getmGreenhousesIds().indexOf(greenhouseKey);
            if (greenhouseIndex > -1) {
                dao.set(greenhouseIndex, itemDao);
                listAdapter.setDao(dao);
                listAdapter.notifyDataSetChanged();
            }


        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            String keyItem = greenhousesListManager.getIds(i);
            GreenhousesItemDao dao = greenhousesListManager.getDao().getGreenhousesPosition(i);


                FragmentListener listener = (FragmentListener) getActivity();
                listener.onGreenhouseClicked(keyItem, dao);

        }
    };


}
