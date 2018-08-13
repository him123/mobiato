package com.ae.benchmark.util;

/*
 * Created by Himanshu on 4/11/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.SuperVisorApproveActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

/*
 * Created by Himanshu dhakecha on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

//    public static final String BROADCAST_ACTION = "com.ae.benchmark.FCM";
    public static final String BROADCAST_ACTION = "com.benchmark.FCM";
    private static final String TAG = "MyFirebaseMsgService";
    public String str_title, str_fullname;
    Intent intentBr;
    String username;
    String user_id, user_dep_name, user_designation, user_full_name, user_cardimage, user_profileimage;
    JSONObject object;
    String str_time;
    String str_message;
//    private DBManager dbManager;

    private Bitmap profilePhoto = null;
    private Boolean isProfile = false;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Displaying data in log
        // It is optional
        this.intentBr = new Intent(BROADCAST_ACTION);
        try {
            object = new JSONObject(remoteMessage.getData());
            Log.v("", "Push notification: " + object.toString());
            sendNotification(object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendNotification(JSONObject object) {

        try {
            Intent intent = null;
//
            intent = new Intent(this, SuperVisorApproveActivity.class);

            if (object.getString("type").toString().equals("1")) {

                intentBr.putExtra("supervisor_id", object.getString("supervisor_id").toString());
                intentBr.putExtra("cust_id", object.getString("cust_id").toString());
                intentBr.putExtra("salesman_id", object.getString("salesman_id").toString());
                intentBr.putExtra("no_of_bottles", object.getString("no_of_bottles").toString());

                intent.putExtra("supervisor_id", object.getString("supervisor_id").toString());
                intent.putExtra("cust_id", object.getString("cust_id").toString());
                intent.putExtra("salesman_id", object.getString("salesman_id").toString());
                intent.putExtra("no_of_bottles", object.getString("no_of_bottles").toString());

                sendBroadcast(intentBr);

            } else {


                intentBr.putExtra("status", object.getString("status").toString());
                intentBr.putExtra("no_of_bottles", object.getString("no_of_bottles").toString());
//                intent.putExtra("tag", "type");
                sendBroadcast(intentBr);
            }

//
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent,
                    PendingIntent.FLAG_ONE_SHOT);
//
            Bitmap bitmapCircle = BitmapFactory.decodeResource(getResources(), R.drawable.ic_noti);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_noti)
                    .setContentTitle("SM1")
                    .setContentText("Request for Approval")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setLargeIcon(bitmapCircle)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void AlertMessageNotifyAPI() {
//        RestClient.getMutualTransfer().get_alert_deliverd(Util.ReadSharePrefrence(MyFirebaseMessagingService.this, Constant.SHRED_PR.ID),
//                str_time, "1",
//                new Callback<Response>() {
//                    @Override
//                    public void success(Response response, Response response2) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(Util.getString(response.getBody().in()));
//                            if (jsonObject.getString("status").equals("1")) {
//                                Toast.makeText(MyFirebaseMessagingService.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(MyFirebaseMessagingService.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.d("Error", "=== Errorr" + error);
//                    }
//                });
//    }

//    private void message_delivery(String user_id, String messageId) {
//        RestClient.getMutualTransfer().message_delivery(user_id, messageId, new Callback<Response>() {
//            @Override
//            public void success(Response response, Response response2) {
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//    }

//    private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {
//        JSONObject jObjStr;
//        Bitmap bitmapProfile = null;
//
//        public AsyncGettingBitmapFromUrl(JSONObject jObjStr) {
//            this.jObjStr = jObjStr;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//
//            System.out.println("doInBackground");
//
//            try {
//                if (!object.optString("user_image").equals("")) {
//                    bitmapProfile = Glide.
//                            with(MyFirebaseMessagingService.this).
//                            load(object.optString("user_image").toString()).
//                            asBitmap().
//                            into(100, 100). // Width and height
//                            get();
//
//
//                }
//
//                if (!object.optString("user_image").equals("")) {
//                    profilePhoto = Glide.
//                            with(MyFirebaseMessagingService.this).
//                            load(object.optString("user_image").toString()).
//                            asBitmap().
//                            into(100, 100). // Width and height
//                            get();
//
//                    URL url = new URL(jObjStr.getString("user_image"));
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setDoInput(true);
//                    connection.connect();
//                    InputStream input = connection.getInputStream();
//                    profilePhoto = BitmapFactory.decodeStream(input);
//
//                    Bitmap bitmap = profilePhoto;
//                    Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//
//                    BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//                    Paint paint = new Paint();
//                    paint.setShader(shader);
//                    paint.setAntiAlias(true);
//                    Canvas c = new Canvas(circleBitmap);
//                    c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
//
//                    profilePhoto = circleBitmap;
//
//
//                    isProfile = true;
//                }
//
//                Bitmap myBitmap = null;
//                if (object.optString("card_image") != null && !object.optString("card_image").equals("")) {
//                    URL url = new URL(jObjStr.getString("card_image"));
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setDoInput(true);
//                    connection.connect();
//                    InputStream input = connection.getInputStream();
//                    myBitmap = BitmapFactory.decodeStream(input);
//                }
//
//                return myBitmap;
//
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//                return null;
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return null;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            System.out.println("bitmap" + bitmap);
//
//            try {
//                RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.expand_notification);
//                RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.collapsed_notification);
//
//                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                Intent intent = null;
//                if ((object.opt("type").toString().equals("USER_MATCH"))) {
//                    collapsedView.setTextViewText(R.id.txt_fullname_collaps, "Today's Match");
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, jObjStr.optString("user_full_name"));
//
//                    collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(MyFirebaseMessagingService.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//                    expandedView.setImageViewBitmap(R.id.img_cardimage, bitmap);
//                    str_title = "Your today's match";
//
//                    intent = new Intent(MyFirebaseMessagingService.this, DailyMatchActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    expandedView.setTextViewText(R.id.txt_fullname, "Today's Match");
//                    expandedView.setTextViewText(R.id.txt_department, jObjStr.getString("user_full_name"));
//                    expandedView.setTextViewText(R.id.txt_designation, Util.capitalFirstLatter(jObjStr.getString("department")));
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, jObjStr.getString("user_full_name"));
//
//                    if (isProfile) {
//                        collapsedView.setImageViewBitmap(R.id.img_profile_collaps, profilePhoto);
//                        expandedView.setImageViewBitmap(R.id.img_profile, profilePhoto);
//                    }
//
//                    intent.putExtra("id", jObjStr.getString("id"));
//                    str_fullname = jObjStr.getString("user_full_name");
//
//                    intent.putExtra("device_token", refreshedToken);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent,
//                            PendingIntent.FLAG_ONE_SHOT);
//                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle(str_fullname)
//                            .setAutoCancel(true)
//                            .setContentIntent(pendingIntent)
//                            .setSound(defaultSoundUri)
//                            .setLargeIcon(bitmapProfile)
//                            .setCustomContentView(collapsedView)
//                            .setCustomBigContentView(expandedView);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    notificationManager.notify(0, builder.build());
//
//                } else if ((object.opt("type").toString().equals("ALERT_ACCEPT"))) {
//                    expandedView.setTextViewText(R.id.txt_fullname, Util.capitalFirstLatter(jObjStr.getString("user_full_name")));
//                    expandedView.setTextViewText(R.id.txt_department, object.getString("message"));
//                    expandedView.setTextViewText(R.id.txt_designation, "");
//                    expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(MyFirebaseMessagingService.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//
//                    collapsedView.setTextViewText(R.id.txt_fullname_collaps, Util.capitalFirstLatter(jObjStr.getString("user_full_name")));
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, jObjStr.optString("message"));
//
//                    collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(MyFirebaseMessagingService.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//                    expandedView.setImageViewBitmap(R.id.img_cardimage, bitmap);
//
//                    if (isProfile) {
//                        collapsedView.setImageViewBitmap(R.id.img_profile_collaps, profilePhoto);
//                        expandedView.setImageViewBitmap(R.id.img_profile, profilePhoto);
//                    }
//
//                    str_title = "Your request has been accepted";
//                    intent = new Intent(MyFirebaseMessagingService.this, MyConnectionsListActivity.class);
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, jObjStr.getString("message"));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    str_fullname = jObjStr.getString("user_full_name");
//
//                    intent.putExtra("device_token", refreshedToken);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent,
//                            PendingIntent.FLAG_ONE_SHOT);
//                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle(str_fullname)
//                            .setAutoCancel(true)
//                            .setContentIntent(pendingIntent)
//                            .setSound(defaultSoundUri)
//                            .setLargeIcon(bitmapProfile)
//                            .setCustomContentView(collapsedView)
//                            .setCustomBigContentView(expandedView);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    notificationManager.notify(0, builder.build());
//                } else if ((object.opt("type").toString().equals("ALERT_REJECT"))) {
//                    expandedView.setTextViewText(R.id.txt_fullname, Util.capitalFirstLatter(jObjStr.getString("user_full_name")));
//                    expandedView.setTextViewText(R.id.txt_department, object.getString("message"));
//                    expandedView.setTextViewText(R.id.txt_designation, "");
//                    expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(MyFirebaseMessagingService.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//
//                    collapsedView.setTextViewText(R.id.txt_fullname_collaps, Util.capitalFirstLatter(jObjStr.getString("user_full_name")));
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, jObjStr.optString("message"));
//
//                    collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(MyFirebaseMessagingService.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//                    expandedView.setImageViewBitmap(R.id.img_cardimage, bitmap);
//
//                    expandedView.setViewVisibility(R.id.vieProfile, View.GONE);
//
//                    if (isProfile) {
//                        collapsedView.setImageViewBitmap(R.id.img_profile_collaps, profilePhoto);
//                        expandedView.setImageViewBitmap(R.id.img_profile, profilePhoto);
//                    }
//
//                    str_title = "You request has been rejected";
//                    intent = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
//
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, jObjStr.getString("message"));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    str_fullname = jObjStr.getString("user_full_name");
//
//                    intent.putExtra("device_token", refreshedToken);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent,
//                            PendingIntent.FLAG_ONE_SHOT);
//                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle(str_fullname)
//                            .setAutoCancel(true)
//                            .setContentIntent(pendingIntent)
//                            .setSound(defaultSoundUri)
//                            .setLargeIcon(bitmapProfile)
//                            .setCustomContentView(collapsedView)
//                            .setCustomBigContentView(expandedView);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    notificationManager.notify(0, builder.build());
//
//                } else if ((object.opt("type").toString().equals("SEND_USER_PROFILE_REQUEST"))) {
//                    expandedView.setTextViewText(R.id.txt_fullname, jObjStr.getString("user_full_name"));
//                    expandedView.setTextViewText(R.id.txt_department, jObjStr.getString("message"));
//                    expandedView.setTextViewText(R.id.txt_designation, "");
////                    expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(MyFirebaseMessagingService.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//
//                    collapsedView.setTextViewText(R.id.txt_fullname_collaps, jObjStr.getString("user_full_name"));
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, jObjStr.getString("message"));
//
//                    collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(MyFirebaseMessagingService.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//                    expandedView.setImageViewBitmap(R.id.img_cardimage, bitmap);
//
//                    expandedView.setViewVisibility(R.id.acceptRejectLayout, View.VISIBLE);
//                    expandedView.setViewVisibility(R.id.vieProfile, View.GONE);
//
//                    if (isProfile) {
//                        collapsedView.setImageViewBitmap(R.id.img_profile_collaps, profilePhoto);
//                        expandedView.setImageViewBitmap(R.id.img_profile, profilePhoto);
//                    }
//
////                    Intent intentAccept = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
////
////                    PendingIntent pendingIntentAccept = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent,
////                            PendingIntent.FLAG_ONE_SHOT);
////
////                    expandedView.setOnClickPendingIntent(R.id.btn_accept, PendingIntent.getService(MyFirebaseMessagingService.this, 0, intentAccept, PendingIntent.FLAG_UPDATE_CURRENT));
//
//
//                    str_title = "Incoming request";
//                    intent = new Intent(MyFirebaseMessagingService.this, RequestListActivity.class);
//                    str_fullname = jObjStr.getString("user_full_name");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                    intent.putExtra("device_token", refreshedToken);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent,
//                            PendingIntent.FLAG_ONE_SHOT);
//                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle(str_fullname)
//                            .setAutoCancel(true)
//                            .setContentIntent(pendingIntent)
//                            .setSound(defaultSoundUri)
//                            .setLargeIcon(bitmapProfile)
//                            .setCustomContentView(collapsedView)
//                            .setCustomBigContentView(expandedView);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    notificationManager.notify(0, builder.build());
//
//                } else if ((object.opt("type").toString().equals("ALERT_SUPPORT"))) {
//                    expandedView.setTextViewText(R.id.txt_fullname, "M-Trans");
//                    collapsedView.setTextViewText(R.id.txt_fullname_collaps, "M-Trans");
//
//                    expandedView.setTextViewText(R.id.txt_department, object.getString("message"));
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, object.getString("message"));
//                    expandedView.setImageViewBitmap(R.id.img_cardimage, bitmap);
//                    expandedView.setTextViewText(R.id.txt_designation, "");
//
//                    if (isProfile) {
//                        collapsedView.setImageViewBitmap(R.id.img_profile_collaps, profilePhoto);
//                        expandedView.setImageViewBitmap(R.id.img_profile, profilePhoto);
//                    }
//
//                    intent = new Intent(MyFirebaseMessagingService.this, InformationActivity.class);
//                    collapsedView.setTextViewText(R.id.txt_department_collaps, jObjStr.getString("message"));
//                    intent.putExtra("information_message", object.getString("message"));
//                    intent.putExtra("information_image", object.getString("card_image"));
//                    intent.putExtra("information_link", object.getString("link"));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    str_fullname = "M-Trans";
//
//                    intent.putExtra("device_token", refreshedToken);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent,
//                            PendingIntent.FLAG_ONE_SHOT);
//                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle(str_fullname)
//                            .setAutoCancel(true)
//                            .setContentIntent(pendingIntent)
//                            .setSound(defaultSoundUri)
//                            .setLargeIcon(bitmapProfile)
//                            .setCustomContentView(collapsedView)
//                            .setCustomBigContentView(expandedView);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    notificationManager.notify(0, builder.build());
//
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}