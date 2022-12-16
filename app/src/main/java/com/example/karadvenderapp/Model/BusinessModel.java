package com.example.karadvenderapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class BusinessModel {
    private String business_info_id;
    private String business_info_name;
    private String owner_name;
    private String fld_business_id;
    private String fld_business_name;
    private String fld_business_category_id;
    private String fld_business_category_name;
    private String fld_business_subcategory_id;
    private String fld_business_subcategory_name;
    private String fld_business_subsubcategory_id;
    private String fld_business_subsubcategory_name;
    private String business_description;
    private String address;
    private String fld_country_id;
    private String fld_country_name;
    private String fld_state_id;
    private String fld_state_name;
    private String fld_district_id;
    private String fld_district_name;
    private String fld_taluka_id;
    private String fld_taluka_name;
    private String fld_city_id;
    private String fld_city_name;
    private String fld_area_id;
    private String fld_area_name;
    private String fld_landmark_id;
    private String fld_landmark_name;
    private String building_name;
    private String pincode;
    private String contact_person;
    private String designation;
    private String mobile_no;
    private String telephone_no;
    private String business_website;
    private String business_email;
    private String facebook_link;
    private String twitter_link;
    private String youtube_link;
    private String google_map_link;
    private String working_days;
    private String start_working_time;
    private String end_working_time;
    private String interval_time;
    private String no_of_people;
    private String payment_mode;
    private String establishment_year;
    private String annual_turnover;
    private String number_of_employees;
    private String certification;
    private String business_image;
    private String start_date;
    private String end_date;


    public BusinessModel(JSONObject jsonObject) throws JSONException {

        this.business_info_id = jsonObject.getString("business_info_id");
        this.business_info_name = jsonObject.getString("business_info_name");
        this.owner_name = jsonObject.getString("owner_name");
        this.fld_business_id = jsonObject.getString("fld_business_id");
        this.fld_business_name = jsonObject.getString("fld_business_name");
        this.fld_business_category_id = jsonObject.getString("fld_business_category_id");
        this.fld_business_category_name = jsonObject.getString("fld_business_category_name");
        this.fld_business_subcategory_id = jsonObject.getString("fld_business_subcategory_id");
        this.fld_business_subcategory_name = jsonObject.getString("fld_business_subcategory_name");
        this.fld_business_subsubcategory_id = jsonObject.getString("fld_business_subsubcategory_id");
        this.fld_business_subsubcategory_name = jsonObject.getString("fld_business_subsubcategory_name");
        this.business_description = jsonObject.getString("business_description");
        this.address = jsonObject.getString("address");
        this.fld_country_id = jsonObject.getString("fld_country_id");
        this.fld_country_name = jsonObject.getString("fld_country_name");
        this.fld_state_id = jsonObject.getString("fld_state_id");
        this.fld_state_name = jsonObject.getString("fld_state_name");
        this.fld_district_id = jsonObject.getString("fld_district_id");
        this.fld_district_name = jsonObject.getString("fld_district_name");
        this.fld_taluka_id = jsonObject.getString("fld_taluka_id");
        this.fld_taluka_name = jsonObject.getString("fld_taluka_name");
        this.fld_city_id = jsonObject.getString("fld_city_id");
        this.fld_city_name = jsonObject.getString("fld_city_name");
        this.fld_area_id = jsonObject.getString("fld_area_id");
        this.fld_area_name = jsonObject.getString("fld_area_name");
        this.fld_landmark_id = jsonObject.getString("fld_landmark_id");
        this.fld_landmark_name = jsonObject.getString("fld_landmark_name");
        this.building_name = jsonObject.getString("building_name");
        this.pincode = jsonObject.getString("pincode");
        this.contact_person = jsonObject.getString("contact_person");
        this.designation = jsonObject.getString("designation");
        this.mobile_no = jsonObject.getString("mobile_no");
        this.telephone_no = jsonObject.getString("telephone_no");
        this.business_website = jsonObject.getString("business_website");
        this.business_email = jsonObject.getString("business_email");
        this.facebook_link = jsonObject.getString("facebook_link");
        this.twitter_link = jsonObject.getString("twitter_link");
        this.youtube_link = jsonObject.getString("youtube_link");
        this.google_map_link = jsonObject.getString("google_map_link");
        this.working_days = jsonObject.getString("working_days");
//        this.start_working_time = jsonObject.getString("start_working_time");
//        this.end_working_time = jsonObject.getString("end_working_time");
        this.interval_time = jsonObject.getString("interval_time");
        this.no_of_people = jsonObject.getString("no_of_people");
        this.payment_mode = jsonObject.getString("payment_mode");
        this.establishment_year = jsonObject.getString("establishment_year");
        this.annual_turnover = jsonObject.getString("annual_turnover");
        this.number_of_employees = jsonObject.getString("number_of_employees");
        this.certification = jsonObject.getString("certification");
        this.business_image = jsonObject.getString("business_image");
        this.start_date = jsonObject.getString("start_date");
        this.end_date = jsonObject.getString("end_date");



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

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
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

    public String getFld_business_subsubcategory_id() {
        return fld_business_subsubcategory_id;
    }

    public void setFld_business_subsubcategory_id(String fld_business_subsubcategory_id) {
        this.fld_business_subsubcategory_id = fld_business_subsubcategory_id;
    }

    public String getFld_business_subsubcategory_name() {
        return fld_business_subsubcategory_name;
    }

    public void setFld_business_subsubcategory_name(String fld_business_subsubcategory_name) {
        this.fld_business_subsubcategory_name = fld_business_subsubcategory_name;
    }

    public String getBusiness_description() {
        return business_description;
    }

    public void setBusiness_description(String business_description) {
        this.business_description = business_description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFld_country_id() {
        return fld_country_id;
    }

    public void setFld_country_id(String fld_country_id) {
        this.fld_country_id = fld_country_id;
    }

    public String getFld_country_name() {
        return fld_country_name;
    }

    public void setFld_country_name(String fld_country_name) {
        this.fld_country_name = fld_country_name;
    }

    public String getFld_state_id() {
        return fld_state_id;
    }

    public void setFld_state_id(String fld_state_id) {
        this.fld_state_id = fld_state_id;
    }

    public String getFld_state_name() {
        return fld_state_name;
    }

    public void setFld_state_name(String fld_state_name) {
        this.fld_state_name = fld_state_name;
    }

    public String getFld_district_id() {
        return fld_district_id;
    }

    public void setFld_district_id(String fld_district_id) {
        this.fld_district_id = fld_district_id;
    }

    public String getFld_district_name() {
        return fld_district_name;
    }

    public void setFld_district_name(String fld_district_name) {
        this.fld_district_name = fld_district_name;
    }

    public String getFld_taluka_id() {
        return fld_taluka_id;
    }

    public void setFld_taluka_id(String fld_taluka_id) {
        this.fld_taluka_id = fld_taluka_id;
    }

    public String getFld_taluka_name() {
        return fld_taluka_name;
    }

    public void setFld_taluka_name(String fld_taluka_name) {
        this.fld_taluka_name = fld_taluka_name;
    }

    public String getFld_city_id() {
        return fld_city_id;
    }

    public void setFld_city_id(String fld_city_id) {
        this.fld_city_id = fld_city_id;
    }

    public String getFld_city_name() {
        return fld_city_name;
    }

    public void setFld_city_name(String fld_city_name) {
        this.fld_city_name = fld_city_name;
    }

    public String getFld_area_id() {
        return fld_area_id;
    }

    public void setFld_area_id(String fld_area_id) {
        this.fld_area_id = fld_area_id;
    }

    public String getFld_area_name() {
        return fld_area_name;
    }

    public void setFld_area_name(String fld_area_name) {
        this.fld_area_name = fld_area_name;
    }

    public String getFld_landmark_id() {
        return fld_landmark_id;
    }

    public void setFld_landmark_id(String fld_landmark_id) {
        this.fld_landmark_id = fld_landmark_id;
    }

    public String getFld_landmark_name() {
        return fld_landmark_name;
    }

    public void setFld_landmark_name(String fld_landmark_name) {
        this.fld_landmark_name = fld_landmark_name;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getTelephone_no() {
        return telephone_no;
    }

    public void setTelephone_no(String telephone_no) {
        this.telephone_no = telephone_no;
    }

    public String getBusiness_website() {
        return business_website;
    }

    public void setBusiness_website(String business_website) {
        this.business_website = business_website;
    }

    public String getBusiness_email() {
        return business_email;
    }

    public void setBusiness_email(String business_email) {
        this.business_email = business_email;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getTwitter_link() {
        return twitter_link;
    }

    public void setTwitter_link(String twitter_link) {
        this.twitter_link = twitter_link;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }

    public String getGoogle_map_link() {
        return google_map_link;
    }

    public void setGoogle_map_link(String google_map_link) {
        this.google_map_link = google_map_link;
    }

    public String getWorking_days() {
        return working_days;
    }

    public void setWorking_days(String working_days) {
        this.working_days = working_days;
    }

    public String getStart_working_time() {
        return start_working_time;
    }

    public void setStart_working_time(String start_working_time) {
        this.start_working_time = start_working_time;
    }

    public String getEnd_working_time() {
        return end_working_time;
    }

    public void setEnd_working_time(String end_working_time) {
        this.end_working_time = end_working_time;
    }

    public String getInterval_time() {
        return interval_time;
    }

    public void setInterval_time(String interval_time) {
        this.interval_time = interval_time;
    }

    public String getNo_of_people() {
        return no_of_people;
    }

    public void setNo_of_people(String no_of_people) {
        this.no_of_people = no_of_people;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getEstablishment_year() {
        return establishment_year;
    }

    public void setEstablishment_year(String establishment_year) {
        this.establishment_year = establishment_year;
    }

    public String getAnnual_turnover() {
        return annual_turnover;
    }

    public void setAnnual_turnover(String annual_turnover) {
        this.annual_turnover = annual_turnover;
    }

    public String getNumber_of_employees() {
        return number_of_employees;
    }

    public void setNumber_of_employees(String number_of_employees) {
        this.number_of_employees = number_of_employees;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getBusiness_image() {
        return business_image;
    }

    public void setBusiness_image(String business_image) {
        this.business_image = business_image;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
}
