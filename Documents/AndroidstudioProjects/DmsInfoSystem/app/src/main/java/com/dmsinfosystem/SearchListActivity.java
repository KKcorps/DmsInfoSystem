package com.dmsinfosystem;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 11/12/14.
 */
public class SearchListActivity extends Activity{
    private String[] mproducts;
    private List<String> mAllProducts = new ArrayList<String>();
    private ArrayList<String> products;
    private EditText editText;
    private String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchlist);

        mAllProducts.addAll(Arrays.asList(getResources().getStringArray(R.array.productDesign)));
        mAllProducts.addAll(Arrays.asList(getResources().getStringArray(R.array.productDevelopment)));
        mAllProducts.addAll(Arrays.asList(getResources().getStringArray(R.array.productHosting)));
        mAllProducts.addAll(Arrays.asList(getResources().getStringArray(R.array.productServices)));
        mAllProducts.addAll(Arrays.asList(getResources().getStringArray(R.array.productDigital)));

        query = getIntent().getStringExtra(SearchManager.QUERY);
        ListView lv = (ListView) findViewById(R.id.searchlistview);
        editText = (EditText) findViewById(R.id.searchEditText);

        editText.setText(query);

        final ArrayAdapter ArAdapter = new ArrayAdapter<String>(this, R.layout.subtext, mAllProducts);
        ArAdapter.getFilter().filter(query);
        lv.setAdapter(ArAdapter);
        //getListView().setAdapter(ArAdapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    ArAdapter.getFilter().filter(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView activityName = (TextView) view;
                Intent SubActivityFromSearch = new Intent(SearchListActivity.this, SubProductActivity.class);
                SubActivityFromSearch.putExtra("subProduct",activityName.getText().toString());
                startActivity(SubActivityFromSearch);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
