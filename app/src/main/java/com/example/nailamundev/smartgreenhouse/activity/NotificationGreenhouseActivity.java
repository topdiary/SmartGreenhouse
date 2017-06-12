package com.example.nailamundev.smartgreenhouse.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemDao;
import com.example.nailamundev.smartgreenhouse.fragment.NotificationGreenhouseFragment;
import com.example.nailamundev.smartgreenhouse.fragment.SettingGreenhouseFragment;

public class NotificationGreenhouseActivity extends AppCompatActivity {

    String keyItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getBundleExtra("keyBundle");
        keyItem = bundle.getString("keyItem");
        GreenhousesItemDao dao = getIntent().getParcelableExtra("dao");
        setTitle(getString(R.string.notificationGreenhouse) +" "+ dao.name);
        setContentView(R.layout.activity_notification_greenhouse);

        initInstances();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerNfGreenhouse, NotificationGreenhouseFragment.newInstance(keyItem))
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
