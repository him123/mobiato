package com.ae.benchmark.util;
/**
 * Created by Rakshit on 03-Feb-17.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Message;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ae.benchmark.data.Const;
import com.ae.benchmark.model.DevicesData;
import com.ae.benchmark.printer.Arabic6822;
import com.ae.benchmark.printer.JsonRpcUtil;
import com.ae.benchmark.printer.LinePrinterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class PrinterHelper {
    private static final UUID MY_UUID;
    byte[] BoldOff;
    byte[] BoldOn;
    byte[] CarriageReturn;
    byte[] CompressOff;
    byte[] CompressOn;
    byte[] DoubleHighOff;
    byte[] DoubleHighOn;
    byte[] DoubleWideOff;
    byte[] DoubleWideOn;
    byte[] NewLine;
    byte[] wakeUp;
    private android.app.ProgressDialog ProgressDialog;
    byte[] UnderlineOff;
    byte[] UnderlineOn;
    ArrayAdapter<String> adapter;
    private ArrayList<DevicesData> arrData = new ArrayList<>();
    ArrayList<BluetoothDevice> arrayListBluetoothDevices;
    BluetoothSocket btSocket;
    private String callbackId;
    int cnln;
    private int count;
    ArrayAdapter<String> detectedAdapter;
    String devicename;
    int endln;
    private HashMap<String, String> hashArabVales;
    private HashMap<String, Integer> hashArbPositions;
    private HashMap<String, Integer> hashPositions;
    private HashMap<String, Integer> hashValues;
    private boolean isEnglish;
    private boolean isExceptionThrown;
    boolean isTwice;
    private JSONObject jArr;
    private BluetoothAdapter mBtAdapter;
    private final BroadcastReceiver mPairReceiver;
    private final BroadcastReceiver mReceiverRequiresPin;
    private BroadcastReceiver myReceiver;
    private NotificationManager notificationManager;
    OutputStream outStream;
    private android.app.ProgressDialog progressDialog;
    byte[] resetprinter;
    String resolution;
    private int retryCount;
    private String sMacAddr;
    int startln;
    private JSONObject status;
    String strFormat;
    String strFormatBold;
    String strFormatHeader;
    String strFormatTitle;
    String strPrintLeftBold;
    String strUnderLine;
    Context context;
    private Activity activity;

    boolean isFirstInvoice = true;

    public PrinterHelper(Context context, Activity activity) {
        this.isTwice = false;
        this.resolution = "";
        this.callbackId = "";
        this.arrayListBluetoothDevices = null;
        this.isExceptionThrown = false;
        this.btSocket = null;
        this.outStream = null;
        this.devicename = "";
        this.startln = 4;
        this.endln = 12;
        this.cnln = 16;
        this.isEnglish = true;
        this.sMacAddr = "";
        this.BoldOn = new byte[]{(byte) 27, (byte) 71};
        this.BoldOff = new byte[]{(byte) 27, (byte) 72};
        this.UnderlineOn = new byte[]{(byte) 27, (byte) 45, (byte) 1};
        byte[] bArr = new byte[3];
        bArr[0] = (byte) 27;
        bArr[1] = (byte) 45;
        this.UnderlineOff = bArr;
        this.CompressOn = new byte[]{(byte) 27, (byte) 33, (byte) 4};
        bArr = new byte[3];
        bArr[0] = (byte) 27;
        bArr[1] = (byte) 33;
        this.CompressOff = bArr;
        this.NewLine = new byte[]{(byte) 13, (byte) 10};
        this.DoubleHighOn = new byte[]{(byte) 27, (byte) 33, (byte) 16};
        this.DoubleHighOff = new byte[]{(byte) 27, (byte) 33, (byte) 16};
        this.DoubleWideOn = new byte[]{(byte) 27, (byte) 33, (byte) 32};
        bArr = new byte[3];
        bArr[0] = (byte) 27;
        bArr[1] = (byte) 33;
        this.DoubleWideOff = bArr;
        this.resetprinter = new byte[]{(byte) 27, (byte) 64};
        this.CarriageReturn = new byte[]{(byte) 13, (byte) 10};
        this.wakeUp = new byte[]{(byte) (Integer.parseInt("1B", 16) & 0xff), (byte) (Integer.parseInt("40", 16) & 0xff)};
        this.retryCount = 0;
        this.count = 0;
        this.myReceiver = new C04641();
        this.mReceiverRequiresPin = new C04652();
        this.notificationManager = null;
        this.mPairReceiver = new C04663();
        this.context = context;
        this.activity = activity;
    }

    public void execute(JSONObject jsonArray) {
        try {

            this.jArr = jsonArray;
            this.arrData = new ArrayList();
//            print();

            Log.v("", "Check object: " + jsonArray.toString());


            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        try {

                            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");
                            outStream = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        PrinterHelper.this.printReports(PrinterHelper.this.sMacAddr);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            // Crashlytics.logException(e);
        }
    }

    class C04641 extends BroadcastReceiver {
        C04641() {
        }

        public void onReceive(Context context, Intent intent) {
            Message msg = Message.obtain();
            try {
                if ("android.bluetooth.device.action.FOUND".equals(intent.getAction())) {
                    Toast.makeText(context, "ACTION_FOUND", Toast.LENGTH_SHORT).show();
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (PrinterHelper.this.arrayListBluetoothDevices.size() < 1) {
                        PrinterHelper.this.detectedAdapter.add(device.getName() + StringUtilities.LF + device.getAddress());
                        PrinterHelper.this.arrayListBluetoothDevices.add(device);
                        PrinterHelper.this.detectedAdapter.notifyDataSetChanged();
                        return;
                    }
                    boolean flag = true;
                    for (int i = 0; i < PrinterHelper.this.arrayListBluetoothDevices.size(); i++) {
                        if (device.getAddress().equals(((BluetoothDevice) PrinterHelper.this.arrayListBluetoothDevices.get(i)).getAddress())) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        PrinterHelper.this.detectedAdapter.add(device.getName() + StringUtilities.LF + device.getAddress());
                        PrinterHelper.this.arrayListBluetoothDevices.add(device);
                        PrinterHelper.this.detectedAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Crashlytics.logException(e);
            }

        }
    }

    class C04652 extends BroadcastReceiver {
        C04652() {
        }

        public void onReceive(Context context, Intent intent) {
            try {
                BluetoothDevice newDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                Class<?> btDeviceInstance = Class.forName(BluetoothDevice.class.getCanonicalName());
                byte[] pin = (byte[]) btDeviceInstance.getMethod("convertPinToBytes", new Class[]{String.class}).invoke(newDevice, new Object[]{"1234"});
                Log.e("Success", "success" + ((Boolean) btDeviceInstance.getMethod("setPin", new Class[]{byte[].class}).invoke(newDevice, new Object[]{pin})).booleanValue());
            } catch (Exception e) {
                e.printStackTrace();
                // Crashlytics.logException(e);
            }
        }
    }

    class C04663 extends BroadcastReceiver {
        C04663() {
        }

        public void onReceive1(Context context, Intent intent) {
            try {
                if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(intent.getAction())) {
                    int state = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", ExploreByTouchHelper.INVALID_ID);
                    int prevState = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", ExploreByTouchHelper.INVALID_ID);
                    if (state == 12 && prevState == 11) {
                        Toast.makeText(context, "Paired", Toast.LENGTH_SHORT).show();
                    } else if (state == 10 && prevState == 12) {
                        Toast.makeText(context, "Unpaired", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Crashlytics.logException(e);
            }
        }

        public void onReceive(Context arg0, Intent arg1) {
        }
    }

    class C04694 implements Runnable {
        private final /* synthetic */ ArrayList val$arrData;
        private final /* synthetic */ String[] val$arrDevices;

        /* renamed from: com.phonegap.sfa.DotmatHelper.4.1 */
        class C04671 implements OnClickListener {
            private final /* synthetic */ ArrayList val$arrData;

            C04671(ArrayList arrayList) {
                this.val$arrData = arrayList;
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {
                    PrinterHelper.this.retryCount = 0;
                    PrinterHelper.this.sMacAddr = ((DevicesData) this.val$arrData.get(which)).getAddress();
                    if (!PrinterHelper.this.sMacAddr.contains(":") && PrinterHelper.this.sMacAddr.length() == 12) {
                        char[] cAddr = new char[17];
                        int j = 0;
                        for (int i = 0; i < 12; i += 2) {
                            PrinterHelper.this.sMacAddr.getChars(i, i + 2, cAddr, j);
                            j += 2;
                            if (j < 17) {
                                int j2 = j + 1;
                                cAddr[j] = ':';
                                j = j2;
                            }
                        }
                        PrinterHelper.this.sMacAddr = new String(cAddr);
                    }
                    PrinterHelper.this.showProgressDialog();
                    PrinterHelper.this.doConnectionTest(PrinterHelper.this.sMacAddr);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Crashlytics.logException(e);
                }
                Toast.makeText(context, " you have selected " +
                        ((DevicesData) this.val$arrData.get(which)).getName(), Toast.LENGTH_SHORT).show();
            }
        }

        /* renamed from: com.phonegap.sfa.DotmatHelper.4.2 */
        class C04682 implements OnClickListener {
            C04682() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PrinterHelper.this.sendUpdate(PrinterHelper.this.status, true);
            }
        }

        C04694(String[] strArr, ArrayList arrayList) {
            this.val$arrDevices = strArr;
            this.val$arrData = arrayList;
        }

        public void run() {
            Builder dialog = new Builder(context);
            dialog.setTitle("Choose Device To Pair");
            dialog.setItems(this.val$arrDevices, new C04671(this.val$arrData));
            dialog.setPositiveButton("Cancel", new C04682());
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    class C04705 implements Runnable {
        private final /* synthetic */ String val$address;

        C04705(String str) {
            this.val$address = str;
        }

        public void run() {
            new ConnectTo().execute(new String[]{this.val$address});
        }
    }

    public class ConnectTo extends AsyncTask<String, Void, Boolean> {
        @SuppressLint({"NewApi"})
        protected Boolean doInBackground(String... address) {
            try {
                BluetoothDevice device = PrinterHelper.this.mBtAdapter.getRemoteDevice(address[0]);
                if (PrinterHelper.this.btSocket != null && PrinterHelper.this.btSocket.isConnected()) {
                    PrinterHelper.this.btSocket.close();
                    PrinterHelper.this.btSocket = null;
                }
                if (VERSION.SDK_INT < 19) {
                    Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{Integer.TYPE});
                    PrinterHelper.this.btSocket = (BluetoothSocket) m.invoke(device, new Object[]{Integer.valueOf(1)});
                } else {
                    PrinterHelper.this.btSocket = device.createInsecureRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                }
                PrinterHelper.this.devicename = device.getName();
                try {
                    PrinterHelper.this.btSocket.connect();
                    PrinterHelper.this.outStream = PrinterHelper.this.btSocket.getOutputStream();
                    return Boolean.valueOf(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    return Boolean.valueOf(false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                // Crashlytics.logException(e2);
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result.booleanValue()) {
                PrinterHelper.this.dismissProgress();
                Log.e("Connected", "true");
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            PrinterHelper.this.printReports(PrinterHelper.this.sMacAddr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                return;
            } else {
                PrinterHelper.this.dismissProgress();
                try {
                    PrinterHelper.this.outStream = new ByteArrayOutputStream();
                    PrinterHelper.this.printReports("000");
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Crashlytics.logException(e);
                }
            }
            try {
                PrinterHelper.this.doConnectionTest(PrinterHelper.this.sMacAddr);
            } catch (JSONException e) {
                e.printStackTrace();
                // Crashlytics.logException(e);
            }
        }

        protected void onPreExecute() {
        }
    }

    @SuppressLint({"NewApi"})
    void printReports(String address) throws JSONException {
        Log.e("Print Report", "" + this.jArr.toString());
        Log.e("Array Length", "" + this.jArr.length());

        try {
//            for (int j = 0; j < (this.jArr.length() > 1 ? 1 : this.jArr.length()); j++) {
//                JSONArray jInner = this.jArr.getJSONArray(j);
//                for (int i = 0; i < jInner.length(); i++) {
//                    JSONObject jDict = jInner.getJSONObject(i);
//                    String request = jDict.getString(App.REQUEST);
//                    JSONObject jsnData = jDict.getJSONObject("mainArr");
//                    this.count = 0;
//                    if (request.equals(App.SALES_INVOICE)) {
            printSalesInvoiceNew(jArr, address);
//                    }
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.outStream != null) {
            try {
                Log.e("FullPrint", "" + this.outStream);
                this.outStream.flush();
                this.outStream.close();
            } catch (IOException e2222) {
                e2222.printStackTrace();
            }
        }
        if (this.btSocket != null && this.btSocket.isConnected()) {
            try {
                this.btSocket.close();
            } catch (IOException e22222) {
                e22222.printStackTrace();
            }
        }
    }

    private class asyncgetDevices extends AsyncTask<Void, Set<BluetoothDevice>, Set<BluetoothDevice>> {
        private asyncgetDevices() {
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        protected Set<BluetoothDevice> doInBackground(Void... params) {
            PrinterHelper.this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            PrinterHelper.this.mBtAdapter.enable();
            if (PrinterHelper.this.mBtAdapter.isEnabled()) {
                return PrinterHelper.this.mBtAdapter.getBondedDevices();
            }
            cancel(true);

            new asyncgetDevices().execute(new Void[0]);
            return null;
        }

        protected void onPostExecute(Set<BluetoothDevice> pairedDevices) {
            super.onPostExecute(pairedDevices);
            try {
                if (pairedDevices == null) {
                    return;
                }
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        Log.e("devices", device.getName() + StringUtilities.LF + device.getAddress());
                        System.out.println("devices" + device.getName() + StringUtilities.LF + device.getAddress());
                        DevicesData d1 = new DevicesData();
                        d1.setAddress(device.getAddress());
                        d1.setName(device.getName());
                        PrinterHelper.this.arrData.add(d1);
                    }
                    PrinterHelper.this.showDialog(PrinterHelper.this.arrData);
                    return;
                }
                Toast.makeText(context, "No Devices Found!", Toast.LENGTH_SHORT).show();
                System.out.println("No devices");
                Log.e("devices", "No devices");

                /*if (context instanceof PromotioninfoActivity) {
                    ((PromotioninfoActivity) context).callback(App.SALES_INVOICE);
                }*/

                try {
                    PrinterHelper.this.status.put("status", false);
                    PrinterHelper.this.status.put("isconnected", -7);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PrinterHelper.this.sendUpdate(PrinterHelper.this.status, true);


            } catch (Exception e) {
                e.printStackTrace();
                // Crashlytics.logException(e);
            }
        }
    }

    static {
        MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    }

    public void print() {

        /*AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Print Option");
        dialog.setPositiveButton("Arebic", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {*/
        new asyncgetDevices().execute(new Void[0]);
        /*    }
        });
        dialog.setNegativeButton("English", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new asyncgetDevices().execute(new Void[0]);
            }
        });
        dialog.show();*/

    }

    public void showDialog(ArrayList<DevicesData> arrData) {
        String[] arrDevices = new String[arrData.size()];
        for (int i = 0; i < arrData.size(); i++) {
            arrDevices[i] = new StringBuilder(String.valueOf(((DevicesData) arrData.get(i)).getName())).append(StringUtilities.LF).append(((DevicesData) arrData.get(i)).getAddress()).toString();
        }
        activity.runOnUiThread(new C04694(arrDevices, arrData));
    }

    private void sendUpdate(JSONObject obj, boolean keepCallback) {
        if (this.callbackId != null) {
            Log.e("End of plugin", "true");
            // success(new PluginResult(Status.OK, obj), this.callbackId);
        }
    }

    public void onDestroy() {
        onDestroy();
        try {
            if (this.btSocket != null) {
                this.btSocket.close();
            }
            if (this.outStream != null) {
                this.outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doConnectionTest(String address) throws JSONException {
        try {
            this.retryCount++;
            if (this.retryCount < 3) {
                Thread.sleep(200);
                activity.runOnUiThread(new C04705(address));
                return;
            }
            dismissProgress();
            try {
                this.status.put("status", false);
                sendUpdate(this.status, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            // Crashlytics.logException(e2);
        }
    }

    private void showProgressDialog() {
        try {
            this.progressDialog = ProgressDialog.show(context, "Please Wait", "Connecting to printer..", false);
        } catch (Exception e) {
            e.printStackTrace();
            // Crashlytics.logException(e);
        }
    }

    private void dismissProgress() {
        try {
            if (this.progressDialog != null && this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Crashlytics.logException(e);
        }

    }

    private String printSepratorcomp() {
        String seprator = "";
        for (int i = 0; i < 137; i++) {
            seprator = new StringBuilder(String.valueOf(seprator)).append("-").toString();
        }
        return seprator;
    }

    private String printSeprator() {
        String seprator = "";
        for (int i = 0; i < 80; i++) {
            seprator = new StringBuilder(String.valueOf(seprator)).append("-").toString();
        }
        return seprator;
    }

    private String printSepratorCompress() {
        String seprator = "";
        for (int i = 0; i < 137; i++) {
            seprator = new StringBuilder(String.valueOf(seprator)).append("-").toString();
        }
        return seprator;
    }

    private void printArabic(String data) {
        try {
            if (data.indexOf("@") == -1 || data.indexOf("!") == -1) {
                this.outStream.write(data.getBytes());
                return;
            }
            String start = data.substring(0, data.indexOf("@")).replaceAll("VATATVAT", "@");
            String middle = data.substring(data.indexOf("@") + 1, data.indexOf("!")).replaceAll("VATATVAT", "@");
            String end = data.substring(data.indexOf("!") + 1, data.length());
//            Log.e("start", start);
//            Log.e("middle", middle);
//            Log.e("end", end);
            Arabic6822 Arabic = new Arabic6822();
            byte[] printbyte = Arabic.Convert(middle, true);
            this.outStream.write(start.getBytes());
            this.outStream.write(printbyte);
            this.outStream.write("  ".getBytes());
            if (end.indexOf("@") == -1 || end.indexOf("!") == -1) {
                this.outStream.write(end.getBytes());
                return;
            }
            String startbet = end.substring(0, end.indexOf("@"));
            String middlebet = end.substring(end.indexOf("@") + 1, end.indexOf("!"));
            String endbet = end.substring(end.indexOf("!") + 1, end.length());
            byte[] printmidbyte = Arabic.Convert(middlebet, true);
            this.outStream.write(startbet.getBytes());
            this.outStream.write(printmidbyte);
            this.outStream.write("  ".getBytes());
            this.outStream.write(endbet.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            // Crashlytics.logException(e);
        }
    }

    private void printheaders(String line, boolean isArabic, int pluscount) {
        Log.e("PrintLine", "" + line);
        if (isArabic) {
            printArabic(line);
        } else {
            try {
                this.outStream.write(line.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.count += pluscount;
    }

    private void line(int ln) {
        for (int i = 0; i < ln; i++) {
            try {
                this.outStream.write(this.NewLine);
            } catch (IOException e) {
                e.printStackTrace();
                // Crashlytics.logException(e);
            }
        }
    }

    private String getAccurateText(String text, int width, int position) {
        String finalText = "";
        if (text.length() == width) {
//            Log.d("Matched String", text);
            return text;
        } else if (text.length() > width) {
            return text.substring(0, width);
        } else {
            finalText = text;
            int i;
            switch (position) {
                case 0:
                    for (i = 0; i < width - text.length(); i++) {
                        finalText = finalText.concat(" ");
                    }
                    return finalText;
                case 1:
                    for (i = 0; i < width - text.length(); i++) {
                        if (i < (width - text.length()) / 2) {
                            finalText = " " + finalText;
                        } else {
                            finalText = new StringBuilder(String.valueOf(finalText)).append(" ").toString();
                        }
                    }
                    return finalText;
                case 2:
                    for (i = 0; i < width - text.length(); i++) {
                        finalText = " " + finalText;
                    }
                    return finalText;
                default:
                    return finalText;
            }
        }
    }

    private void headervanstockprint(JSONObject object, int type) throws JSONException {
        try {
            this.outStream.write(this.BoldOn);
            printheaders(getAccurateText("ROUTE: " + object.getString("ROUTE"), 40, 0) + getAccurateText("DATE:" + object.getString("DOC DATE") + " " + object.getString("TIME") + " ", 40, 2), false, 1);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            printheaders(getAccurateText("SALESMAN: " + object.getString("SALESMAN") + "", 40, 0) + getAccurateText("SALESMAN NO: " + object.getString("SALESMANNO"), 40, 2), true, 1);
            this.outStream.write(this.NewLine);
            printheaders(getAccurateText("TRIP ID:" + object.getString("TourID"), 80, 0), false, 2);
            this.outStream.write(this.BoldOff);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.count++;
            if (type == 104) {
                this.outStream.write(this.BoldOn);
                this.outStream.write(this.DoubleWideOn);
                printheaders(getAccurateText("VAN STOCK SUMMARY ", 40, 1), false, 1);
                this.outStream.write(this.DoubleWideOff);
                this.outStream.write(this.BoldOff);
            } else if (type == 10) {
                this.outStream.write(this.BoldOn);
                this.outStream.write(this.DoubleWideOn);
                printheaders(getAccurateText("ITEM SALES SUMMARY ", 40, 1), false, 1);
                this.outStream.write(this.DoubleWideOff);
                this.outStream.write(this.BoldOff);
            } else if (type == 6) {
                this.outStream.write(this.BoldOn);
                this.outStream.write(this.DoubleWideOn);
                printheaders(getAccurateText("COMPANY CREDIT SUMMARY", 40, 1), false, 1);
                this.outStream.write(this.DoubleWideOff);
                this.outStream.write(this.BoldOff);
            } else if (type == 25) {
                this.outStream.write(this.BoldOn);
                this.outStream.write(this.DoubleWideOn);
                printheaders(getAccurateText("TEMPORARY CREDIT SUMMARY", 40, 1), false, 1);
                this.outStream.write(this.DoubleWideOff);
                this.outStream.write(this.BoldOff);
            }
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
        } catch (Exception e) {
            e.printStackTrace();
            // Crashlytics.logException(e);
        }
    }

    //Sales Invoice
    void printSalesInvoice(JSONObject object, String args) {

        StringBuffer s1 = new StringBuffer();

        try {
            int printoultlet;
            JSONArray jCheques;
            JSONObject jCash;
            JSONObject jChequeDetails;
            String copyStatus;
            JSONObject jSONObject;
            int i;
            StringBuilder stringBuilder;
            /*if (object.getString("printoutletitemcode").length() > 0) {
                printoultlet = Integer.parseInt(object.getString("printoutletitemcode"));
            } else {
                printoultlet = 0;
            }*/
//            this.hashValues = new HashMap();
//            this.hashValues.put("نعم لا", Integer.valueOf(6));
//            this.hashValues.put("رمز الصنف", Integer.valueOf(15));
//            this.hashValues.put("وصف", Integer.valueOf(32));
//            this.hashValues.put("UOM", Integer.valueOf(9));
//            this.hashValues.put("QTY", Integer.valueOf(11));
//            this.hashValues.put("سعر الوحدة", Integer.valueOf(12));
//            this.hashValues.put("المبلغ الإجمالي", Integer.valueOf(12));
//            this.hashValues.put("إجمالي القرص", Integer.valueOf(12));
//            this.hashValues.put("مقاطعة فات", Integer.valueOf(7));
//            this.hashValues.put("Vat %", Integer.valueOf(7));
//            this.hashValues.put("كمية SAR", Integer.valueOf(13));
//
//            this.hashPositions = new HashMap();
//            this.hashPositions.put("نعم لا", Integer.valueOf(0));
//            this.hashPositions.put("رمز الصنف", Integer.valueOf(0));
//            this.hashPositions.put("وصف", Integer.valueOf(0));
//            this.hashPositions.put("UOM", Integer.valueOf(1));
//            this.hashPositions.put("QTY", Integer.valueOf(1));
//            this.hashPositions.put("سعر الوحدة", Integer.valueOf(1));
//            this.hashPositions.put("المبلغ الإجمالي", Integer.valueOf(1));
//            this.hashPositions.put("إجمالي القرص", Integer.valueOf(1));
//            this.hashPositions.put("مقاطعة فات", Integer.valueOf(1));
//            this.hashPositions.put("Vat %", Integer.valueOf(1));
//            this.hashPositions.put("كمية SAR", Integer.valueOf(1));


            this.hashValues = new HashMap();
            this.hashValues.put("SI No", Integer.valueOf(6));
            this.hashValues.put("Item Code", Integer.valueOf(15));
            this.hashValues.put("Description", Integer.valueOf(32));
            this.hashValues.put("UOM", Integer.valueOf(9));
            this.hashValues.put("QTY", Integer.valueOf(11));
            this.hashValues.put("UNIT Price", Integer.valueOf(12));
            this.hashValues.put("Total amount", Integer.valueOf(12));
            this.hashValues.put("Total Disc", Integer.valueOf(12));
            this.hashValues.put("Vat Amt", Integer.valueOf(7));
            this.hashValues.put("Vat %", Integer.valueOf(7));
            this.hashValues.put("Amount SAR", Integer.valueOf(13));

            this.hashPositions = new HashMap();
            this.hashPositions.put("SI No", Integer.valueOf(0));
            this.hashPositions.put("Item Code", Integer.valueOf(0));
            this.hashPositions.put("Description", Integer.valueOf(0));
            this.hashPositions.put("UOM", Integer.valueOf(1));
            this.hashPositions.put("QTY", Integer.valueOf(1));
            this.hashPositions.put("UNIT Price", Integer.valueOf(1));
            this.hashPositions.put("Total amount", Integer.valueOf(1));
            this.hashPositions.put("Total Disc", Integer.valueOf(1));
            this.hashPositions.put("Vat Amt", Integer.valueOf(1));
            this.hashPositions.put("Vat %", Integer.valueOf(1));
            this.hashPositions.put("Amount SAR", Integer.valueOf(1));


            this.outStream.write(this.wakeUp);
            line(this.startln);

            headerprint(object, 9);
//            this.outStream.write(this.BoldOn);
//            this.outStream.write(this.DoubleWideOn);
//            printheaders(getAccurateText("SALES INVOICE", 40, 1), true, 2);
//            this.outStream.write(this.BoldOff);
//            this.outStream.write(this.DoubleWideOff);
//            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);

            isFirstInvoice = true;

            JSONArray headers = object.getJSONArray("HEADERS");
            String strheader = "";
            String strHeaderBottom = "";
            String strTotal = "";

            int MAXLEngth = 137;
            for (i = 0; i < headers.length(); i++) {
                MAXLEngth -= ((Integer) this.hashValues.get(headers.getString(i).toString())).intValue();
            }
            if (MAXLEngth > 0) {
                MAXLEngth /= headers.length();
            }

            for (i = 0; i < headers.length(); i++) {
                try {
                    String string;
                    stringBuilder = new StringBuilder(String.valueOf(strheader));
                    if (headers.getString(i).indexOf(" ") == -1) {
                        string = headers.getString(i);
                    } else {
                        string = headers.getString(i).substring(0, headers.getString(i).indexOf(" "));
                    }
                    strheader = stringBuilder.append(getAccurateText(string,
                            ((Integer) this.hashValues.get(headers.getString(i).toString())).intValue() +
                                    MAXLEngth,
                            ((Integer) this.hashPositions.get(headers.getString(i).toString())).intValue())).toString();

                    stringBuilder = new StringBuilder(String.valueOf(strHeaderBottom));
                    if (headers.getString(i).indexOf(" ") == -1) {
                        string = "";
                    } else {
                        string = headers.getString(i).substring(headers.getString(i).indexOf(" "), headers.getString(i).length());
                    }
                    strHeaderBottom = stringBuilder.append(getAccurateText(string,
                            ((Integer) this.hashValues.get(headers.getString(i).toString())).intValue() + MAXLEngth,
                            ((Integer) this.hashPositions.get(headers.getString(i).toString())).intValue())).toString();

                } catch (Exception e) {
                }
            }
            this.outStream.write(this.CompressOn);

            printlines1(strheader, 1, object, 1, args, 9);
            printlines1(strHeaderBottom, 1, object, 1, args, 9);
            printlines1(printSepratorcomp(), 1, object, 1, args, 9);

            this.outStream.write(this.CompressOff);

            JSONArray jData = object.getJSONArray(JsonRpcUtil.PARAM_DATA);
            for (i = 0; i < jData.length(); i++) {
                JSONArray jArr = jData.getJSONArray(i);
                String strData = "";
                for (int j = 0; j < jArr.length(); j++) {
                    int i2;
                    Object obj;
                    String itemDescrion = jArr.getString(j);

                    stringBuilder = new StringBuilder(String.valueOf(strData));

                    i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;

                    HashMap hashMap = this.hashPositions;

                    obj = headers.getString(j).toString();

                    strData = stringBuilder.append(getAccurateText(itemDescrion, i2, ((Integer) hashMap.get(obj)).intValue())).toString();
                }
                this.outStream.write(this.CompressOn);
                this.count++;
                printlines1(strData.replaceAll("%20", " ").replaceAll("%26", "&").replaceAll("%2C", ",").replaceAll("%28", "(").replaceAll("%29", ")").replaceAll("%2F", "/").replaceAll("%5C", "\\").replaceAll("%5C", "\\"), 1, object, 1, args, 9);
                this.outStream.write(this.CompressOff);
            }

            this.outStream.write(this.BoldOn);
            this.outStream.write(this.DoubleWideOn);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            //printheaders(object.getString("invheadermsg"), false, 3);

//            JSONArray focData = object.getJSONArray("foc");
//            if (focData.length() > 0) {
//                isFirstInvoice = false;
//                printheaders(getAccurateText("FREE GOODS", 40, 1), false, 2);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//
//                this.outStream.write(this.CompressOn);
//                printlines1(strheader, 1, object, 1, args, 9);
//                printlines1(strHeaderBottom, 1, object, 1, args, 9);
//                printlines1(printSepratorcomp(), 1, object, 1, args, 9);
//                this.outStream.write(this.CompressOff);
//
//                for (i = 0; i < focData.length(); i++) {
//                    JSONArray jArr = focData.getJSONArray(i);
//                    String strData = "";
//                    for (int j = 0; j < jArr.length(); j++) {
//                        int i2;
//                        Object obj;
//                        String itemDescrion = jArr.getString(j);
//
//                        stringBuilder = new StringBuilder(String.valueOf(strData));
//                        i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
//
//                        HashMap hashMap = this.hashPositions;
//                        obj = headers.getString(j).toString();
//
//                        strData = stringBuilder.append(getAccurateText(itemDescrion, i2, ((Integer) hashMap.get(obj)).intValue())).toString();
//                    }
//                    this.outStream.write(this.CompressOn);
//                    this.count++;
//                    printlines1(strData, 1, object, 1, args, 9);
//                    this.outStream.write(this.CompressOff);
//                }
//            }


            //Gagdadfdsafsd
            this.outStream.write(this.CompressOn);
            printlines1(printSepratorcomp(), 1, object, 2, args, 9);

            String strTotalNoVat = "";
            String strDiscount = "";
            String strTotalVat = "";
            String strTotalWithVAT = "";
            JSONArray jTotal = object.getJSONArray("TOTAL");
            JSONObject jTOBject = jTotal.getJSONObject(0);

            if (jTOBject.has("Total Amount(AED)")) {
                String Amount = jTOBject.getString("Total Amount(AED)");
                String AmountBeforeTax = jTOBject.getString("Total Befor TAX(AED)");

                int decimals = Amount.indexOf(".");
                int decimalsBT = AmountBeforeTax.indexOf(".");

                String decimalValue = null;

                //GROSS AMOUNT
                //FOR BEFORE TAX
                if (0 <= decimalsBT) {
                    decimalValue = AmountBeforeTax.substring(decimalsBT + 1);
                    AmountBeforeTax = AmountBeforeTax.substring(0, decimalsBT).replaceAll("-", "");

                    String strWord = "";
                    try {
                        Long data = Long.parseLong(AmountBeforeTax);
                        Long dataDecimal = Long.parseLong(decimalValue);
                        strWord = NumberToWord.convert(data) + " and " + NumberToWord.convert(dataDecimal) + " fills";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    printlines1(getAccurateText("GROSS AMOUNT: AED - " + strWord.toUpperCase(), 130, 0), 1, object, 2, args, 9);
//                    printlines1(getAccurateText("AED - ANOTHER" + strWord.toUpperCase(), 130, 0), 1, object, 1, args, 9);
                } else {

                    String strWord = "";
                    try {
                        Long data = Long.parseLong(AmountBeforeTax);
                        strWord = NumberToWord.convert(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    printlines1(getAccurateText("GROSS AMOUNT: AED - " + strWord.toUpperCase(), 130, 0), 1, object, 2, args, 9);
//                    printlines1(getAccurateText("AED - ANOTHER" + strWord.toUpperCase(), 130, 0), 1, object, 1, args, 9);
                }

                //TOTAL AMOUNT
                if (0 <= decimals) {
                    decimalValue = Amount.substring(decimals + 1);
                    Amount = Amount.substring(0, decimals).replaceAll("-", "");

                    String strWord = "";
                    try {
                        Long data = Long.parseLong(Amount);
                        Long dataDecimal = Long.parseLong(decimalValue);
                        strWord = NumberToWord.convert(data) + " and " + NumberToWord.convert(dataDecimal) + " fills";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    printlines1(getAccurateText("TOTAL AMOUNT: AED - " + strWord.toUpperCase(), 130, 0), 1, object, 2, args, 9);
//                    printlines1(getAccurateText("AED - ANOTHER" + strWord.toUpperCase(), 130, 0), 1, object, 1, args, 9);
                } else {

                    String strWord = "";
                    try {
                        Long data = Long.parseLong(Amount);
                        strWord = NumberToWord.convert(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    printlines1(getAccurateText("TOTAL AMOUNT: AED - " + strWord.toUpperCase(), 130, 0), 1, object, 2, args, 9);
//                    printlines1(getAccurateText("AED - ANOTHER" + strWord.toUpperCase(), 130, 0), 1, object, 1, args, 9);
                }


            }

            this.outStream.write(this.CompressOn);

            if (jTOBject.has("Total Befor TAX(AED)")) {
                strTotalNoVat = getAccurateText("Total Before TAX(AED) : " + jTOBject.getString("Total Befor TAX(AED)"),
                        137 + MAXLEngth, 2);
                printlines1(strTotalNoVat, 1, object, 2, args, 9);
            }

            if (jTOBject.has("INVOICE DISCOUNT(AED)")) {
                strDiscount = getAccurateText("INVOICE DISCOUNT(AED) : " + jTOBject.getString("INVOICE DISCOUNT(AED)"), 137 + MAXLEngth, 2);
                printlines1(strDiscount, 1, object, 2, args, 9);
            }
            if (jTOBject.has("Net Amount(AED)")) {
                strDiscount = getAccurateText("Net Amount(AED) : " + jTOBject.getString("Net Amount(AED)"), 137 + MAXLEngth, 2);
                printlines1(strDiscount, 1, object, 2, args, 9);
            }
            if (jTOBject.has("VAT(AED)")) {
                strTotalVat = getAccurateText("VAT(AED) : " + jTOBject.getString("VAT(AED)"), 137 + MAXLEngth, 2);
                printlines1(strTotalVat, 1, object, 2, args, 9);
            }
            if (jTOBject.has("Total Amount(AED)")) {
                strTotalWithVAT = getAccurateText("Total Amount(AED) : " + jTOBject.getString("Total Amount(AED)"),
                        137 + MAXLEngth, 2);
                printlines1(strTotalWithVAT, 1, object, 2, args, 9);
            }

            this.outStream.write(this.CompressOff);
//            printlines1(getAccurateText("", 80, 1), 2, object, 1, args, 9);
//            this.outStream.write(this.BoldOn);
            jSONObject = object;

//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.BoldOff);
//            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);

//            printlines1(getAccurateText("_____________", 40, 1) + getAccurateText("____________", 40, 1), 2, object, 1, args, 5);
//            printlines1(getAccurateText("CUSTOMER", 40, 1) + getAccurateText("SALESMAN", 40, 1), 2, object, 1, args, 5);
//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.NewLine);
            this.outStream.write(this.CompressOn);

            printlines1(getAccurateText("Payment to be pain to our RAK Bank Account",
                    137 + MAXLEngth, 2)
                    , 1, object, 2, args, 9);

            printlines1(getAccurateText("Account Title: Balade Farm Food Industries LLC",
                    137 + MAXLEngth, 2)
                    , 1, object, 2, args, 9);

            printlines1(getAccurateText("Back Name: RAK Bank",
                    137 + MAXLEngth, 2)
                    , 1, object, 2, args, 9);

            printlines1(getAccurateText("IBAN: AES451221646553131",
                    137 + MAXLEngth, 2)
                    , 1, object, 2, args, 9);

//            printlines1(getAccurateText(object.getString("invoicefooter"),
//                    80, 1), 1, object, 1, args, 9);
            // printlines1(getAccurateText("For any complaints please contact " + object.getString("supervisorname") + "on" + object.getString("supervisorno"), 40, 0), false, 1);

            //jSONObject = object;

            this.outStream.write(this.CompressOff);

            this.outStream.write(this.NewLine);
            this.outStream.write(this.BoldOff);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
//            if (context instanceof PromotioninfoActivity) {
//                //closeConnection();
//
//                ((PromotioninfoActivity) context).callback(App.SALES_INVOICE);
//            }

            Log.v("", "================= PRINT LAYOUTS =================");
            Log.v("", this.outStream.toString());

//            TextView txtLine = new TextView(PrintTestActivity.this);
//            txtLine.setText(new String(this.outStream.toString()));
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    // Stuff that updates the UI
//
//                    ll_main.addView(txtLine);
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printlines1(String data, int ln,
                             JSONObject object,
                             int sts, String adr, int tp) throws JSONException, IOException, LinePrinterException {
        int i;
        this.count += ln;
        boolean isEnd = false;
        if (sts == 2 && this.count != 0) {
            Log.e("Going for Arabic1", "Arabic" + data);
            printArabic(data);
            isEnd = true;
            int lnno = (48 - this.count) + this.endln;
            for (i = 0; i < lnno; i++) {
                try {
                    if (i % 10 == 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.outStream.write(this.NewLine);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            this.outStream.write(this.CarriageReturn);
            this.count = 0;
            try {
                Thread.sleep(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            }
            this.status.put("status", true);
            this.status.put("isconnected", 0);
            sendUpdate(this.status, true);
        }
        if (!isEnd) {
            printArabic(data);
            Log.e("Going for Arabic", "Arabic" + data);
            for (i = 0; i < ln; i++) {
                try {
                    this.outStream.write(this.NewLine);
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            }
            if (this.count % 10 == 0) {
                try {
                    Thread.sleep(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
                } catch (InterruptedException e32) {
                    e32.printStackTrace();
                }
            }
            /*if (this.count > 48) {
                Log.e("Count 1 time", "Count " + this.count);
                this.outStream.write(this.CompressOff);
                this.count = 0;
                try {
                    line(this.cnln);
                    if (tp == 4) {
                        headervanstockprint(object, tp);
                    } else if (tp == 6 || tp == 25) {
                        headervanstockprint(object, tp);
                    } else {
                        headerinvprint(object, tp);
                    }
                    this.outStream.write(printSeprator().getBytes());
                    this.count++;
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }*/
        }
    }


    private void printlines2(String data, int ln, JSONObject object, int sts, String adr, int tran, int tp) throws JSONException, IOException, LinePrinterException {
        int i;
        this.count += ln;
        boolean isEnd = false;
        if (sts == 2 && this.count != 0) {
            printArabic(data);
            //Log.e("salessummary count", this.count);
            //Log.e("salessummary pln", 48);
            isEnd = true;
            int lnno1 = (48 - this.count) + this.endln;
            for (i = 0; i < lnno1; i++) {
                try {
                    if (i % 10 == 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.outStream.write(this.NewLine);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            this.outStream.write(this.CarriageReturn);
            this.count = 0;
            try {
                Thread.sleep(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            }
//            this.status.put("status", true);
//            this.status.put("isconnected", 0);
            //           sendUpdate(this.status, true);
        }
        if (!isEnd) {
            printArabic(data);
            for (i = 0; i < ln; i++) {
                try {
                    this.outStream.write(this.NewLine);
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            }
            if (this.count % 10 == 0) {
                try {
                    Thread.sleep(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
                } catch (InterruptedException e32) {
                    e32.printStackTrace();
                }
            }
            Log.d("Count", "Count1" + this.count);
            if (this.count > 48) {
                this.count = 0;
                Log.d("Count", "Count1" + this.count);
                Log.d("Pln", "PLn" + 48);
                try {
                    line(this.cnln);
                    //this.outStream.write(this.CompressOff);

                    this.outStream.write(this.CompressOn);
                    if (tran == 2) {
                        headerprint(object, tp);
                    } else if (tran == 1) {
                        headerprint(object, tp);
                    } else if (tran == 5) {
                        headerprint(object, tp);
                    } else if (tran == 4) {
                        headerprint(object, tp);
                    } else if (tran == 3) {
                        headerprint(object, tp);
                    } else if (tran == 6) {
                        headerprint(object, tp);
                    } else if (tran == 7) {
                        headerprint(object, tp);
                    }

                    this.outStream.write(this.CompressOff);
                    this.outStream.write(printSeprator().getBytes());
                    this.count++;
                    if (tran == 5 || tran == 4) {
                        //this.outStream.write(this.CompressOn);
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        }
    }


    void printSalesInvoiceNew(JSONObject object, String args) {
        StringBuffer s1 = new StringBuffer();
        try {
            int printoultlet;
            JSONArray jCheques;
            JSONObject jCash;
            JSONObject jChequeDetails;
            String copyStatus;
            JSONObject jSONObject;
            int i;
            StringBuilder stringBuilder;
            /*if (object.getString("printoutletitemcode").length() > 0) {
                printoultlet = Integer.parseInt(object.getString("printoutletitemcode"));
            } else {
                printoultlet = 0;
            }*/
//            if (object.getString("displayupc").equals("1")) {
//
//                this.hashValues = new HashMap();
//                this.hashValues.put("ITEM NO", Integer.valueOf(10));
//                this.hashValues.put("DESCRIPTION", Integer.valueOf(80));
////                this.hashValues.put("ARABIC DESCRIPTION", Integer.valueOf(40));
//                this.hashValues.put("UPC ", Integer.valueOf(7));
//                this.hashValues.put("TOTAL UNITS", Integer.valueOf(12));
//                this.hashValues.put("UNIT PRICE", Integer.valueOf(12));
//                this.hashValues.put("AMOUNT", Integer.valueOf(12));
//                this.hashValues.put("REASON CODE", Integer.valueOf(11));
//                //this.hashValues.put("NET LOAD", Integer.valueOf(8));
//                //this.hashValues.put("VALUE", Integer.valueOf(8));
//                //this.hashValues.put("Description", Integer.valueOf(40));
//                this.hashPositions = new HashMap();
//                this.hashPositions.put("ITEM NO", Integer.valueOf(0));
//                // this.hashPositions.put("Item#", Integer.valueOf(0));
//                this.hashPositions.put("DESCRIPTION", Integer.valueOf(1));
////                this.hashPositions.put("ARABIC DESCRIPTION", Integer.valueOf(0));
//                this.hashPositions.put("UPC ", Integer.valueOf(2));
//                this.hashPositions.put("TOTAL UNITS", Integer.valueOf(2));
//                this.hashPositions.put("UNIT PRICE", Integer.valueOf(2));
//                this.hashPositions.put("AMOUNT", Integer.valueOf(2));
//                this.hashPositions.put("REASON CODE", Integer.valueOf(2));
//
//
//            } else {
//
//                this.hashValues = new HashMap();
//                this.hashValues.put("ITEM NO", Integer.valueOf(10));
//                this.hashValues.put("DESCRIPTION", Integer.valueOf(80));
////                this.hashValues.put("ARABIC DESCRIPTION", Integer.valueOf(40));
//                this.hashValues.put("UPC ", Integer.valueOf(7));
//                this.hashValues.put("TOTAL UNITS", Integer.valueOf(12));
//                this.hashValues.put("UNIT PRICE", Integer.valueOf(12));
//                this.hashValues.put("AMOUNT", Integer.valueOf(12));
//                this.hashValues.put("REASON CODE", Integer.valueOf(11));
//                //this.hashValues.put("NET LOAD", Integer.valueOf(8));
//                //this.hashValues.put("VALUE", Integer.valueOf(8));
//                //this.hashValues.put("Description", Integer.valueOf(40));
//                this.hashPositions = new HashMap();
//                this.hashPositions.put("ITEM NO", Integer.valueOf(0));
//                // this.hashPositions.put("Item#", Integer.valueOf(0));
//                this.hashPositions.put("DESCRIPTION", Integer.valueOf(1));
////                this.hashPositions.put("ARABIC DESCRIPTION", Integer.valueOf(0));
//                this.hashPositions.put("UPC ", Integer.valueOf(2));
//                this.hashPositions.put("TOTAL UNITS", Integer.valueOf(2));
//                this.hashPositions.put("UNIT PRICE", Integer.valueOf(2));
//                this.hashPositions.put("AMOUNT", Integer.valueOf(2));
//                this.hashPositions.put("REASON CODE", Integer.valueOf(2));
//            }


            this.hashValues = new HashMap();
            this.hashValues.put("SI No", Integer.valueOf(6));
            this.hashValues.put("Item Code", Integer.valueOf(15));
            this.hashValues.put("Description", Integer.valueOf(32));
            this.hashValues.put("UOM", Integer.valueOf(9));
            this.hashValues.put("QTY", Integer.valueOf(11));
            this.hashValues.put("UNIT Price", Integer.valueOf(12));
            this.hashValues.put("Total amount", Integer.valueOf(12));
            this.hashValues.put("Total Disc", Integer.valueOf(12));
            this.hashValues.put("Vat Amt", Integer.valueOf(7));
            this.hashValues.put("Vat %", Integer.valueOf(7));
            this.hashValues.put("Amount SAR", Integer.valueOf(13));

            this.hashPositions = new HashMap();
            this.hashPositions.put("SI No", Integer.valueOf(0));
            this.hashPositions.put("Item Code", Integer.valueOf(0));
            this.hashPositions.put("Description", Integer.valueOf(0));
            this.hashPositions.put("UOM", Integer.valueOf(1));
            this.hashPositions.put("QTY", Integer.valueOf(1));
            this.hashPositions.put("UNIT Price", Integer.valueOf(1));
            this.hashPositions.put("Total amount", Integer.valueOf(1));
            this.hashPositions.put("Total Disc", Integer.valueOf(1));
            this.hashPositions.put("Vat Amt", Integer.valueOf(1));
            this.hashPositions.put("Vat %", Integer.valueOf(1));
            this.hashPositions.put("Amount SAR", Integer.valueOf(1));


            this.outStream.write(this.wakeUp);
            line(this.startln);
            this.outStream.write(this.CompressOn);
            headerprint(object, 9);


            //this.outStream.write(this.BoldOn);
            //this.outStream.write(this.DoubleWideOn);
            //printheaders(object.getString("invheadermsg"), false, 3);
            printheaders(getAccurateText("SALES INVOICE" + " " + ArabicLabels.salesinvoice,
                    100, 1), true, 2);
            //this.outStream.write(this.BoldOff);
            //this.outStream.write(this.DoubleWideOff);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            //this.outStream.write(this.NewLine);
            //this.outStream.write(this.NewLine);
            //this.outStream.write(this.NewLine);

            JSONArray headers = object.getJSONArray("HEADERS");
            String strheader = "";
            String strHeaderBottom = "";
            String strTotal = "";

            JSONArray jTotal = object.getJSONArray("TOTAL");
            int MAXLEngth = 137;
            for (i = 0; i < headers.length(); i++) {
                MAXLEngth -= ((Integer) this.hashValues.get(headers.getString(i).toString())).intValue();
            }
            if (MAXLEngth > 0) {
                MAXLEngth /= headers.length();
            }
            JSONObject jTOBject = jTotal.getJSONObject(0);
            for (i = 0; i < headers.length(); i++) {
                try {
                    String string;
                    stringBuilder = new StringBuilder(String.valueOf(strheader));
                    string = headers.getString(i);
                    strheader = stringBuilder.append(getAccurateText(string,
                            ((Integer) this.hashValues.get(headers.getString(i).toString())).intValue() +
                                    MAXLEngth, ((Integer) this.hashPositions.get(headers.getString(i).toString())).intValue())).toString();

                    stringBuilder = new StringBuilder(String.valueOf(strHeaderBottom));
                    if (headers.getString(i).indexOf(" ") == -1) {
                        string = "";
                    } else {
                        string = headers.getString(i).substring(headers.getString(i).indexOf(" "), headers.getString(i).length());
                    }
                    //strHeaderBottom = stringBuilder.append(getAccurateText(string, ((Integer) this.hashValues.get(headers.getString(i).toString())).intValue() + MAXLEngth, ((Integer) this.hashPositions.get(headers.getString(i).toString())).intValue())).toString();
                    if (jTOBject.has(headers.getString(i))) {
                        strTotal = new StringBuilder(String.valueOf(strTotal)).append(getAccurateText(jTOBject.getString(headers.getString(i).toString()), ((Integer) this.hashValues.get(headers.getString(i).toString())).intValue() + MAXLEngth, ((Integer) this.hashPositions.get(headers.getString(i).toString())).intValue())).toString();
                    } else {
                        stringBuilder = new StringBuilder(String.valueOf(strTotal));
                        if (headers.getString(i).equals("DESCRIPTION")) {
                            string = ArabicLabels.total + " " + "TOTAL";
                        } else {
                            string = "";
                        }
                        strTotal = stringBuilder.append(getAccurateText(string, ((Integer) this.hashValues.get(headers.getString(i))).intValue() + MAXLEngth, 1)).toString();
                    }
                } catch (Exception e) {
                }
            }
            //this.outStream.write(this.CompressOn);


            String arabicheader = "@" + getAccurateText(ArabicLabels.amount + "" +
                    "     " + ArabicLabels.UnitPrice + "  " + ArabicLabels.TotalUnits + "                                          " + ArabicLabels.Description + "                                     " + ArabicLabels.ItemNo, 150, 0) + "!";
            printheaders(arabicheader, true, 1);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);

            printlines1(strheader, 1, object, 1, args, 1);
            // printlines1(strHeaderBottom, 1, object, 1, args, 5);
            printlines1(printSepratorcomp(), 1, object, 1, args, 1);
            //this.outStream.write(this.CompressOff);

            JSONArray jData = object.getJSONArray(JsonRpcUtil.PARAM_DATA);
            for (i = 0; i < jData.length(); i++) {
                JSONArray jArr = jData.getJSONArray(i);
                String strData = "";
                for (int j = 0; j < jArr.length(); j++) {
                    int i2;
                    Object obj;
                    String itemDescrion = jArr.getString(j);
                    if (j == 0) {
                        //itemDescrion = new StringBuilder(String.valueOf(i + 1)).toString();
                    } else if (j == 2) {
                        itemDescrion = "           @" + jArr.getString(j) + "!";
                    }
                    stringBuilder = new StringBuilder(String.valueOf(strData));
                    if (j == 2) {
                        //i2 = 60;
                        i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
                    } else {
                        i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
                    }
                    HashMap hashMap = this.hashPositions;
                    if (j == 7) {
                        obj = "Description";
                    } else {
                        obj = headers.getString(j).toString();
                    }
                    strData = stringBuilder.append(getAccurateText(itemDescrion, i2, ((Integer) hashMap.get(obj)).intValue())).toString();
                }
                //this.outStream.write(this.CompressOn);
                this.count++;
                printlines1(strData, 1, object, 1, args, 1);
                //this.outStream.write(this.CompressOff);
            }

            //this.outStream.write(this.BoldOn);
            //this.outStream.write(this.DoubleWideOn);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            //printheaders(object.getString("invheadermsg"), false, 3);

//            JSONArray focData = object.getJSONArray("foc");
//            if (focData.length() > 0) {
//                printheaders(getAccurateText("FREE GOODS", 40, 1), false, 2);
//                //this.outStream.write(this.BoldOff);
//                //this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//
//                //this.outStream.write(this.CompressOn);
//                printlines1(strheader, 1, object, 1, args, 1);
//                // printlines1(strHeaderBottom, 1, object, 1, args, 5);
//                printlines1(printSepratorcomp(), 1, object, 1, args, 1);
//                //this.outStream.write(this.CompressOff);
//
//                for (i = 0; i < focData.length(); i++) {
//                    JSONArray jArr = focData.getJSONArray(i);
//                    String strData = "";
//                    for (int j = 0; j < jArr.length(); j++) {
//                        int i2;
//                        Object obj;
//                        String itemDescrion = jArr.getString(j);
//                        if (j == 0) {
//                            //itemDescrion = new StringBuilder(String.valueOf(i + 1)).toString();
//                        } else if (j == 2) {
//                            itemDescrion = "           @" + jArr.getString(j) + "!";
//                        }
//                        stringBuilder = new StringBuilder(String.valueOf(strData));
//                        if (j == 2) {
//                            //i2 = 60;
//                            i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
//                        } else {
//                            i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
//                        }
//                        HashMap hashMap = this.hashPositions;
//                        if (j == 7) {
//                            obj = "Description";
//                        } else {
//                            obj = headers.getString(j).toString();
//                        }
//                        strData = stringBuilder.append(getAccurateText(itemDescrion, i2, ((Integer) hashMap.get(obj)).intValue())).toString();
//                    }
//                    //this.outStream.write(this.CompressOn);
//                    this.count++;
//                    printlines1(strData, 1, object, 1, args, 1);
//                    //this.outStream.write(this.CompressOff);
//                }
//            }

//            JSONArray grData = object.getJSONArray("gr");
//            if (grData.length() > 0) {
//                printheaders(getAccurateText("GOOD RETURN", 40, 1), false, 2);
//                //this.outStream.write(this.BoldOff);
//                //this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//
//                //this.outStream.write(this.CompressOn);
//                printlines1(strheader, 1, object, 1, args, 1);
//                // printlines1(strHeaderBottom, 1, object, 1, args, 5);
//                printlines1(printSepratorcomp(), 1, object, 1, args, 1);
//                //this.outStream.write(this.CompressOff);
//
//                for (i = 0; i < grData.length(); i++) {
//                    JSONArray jArr = grData.getJSONArray(i);
//                    String strData = "";
//                    for (int j = 0; j < jArr.length(); j++) {
//                        int i2;
//                        Object obj;
//                        String itemDescrion = jArr.getString(j);
//                        if (j == 0) {
//                            //itemDescrion = new StringBuilder(String.valueOf(i + 1)).toString();
//                        } else if (j == 2) {
//                            itemDescrion = "           @" + jArr.getString(j) + "!";
//                        }
//                        stringBuilder = new StringBuilder(String.valueOf(strData));
//                        if (j == 2) {
//                            //i2 = 60;
//                            i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
//                        } else {
//                            i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
//                        }
//                        HashMap hashMap = this.hashPositions;
//                        if (j == 7) {
//                            obj = "Description";
//                        } else {
//                            obj = headers.getString(j).toString();
//                        }
//                        strData = stringBuilder.append(getAccurateText(itemDescrion, i2, ((Integer) hashMap.get(obj)).intValue())).toString();
//                    }
//                    //this.outStream.write(this.CompressOn);
//                    this.count++;
//                    printlines1(strData, 1, object, 1, args, 1);
//                    //this.outStream.write(this.CompressOff);
//                }
//            }


            //this.outStream.write(this.BoldOn);
            //this.outStream.write(this.DoubleWideOn);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
//            JSONArray brData = object.getJSONArray("br");
//            if (brData.length() > 0) {
//                //printheaders(object.getString("invheadermsg"), false, 3);
//                printheaders(getAccurateText("BAD RETURN", 40, 1), false, 2);
//                //this.outStream.write(this.BoldOff);
//                //this.outStream.write(this.DoubleWideOff);
//                // this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//
//                //this.outStream.write(this.CompressOn);
//                printlines1(strheader, 1, object, 1, args, 1);
//                // printlines1(strHeaderBottom, 1, object, 1, args, 5);
//                printlines1(printSepratorcomp(), 1, object, 1, args, 1);
//                //this.outStream.write(this.CompressOff);
//
//                for (i = 0; i < brData.length(); i++) {
//                    JSONArray jArr = brData.getJSONArray(i);
//                    String strData = "";
//                    for (int j = 0; j < jArr.length(); j++) {
//                        int i2;
//                        Object obj;
//                        String itemDescrion = jArr.getString(j);
//                        if (j == 0) {
//                            //itemDescrion = new StringBuilder(String.valueOf(i + 1)).toString();
//                        } else if (j == 2) {
//                            itemDescrion = "           @" + jArr.getString(j) + "!";
//                        }
//                        stringBuilder = new StringBuilder(String.valueOf(strData));
//                        if (j == 2) {
//                            //i2 = 60;
//                            i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
//                        } else {
//                            i2 = ((Integer) this.hashValues.get(headers.getString(j).toString())).intValue() + MAXLEngth;
//                        }
//                        HashMap hashMap = this.hashPositions;
//                        if (j == 7) {
//                            obj = "Description";
//                        } else {
//                            obj = headers.getString(j).toString();
//                        }
//                        strData = stringBuilder.append(getAccurateText(itemDescrion, i2, ((Integer) hashMap.get(obj)).intValue())).toString();
//                    }
//                    //this.outStream.write(this.CompressOn);
//                    this.count++;
//                    printlines1(strData, 1, object, 1, args, 1);
//                    //this.outStream.write(this.CompressOff);
//                }
//            }


            //Gagdadfdsafsd
            //this.outStream.write(this.CompressOn);
            printlines1(printSepratorcomp(), 1, object, 1, args, 1);
            printlines1(strTotal, 1, object, 1, args, 1);
            //this.outStream.write(this.CompressOff);
            printlines1(getAccurateText("", 80, 1), 2, object, 1, args, 1);
            //this.outStream.write(this.BoldOn);
            jSONObject = object;
            if (object.getString("invoicepriceprint").equals("1")) {
                jSONObject = object;
                printlines2(getAccurateText("NET SALES", 20, 0) +
                                getAccurateText(" : ", 3, 0) +
                                getAccurateText(object.getString("SUB TOTAL") + " SAR", 12, 0) +
                                getAccurateText(" : ", 3, 0) + "@" +
                                getAccurateText(ArabicTEXT.NetSales, 15, 2) + "!",
                        1, jSONObject, 1, args, 1, 1);
                //this.outStream.write(this.BoldOff);
                //this.outStream.write(this.BoldOn);
                if (object.has("INVOICE DISCOUNT")) {
                    Log.e("I have discount", "Discount");
                    if (object.getString("INVOICE DISCOUNT").toString().length() > 0) {
                        Log.e("I have discount", "Discount1");
                        if (Double.parseDouble(object.getString("INVOICE DISCOUNT")) > 0.0d) {
                            jSONObject = object;
                            printlines2(getAccurateText("INVOICE DISCOUNT", 20, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(object.getString("INVOICE DISCOUNT") + " SAR", 12, 0) + getAccurateText(" : ", 3, 0) + "@" + getAccurateText(ArabicTEXT.InvoiceDiscount, 15, 2) + "!", 1, jSONObject, 1, args, 1, 1);
                        } else {
                            Log.e("I have discount", "Discount Else");
                            jSONObject = object;
                            printlines2(getAccurateText("INVOICE DISCOUNT", 20, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(object.getString("INVOICE DISCOUNT") + " SAR", 12, 0) + getAccurateText(" : ", 3, 0) + "@" + getAccurateText(ArabicTEXT.InvoiceDiscount, 15, 2) + "!", 1, jSONObject, 1, args, 1, 1);
                        }
                    }
                }
                //this.outStream.write(this.BoldOff);
                //this.outStream.write(this.BoldOn);
                if (Const.isDisplayVatTax) {
                    try {
                        printlines2(getAccurateText("VATVATATVAT5%", 27, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(object.getString("VAT") + " SAR", 12, 0) + getAccurateText(" : 5%", 5, 0) + "@" + getAccurateText(ArabicTEXT.Vat, 22, 2) + "!", 1, jSONObject, 1, args, 1, 1);
                        //this.outStream.write(this.BoldOff);
                        //this.outStream.write(this.BoldOn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                jSONObject = object;
                printlines2(getAccurateText("TOTAL", 20, 0) +
                                getAccurateText(" : ", 3, 0) + getAccurateText(
                        object.getString("NET SALES") + " SAR", 12, 0)
                                + getAccurateText(" : ", 3, 0) + "@" +
                                getAccurateText(ArabicTEXT.TOTAL, 15, 2) + "!",
                        1, jSONObject, 1, args, 1, 1);
                //this.outStream.write(this.BoldOff);
            }
            //printlines1(getAccurateText("Net Value : ", 50, 2) + getAccurateText(object.getString("netvalue"), 12, 2), 3, jSONObject, 1, args, 5);
            this.outStream.write(this.NewLine);
            //this.outStream.write(this.BoldOff);
            this.outStream.write(this.NewLine);

            printlines1(getAccurateText("_____________", 40, 1) + getAccurateText("____________", 80, 2), 2, object, 1, args, 5);
            printlines1(getAccurateText("CUSTOMER", 40, 1) + getAccurateText("SALESMAN", 80, 2), 2, object, 1, args, 5);
//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.NewLine);

            // chagnes
            try {
                printlines1(getAccurateText(ArabicLabels.customer, 40, 1) + getAccurateText(ArabicLabels.salesman, 80, 2), 2, object, 1, args, 5);
                this.outStream.write(this.NewLine);
                this.outStream.write(this.NewLine);
            } catch (Exception e) {
                e.fillInStackTrace();
            }

            printlines1(getAccurateText(object.getString("invoicefooter"), 80, 1), 1, object, 1, args, 5);
            // printlines1(getAccurateText("For any complaints please contact " + object.getString("supervisorname") + "on" + object.getString("supervisorno"), 40, 0), false, 1);

            //jSONObject = object;
            this.outStream.write(this.CompressOff);
//            if (context instanceof PromotioninfoActivity) {
//                closeConnection();
//                ((PromotioninfoActivity) context).callback(App.SALES_INVOICE);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void headerinvprint(JSONObject object, int invtype) throws JSONException {
        try {

            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);

            this.outStream.write(this.BoldOn);
            printheaders(getAccurateText("ROUTE#: " + object.getString("ROUTE"), 26, 0) + getAccurateText("SMAN#: " + object.getString("SALESMAN"), 26, 0) + getAccurateText("DATE:" + object.getString("DOC DATE") + " " + object.getString("TIME"), 26, 2), false, 1);
            this.outStream.write(this.NewLine);
            // printheaders(getAccurateText("SALESMAN: " + object.getString("SALESMAN"), 40, 0) + getAccurateText("SALESMAN NO: " + object.getString("CONTACTNO"), 40, 2), false, 1);
            // this.outStream.write(this.NewLine);
            try {
                if (!(invtype == 3)) {
                    printheaders(getAccurateText("DOCUMENT NO: " + object.getString("DOCUMENT NO"), 40, 0) + getAccurateText("TRIP START DATE:" + object.getString("TRIP START DATE"), 40, 2), false, 1);
                    this.outStream.write(this.NewLine);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //printheaders(getAccurateText("SUPERVISOR NAME:" + object.getString("supervisorname"), 40, 0) + getAccurateText("SUPERVISOR PHONE: " + object.getString("supervisorno"), 40, 2), true, 1);
            /*if(invtype != 3) {
                this.outStream.write(this.NewLine);
                printheaders(getAccurateText("TRIP ID:" + object.getString("TripID"), 80, 0), false, 2);
            }*/
            this.outStream.write(this.BoldOff);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.BoldOn);
            this.outStream.write(this.DoubleWideOn);
            if (invtype == 101) {
                printheaders(getAccurateText("LOAD SUMMARY ", 40, 1), false, 1);
                printheaders(getAccurateText("LOAD NUMBER: " + Const.loadNumber, 40, 1), false, 1);
                Const.loadNumber = Const.loadNumber + 1;
            } else if (invtype == 2) {
                printheaders(getAccurateText("LOAD TRANSFER SUMMARY", 40, 1), false, 1);
            } else if (invtype == 103) {
                printheaders(getAccurateText("ENDING INVENTORY SUMMARY", 40, 1), false, 2);
            } else if (invtype == 102) {
                printheaders(getAccurateText("LOAD REQUEST - " + object.getString("LOAD DATE"), 40, 1), false, 1);
            } else if (invtype == 6) {
                printheaders(getAccurateText("COMPANY CREDIT SUMMARY", 40, 1), false, 1);
            }
            this.outStream.write(this.DoubleWideOff);
            this.outStream.write(this.BoldOff);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            /*if (invtype == 2) {
                JSONArray jData = object.getJSONArray(JsonRpcUtil.PARAM_DATA);
                if (jData.getJSONObject(0).getJSONArray("DATA").length() > 0 && (jData.getJSONObject(1).getJSONArray("DATA").length() > 0 || jData.getJSONObject(2).getJSONArray("DATA").length() > 0)) {
                    printheaders(getAccurateText("FROM & TO ROUTE: " + object.getString("TO ROUTE"), 80, 0), false, 1);
                    this.outStream.write(this.NewLine);
                } else if (jData.getJSONObject(0).getJSONArray("DATA").length() > 0) {
                    printheaders(getAccurateText("FROM ROUTE: " + object.getString("TO ROUTE"), 80, 0), false, 1);
                    this.outStream.write(this.NewLine);
                } else if (jData.getJSONObject(1).getJSONArray("DATA").length() > 0) {
                    printheaders(getAccurateText("TO ROUTE: " + object.getString("TO ROUTE"), 80, 0), false, 1);
                    this.outStream.write(this.NewLine);
                } else if (jData.getJSONObject(2).getJSONArray("DATA").length() > 0) {
                    printheaders(getAccurateText("TO ROUTE: " + object.getString("TO ROUTE"), 80, 0), false, 1);
                    this.outStream.write(this.NewLine);
                }
            }
            if (invtype == 5) {
                printheaders(getAccurateText("Requested Delivery Date : " + object.getString("Requestdate"), 80, 0), false, 1);
                this.outStream.write(this.NewLine);
                this.outStream.write(this.NewLine);
            }*/
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

//    private void headerprint(JSONObject object, int type) throws JSONException {
//
//        try {
//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.BoldOn);
//            printheaders(getAccurateText("Berain Water and Beverages Factory - Branch",
//                    45, 0) + getAccurateText("@" + ArabicLabels.one + "!",
//                    80, 2), true, 1);
//
//            printheaders(getAccurateText("ROUTE#: " + object.getString("ROUTE"), 26, 0) + getAccurateText("SALESMAN#: " + object.getString("SALESMAN"), 26, 0) + getAccurateText("DATE:" + object.getString("DOC DATE") + " " + object.getString("TIME"), 26, 2), false, 1);
//            this.outStream.write(this.NewLine);
//            if (type == 3 || type == 5 || type == 6 || type == 4 || type == 7) {
//                printheaders(getAccurateText("TRIP START DATE:" + object.getString("TRIP START DATE"), 40, 0), false, 1);
//            } else {
//                /*if (!(type == 9 || type == 10)) {
//                    printheaders(getAccurateText("TRIP ID:" + object.getString("TripID"), 80, 0), false, 1);
//                }*/
//                /*else if(type == 9){
//                    printheaders(getAccurateText("SUPERVISOR NAME: " + object.getString("supervisorname"), 40, 0) + getAccurateText("SUPERVISOR NO: " + object.getString("supervisorno"), 40, 2), false, 1);
//                }*/
//            }
//            this.outStream.write(this.BoldOff);
//            this.outStream.write(this.NewLine);
//            this.outStream.write(this.NewLine);
//            if (!(type == 3 && type == 6 && type == 4 && type == 5 && type == 7) && object.has("invheadermsg") && object.getString("invheadermsg").length() > 0) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                //printheaders(object.getString("invheadermsg"), false, 3);
//                printheaders(getAccurateText(object.getString("invheadermsg"), 40, 1), false, 2);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.DoubleWideOff);
//            }
//            if (type == 1) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                try {
//                    printheaders(getAccurateText("Customer Order Request - " + object.getString("DELIVERY DATE"), 40, 1), false, 2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    printheaders(getAccurateText("Customer Order Request", 40, 1), false, 2);
//                }
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                try {
//                    printheaders("CUSTOMER ID: " + object.getString("CUSTOMERID"), false, 2);
//                    this.outStream.write(this.NewLine);
//                    String parts = object.getString("CUSTOMER");
//                    printheaders("CUSTOMER: " + parts.replaceAll("%20", " ").replaceAll("%26", "&").replaceAll("%2C", ",").replaceAll("%28", "(").replaceAll("%29", ")").replaceAll("%2F", "/").replaceAll("%5C", "\\"), true, 2);
//                    this.outStream.write(this.NewLine);
//                    String address = object.getString("ADDRESS").equals("null") ? "" : object.getString("ADDRESS");
//                    printheaders("ADDRESS: " + address, false, 1);
//                    this.outStream.write(this.NewLine);
//                    String no = object.getString("TRN").equals("null") ? "" : object.getString("TRN");
//                    printheaders("CUSTOMER TRN: " + no, false, 1);
//                    this.outStream.write(this.NewLine);
//                    this.outStream.write(this.BoldOff);
//
//                } catch (Exception e) {
//                }
//                this.count++;
//            } else if (type == 9) {   //Type for Sales Invoice
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                if (object.getString("LANG").equals("en")) {
//                    String customerType = "";
//                    if (object.getString("customertype").equals("1")) {
//                        customerType = "CASH";
//                    } else if (object.getString("customertype").equals("2")) {
//                        customerType = "CREDIT";
//                    } else if (object.getString("customertype").equals("3")) {
//                        customerType = "TC";
//                    }
//                    printheaders(getAccurateText("TAX INVOICE", 40, 1), false, 2);
//
//                    this.outStream.write(this.NewLine);
//                    //printheaders(getAccurateText(customerType + " " + "INVOICE NO - " + object.getString("ORDERNO"),40,1),false,2);
//                }
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                if (!object.getString("LPONO").equals("")) {
//                    printheaders(getAccurateText("LPO NO: " + object.getString("LPONO"), 137, 2), false, 0);
//                }
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                try {
//                    printheaders("INVOICE NO: " + object.getString("ORDERNO"), false, 1);
//                    this.outStream.write(this.NewLine);
//                    printheaders("CUSTOMER ID: " + object.getString("CUSTOMERID"), false, 2);
//                    this.outStream.write(this.NewLine);
//                    String parts = object.getString("CUSTOMER");
//                    printheaders("CUSTOMER: " + parts.replaceAll("%20", " ").replaceAll("%26", "&").replaceAll("%2C", ",").replaceAll("%28", "(").replaceAll("%29", ")").replaceAll("%2F", "/").replaceAll("%5C", "\\"), false, 2);
//                    this.outStream.write(this.NewLine);
//                    String address = object.getString("ADDRESS").equals("null") ? "" : object.getString("ADDRESS");
//                    printheaders("ADDRESS: " + address, false, 1);
//                    this.outStream.write(this.NewLine);
//                    String no = object.getString("TRN").equals("null") ? "" : object.getString("TRN");
//                    printheaders("CUSTOMER TRN: " + no, false, 1);
//                    this.outStream.write(this.NewLine);
//                    this.outStream.write(this.BoldOff);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                this.count++;
//            } else if (type == 11) {   //Type for GR,BR
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                if (object.getString("LANG").equals("en")) {
//                    String customerType = "";
//                    if (object.getString("customertype").equals("1")) {
//                        customerType = "CASH";
//                    } else if (object.getString("customertype").equals("2")) {
//                        customerType = "CREDIT";
//                    } else if (object.getString("customertype").equals("3")) {
//                        customerType = "TC";
//                    }
//                    printheaders(getAccurateText("TAX CREDIT NOTE", 40, 1), false, 2);
//                    this.outStream.write(this.NewLine);
//                    //printheaders(getAccurateText(customerType + " " + "INVOICE NO - " + object.getString("ORDERNO"),40,1),false,2);
//                }
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                try {
//                    printheaders("CREDIT NOTE NO: " + object.getString("ORDERNO"), false, 1);
//                    this.outStream.write(this.NewLine);
//                    printheaders("CUSTOMER ID: " + object.getString("CUSTOMERID"), false, 2);
//                    this.outStream.write(this.NewLine);
//                    String parts = object.getString("CUSTOMER");
//                    printheaders("CUSTOMER: " + parts.replaceAll("%20", " ").replaceAll("%26", "&").replaceAll("%2C", ",").replaceAll("%28", "(").replaceAll("%29", ")").replaceAll("%2F", "/").replaceAll("%5C", "\\"), false, 2);
//                    this.outStream.write(this.NewLine);
//                    String address = object.getString("ADDRESS").equals("null") ? "" : object.getString("ADDRESS");
//                    printheaders("ADDRESS: " + address, false, 1);
//                    this.outStream.write(this.NewLine);
//                    String no = object.getString("TRN").equals("null") ? "" : object.getString("TRN");
//                    printheaders("CUSTOMER TRN: " + no, false, 1);
//                    this.outStream.write(this.NewLine);
//                    this.outStream.write(this.BoldOff);
//
//                } catch (Exception e) {
//                }
//                this.count++;
//            } else if (type == 10) {   //Type for Delivery
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                if (object.getString("LANG").equals("en")) {
//                    //printheaders(getAccurateText(object.getString("INVOICETYPE"), 40, 1), false, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders(getAccurateText("DELIVERY NO - " + object.getString("ORDERNO"), 40, 1), false, 2);
//                    printheaders(getAccurateText("DELIVERY DATE - " + object.getString("DELIVERYDATE"), 40, 1), false, 2);
//                }
//                if (object.getString("LANG").equals("en")) {
//                    String customerType = "";
//                    if (object.getString("customertype").equals("1")) {
//                        customerType = "CASH";
//                    } else if (object.getString("customertype").equals("2")) {
//                        customerType = "CREDIT";
//                    } else if (object.getString("customertype").equals("3")) {
//                        customerType = "TC";
//                    }
//                    //printheaders(getAccurateText(object.getString("INVOICETYPE"), 40, 1), false, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders(getAccurateText("INVOICE NO - " + object.getString("ORDERNO"), 40, 1), false, 2);
//                }
//
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                try {
//                    printheaders("CUSTOMER ID: " + object.getString("CUSTOMERID"), false, 2);
//                    this.outStream.write(this.NewLine);
//
//                    String[] parts = object.getString("CUSTOMER").split("\\-");
//                    printheaders("CUSTOMER: " + parts[0], false, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders("ADDRESS: " + object.getString("ADDRESS"), false, 1);
//                    this.outStream.write(this.NewLine);
//                    printheaders("PAYEE: " + object.getString("payee"), false, 1);
//                    this.outStream.write(this.NewLine);
//
//                    String method = "";
//                    try {
//                        if (!Const.paySource.equals("") && !Const.paySource.equals("null")) {
//                            method = Const.paySource.replace("%20", " ");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    String reference = "";
//                    try {
//                        if (!Const.payReference.equals("") && !Const.payReference.equals("null")) {
//                            reference = Const.payReference.replace("%20", " ");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    printheaders(getAccurateText("PAYMENT REFERENCE: " + reference, 80, 0) + getAccurateText("  PAYMENT METHOD: " + method, 80, 2), false, 1);
////                    printheaders("PAYMENT REFERENCE: " + object.getString("PAYMENTREF") + "     "+"PAYMENT METHOD: "+method, false, 1);
//                    this.outStream.write(this.NewLine);
//                    //this.outStream.write(this.NewLine);
//                    //this.outStream.write(this.NewLine);
//                    this.outStream.write(this.BoldOff);
//                } catch (Exception e) {
//                }
//                this.count++;
//            } else if (type == 2) {
//                printheaders("Doc No: " + object.getString("DOCUMENT NO"), false, 2);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                if (object.getString("LANG").equals("en")) {
//                    printheaders(getAccurateText("PAYMENT RECEIPT", 40, 1), false, 2);
//                }
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                printheaders("CUSTOMER ID: " + object.getString("CUSTOMERID"), false, 2);
//                this.outStream.write(this.NewLine);
//                String parts = object.getString("CUSTOMER");
//                printheaders("CUSTOMER: " + parts.replaceAll("%20", " ").replaceAll("%26", "&").replaceAll("%2C", ",").replaceAll("%28", "(").replaceAll("%29", ")").replaceAll("%2F", "/").replaceAll("%5C", "\\"), true, 2);
//                this.outStream.write(this.NewLine);
//
//                this.outStream.write(this.BoldOff);
//                printheaders("ADDRESS :" + object.getString("ADDRESS"), true, 1);
//                this.outStream.write(this.NewLine);
//                String no = object.getString("TRN").equals("null") ? "" : object.getString("TRN");
//                printheaders("CUSTOMER TRN: " + no, false, 1);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            } else if (type == 3) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                if (isFirstInvoice) {
//                    printheaders(getAccurateText("DEPOSIT SUMMARY", 40, 1), false, 3);
//                } else {
//                    printheaders(getAccurateText("CREDIT NOTE", 40, 1), false, 3);
//                }
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.count++;
//            } else if (type == 4) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("SALES SUMMARY", 40, 1), false, 1);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            } else if (type == 5) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("ROUTE ACTIVITY LOG", 40, 1), false, 1);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            } else if (type == 6) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("ROUTE SUMMARY", 40, 1), false, 1);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            } else if (type == 7) {
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("DAMAGE/EXPIRY REPORT", 40, 1), false, 3);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            }
//        } catch (Exception e2) {
//            e2.printStackTrace();
//        }
//    }


    private void headerprint(JSONObject object, int type) throws JSONException {
        try {

            if (type == 9 || type == 10) {
                printheaders(getAccurateText("Berain Water and Beverages Factory - Branch",

                        45, 0) + getAccurateText("@" + ArabicLabels.one + "!", 80, 2), true, 1);
                this.outStream.write(this.NewLine);
                printheaders(getAccurateText("C.C.No 72253 C.R. 1010167216", 45, 0) + getAccurateText("@" + ArabicLabels.two + "!", 80, 2), true, 1);
                this.outStream.write(this.NewLine);
//                printheaders(getAccurateText("P.O box 1799 - Riyadh 11441", 45, 0) + getAccurateText("@" + ArabicLabels.three + "!", 80, 2), true, 1);
//                this.outStream.write(this.NewLine);
//                printheaders(getAccurateText("Riyadh - 2nd Industrial City", 45, 0) + getAccurateText("@" + ArabicLabels.four + "!", 80, 2), true, 1);
//                this.outStream.write(this.NewLine);
//                printheaders(getAccurateText("Kingdom of Saudi Arabia", 45, 0) + getAccurateText("@" + ArabicLabels.five + "!", 80, 2), true, 1);
//                this.outStream.write(this.NewLine);
//                printheaders(getAccurateText("Customer services : 920025555", 45, 0) + getAccurateText("@" + ArabicLabels.six + "!", 80, 2), true, 1);
//                this.outStream.write(this.NewLine);
                printheaders(getAccurateText("VAT Registration No. 310099425600003", 45, 0) + getAccurateText("@" + ArabicLabels.seven + "!", 80, 2), true, 1);
                this.outStream.write(this.NewLine);
            }
            this.outStream.write(this.NewLine);
            //this.outStream.write(this.BoldOn);
            printheaders(getAccurateText("ROUTE#: " +
                    object.getString("ROUTE"), 42, 0) +
                    getAccurateText("SALESMAN#: " + object.getString("SALESMAN"), 42, 0)
                    + getAccurateText("Invoice Date: " + object.getString("DOC DATE") + " "
                    + object.getString("TIME"), 42, 2), false, 1);
            this.outStream.write(this.NewLine);
            printheaders(getAccurateText(object.getString("ROUTE") + "@" + ArabicLabels.Route + "!", 42, 0) + getAccurateText(object.getString("SALESMAN") + " " + "@" + ArabicLabels.SalesMan + "!", 42, 0), true, 1);
            printheaders(getAccurateText(object.getString("TIME") + " " + object.getString("DOC DATE") + " " + "@" + ArabicLabels.Date + "!", 47, 2), true, 1);
            //printheaders(getAccurateText("SALESMAN: " + object.getString("SALESMAN"), 40, 0) + getAccurateText("SALESMAN NO: " + object.getString("CONTACTNO"), 40, 2), true, 1);
            this.outStream.write(this.NewLine);
            //printheaders(getAccurateText("SUPERVISOR NAME:" + object.getString("supervisorname"), 40, 0) + getAccurateText("SUPERVISOR PHONE: " + object.getString("supervisorno"), 40, 2), true, 1);
            if (type == 3 || type == 5 || type == 6 || type == 4 || type == 7) {
                printheaders(getAccurateText("TRIP START DATE:" + object.getString("TRIP START DATE"), 40, 0) + getAccurateText("TRIP ID:" + object.getString("TripID"), 40, 2), false, 1);
            } else {
                if (!(type == 9 || type == 10)) {
                    printheaders(getAccurateText("TRIP ID:" + object.getString("TripID"), 80, 0), false, 1);
                }
                /*else if(type == 9){
                    printheaders(getAccurateText("SUPERVISOR NAME: " + object.getString("supervisorname"), 40, 0) + getAccurateText("SUPERVISOR NO: " + object.getString("supervisorno"), 40, 2), false, 1);
                }*/
            }
            //this.outStream.write(this.BoldOff);
            this.outStream.write(this.NewLine);
            if (!(type == 3 && type == 6 && type == 4 && type == 5 && type == 7) && object.has("invheadermsg") && object.getString("invheadermsg").length() > 0) {
                this.outStream.write(this.BoldOn);
                this.outStream.write(this.DoubleWideOn);
                //printheaders(object.getString("invheadermsg"), false, 3);
                printheaders(getAccurateText(object.getString("invheadermsg"), 40, 1), false, 2);
                this.outStream.write(this.BoldOff);
                this.outStream.write(this.DoubleWideOff);
            }

//            if (type == 1) {
//                this.outStream.write(this.BoldOn);
//                //this.outStream.write(this.DoubleWideOn);//Segi
//                if (object.getString("LANG").equals("en")) {
//                    printheaders(getAccurateText(object.getString("INVOICETYPE"), 40, 1), false, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders(getAccurateText("ORDER NO - " + object.getString("ORDERNO"), 40, 1), false, 2);
//                } else if (object.getString("invoicepaymentterms").contains("2")) {
//                    printheaders(getAccurateText("@" + ArabicTEXT.Creditinvoice + "!:" + object.getString("invoicenumber"), 40, 1), true, 2);
//                } else if (object.getString("invoicepaymentterms").contains("0") || object.getString("invoicepaymentterms").contains("1")) {
//                    printheaders(getAccurateText("@" + ArabicTEXT.Cashinvoice + "!:" + object.getString("invoicenumber"), 40, 1), true, 2);
//                } else {
//                    //printheaders(getAccurateText(object.getString("INVOICETYPE"), 40, 1), false, 2);
//                }
////                this.outStream.write(this.DoubleWideOff);//Segi
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                try {
//                    String[] parts = object.getString("CUSTOMER").split("\\-");
//                    printheaders("CUSTOMER: " + parts[0], true, 2);
//                    this.outStream.write(this.NewLine);
//                    try {
//                        Log.e("ARABIC CUSTOMER", "" + parts[1]);
//                        printheaders("          @" + parts[1] + "!", true, 1);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    this.outStream.write(this.NewLine);
//                    this.outStream.write(this.BoldOff);
//                    printheaders("ADDRESS: " + object.getString("ADDRESS"), false, 1);
//                    this.outStream.write(this.NewLine);
//                    printheaders("          @" + object.getString("ARBADDRESS") + "!", true, 1);
//                    this.outStream.write(this.NewLine);
//                    // this.outStream.write(this.NewLine);
//                    //  this.outStream.write(this.NewLine);
//                } catch (Exception e) {
//                }
//                this.count++;
//            }
//
//            else if (type == 9) {   //Type for Sales Invoice
            //this.outStream.write(this.BoldOn);
            //this.outStream.write(this.DoubleWideOn);
            if (object.getString("LANG").equals("en")) {
                String customerType = "";
                String customerTypear = "";
                if (object.getString("customertype").equals("1")) {
                    customerType = "CASH INVOICE NO";
                    customerTypear = ArabicLabels.CASH; // changes
                } else if (object.getString("customertype").equals("2")) {
                    customerType = "CREDIT INVOICE NO";
                    customerTypear = ArabicLabels.Credit + " " + ArabicLabels.InvoiceNo;  // changes
                } else if (object.getString("customertype").equals("3")) {
                    customerType = "TC INVOICE NO";
                    customerTypear = ArabicLabels.TC + " " + ArabicLabels.InvoiceNo; //changes
                }
                //printheaders(getAccurateText(object.getString("INVOICETYPE"), 40, 1), false, 2);
//                    printheaders(getAccurateText(customerType + " " + "INVOICE NO - " + object.getString("ORDERNO"),40,1),false,2);
                printheaders("        " + getAccurateText("@" + customerTypear + "!" + " / " + customerType + " - " + object.getString("ORDERNO"), 100, 1), true, 2);
                this.outStream.write(this.NewLine);
                printheaders("        " + getAccurateText("MANUAL INVOICE - " + object.getString("MANUAL INVOICE"), 100, 1), false, 0);

            } else if (object.getString("invoicepaymentterms").contains("2")) {
                printheaders(getAccurateText("@" + ArabicTEXT.Creditinvoice + "!:"
                        + object.getString("invoicenumber"), 40, 1), true, 2);
            } else if (object.getString("invoicepaymentterms").contains("0") || object.getString("invoicepaymentterms").contains("1")) {
                printheaders(getAccurateText("@" + ArabicTEXT.Cashinvoice + "!:" + object.getString("invoicenumber"), 40, 1), true, 2);
            } else {
                //printheaders(getAccurateText(object.getString("INVOICETYPE"), 40, 1), false, 2);
            }
            //this.outStream.write(this.DoubleWideOff);
            //this.outStream.write(this.BoldOff);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            //this.outStream.write(this.BoldOn);
            try {

                printheaders(getAccurateText("CUSTOMER ID: " + Const.custPayID,
                        60, 0) + getAccurateText(" : ", 3,
                        0) + getAccurateText(ArabicLabels.CustomerID, 35,
                        2), true, 2);
                this.outStream.write(this.NewLine);
                printheaders(getAccurateText("CUSTOMER NAME: " + Const.custPayName,
                        60, 0) + getAccurateText(" : ", 3, 0) +
                        getAccurateText(ArabicLabels.CustomerName, 35, 2), true, 2);
                this.outStream.write(this.NewLine);
                printheaders("ARABIC NAME: @" + Const.custPayNameAR + "!", true, 1);
                this.outStream.write(this.NewLine);
                //this.outStream.write(this.BoldOff);

                // changes
                printheaders("CUSTOMER ADDRESS: @" + Const.custPayAddress + "!", true, 1);
                this.outStream.write(this.NewLine);
                this.outStream.write(this.NewLine);

                printheaders(getAccurateText("CUSTOMER VAT NUMBER: " + Const.cusVATno, 60, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(ArabicLabels.CUSTOMER_VAT_NUMBER, 45, 2), true, 2);
                this.outStream.write(this.NewLine);
                printheaders(getAccurateText("CUSTOMER BRANCH ID: " + Const.cusBranchID, 60, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(ArabicLabels.CUSTOMER_BRANCH_ID, 45, 2), true, 2);
                this.outStream.write(this.NewLine);
                printheaders(getAccurateText("BRANCH NAME: " + Const.cusBranchName, 60, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(ArabicLabels.BRANCH_NAME, 45, 2), true, 2);
                this.outStream.write(this.NewLine);
                    /*printheaders("BRANCH ARABIC NAME: @" + Const.cusBranchNameAR + "!", true, 1);
                    this.outStream.write(this.NewLine);
                    this.outStream.write(this.NewLine);*/

            } catch (Exception e) {
            }
            this.count++;
//            }

//            else if (type == 10) {   //Type for Delivery
//                //this.outStream.write(this.BoldOn);
//                //this.outStream.write(this.DoubleWideOn);
//                if (object.getString("LANG").equals("en")) {
//                    //printheaders(getAccurateText(object.getString("INVOICETYPE"), 40, 1), false, 2);
//
//                    this.outStream.write(this.NewLine);
//                    printheaders("        "+ getAccurateText(ArabicLabels.DeliveryNo +" / DELIVERY NO - " + object.getString("ORDERNO"), 100, 1), true, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders("        "+ getAccurateText(ArabicLabels.DeliveryDate +" / DELIVERY DATE - " + object.getString("DELIVERYDATE"), 100, 1), true, 2);
//
//                }
//                if (object.getString("LANG").equals("en")) {
//                    String customerType = "";
//                    if (object.getString("customertype").equals("1")) {
//                        customerType = "CASH";
//                    } else if (object.getString("customertype").equals("2")) {
//                        customerType = "CREDIT";
//                    } else if (object.getString("customertype").equals("3")) {
//                        customerType = "TC";
//                    }
//                    this.outStream.write(this.NewLine);
//
//                    printheaders("      " + getAccurateText("@"+ArabicLabels.InvoiceNo + "!" + " / INVOICE NO - " + object.getString("ORDERNO"), 100, 1), true, 2);
//
////                    this.outStream.write(this.NewLine);
////                    printheaders("        "+getAccurateText("MANUAL INVOICE - " + object.getString("MANUAL INVOICE"),100,1),false,0);
//
//                } else if (object.getString("invoicepaymentterms").contains("2")) {
//                    printheaders(getAccurateText("@" + ArabicTEXT.Creditinvoice + "!:" + object.getString("invoicenumber"), 40, 1), true, 2);
//                } else if (object.getString("invoicepaymentterms").contains("0") || object.getString("invoicepaymentterms").contains("1")) {
//                    printheaders(getAccurateText("@" + ArabicTEXT.Cashinvoice + "!:" + object.getString("invoicenumber"), 40, 1), true, 2);
//                } else {
//                    //printheaders(getAccurateText(object.getString("INVOICETYPE"), 40, 1), false, 2);
//                }
//
//                //this.outStream.write(this.DoubleWideOff);
//                //this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                //this.outStream.write(this.BoldOn);
//                try {
//                    printheaders(getAccurateText("CUSTOMER ID: " + Const.custPayID, 60, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(ArabicLabels.CustomerID, 35, 2), true, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders(getAccurateText("CUSTOMER NAME: " + Const.custPayName, 60, 0) + getAccurateText(" : ", 3, 0)+ getAccurateText(ArabicLabels.CustomerName, 35, 2), true, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders("ARABIC NAME: @" + Const.custPayNameAR + "!", true, 1);
//                    this.outStream.write(this.NewLine);
//
//                    printheaders("CUSTOMER ADDRESS: @" + Const.custPayAddress+ "!", true, 1);
//                    this.outStream.write(this.NewLine);
//                    this.outStream.write(this.NewLine);
//
//                    printheaders(getAccurateText("CUSTOMER VAT NUMBER: " + Const.cusVATno, 60, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(ArabicLabels.CUSTOMER_VAT_NUMBER, 45, 2), true, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders(getAccurateText("CUSTOMER BRANCH ID: " + Const.cusBranchID, 60, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(ArabicLabels.CUSTOMER_BRANCH_ID, 45, 2), true, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders(getAccurateText("BRANCH NAME: " + Const.cusBranchName, 60, 0) + getAccurateText(" : ", 3, 0) + getAccurateText(ArabicLabels.BRANCH_NAME, 45, 2), true, 2);
//                    this.outStream.write(this.NewLine);
//                    printheaders("BRANCH ARABIC NAME: @" + Const.cusBranchNameAR + "!", true, 1);
//                    this.outStream.write(this.NewLine);
//
//
//                    printheaders("PAYEE: " + object.getString("payee"), false, 1);
//                    this.outStream.write(this.NewLine);
//                    String method = "";
//                    try {
//                        if (!Const.paySource.equals("") && !Const.paySource.equals("null")) {
//                            method = Const.paySource.replace("%20", " ");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    String reference = "";
//                    try {
//                        if (!Const.payReference.equals("") && !Const.payReference.equals("null")) {
//                            reference = Const.payReference.replace("%20", " ");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    printheaders(getAccurateText("PAYMENT REFERENCE: " + reference, 68, 0) + getAccurateText("  PAYMENT METHOD: " + method, 68, 2), false, 1);
//                    this.outStream.write(this.NewLine);
//                    this.outStream.write(this.NewLine);
//                    //this.outStream.write(this.NewLine);
//                    //this.outStream.write(this.NewLine);
//                } catch (Exception e) {
//                }
//                this.count++;
//            } else if (type == 2) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                if (object.getString("LANG").equals("en")) {
//                    printheaders(getAccurateText("RECEIPT: " + object.getString("RECEIPT"), 40, 1), false, 2);
//                } else {
//                    printheaders(getAccurateText("@" + ArabicTEXT.Receipt + "!:" + object.getString("RECEIPT"), 40, 1), true, 2);
//                }
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//
//                printheaders("CUSTOMER ID: " + object.getString("CUSTOMERID"), false, 2);
//                this.outStream.write(this.NewLine);
//                printheaders("CUSTOMER NAME: " + Const.custPayName, false, 2);
//                this.outStream.write(this.NewLine);
//                try {
//                    printheaders("ARABIC NAME: @" + Const.custPayNameAR + "!", true, 1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOff);
//                printheaders("CUSTOMER ADDRESS: @" + Const.custPayAddress+ "!", true, 1);
//                this.outStream.write(this.NewLine);
//                String reference = "";
//                try {
//                    if (!Const.payReference.equals("") && !Const.payReference.equals("null")) {
//                        reference = Const.payReference.replace("%20", " ");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                printheaders("PAYMENT REFERENCE: " + reference, false, 1);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//
//            } else if (type == 3) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("DEPOSIT SUMMARY", 40, 1), false, 3);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//                this.count++;
//            } else if (type == 4) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("SALES SUMMARY", 40, 1), false, 1);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            } else if (type == 5) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("ROUTE ACTIVITY LOG", 40, 1), false, 1);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            } else if (type == 6) {
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("ROUTE SUMMARY", 40, 1), false, 1);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            } else if (type == 7) {
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.BoldOn);
//                this.outStream.write(this.DoubleWideOn);
//                printheaders(getAccurateText("DAMAGE/EXPIRY REPORT", 40, 1), false, 3);
//                this.outStream.write(this.DoubleWideOff);
//                this.outStream.write(this.BoldOff);
//                this.outStream.write(this.NewLine);
//                this.outStream.write(this.NewLine);
//            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void headerprintForInvoice(JSONObject object, int type) throws JSONException {
        try {

            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.BoldOn);


            printheaders(getAccurateText("ROUTE#: " + object.getString("ROUTE"), 26, 0) +
                    getAccurateText("SALESMAN#: " + object.getString("SALESMAN"), 26, 0) +
                    getAccurateText("DATE:" + object.getString("DOC DATE") + " " +
                            object.getString("TIME"), 26, 2), false, 1);


            this.outStream.write(this.BoldOn);
            this.outStream.write(this.DoubleWideOn);

            if (!object.getString("LPONO").equals("")) {
                printheaders(getAccurateText("LPO NO: " + object.getString("LPONO"), 137, 2),
                        false, 0);
            }
            this.outStream.write(this.NewLine);
            this.outStream.write(this.NewLine);
            this.outStream.write(this.BoldOn);
            try {

                JSONArray headers = object.getJSONArray("HEADERS");
                String strheader = "";
                String strHeaderBottom = "";
                String strTotal = "";

                int MAXLEngth = 137;

                for (int i = 0; i < headers.length(); i++) {
                    MAXLEngth -= ((Integer) this.hashValues.get(headers.getString(i).toString())).intValue();
                }

                if (MAXLEngth > 0) {
                    MAXLEngth /= headers.length();
                }


//                    strTotalNoVat = getAccurateText("Total Before TAX(AED) : " + jTOBject.getString("Total Befor TAX(AED)"),
//                            137 + MAXLEngth, 2);
//                    printlines1(strTotalNoVat, 1, object, 1, args, 9);

                this.outStream.write(this.CompressOn);
//
//
//                    printlines1(getAccurateText("Invoice No : " + object.getString("ORDERNO"), 137 + MAXLEngth, 2), 1, object, 1, "", 9);
//                    printlines1(getAccurateText("Invoice Date : " + object.getString("DOC DATE"), 137 + MAXLEngth, 2), 1, object, 1, "", 9);
//                    printlines1(getAccurateText("Order No : " + object.getString("ORDERNO"), 137 + MAXLEngth, 2), 1, object, 1, "", 9);
//                    printlines1(getAccurateText("Order Date : " + object.getString("DOC DATE"), 137 + MAXLEngth, 2), 1, object, 1, "", 9);
//                    printlines1(getAccurateText("Delivery Date : " + object.getString("TRIP START DATE"), 137 + MAXLEngth, 2), 1, object, 1, "", 9);


                String strPhn = "Phone: " + object.getString("CONTACTNO");
                String strEmi = "Emirate: " + "N/A";
                String strCou = "Country: " + "Dubai";
                String strTrn = "TRN: " + object.getString("TRN");
                String strCus = "Place of Supply: " + "Abu Dhabi";

                printlines1(strPhn + getAccurateText("رقم الفاتورة : " + object.getString("ORDERNO"), 137 + MAXLEngth - strPhn.length(), 2), 1, object, 1, "", 9);
                printlines1(strEmi + getAccurateText("تاريخ الفاتورة : " + object.getString("DOC DATE"), 137 + MAXLEngth - strEmi.length(), 2), 1, object, 1, "", 9);
                printlines1(strCou + getAccurateText("رقم الطلب : " + object.getString("ORDERNO"), 137 + MAXLEngth - strCou.length(), 2), 1, object, 1, "", 9);
                printlines1(strTrn + getAccurateText("تاريخ الطلب : " + object.getString("DOC DATE"), 137 + MAXLEngth - strTrn.length(), 2), 1, object, 1, "", 9);
                printlines1(strCus + getAccurateText("تاريخ التسليم او الوصول : " + object.getString("TRIP START DATE"), 137 + MAXLEngth - strCus.length(), 2), 1, object, 1, "", 9);


                this.outStream.write(this.CompressOff);

                this.outStream.write(this.NewLine);
                this.outStream.write(this.BoldOff);

            } catch (Exception e) {
                e.printStackTrace();
            }
            this.count++;

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }


}
