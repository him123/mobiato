package com.ae.benchmark.rest;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import static com.ae.benchmark.util.Constant.URL;


/**
 * Created by dgohil on 6/17/15.
 */
public class RestClient extends Activity {
    private static CommonService REST_CLIENT_MUTUAL_TRANSFER;

    static {
        setupRestClient();
    }

    private RestClient() {
    }

    private static void setupRestClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory()) // This is the important line ;)
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        REST_CLIENT_MUTUAL_TRANSFER = buildAdapter(URL, gson).create(CommonService.class);
    }

    private static RestAdapter buildAdapter(String endPoint, Gson gson) {

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(endPoint)
                .setConverter(new GsonConverter(gson))
                .build();
    }


    public static CommonService getMutualTransfer() {
        return REST_CLIENT_MUTUAL_TRANSFER;
    }


}
