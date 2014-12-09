package com.dmsinfosystem;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 8/12/14.
 */
public class SubProductActivity extends Activity{
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;

    private TextView mSubProduct;
    private String SubProductHeading;

    private List<String> HostingPackages;
    private List<String> BasicPackages = new ArrayList<String>();
    private List<String> StandardPackages = new ArrayList<String>();
    private List<String> PremiumPackages = new ArrayList<String>();
    private List<String> PremiumPlusPackages = new ArrayList<String>();
    private HashMap<String, List<String> > ChildPackages = new HashMap<String, List<String> >() ;
    private static int j;
    private Intent product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        product = getIntent();

        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        mSubProduct = (TextView) findViewById(R.id.subProductHeading);
        mSubProduct.setText( product.getStringExtra("subProduct") );

        initialiseList();

        mExpandableListAdapter = new com.dmsinfosystem.ExpandableListAdapter(this, HostingPackages, ChildPackages);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(),"Click working", Toast.LENGTH_SHORT).show();
                Log.i("Expendables", "Group click listener working");
                return false;
            }
        });
    }

    protected void initialiseList() {

        BasicPackages.add("100 MB Web Space");
        BasicPackages.add("1 Website");
        BasicPackages.add("3 E-Mail IDs ");
        BasicPackages.add("2 Sub Domains");
        BasicPackages.add("2 MYSQL Database");
        BasicPackages.add("C-Panel – No Control Panel");


        StandardPackages.add("1024 MB Web Space");
        StandardPackages.add("1 Website");
        StandardPackages.add("5 E-Mail IDs ");
        StandardPackages.add("3 Sub Domains");
        StandardPackages.add("3 MYSQL Database");
        StandardPackages.add("C-Panel – No Control Panel");


        PremiumPackages.add("10 GB Web Space");
        PremiumPackages.add("1 Website");
        PremiumPackages.add("15 E-Mail IDs ");
        PremiumPackages.add("5 Sub Domains");
        PremiumPackages.add("5 MYSQL Database");
        PremiumPackages.add("C-Panel – No Control Panel");

        PremiumPlusPackages.add("Unlimited Web Space");
        PremiumPlusPackages.add("1 Website");
        PremiumPlusPackages.add("20 E-Mail IDs ");
        PremiumPlusPackages.add("10 Sub Domains");
        PremiumPlusPackages.add("10 MYSQL Database");
        PremiumPlusPackages.add("C-Panel – No Control Panel");

        String[] mHostingPackages = getResources().getStringArray(R.array.hostingPackages);
        HostingPackages = Arrays.asList(mHostingPackages);

        ChildPackages.put(mHostingPackages[0],BasicPackages);
        ChildPackages.put(mHostingPackages[1],StandardPackages);
        ChildPackages.put(mHostingPackages[2],PremiumPackages);
        ChildPackages.put(mHostingPackages[3],PremiumPlusPackages);
    }
}

