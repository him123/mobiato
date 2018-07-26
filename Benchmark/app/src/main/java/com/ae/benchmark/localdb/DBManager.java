package com.ae.benchmark.localdb;

/**
 * Created by Himm on 1/25/2018.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ae.benchmark.model.Collection;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Load;
import com.ae.benchmark.model.Payment;
import com.ae.benchmark.model.SalesInvoice;
import com.ae.benchmark.model.Transaction;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    //========================================== INSERT PART START ========================================================


    //INSERT LOAD HEADER ONLY SINGLE
    public void insertSalesman(JSONObject SalesmanObj) {

        ContentValues contentValue = new ContentValues();

//        contentValue.put(DatabaseHelper.UNIQUE_ID, load_no);
//        contentValue.put(DatabaseHelper.UNIQUE_ID, load_no);
//        contentValue.put(DatabaseHelper.UNIQUE_ID, load_no);


        database.insert(DatabaseHelper.TABLE_SALES_MAN, null, contentValue);
    }


    //INSERT LOAD HEADER ONLY SINGLE
    public void insertLoad(String load_no, String load_del_date, String is_verified) {

        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.LOAD_NO, load_no);
        contentValue.put(DatabaseHelper.LOAD_DEL_DATE, load_del_date);
        contentValue.put(DatabaseHelper.LOAD_IS_VERIFIED, is_verified);

        database.insert(DatabaseHelper.TABLE_LOAD_HEADER, null, contentValue);
    }

    //INSERT LOAD ITEMS ONKY SINGLE
    public void insertLoadItems(String item_code,
                                String item_name_en,
                                String item_name_ar,
                                String item_type,
                                String item_price,
                                String item_barcode,
                                String item_division,
                                String item_con_fector,
                                String load_no) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.ITEM_CODE, item_code);
        contentValue.put(DatabaseHelper.ITEM_NAME_EN, item_name_en);
        contentValue.put(DatabaseHelper.ITEM_NAME_AR, item_name_ar);
        contentValue.put(DatabaseHelper.ITEM_TYPE, item_type);
        contentValue.put(DatabaseHelper.ITEM_PRICE, item_price);
        contentValue.put(DatabaseHelper.ITEM_BARCODE, item_barcode);
        contentValue.put(DatabaseHelper.ITEM_DIVISION, item_division);
        contentValue.put(DatabaseHelper.ITEM_CON_FECTOR, item_con_fector);
        contentValue.put(DatabaseHelper.LOAD_NO, load_no);


        database.insert(DatabaseHelper.TABLE_LOAD_ITEMS, null, contentValue);
    }


    public void insertCollectionHeader(String docNum, String invNum,
                                       String custNum, String custName,
                                       String payMethod, String isCollected,
                                       String amount, String dueDate, String dueAmnt, String inv_date) {
        try {
            ContentValues contentValue = new ContentValues();

            contentValue.put(DatabaseHelper.COL_DOC_CODE, docNum);
            contentValue.put(DatabaseHelper.COL_INVOICE_NO, invNum);
            contentValue.put(DatabaseHelper.COL_CUST_NO, custNum);
            contentValue.put(DatabaseHelper.COL_CUST_NAME, custName);
            contentValue.put(DatabaseHelper.COL_PAY_METHOD, payMethod);
            contentValue.put(DatabaseHelper.COL_IS_COLLECTED, isCollected);

            contentValue.put(DatabaseHelper.COL_AMOUNT, amount);
            contentValue.put(DatabaseHelper.COL_DUE_DATE, dueDate);
            contentValue.put(DatabaseHelper.COL_DUE_AMOUNT, dueAmnt);
            contentValue.put(DatabaseHelper.COL_INVOICE_DATE, inv_date);

            database.insert(DatabaseHelper.TABLE_COLLECTION_HEADER, null, contentValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //INSERT SALESMAN
    public void insertSalesInvoiceHeader(SalesInvoice salesInvoice) {

        try {

            ContentValues contentValue = new ContentValues();

            contentValue.put(DatabaseHelper.SVH_CODE, salesInvoice.inv_no);
            contentValue.put(DatabaseHelper.SVH_INVOICE_TYPE, salesInvoice.inv_type);
            contentValue.put(DatabaseHelper.SVH_INVOICE_TYPE_CODE, salesInvoice.inv_type_code);
            contentValue.put(DatabaseHelper.SVH_CUST_CODE, salesInvoice.cust_code);
            contentValue.put(DatabaseHelper.SVH_CUST_SALES_ORG, salesInvoice.cust_sales_org);
            contentValue.put(DatabaseHelper.SVH_CUST_DIST_CHANNEL, salesInvoice.cust_dist_channel);
            contentValue.put(DatabaseHelper.SVH_CUST_DIVISION, salesInvoice.cust_division);
            contentValue.put(DatabaseHelper.SVH_INVOICE_DATE, salesInvoice.inv_date);
            contentValue.put(DatabaseHelper.SVH_DELVERY_DATE, salesInvoice.del_date);
            contentValue.put(DatabaseHelper.SVH_CUST_NAME, salesInvoice.cust_name_en);
            contentValue.put(DatabaseHelper.SVH_TOT_AMT_SALES, salesInvoice.tot_amnt_sales);
            contentValue.put(DatabaseHelper.SVH_INVOICE_HEADER_DISC_IN_PER, salesInvoice.inv_header_dis_per);
            contentValue.put(DatabaseHelper.SVH_INVOICE_HEADER_DISC_IN_VAL, salesInvoice.inv_header_dis_val);
            contentValue.put(DatabaseHelper.SVH_VAT_PER, salesInvoice.inv_header_vat_per);
            contentValue.put(DatabaseHelper.SVH_VAT_VAL, salesInvoice.inv_header_vat_val);


            database.insert(DatabaseHelper.TABLE_INVOICE_HEADER, null, contentValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void insertSalesInvoiceItem(Item salesInvoiceItem) {

        try {

            ContentValues contentValue = new ContentValues();

            contentValue.put(DatabaseHelper.SVH_CODE, salesInvoiceItem.sales_inv_nun);

            contentValue.put(DatabaseHelper.ITEM_NAME_EN, salesInvoiceItem.item_name_en);
            contentValue.put(DatabaseHelper.ITEM_PRICE, salesInvoiceItem.item_price);
            contentValue.put(DatabaseHelper.ITEM_QTY, salesInvoiceItem.item_qty);
            contentValue.put(DatabaseHelper.ITEM_CODE, salesInvoiceItem.item_code);
            contentValue.put(DatabaseHelper.ITEM_COUPON_CODE, salesInvoiceItem.item_barcode);
            contentValue.put(DatabaseHelper.ITEM_DISCOUNT_PER, salesInvoiceItem.item_disc_per);
            contentValue.put(DatabaseHelper.ITEM_DISCOUNT_VAL, salesInvoiceItem.item_disc_val);
            contentValue.put(DatabaseHelper.ITEM_VAT_PER, salesInvoiceItem.item_vat_per);
            contentValue.put(DatabaseHelper.ITEM_VAT_VAL, salesInvoiceItem.item_vat_val);

            database.insert(DatabaseHelper.TABLE_INVOICE_ITEMS, null, contentValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //INSERT LOAD HEADERS AND ITEMS BULK
    public void insertLoadArr(JSONArray jArr) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject singleObj = jArr.getJSONObject(i);

                ContentValues contentValue = new ContentValues();

                contentValue.put(DatabaseHelper.LOAD_NO, singleObj.getString("load_no"));
                contentValue.put(DatabaseHelper.LOAD_DEL_DATE, singleObj.getString("delivery_date"));
                contentValue.put(DatabaseHelper.LOAD_IS_VERIFIED, "0");

                JSONArray jLoadItemArr = singleObj.getJSONArray("load_items");

                for (int j = 0; j < jLoadItemArr.length(); j++) {
                    JSONObject singleObjItem = jLoadItemArr.getJSONObject(j);
                    ContentValues contentValueItem = new ContentValues();

                    contentValueItem.put(DatabaseHelper.ITEM_CODE, singleObjItem.getString("item_code"));
                    contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, singleObjItem.getString("item_name"));
                    contentValueItem.put(DatabaseHelper.ITEM_QTY, singleObjItem.getString("item_qty"));
                    contentValueItem.put(DatabaseHelper.ITEM_UOM, singleObjItem.getString("item_uom"));
                    contentValueItem.put(DatabaseHelper.LOAD_DATE, singleObjItem.getString("load_date"));
                    contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, singleObjItem.getString("total_price"));
                    contentValueItem.put(DatabaseHelper.LOAD_NO, singleObj.getString("load_no"));

                    db.insert(DatabaseHelper.TABLE_LOAD_ITEMS, null, contentValueItem);

                }


                db.insert(DatabaseHelper.TABLE_LOAD_HEADER, null, contentValue);

            }

            db.setTransactionSuccessful();
            db.endTransaction();
//            dbHelper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //INSERT LOAD HEADERS AND ITEMS BULK
    public void insertUnload(JSONArray jArr) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject singleObj = jArr.getJSONObject(i);

                ContentValues contentValue = new ContentValues();

                contentValue.put(DatabaseHelper.LOAD_NO, singleObj.getString("load_no"));
                contentValue.put(DatabaseHelper.LOAD_DEL_DATE, singleObj.getString("delivery_date"));
                contentValue.put(DatabaseHelper.LOAD_IS_VERIFIED, "0");

                JSONArray jLoadItemArr = singleObj.getJSONArray("load_items");

                for (int j = 0; j < jLoadItemArr.length(); j++) {
                    JSONObject singleObjItem = jLoadItemArr.getJSONObject(j);
                    ContentValues contentValueItem = new ContentValues();

                    contentValueItem.put(DatabaseHelper.ITEM_CODE, singleObjItem.getString("item_code"));
                    contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, singleObjItem.getString("item_name"));
                    contentValueItem.put(DatabaseHelper.ITEM_QTY, singleObjItem.getString("item_qty"));
                    contentValueItem.put(DatabaseHelper.ITEM_UOM, singleObjItem.getString("item_uom"));
                    contentValueItem.put(DatabaseHelper.LOAD_DATE, singleObjItem.getString("load_date"));
                    contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, singleObjItem.getString("total_price"));
                    contentValueItem.put(DatabaseHelper.LOAD_NO, singleObj.getString("load_no"));

                    db.insert(DatabaseHelper.TABLE_UNLOAD_ITEMS, null, contentValueItem);

                }


//                db.insert(DatabaseHelper.TABLE_LOAD_HEADER, null, contentValue);

            }

            db.setTransactionSuccessful();
            db.endTransaction();
//            dbHelper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //INSERT LOAD HEADERS AND ITEMS BULK
    public void insertVanStockArr(List<Item> jArr, String load_no) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.beginTransaction();
//            for (int i = 0; i < jArr.length(); i++) {
//                JSONObject singleObj = jArr.getJSONObject(i);
//
//                ContentValues contentValue = new ContentValues();
//
//                contentValue.put(DatabaseHelper.LOAD_NO, singleObj.getString("load_no"));
//                contentValue.put(DatabaseHelper.LOAD_DEL_DATE, singleObj.getString("delivery_date"));
//                contentValue.put(DatabaseHelper.LOAD_IS_VERIFIED, "0");
//
//                JSONArray jLoadItemArr = singleObj.getJSONArray("load_items");

            for (int j = 0; j < jArr.size(); j++) {
//                JSONObject singleObjItem = jArr.getJSONObject(j);
                ContentValues contentValueItem = new ContentValues();

                contentValueItem.put(DatabaseHelper.ITEM_CODE, jArr.get(j).item_code);
                contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, jArr.get(j).item_name_en);
//                contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, jArr.get(j).item_name_ar);
                contentValueItem.put(DatabaseHelper.ITEM_QTY, jArr.get(j).item_qty);
                contentValueItem.put(DatabaseHelper.ITEM_UOM, jArr.get(j).item_uom);
                contentValueItem.put(DatabaseHelper.LOAD_DATE, "22/07/2018");
                contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, jArr.get(j).item_price);
                contentValueItem.put(DatabaseHelper.LOAD_NO, load_no);

                db.insert(DatabaseHelper.TABLE_VANSTOCK_ITEMS, null, contentValueItem);

            }

//                db.insert(DatabaseHelper.TABLE_VANSTOCK_HEADER, null, contentValue);

//            }

            db.setTransactionSuccessful();
            db.endTransaction();
//            dbHelper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //INSERT CUSTOMER SINGLE
    public void insertCustomer(String cust_num,
                               String cust_name_en,
                               String cust_name_ar,
                               String cust_dist_channel,
                               String cust_division,
                               String cust_sales_org,
                               String cust_credit_limit,
                               String cust_avail_bal,
                               String cust_payment_term,
                               String cust_address,
                               String cust_type,
                               String cust_possessed_empty_bottle,
                               String cust_possessed_filled_bottle,
                               String cust_latitude,
                               String cust_longitude
    ) {

        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.CUST_NUM, cust_num);
        contentValue.put(DatabaseHelper.CUST_NAME_EN, cust_name_en);
        contentValue.put(DatabaseHelper.CUST_NAME_AR, cust_name_ar);
        contentValue.put(DatabaseHelper.CUST_DIST_CHANNEL, cust_dist_channel);
        contentValue.put(DatabaseHelper.CUST_DIVISION, cust_division);
        contentValue.put(DatabaseHelper.CUST_SALES_ORG, cust_sales_org);
        contentValue.put(DatabaseHelper.CUST_CREDIT_LIMIT, cust_credit_limit);
        contentValue.put(DatabaseHelper.CUST_AVAIL_BAL, cust_avail_bal);
        contentValue.put(DatabaseHelper.CUST_PAYMENT_TERM, cust_payment_term);
        contentValue.put(DatabaseHelper.CUST_ADDRESS, cust_address);
        contentValue.put(DatabaseHelper.CUST_TYPE, cust_type);
        contentValue.put(DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE, cust_possessed_empty_bottle);
        contentValue.put(DatabaseHelper.CUST_POSSESSED_FILLED_BOTTLE, cust_possessed_filled_bottle);
        contentValue.put(DatabaseHelper.CUST_LATITUDE, cust_latitude);
        contentValue.put(DatabaseHelper.CUST_LONGITUDE, cust_longitude);

        database.insert(DatabaseHelper.TABLE_CUSTOMER, null, contentValue);
    }


    //INSERT CUSTOMER BULK
    public void insertCustomerArr(JSONArray jArr) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject singleObj = jArr.getJSONObject(i);
                ContentValues contentValue = new ContentValues();

                contentValue.put(DatabaseHelper.CUST_NUM, singleObj.getString("cust_num"));
                contentValue.put(DatabaseHelper.CUST_NAME_EN, singleObj.getString("cust_name_en"));
                contentValue.put(DatabaseHelper.CUST_NAME_AR, singleObj.getString("cust_name_ar"));
                contentValue.put(DatabaseHelper.CUST_DIST_CHANNEL, singleObj.getString("cust_dist_channel"));
                contentValue.put(DatabaseHelper.CUST_DIVISION, singleObj.getString("cust_division"));
                contentValue.put(DatabaseHelper.CUST_SALES_ORG, singleObj.getString("cust_sales_org"));
                contentValue.put(DatabaseHelper.CUST_CREDIT_LIMIT, singleObj.getString("cust_credit_limit"));
                contentValue.put(DatabaseHelper.CUST_AVAIL_BAL, singleObj.getString("cust_avail_bal"));
                contentValue.put(DatabaseHelper.CUST_PAYMENT_TERM, singleObj.getString("cust_payment_term"));
                contentValue.put(DatabaseHelper.CUST_ADDRESS, singleObj.getString("cust_address"));
                contentValue.put(DatabaseHelper.CUST_TYPE, singleObj.getString("cust_type"));
                contentValue.put(DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE, singleObj.getString("cust_possessed_empty_bottle"));
                contentValue.put(DatabaseHelper.CUST_POSSESSED_FILLED_BOTTLE, singleObj.getString("cust_possessed_filled_bottle"));
                contentValue.put(DatabaseHelper.CUST_LATITUDE, singleObj.getString("cust_lat"));
                contentValue.put(DatabaseHelper.CUST_LONGITUDE, singleObj.getString("cust_long"));
                contentValue.put(DatabaseHelper.CUST_SALE, "0");
                contentValue.put(DatabaseHelper.CUST_ORDER, "0");
                contentValue.put(DatabaseHelper.CUST_COLLECTION, "0");
                contentValue.put(DatabaseHelper.IS_STOCK_CAPTURED, "0");


                db.insert(DatabaseHelper.TABLE_CUSTOMER, null, contentValue);

            }

            db.setTransactionSuccessful();
            db.endTransaction();
//            dbHelper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //INSERT ITEMS SINGLE
    public void insertItems(String item_code,
                            String item_name_en,
                            String item_name_ar,
                            String item_type,
                            String item_price,
                            String item_barcode,
                            String item_division,
                            String item_con_fector

    ) {

//            db.beginTransaction();
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.ITEM_CODE, item_code);
        contentValue.put(DatabaseHelper.ITEM_NAME_EN, item_name_en);
        contentValue.put(DatabaseHelper.ITEM_NAME_AR, item_name_ar);
        contentValue.put(DatabaseHelper.ITEM_TYPE, item_type);
        contentValue.put(DatabaseHelper.ITEM_PRICE, item_price);
        contentValue.put(DatabaseHelper.ITEM_BARCODE, item_barcode);
        contentValue.put(DatabaseHelper.ITEM_DIVISION, item_division);
        contentValue.put(DatabaseHelper.ITEM_CON_FECTOR, item_con_fector);

        database.insert(DatabaseHelper.TABLE_ITEM, null, contentValue);
    }


    //INSERT ITEMS BULK
    public void insertItemsArray(JSONArray jArr) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject singleObj = jArr.getJSONObject(i);
                ContentValues contentValue = new ContentValues();

                contentValue.put(DatabaseHelper.ITEM_CODE, singleObj.getString("item_code"));
                contentValue.put(DatabaseHelper.ITEM_NAME_EN, singleObj.getString("item_name_en"));
                contentValue.put(DatabaseHelper.ITEM_NAME_AR, singleObj.getString("item_name_ar"));
                contentValue.put(DatabaseHelper.ITEM_TYPE, singleObj.getString("item_type"));
                contentValue.put(DatabaseHelper.ITEM_PRICE, singleObj.getString("item_price"));
                contentValue.put(DatabaseHelper.ITEM_BARCODE, singleObj.getString("item_barcode"));
                contentValue.put(DatabaseHelper.ITEM_DIVISION, singleObj.getString("division"));
                contentValue.put(DatabaseHelper.ITEM_CON_FECTOR, singleObj.getString("conversion_factor"));
                contentValue.put(DatabaseHelper.ITEM_UOM, singleObj.getString("item_uom"));

                database.insert(DatabaseHelper.TABLE_ITEM, null, contentValue);
            }

            db.setTransactionSuccessful();
            db.endTransaction();
//            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            db.endTransaction();
        }
    }


    //INSERT TRANSACTION FOR CUSTOMER TIMELINE
    public void insertTransaction(Transaction transaction) {

        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.TR_TYPE, transaction.tr_type);
        contentValue.put(DatabaseHelper.TR_DATE, transaction.tr_date_time);
        contentValue.put(DatabaseHelper.TR_CUSTOMER_NUM, transaction.tr_customer_num);
        contentValue.put(DatabaseHelper.TR_CUSTOMER_NAME, transaction.tr_customer_name);
        contentValue.put(DatabaseHelper.TR_SALESMAN_ID, transaction.tr_salesman_id);
        contentValue.put(DatabaseHelper.TR_INVOICE_ID, transaction.tr_invoice_id);
        contentValue.put(DatabaseHelper.TR_ORDER_ID, transaction.tr_order_id);
        contentValue.put(DatabaseHelper.TR_COLLECTION_ID, transaction.tr_collection_id);
        contentValue.put(DatabaseHelper.TR_PYAMENT_ID, transaction.tr_pyament_id);

        database.insert(DatabaseHelper.TABLE_TRANSACTION, null, contentValue);
    }

    //INSERT TRANSACTION FOR CUSTOMER TIMELINE
    public void insertPayment(Payment payment) {

        ContentValues contentValue = new ContentValues();

        //contentValue.put(DatabaseHelper.PAYMENT_ID, payment.getPayment_id());
        contentValue.put(DatabaseHelper.PAYMENT_INVOICE_ID, payment.getInvoice_id());
        contentValue.put(DatabaseHelper.PAYMENT_COLLECTION_ID, payment.getCollection_id());
        contentValue.put(DatabaseHelper.PAYMENT_TYPE, payment.getPayment_type());
        contentValue.put(DatabaseHelper.PAYMENT_DATE, payment.getPayment_date());
        contentValue.put(DatabaseHelper.PAYMENT_CHEQUE_NO, payment.getCheque_no());
        contentValue.put(DatabaseHelper.PAYMENT_BANK_NAME, payment.getBank_name());
        contentValue.put(DatabaseHelper.PAYMENT_AMOUNT, payment.getPayment_amount());
        contentValue.put(DatabaseHelper.PAYMENT_CUSTOMER_ID, payment.getCust_id());

        database.insert(DatabaseHelper.TABLE_PAYMENT, null, contentValue);
    }
//    //INSERT TRANSACTION FOR CUSTOMER TIMELINE
//    public void insertFromLoadToVanStock() {
//
//        ContentValues contentValue = new ContentValues();
//
//        contentValue.put(DatabaseHelper.TRA_ACT, act);
//        contentValue.put(DatabaseHelper.TRA_DATE, date);
//        contentValue.put(DatabaseHelper.TRA_TIME, time);
//
//        database.insert(DatabaseHelper.TABLE_TRANSACTION, null, contentValue);
//    }

    //========================================== INSERT PART OVER ========================================================


    //========================================== UPDATE PART START ========================================================
    public int updateEmptyBottles(String emptyBottles, String custNum) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE, emptyBottles);
        int i = database.update(DatabaseHelper.TABLE_CUSTOMER, contentValues, DatabaseHelper.CUST_NUM + " = " + custNum, null);
        return i;
    }

    public int updateCustCaptredStock(String cust_num, String val) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.IS_STOCK_CAPTURED, val);
        int i = database.update(DatabaseHelper.TABLE_CUSTOMER, contentValues, DatabaseHelper.CUST_NUM + " = " + cust_num, null);
        return i;
    }


    public int updateCustomerTransactionType(String cust_num, String transaction_type, String transaction_val) {

        ContentValues contentValues = new ContentValues();
        if (transaction_type.equals("sale")) {
            contentValues.put(DatabaseHelper.CUST_SALE, transaction_val);
        } else if (transaction_type.equals("order")) {
            contentValues.put(DatabaseHelper.CUST_ORDER, transaction_val);
        } else if (transaction_type.equals("collection")) {
            contentValues.put(DatabaseHelper.CUST_COLLECTION, transaction_val);
        }

        int i = database.update(DatabaseHelper.TABLE_CUSTOMER, contentValues,
                DatabaseHelper.CUST_NUM + " = " + cust_num, null);

        return i;
    }

    public int updateLoadItemQty(String load_num, String item_code, String item_qty) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ITEM_QTY, item_qty);
        int i = database.update(DatabaseHelper.TABLE_LOAD_ITEMS, contentValues,
                DatabaseHelper.ITEM_CODE + " = " + item_code, null);
        return i;
    }


    public int updateUnLoadItemQty(String load_num, String item_code, String item_qty) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ITEM_QTY, item_qty);
        int i = database.update(DatabaseHelper.TABLE_UNLOAD_ITEMS, contentValues,
                DatabaseHelper.ITEM_CODE + " = " + item_code, null);
        return i;
    }

    public int updatedFilledBottles(String filledBottles, String custNum) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CUST_POSSESSED_FILLED_BOTTLE, filledBottles);
        int i = database.update(DatabaseHelper.TABLE_CUSTOMER, contentValues, DatabaseHelper.CUST_NUM + " = " + custNum, null);
        return i;
    }

    public int updateLoadVerified(String load_no) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.LOAD_NO, load_no);
        contentValues.put(DatabaseHelper.LOAD_IS_VERIFIED, "1");
        int i = database.update(DatabaseHelper.TABLE_LOAD_HEADER, contentValues, DatabaseHelper.LOAD_NO + " = " + load_no, null);
        return i;
    }

    public int updateVanStock(String item_code, String item_qty) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ITEM_QTY, item_qty);
        int i = database.update(DatabaseHelper.TABLE_VANSTOCK_ITEMS, contentValues,
                DatabaseHelper.ITEM_CODE + " = " + item_code, null);
        return i;
    }


    public void resetUnload() {
        String selectQueryVanStock = "SELECT * FROM " + DatabaseHelper.TABLE_VANSTOCK_ITEMS;
        String selectQueryUnload = "SELECT * FROM " + DatabaseHelper.TABLE_UNLOAD_ITEMS;

        Cursor cursor;
        Cursor cursor2;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            cursor = db.rawQuery(selectQueryVanStock, null);
            cursor2 = db.rawQuery(selectQueryUnload, null);
            if (cursor.getCount() <= 0) {
                //IF FULL VAN STOCK EMPTY ALL DATA NEED IN VAN AGAIN
                if (cursor2.moveToFirst()) {
                    do {
                        ContentValues contentValueItem = new ContentValues();

                        contentValueItem.put(DatabaseHelper.ITEM_CODE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_CODE)));
                        contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_NAME_EN)));
                        contentValueItem.put(DatabaseHelper.ITEM_QTY, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_QTY)));
                        contentValueItem.put(DatabaseHelper.ITEM_UOM, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_UOM)));
                        contentValueItem.put(DatabaseHelper.LOAD_DATE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_DATE)));
                        contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE)));
                        contentValueItem.put(DatabaseHelper.LOAD_NO, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_NO)));

                        db.insert(DatabaseHelper.TABLE_VANSTOCK_ITEMS, null, contentValueItem);

                    } while (cursor2.moveToNext());

                }

                //DELETE UNLOAD TABLE
                db.execSQL("delete from " + DatabaseHelper.TABLE_UNLOAD_ITEMS);
            } else {
                //IF FULL VAN STOCK NOT FULL EMPTY ALL UNLOAD DATA NEED IN VAN AGAIN

                if (cursor2.moveToFirst()) {
                    do {

                        if (CheckIsItemAlreadyExist(cursor2.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE)),
                                db, DatabaseHelper.TABLE_VANSTOCK_ITEMS)) {

                            //UPDATE

                            ContentValues contentValueItem = new ContentValues();

                            contentValueItem.put(DatabaseHelper.ITEM_CODE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_CODE)));
                            contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_NAME_EN)));

                            String current_qty = getUnloadBottleQty(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_CODE)), db, DatabaseHelper.TABLE_VANSTOCK_ITEMS);
                            int tot_qty = Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_QTY))) +
                                    Integer.parseInt(current_qty);
                            contentValueItem.put(DatabaseHelper.ITEM_QTY, tot_qty);
                            contentValueItem.put(DatabaseHelper.ITEM_UOM, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_UOM)));
                            contentValueItem.put(DatabaseHelper.LOAD_DATE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_DATE)));
                            contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE)));
                            contentValueItem.put(DatabaseHelper.LOAD_NO, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_NO)));


                            int i = db.update(DatabaseHelper.TABLE_VANSTOCK_ITEMS, contentValueItem,
                                    DatabaseHelper.ITEM_CODE + " = " + cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_CODE)), null);


                        } else {
                            //INSERT
                            ContentValues contentValueItem = new ContentValues();

                            contentValueItem.put(DatabaseHelper.ITEM_CODE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_CODE)));
                            contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_NAME_EN)));
                            contentValueItem.put(DatabaseHelper.ITEM_QTY, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_QTY)));
                            contentValueItem.put(DatabaseHelper.ITEM_UOM, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ITEM_UOM)));
                            contentValueItem.put(DatabaseHelper.LOAD_DATE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_DATE)));
                            contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE)));
                            contentValueItem.put(DatabaseHelper.LOAD_NO, cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.LOAD_NO)));

                            db.insert(DatabaseHelper.TABLE_VANSTOCK_ITEMS, null, contentValueItem);
                        }

                    } while (cursor2.moveToNext());

                }

                //DELETE UNLOAD TABLE
                db.execSQL("delete from " + DatabaseHelper.TABLE_UNLOAD_ITEMS);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processUnload(String item_code, String item_qty) {

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_VANSTOCK_ITEMS +
                " WHERE " + DatabaseHelper.ITEM_CODE + " = " + item_code;

        Cursor cursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {


//            String current_qty = getUnloadBottleQty(item_code, db);

            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    String qty = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY));

//                    int updateQty = Integer.parseInt(qty)+Integer.parseInt(item_qty);

                    if (Integer.parseInt(qty) == Integer.parseInt(item_qty)) {

                        if (CheckIsItemAlreadyExist(item_code, db, DatabaseHelper.TABLE_UNLOAD_ITEMS)) {
                            //UPDATE TO UNLOAD
                            String current_qty = getUnloadBottleQty(item_code, db, DatabaseHelper.TABLE_UNLOAD_ITEMS);
                            int updateQty = Integer.parseInt(current_qty) + Integer.parseInt(item_qty);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DatabaseHelper.ITEM_QTY, updateQty);
                            int i = db.update(DatabaseHelper.TABLE_UNLOAD_ITEMS, contentValues,
                                    DatabaseHelper.ITEM_CODE + " = " + item_code, null);
                        } else {

                            //ADD FULL ITEM TO UNLOAD TABLE
                            ContentValues contentValueItem = new ContentValues();

                            contentValueItem.put(DatabaseHelper.ITEM_CODE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE)));
                            contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME_EN)));
                            contentValueItem.put(DatabaseHelper.ITEM_QTY, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY)));
                            contentValueItem.put(DatabaseHelper.ITEM_UOM, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_UOM)));
                            contentValueItem.put(DatabaseHelper.LOAD_DATE, "22/07/2018");
                            contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE)));
                            contentValueItem.put(DatabaseHelper.LOAD_NO, cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO)));


                            db.insert(DatabaseHelper.TABLE_UNLOAD_ITEMS, null, contentValueItem);
                        }

                        //REMOVE FROM VAN STOCK
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put(DatabaseHelper.ITEM_QTY, item_qty);
                        int i = db.delete(DatabaseHelper.TABLE_VANSTOCK_ITEMS,
                                DatabaseHelper.ITEM_CODE + " = " + item_code, null);

                    } else if (Integer.parseInt(qty) > Integer.parseInt(item_qty)) {

                        if (CheckIsItemAlreadyExist(item_code, db, DatabaseHelper.TABLE_UNLOAD_ITEMS)) {
                            //UPDATE TO UNLOAD
                            String current_qty = getUnloadBottleQty(item_code, db, DatabaseHelper.TABLE_UNLOAD_ITEMS);
                            int updateQty = Integer.parseInt(current_qty) + Integer.parseInt(item_qty);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DatabaseHelper.ITEM_QTY, updateQty);
                            int i = db.update(DatabaseHelper.TABLE_UNLOAD_ITEMS, contentValues,
                                    DatabaseHelper.ITEM_CODE + " = " + item_code, null);
                        } else {

                            //ADD TO UNLOAD
                            ContentValues contentValueItem = new ContentValues();

                            contentValueItem.put(DatabaseHelper.ITEM_CODE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE)));
                            contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME_EN)));
                            contentValueItem.put(DatabaseHelper.ITEM_QTY, item_qty);
                            contentValueItem.put(DatabaseHelper.ITEM_UOM, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_UOM)));
                            contentValueItem.put(DatabaseHelper.LOAD_DATE, "22/07/2018");
                            contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE)));
                            contentValueItem.put(DatabaseHelper.LOAD_NO, cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO)));


                            db.insert(DatabaseHelper.TABLE_UNLOAD_ITEMS, null, contentValueItem);
                        }


                        Integer remainingQty = Integer.parseInt(qty) - Integer.parseInt(item_qty);
                        //UPDATE IN VAN STOCK
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.ITEM_QTY, remainingQty);
                        int i = db.update(DatabaseHelper.TABLE_VANSTOCK_ITEMS, contentValues,
                                DatabaseHelper.ITEM_CODE + " = " + item_code, null);
                    }
                } while (cursor.moveToNext());
            }


        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }
//        return i;
    }


    public static boolean CheckIsItemAlreadyExist(String item_code, SQLiteDatabase db, String tableName) {
//        SQLiteDatabase sqldb = EGLifeStyleApplication.sqLiteDatabase;
        String Query = "Select * from " + tableName + " where " +
                DatabaseHelper.ITEM_CODE + " = " + item_code;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public void updateUnloadVanStock(String item_code, String item_qty) {

//        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_VANSTOCK_ITEMS + " WHERE " ++ ;
////                " WHERE " + DatabaseHelper.LOAD_NO + " = " + load_no;

        String selectQuery = "SELECT " + DatabaseHelper.ITEM_QTY + " FROM " + DatabaseHelper.TABLE_VANSTOCK_ITEMS +
                " WHERE " + DatabaseHelper.ITEM_CODE + " = " + item_code;

        Cursor cursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    String qty = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY));

                    if (Integer.parseInt(qty) == Integer.parseInt(item_qty)) {

                        //REMOVE FROM VAN STOCK
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.ITEM_QTY, item_qty);
                        int i = database.delete(DatabaseHelper.TABLE_VANSTOCK_ITEMS,
                                DatabaseHelper.ITEM_CODE + " = " + item_code, null);

                        //ADD TO UNLOAD
                        ContentValues contentValueItem = new ContentValues();

                        contentValueItem.put(DatabaseHelper.ITEM_CODE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE)));
                        contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME_EN)));
                        contentValueItem.put(DatabaseHelper.ITEM_QTY, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY)));
                        contentValueItem.put(DatabaseHelper.ITEM_UOM, cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_UOM)));
                        contentValueItem.put(DatabaseHelper.LOAD_DATE, "22/07/2018");
                        contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE)));
                        contentValueItem.put(DatabaseHelper.LOAD_NO, cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO)));

                        db.insert(DatabaseHelper.TABLE_UNLOAD_ITEMS, null, contentValueItem);

                    } else if (Integer.parseInt(qty) > Integer.parseInt(item_qty)) {
                        //update in van stock

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.ITEM_QTY, item_qty);
                        int i = database.update(DatabaseHelper.TABLE_VANSTOCK_ITEMS, contentValues,
                                DatabaseHelper.ITEM_CODE + " = " + item_code, null);


                        //add in unload
                    }
                } while (cursor.moveToNext());
            }


        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.ITEM_QTY, item_qty);
//        int i = database.update(DatabaseHelper.TABLE_UNLOAD_ITEMS, contentValues,
//                DatabaseHelper.ITEM_CODE + " = " + item_code, null);
//        return i;
    }

    //========================================== UPDATE PART OVER ========================================================


    //========================================== CHECK CONDITION PART START ========================================================

    public boolean checkIsNotVerified() {
        String Query = "Select * from " + DatabaseHelper.TABLE_LOAD_HEADER +
                " WHERE " + DatabaseHelper.LOAD_IS_VERIFIED + "='" + "0" + "'";
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public boolean checkIsCustWithType(String cust_num, String type, String table) {
        String Query = "Select * from " + table +
                " WHERE " + DatabaseHelper.LOAD_IS_VERIFIED + "='" + "0" + "'";
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //========================================== CHECK CONDITION PART OVER ========================================================


    //========================================== GET DATA PART START ========================================================

    //Item List
    public ArrayList<Item> getAllItems() {

        ArrayList<Item> list = new ArrayList<Item>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_ITEM;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Item item = new Item();
                        //only one column
                        item.item_code = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE));
                        item.item_name_en = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME_EN));
                        item.item_price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_PRICE));
//                        item.load_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO));

                        //you could add additional columns here..
                        list.add(item);

                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }

    public ArrayList<Customer> getAllCust() {

        ArrayList<Customer> list = new ArrayList<Customer>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_CUSTOMER;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        db.beginTransaction();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Customer customer = new Customer();
                        //only one column
                        customer.cust_num = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_NUM));
                        customer.cust_name_en = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_NAME_EN));
                        customer.cust_name_ar = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_NAME_AR));
                        customer.cust_dist_channel = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_DIST_CHANNEL));
                        customer.cust_division = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_DIVISION));
                        customer.cust_sales_org = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_SALES_ORG));
                        customer.cust_credit_limit = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_CREDIT_LIMIT));
                        customer.cust_avail_bal = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_AVAIL_BAL));
                        customer.cust_payment_term = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_PAYMENT_TERM));
                        customer.cust_address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_ADDRESS));
                        customer.cust_type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_TYPE));
                        customer.cust_possessed_empty_bottle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE));
                        customer.cust_possessed_filled_bottle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_POSSESSED_FILLED_BOTTLE));
                        customer.cust_latitude = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_LATITUDE));
                        customer.cust_longitude = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_LONGITUDE));

                        customer.cust_sale = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_ORDER));
                        customer.cust_order = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_ORDER));
                        customer.cust_collection = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_COLLECTION));

                        //you could add additional columns here..
                        list.add(customer);

                    } while (cursor.moveToNext());
                }

                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }



    public ArrayList<Transaction> getAllTransactions() {

        ArrayList<Transaction> list = new ArrayList<Transaction>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_TRANSACTION;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Transaction transaction = new Transaction();
                        //only one column
                        transaction.tr_type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_TYPE));
                        transaction.tr_date_time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_DATE));
                        transaction.tr_salesman_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_SALESMAN_ID));
                        transaction.tr_customer_name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_CUSTOMER_NAME));
                        transaction.tr_customer_num = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_CUSTOMER_NUM));
                        transaction.tr_collection_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_COLLECTION_ID));
                        transaction.tr_order_id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_ORDER_ID));
                        transaction.tr_invoice_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_INVOICE_ID));
                        transaction.tr_pyament_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_PYAMENT_ID));

                        //you could add additional columns here..
                        list.add(transaction);

                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }

    public ArrayList<Transaction> getAllTransactionsForCustomer(String cust_num) {

        ArrayList<Transaction> list = new ArrayList<Transaction>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_TRANSACTION  +
                " WHERE " + DatabaseHelper.TR_CUSTOMER_NUM + " = " + cust_num;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Transaction transaction = new Transaction();
                        //only one column
                        transaction.tr_type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_TYPE));
                        transaction.tr_date_time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_DATE));
                        transaction.tr_salesman_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_SALESMAN_ID));
                        transaction.tr_customer_name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_CUSTOMER_NAME));
                        transaction.tr_customer_num = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_CUSTOMER_NUM));
                        transaction.tr_collection_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_COLLECTION_ID));
                        transaction.tr_order_id= cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_ORDER_ID));
                        transaction.tr_invoice_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_INVOICE_ID));
                        transaction.tr_pyament_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TR_PYAMENT_ID));

                        //you could add additional columns here..
                        list.add(transaction);

                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }


    public ArrayList<Collection> getAllCollections(String custNum) {

        ArrayList<Collection> list = new ArrayList<Collection>();

        // Select All Query
//        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_COLLECTION_HEADER;

        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_COLLECTION_HEADER +
                " WHERE " + DatabaseHelper.COL_CUST_NO + " = " + custNum;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Collection collection = new Collection();
                        //only one column
                        collection.coll_doc_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOC_CODE));
                        collection.coll_inv_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_INVOICE_NO));
                        collection.coll_cust_name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CUST_NAME));
                        collection.coll_cust_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CUST_NO));
                        collection.coll_cust_pay_method = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PAY_METHOD));

                        collection.coll_amount = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AMOUNT));
                        collection.coll_inv_date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_INVOICE_DATE));
                        collection.coll_due_date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DUE_DATE));
                        collection.coll_due_amt = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DUE_DATE));

                        //you could add additional columns here..
                        list.add(collection);

                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }

    public ArrayList<Item> getLoadItem(String load_no) {

        ArrayList<Item> list = new ArrayList<Item>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_LOAD_ITEMS +
                " WHERE " + DatabaseHelper.LOAD_NO + " = " + load_no;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Item item = new Item();
                        //only one column
                        item.item_code = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE));
                        item.item_name_en = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME_EN));
                        item.item_price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE));
                        item.item_qty = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY));
                        item.item_uom = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_UOM));
                        item.load_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO));
//
                        //you could add additional columns here..
                        list.add(item);

                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }


    public ArrayList<Item> getVanStock() {

        ArrayList<Item> list = new ArrayList<Item>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_VANSTOCK_ITEMS + "";
//                " WHERE " + DatabaseHelper.LOAD_NO + " = " + load_no;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Item item = new Item();
                        //only one column
                        item.item_code = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE));
                        item.item_name_en = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME_EN));
                        item.item_price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE));
                        item.item_qty = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY));
                        item.item_uom = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_UOM));
                        item.load_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO));
//
                        //you could add additional columns here..
                        list.add(item);

                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }


    public void copyVanStockToUnload() {

        ArrayList<Item> list = new ArrayList<Item>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_VANSTOCK_ITEMS + "";
//                " WHERE " + DatabaseHelper.LOAD_NO + " = " + load_no;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Item item = new Item();
                        //only one column
                        item.item_code = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE));
                        item.item_name_en = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME_EN));
                        item.item_price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE));
                        item.item_qty = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY));
                        item.item_uom = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_UOM));
                        item.load_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO));
//                        item.load_date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_DATE));

                        ContentValues contentValueItem = new ContentValues();

                        contentValueItem.put(DatabaseHelper.ITEM_CODE, item.item_code);
                        contentValueItem.put(DatabaseHelper.ITEM_NAME_EN, item.item_name_en);
                        contentValueItem.put(DatabaseHelper.ITEM_QTY, item.item_qty);
                        contentValueItem.put(DatabaseHelper.ITEM_UOM, item.item_uom);
//                        contentValueItem.put(DatabaseHelper.LOAD_DATE, singleObjItem.getString("load_date"));
                        contentValueItem.put(DatabaseHelper.LOAD_TOT_PRICE, item.item_price);
                        contentValueItem.put(DatabaseHelper.LOAD_NO, item.load_no);

                        db.insert(DatabaseHelper.TABLE_UNLOAD_ITEMS, null, contentValueItem);
//
                        //you could add additional columns here..
                        list.add(item);

                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

//        return list;
    }


    public ArrayList<Item> getUnload() {

        ArrayList<Item> list = new ArrayList<Item>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_UNLOAD_ITEMS + "";
//                " WHERE " + DatabaseHelper.LOAD_NO + " = " + load_no;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Item item = new Item();
                        //only one column
                        item.item_code = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_CODE));
                        item.item_name_en = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME_EN));
                        item.item_price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE));
                        item.item_qty = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY));
                        item.item_uom = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_UOM));
                        item.load_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO));
//
                        //you could add additional columns here..
                        list.add(item);

                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }


    public int getUnloadVanStockCount() {

        ArrayList<Item> list = new ArrayList<Item>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_UNLOAD_ITEMS + "";
//                " WHERE " + DatabaseHelper.LOAD_NO + " = " + load_no;

        Cursor cursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            cursor = db.rawQuery(selectQuery, null);


        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return cursor.getCount();
    }

    //GET EMPTY OR FILLEd BOTTLES
    public String getLoadEmptyOrFilledBottles(int type, String custNum) { // type = 0 empty, 1 = filled

        String bottlesCount = "0";

        String selectQuery;
        if (type == 1) {
            // Get FILLED BOTTLE
            selectQuery = "SELECT " + DatabaseHelper.CUST_POSSESSED_FILLED_BOTTLE + " FROM " + DatabaseHelper.TABLE_CUSTOMER +
                    " WHERE " + DatabaseHelper.CUST_NUM + " = " + custNum;
        } else {
            // Get EMPTY BOTTLE
            selectQuery = "SELECT " + DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE + " FROM " + DatabaseHelper.TABLE_CUSTOMER +
                    " WHERE " + DatabaseHelper.CUST_NUM + " = " + custNum;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count > 0)
                if (type == 0)
                    bottlesCount = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE));
                else
                    bottlesCount = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CUST_POSSESSED_FILLED_BOTTLE));

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bottlesCount;
    }


    //GET LAST INVOICE GENERETED NUMBER
    public long getLastInvoiceID() { // type = 0 empty, 1 = filled
        long index = 0;
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_INVOICE_HEADER;
//                " WHERE " + DatabaseHelper.CUST_NUM + " = " + custNum;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                String count = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SVH_CODE));
                index = Integer.parseInt(count);
            } else {
                index = 0;
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return index;
    }


    //GET LAST INVOICE GENERATED NUMBER
    public long getLastCollectionID() { // type = 0 empty, 1 = filled
        long index = 0;
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_COLLECTION_HEADER;
//                " WHERE " + DatabaseHelper.CUST_NUM + " = " + custNum;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                String count = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOC_CODE));
                index = Integer.parseInt(count);
            } else {
                index = 0;
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return index;
    }


    //GET EMPTY OR FILLEd BOTTLES
    public String getBottleQty(String item_code) { // type = 0 empty, 1 = filled

        String bottlesCount = "0";

        String selectQuery;
//        if (type == 1) {
        // Get FILLED BOTTLE
        selectQuery = "SELECT " + DatabaseHelper.ITEM_QTY + " FROM " + DatabaseHelper.TABLE_VANSTOCK_ITEMS +
                " WHERE " + DatabaseHelper.ITEM_CODE + " = " + item_code;
//        } else {
//            // Get EMPTY BOTTLE
//            selectQuery = "SELECT " + DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE + " FROM " + DatabaseHelper.TABLE_CUSTOMER +
//                    " WHERE " + DatabaseHelper.CUST_NUM + " = " + custNum;
//        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count > 0)

                bottlesCount = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY));

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bottlesCount;
    }


    //GET EMPTY OR FILLEd BOTTLES
    public String getUnloadBottleQty(String item_code, SQLiteDatabase db, String tableName) { // type = 0 empty, 1 = filled

        String bottlesCount = "0";

        String selectQuery;
//        if (type == 1) {
        // Get FILLED BOTTLE
        selectQuery = "SELECT " + DatabaseHelper.ITEM_QTY + " FROM " + tableName +
                " WHERE " + DatabaseHelper.ITEM_CODE + " = " + item_code;
//        } else {
//            // Get EMPTY BOTTLE
//            selectQuery = "SELECT " + DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE + " FROM " + DatabaseHelper.TABLE_CUSTOMER +
//                    " WHERE " + DatabaseHelper.CUST_NUM + " = " + custNum;
//        }

//        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count > 0)

                bottlesCount = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QTY));

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bottlesCount;
    }


    //GET EMPTY OR FILLEd BOTTLES
    public String getBottlePrice(String item_code) { // type = 0 empty, 1 = filled

        String bottlesCount = "0";

        String selectQuery;
//        if (type == 1) {
        // Get FILLED BOTTLE
        selectQuery = "SELECT " + DatabaseHelper.LOAD_TOT_PRICE + " FROM " + DatabaseHelper.TABLE_VANSTOCK_ITEMS +
                " WHERE " + DatabaseHelper.ITEM_CODE + " = " + item_code;
//        } else {
//            // Get EMPTY BOTTLE
//            selectQuery = "SELECT " + DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE + " FROM " + DatabaseHelper.TABLE_CUSTOMER +
//                    " WHERE " + DatabaseHelper.CUST_NUM + " = " + custNum;
//        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count > 0)

                bottlesCount = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_TOT_PRICE));

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bottlesCount;
    }


    //GET EMPTY OR FILLEd BOTTLES
    public String getCustStockCaptured(String cust_num) { // type = 0 empty, 1 = filled

        String isCaptured = "0";

        String selectQuery;
//        if (type == 1) {
        // Get FILLED BOTTLE
        selectQuery = "SELECT " + DatabaseHelper.IS_STOCK_CAPTURED + " FROM " + DatabaseHelper.TABLE_CUSTOMER +
                " WHERE " + DatabaseHelper.CUST_NUM + " = " + cust_num;
//        } else {
//            // Get EMPTY BOTTLE
//            selectQuery = "SELECT " + DatabaseHelper.CUST_POSSESSED_EMPTY_BOTTLE + " FROM " + DatabaseHelper.TABLE_CUSTOMER +
//                    " WHERE " + DatabaseHelper.CUST_NUM + " = " + custNum;
//        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count > 0)

                isCaptured = cursor.getString(cursor.getColumnIndex(DatabaseHelper.IS_STOCK_CAPTURED));

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return isCaptured;
    }


    public ArrayList<Load> getAllLoad() {

        ArrayList<Load> list = new ArrayList<Load>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_LOAD_HEADER;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Load load = new Load();
                        //only one column
                        load.load_no = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_NO));
                        load.del_date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_DEL_DATE));
                        load.is_verified = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOAD_IS_VERIFIED));

                        //you could add additional columns here..
                        list.add(load);

                    } while (cursor.moveToNext());
                }

                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return list;
    }

    //========================================== GET DATA PART OVER ========================================================

//    public String getDailyMatchJson() {
//        String[] columns = new String[]{DatabaseHelper.JSON};
//        Cursor cursor = database.query(
//                DatabaseHelper.TABLE_DAILY_MATCHES,
//                columns,
//                null,
//                null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor.getString(cursor.getColumnIndex("json"));
//    }


    //========================================== DELETE PART START ========================================================

    public void delete(String load_no) {
        database.delete(DatabaseHelper.TABLE_LOAD_HEADER, DatabaseHelper.LOAD_NO + "=" + load_no, null);
    }


    public void deleteDatabase(Context context) {
        context.deleteDatabase(DatabaseHelper.DB_NAME);
    }
    //========================================== DELETE PART OVER ========================================================


}