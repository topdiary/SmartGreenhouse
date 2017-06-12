package com.example.nailamundev.smartgreenhouse.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemDao;
import com.example.nailamundev.smartgreenhouse.fragment.MainFragment;
import com.example.nailamundev.smartgreenhouse.fragment.MoreInfoFragment;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

public class MoreInfoActivity extends AppCompatActivity {

    String keyItem;
    Toolbar toolbarGreenhouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Parcelable Extra
        Bundle bundle = getIntent().getBundleExtra("keyBundle");
        keyItem = bundle.getString("keyItem");
        GreenhousesItemDao dao = getIntent().getParcelableExtra("dao");
        setTitle(dao.name);
        setContentView(R.layout.activity_more_info);

        initInstances();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerMore, MoreInfoFragment.newInstance(keyItem, dao))
                    .commit();

        }

    }

    private void initInstances() {
        toolbarGreenhouse = (Toolbar) findViewById(R.id.toolbarGreenhouse);
        setSupportActionBar(toolbarGreenhouse);
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
