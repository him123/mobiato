package com.ae.benchmark.rest;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by dgohil on 6/17/15.
 */
public interface CommonService {

    @FormUrlEncoded
    @POST("/login.php")
    void login(
            @Field("user") String username,
            @Field("pass") String pass,
            @Field(value = "token", encodeValue = false) String token,

            Callback<Response> user);


    @GET("/UserauthSet")
    void loginOData(
            @Query("$filter=USERID") String USERID,
            @Query("$filter=PASSWORD") String PASSWORD,
            @Query("$filter=format") String format,

            Callback<Response> user);


    @FormUrlEncoded
    @POST("/request_for_approval.php")
    void request_for_approval(
            @Field("supervisor_id") String supervisor_id,
            @Field("cust_id") String cust_id,
            @Field("salesman_id") String salesman_id,
            @Field("no_of_bottles") String no_of_bottles,

            Callback<Response> user);


    @FormUrlEncoded
    @POST("/accept_reject_approval.php")
    void accept_reject_approval(
            @Field("salesman_id") String salesman_id,
            @Field("status") String status,

            Callback<Response> user);

//    @FormUrlEncoded
//    @POST("/login_new_for_view_table.php")
//    void login_new_for_view(
//            @Field("mobile_num") String mobile_num,
//
//            Callback<Response> user);
//
//    @FormUrlEncoded
//    @POST("/get-users.php")
//    void getusers(@Field("id") String full_name,
//                  @Field("state_id") String state_id,
//                  @Field("dept_id") String dept_id,
//                  @Field("sub_dept_id") String sub_dept_id,
//                  @Field("post_id") String post_id,
//                  @Field("designation") String designation,
//                  @Field("wtt_dist_id") String wtt_dist_id,
//                  @Field("wtt_state_id") String wtt_state_id,
//                  @Field("record_per_page") String record_per_page,
//                  @Field("page_num") String page_num,
//                  @Field("token") String token,
//                  @Field("emp_type") String emp_type,
//
//                  Callback<ArrayList<User>> user);
//
//
//    @FormUrlEncoded
//    @POST("/get_users_newwwwwww.php")
//    void getusers_new(@Field("id") String full_name,
//                      @Field("state_id") String state_id,
//                      @Field("dept_id") String dept_id,
//                      @Field("sub_dept_id") String sub_dept_id,
//                      @Field("post_id") String post_id,
//                      @Field("designation") String designation,
//                      @Field("wtt_dist_id") String wtt_dist_id,
//                      @Field("wtt_state_id") String wtt_state_id,
//                      @Field("record_per_page") String record_per_page,
//                      @Field("page_num") String page_num,
//                      @Field("token") String token,
//                      @Field("emp_type") String emp_type,
//
//                      Callback<ArrayList<User>> user);
//
//
//    @FormUrlEncoded
//    @POST("/global-search.php")
//    void search(
//            @Field("id") String id,
//            @Field("state_id") String state_id,
//            @Field("dept_id") String dept_id,
//            @Field("sub_dept_id") String sub_dept_id,
//            @Field("post_id") String post_id,
//            @Field("wtt_dist_id") String wtt_dist_id,
//            @Field("record_per_page") String record_per_page,
//            @Field("page_num") String page_num,
//            @Field("token") String token,
//            @Field("emp_type") String empp_type,
//
//            Callback<ArrayList<User>> user);
//
//    @FormUrlEncoded
//    @POST("/global-search-newwwwwww.php")
//    void search_new(
//            @Field("id") String id,
//            @Field("state_id") String state_id,
//            @Field("dept_id") String dept_id,
//            @Field("sub_dept_id") String sub_dept_id,
//            @Field("post_id") String post_id,
//            @Field("wtt_dist_id") String wtt_dist_id,
//            @Field("record_per_page") String record_per_page,
//            @Field("page_num") String page_num,
//            @Field("token") String token,
//            @Field("emp_type") String empp_type,
//
//            Callback<ArrayList<User>> user);
//
//
//    @Multipart
//    @POST("/register.php")
//    void register(
//            //Personal information
//            @Part("full_name") TypedString full_name,
//            @Part("dob") TypedString dob,
//            @Part("mobile_num") TypedString mobile,
//            @Part("email") TypedString email,
//            //Profesional information
//            @Part("dept_id") TypedString dept_it,
//            @Part("sub_dept_id") TypedString sub_dept_id,
//            @Part("post_id") TypedString post_id,
//            @Part("designation") TypedString designation,
//            @Part("office_landline_num") TypedString office_landline_num,
//            @Part("current_dist_id") TypedString current_dist_id,
//            @Part("wtt_dist_id") TypedString wtt_dist_id,
//            @Part("state_id") TypedString state_id,
//            @Part("fcm_id") TypedString fcm_id,
//            @Part("password") TypedString password,
//            @Part("card_image") TypedFile image,
//
//            Callback<Response> user);
//
//    @Multipart
//    @POST("/update-profile.php")
//    void update_profile(
//            //Personal information
//            @Part("id") TypedString id,
//            @Part(" ") TypedString full_name,
//            @Part("dob") TypedString dob,
//            //Profesional information
//            @Part("dept_id") TypedString dept_it,
//            @Part("sub_dept_id") TypedString sub_dept_id,
//            @Part("post_id") TypedString post_id,
//            @Part("designation") TypedString designation,
//            @Part("office_landline_num") TypedString office_landline_num,
//            @Part("current_dist_id") TypedString current_dist_id,
//            @Part("wtt_dist_id") TypedString wtt_dist_id,
//            @Part("state_id") TypedString state_id,
//            @Part("card_image") TypedFile image,
//            @Part("profile_image") TypedFile profile_image,
//            @Part("token") TypedString token,
//            Callback<Response> user);
//
//
//    @FormUrlEncoded
//    @POST("/send-users-profile-request.php")
//    void send_request(@Field("id") String id,
//                      @Field("to_user_id") String to_user_id,
//                      @Field("private_message") String private_message,
//                      @Field("token") String token,
//
//                      Callback<Response> res);
//
//    @FormUrlEncoded
//    @POST("/appear-in-search-setting.php")
//    void appear_in_search_settings(@Field("id") String id,
//                                   @Field("appear_in_search") String appear_in_search,
//                                   @Field("token") String token,
//
//                                   Callback<Response> res);
//
//    @FormUrlEncoded
//    @POST("/email-notification-setting.php")
//    void email_nofification_setting(@Field("id") String id,
//                                    @Field("email_notification") String email_notification,
//                                    @Field("token") String token,
//
//                                    Callback<Response> res);
//
//    @FormUrlEncoded
//    @POST("/post-suggestions.php")
//    void post_suggestions(@Field("id") String id,
//                          @Field("description") String description,
//                          @Field("token") String token,
//
//                          Callback<Response> res);
//
//    @FormUrlEncoded
//    @POST("/get-my-notification.php")
//    void get_my_notifications(@Field("id") String id,
//                              @Field("token") String token,
//                              @Field("record_per_page") String records_per_page,
//                              @Field("page_num") String page_no,
//                              Callback<ArrayList<Notification>> notification);
//
//    @FormUrlEncoded
//    @POST("/get-my-request.php")
//    void get_my_request(@Field("id") String id,
//                        @Field("token") String token,
//                        @Field("records_per_page") String records_per_page,
//                        @Field("page_no") String page_no,
//                        @Field("emp_type") String empp_type,
//
//                        Callback<ArrayList<User>> user);
//
//    @FormUrlEncoded
//    @POST("/accept-reject-profile-request.php")
//    void accept_request(
//            @Field("id") String id,
//            @Field("user_id") String user_id,
//            @Field("approval_id") String approval_id,
//            @Field("approval_status") String approval_status,// type for block 1 2 3=block
//            @Field("token") String token,
//            @Field("reject_reason_message") String reject_reason_message,
//
//            Callback<Response> res);
//
//    @FormUrlEncoded
//    @POST("/request-for-upload-card.php")
//    void request_for_card(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("to_user_id") String to_user_id,
//
//            Callback<Response> res);
//
//
//    @FormUrlEncoded
//    @POST("/get-my-connections.php")
//    void get_my_connections(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("records_per_page") String records_per_page,
//            @Field("page_num") String page_no,
//            @Field("emp_type") String empp_type,
//
//            Callback<ArrayList<User>> user);
//
//    @FormUrlEncoded
//    @POST("/auto_suggestion.php")
//    void get_auto_suggetion(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("records_per_page") String records_per_page,
//            @Field("page_num") String page_no,
//            @Field("emp_type") String empp_type,
//
//            Callback<ArrayList<User>> user);
//
//
//    @FormUrlEncoded
//    @POST("/get-user-info-list.php")
//    void get_user_from_emp_code(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("user_id") String user_id,
//            @Field("emp_type") String emp_type,
//
//            Callback<ArrayList<User>> user);
//
//    @FormUrlEncoded
//    @POST("/verify-otp.php")
//    void verify_otp(
//            @Field("id") String id,
//            @Field("otp") String otp,
//
//            Callback<Response> res);
//
//    @GET("/get-state-list.php")
//    void get_state_list(
//            Callback<ArrayList<Selection>> selection);
//
//    @GET("/get_zones.php")
//    void get_zone_list(
//            Callback<ArrayList<Selection>> selection);
//
//    @GET("/get_divisions.php")
//    void get_division_list(
//            Callback<ArrayList<Selection>> selection);
//
//    @FormUrlEncoded
//    @POST("/get-city-list.php")
//    void get_city_list(
//            @Field("state_id") String state_id,
//            Callback<ArrayList<Selection>> selection);
//
//    @FormUrlEncoded
//    @POST("/get_divisions.php")
//    void get_division_list(
//            @Field("zone_id") String zone_id,
//            Callback<ArrayList<Selection>> selection);
//
//    @FormUrlEncoded
//    @POST("/get-category-list.php")
//    void get_departments(
//            @Field("state_id") String state_id,
//            Callback<ArrayList<Selection>> selection);
//
//    //CENTRAL GOVT DEPARTMENTS
//    @GET("/get-central-categories.php")
//    void get_central_departments(
//            Callback<ArrayList<Selection>> selection);
//
//    //RAILWAYS DEPARTMENTS
//    @GET("/get-railways-categories.php")
//    void get_rail_departments(
//            Callback<ArrayList<Selection>> selection);
//
//    //RAILWAYS SUB DEPARTMENTS
//
//    //RAILWAYS DESIGNATIONS
//    @GET("/get-railways-desig.php")
//    void get_rail_designations(
//            Callback<ArrayList<Selection>> selection);
//
//    //ALL INDIA BANKS
//    @GET("/get-banks.php")
//    void get_all_banks(
//            Callback<ArrayList<Selection>> selection);
//
//    @FormUrlEncoded
//    @POST("/get-inner-sub-category-list.php")
//    void get_inner_sub_departments(
//            @Field("sub_category_id") String sub_category_id,
//            Callback<ArrayList<Selection>> selection);
//
//    @FormUrlEncoded
//    @POST("/get-sub-category-list.php")
//    void get_sub_departments(
//            @Field("category_id") String category_id,
//            Callback<ArrayList<Selection>> selection);
//
//    @FormUrlEncoded
//    @POST("/get-user-chat-history.php")
//    void get_user_message_history(
//
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("user_id") String user_id,
//            @Field("page_num") String page_num,
//            @Field("record_per_page") String record_per_page,
//            Callback<ArrayList<Chat>> selection);
//
//    @FormUrlEncoded
//    @POST("/get-dashboard-data.php")
//    void get_dashboard_data(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("emp_type") String empp_type,
//            @Field("fcm_id") String fcm_id,
//
//            Callback<ArrayList<User>> users);
//
//    @FormUrlEncoded
//    @POST("/get-user-chat-list.php")
//    void get_user_chat_list(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("user_id") String user_id,
//            Callback<Response> users);
//
//    @FormUrlEncoded
//    @POST("/get-user-info.php")
//    void get_user_info(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("user_id") String user_id,
//            Callback<Response> users);
//
//
//    @Multipart
//    @POST("/save-profile-information.php")
//    void register_step_two(
//            @Part("id") TypedString id,
//            @Part("token") TypedString token,
//            @Part("state_id") TypedString state_id,
//            @Part("dept_id") TypedString dept_id,
//            @Part("sub_dept_id") TypedString sub_dept_id,
//            @Part("post_id") TypedString post_id,
//            @Part("designation") TypedString designation,
//            @Part("current_dist_id") TypedString current_dist_id,
//            @Part("wtt_dist_id") TypedString wtt_dist_id,
//            @Part("inner_sub_dept_id") TypedString inner_sub_dept_id,
//
//            @Part("sub_division") TypedString sub_division,
//            @Part("wtt_state_id") TypedString wtt_state_id,
//            @Part("emp_type") TypedString emp_type,
//
//            @Part("other_sub_dept") TypedString other_sub_dept,
//            @Part("railway_category") TypedString railway_category,
//
//            @Part("card_image") TypedFile card_image,
//            Callback<Response> res);
//
//    @Multipart
//    @POST("/register-step-one.php")
//    void register_step_one(
//            @Part("first_name") TypedString first_name,
//            @Part("last_name") TypedString last_name,
//            @Part("dob") TypedString dob,
//            @Part("mobile_num") TypedString mobile_num,
//            @Part("email") TypedString email,
//            @Part("fcm_id") TypedString fcm_id,
//            @Part("password") TypedString password,
//            @Part("device_name") TypedString device_name,
//            @Part("android_os_version") TypedString android_os_version,
//            @Part("gender") TypedString gender,
//            @Part("long_info_id") TypedString long_info_id,
//            @Part("profile_image") TypedFile profile_image,
//
//            Callback<Response> res);
//
//    @Multipart
//    @POST("/save-personal-details.php")
//    void save_personal_details(
//            @Part("id") TypedString id,
//            @Part("token") TypedString token,
//            @Part("first_name") TypedString first_name,
//            @Part("last_name") TypedString last_name,
//            @Part("dob") TypedString dob,
//            @Part("email") TypedString email,
//            @Part("gender") TypedString gender,
//            @Part("profile_image") TypedFile profile_image,
//
//            Callback<Response> res);
//
//
//    @FormUrlEncoded
//    @POST("/send-message.php")
//    void send_message(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("message") String message,
//            @Field("to_user_id") String to_user_id,
//            @Field("message_time") String message_time,
//            @Field("emp_type") String emp_type,
//
//            Callback<ArrayList<User>> users);
//
//    @FormUrlEncoded
//    @POST("/conversation_history_list.php")
//    void get_conversation_history_list(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("record_per_page") String record_per_page,
//            @Field("page_no") String page_num,
//            @Field("emp_type") String empp_type,
//
//            Callback<ArrayList<User>> selection);
//
//    @FormUrlEncoded
//    @POST("/get_people_request.php")
//    void get_people_request(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("record_per_page") String record_per_page,
//            @Field("page_num") String page_num,
//            @Field("emp_type") String empp_type,
//            Callback<ArrayList<User>> selection);
//
//
//    @FormUrlEncoded
//    @POST("/block-user-list.php")
//    void get_blocked_user(
//            @Field("id") String id,
//            @Field("token") String token,
//            @Field("record_per_page") String record_per_page,
//            @Field("page_num") String page_num,
//            Callback<ArrayList<User>> selection);
//
//
//    @FormUrlEncoded
//    @POST("/forget_password_web.php")
//    void forget_password(
//            @Field("mobile_number") String mobile_number,
//            Callback<Response> res);
//
//
//    @FormUrlEncoded
//    @POST("/add-help.php")
//    void get_help(
//            @Field("mobile_num") String mobile_num,
//            @Field("query") String query,
//            Callback<Response> res);
//
//    @FormUrlEncoded
//    @POST("/check-version-info.php")
//    void get_current_ver(
//            @Field("id") String id,
//            @Field("version") String version,
//            @Field("token") String token,
//
//            Callback<Response> res);
//
//    @FormUrlEncoded
//    @POST("/resend_otp.php")
//    void resend_otp(
//            @Field("mobile_number") String mobile_number,
//            Callback<Response> res);
//
//    @FormUrlEncoded
//    @POST("/long_info_user.php")
//    void contact_sync(
//            @Field("user_id") String user_id,
//            @Field("long_info") String long_info,
//            Callback<Response> res);
//
//
//    @FormUrlEncoded
//    @POST("/alert_delivered.php")
//    void get_alert_deliverd(@Field("user_id") String str_user_id,
//                            @Field("del_time") String timestamp,
//                            @Field("message_id") String str_message,
//                            Callback<Response> res);
//
//
//    @FormUrlEncoded
//    @POST("/message_delivery.php")
//    void message_delivery(@Field("id") String str_user_id,
//                          @Field("message_id") String message_id,
//                          Callback<Response> res);


}

