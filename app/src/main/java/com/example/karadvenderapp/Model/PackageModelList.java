package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class PackageModelList {

    private String fld_package_id;
    private String fld_product_type_id;
    private String fld_product_type_name;
    private String fld_package_name;
    private String fld_business_id;
    private String fld_business_name;
    private String fld_business_category_id;
    private String fld_business_category_name;
    private String fld_package_amount;
    private String fld_validity_in_days_number;



    public PackageModelList(JSONObject jsonObject) throws JSONException {
        this.fld_package_id=jsonObject.getString("fld_package_id");
        this.fld_product_type_id=jsonObject.getString("fld_product_type_id");
        this.fld_product_type_name=jsonObject.getString("fld_product_type_name");
        this.fld_package_name=jsonObject.getString("fld_package_name");
//        this.business_type = jsonObject.getString("business_type");
        this.fld_business_id=jsonObject.getString("fld_business_id");
        this.fld_business_name=jsonObject.getString("fld_business_name");
        this.fld_business_category_id=jsonObject.getString("fld_business_category_id");
        this.fld_business_category_name=jsonObject.getString("fld_business_category_name");
        this.fld_package_amount=jsonObject.getString("fld_package_amount");
        this.fld_validity_in_days_number=jsonObject.getString("fld_validity_in_days_number");

    }


    public String getFld_package_id() {
        return fld_package_id;
    }

    public void setFld_package_id(String fld_package_id) {
        this.fld_package_id = fld_package_id;
    }

    public String getFld_product_type_id() {
        return fld_product_type_id;
    }

    public void setFld_product_type_id(String fld_product_type_id) {
        this.fld_product_type_id = fld_product_type_id;
    }

    public String getFld_product_type_name() {
        return fld_product_type_name;
    }

    public void setFld_product_type_name(String fld_product_type_name) {
        this.fld_product_type_name = fld_product_type_name;
    }

    public String getFld_package_name() {
        return fld_package_name;
    }

    public void setFld_package_name(String fld_package_name) {
        this.fld_package_name = fld_package_name;
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

    public String getFld_business_category_id() {
        return fld_business_category_id;
    }

    public void setFld_business_category_id(String fld_business_category_id) {
        this.fld_business_category_id = fld_business_category_id;
    }

    public String getFld_business_category_name() {
        return fld_business_category_name;
    }

    public void setFld_business_category_name(String fld_business_category_name) {
        this.fld_business_category_name = fld_business_category_name;
    }

    public String getFld_package_amount() {
        return fld_package_amount;
    }

    public void setFld_package_amount(String fld_package_amount) {
        this.fld_package_amount = fld_package_amount;
    }

    public String getFld_validity_in_days_number() {
        return fld_validity_in_days_number;
    }

    public void setFld_validity_in_days_number(String fld_validity_in_days_number) {
        this.fld_validity_in_days_number = fld_validity_in_days_number;
    }
}
