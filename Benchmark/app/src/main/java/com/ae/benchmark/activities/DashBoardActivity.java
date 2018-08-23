package com.ae.benchmark.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.ae.benchmark.adapters.FragmentPrint;
import com.ae.benchmark.fragments.DashboardFragment;
import com.ae.benchmark.fragments.FragmentAudit;
import com.ae.benchmark.fragments.FragmentCustomerSelection;
import com.ae.benchmark.fragments.FragmentDashboardOne;
import com.ae.benchmark.fragments.FragmentJourneyPlan;
import com.ae.benchmark.fragments.FragmentManageInventory;
import com.ae.benchmark.fragments.FragmentPayments;
import com.ae.benchmark.fragments.FragmentSales;
import com.ae.benchmark.fragments.FragmentSettings;
import com.ae.benchmark.fragments.InvoiceSummury;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.PrinterHelper;
import com.ae.benchmark.util.UtilApp;
import com.google.android.gms.maps.model.Dash;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private static final String TAG_DATA_POSTING = "Data_Posting_Audit";
    private static final String TAG_PRINT = "Print";
    private static final String TAG_SETTINGS = "Settings";
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
    private ImageView img_logout;
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

    String isEnd = "0";
    public boolean isMenu = false;
    public Menu menu;
    String reqBody = "{\"d\":{\"Currency\":\"SAR\",\"OrderValue\":\"2000\",\"OrderId\":\"0\"," +
            "\"TripId\":\"C111000000000969\",\"Division\":\"00\",\"SalesOrg\"" +
            ":\"1000\",\"DistChannel\":\"10\",\"Function\":\"HHTIV\",\"PurchaseNum\":" +
            "\"9690300001\",\"CustomerId\":\"0000200026\",\"DocumentType\":" +
            "\"ZVAN\",\"SOItems\":[{\"Storagelocation\":\"\",\"Material\":" +
            "\"000000000014000000\",\"ItemValue\":\"17.50\",\"Plant\":\"\",\"Description\":" +
            "\"Berain 48*200ml Carton\",\"UoM\":\"CAR\",\"Value\":\"17.50\",\"Quantity\":" +
            "\"5\",\"Item\":\"0010\",\"Route\":\"C11100\"}]}}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.inject(this);

//        new AsyncTaskREST().execute();


//        String str = "INV0000000000";
//        String digits = str.replaceAll("[^0-9.]", "");
//        str.replaceAll("[^\\d.]", "");;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        db = new DBManager(DashBoardActivity.this);
        db.open();
        mHandler = new Handler();

//        Log.v("", "Check items: " + db.getAllItems().get(1).item_name_en.toString());

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);

        TextView txt_route = (TextView) navHeader.findViewById(R.id.txt_route);
        TextView txt_salemane_no = (TextView) navHeader.findViewById(R.id.txt_salemane_no);
        TextView txt_vehicle_no = (TextView) navHeader.findViewById(R.id.txt_vehicle_no);
        TextView txt_channel = (TextView) navHeader.findViewById(R.id.txt_channel);

        txtName.setText(UtilApp.ReadSharePrefrenceString(DashBoardActivity.this, Constant.SALESMAN.SALESMAN_NAME));
        txt_route.setText("Route: " + UtilApp.ReadSharePrefrenceString(DashBoardActivity.this, Constant.SALESMAN.SALESMAN_ROUTE));
        txt_salemane_no.setText("Salesman ID: " + UtilApp.ReadSharePrefrenceString(DashBoardActivity.this, Constant.SALESMAN.SALESMAN_ID));
        txt_vehicle_no.setText("Vehicle No: " + UtilApp.ReadSharePrefrenceString(DashBoardActivity.this, Constant.SALESMAN.SALESMAN_VEHICLE));

        if (UtilApp.ReadSharePrefrenceString(DashBoardActivity.this, Constant.SALESMAN.SALESMAN_CHANNEL)
                .equalsIgnoreCase("20")) {
            txt_channel.setText("Channel: " + "Direct Distribution");
        } else if (UtilApp.ReadSharePrefrenceString(DashBoardActivity.this, Constant.SALESMAN.SALESMAN_CHANNEL)
                .equalsIgnoreCase("40")) {
            txt_channel.setText("Channel: " + "Horeca");
        } else if (UtilApp.ReadSharePrefrenceString(DashBoardActivity.this, Constant.SALESMAN.SALESMAN_CHANNEL)
                .equalsIgnoreCase("20")) {
            txt_channel.setText("Channel: " + "Home Delivery");
        }


//        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
//        txt_unique_id = (TextView) navHeader.findViewById(R.id.txt_unique_id);
//        imgNavHeaderBg = (View) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);


        img_logout = (ImageView) navHeader.findViewById(R.id.img_logout);

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DashBoardActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Logout!")
                        .setContentText("Are you sure want to logout?")
                        .setCancelText("No")
                        .setConfirmText("Yes")

                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                UtilApp.WriteSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISLOGIN, false);
                                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                startActivity(intent);
                                finish(); // call this to finish the current activity
                            }
                        })
                        .show();
            }
        });

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


        if (Constant.NAV_AUDIT.equals("yes")) {
            navItemIndex = 4;
            CURRENT_TAG = TAG_DATA_POSTING;
            loadHomeFragment();
            Constant.NAV_AUDIT = "no";
        } else if (Constant.IS_VAN_STOCK.equals("yes")) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_MANAGE_LOAD;
            loadHomeFragment();
        } else if (Constant.PRINT.equals("yes")) {
            navItemIndex = 4;
            CURRENT_TAG = TAG_PRINT;
            loadHomeFragment();
        } else if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isEnd = extras.getString("end");

        }

        if (isEnd.equals("1")) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }

//        if (UtilApp.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                DashBoardActivity.this)) {
//            createFile();
//        } else {
//            ActivityCompat.requestPermissions(DashBoardActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    1);
//        }
    }

    public void createFile() {

        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");
            if (!file.exists()) {
                file.createNewFile();
                byte[] data1 = {1, 1, 0, 0};
                //write the bytes in file
                if (file.exists()) {
                    OutputStream fo = new FileOutputStream(file);
                    fo.write(data1);
                    fo.close();
                    System.out.println("========== file created: ==========" + file);
//            url = upload.upload(file);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        Menu menuNav1 = navigationView.getMenu();
        MenuItem nav_home = menuNav1.findItem(R.id.nav_home);
        MenuItem nav_inventory = menuNav1.findItem(R.id.nav_inventory);
        MenuItem nav_journey = menuNav1.findItem(R.id.nav_journey);
        MenuItem nav_payment = menuNav1.findItem(R.id.nav_payment);
        //MenuItem nav_data = menuNav1.findItem(R.id.nav_data);
        MenuItem nav_print = menuNav1.findItem(R.id.nav_print);
        MenuItem nav_settings = menuNav1.findItem(R.id.nav_settings);


        boolean isEndDay = UtilApp.ReadSharePrefrence(getApplicationContext(),
                Constant.SHRED_PR.IS_DAY_END);

        String endDate = UtilApp.ReadSharePrefrenceString(getApplicationContext(),
                Constant.SHRED_PR.END_DATE);

        if (isEndDay) {
            if (endDate.equals(UtilApp.getCurrentDate())) {
                nav_home.setEnabled(true);
                nav_inventory.setEnabled(false);
                nav_journey.setEnabled(false);
                nav_payment.setEnabled(false);
                //nav_data.setEnabled(true);
                nav_print.setEnabled(true);
            } else {

                UtilApp.WriteSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.IS_DAY_END, false);

                nav_home.setEnabled(true);
                nav_inventory.setEnabled(true);
                nav_journey.setEnabled(false);
                nav_payment.setEnabled(false);
                //nav_data.setEnabled(true);
                nav_print.setEnabled(true);
            }

        } else {

            if (UtilApp.ReadSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISJPLOADED))
                nav_journey.setEnabled(true);
            else
                nav_journey.setEnabled(false);

            if (UtilApp.ReadSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISPAYMET))
                nav_payment.setEnabled(true);
            else
                nav_payment.setEnabled(false);

            //nav_data.setEnabled(true);
            nav_inventory.setEnabled(true);


        }
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
                FragmentJourneyPlan moviesFragment = new FragmentJourneyPlan();
                return moviesFragment;
            case 3:
                // PAYMENTS
                FragmentPayments connectionsFragment = new FragmentPayments();
                return connectionsFragment;
            case 4:
                // DATA POSTING AUDIT
                FragmentAudit employeeCodeSearchFragment = new FragmentAudit();
                return employeeCodeSearchFragment;
            case 5:
                // SETTINGS
                FragmentSettings shareAppFragment = new FragmentSettings();
                return shareAppFragment;

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
                        isMenu = false;
                        onPrepareOptionsMenu(menu);
                        break;
                    case R.id.nav_inventory:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_MANAGE_LOAD;
                        isMenu = false;
                        onPrepareOptionsMenu(menu);
                        break;
                    case R.id.nav_journey:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_JOURNEY_PLAN;
                        isMenu = true;
                        onPrepareOptionsMenu(menu);
                        break;
                    case R.id.nav_payment:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_PAYMENTS;
                        isMenu = false;
                        onPrepareOptionsMenu(menu);
                        break;
                    case R.id.nav_print:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_PRINT;
                        break;

                    case R.id.nav_settings:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_SETTINGS;
//                        isMenu = false;
//                        onPrepareOptionsMenu(menu);
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


                Menu menuNav1 = navigationView.getMenu();
                MenuItem nav_home = menuNav1.findItem(R.id.nav_home);
                MenuItem nav_inventory = menuNav1.findItem(R.id.nav_inventory);
                MenuItem nav_journey = menuNav1.findItem(R.id.nav_journey);
                MenuItem nav_payment = menuNav1.findItem(R.id.nav_payment);
                //MenuItem nav_data = menuNav1.findItem(R.id.nav_data);
                MenuItem nav_print = menuNav1.findItem(R.id.nav_print);
                MenuItem nav_settings = menuNav1.findItem(R.id.nav_settings);


                boolean isEndDay = UtilApp.ReadSharePrefrence(getApplicationContext(),
                        Constant.SHRED_PR.IS_DAY_END);

                String endDate = UtilApp.ReadSharePrefrenceString(getApplicationContext(),
                        Constant.SHRED_PR.END_DATE);

                if (isEndDay) {
                    if (endDate.equals(UtilApp.getCurrentDate())) {
                        nav_home.setEnabled(true);
                        nav_inventory.setEnabled(false);
                        nav_journey.setEnabled(false);
                        nav_payment.setEnabled(false);
                        //nav_data.setEnabled(true);
                        nav_print.setEnabled(true);
                    } else {

                        UtilApp.WriteSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.IS_DAY_END, false);

                        nav_home.setEnabled(true);
                        nav_inventory.setEnabled(true);
                        nav_journey.setEnabled(false);
                        nav_payment.setEnabled(false);
                        //nav_data.setEnabled(true);
                        nav_print.setEnabled(true);
                    }

                } else {

                    if (UtilApp.ReadSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISJPLOADED))
                        nav_journey.setEnabled(true);
                    else
                        nav_journey.setEnabled(false);

                    if (UtilApp.ReadSharePrefrence(DashBoardActivity.this, Constant.SHRED_PR.ISPAYMET))
                        nav_payment.setEnabled(true);
                    else
                        nav_payment.setEnabled(false);

                    //nav_data.setEnabled(true);
                    nav_inventory.setEnabled(true);


                }

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_select_customer, menu);
//
//        this.menu = menu;
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        switch (item.getItemId()) {
//
//            case R.id.nav_stock: {
//
//                Intent intent = new Intent(getApplicationContext(), VanStockActivity.class);
//                startActivity(intent);
//                isMenu = false;
//                break;
//            }
//
//            case R.id.nav_dashboard: {
//                navItemIndex = 0;
//                CURRENT_TAG = TAG_HOME;
//                loadHomeFragment();
//                isMenu = false;
//                onPrepareOptionsMenu(menu);
//                break;
//            }
//
//            case R.id.nav_sales: {
//
//                Intent intent = new Intent(getApplicationContext(), SaleHistoyyActivity.class);
//                startActivity(intent);
//                isMenu = false;
//                break;
//            }
//
//            case R.id.nav_map:
//                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//                startActivity(intent);
//                Toast.makeText(getBaseContext(), "You selected Map", Toast.LENGTH_SHORT).show();
//                break;
//
//        }
//        return true;
//
//    }

//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//        MenuItem nav_stock = menu.findItem(R.id.nav_stock);
//        nav_stock.setVisible(isMenu);
//
//        MenuItem nav_dashboard = menu.findItem(R.id.nav_dashboard);
//        nav_dashboard.setVisible(isMenu);
//
//        MenuItem nav_sales = menu.findItem(R.id.nav_sales);
//        nav_sales.setVisible(isMenu);
//
//        MenuItem nav_Print = menu.findItem(R.id.nav_Print);
//        nav_Print.setVisible(isMenu);
//
//        MenuItem nav_map = menu.findItem(R.id.nav_map);
//        nav_map.setVisible(isMenu);
//
//        return true;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    createFile();
                } else {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashBoardActivity.this);
                    alertDialogBuilder.setMessage("Kindly grant all permission, we respect your privacy and data!");
                    alertDialogBuilder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

                                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null)));
                                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                                    }
                                }
                            });

                    alertDialogBuilder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                return;
            }
        }
    }

    public class AsyncTaskREST extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            String json = null;
            // The Username & Password
//            final EditText em =  (EditText) findViewById(R.id.Username);
//            String email = (String) em.getText().toString();
//            final EditText pw =  (EditText) findViewById(R.id.Password);
//            String password = (String) pw.getText().toString();
            // -----------------------

            try {
                HttpResponse response;
                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("email", "");
//                jsonObject.accumulate("password", "");
                json = jsonObject.toString();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://46.235.93.243:8047/sap/opu/odata/sap/ZSFA_CUSTOMER_ORDER_SRV/SOHeaders/");
                httpPost.setEntity(new StringEntity(reqBody, "UTF-8"));
                httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("X-Requested-With", "application/json");
                httpPost.setHeader("Accept-Language", "en-US");
                response = httpClient.execute(httpPost);
                String sresponse = response.getEntity().toString();
                Log.w("QueingSystem", sresponse);
                Log.w("QueingSystem", EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());

            } finally {
                /* nothing to do here */
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (result != null) {
                // do something
            } else {
                // error occured
            }
        }
    }

}