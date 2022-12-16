package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class InvoiceList {

    private String id;
    private String fld_package_id;
    private String fld_package_name;
    private String fld_package_amount;
    private String fld_validity_in_days_number;
    private String owner_name;
    private String start_date;
    private String end_date;
    private String mobile;
    private String email;
    private String fld_business_id;
    private String fld_business_name;
    private String business_info_name;
    private String fld_business_category_id;
    private String fld_business_category_name;
    private String created_at;
    private String fld_product_type_id;
    private String fld_product_type_name;
    private String razorpay_stageOfPayment;

    public InvoiceList(JSONObject jsonObject) throws JSONException {
        this.id=jsonObject.getString("id");
        this.fld_package_id=jsonObject.getString("fld_package_id");
        this.fld_package_name=jsonObject.getString("fld_package_name");
        this.fld_package_amount=jsonObject.getString("fld_package_amount");
        this.fld_validity_in_days_number=jsonObject.getString("fld_validity_in_days_number");
        this.owner_name=jsonObject.getString("owner_name");
        this.start_date=jsonObject.getString("start_date");
        this.end_date=jsonObject.getString("end_date");
        this.mobile=jsonObject.getString("mobile");
        this.email=jsonObject.getString("email");
        this.fld_business_id=jsonObject.getString("fld_business_id");
        this.fld_business_name=jsonObject.getString("fld_business_name");
        this.business_info_name=jsonObject.getString("business_info_name");
        this.fld_business_category_id=jsonObject.getString("fld_business_category_id");
        this.fld_business_category_name=jsonObject.getString("fld_business_category_name");
        this.created_at=jsonObject.getString("created_at");
        this.fld_product_type_id=jsonObject.getString("fld_product_type_id");
        this.fld_product_type_name=jsonObject.getString("fld_product_type_name");
        this.razorpay_stageOfPayment=jsonObject.getString("razorpay_stageOfPayment");

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFld_package_id() {
        return fld_package_id;
    }

    public void setFld_package_id(String fld_package_id) {
        this.fld_package_id = fld_package_id;
    }

    public String getFld_package_name() {
        return fld_package_name;
    }

    public void setFld_package_name(String fld_package_name) {
        this.fld_package_name = fld_package_name;
    }

    public String getFld_package_amount() {
        return fld_package_amount;
    }

    public void setFld_package_amount(String fld_package_amount) {
        this.fld_package_amount = fld_package_amount;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRazorpay_stageOfPayment() {
        return razorpay_stageOfPayment;
    }

    public void setRazorpay_stageOfPayment(String razorpay_stageOfPayment) {
        this.razorpay_stageOfPayment = razorpay_stageOfPayment;
    }

    public String getFld_validity_in_days_number() {
        return fld_validity_in_days_number;
    }

    public void setFld_validity_in_days_number(String fld_validity_in_days_number) {
        this.fld_validity_in_days_number = fld_validity_in_days_number;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getBusiness_info_name() {
        return business_info_name;
    }

    public void setBusiness_info_name(String business_info_name) {
        this.business_info_name = business_info_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
