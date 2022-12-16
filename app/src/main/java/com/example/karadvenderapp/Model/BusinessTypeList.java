package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class BusinessTypeList {
    private String business_id;
    private String business_name;



    public BusinessTypeList(JSONObject jsonObject) {
        try {
            this.business_id = jsonObject.getString("fld_business_id");
            this.business_name = jsonObject.getString("fld_business_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.business_name;            // What to display in the Spinner list.
    }
    public BusinessTypeList(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }
}
