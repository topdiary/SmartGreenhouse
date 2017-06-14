package com.example.nailamundev.smartgreenhouse.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemDao;
import com.example.nailamundev.smartgreenhouse.fragment.NotificationGreenhouseFragment;

public class NotificationGreenhouseActivity extends AppCompatActivity {

    private static final String KEY_ITEM = "keyItem";
    private static final String KEY_BUNDLE = "keyBundle";
    private static final String DAO = "dao";
    String keyItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE);
        keyItem = bundle.getString(KEY_ITEM);
        GreenhousesItemDao dao = getIntent().getParcelableExtra(DAO);
        setTitle(getString(R.string.notificationGreenhouse) + " " + dao.name);
        setContentView(R.layout.activity_notification_greenhouse);

        initInstances();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerNfGreenhouse,
                            NotificationGreenhouseFragment.newInstance(keyItem))
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
