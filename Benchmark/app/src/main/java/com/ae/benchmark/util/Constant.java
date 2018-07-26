package com.ae.benchmark.util;

/**
 * Created by Himm on 4/20/2018.
 */

public class Constant {

    public static String AppNameSuper = "Benchmark";

    // Newly Final created production
    public static String URL = "http://192.254.133.11/~staging/newproject/";

    public static class SHRED_PR {
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

    public static class TRANSACTION_TYPES {
        public static final String TT_LOAD_CONF = "tt_load_conf";
        public static final String TT_STOCK_CAP = "tt_stock_cap";
        public static final String TT_SALES_CREATED = "tt_sales_created";
        public static final String TT_CREDIT_COLLECTION = "tt_credit_collection";
        public static final String TT_CASH_COLLECTION = "tt_cash_collection";
        public static final String TT_PAYMENT_BY_CASH = "tt_payment_by_cash";
        public static final String TT_PAYMENT_BY_CHEQUE = "tt_payment_by_cheque";
        public static final String TT_CUST_CREATED = "tt_cust_created";
        public static final String TT_UNLOAD= "tt_unload";
        public static final String TT_OREDER_CREATED= "tt_order_created";
        public static final String TT_END_DAY= "tt_end_day";
    }

    public static class NEW_CUSTOMER {
        public static  String edtTradeName = "edtTradeName";
        public static  String edtArea = "edtArea";
        public static  String edtStreet = "edtStreet";
        public static  String edtEmail = "edtEmail";
        public static  String edtMobile = "edtMobile";
        public static  String edtCrNum = "edtCrNum";
        public static  String edtCrExpDate = "edtCrExpDate";
        public static  String edtIqmaNo = "edtIqmaNo";
        public static  String edtIqamExpDate = "edtIqamExpDate";
    }
}
