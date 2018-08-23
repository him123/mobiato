package com.ae.benchmark.bgservices;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Collection;
import com.ae.benchmark.model.Customer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.ae.benchmark.bgservices.action.FOO";
    private static final String ACTION_BAZ = "com.ae.benchmark.bgservices.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.ae.benchmark.bgservices.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.ae.benchmark.bgservices.extra.PARAM2";

    public UploadIntentService() {
        super("UploadIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UploadIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UploadIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    public class AsyncTaskREST extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            HttpURLConnection urlConnection = null;
//            String json = null;
//            // The Username & Password
////            final EditText em =  (EditText) findViewById(R.id.Username);
////            String email = (String) em.getText().toString();
////            final EditText pw =  (EditText) findViewById(R.id.Password);
////            String password = (String) pw.getText().toString();
//            // -----------------------
//
//
//            DBManager db = new DBManager(UploadIntentService.this);
//            ArrayList<Customer> customers = db.getAllCust();
//
//            ArrayList<Collection> collections = db.getAllCollections();
//
//
//            try {
//                HttpResponse response;
//                JSONObject jsonObject = new JSONObject();
////                jsonObject.accumulate("email", "");
////                jsonObject.accumulate("password", "");
//                json = jsonObject.toString();
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost("http://46.235.93.243:8047/sap/opu/odata/sap/ZSFA_CUSTOMER_ORDER_SRV/SOHeaders/");
//                httpPost.setEntity(new StringEntity(reqBody, "UTF-8"));
//                httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
//                httpPost.setHeader("Accept", "application/json");
//                httpPost.setHeader("X-Requested-With", "application/json");
//                httpPost.setHeader("Accept-Language", "en-US");
//                response = httpClient.execute(httpPost);
//                String sresponse = response.getEntity().toString();
//                Log.w("QueingSystem", sresponse);
//                Log.w("QueingSystem", EntityUtils.toString(response.getEntity()));
//            } catch (Exception e) {
//                Log.d("InputStream", e.getLocalizedMessage());
//
//            } finally {
//                /* nothing to do here */
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (result != null) {
//                // do something
//            } else {
//                // error occured
//            }
//        }
//    }
}
