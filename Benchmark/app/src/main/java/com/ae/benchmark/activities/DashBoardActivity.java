package com.ae.benchmark.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.fragments.DashboardFragment;
import com.ae.benchmark.fragments.FragmentCustomerSelection;
import com.ae.benchmark.fragments.FragmentDashboardOne;
import com.ae.benchmark.fragments.FragmentManageInventory;
import com.ae.benchmark.fragments.FragmentPayments;
import com.ae.benchmark.fragments.FragmentSales;
import com.ae.benchmark.fragments.InvoiceSummury;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import butterknife.ButterKnife;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by Rakshit on 12-Jan-17.
 */
/*
@ This activity will be called when the + button is clicked on the visit list or all customer screen.
@ This will create a customer in the backend.
*/
public class DashBoardActivity extends AppCompatActivity {

    // urls to load navigation header background image
    // and profile image
    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_MANAGE_LOAD = "mange_inventory";
    private static final String TAG_JOURNEY_PLAN = "Journey_Plan";
    private static final String TAG_PAYMENTS = "Payments";
    //    private static final String TAG_ROUTE_RECONE = "Route_Recone";
    private static final String TAG_SALE_SNAP = "Sales_Snap";
    private static final String TAG_DATA_POSTING = "Data_Posting_Audit";
    private static final String TAG_CATALOGUE = "Catalogue";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_SUGGESTIONS = "suggestions";
    private static final String TAG_RATE_APP = "RATE_APP";
    //new
    private static final String TAG_SHARE_APP = "SHARE_APP";
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = TAG_HOME;
    static Button notifCount;
    public String str_Post_Status;
    public int msgCount;
    public String str_reqCount;
    public int reqCount;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    //    private View imgNavHeaderBg;
    private ImageView imgProfile;
    private TextView txtName, txtWebsite, txt_unique_id;
    private Toolbar toolbar;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private TextView gallery_message, gallery_request;
    TextView mTitle;

    DBManager db;
//    MaterialShowcaseSequence sequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        db = new DBManager(DashBoardActivity.this);
        db.open();
        mHandler = new Handler();

        Log.v("","Check items: "+db.getAllItems().get(1).item_name_en.toString());

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);

//        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
//        txt_unique_id = (TextView) navHeader.findViewById(R.id.txt_unique_id);
//        imgNavHeaderBg = (View) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles_own);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

//        sequence = new MaterialShowcaseSequence(this, "Main");
//        ShowcaseConfig config = new ShowcaseConfig();
//        config.setDelay(500); // half second between each showcase view
//        sequence.setConfig(config);
//
//        sequence.addSequenceItem(navigationView,
//                "By pressing this Menu button you will find more options to explore.", "GOT IT");
//
//        sequence.start();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Menu menuNav = navigationView.getMenu();

        MenuItem nav_itemMI = menuNav.findItem(R.id.nav_inventory);
        MenuItem nav_itemJP = menuNav.findItem(R.id.nav_journey);
        MenuItem nav_itemMIPy = menuNav.findItem(R.id.nav_payment);
        MenuItem nav_itemSa = menuNav.findItem(R.id.nav_sales);
        MenuItem nav_itemDa = menuNav.findItem(R.id.nav_data);

        if (UtilApp.ReadSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISJPLOADED))
            nav_itemJP.setEnabled(true);
        else
            nav_itemJP.setEnabled(false);

        if (UtilApp.ReadSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISPAYMET))
            nav_itemMIPy.setEnabled(true);
        else
            nav_itemMIPy.setEnabled(false);

        if (UtilApp.ReadSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISSALES))
            nav_itemSa.setEnabled(true);
        else
            nav_itemSa.setEnabled(false);

        if (UtilApp.ReadSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISDATA))
            nav_itemDa.setEnabled(true);
        else
            nav_itemDa.setEnabled(false);
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {

        try {
            // name, website
//            txtName.setText("Username");
//            txtWebsite.setText("Sales no");
//
//            txt_unique_id.setText("uid");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("RestrictedApi")
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
//            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        // show or hide the fab button
//        toggleFab();
        //Closing drawer on item clickss
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_customer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.nav_import:
                Toast.makeText(getBaseContext(), "You selected Import", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_stock:
                Toast.makeText(getBaseContext(), "You selected Stock", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_dashboard:
                Toast.makeText(getBaseContext(), "You selected Dashboard", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sales:
                Toast.makeText(getBaseContext(), "You selected Sales", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Print:
                Toast.makeText(getBaseContext(), "You selected Print", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_cat:
                Toast.makeText(getBaseContext(), "You selected Catalogue", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_map:
                Intent intent = new Intent(getApplicationContext() , MapsActivity.class);
                startActivity(intent);
                break;

        }
        return true;

    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // HOME
                return new DashboardFragment().newInstance(getSupportFragmentManager());
            case 1:
                // MANAGE INVENTORY
                FragmentManageInventory mainRequestFragment = new FragmentManageInventory();
                return mainRequestFragment;
            case 2:
                // JOURNEY PLAN
                FragmentCustomerSelection moviesFragment = new FragmentCustomerSelection();
                return moviesFragment;
            case 3:
                // PAYMENTS
                FragmentPayments connectionsFragment = new FragmentPayments();
                return connectionsFragment;
//            case 4:
//                // ROUTE RECON
//                FragmentOrder messagesFragments = new FragmentOrder();
//                return messagesFragments;
            case 5:
                // SALES SNAP
                InvoiceSummury dailyActivityFragment = new InvoiceSummury();
                return dailyActivityFragment;
            case 6:
                // DATA POSTING AUDIT
                FragmentSales employeeCodeSearchFragment = new FragmentSales();
                return employeeCodeSearchFragment;
            case 7:
                // CATALOGUE
                FragmentSales settingsFragment = new FragmentSales();
                return settingsFragment;

            case 8:
                // SETTINGS
                FragmentSales shareAppFragment = new FragmentSales();
                return shareAppFragment;
            case 9:
                // SHARE APP
                FragmentManageInventory rateThisAppFragment = new FragmentManageInventory();
                return rateThisAppFragment;

            case 10:
                // RATE THIS APP
                FragmentSales suggestionsFragment = new FragmentSales();
                return suggestionsFragment;

            case 11:
                // QUERY AND SUGGESTIONS
                FragmentSales queryFragment = new FragmentSales();
                return queryFragment;


            default:
                return new DashboardFragment().newInstance(getSupportFragmentManager());
        }
    }

    private void setToolbarTitle() {
        mTitle.setText(activityTitles[navItemIndex]);
        getSupportActionBar().setTitle("");
    }

    private void selectNavMenu() {

        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action

                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_inventory:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_MANAGE_LOAD;
                        break;
                    case R.id.nav_journey:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_JOURNEY_PLAN;
                        break;
                    case R.id.nav_payment:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_PAYMENTS;
                        break;
//                    case R.id.nav_route:
//                        navItemIndex = 4;
//                        CURRENT_TAG = TAG_ROUTE_RECONE;
//                        break;
                    case R.id.nav_sales:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SALE_SNAP;
                        break;
                    case R.id.nav_data:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_DATA_POSTING;
                        break;
                    case R.id.nav_catalogue:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_CATALOGUE;
                        break;

                    case R.id.nav_settings:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;

                    case R.id.nav_share:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_SHARE_APP;
                        break;


                    case R.id.nav_rate: // Rate this app
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_RATE_APP;
//                        Intent intent = new Intent(MainActivity.this, RateThisAppActivity.class);
//                        startActivity(intent);
//                        drawer.closeDrawers();

                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                        break;

                    case R.id.nav_your_suggestions:
                        navItemIndex = 10;
                        CURRENT_TAG = TAG_SUGGESTIONS;
                        break;
                    default:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
