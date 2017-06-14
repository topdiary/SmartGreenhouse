package com.example.nailamundev.smartgreenhouse.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.dao.GreenhouseControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class GreenhouseControlFragment extends Fragment implements View.OnClickListener {

    private static final int COLOR_ON_SELECT = 0x00ffffff;
    private static final int COLOR_OFF_SELECT = 0x30000000;
    private static final String VENTILATOR = "fan1";
    private static final String LIGHT = "led";
    private static final String EVAP = "evap";
    private static final String WATER = "watering";
    private static final String FOGGER = "fogger";
    private static final String FIRST_CONTROL = "FIRST_CONTROL";
    private static final String CHECK_FIRST = "checkFirst";
    private static final String UID = "uid";
    private static final String KEY_ITEM = "keyItem";
    private static final String TAG_CHECK_POINT = "GreenhouseControlFragment";

    private int checkFirst;
    private int stateMode;
    private String keyItem;
    private ImageView ivModeDevice, ivLogoModeDevice;
    private ImageView iconDeviceVen, iconDeviceLight, iconDeviceEvap, iconDeviceWater,
            iconDeviceFogger;
    private TextView tvModeDevice;
    private ToggleButton tgVentilator, tgLight, tgEvap, tgWater, tgFogger;
    private View vVentilator, vLight, vEvap, vWater, vFogger;
    private GreenhouseControl stateCheck;

    //Firebase Variable
    private DatabaseReference mDatabase, mDataControl, mControl, mStatus, mDataControlNormal;
    private ValueEventListener valueEventListenerAct, valueEventListenerStatus,
            valueEventListenerControl;


    /****************
     * Function
     ***************/

    public GreenhouseControlFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static GreenhouseControlFragment newInstance(String keyItem) {
        GreenhouseControlFragment fragment = new GreenhouseControlFragment();
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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDataControl = mDatabase.child("realtimeActState").child(UID).child(keyItem);

        mControl = mDatabase.child("controlAct").child(UID).child(keyItem);
        mDataControlNormal = mDatabase.child("realtimeActState").child(UID).child(keyItem);
        mStatus = mDatabase.child("status").child(UID).child(keyItem);


        checkFirst = 0;
        stateCheck = new GreenhouseControl(99, 99, 99, 99, 99, 99, 99);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_greenhouse_control, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        // ImageView
        ivModeDevice = (ImageView) rootView.findViewById(R.id.ivModeDevice);
        ivLogoModeDevice = (ImageView) rootView.findViewById(R.id.ivLogoModeDevice);
        iconDeviceVen = (ImageView) rootView.findViewById(R.id.ivIconDevice);
        iconDeviceLight = (ImageView) rootView.findViewById(R.id.ivIconDeviceLight);
        iconDeviceEvap = (ImageView) rootView.findViewById(R.id.ivIconDeviceEvap);
        iconDeviceWater = (ImageView) rootView.findViewById(R.id.ivIconDeviceWater);
        iconDeviceFogger = (ImageView) rootView.findViewById(R.id.ivIconDeviceFogger);


        // TextView
        tvModeDevice = (TextView) rootView.findViewById(R.id.tvModeDevice);


        //View
        vVentilator = rootView.findViewById(R.id.vVentilator);
        vLight = rootView.findViewById(R.id.vLight);
        vEvap = rootView.findViewById(R.id.vEvap);
        vWater = rootView.findViewById(R.id.vWater);
        vFogger = rootView.findViewById(R.id.vFogger);

        //Init Control Greenhouses
        tgVentilator = (ToggleButton) rootView.findViewById(R.id.tgVentilator);
        tgLight = (ToggleButton) rootView.findViewById(R.id.tgLight);
        tgEvap = (ToggleButton) rootView.findViewById(R.id.tgEvap);
        tgWater = (ToggleButton) rootView.findViewById(R.id.tgWater);
        tgFogger = (ToggleButton) rootView.findViewById(R.id.tgFogger);

        // Set Text Toggle
        tgVentilator.setText(null);
        tgLight.setText(null);
        tgEvap.setText(null);
        tgWater.setText(null);


        tgVentilator.setOnClickListener(this);
        tgLight.setOnClickListener(this);
        tgEvap.setOnClickListener(this);
        tgWater.setOnClickListener(this);
        tgFogger.setOnClickListener(this);

        if (mDatabase != null) {
            checkMode();
        }


    }


    private void normalMode() {
        mDataControl.removeEventListener(valueEventListenerAct);

        valueEventListenerControl = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GreenhouseControl stateControlAct = dataSnapshot.getValue(GreenhouseControl.class);
                updateStateDeviceNormal(stateControlAct);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mControl.addValueEventListener(valueEventListenerControl);
    }

    private void updateStateDeviceNormal(GreenhouseControl stateControlAct) {

        if (checkFirst == 0) {
            ivModeDevice.setImageResource(R.drawable.backdevice);
            ivLogoModeDevice.setImageResource(R.drawable.ic_action_power);
            tvModeDevice.setText(R.string.normal);
            onSelectItem();
            setStateNormal(stateControlAct);
        }

        checkFirst = 1;
    }


    private void loadDataControl(final int stateMode) {

        Log.d(TAG_CHECK_POINT, "loadDataControl");
        checkFirst = 0;

        valueEventListenerAct = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GreenhouseControl state = dataSnapshot.getValue(GreenhouseControl.class);
                Log.d(TAG_CHECK_POINT, String.valueOf(state.manual));

                updateStateDevice(state, stateMode);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDataControl.addValueEventListener(valueEventListenerAct);
    }

    private void updateStateDevice(GreenhouseControl state, int stateMode) {

        Log.d(TAG_CHECK_POINT, "updateStateDevice");

        if (stateMode == 0) {
            // Mode Automation
            ivModeDevice.setImageResource(R.drawable.backdevice_ai);
            ivLogoModeDevice.setImageResource(R.drawable.ic_action_automode);
            tvModeDevice.setText(R.string.auto);
            offSelectItem(state);

        } else {

            // Manual Greenhouse
            if (state.manual == 1) {
                ivModeDevice.setImageResource(R.drawable.backdevice);
                ivLogoModeDevice.setImageResource(R.drawable.ic_action_power_manual);
                tvModeDevice.setText(R.string.manual);
                offSelectItem(state);


            } else {

                // Mode Normal
                ivModeDevice.setImageResource(R.drawable.backdevice);
                ivLogoModeDevice.setImageResource(R.drawable.ic_action_power);
                tvModeDevice.setText(R.string.normal);
                onSelectItem();
                setStateNormal(state);

            }
        }

    }

    private void offSelectItem(GreenhouseControl state) {

        Log.d(TAG_CHECK_POINT, "offSelectItem");

        vVentilator.setBackgroundColor(COLOR_OFF_SELECT);
        vLight.setBackgroundColor(COLOR_OFF_SELECT);
        vEvap.setBackgroundColor(COLOR_OFF_SELECT);
        vWater.setBackgroundColor(COLOR_OFF_SELECT);
        vFogger.setBackgroundColor(COLOR_OFF_SELECT);


        tgVentilator.setEnabled(false);
        tgLight.setEnabled(false);
        tgEvap.setEnabled(false);
        tgWater.setEnabled(false);
        tgFogger.setEnabled(false);
        setStateNormal(state);
    }

    private void onSelectItem() {

        vVentilator.setBackgroundColor(COLOR_ON_SELECT);
        vLight.setBackgroundColor(COLOR_ON_SELECT);
        vEvap.setBackgroundColor(COLOR_ON_SELECT);
        vWater.setBackgroundColor(COLOR_ON_SELECT);
        vFogger.setBackgroundColor(COLOR_ON_SELECT);

        tgVentilator.setEnabled(true);
        tgLight.setEnabled(true);
        tgEvap.setEnabled(true);
        tgWater.setEnabled(true);
        tgFogger.setEnabled(true);
    }

    private void setStateNormal(GreenhouseControl state) {

        if (state != null) {

            // Ventilator
            if (state.fan1 != stateCheck.fan1) {

                if (state.fan1 == 1) {

                    iconDeviceVen.setImageResource(R.drawable.fan);
                    tgVentilator.setChecked(false);

                } else {

                    iconDeviceVen.setImageResource(R.drawable.fan_open);
                    tgVentilator.setChecked(true);

                }
            }

            //led
            if (state.led != stateCheck.led) {
                // Light
                if (state.led == 1) {
                    iconDeviceLight.setImageResource(R.drawable.light);
                    tgLight.setChecked(false);
                } else {
                    iconDeviceLight.setImageResource(R.drawable.light_open);
                    tgLight.setChecked(true);
                }
            }

            // Evap
            if (state.evap != stateCheck.evap) {
                if (state.evap == 1) {
                    iconDeviceEvap.setImageResource(R.drawable.evap);
                    tgEvap.setChecked(false);
                } else {
                    iconDeviceEvap.setImageResource(R.drawable.evap_open);
                    tgEvap.setChecked(true);
                }
            }

            // Watering
            if (state.watering != stateCheck.watering) {
                if (state.watering == 1) {
                    iconDeviceWater.setImageResource(R.drawable.water);
                    tgWater.setChecked(false);
                } else {
                    iconDeviceWater.setImageResource(R.drawable.water_open);
                    tgWater.setChecked(true);

                }
            }

            // Fogger
            if (state.fogger != stateCheck.fogger) {
                if (state.fogger == 1) {
                    iconDeviceFogger.setImageResource(R.drawable.foggy);
                    tgFogger.setChecked(false);
                } else {
                    iconDeviceFogger.setImageResource(R.drawable.foggy_open);
                    tgFogger.setChecked(true);
                }
            }
        }

        stateCheck = state;
    }

    private boolean checkNetwork() {
        Log.d(TAG_CHECK_POINT, "checkNetwork");
        ConnectivityManager manager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return !(networkInfo != null && networkInfo.isConnected());

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mDatabase != null) {
            checkMode();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (valueEventListenerAct != null) {
            mDataControl.removeEventListener(valueEventListenerAct);
        }
        if (valueEventListenerStatus != null) {
            mStatus.removeEventListener(valueEventListenerStatus);
        }
        if (valueEventListenerControl != null) {
            mControl.removeEventListener(valueEventListenerControl);
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


    private void doFunctionCheckFirst() {
        SharedPreferences checkFirst = getContext()
                .getSharedPreferences(FIRST_CONTROL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = checkFirst.edit();
        Boolean stateFirst = checkFirst.getBoolean(CHECK_FIRST, true);
        if (stateFirst && checkNetwork()) {
            PromptDialog dialog = new PromptDialog(getContext());
            dialog.setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                    .setAnimationEnable(true)
                    .setTitleText(getString(R.string.info))
                    .setContentText(getString(R.string.text_control))
                    .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {

                            dialog.dismiss();

                        }
                    }).show();

            editor.putBoolean(CHECK_FIRST, false);
            editor.commit();
        }
    }

    private void setDeviceControl(String topic, Boolean state) {

        Log.d(TAG_CHECK_POINT, "setDeviceControl");
        switch (topic) {
            case VENTILATOR:
                if (state)
                    mControl.child(VENTILATOR).setValue(1);
                else
                    mControl.child(VENTILATOR).setValue(0);

                break;
            case LIGHT:
                if (state)
                    mControl.child(LIGHT).setValue(1);
                else
                    mControl.child(LIGHT).setValue(0);
                break;
            case EVAP:
                if (state)
                    mControl.child(EVAP).setValue(1);
                else
                    mControl.child(EVAP).setValue(0);
                break;
            case WATER:
                if (state)
                    mControl.child(WATER).setValue(1);
                else
                    mControl.child(WATER).setValue(0);
                break;
            case FOGGER:
                if (state)
                    mControl.child(FOGGER).setValue(1);
                else
                    mControl.child(FOGGER).setValue(0);
                break;
        }

    }


    /***********************
     * Listener Zone
     **********************/

    @Override
    public void onClick(View view) {
        Log.d(TAG_CHECK_POINT, "onClick");
        doFunctionCheckFirst();
        switch (view.getId()) {
            case R.id.tgVentilator:
                setDeviceControl(VENTILATOR, tgVentilator.isChecked());
                break;
            case R.id.tgLight:
                setDeviceControl(LIGHT, tgLight.isChecked());
                break;
            case R.id.tgEvap:
                setDeviceControl(EVAP, tgEvap.isChecked());
                break;
            case R.id.tgWater:
                setDeviceControl(WATER, tgWater.isChecked());
                break;
            case R.id.tgFogger:
                setDeviceControl(FOGGER, tgFogger.isChecked());
        }

    }

    private void checkMode() {
        Log.d(TAG_CHECK_POINT, "checkMode");
        valueEventListenerStatus = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                stateMode = Integer.valueOf(String.valueOf(map.get("mode")));

                if (mDataControl != null) {
                    loadDataControl(stateMode);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mStatus.addValueEventListener(valueEventListenerStatus);
    }
}

