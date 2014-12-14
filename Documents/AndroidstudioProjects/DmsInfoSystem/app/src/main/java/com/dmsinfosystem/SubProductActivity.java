package com.dmsinfosystem;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import jxl.read.biff.BiffException;
import jxl.read.biff.File;
import jxl.*;
import jxl.read.biff.WorkbookParser;
/**
 * Created by root on 8/12/14.
 */

public class SubProductActivity extends Activity{
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;

    private TextView mSubProduct;
    private String SubProductHeading;

    private List<String> HostingPackages = new ArrayList<String>();

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
        SubProductHeading = product.getStringExtra("subProduct");
        mSubProduct.setText( SubProductHeading );

        initialiseList(SubProductHeading);

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

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void initialiseList(String subProductHeading) {

        List<String> resultSet = new ArrayList<String>();

        String packageName;
        File inputWorkbook = new File(new byte[]{});
        //File inputWorkbook = new File();

        InputStream inputStream = getResources().openRawResource(R.raw.hostingpackages);
        /*try {
            inputWorkbook = new File(convertStreamToByteArray(inputStream));

        }catch (IOException e){
            Log.i("File Reader", "IO Exception");
            e.printStackTrace();
        }*/
        if(null != inputWorkbook){
            Workbook w;
            try {
                //w = Workbook.getWorkbook(inputWorkbook);
                //w = Workbook.getWorkbook(inputWorkbook);
                w = Workbook.getWorkbook(inputStream);
                // Get the first sheet
                Log.i("File Reader", "Number of Sheets: " + String.valueOf(w.getNumberOfSheets()) );

                Sheet sheet = w.getSheet(0);
                Log.i("File Reader", "Columns: " + String.valueOf(sheet.getColumns()) );

                // Loop over column and lines
                for (int j = 0; j < sheet.getColumns(); j++) {
                    resultSet = new ArrayList<String>();
                    Cell cell = sheet.getCell(j, 0);
                    packageName = cell.getContents();
                    if(packageName!="") {
                        HostingPackages.add(subProductHeading + packageName);
                    }else{
                        Log.i("File Reader", "Columns Ended");
                        break;
                    }
                    if(!cell.getContents().isEmpty()){
                        for (int i = 1; i < sheet.getRows(); i++) {
                            Cell cel = sheet.getCell(j, i);
                            resultSet.add(cel.getContents());
                            Log.i("File Reader","Cell Contents: "+cel.getContents());
                      }
                    }
                    if(resultSet.size()!=0) {
                        ChildPackages.put(subProductHeading + packageName, resultSet);
                    }else{
                        Log.i("File Reader", "File Ended");
                        break;
                    }
                    //continue;
                }
            } catch (BiffException e) {
                e.printStackTrace();
                Log.i("File Reader", "BIFF Exception");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("File Reader", "Other Exceptions");
            }
        }
        else
        {
            resultSet.add("File not found..!");
            Log.i("File Reader", "File not Found");
        }
        if(resultSet.size()==0){
            //resultSet.add("Data not found..!");
            Log.i("File Reader", "Data not Found");
        }

       /* BasicPackages.add("100 MB Web Space");
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
        ChildPackages.put(mHostingPackages[3],PremiumPlusPackages);*/
    }

    public static byte[] convertStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[is.available()];
        int i = Integer.MAX_VALUE;
        while ((i = is.read(buff, 0, buff.length)) > 0) {
            baos.write(buff, 0, i);
        }

        return baos.toByteArray(); // be sure to close InputStream in calling function
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

