package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Business_SubCategoryList {
    private String fld_business_id;
    private String fld_business_category_id;
    private String fld_business_subcategory_id;
    private String fld_business_subcategory_name;



    public Business_SubCategoryList(JSONObject jsonObject) {
        try {
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.fld_business_category_id = jsonObject.getString("fld_business_category_id");
            this.fld_business_subcategory_id = jsonObject.getString("fld_business_subcategory_id");
            this.fld_business_subcategory_name = jsonObject.getString("fld_business_subcategory_name");
           } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.fld_business_subcategory_name;            // What to display in the Spinner list.
    }
    public Business_SubCategoryList(String fld_business_subcategory_name) {
        this.fld_business_subcategory_name = fld_business_subcategory_name;
    }

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getFld_business_category_id() {
        return fld_business_category_id;
    }

    public void setFld_business_category_id(String fld_business_category_id) {
        this.fld_business_category_id = fld_business_category_id;
    }

    public String getFld_business_subcategory_id() {
        return fld_business_subcategory_id;
    }

    public void setFld_business_subcategory_id(String fld_business_subcategory_id) {
        this.fld_business_subcategory_id = fld_business_subcategory_id;
    }

    public String getFld_business_subcategory_name() {
        return fld_business_subcategory_name;
    }

    public void setFld_business_subcategory_name(String fld_business_subcategory_name) {
        this.fld_business_subcategory_name = fld_business_subcategory_name;
    }
}
