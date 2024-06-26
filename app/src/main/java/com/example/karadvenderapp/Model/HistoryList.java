package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class HistoryList
{
    private String business_info_name;
    private String business_info_id;
    private String fld_business_details_id;
    private String fld_business_id;
    private String user_name;
    private String user_mobile;
    private String user_email;
    private String fld_actual_booking_slot;
    private String fld_service_requested_date;
    private String fld_user_issued_servicesApp;



    public HistoryList(JSONObject jsonObject) {
        try {
            this.fld_user_issued_servicesApp = jsonObject.getString("fld_user_issued_servicesApp");
            this.business_info_id = jsonObject.getString("business_info_id");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.fld_business_details_id = jsonObject.getString("fld_business_details_id");
            this.business_info_name = jsonObject.getString("business_info_name");
            this.user_name = jsonObject.getString("user_name");
            this.user_mobile = jsonObject.getString("user_mobile");
            this.user_email = jsonObject.getString("user_email");
            this.fld_actual_booking_slot = jsonObject.getString("fld_actual_booking_slot");
            this.fld_service_requested_date = jsonObject.getString("fld_service_requested_date");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getFld_user_issued_servicesApp() {
        return fld_user_issued_servicesApp;
    }

    public void setFld_user_issued_servicesApp(String fld_user_issued_servicesApp) {
        this.fld_user_issued_servicesApp = fld_user_issued_servicesApp;
    }

    public String getFld_business_details_id() {
        return fld_business_details_id;
    }

    public void setFld_business_details_id(String fld_business_details_id) {
        this.fld_business_details_id = fld_business_details_id;
    }

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getBusiness_info_id() {
        return business_info_id;
    }

    public void setBusiness_info_id(String business_info_id) {
        this.business_info_id = business_info_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getBusiness_info_name() {
        return business_info_name;
    }

    public void setBusiness_info_name(String business_info_name) {
        this.business_info_name = business_info_name;
    }

    public String getFld_actual_booking_slot() {
        return fld_actual_booking_slot;
    }

    public void setFld_actual_booking_slot(String fld_actual_booking_slot) {
        this.fld_actual_booking_slot = fld_actual_booking_slot;
    }

    public String getFld_service_requested_date() {
        return fld_service_requested_date;
    }

    public void setFld_service_requested_date(String fld_service_requested_date) {
        this.fld_service_requested_date = fld_service_requested_date;
    }
}
