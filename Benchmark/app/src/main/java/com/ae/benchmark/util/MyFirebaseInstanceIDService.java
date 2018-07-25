package com.ae.benchmark.util;

/**
 * Created by Himanshu on 4/11/2017.
 */


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Belal on 5/27/2016.
 */


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {

        Log.d(TAG, " ======= Wrote token in sharedPreference: " + token);

        UtilApp.WriteSharePrefrence(MyFirebaseInstanceIDService.this, Constant.SHRED_PR.FCM_TOKEN, token);

        //You can implement this method to store the token on your server
        //Not required for current project
    }
}