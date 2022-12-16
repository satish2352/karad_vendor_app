package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Business_categoryList {
    private String fld_business_category_id;
    private String fld_business_id;
    private String fld_business_category_name;
    private String fld_business_category_delete;
    private String fld_business_category_created_date;
    private String fld_business_category_modified_date;


    public Business_categoryList(JSONObject jsonObject) {
        try {
            this.fld_business_category_id = jsonObject.getString("fld_business_category_id");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.fld_business_category_name = jsonObject.getString("fld_business_category_name");
            this.fld_business_category_delete = jsonObject.getString("fld_business_category_delete");
            this.fld_business_category_created_date = jsonObject.getString("fld_business_category_created_date");
            this.fld_business_category_modified_date = jsonObject.getString("fld_business_category_modified_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.fld_business_category_name;            // What to display in the Spinner list.
    }
    public Business_categoryList(String fld_business_category_name) {
        this.fld_business_category_name = fld_business_category_name;
    }

    public String getFld_business_category_id() {
        return fld_business_category_id;
    }

    public void setFld_business_category_id(String fld_business_category_id) {
        this.fld_business_category_id = fld_business_category_id;
    }

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getFld_business_category_name() {
        return fld_business_category_name;
    }

    public void setFld_business_category_name(String fld_business_category_name) {
        this.fld_business_category_name = fld_business_category_name;
    }

    public String getFld_business_category_delete() {
        return fld_business_category_delete;
    }

    public void setFld_business_category_delete(String fld_business_category_delete) {
        this.fld_business_category_delete = fld_business_category_delete;
    }

    public String getFld_business_category_created_date() {
        return fld_business_category_created_date;
    }

    public void setFld_business_category_created_date(String fld_business_category_created_date) {
        this.fld_business_category_created_date = fld_business_category_created_date;
    }

    public String getFld_business_category_modified_date() {
        return fld_business_category_modified_date;
    }

    public void setFld_business_category_modified_date(String fld_business_category_modified_date) {
        this.fld_business_category_modified_date = fld_business_category_modified_date;
    }
}
