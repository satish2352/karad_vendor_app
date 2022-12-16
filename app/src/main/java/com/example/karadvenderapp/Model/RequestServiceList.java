package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestServiceList
{
    String fld_user_issued_servicesApp ;
    String business_info_id ;
    String business_info_name ;
    String fld_business_details_id ;
    String fld_business_details_name ;
    String fld_business_id;
    String fld_business_name;
    String user_name;
    String user_mobile;
    String user_email;
    String fld_service_requested_date;
    String allocatedService;
    String fld_service_issuedorreturned;


    public RequestServiceList()
    {
    }

    public RequestServiceList(JSONObject jsonObject)
    {
        try {
            this.fld_user_issued_servicesApp = jsonObject.getString("fld_user_issued_servicesApp");
            this.business_info_id = jsonObject.getString("business_info_id");
            this.business_info_name = jsonObject.getString("business_info_name");
            this.fld_business_details_id = jsonObject.getString("fld_business_details_id");
            this.fld_business_details_name = jsonObject.getString("fld_business_details_name");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.fld_business_name = jsonObject.getString("fld_business_name");
            this.user_name = jsonObject.getString("user_name");
            this.user_mobile = jsonObject.getString("user_mobile");
            this.user_email = jsonObject.getString("user_email");
            this.fld_service_requested_date = jsonObject.getString("fld_service_requested_date");
            this.allocatedService = jsonObject.getString("allocatedService");
            this.fld_service_issuedorreturned = jsonObject.getString("fld_service_issuedorreturned");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getAllocatedService()
    {
        return allocatedService;
    }

    public void setAllocatedService(String allocatedService) {
        this.allocatedService = allocatedService;
    }

    public String getFld_user_issued_servicesApp() {
        return fld_user_issued_servicesApp;
    }

    public void setFld_user_issued_servicesApp(String fld_user_issued_servicesApp) {
        this.fld_user_issued_servicesApp = fld_user_issued_servicesApp;
    }

    public String getBusiness_info_id() {
        return business_info_id;
    }

    public void setBusiness_info_id(String business_info_id) {
        this.business_info_id = business_info_id;
    }

    public String getBusiness_info_name() {
        return business_info_name;
    }

    public void setBusiness_info_name(String business_info_name) {
        this.business_info_name = business_info_name;
    }

    public String getFld_business_details_id() {
        return fld_business_details_id;
    }

    public void setFld_business_details_id(String fld_business_details_id) {
        this.fld_business_details_id = fld_business_details_id;
    }

    public String getFld_business_details_name() {
        return fld_business_details_name;
    }

    public void setFld_business_details_name(String fld_business_details_name) {
        this.fld_business_details_name = fld_business_details_name;
    }

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getFld_business_name() {
        return fld_business_name;
    }

    public void setFld_business_name(String fld_business_name) {
        this.fld_business_name = fld_business_name;
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

    public String getFld_service_requested_date() {
        return fld_service_requested_date;
    }

    public void setFld_service_requested_date(String fld_service_requested_date) {
        this.fld_service_requested_date = fld_service_requested_date;
    }

    public String getFld_service_issuedorreturned() {
        return fld_service_issuedorreturned;
    }

    public void setFld_service_issuedorreturned(String fld_service_issuedorreturned) {
        this.fld_service_issuedorreturned = fld_service_issuedorreturned;
    }
}
