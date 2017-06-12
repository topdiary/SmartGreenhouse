package com.example.nailamundev.smartgreenhouse.fragment;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.dao.AccountGreenhouse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

import es.dmoral.toasty.Toasty;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ProductFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private static final String UID = "uid";
    private static final String KEY_ITEM = "keyItem";

    private String keyItem, resultCategory;
    private Spinner spinnerProduct;
    private ArrayAdapter<CharSequence> adapter;
    private EditText editTextAmount, editTextNote;
    private TextView tvDateResult, tvTimeResult;
    private Button btnCancel, btnOk;
    private String formattedDate;
    private int amount;

    //Firebase
    private DatabaseReference mDatabase, mProductRef;

    /****************
     * Function
     ***************/
    public ProductFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ProductFragment newInstance(String keyItem) {
        ProductFragment fragment = new ProductFragment();
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
        mProductRef = mDatabase.child("product").child(UID).child(keyItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_greenhouse, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        editTextAmount = (EditText) rootView.findViewById(R.id.editTextAmount);
        editTextNote = (EditText) rootView.findViewById(R.id.editTextNote);
        tvDateResult = (TextView) rootView.findViewById(R.id.tvDateResult);
        tvTimeResult = (TextView) rootView.findViewById(R.id.tvTimeResult);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnOk = (Button) rootView.findViewById(R.id.btnOk);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        formattedDate = df.format(c.getTime());
        String formatDate = date.format(c.getTime());
        String formatTime = time.format(c.getTime());
        tvDateResult.setText(formatDate);
        tvTimeResult.setText(formatTime);

        spinnerProduct = (Spinner) rootView.findViewById(R.id.spinnerProduct);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_product, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(adapter);
        spinnerProduct.setOnItemSelectedListener(this);
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


    private void sendDataToFirebase() {
        try {
            amount = Integer.parseInt(editTextAmount.getText().toString());
        } catch (NumberFormatException e) {

        }

        AccountGreenhouse accountGreenhouse = new AccountGreenhouse(amount, resultCategory,
                editTextNote.getText().toString(), formattedDate);
        mProductRef.push().setValue(accountGreenhouse);
        Toasty.success(getActivity(), getString(R.string.success), Toast.LENGTH_SHORT, true).show();
        doFunctionButtonCancel();
    }


    private void doFunctionButtonCancel() {
        editTextAmount.setText("");
        editTextNote.setText("");
        spinnerProduct.setSelection(0);
    }


    /********************
     * Listener
     ********************/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                doFunctionButtonCancel();
                break;

            case R.id.btnOk:
                sendDataToFirebase();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        resultCategory = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
