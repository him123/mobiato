package com.ae.benchmark.localdb;

/**
 * Created by Himm on 1/25/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_ITEM = "items";
    public static final String TABLE_FREE_GOODS = "free_goods";

    public static final String TABLE_SALES_MAN = "sales_man";

    public static final String TABLE_LOAD_HEADER = "load_header";
    public static final String TABLE_LOAD_ITEMS = "load_items";

    public static final String TABLE_VANSTOCK_HEADER = "vanstock_header";
    public static final String TABLE_VANSTOCK_ITEMS = "vanstock_items";

    public static final String TABLE_INVOICE_HEADER = "invoice_header";
    public static final String TABLE_INVOICE_ITEMS = "invoice_items";

    public static final String TABLE_COLLECTION_HEADER = "collection_header";
    public static final String TABLE_COLLECTION_ITEMS = "collection_items";

    public static final String TABLE_ORDER_HEADER = "order_header";
    public static final String TABLE_ORDER_ITEMS = "order_items";

    public static final String TABLE_CUSTOMER = "customers";

    public static final String TABLE_TRANSACTION = "transactions";

    public static final String TABLE_UNLOAD_ITEMS = "unload_items";
    public static final String TABLE_PAYMENT = "payment";
    public static final String TABLE_RECENT_CUSTOMER = "recent_customer";

    //Salesman columns
    public static final String UNIQUE_ID = "u_id";
    public static final String SALESMAN_ID = "sman_id";
    public static final String SALESMAN_NAME_EN = "sname_en";
    public static final String SALESMAN_NAME_AR = "sname_ar";
    public static final String SALESMAN_DIS_CHANNEL = "s_dis_channel";
    public static final String SALESMAN_ORG = "sorg";
    public static final String SALESMAN_DIVISION = "s_division";
    public static final String SALESMAN_ROUTE = "s_route";
    public static final String SALESMAN_VEHICLE_NO = "vehicle_no";
    public static final String SALESMAN_LOGIN_STATUS = "login_status";

    //Free Goods columns
    public static final String FG_CONDITION_RECORD = "fg_condition_record";
    public static final String FG_CUSTOMER_ID = "fg_customer_id";
    public static final String FG_QUA_ITEM_ID = "fg_qua_item_id";
    public static final String FG_QUA_ITEM_NAME = "fg_qua_item_name";
    public static final String FG_ASSI_ITEM_ID = "fg_assi_item_id";
    public static final String FG_ASSI_ITEM_NAME = "fg_assi_item_name";
    public static final String FG_QUA_ITEM_QUALITY = "fg_item_quality";
    public static final String FG_ASS_ITEM_QUALITY = "fg_ass_item_quality";


    public static final String IS_UPLOADED = "is_uploaded";

    //Common columns
    public static final String _ID = "_id";

    // TABLE_ITEM Table columns
    public static final String ITEM_NAME_EN = "item_name_en";
    public static final String ITEM_NAME_AR = "item_name_ar";
    public static final String ITEM_PRICE = "item_price";
    public static final String ITEM_CODE = "item_code";
    public static final String ITEM_TYPE = "item_type";
    public static final String ITEM_BARCODE = "item_barcode";
    public static final String ITEM_DIVISION = "item_division";
    public static final String ITEM_CON_FECTOR = "item_con_factor";
    public static final String ITEM_DISCOUNT_PER = "item_discount_per";
    public static final String ITEM_DISCOUNT_VAL = "item_discount_val";
    public static final String ITEM_COUPON_CODE = "item_coupon_code";
    public static final String ITEM_VAT_VAL = "item_vat_val";
    public static final String ITEM_VAT_PER = "item_vat_per";
    public static final String ITEM_QTY = "item_qty";
    public static final String ITEM_UOM = "item_uom";

    // LOAD Table columns
    public static final String LOAD_NO = "load_no";
    public static final String LOAD_DEL_DATE = "delivery_date";
    public static final String LOAD_IS_VERIFIED = "is_verified";
    public static final String LOAD_DATE = "load_date";
    public static final String LOAD_TOT_PRICE = "tot_price";


    // CUSTOMER Table columns
    public static final String CUST_NAME_EN = "cust_name_en";
    public static final String CUST_NAME_AR = "cust_name_ar";
    public static final String CUST_NUM = "cust_num";
    public static final String CUST_DEL_DATE = "cust_delivery_date";
    public static final String CUST_DIST_CHANNEL = "cust_dist_channel";
    public static final String CUST_DIVISION = "cust_division";
    public static final String CUST_SALES_ORG = "cust_sales_org";
    public static final String CUST_CREDIT_LIMIT = "cust_credit_limit";
    public static final String CUST_AVAIL_BAL = "cust_avail_bal";
    public static final String CUST_PAYMENT_TERM = "cust_payment_term";
    public static final String CUST_ADDRESS = "cust_address";
    public static final String CUST_TYPE = "cust_type";
    public static final String CUST_POSSESSED_EMPTY_BOTTLE = "cust_possessed_empty_bottle";
    public static final String CUST_POSSESSED_FILLED_BOTTLE = "cust_possessed_filled_bottle";
    public static final String CUST_LONGITUDE = "cust_longitude";
    public static final String CUST_LATITUDE = "cust_latitude";
    public static final String CUST_SALE = "cust_sale";
    public static final String CUST_ORDER = "cust_order";
    public static final String CUST_COLLECTION = "cust_collection";
    public static final String IS_STOCK_CAPTURED = "is_stock_captured";
    public static final String CUST_CREATED_DATE = "cust_created_date";


    //SALES INVOICE HEADER COLUMNS
    public static final String SVH_CODE = "sv_code";
    public static final String SVH_INVOICE_TYPE = "sv_type";
    public static final String SVH_INVOICE_TYPE_CODE = "sv_type_code";
    public static final String SVH_CUST_CODE = "sv_cust_code";
    public static final String SVH_CUST_SALES_ORG = "sv_cust_sales_org";
    public static final String SVH_CUST_DIST_CHANNEL = "sv_cust_dist_channel";
    public static final String SVH_CUST_DIVISION = "sv_cust_division";
    public static final String SVH_INVOICE_DATE = "sv_invoice_date";
    public static final String SVH_DELVERY_DATE = "sv_delvery_date";
    public static final String SVH_CUST_NAME = "sv_cust_name";
    public static final String SVH_TOT_AMT_SALES = "sv_tot_amt_sales";
    public static final String SVH_INVOICE_HEADER_DISC_IN_PER = "sv_invoice_header_disc_in_per";
    public static final String SVH_INVOICE_HEADER_DISC_IN_VAL = "sv_invoice_header_disc_in_val";
    public static final String SVH_VAT_PER = "sv_vat_per";
    public static final String SVH_VAT_VAL = "sv_vat_val";


    //COLLECTION COLUMNS
    public static final String COL_DOC_CODE = "col_code";
    public static final String COL_INVOICE_NO = "col_invoice_no";
    public static final String COL_CUST_NO = "col_cust_no";
    public static final String COL_CUST_NAME = "col_cust_name";
    public static final String COL_PAY_METHOD = "col_pay_method";
    public static final String COL_TOT_AMT = "col_tot_amt";
    public static final String COL_PAY_TERM = "col_pay_term";
    public static final String COL_IS_COLLECTED = "is_collected";
    public static final String COL_AMOUNT = "col_amount";
    public static final String COL_DUE_AMOUNT = "col_due_amt";
    public static final String COL_INVOICE_DATE = "col_inv_date";
    public static final String COL_DUE_DATE = "col_due_date";


    //Transaction table columns
    public static final String TR_TYPE = "tr_type";
    public static final String TR_DATE = "tr_date_time";

    public static final String TR_CUSTOMER_NUM = "tr_customer_num";
    public static final String TR_CUSTOMER_NAME = "tr_customer_name";
    public static final String TR_SALESMAN_ID = "tr_salesman_id";
    public static final String TR_INVOICE_ID = "tr_invoice_id";
    public static final String TR_ORDER_ID = "tr_order_id";
    public static final String TR_COLLECTION_ID = "tr_collection_id";
    public static final String TR_PYAMENT_ID = "tr_pyament_id";
    public static final String TR_IS_POSTED = "tr_is_posted";

    // PAYMENT Table columns
    public static final String PAYMENT_ID = "payment_id";
    public static final String PAYMENT_INVOICE_ID = "invoice_id";
    public static final String PAYMENT_COLLECTION_ID = "collection_id";
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String PAYMENT_DATE = "payment_date";
    public static final String PAYMENT_CHEQUE_NO = "cheque_no";
    public static final String PAYMENT_BANK_NAME = "bank_name";
    public static final String PAYMENT_AMOUNT = "payment_amount";
    public static final String PAYMENT_CUSTOMER_ID = "cust_id";

    // RECENT_CUSTOMER Table columns
    public static final String RECENT_CUSTOMER_ID = "reent_customer_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String DATE_TIME = "date_time";

    // ORDER HEADER Table columns
    public static final String ORDER_NO = "order_no";
    public static final String ORDER_DATE = "order_date";
    public static final String ORDER_AMOUNT = "order_amount";


    // Database Information
        static final String DB_NAME = "5GALLON.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_SALESMAN = "create table " + TABLE_SALES_MAN + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UNIQUE_ID + " TEXT NOT NULL, " +
            "" + SALESMAN_ID + " TEXT NOT NULL, " + SALESMAN_NAME_EN + " TEXT NOT NULL," +
            "" + SALESMAN_NAME_AR + " TEXT NOT NULL," + SALESMAN_DIS_CHANNEL + " TEXT NOT NULL," +
            "" + SALESMAN_ORG + " TEXT NOT NULL," + SALESMAN_DIVISION + " TEXT NOT NULL," +
            "" + SALESMAN_ROUTE + " TEXT NOT NULL," + SALESMAN_VEHICLE_NO + " TEXT NOT NULL," +
            "" + SALESMAN_LOGIN_STATUS + " TEXT NOT NULL);";

    //CREATE LOAD HEADER
    private static final String CREATE_TABLE_LOAD_HEADER = "create table " + TABLE_LOAD_HEADER + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "" + LOAD_NO + " TEXT NOT NULL, " +
            "" + LOAD_DEL_DATE + " TEXT NOT NULL," + "" +
            "" + LOAD_IS_VERIFIED + " TEXT NOT NULL);";

    // CREATE LOAD ITEM
    private static final String CREATE_TABLE_LOAD_ITEMS = "create table " + TABLE_LOAD_ITEMS + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_CODE + " TEXT NOT NULL, " +
            ITEM_NAME_EN + " TEXT NOT NULL, " + ITEM_QTY + " TEXT NOT NULL, " +
            LOAD_NO + " TEXT NOT NULL, " + "" + LOAD_DATE + " TEXT NOT NULL, " +
            ITEM_UOM + " TEXT NOT NULL, " +
            LOAD_TOT_PRICE + " TEXT NOT NULL);";


    private static final String CREATE_TABLE_VANSTOCK_HEADER = "create table " + TABLE_VANSTOCK_HEADER + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "" + LOAD_NO + " TEXT NOT NULL, " +
            "" + LOAD_DEL_DATE + " TEXT NOT NULL," + "" +
            "" + LOAD_IS_VERIFIED + " TEXT NOT NULL);";

    // CREATE VAN STOCK ITEM
    private static final String CREATE_TABLE_VANSTOCK_ITEMS = "create table " + TABLE_VANSTOCK_ITEMS + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_CODE + " TEXT NOT NULL, " +
            ITEM_NAME_EN + " TEXT NOT NULL, " + ITEM_QTY + " TEXT NOT NULL, " +
            LOAD_NO + " TEXT NOT NULL, " + "" + LOAD_DATE + " TEXT NOT NULL, " +
            ITEM_UOM + " TEXT NOT NULL, " +
            LOAD_TOT_PRICE + " TEXT NOT NULL);";

    //CREATE CUSTOMER MASTER
    private static final String CREATE_CUSTOMER = "create table " + TABLE_CUSTOMER + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUST_NAME_AR + " TEXT NOT NULL, " + CUST_NAME_EN + " TEXT NOT NULL," +
            "" + CUST_NUM + "  TEXT NOT NULL, " + CUST_DIST_CHANNEL + " TEXT NOT NULL, " +
            "" + CUST_DIVISION + " TEXT NOT NULL, " + CUST_SALES_ORG + " TEXT NOT NULL, " +
            "" + CUST_CREDIT_LIMIT + " TEXT NOT NULL, " + CUST_AVAIL_BAL + " TEXT NOT NULL, " +
            "" + CUST_PAYMENT_TERM + " TEXT NOT NULL, " + CUST_ADDRESS + " TEXT NOT NULL, " +
            "" + CUST_TYPE + " TEXT NOT NULL, " + CUST_POSSESSED_EMPTY_BOTTLE + " TEXT NOT NULL, " +
            "" + CUST_POSSESSED_FILLED_BOTTLE + " TEXT NOT NULL, " +
            "" + CUST_SALE + " TEXT NOT NULL, " + CUST_ORDER + " TEXT NOT NULL, " +
            "" + CUST_COLLECTION + " TEXT NOT NULL, "  + CUST_LATITUDE + " TEXT NOT NULL, " +
            "" + IS_STOCK_CAPTURED + " TEXT NOT NULL, " + CUST_CREATED_DATE + " TEXT , " +
            "" + CUST_LONGITUDE + " TEXT NOT NULL);";

    // CREATE ITEM MASTER
    private static final String CREATE_ITEMS = "create table " + TABLE_ITEM + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_CODE + " TEXT NOT NULL, " +
            "" + ITEM_NAME_EN + " TEXT NOT NULL, " + ITEM_NAME_AR + " TEXT NOT NULL, " +
            "" + ITEM_TYPE + " TEXT NOT NULL, " + ITEM_PRICE + " TEXT NOT NULL, " +
            "" + ITEM_CON_FECTOR + " TEXT NOT NULL, " + ITEM_UOM + " TEXT NOT NULL, " +
            "" + ITEM_BARCODE + " TEXT NOT NULL, " + ITEM_DIVISION + " TEXT NOT NULL);";

    // CREATE FREE GOODS
    private static final String CREATE_FREE_GOODS = "create table " + TABLE_FREE_GOODS + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UNIQUE_ID + " TEXT NOT NULL, " +
            "" + FG_CONDITION_RECORD + " TEXT NOT NULL, " + FG_CUSTOMER_ID + " TEXT NOT NULL," +
            "" + FG_QUA_ITEM_ID + " TEXT NOT NULL," + FG_QUA_ITEM_NAME + " TEXT NOT NULL," +
            "" + FG_ASSI_ITEM_ID + " TEXT NOT NULL," + FG_ASSI_ITEM_NAME + " TEXT NOT NULL," +
            "" + FG_QUA_ITEM_QUALITY + " TEXT NOT NULL," +
            "" + FG_ASS_ITEM_QUALITY + " TEXT NOT NULL);";

    //CREATE INVOICE HEADER
    private static final String CREATE_INVOICE_HEADER = "create table " + TABLE_INVOICE_HEADER + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SVH_CODE + " TEXT NOT NULL, " + SVH_INVOICE_TYPE + " TEXT NOT NULL," +
            "" + SVH_INVOICE_TYPE_CODE + " TEXT NOT NULL, " + SVH_CUST_CODE + " TEXT NOT NULL, " +
            "" + SVH_CUST_SALES_ORG + " TEXT NOT NULL, " + SVH_CUST_DIST_CHANNEL + " TEXT NOT NULL, " +
            "" + SVH_CUST_DIVISION + " TEXT NOT NULL, " + SVH_INVOICE_DATE + " TEXT NOT NULL, " +
            "" + SVH_DELVERY_DATE + " TEXT NOT NULL, " + SVH_CUST_NAME + " TEXT NOT NULL, " +
            "" + SVH_TOT_AMT_SALES + " TEXT NOT NULL, " + SVH_INVOICE_HEADER_DISC_IN_PER + " TEXT NOT NULL, " +
            "" + SVH_INVOICE_HEADER_DISC_IN_VAL + " TEXT NOT NULL, " + SVH_VAT_PER + " TEXT NOT NULL, " +
            "" + SVH_VAT_VAL + " TEXT NOT NULL);";

    //CREATE INVOICE ITEMS
    private static final String CREATE_INVOICE_ITEMS = "create table " + TABLE_INVOICE_ITEMS + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SVH_CODE + " TEXT NOT NULL, " + ITEM_CODE + " TEXT NOT NULL," +
            "" + ITEM_NAME_EN + " TEXT NOT NULL, " + ITEM_PRICE + " TEXT NOT NULL, " +
            "" + ITEM_QTY + " TEXT NOT NULL, " + ITEM_COUPON_CODE + " TEXT NOT NULL, " +
            "" + ITEM_DISCOUNT_PER + " TEXT NOT NULL, " + ITEM_DISCOUNT_VAL + " TEXT NOT NULL, " +
            "" + ITEM_VAT_VAL + " TEXT NOT NULL, " +
            "" + ITEM_VAT_PER + " TEXT NOT NULL);";

    //CREATE COLLECTION HEADER
    private static final String CREATE_COLLECTION_HEADER = "create table " + TABLE_COLLECTION_HEADER + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DOC_CODE + " TEXT NOT NULL, " + COL_INVOICE_NO + " TEXT NOT NULL," +
            "" + COL_CUST_NO + " TEXT NOT NULL, " + COL_CUST_NAME + " TEXT NOT NULL, " + COL_IS_COLLECTED + " TEXT NOT NULL, " + ""
            + COL_AMOUNT + " TEXT NOT NULL, " + COL_INVOICE_DATE + " TEXT NOT NULL, " +
            COL_DUE_AMOUNT + " TEXT NOT NULL, " + COL_DUE_DATE + " TEXT NOT NULL, " +

            "" + COL_PAY_METHOD + " TEXT NOT NULL);";

    //CREATE COLLECTION ITEMS
    private static final String CREATE_COLLECTION_ITEMS = "create table " + TABLE_COLLECTION_ITEMS + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DOC_CODE + " TEXT NOT NULL, " + COL_TOT_AMT + " TEXT NOT NULL," +
            "" + COL_PAY_TERM + " TEXT NOT NULL);";


    // CREATE LOAD ITEM
    private static final String CREATE_TABLE_UNLOAD_ITEMS = "create table " + TABLE_UNLOAD_ITEMS + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_CODE + " TEXT NOT NULL, " +
            ITEM_NAME_EN + " TEXT NOT NULL, " + ITEM_QTY + " TEXT NOT NULL, " +
            LOAD_NO + " TEXT NOT NULL, " + "" + LOAD_DATE + " TEXT NOT NULL, " +
            ITEM_UOM + " TEXT NOT NULL, " +
            LOAD_TOT_PRICE + " TEXT NOT NULL);";

    // CREATE LOAD ITEM
    private static final String CREATE_TABLE_PAYMENT = "create table " + TABLE_PAYMENT + "(" + PAYMENT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PAYMENT_INVOICE_ID + " TEXT NOT NULL, " +
            PAYMENT_COLLECTION_ID + " TEXT NOT NULL, " + PAYMENT_TYPE + " TEXT NOT NULL, " +
            PAYMENT_DATE + " TEXT NOT NULL, " + "" + PAYMENT_CHEQUE_NO + " TEXT NOT NULL, " +
            PAYMENT_BANK_NAME + " TEXT NOT NULL, " +
            PAYMENT_AMOUNT + " TEXT NOT NULL, " +
            PAYMENT_CUSTOMER_ID + " TEXT NOT NULL);";

    private static final String CREATE_TRANSACTION = "create table " + TABLE_TRANSACTION + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TR_TYPE + " TEXT NOT NULL, " + TR_DATE + " TEXT NOT NULL," +
            TR_CUSTOMER_NUM + " TEXT, " + "" + TR_CUSTOMER_NAME + " TEXT , " +
            TR_SALESMAN_ID + " TEXT , " + "" + TR_INVOICE_ID + " TEXT , " +
            TR_ORDER_ID + " TEXT , " + "" + TR_COLLECTION_ID + " TEXT , " +
            "" + TR_PYAMENT_ID + " TEXT ," + "" + TR_IS_POSTED + " TEXT );";

    private static final String CREATE_RECENT_CUSTOMER = "create table " + TABLE_RECENT_CUSTOMER + "(" + RECENT_CUSTOMER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_ID + " TEXT NOT NULL, "
            + CUSTOMER_NAME + " TEXT NOT NULL," +
             DATE_TIME + " TEXT );";

    // ORDER HEADER
    private static final String CREATE_TABLE_ORDER_HEADER = "create table " + TABLE_ORDER_HEADER + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "" + ORDER_NO + " TEXT NOT NULL, " + "" + SALESMAN_ID + " TEXT NOT NULL, " +
            "" + CUST_NUM + " TEXT NOT NULL, " + "" + ORDER_AMOUNT + " TEXT NOT NULL, " +
            "" + ORDER_DATE + " TEXT NOT NULL);";

    // CREATE LOAD ITEM
    private static final String CREATE_TABLE_ORDER_ITEMS = "create table " + TABLE_ORDER_ITEMS + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_CODE + " TEXT NOT NULL, " +
            ITEM_NAME_EN + " TEXT NOT NULL, " + ITEM_QTY + " TEXT NOT NULL, " +
            ORDER_NO + " TEXT NOT NULL, " + "" + ORDER_DATE + " TEXT NOT NULL, " +
            ITEM_UOM + " TEXT NOT NULL, " +
            ITEM_PRICE + " TEXT NOT NULL);";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_SALESMAN);
        db.execSQL(CREATE_TABLE_LOAD_HEADER);
        db.execSQL(CREATE_TABLE_LOAD_ITEMS);
        db.execSQL(CREATE_CUSTOMER);
        db.execSQL(CREATE_ITEMS);
        db.execSQL(CREATE_FREE_GOODS);
        db.execSQL(CREATE_INVOICE_HEADER);
        db.execSQL(CREATE_INVOICE_ITEMS);
        db.execSQL(CREATE_COLLECTION_HEADER);
        db.execSQL(CREATE_COLLECTION_ITEMS);
        db.execSQL(CREATE_TRANSACTION);
        db.execSQL(CREATE_TABLE_VANSTOCK_HEADER);
        db.execSQL(CREATE_TABLE_VANSTOCK_ITEMS);
        db.execSQL(CREATE_TABLE_UNLOAD_ITEMS);
        db.execSQL(CREATE_TABLE_PAYMENT);
        db.execSQL(CREATE_TABLE_ORDER_HEADER);
        db.execSQL(CREATE_TABLE_ORDER_ITEMS);
        db.execSQL(CREATE_RECENT_CUSTOMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
//        onCreate(db);
    }
}