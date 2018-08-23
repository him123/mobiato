package com.ae.benchmark.webservice;

import android.content.Context;
import android.util.Log;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.NetworkUtility;
import com.ae.benchmark.data.Const;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Collection;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Load;
import com.ae.benchmark.model.SalesInvoice;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WsPushData {

    private Context context;
    private String message = "";
    private boolean success = false;
    private DBManager db;

    public WsPushData(Context context) {
        this.context = context;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void executeWebservice() {
        db = new DBManager(context);
        db.open();
        final String url = Const.WS_URL + "ZSFA_CUSTOMER_ORDER_SRV/SOHeaders";

        //Invoices
        ArrayList<SalesInvoice> salesInvoices = db.getAllInvoiceHead();
        for (int i = 0; i < salesInvoices.size(); i++) {
            if (salesInvoices.get(i).getInv_type().equalsIgnoreCase("Sale")) {
                String json = generateInvoiceJson(salesInvoices.get(i));
                String response = NetworkUtility.postApiData(context, url, json);
                Log.e("Publish", "Response "+response);

            }
        }

        //Orders
        ArrayList<Item> orderList = db.getAllOrders();
        for (int i = 0; i < orderList.size(); i++) {
            String json = generateOrderJson(orderList.get(i));
            String response = NetworkUtility.postApiData(context, url, json);
            Log.e("Publish", "Response "+response);
        }


        //Load Request
        ArrayList<Load> loadList = db.getAllLoad("1");
        for (int i = 0; i < loadList.size(); i++) {
            String json = generateLoadRequestJson(loadList.get(i));
            String response = NetworkUtility.postApiData(context, url, json);
            Log.e("Publish", "Response "+response);
        }

        //Collection
        ArrayList<Collection> collectionList = db.getAllCollections();
        for (int i = 0; i < collectionList.size(); i++) {
            String json = generateCollectionJson(collectionList.get(i));
            String response = NetworkUtility.postApiData(context, url, json);
            Log.e("Publish", "Response "+response);
        }

        //Return
        ArrayList<Item> returnList = db.getAllReturns();
        for (int i = 0; i < returnList.size(); i++) {
            String json = generateReturnJson(orderList.get(i));
            String response = NetworkUtility.postApiData(context, url, json);
            Log.e("Publish", "Response "+response);
        }

        //Unload
        String json = generateUnloadJson();
        String response = NetworkUtility.postApiData(context, url, json);
        Log.e("Publish", "Response "+response);
        
    }

    private String generateInvoiceJson(SalesInvoice salesInvoice){

        String jsonInvoice = "";
        try {

            JSONObject jsonObjectMain = new JSONObject();
            JSONObject jsonObjectD = new JSONObject();
            double totalSale = 0;

            JSONArray array = new JSONArray();
            ArrayList<Item> salesItem = db.getAllInvoicItem(salesInvoice.getInv_no());

            for (int i = 0; i < salesItem.size(); i++) {
                JSONObject raw = new JSONObject();
                Item item = salesItem.get(i);
                double total = Double.parseDouble(item.getItem_price()) * Double.parseDouble(item.getItem_qty());
                totalSale += total;
                raw.put("Storagelocation", "");
                raw.put("Material", item.getItem_code());
                raw.put("ItemValue", item.getItem_price());
                raw.put("Plant", "");
                raw.put("Description", item.getItem_name_en());
                raw.put("UoM", "");
                raw.put("Value", String.valueOf(total));
                raw.put("Quantity", item.getItem_qty());
                raw.put("Item", "00"+(i+1)+"0");
                raw.put("Route", UtilApp.ReadSharePrefrenceString(context, Constant.SHRED_PR.USERNAME));
                array.put(raw);
            }

            jsonObjectD.put("Longitude", "0.0");
            jsonObjectD.put("Latitude", "0.0");
            jsonObjectD.put("OrderValue", String.valueOf(totalSale));
            jsonObjectD.put("Division", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_DIVISION));
            jsonObjectD.put("Currency", "SAR");
            jsonObjectD.put("OrderId", salesInvoice.getInv_no());
            jsonObjectD.put("TripId", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("Assignment", salesInvoice.getInv_no());
            jsonObjectD.put("SalesOrg",  salesInvoice.getCust_sales_org());
            jsonObjectD.put("DistChannel", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_CHANNEL));
            jsonObjectD.put("Function", "HHTIV");
            jsonObjectD.put("PurchaseNum", salesInvoice.getInv_no());
            jsonObjectD.put("CustomerId", salesInvoice.getCust_code());
            jsonObjectD.put("DocumentType", "Z5SO");
            jsonObjectD.put("SOItems", array);


            jsonObjectMain.put("d", jsonObjectD);
            jsonInvoice = jsonObjectMain.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonInvoice;
    }


    private String generateOrderJson(Item salesInvoice){

        String jsonInvoice = "";
        try {

            JSONObject jsonObjectMain = new JSONObject();
            JSONObject jsonObjectD = new JSONObject();
            double totalSale = 0;

            JSONArray array = new JSONArray();
            ArrayList<Item> salesItem = db.getOrderItems(salesInvoice.getItem_code());

            for (int i = 0; i < salesItem.size(); i++) {
                JSONObject raw = new JSONObject();
                Item item = salesItem.get(i);
                double total = Double.parseDouble(item.getItem_price()) * Double.parseDouble(item.getItem_qty());
                totalSale += total;
                raw.put("Storagelocation", "");
                raw.put("Material", item.getItem_code());
                raw.put("ItemValue", item.getItem_price());
                raw.put("Plant", "");
                raw.put("Description", item.getItem_name_en());
                raw.put("UoM", "");
                raw.put("Value", String.valueOf(total));
                raw.put("Quantity", item.getItem_qty());
                raw.put("Item", "00"+(i+1)+"0");
                raw.put("Route", UtilApp.ReadSharePrefrenceString(context, Constant.SHRED_PR.USERNAME));
                array.put(raw);
            }

            jsonObjectD.put("Longitude", "0.0");
            jsonObjectD.put("Latitude", "0.0");
            jsonObjectD.put("OrderValue", String.valueOf(totalSale));
            jsonObjectD.put("Division", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_DIVISION));
            jsonObjectD.put("Currency", "SAR");
            jsonObjectD.put("OrderId", salesInvoice.getItem_code());
            jsonObjectD.put("TripId", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("Assignment", salesInvoice.getItem_code());
            jsonObjectD.put("SalesOrg",  "");
            jsonObjectD.put("DistChannel", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_CHANNEL));
            jsonObjectD.put("Function", "ORDERREQ");
            jsonObjectD.put("PurchaseNum", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("CustomerId", salesInvoice.getCust_id());
            jsonObjectD.put("DocumentType", "Z5SO");
            jsonObjectD.put("SOItems", array);


            jsonObjectMain.put("d", jsonObjectD);
            jsonInvoice = jsonObjectMain.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonInvoice;
    }


    private String generateLoadRequestJson(Load load){

        String jsonInvoice = "";
        try {

            JSONObject jsonObjectMain = new JSONObject();
            JSONObject jsonObjectD = new JSONObject();
            double totalSale = 0;

            JSONArray array = new JSONArray();
            ArrayList<Item> loadItem = db.getLoadItem(load.load_no);

            for (int i = 0; i < loadItem.size(); i++) {
                JSONObject raw = new JSONObject();
                Item item = loadItem.get(i);
                double total = Double.parseDouble(item.getItem_price()) * Double.parseDouble(item.getItem_qty());
                totalSale += total;
                raw.put("Storagelocation", "");
                raw.put("Material", item.getItem_code());
                raw.put("ItemValue", item.getItem_price());
                raw.put("Plant", "");
                raw.put("Description", item.getItem_name_en());
                raw.put("UoM", item.getItem_uom());
                raw.put("Value", String.valueOf(total));
                raw.put("Quantity", item.getItem_qty());
                raw.put("Item", "00"+(i+1)+"0");
                raw.put("Route", UtilApp.ReadSharePrefrenceString(context, Constant.SHRED_PR.USERNAME));
                array.put(raw);
            }

            jsonObjectD.put("Longitude", "0.0");
            jsonObjectD.put("Latitude", "0.0");
            jsonObjectD.put("OrderValue", String.valueOf(totalSale));
            jsonObjectD.put("Division", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_DIVISION));
            jsonObjectD.put("Currency", "SAR");
            jsonObjectD.put("OrderId", load.load_no);
            jsonObjectD.put("TripId", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("SalesOrg",  "");
            jsonObjectD.put("DistChannel", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_CHANNEL));
            jsonObjectD.put("Function", "ORDERREQ");
            jsonObjectD.put("PurchaseNum", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("CustomerId", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("DocumentType", "Z5SO");
            jsonObjectD.put("SOItems", array);


            jsonObjectMain.put("d", jsonObjectD);
            jsonInvoice = jsonObjectMain.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonInvoice;
    }


    private String generateCollectionJson(Collection collection){

        String jsonInvoice = "";
        try {

            JSONObject jsonObjectMain = new JSONObject();
            JSONObject jsonObjectD = new JSONObject();

            JSONArray array = new JSONArray();

            JSONObject soItem = new JSONObject();
            soItem.put("OrderId", collection.coll_inv_no);
            soItem.put("Payreference", "");
            array.put(soItem);


            jsonObjectD.put("OrderValue", collection.coll_amount);
            jsonObjectD.put("Function", "COLLECTION");
            jsonObjectD.put("RefCust", collection.coll_cust_no);
            jsonObjectD.put("CustomerId", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("DocumentType", "Z5SO");
            jsonObjectD.put("SOItems", array);


            jsonObjectMain.put("d", jsonObjectD);
            jsonInvoice = jsonObjectMain.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonInvoice;
    }

    private String generateReturnJson(Item salesInvoice){

        String jsonInvoice = "";
        try {

            JSONObject jsonObjectMain = new JSONObject();
            JSONObject jsonObjectD = new JSONObject();
            double totalSale = 0;

            JSONArray array = new JSONArray();
            ArrayList<Item> salesItem = db.getReturnItems(salesInvoice.getItem_code());

            for (int i = 0; i < salesItem.size(); i++) {
                JSONObject raw = new JSONObject();
                Item item = salesItem.get(i);
                double total = Double.parseDouble(item.getItem_price()) * Double.parseDouble(item.getItem_qty());
                totalSale += total;
                raw.put("Storagelocation", "");
                raw.put("Material", item.getItem_code());
                raw.put("ItemValue", item.getItem_price());
                raw.put("Plant", "");
                raw.put("Description", item.getItem_name_en());
                raw.put("UoM", "");
                raw.put("Value", String.valueOf(total));
                raw.put("Quantity", item.getItem_qty());
                raw.put("Item", "00"+(i+1)+"0");
                raw.put("Route", UtilApp.ReadSharePrefrenceString(context, Constant.SHRED_PR.USERNAME));
                array.put(raw);
            }

            jsonObjectD.put("Longitude", "0.0");
            jsonObjectD.put("Latitude", "0.0");
            jsonObjectD.put("OrderValue", String.valueOf(totalSale));
            jsonObjectD.put("Division", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_DIVISION));
            jsonObjectD.put("Currency", "SAR");
            jsonObjectD.put("OrderId", salesInvoice.getItem_code());
            jsonObjectD.put("TripId", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("Assignment", salesInvoice.getItem_code());
            jsonObjectD.put("SalesOrg",  "");
            jsonObjectD.put("DistChannel", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_CHANNEL));
            jsonObjectD.put("Function", "RETURNS");
            jsonObjectD.put("PurchaseNum", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("CustomerId", salesInvoice.getCust_id());
            jsonObjectD.put("DocumentType", "Z5SO");
            jsonObjectD.put("SOItems", array);


            jsonObjectMain.put("d", jsonObjectD);
            jsonInvoice = jsonObjectMain.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonInvoice;
    }


    private String generateUnloadJson(){

        String jsonInvoice = "";
        try {

            JSONObject jsonObjectMain = new JSONObject();
            JSONObject jsonObjectD = new JSONObject();
            double totalSale = 0;

            JSONArray array = new JSONArray();
            ArrayList<Item> salesItem = db.getUnload();

            for (int i = 0; i < salesItem.size(); i++) {
                JSONObject raw = new JSONObject();
                Item item = salesItem.get(i);
                double total = Double.parseDouble(item.getItem_price()) * Double.parseDouble(item.getItem_qty());
                totalSale += total;
                raw.put("Storagelocation", "");
                raw.put("Material", item.getItem_code());
                raw.put("ItemValue", item.getItem_price());
                raw.put("Plant", "");
                raw.put("Description", item.getItem_name_en());
                raw.put("UoM", "");
                raw.put("Value", String.valueOf(total));
                raw.put("Quantity", item.getItem_qty());
                raw.put("Item", "00"+(i+1)+"0");
                raw.put("Route", UtilApp.ReadSharePrefrenceString(context, Constant.SHRED_PR.USERNAME));
                array.put(raw);
            }

            jsonObjectD.put("Longitude", "0.0");
            jsonObjectD.put("Latitude", "0.0");
            jsonObjectD.put("OrderValue", String.valueOf(totalSale));
            jsonObjectD.put("Division", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_DIVISION));
            jsonObjectD.put("Currency", "SAR");
            jsonObjectD.put("OrderId", "0");
            jsonObjectD.put("TripId", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("SalesOrg",  "");
            jsonObjectD.put("DistChannel", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_CHANNEL));
            jsonObjectD.put("Function", "UNLOAD");
            jsonObjectD.put("PurchaseNum", "");
            jsonObjectD.put("CustomerId", UtilApp.ReadSharePrefrenceString(context, Constant.SALESMAN.SALESMAN_ID));
            jsonObjectD.put("DocumentType", "Z5SO");
            jsonObjectD.put("SOItems", array);


            jsonObjectMain.put("d", jsonObjectD);
            jsonInvoice = jsonObjectMain.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonInvoice;
    }

    private void parseResponse(final String response) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("d")) {
                jsonObject = jsonObject.getJSONObject("d");
                JSONArray array = jsonObject.optJSONArray("results");
                if(array.length() > 0){
                    success = true;
                    DBManager dbManager = new DBManager(context);
                    dbManager.open();
                    dbManager.insertCustomerArr(array);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            message = context.getString(R.string.common_error);
        }
    }
}
