package com.example.nailamundev.smartgreenhouse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class SettingGreenhouseFragment extends Fragment {

    private static final String UID = "uid";
    private static final String KEY_ITEM = "keyItem";
    private TextView tvStateMode;
    private Switch switchMode;
    private String keyItem;
    private int stateMode;

    //Firebase
    private DatabaseReference mDatabase, mMode, mControlMode;
    private ValueEventListener valueEventListener;


    /****************
     * Function
     ***************/

    public SettingGreenhouseFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static SettingGreenhouseFragment newInstance(String keyItem, GreenhousesItemDao dao) {
        SettingGreenhouseFragment fragment = new SettingGreenhouseFragment();
        Bundle args = new Bundle();

        args.putString(KEY_ITEM, keyItem);
        args.putParcelable("dao", dao);
        Log.d("SettingGreenhouseFragment", "keyItem : " + keyItem);
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

        //Firebase Reference
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mMode = mDatabase.child("status").child(UID).child(keyItem);
        mControlMode =  mDatabase.child("controlAct").child(UID).child(keyItem).child("mode");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting_greenhouse, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        tvStateMode = (TextView) rootView.findViewById(R.id.tvStateMode);
        switchMode = (Switch) rootView.findViewById(R.id.switchMode);
        switchMode.setOnCheckedChangeListener(switchListener);
        checkStateMode();
    }


    private void updateSwitch(int stateMode) {

        if (stateMode == 1){
            tvStateMode.setText(getString(R.string.off));
            switchMode.setChecked(false);

        }
        else {
            tvStateMode.setText(getString(R.string.on));
            switchMode.setChecked(true);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (valueEventListener != null) {
            mMode.removeEventListener(valueEventListener);
        }
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


    /***************
     * Lintener
     ***************/

    CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            if (b == false) {
               mControlMode.setValue(1);
            }
            if (b == true){
                mControlMode.setValue(0);
            }

        }
    };


    private void checkStateMode() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                stateMode = Integer.valueOf(String.valueOf(map.get("mode")));
                updateSwitch(stateMode);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mMode.addValueEventListener(valueEventListener);

    }


}
