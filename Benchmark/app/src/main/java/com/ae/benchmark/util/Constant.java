package com.ae.benchmark.util;

/**
 * Created by Himm on 4/20/2018.
 */

public class Constant {

    public static String AppNameSuper = "Benchmark";

    // Newly Final created production
//    public static String URL = "http://192.254.133.11/~staging/newproject/";
    public static String URL = "https://gbcportal.gbc.sa/sap/opu/odata/sap/ZSFA_5G_DOWNLOAD_SRV";

    public static String checkBoxValue = "";
    public static String checkBoxFalse = "no";

    public static String NAV_AUDIT = "no";
    public static String VAN_STOCK = "no";
    public static String PRINT = "no";

    public static int TARGET_DTD_MTD = 10000;

    public static String INV_LAST = "INV_LAST";
    public static String ORD_LAST = "ORD_LAST";
    public static String LOAD_LAST = "LOAD_LAST";
    public static String COLLECTION_LAST = "COLLECTION_LAST";
    public static String CUSTOMER_LAST = "CUSTOMER_LAST";
    public static String PAYMENT_LAST = "PAYMENT_LAST";
    public static String LOAD_REQUEST_LAST = "LOAD_REQUEST_LAST";


    public static class SHRED_PR {
        public static String END_DATE = "end_date";
        public static String IS_DAY_END = "is_day_end";
        public static final String SHARE_PREF = AppNameSuper + "_preferences";

        public static final String SALESMANID = "salesman_id";
        public static final String ISJPLOADED = "isJPLoaded";
        public static final String ISPAYMET = "isPayment";
        public static final String ISSALES = "isSales";
        public static final String ISDATA = "isData";
        public static final String ISLOGIN = "isLogin";
        public static final String ISINTRO = "isIntro";

        public static final String ISSTOCKCAPTURED = "isStockCaptured";
        public static final String EMPTYES = "emptyes";
        public static final String FCM_TOKEN = "fcm_token";

        public static final String USERNAME = "username";
        public static final String ISUNLOAD = "is_unload";
        public static final String ISCHECKIN = "is_checkin";
    }

    public static class SALESMAN {
        public static final String SALESMAN_ID = "SALESMAN";
        public static final String SALESMAN_NAME = "name1";
        public static final String SALESMAN_SALES_ORG = "salesorg";
        public static final String SALESMAN_CHANNEL = "channel";
        public static final String SALESMAN_DIVISION = "Division";
        public static final String SALESMAN_VEHICLE = "Vehicle";
        public static final String SALESMAN_SUPERVISOR = "supervisor";


    }

    public static class TRANSACTION_TYPES {
        public static final String TT_LOAD_CONF = "tt_load_conf";
        public static final String TT_STOCK_CAP = "tt_stock_cap";
        public static final String TT_SALES_CREATED = "tt_sales_created";
        public static final String TT_CREDIT_COLLECTION = "tt_credit_collection";
        public static final String TT_CASH_COLLECTION = "tt_cash_collection";
        public static final String TT_PAYMENT_BY_CASH = "tt_payment_by_cash";
        public static final String TT_PAYMENT_BY_CHEQUE = "tt_payment_by_cheque";
        public static final String TT_CUST_CREATED = "tt_cust_created";
        public static final String TT_UNLOAD = "tt_unload";
        public static final String TT_OREDER_CREATED = "tt_order_created";
        public static final String TT_END_DAY = "tt_end_day";
        public static final String TT_LOAD_CREATE = "tt_load_create";
    }

    public static class NEW_CUSTOMER {
        public static String edtCustomerName = "edtCustomerName";
        public static String edtDistCh = "edtDistCh";
        public static String edtDiv = "edtDiv";
        public static String edtSalesOrg = "edtSalesOrg";
        public static String edtCreditLim = "edtCreditLim";
        public static String edtAvailBal = "edtAvailBal";
        public static String edtPayTerm = "edtPayTerm";
        public static String edtAddress = "edtAddress";
        public static String edtCustType = "edtCustType";
    }
}
