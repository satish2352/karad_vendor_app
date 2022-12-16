package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class BusinessTime {
     private String fld_business_time_id;
     private String business_info_id;
     private String fld_working_open_time;
     private String fld_working_close_time;

    public BusinessTime(JSONObject jsonObject) throws JSONException {
        this.fld_business_time_id=jsonObject.getString("fld_business_time_id");
        this.business_info_id=jsonObject.getString("business_info_id");
        this.fld_working_open_time=jsonObject.getString("fld_working_open_time");
        this.fld_working_close_time=jsonObject.getString("fld_working_close_time");
    }


    public String getFld_business_time_id() {
        return fld_business_time_id;
    }

    public void setFld_business_time_id(String fld_business_time_id) {
        this.fld_business_time_id = fld_business_time_id;
    }

    public String getBusiness_info_id() {
        return business_info_id;
    }

    public void setBusiness_info_id(String business_info_id) {
        this.business_info_id = business_info_id;
    }

    public String getFld_working_open_time() {
        return fld_working_open_time;
    }

    public void setFld_working_open_time(String fld_working_open_time) {
        this.fld_working_open_time = fld_working_open_time;
    }

    public String getFld_working_close_time() {
        return fld_working_close_time;
    }

    public void setFld_working_close_time(String fld_working_close_time) {
        this.fld_working_close_time = fld_working_close_time;
    }
}
