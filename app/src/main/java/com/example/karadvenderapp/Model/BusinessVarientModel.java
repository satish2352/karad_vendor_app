package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class BusinessVarientModel {
    private String fld_business_details_id;
    private String business_info_id;
    private String fld_business_details_name;
    private String fld_business_details_size;
    private String fld_business_details_rate;

    public BusinessVarientModel(JSONObject jsonObject) throws JSONException {
        this.fld_business_details_id = jsonObject.getString("fld_business_details_id");
        this.business_info_id = jsonObject.getString("business_info_id");
        this.fld_business_details_name = jsonObject.getString("fld_business_details_name");
        this.fld_business_details_size = jsonObject.getString("fld_business_details_size");
        this.fld_business_details_rate = jsonObject.getString("fld_business_details_rate");
    }


    public String getFld_business_details_rate() {
        return fld_business_details_rate;
    }

    public void setFld_business_details_rate(String fld_business_details_rate) {
        this.fld_business_details_rate = fld_business_details_rate;
    }

    public String getFld_business_details_id() {
        return fld_business_details_id;
    }

    public void setFld_business_details_id(String fld_business_details_id) {
        this.fld_business_details_id = fld_business_details_id;
    }

    public String getBusiness_info_id() {
        return business_info_id;
    }

    public void setBusiness_info_id(String business_info_id) {
        this.business_info_id = business_info_id;
    }

    public String getFld_business_details_name() {
        return fld_business_details_name;
    }

    public void setFld_business_details_name(String fld_business_details_name) {
        this.fld_business_details_name = fld_business_details_name;
    }

    public String getFld_business_details_size() {
        return fld_business_details_size;
    }

    public void setFld_business_details_size(String fld_business_details_size) {
        this.fld_business_details_size = fld_business_details_size;
    }
}