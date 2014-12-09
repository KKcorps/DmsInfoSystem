package com.dmsinfosystem;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class HomeScreen extends Activity {

    String TAG = "DMS  Activity";
    String PRO = "product";
    int REQUEST = 101;
    public static TextView nameDisplay;
    public static TextView linkView;
    private String[] mProducts;
    private String[][] mSubProducts;
    private String[] mProductNames;
    private String subProduct;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout imageGallery;
    private HorizontalScrollView mHorizontalScrollViewTop;
    private int[] ImageArray = {R.drawable.webd,R.drawable.webhosting,R.drawable.seo,R.drawable.ecommerce};
    private String[] PopularProductsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PopularProductsArray = getResources().getStringArray(R.array.popularProducts);
        //Setting images for Horizontal Scroller
        imageGallery=(LinearLayout) findViewById(R.id.linearImage);

        mHorizontalScrollViewTop = (HorizontalScrollView) findViewById(R.id.HSVImage);
        for(int i=0; i<ImageArray.length; i++){

            ImageView image=new ImageView(HomeScreen.this);
            TextView TitleView =  new TextView(HomeScreen.this);
            LinearLayout mDivision = new LinearLayout(HomeScreen.this);
            TitleView.setText(PopularProductsArray[i]);
            image.setBackgroundResource(ImageArray[i]);

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(100,100);
            mDivision.setLayoutParams(imageParams);
            mDivision.addView(image);
            mDivision.addView(TitleView);
            imageGallery.addView(mDivision);
            //imageGallery.addView(mDivision);
        }

        //Setting up Navigation Drawer
        mProducts = getResources().getStringArray(R.array.product);
        //mSubProducts = getResources().getStringArray(R.array.subProduct);

        //Log.i(TAG, mAppTitles[0]);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //Toggle Drawer with action Bar
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.ic_launcher,R.string.app_name,R.string.products_drawer);

        CustomAdapter mAdapter = new CustomAdapter(this,R.layout.textheader,R.layout.subtext);

        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.subProduct);
        int n = ta.length();
        String[][] array = new String[n][];
        for (int i = 0; i < n; ++i) {
            mAdapter.addItem(mProducts[i]);

            int id = ta.getResourceId(i, 0);

            if (id > 0) {
                array[i] = res.getStringArray(id);
                for(int j=0;j<array[i].length;j++){
                    mAdapter.addSectionHeaderItem(array[i][j]);
                }
            }
        }

        ta.recycle(); // Important!
        /*for (int i = 1; i < mProducts.length ; i++) {
            mAdapter.addItem(mProducts[i]);
            subProduct = PRO + mProducts[i];
            mProductNames = mSubProducts[i];
            for (int j = 1;j < mSubProducts.length;j++ ){

                mAdapter.addSectionHeaderItem(mSubProducts[j]);
            }

        }*/


        // Set the adapter for the list view
        mDrawerList.setAdapter(mAdapter);
        Log.i(TAG,"String Adapter set");

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        Log.i(TAG, "Listener set");

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(R.string.drawer_close);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.drawer_open);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            Log.i("Navigation", String.valueOf(position) +" In Item click listened method");
            Intent productIntent = new Intent(HomeScreen.this, SubProductActivity.class);

            CustomAdapter newAdapter = (CustomAdapter) parent.getAdapter();
            String mSubProductHeading = newAdapter.getItem(position);

            productIntent.putExtra("subProduct",mSubProductHeading);
            startActivity(productIntent);

        }
    }


    /** Starts activity corresponding to the drawer **/

    private void selectItem(int position) {
        Intent navigationDrawerActivity;
        Log.i(TAG, String.valueOf(position));

        /*switch (position) {
            case 1:

                navigationDrawerActivity = new Intent(Homescreen.this, DMS.class);
                //navigationDrawerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(navigationDrawerActivity);
                break;

            case 2:
                navigationDrawerActivity = new Intent(Homescreen.this, QEA.class);
                //navigationDrawerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(navigationDrawerActivity);
                break;

            case 3:
                navigationDrawerActivity = new Intent(Homescreen.this, NGM.class);
                //navigationDrawerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(navigationDrawerActivity);
                break;

            default:
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
        }*/

        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawers();

        //setTitle(mPlanetTitles[position]);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
