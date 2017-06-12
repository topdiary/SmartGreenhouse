package com.example.nailamundev.smartgreenhouse.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemDao;
import com.example.nailamundev.smartgreenhouse.fragment.MainFragment;
import com.example.nailamundev.smartgreenhouse.fragment.SettingGreenhouseFragment;

public class SettingGreenhouseActivity extends AppCompatActivity {
    String keyItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Parcelable Extra
        Bundle bundle = getIntent().getBundleExtra("keyBundle");
        keyItem = bundle.getString("keyItem");
        GreenhousesItemDao dao = getIntent().getParcelableExtra("dao");
        setTitle(getString(R.string.auto) + " " + dao.name);
        Log.d("onCreate", "keyItem : " + keyItem);
        setContentView(R.layout.activity_setting_greenhouse);

        initInstances();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerSetting, SettingGreenhouseFragment.newInstance(keyItem, dao))
                    .commit();

        }

    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
