package com.ae.benchmark.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.ALLItemsListActivity;
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.activities.PreOrderRequestActivity;
import com.ae.benchmark.data.Const;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.localdb.DatabaseHelper;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterAudit extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Transaction> mItemList;
    Context mContext;
    String type;
    DBManager db;
    ArrayList<Item> arr;

    JSONObject jsonObject;

    public RecyclerAdapterAudit(List<Transaction> itemList, Context context, String type) {
        this.mItemList = itemList;
        mContext = context;
        this.type = type;
        db = new DBManager(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.row_audit, parent, false);
        return new RecyclerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (!isPositionHeader(position)) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        final Transaction item = mItemList.get(position);


        item.setTr_is_posted("No");
        switch (item.tr_type) {
            case Constant.TRANSACTION_TYPES.TT_STOCK_CAP:
                holder.txtType.setText("SC");
                break;
            case Constant.TRANSACTION_TYPES.TT_SALES_CREATED:
                holder.txtType.setText("SI");
                holder.txtInvoiceNo.setText("INV " + item.tr_invoice_id);
                break;
            case Constant.TRANSACTION_TYPES.TT_OREDER_CREATED:
                holder.txtType.setText("OR");
                holder.txtInvoiceNo.setText("ORD " + item.tr_invoice_id);
                break;
            case Constant.TRANSACTION_TYPES.TT_LOAD_CONF:
                holder.txtType.setText("LC");
                break;
            case Constant.TRANSACTION_TYPES.TT_LOAD_CREATE:
                holder.txtType.setText("LCR");
                break;
            case Constant.TRANSACTION_TYPES.TT_RETURN_CREATED:
                holder.txtType.setText("BR");
                holder.txtInvoiceNo.setText("RET " + item.tr_invoice_id);
                break;
        }

        if (item.getTr_is_posted().equals("No")) {
            holder.imgIsPosted.setBackgroundResource(R.drawable.ic_action_sync);
        } else {
            holder.imgIsPosted.setBackgroundResource(R.drawable.ic_icon_verified_sel);
        }

        if (type.equalsIgnoreCase("")) {
            holder.imgIsPosted.setBackgroundResource(R.drawable.ico_magni_glass);
        }

        holder.imgPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (item.tr_type) {


                    case Constant.TRANSACTION_TYPES.TT_STOCK_CAP:
                        holder.txtType.setText("SC");
                        break;
                    case Constant.TRANSACTION_TYPES.TT_SALES_CREATED:
                        holder.txtType.setText("SL");
                        holder.txtInvoiceNo.setText("INV " + item.tr_invoice_id);
                        db.open();

                        arr = db.getItemsForPrint(DatabaseHelper.SVH_CODE,
                                item.tr_invoice_id, DatabaseHelper.TABLE_INVOICE_ITEMS);
                        jsonObject = invoicePrint(arr, item.tr_customer_num, db);
                        UtilApp.askForPrintNoFinish(mContext, jsonObject);

                        break;
                    case Constant.TRANSACTION_TYPES.TT_OREDER_CREATED:
                        holder.txtType.setText("OR");
                        holder.txtInvoiceNo.setText("ORD " + item.tr_invoice_id);
                        db.open();
                        arr = db.getItemsForPrint(DatabaseHelper.ORDER_NO,
                                item.tr_invoice_id, DatabaseHelper.TABLE_ORDER_ITEMS);
                        jsonObject = orderPrint(arr, item.tr_customer_num, db);

                        UtilApp.askForPrintNoFinish(mContext, jsonObject);

                        break;
                    case Constant.TRANSACTION_TYPES.TT_LOAD_CONF:
                        holder.txtType.setText("LC");

                        db.open();
                        arr = db.getItemsForPrint(DatabaseHelper.LOAD_NO,
                                item.tr_invoice_id, DatabaseHelper.TABLE_VANSTOCK_ITEMS);
                        jsonObject = loadConfPrint(mContext, arr, item.tr_customer_num, db);

                        UtilApp.askForPrintNoFinish(mContext, jsonObject);

                        break;
                    case Constant.TRANSACTION_TYPES.TT_LOAD_CREATE:
                        holder.txtType.setText("LCR");
                        break;

                    case Constant.TRANSACTION_TYPES.TT_RETURN_CREATED:
                        holder.txtType.setText("OR");
                        holder.txtInvoiceNo.setText("ORD " + item.tr_invoice_id);
                        db.open();
                        arr = db.getItemsForPrint(DatabaseHelper.ORDER_NO,
                                item.tr_invoice_id, DatabaseHelper.TABLE_RETURN_ITEMS);
                        jsonObject = returnPrint(mContext, arr, item.tr_customer_num, db);

                        UtilApp.askForPrintNoFinish(mContext, jsonObject);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size(); // header
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txtType, txtInvoiceNo;

        public ImageView imgIsPosted, imgPrint;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public RecyclerItemViewHolder(View parent, final Context context) {
            super(parent);

            txtType = (TextView) parent.findViewById(R.id.txtType);
            txtInvoiceNo = (TextView) parent.findViewById(R.id.txtInvoiceNo);
            imgIsPosted = (ImageView) parent.findViewById(R.id.imgIsPosted);
            imgPrint = (ImageView) parent.findViewById(R.id.imgPrint);

        }
    }

    //INVOICE PRINT
    private JSONObject invoicePrint(ArrayList<Item> arr, String cust_num, DBManager db) {
        final JSONObject outterObject = new JSONObject();
        try {
            db.open();
            Customer customer = db.getCustomerByNum(cust_num);


            JSONArray headerData = null;
            JSONArray headerJarr = new JSONArray();
            JSONObject totalObj = new JSONObject();

            double price = 0;
            for (int i = 0; i < arr.size(); i++) {
                headerData = new JSONArray();

                headerData.put(i + "");// SI NO
                headerData.put(arr.get(i).item_code); // ITEM CODE
                headerData.put(arr.get(i).item_name_en);// DESC
                headerData.put(arr.get(i).item_uom);// UOM

                headerData.put(arr.get(i).item_qty);// QTY
                headerData.put(arr.get(i).item_price); // UNIT Price
                double totAmt = Double.parseDouble(arr.get(i).item_price) * Double.parseDouble(arr.get(i).item_qty);
                headerData.put(totAmt + ""); // TOTAL amount
                price = totAmt;

                double vatAmt = totAmt * 5 / 100;
                headerData.put(vatAmt + ""); //Vat Amt
                headerData.put("5"); //Vat %
                double amtSAR = totAmt + vatAmt;
                headerData.put(amtSAR + ""); //Amount SAR
            }

            outterObject.put("customer_name_en", "");
            outterObject.put("customer_name_ar", "");
            outterObject.put("SALESMAN", "");
            outterObject.put("ROUTE", "");
            outterObject.put("invoice_date", UtilApp.getCurrentDate());
            outterObject.put("customer_address", "");
            outterObject.put("print_type", Constant.SALES_INVOICE);
            outterObject.put("DOC DATE", "");
            outterObject.put("LPONO", "");
            outterObject.put("CONTACTNO", "");
            outterObject.put("TRN", "");
            outterObject.put("ORDERNO", "");
            outterObject.put("TRIP START DATE", "");
            outterObject.put("invoicepriceprint", "1");
            outterObject.put("invoicepaymentterms", "2");
            outterObject.put("SUB TOTAL", price + "");
            outterObject.put("invoicenumber", arr.get(0).sales_inv_nun);
            outterObject.put("TIME", UtilApp.getCurrentDate());
            outterObject.put("LANG", "AR");
            outterObject.put("INVOICE DISCOUNT", "");
            outterObject.put("VAT", "5");
            outterObject.put("NET SALES", price);
            outterObject.put("invoicefooter", "");

            Const.custPayID = customer.cust_num;
            Const.custPayName = customer.cust_name_en;
            Const.custPayNameAR = customer.cust_name_ar;
            Const.custPayAddress = customer.cust_address;
            Const.cusVATno = "";
            Const.cusBranchID = "";
            Const.cusBranchName = "";

            // HEADER ARRAY
            JSONArray HEADER = new JSONArray();
            HEADER.put("SI No");
            HEADER.put("Item Code");
            HEADER.put("Description");
            HEADER.put("UOM");
            HEADER.put("QTY");
            HEADER.put("UNIT Price");
            HEADER.put("Total amount");
            HEADER.put("Total Disc");
            HEADER.put("Vat Amt");
            HEADER.put("Vat %");
            HEADER.put("Amount SAR");

            //ADDING TOTAL IN MAIN OBJECT
            totalObj.put("Total Amount(AED)", price);
            double afterTax = price * 5 / 100;
            totalObj.put("Total Befor TAX(AED)", price);
            totalObj.put("GROSS AMOUNT: AED - ", afterTax);

            //ADDING DATA IN MAIN OBJECT
            JSONArray totalArr = new JSONArray();
            totalArr.put(totalObj);

            outterObject.put("TOTAL", totalArr);
            outterObject.put("data", headerJarr);
            outterObject.put("HEADERS", HEADER);

            Log.v("", "Check this json array: " + outterObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return outterObject;
    }

    //ORDER PRINT
    private JSONObject orderPrint(ArrayList<Item> arr, String cust_num, DBManager db) {


        final JSONObject outterObject = new JSONObject();

        try {
            db.open();
            Customer customer = db.getCustomerByNum(cust_num);

            JSONArray headerData = null;
//                JSONArray headerDataReturn = null;
            JSONArray headerJarr = new JSONArray();
            JSONObject totalObj = new JSONObject();


            double totAmt = 0;
            for (int i = 0; i < arr.size(); i++) {
                headerData = new JSONArray();

                Item item = arr.get(i);

                headerData.put(item.item_code); // ITEM CODE
                headerData.put(item.item_name_en);// EN NAME
                headerData.put(item.item_name_en);// AR NAME

                headerData.put(item.item_uom);// UOM
                double totUnits = +Double.parseDouble(item.item_qty);
                headerData.put(totUnits + ""); // TOTAL UNITS
                headerData.put(item.item_price); // UNIT PRICE
                totAmt = +Double.parseDouble(item.item_price);
                headerData.put(totAmt + ""); // Amount

                headerJarr.put(headerData);
            }

            outterObject.put("customer_name_en", "");
            outterObject.put("customer_name_ar", "");
            outterObject.put("SALESMAN", "");
            outterObject.put("ROUTE", "");
            outterObject.put("TripID", "fdasf");
            outterObject.put("invoice_date", UtilApp.getCurrentDate());
            outterObject.put("customer_address", "");
            outterObject.put("print_type", Constant.ORDER_REQUEST);
            outterObject.put("DOC DATE", "");
            outterObject.put("LPONO", "");
            outterObject.put("CONTACTNO", "");
            outterObject.put("CUSTOMER", "customer1 - custoerm2");
            outterObject.put("ADDRESS", "");
            outterObject.put("ARBADDRESS", "");
            outterObject.put("ORDERNO", "");
            outterObject.put("TRIP START DATE", "");
            outterObject.put("invoicepriceprint", "1");
            outterObject.put("invoicepaymentterms", "2");
            outterObject.put("SUB TOTAL", totAmt + "");
            outterObject.put("invoicenumber", arr.get(0).order_id);
            outterObject.put("TIME", UtilApp.getCurrentDate());
            outterObject.put("LANG", "AR");
            outterObject.put("INVOICE DISCOUNT", "");
            outterObject.put("VAT", "5");
            outterObject.put("NET SALES", totAmt);
            outterObject.put("invoicefooter", "");

            Const.custPayID = customer.cust_num;
            Const.custPayName = customer.cust_name_en;
            Const.custPayNameAR = customer.cust_name_ar;
            Const.custPayAddress = customer.cust_address;
            Const.cusVATno = "";
            Const.cusBranchID = "";
            Const.cusBranchName = "";

            JSONArray HEADER = new JSONArray();
            HEADER.put("ITEM NO");
            HEADER.put("ENGLISH DESCRIPTION");
            HEADER.put("ARABIC DESCRIPTION");
            HEADER.put("UOM");
            HEADER.put("TOTAL UNITS");
            HEADER.put("UNIT PRICE");
            HEADER.put("AMOUNT");
//                        }


            //ADDING TOTAL IN MAIN OBJECT
            totalObj.put("Total Amount(AED)", totAmt);
            double afterTax = totAmt * 5 / 100;
            totalObj.put("Total Befor TAX(AED)", totAmt);
            totalObj.put("GROSS AMOUNT: AED - ", afterTax);

            //ADDING DATA IN MAIN OBJECT
            JSONArray totalArr = new JSONArray();
            totalArr.put(totalObj);

            outterObject.put("TOTAL", totalArr);
            outterObject.put("data", headerJarr);
            outterObject.put("HEADERS", HEADER);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outterObject;
    }

    //VAN STOCK PRINT
    private JSONObject loadConfPrint(Context context, ArrayList<Item> arr, String cust_num, DBManager db) {


        final JSONObject mainArr = new JSONObject();


        try {
            mainArr.put("print_type", Constant.VAN_STOCK);
            mainArr.put("ROUTE", UtilApp.ReadSharePrefrence(context, Constant.SHRED_PR.SALESMANID));
            mainArr.put("DOC DATE", UtilApp.getCurrentDate());
            mainArr.put("TIME", "00:00:00");
            mainArr.put("SALESMAN", UtilApp.ReadSharePrefrence(context, Constant.SHRED_PR.SALESMANID));
            mainArr.put("CONTACTNO", "1234");
            mainArr.put("supervisorname", "-");
            mainArr.put("LANG", "AR");
            mainArr.put("INVOICETYPE", "ORDER REQUEST");
            mainArr.put("invoicenumber", "5465");
            mainArr.put("SALESMANNO", "45645");


            mainArr.put("invoicepaymentterms", "3");
//                    mainArr.put("CUSTOMERID", customer.cust_num);
//                    mainArr.put("CUSTOMER", customer.cust_name_en);
//                    mainArr.put("ADDRESS", customer.cust_address);
//                    mainArr.put("ARBADDRESS", customer.cust_address);
            mainArr.put("TourID", UtilApp.ReadSharePrefrence(context, Constant.SHRED_PR.SALESMANID));
            mainArr.put("LANG", "ar");
            mainArr.put("invoicepaymentterms", "2");
            mainArr.put("RECEIPT", "INVOICE RECEIPT");
            mainArr.put("SUB TOTAL", "1000");
            mainArr.put("INVOICE DISCOUNT", "20");
            mainArr.put("NET SALES", "980");
            mainArr.put("TRIP START DATE", "");

            JSONArray TOTAL = new JSONArray();
            JSONObject jobjTot = new JSONObject();

            double tot = 0.0;

            JSONArray outterData = new JSONArray();
            JSONArray data = null;
            double qty = 0.0;
            for (int i = 0; i < arr.size(); i++) {
                Item item = arr.get(i);
                tot += Double.parseDouble(item.item_price);
                qty = Double.parseDouble(item.item_qty);

                data = new JSONArray();

                data.put(item.item_code);
                data.put(item.item_name_en);
                data.put(item.item_qty);
                data.put(item.item_qty);
                data.put("" + qty);

                outterData.put(data);
            }

            JSONArray totArr = new JSONArray();
            JSONObject totObj = new JSONObject();

            totObj.put("Total Amount(AED)", "" + tot);
            totObj.put("Total Befor TAX(AED)", "" + tot);
            totObj.put("GROSS AMOUNT: AED - ", "" + tot);
            totArr.put(totObj);
            mainArr.put("TOTAL", totArr);

            mainArr.put("closevalue", tot);

            JSONArray HEADERS = new JSONArray();
            HEADERS.put("ITEM#");
            HEADERS.put("DESCRIPTION");
            HEADERS.put("LOADED QTY");
            HEADERS.put("SALE QTY");
            HEADERS.put("TRUCK STOCK");
            HEADERS.put("TOTAL");

            mainArr.put("HEADERS", HEADERS);

            mainArr.put("data", outterData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mainArr;
    }

    //RETURN PRINT
    private JSONObject returnPrint(Context context, ArrayList<Item> arr, String cust_num, DBManager db) {


        final JSONObject mainArr = new JSONObject();


        try {
            JSONArray jInter = new JSONArray();
            JSONObject jDict = new JSONObject();
//            jDict.put(App.REQUEST, App.BAD_RETURN_REPORT);

            mainArr.put("print_type", Constant.BAD_RETURN_REPORT);
            mainArr.put("ROUTE", UtilApp.ReadSharePrefrence(context, Constant.SHRED_PR.SALESMANID));
            mainArr.put("DOC DATE", UtilApp.getCurrentDate());
            mainArr.put("TIME", UtilApp.getCurrentTime());
            mainArr.put("SALESMAN", UtilApp.ReadSharePrefrence(context, Constant.SHRED_PR.SALESMANID));
            mainArr.put("CONTACTNO", "1234");
            mainArr.put("DOCUMENT NO", "80001234");  //Load Summary No
            mainArr.put("ORDERNO", "80001234");  //Load Summary No
            mainArr.put("TRIP START DATE", "");
            mainArr.put("supervisorname", "-");
            mainArr.put("TripID", UtilApp.ReadSharePrefrence(context, Constant.SHRED_PR.SALESMANID));
            //mainArr.put("invheadermsg","HAPPY NEW YEAR");
            mainArr.put("LANG", "en");
            mainArr.put("invoicepaymentterms", "2");
            mainArr.put("invoicenumber", "1300000001");
            mainArr.put("INVOICETYPE", "SALES INVOICE");
            String arabicCustomer = "اللولو هايبر ماركت";
            mainArr.put("CUSTOMER", "LULU HYPER MARKET" + "-" + arabicCustomer);
            mainArr.put("ADDRESS", "3101, 21st Street, Riyadh");
            mainArr.put("ARBADDRESS", "");
            mainArr.put("displayupc", "0");
            mainArr.put("invoicepriceprint", "1");
            mainArr.put("SUB TOTAL", "1000");
            mainArr.put("INVOICE DISCOUNT", "20");
            mainArr.put("NET SALES", "980");
            mainArr.put("closevalue", "+5000");
            mainArr.put("damagevariance", "+1000");
            mainArr.put("TOTAL_DAMAGE_VALUE", "+2000");
            //mainArr.put("Load Number","1");


//            JSONArray jData = new JSONArray();
//            for(DepositReport obj:depositReports){
//                JSONArray jData1 = new JSONArray();
//                jData1.put(obj.getInvoiceNo());
//                jData1.put(obj.getCustomerNo());
//                jData1.put(obj.getCustomerName());
//                jData1.put(obj.getChequeNo());
//                jData1.put(obj.getChequeDate());
//                jData1.put(obj.getBankName());
//                jData1.put(obj.getChequeAmount());
//                totalCheque += Double.parseDouble(obj.getChequeAmount());
//                jData1.put(obj.getCashAmount());
//                totalCash += Double.parseDouble(obj.getCashAmount());
//                jData.put(jData1);
//            }


            JSONArray HEADERS = new JSONArray();
            JSONArray TOTAL = new JSONArray();
            HEADERS.put("ITEM#");
            HEADERS.put("DESCRIPTION");
            HEADERS.put("INVOICE CREDIT");
            HEADERS.put("LOADED IN");
            HEADERS.put("PRICE");//Summation of all
            HEADERS.put("-----VARIANCE----- QTY         AMOUNT");  //Truck Damage
            //HEADERS.put("Description");
            //HEADERS.put(obj1);
            // HEADERS.put(obj2);
            mainArr.put("HEADERS", HEADERS);
            JSONObject totalObj = new JSONObject();
            totalObj.put("INVOICE CREDIT", "+200");
            totalObj.put("LOADED IN", "+100");  //Summation of all
            totalObj.put("-----VARIANCE----- QTY         AMOUNT", "+100");  //Summation of all
            TOTAL.put(totalObj);
            mainArr.put("TOTAL", TOTAL);
            JSONArray jData1 = new JSONArray();
            jData1.put("14020106");
            jData1.put("Test Material");
            jData1.put("+10");
            jData1.put("+9");
            jData1.put("+12");
            jData1.put("-1         +12");
            JSONArray jData2 = new JSONArray();
            jData2.put("14020106");
            jData2.put("Test Material");
            jData2.put("+10");
            jData2.put("+9");
            jData2.put("+12");
            jData2.put("-1         +12");
            JSONArray jData3 = new JSONArray();
            jData3.put("14020106");
            jData3.put("Test Material");
            jData3.put("+10");
            jData3.put("+9");
            jData3.put("+12");
            jData3.put("-1         +12");
            JSONArray jData = new JSONArray();
            jData.put(jData1);
            jData.put(jData2);
            jData.put(jData3);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DATA", jData);
            jsonObject.put("HEADERS", HEADERS);
            jsonObject.put("TOTAL", totalObj);
            JSONArray jDataNew = new JSONArray();
            jDataNew.put(jsonObject);
            mainArr.put("data", jData);
            // mainArr.put("tcData",jData);
            //  mainArr.put("creditData",jData);

            /*mainArr.put("data",jData);
            mainArr.put("data",jData);
            mainArr.put("data",jData);
*/
//            jDict.put("mainArr", mainArr);
//            jInter.put(jDict);
//            jArr.put(jInter);
//            jArr.put(HEADERS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mainArr;
    }


}
