package com.example.nailamundev.smartgreenhouse.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.nailamundev.smartgreenhouse.R;
import com.google.firebase.messaging.FirebaseMessaging;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class NotificationGreenhouseFragment extends Fragment implements View.OnClickListener {



    private static final String P_NAME = "NotificationGreenhouse_Config_";
    private static final String SWITCH_ALL = "switchAll";
    private static final String SWITCH_TEMPERATURE = "switchTemperature";
    private static final String SWITCH_HUMIDITY = "switchHumidity";
    private static final String SWITCH_SOIL = "switchSoil";
    private static final String SWITCH_LIGHT = "switchLight";
    private static final String SWITCH_WATER = "switchWater";

    private static final String TOPIC_TEMPERATURE = "_temperature";
    private static final String TOPIC_HUMIDITY = "_humidity";
    private static final String TOPIC_SOIL = "_soil";
    private static final String TOPIC_LIGHT = "_light";
    private static final String TOPIC_WATER = "_water";


    private Switch switchAll, switchTemperature, switchHumidity, switchSoil, switchLight,
            switchWater;
    private Boolean switchAllState, switchTemperatureState, switchHumidityState, switchSoilState,
            switchLightState, switchWaterState;                     // State Switch first open app
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String keyItem;


    /****************
     * Function
     ***************/


    public NotificationGreenhouseFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static NotificationGreenhouseFragment newInstance(String keyItem) {
        NotificationGreenhouseFragment fragment = new NotificationGreenhouseFragment();
        Bundle args = new Bundle();
        args.putString("keyItem", keyItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        keyItem = getArguments().getString("keyItem");
        Log.d("NotificationGreenhouseFragment", "Key : " + keyItem);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

        sp = getActivity().getSharedPreferences(P_NAME + keyItem, getContext().MODE_PRIVATE);
        editor = sp.edit();
        editor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_greenhouse,
                container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        switchAll = (Switch) rootView.findViewById(R.id.switchAll);
        switchTemperature = (Switch) rootView.findViewById(R.id.switchTemperature);
        switchHumidity = (Switch) rootView.findViewById(R.id.switchHumidity);
        switchSoil = (Switch) rootView.findViewById(R.id.switchSoil);
        switchLight = (Switch) rootView.findViewById(R.id.switchLight);
        switchWater = (Switch) rootView.findViewById(R.id.switchWater);

        switchAll.setOnClickListener(this);
        switchTemperature.setOnClickListener(this);
        switchHumidity.setOnClickListener(this);
        switchSoil.setOnClickListener(this);
        switchLight.setOnClickListener(this);
        switchWater.setOnClickListener(this);

        updateStateSwitch();

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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void updateStateSwitch() {
        switchAllState = sp.getBoolean(SWITCH_ALL, false);
        switchTemperatureState = sp.getBoolean(SWITCH_TEMPERATURE, false);
        switchHumidityState = sp.getBoolean(SWITCH_HUMIDITY, false);
        switchSoilState = sp.getBoolean(SWITCH_SOIL, false);
        switchLightState = sp.getBoolean(SWITCH_LIGHT, false);
        switchWaterState = sp.getBoolean(SWITCH_WATER, false);

        if (switchAllState) {
            switchAll.setChecked(true);
            switchTemperature.setChecked(true);
            switchHumidity.setChecked(true);
            switchSoil.setChecked(true);
            switchLight.setChecked(true);
            switchWater.setChecked(true);
        } else {
            switchAll.setChecked(false);
            switchTemperature.setChecked(switchTemperatureState);
            switchHumidity.setChecked(switchHumidityState);
            switchSoil.setChecked(switchSoilState);
            switchLight.setChecked(switchLightState);
            switchWater.setChecked(switchWaterState);

        }

    }


    private void doFunctionWater() {
        if (switchWater.isChecked()) {
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_WATER);
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_WATER);
        }
        editor.putBoolean(SWITCH_WATER, switchWater.isChecked());
        editor.commit();
    }

    private void doFunctionLight() {
        if (switchLight.isChecked()) {
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_LIGHT);
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_LIGHT);
        }
        editor.putBoolean(SWITCH_LIGHT, switchLight.isChecked());
        editor.commit();
    }

    private void doFunctionSoil() {
        if (switchSoil.isChecked()) {
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_SOIL);
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_SOIL);
        }
        editor.putBoolean(SWITCH_SOIL, switchSoil.isChecked());
        editor.commit();
    }

    private void doFunctionHumidity() {
        if (switchHumidity.isChecked()) {
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_HUMIDITY);
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_HUMIDITY);
        }

        editor.putBoolean(SWITCH_HUMIDITY, switchHumidity.isChecked());
        editor.commit();
    }

    private void doFunctionTemperature() {
        if (switchTemperature.isChecked()) {
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_TEMPERATURE);
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_TEMPERATURE);
        }
        editor.putBoolean(SWITCH_TEMPERATURE, switchTemperature.isChecked());
        editor.commit();
    }

    private void doFunctionAll() {
        if (switchAll.isChecked()) {
            switchTemperature.setChecked(true);
            switchHumidity.setChecked(true);
            switchSoil.setChecked(true);
            switchLight.setChecked(true);
            switchWater.setChecked(true);

            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_TEMPERATURE);
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_HUMIDITY);
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_SOIL);
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_LIGHT);
            FirebaseMessaging.getInstance().subscribeToTopic(keyItem + TOPIC_WATER);
        } else {
            switchTemperature.setChecked(false);
            switchHumidity.setChecked(false);
            switchSoil.setChecked(false);
            switchLight.setChecked(false);
            switchWater.setChecked(false);

            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_TEMPERATURE);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_HUMIDITY);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_SOIL);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_LIGHT);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keyItem + TOPIC_WATER);

        }
        editor.putBoolean(SWITCH_ALL, switchAll.isChecked());
        editor.putBoolean(SWITCH_TEMPERATURE, switchTemperature.isChecked());
        editor.putBoolean(SWITCH_HUMIDITY, switchHumidity.isChecked());
        editor.putBoolean(SWITCH_SOIL, switchSoil.isChecked());
        editor.putBoolean(SWITCH_LIGHT, switchLight.isChecked());
        editor.putBoolean(SWITCH_WATER, switchWater.isChecked());
        editor.commit();
    }



    /********************
     * Listener
     ********************/


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.switchAll:
                doFunctionAll();
                break;

            case R.id.switchTemperature:
                doFunctionTemperature();
                break;

            case R.id.switchHumidity:
                doFunctionHumidity();
                break;
            case R.id.switchSoil:
                doFunctionSoil();
                break;
            case R.id.switchLight:
                doFunctionLight();

                break;
            case R.id.switchWater:
                doFunctionWater();
                break;
        }
    }

}
