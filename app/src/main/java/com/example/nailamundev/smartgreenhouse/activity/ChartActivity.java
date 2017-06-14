package com.example.nailamundev.smartgreenhouse.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.fragment.ChartValueFragment;

public class ChartActivity extends AppCompatActivity {

    Toolbar toolbarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initInstances();

        // Fragment
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerChart, ChartValueFragment.newInstance())
                    .commit();

        }
    }


    private void initInstances() {
        toolbarChart = (Toolbar) findViewById(R.id.toolbarChart);
        setSupportActionBar(toolbarChart);
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
