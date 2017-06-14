package com.example.nailamundev.smartgreenhouse.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.fragment.AccountGreenhouseFragment;

public class AccountGreenhouseActivity extends AppCompatActivity {

    private static final String KEY_ITEM = "keyItem";
    private static final String KEY_BUNDLE = "keyBundle";
    String keyItem;
    Toolbar toolbarAccountGreenhouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_greenhouse);
        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE);
        keyItem = bundle.getString(KEY_ITEM);
        setTitle(R.string.account);

        // Fragment
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerAccount,
                            AccountGreenhouseFragment.newInstance(keyItem))
                    .commit();

        }
        initInstances();
    }

    private void initInstances() {
        toolbarAccountGreenhouse = (Toolbar) findViewById(R.id.toolbarAccountGreenhouse);
        setSupportActionBar(toolbarAccountGreenhouse);
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
