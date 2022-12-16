package com.example.karadvenderapp.NetworkController;


import androidx.annotation.Nullable;

import com.google.gson.JsonObject;

import org.json.JSONArray;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIInterface {

    @FormUrlEncoded
    @POST("API_Vendor/razorpay/pay.php")
    Call<ResponseBody> getorder_id(@Field("vendor_id") String vendor_id,
                                   @Field("business_info_id") String business_info_id,
                                   @Field("fld_package_id") String fld_package_id,
                                   @Field("amount") String amount);


    @FormUrlEncoded
    @POST("API_Vendor/razorpay/verify.php")
    Call<ResponseBody> sendData_id(@Field("razorpay_order_id") String razorpay_order_id,
                                   @Field("razorpay_payment_id") String razorpay_payment_id,
                                   @Field("razorpay_signature") String razorpay_signature,
                                   @Field("fld_package_id") String fld_package_id,
                                   @Field("vendor_id") String vendor_id,
                                   @Field("business_info_id") String business_info_id);

    //regiterpage
    @Multipart
    @POST("API_Vendor/addvendor.php")
    Call<ResponseBody> regiternew(@Part("company_name") @Nullable RequestBody company_name,
                                  @Part("owner_name") @Nullable RequestBody owner_name,
                                  @Part("mobile") @Nullable RequestBody mobile,
                                  @Part("email") @Nullable RequestBody email,
                                  @Part("date_of_birth") @Nullable RequestBody date_of_birth,
                                  @Part("gender") @Nullable RequestBody gender,
                                  @Part("password") @Nullable RequestBody password,
//                                  @Part("end_date") @Nullable RequestBody end_date,
                                  @Part @Nullable MultipartBody.Part file,
                                  @Part @Nullable MultipartBody.Part file2,
                                  @Part @Nullable MultipartBody.Part file3,
                                  @Part @Nullable MultipartBody.Part file4);


    //Login page
    @FormUrlEncoded
    @POST("API_Vendor/login.php")
    Call<ResponseBody> getlogin(@Field("mobile") String mobile,
                                @Field("vendor_token") String vendor_token);

    // otp page
    @FormUrlEncoded
    @POST("API_Vendor/checkotp.php")
    Call<ResponseBody> getotp(@Field("mobile") String mobile,
                              @Field("vendor_otp") String user_otp);

    // home page
    @FormUrlEncoded
    @POST("API_Vendor/view_business.php")
    Call<ResponseBody> getData(@Field("vendor_id") String vendor_id);


    @FormUrlEncoded
    @POST("API_Vendor/notification_count_vendor.php")
    Call<ResponseBody> getnoti_counts(@Field("vendor_id") String vendor_id);

    @FormUrlEncoded
    @POST("API_Vendor/notification_update_count_vendor.php")
    Call<ResponseBody> getnoti_countszero(@Field("vendor_id") String vendor_id,
                                          @Field("readnotification") String readnotification);

    // renewalrequest page
    @FormUrlEncoded
    @POST("API_Vendor/renewal_request.php")
    Call<ResponseBody> sendrequest(@Field("owner_name") String owner_name,
                                   @Field("mobile_number") String mobile_number,
                                   @Field("request_send_date") String request_send_date);

    // add business
    @GET("API_Vendor/view_businesstype.php")
    Call<ResponseBody> getbusinessType();

    @FormUrlEncoded
    @POST("API_Vendor/view_business_category.php")
    Call<ResponseBody> getcatebusinessType(@Field("fld_business_id") String fld_business_id);

    @FormUrlEncoded
    @POST("API_Vendor/view_business_subcategory.php")
    Call<ResponseBody> getsub_cate_businessType(@Field("fld_business_category_id") String fld_business_category_id);

    @FormUrlEncoded
    @POST("API_Vendor/view_business_sub_subcategory.php")
    Call<ResponseBody> getsub_sub_cate_businessType(@Field("fld_business_subcategory_id") String fld_business_subcategory_id);

    @GET("API_Vendor/view_country.php")
    Call<ResponseBody> getcount();

    @FormUrlEncoded
    @POST("API_Vendor/view_state.php")
    Call<ResponseBody> getstate(@Field("fld_country_id") String fld_country_id);

    @FormUrlEncoded
    @POST("API_Vendor/view_district.php")
    Call<ResponseBody> getDistrict(@Field("fld_country_id") String fld_country_id,
                                   @Field("fld_state_id") String fld_state_id);

    @FormUrlEncoded
    @POST("API_Vendor/view_taluka.php")
    Call<ResponseBody> gettaluka(@Field("fld_district_id") String fld_district_id);

    @FormUrlEncoded
    @POST("API_Vendor/view_city.php")
    Call<ResponseBody> getvillage(@Field("fld_country_id") String fld_country_id,
                                  @Field("fld_state_id") String fld_state_id,
                                  @Field("fld_district_id") String fld_district_id,
                                  @Field("fld_taluka_id") String fld_taluka_id);

    //notification
    @FormUrlEncoded
    @POST("API_Vendor/notification_vendor_list.php")
    Call<ResponseBody> getnofication(@Field("vendor_id") String vendor_id);

    @FormUrlEncoded
    @POST("API_Vendor/view_area.php")
    Call<ResponseBody> getArea(@Field("fld_city_id") String fld_city_id);

    @FormUrlEncoded
    @POST("API_Vendor/view_landmark.php")
    Call<ResponseBody> getLandmark(@Field("fld_city_id") String fld_city_id,
                                   @Field("fld_area_id") String fld_area_id);



    // add business

    @Multipart
    @POST("API_Vendor/add_business_information.php") Call<ResponseBody> getadd_business(
            @Part("business_info_name") @Nullable RequestBody business_info_name,
            @Part("owner_name") @Nullable RequestBody owner_name,
            @Part("fld_business_id") @Nullable RequestBody fld_business_id,
            @Part("fld_business_category_id") @Nullable RequestBody fld_business_category_id,
            @Part("fld_business_subcategory_id") @Nullable RequestBody fld_business_subcategory_id,
            @Part("fld_business_subsubcategory_id") @Nullable RequestBody fld_business_subsubcategory_id,
            @Part("business_description") @Nullable RequestBody business_description,
            @Part("address") @Nullable RequestBody address,
            @Part("fld_country_id") @Nullable RequestBody fld_country_id,
            @Part("fld_state_id") @Nullable RequestBody fld_state_id,
            @Part("fld_district_id") @Nullable RequestBody fld_district_id,
            @Part("fld_taluka_id") @Nullable RequestBody fld_taluka_id,
            @Part("fld_city_id") @Nullable RequestBody fld_city_id,
            @Part("fld_area_id") @Nullable RequestBody fld_area_id,
            @Part("fld_landmark_id") @Nullable RequestBody fld_landmark_id,
            @Part("building_name") @Nullable RequestBody building_name,
            @Part("pincode") @Nullable RequestBody pincode,
            @Part("contact_person") @Nullable RequestBody contact_person,
            @Part("designation") @Nullable RequestBody designation,
            @Part("mobile_no") @Nullable RequestBody mobile_no,
            @Part("telephone_no") @Nullable RequestBody telephone_no,
            @Part("business_website") @Nullable RequestBody business_website,
            @Part("business_email") @Nullable RequestBody business_email,
            @Part("facebook_link") @Nullable RequestBody facebook_link,
            @Part("twitter_link") @Nullable RequestBody twitter_link,
            @Part("youtube_link") @Nullable RequestBody youtube_link,
            // @Part("working_time") @Nullable RequestBody working_time,
            @Part("google_map_link") @Nullable RequestBody google_map_link,
            @Part("working_days") @Nullable RequestBody working_days,
            @Part("interval_time") @Nullable RequestBody interval_time,
            @Part("no_of_people") @Nullable RequestBody no_of_people,
           // @Part("payment_mode") @Nullable RequestBody payment_mode,
            @Part("establishment_year") @Nullable RequestBody establishment_year,
            @Part("annual_turnover") @Nullable RequestBody annual_turnover,
            @Part("number_of_employees") @Nullable RequestBody number_of_employees,
            @Part("certification") @Nullable RequestBody certification,
            @Part("vendor_id") @Nullable RequestBody vendor_id,
            @Part("fld_pre_booking") @Nullable RequestBody fld_pre_booking,
            @Part @Nullable MultipartBody.Part file);


    //add package /packageAmt
    @GET("API_Vendor/view_package.php")
    Call<ResponseBody> getpakage();


    //add business photo
    @Multipart
    @POST("API_Vendor/add_business_photos.php")
    Call<ResponseBody> upload(@Part("business_info_id") RequestBody  business_info_id,
            @Part MultipartBody.Part business_photo);


    //updateActivity
    @Multipart
    @POST("API_Vendor/update_vendor.php")
    Call<ResponseBody> update(@Part("vendor_id") @Nullable RequestBody vendor_id,
                              @Part("company_name") @Nullable RequestBody company_name,
                              @Part("owner_name") @Nullable RequestBody owner_name,
                              @Part("mobile") @Nullable RequestBody mobile,
                              @Part("email") @Nullable RequestBody email,
                              @Part("date_of_birth") @Nullable RequestBody date_of_birth,
                              @Part("gender") @Nullable RequestBody gender,
                              @Part("approve_date") @Nullable RequestBody approve_date,
                              @Part("end_date") @Nullable RequestBody end_date,
                              @Part @Nullable MultipartBody.Part file,
                              @Part @Nullable MultipartBody.Part file1,
                              @Part @Nullable MultipartBody.Part file2,
                              @Part @Nullable MultipartBody.Part file3);

    //View Details Activity
    @FormUrlEncoded
    @POST("API_Vendor/view_businessphotos.php")
    Call<ResponseBody> getphotos(@Field("business_info_id") String business_info_id);

    @FormUrlEncoded
    @POST("API_Vendor/view_business_varient.php")
    Call<ResponseBody> getbusi_Data(@Field("business_info_id") String business_info_id);


    @FormUrlEncoded
    @POST("API_Vendor/add_business_varient.php")
    Call<ResponseBody> add_varient_businss(@Field("business_info_id") String business_info_id,
                                           @Field("fld_business_details_name") String fld_business_details_name,
                                           @Field("fld_business_details_size") String fld_business_details_size,
                                           @Field("fld_business_details_rate") String fld_business_details_rate);

    @FormUrlEncoded
    @POST("API_Vendor/add_business_working_time.php")
    Call<ResponseBody> add_business_time(@Field("business_info_id") String business_info_id,
                                         @Field("fld_working_open_time") String fld_working_open_time,
                                         @Field("fld_working_close_time") String fld_working_close_time);

    @FormUrlEncoded
    @POST("API_Vendor/update_business_time.php")
    Call<ResponseBody> update_business_time(@Field("fld_business_time_id") String fld_business_time_id,
                                            @Field("fld_working_open_time") String fld_working_open_time,
                                            @Field("fld_working_close_time") String fld_working_close_time);

    @FormUrlEncoded
    @POST("API_Vendor/view_business_working_time.php")
    Call<ResponseBody> View_business_time(@Field("business_info_id") String business_info_id);

    @FormUrlEncoded
    @POST("API_Vendor/delete_business_time_variant.php")
    Call<ResponseBody> delete_business_time(@Field("fld_business_time_id") String fld_business_time_id);

    @FormUrlEncoded
    @POST("API_Vendor/delete_business_information.php")
    Call<ResponseBody> delete_business(@Field("business_info_id") String business_info_id);

    @FormUrlEncoded
    @POST("API_Vendor/delete_business_variant.php")
    Call<ResponseBody> delete_Varient(@Field("fld_business_details_id") String fld_business_details_id);

    @FormUrlEncoded
    @POST("API_Vendor/update_business_variant.php")
    Call<ResponseBody> update_Varient(@Field("fld_business_details_id") String fld_business_details_id,
                                      @Field("fld_business_details_name") String fld_business_details_name,
                                      @Field("fld_business_details_size") String fld_business_details_size,
                                      @Field("fld_business_details_rate") String fld_business_details_rate);


    //update Business

    @Multipart
    @POST("API_Vendor/update_business_information.php")
    Call<ResponseBody> update_my_business(
            @Part("business_info_id") @Nullable RequestBody business_info_id,
            @Part("business_info_name") @Nullable RequestBody business_info_name,
            @Part("owner_name") @Nullable RequestBody owner_name,
            @Part("fld_business_id") @Nullable RequestBody fld_business_id,
            @Part("fld_business_category_id") @Nullable RequestBody fld_business_category_id,
            @Part("fld_business_subcategory_id") @Nullable RequestBody fld_business_subcategory_id,
            @Part("fld_business_subsubcategory_id") @Nullable RequestBody fld_business_subsubcategory_id,
            @Part("business_description") @Nullable RequestBody business_description,
            @Part("address") @Nullable RequestBody address,
            @Part("fld_country_id") @Nullable RequestBody fld_country_id,
            @Part("fld_state_id") @Nullable RequestBody fld_state_id,
            @Part("fld_district_id") @Nullable RequestBody fld_district_id,
            @Part("fld_taluka_id") @Nullable RequestBody fld_taluka_id,
            @Part("fld_city_id") @Nullable RequestBody fld_city_id,
            @Part("fld_area_id") @Nullable RequestBody fld_area_id,
            @Part("fld_landmark_id") @Nullable RequestBody fld_landmark_id,
            @Part("building_name") @Nullable RequestBody building_name,
            @Part("pincode") @Nullable RequestBody pincode,
            @Part("contact_person") @Nullable RequestBody contact_person,
            @Part("designation") @Nullable RequestBody designation,
            @Part("mobile_no") @Nullable RequestBody mobile_no,
            @Part("telephone_no") @Nullable RequestBody telephone_no,
            @Part("business_website") @Nullable RequestBody business_website,
            @Part("business_email") @Nullable RequestBody business_email,
            @Part("facebook_link") @Nullable RequestBody facebook_link,
            @Part("twitter_link") @Nullable RequestBody twitter_link,
            @Part("youtube_link") @Nullable RequestBody youtube_link,
            // @Part("working_time") @Nullable RequestBody working_time,
            @Part("google_map_link") @Nullable RequestBody google_map_link,
            @Part("working_days") @Nullable RequestBody working_days,
            @Part("interval_time") @Nullable RequestBody interval_time,
            @Part("no_of_people") @Nullable RequestBody no_of_people,
//            @Part("payment_mode") @Nullable RequestBody payment_mode,
            @Part("establishment_year") @Nullable RequestBody establishment_year,
            @Part("annual_turnover") @Nullable RequestBody annual_turnover,
            @Part("number_of_employees") @Nullable RequestBody number_of_employees,
            @Part("certification") @Nullable RequestBody certification,
            @Part("vendor_id") @Nullable RequestBody vendor_id,
            @Part("fld_pre_booking") @Nullable RequestBody fld_pre_booking,
            @Part @Nullable MultipartBody.Part file);

    @GET("API_Vendor/current_date.php")
    Call<ResponseBody> getdate();

    @FormUrlEncoded
    @POST("API_Vendor/business_pending_status.php")
    Call<ResponseBody> getappintment_history_List(@Field("vendor_id") String vendor_id,
                                                  @Field("business_info_id") String business_info_id,
                                                  @Field("fld_business_details_id") String fld_business_details_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_accepted_status.php")
    Call<ResponseBody> getaccept_history_List(@Field("vendor_id") String vendor_id,
                                              @Field("business_info_id") String business_info_id,
                                              @Field("fld_business_details_id") String fld_business_details_id);


    @FormUrlEncoded
    @POST("API_Vendor/business_completed_status.php")
    Call<ResponseBody> getcompleted_history_List(@Field("vendor_id") String vendor_id,
                                                 @Field("business_info_id") String business_info_id,
                                                 @Field("fld_business_details_id") String fld_business_details_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_rejected_status.php")
    Call<ResponseBody> getrejected_history_List(@Field("vendor_id") String vendor_id,
                                                @Field("business_info_id") String business_info_id,
                                                @Field("fld_business_details_id") String fld_business_details_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_status.php")
    Call<ResponseBody> getchangestaus(@Field("vendor_id") String vendor_id,
                                      @Field("business_info_id") String business_info_id,
                                      @Field("fld_business_details_id") String fld_business_details_id,
                                      @Field("status") String status,
                                      @Field("fld_user_issued_servicesApp") String fld_user_issued_servicesApp);

    @FormUrlEncoded
    @POST("API_Vendor/invoice.php")
    Call<ResponseBody> getInVoice(@Field("vendor_id") String vendor_id,
                                  @Field("payment_status") String payment_status);

    @Multipart
    @POST("API_Vendor/update_vendor.php")
    Call<ResponseBody> UpdateProfile(@Part("vendor_id") @Nullable RequestBody vendor_id,
                                     @Part("company_name") @Nullable RequestBody company_name,
                                     @Part("owner_name") @Nullable RequestBody owner_name,
                                     @Part("mobile") @Nullable RequestBody mobile,
                                     @Part("email") @Nullable RequestBody email,
                                     @Part("date_of_birth") @Nullable RequestBody date_of_birth,
                                     @Part("gender") @Nullable RequestBody gender,
                                     @Part MultipartBody.Part fld_vendor_photo);

    @FormUrlEncoded
    @POST("API_Vendor/update_vendor_data.php")
    Call<ResponseBody> UpdateProfilewithoutimg(@Field("vendor_id") String vendor_id,
                                               @Field("company_name") String company_name,
                                               @Field("owner_name") String owner_name,
                                               @Field("mobile") String mobile,
                                               @Field("email") String email,
                                               @Field("date_of_birth") String date_of_birth,
                                               @Field("gender") String gender,
                                               @Field("fld_vendor_photo") String fld_vendor_photo);


    @Headers
            ({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("API_Vendor/change_password.php")
    Call<ResponseBody> postRawJSON(@Body JsonObject locationPost);

    @FormUrlEncoded
    @POST("API_Vendor/business_pending_status.php")
    Call<ResponseBody> userRequest(@Field("vendor_id") String vendor_id,
                                  @Field("business_info_id") String business_info_id,
                                  @Field("fld_business_id") String fld_business_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_accepted_status.php")
    Call<ResponseBody> acceptRequest(@Field("vendor_id") String vendor_id,
                                   @Field("business_info_id") String business_info_id,
                                     @Field("fld_business_id") String fld_business_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_rejected_status.php")
    Call<ResponseBody> rejectRequest(@Field("vendor_id") String vendor_id,
                                     @Field("business_info_id") String business_info_id,
                                     @Field("fld_business_id") String fld_business_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_completed_status.php")
    Call<ResponseBody> completeRequest(@Field("vendor_id") String vendor_id,
                                     @Field("business_info_id") String business_info_id,
                                       @Field("fld_business_id") String fld_business_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_cancel_status.php")
    Call<ResponseBody> cancelRequest(@Field("vendor_id") String vendor_id,
                                     @Field("business_info_id") String business_info_id,
                                     @Field("fld_business_id") String fld_business_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_disapproved_status.php")
    Call<ResponseBody>disapprovedRequest(@Field("vendor_id") String vendor_id,
                                     @Field("business_info_id") String business_info_id,
                                         @Field("fld_business_id") String fld_business_id);

    @FormUrlEncoded
    @POST("API_Vendor/business_status.php")
    Call<ResponseBody> status(@Field("vendor_id") String vendor_id,
                              @Field("business_info_id") String business_info_id,
                              @Field("fld_user_issued_servicesApp") String fld_user_issued_servicesApp,
                              @Field("fld_service_issuedorreturned") String fld_service_issuedorreturned,
                              @Field("status") String status);


}

