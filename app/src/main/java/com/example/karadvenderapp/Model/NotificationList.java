package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationList
{
    String notification_id;
    String business_info_id;
    String business_info_name;
    String fld_business_id;
    String business_image;
    String user_id;
    String user_name;
    String user_mobile;
    String user_type;
    String note_text;
    String readnotification;
    String created_at;
    String updated_at;

    public NotificationList() {
    }

    public NotificationList(JSONObject jsonObject) {
        try {
            this.notification_id = jsonObject.getString("notification_id");
            this.business_info_id = jsonObject.getString("business_info_id");
            this.business_info_name = jsonObject.getString("business_info_name");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.business_image = jsonObject.getString("business_image");
            this.user_id = jsonObject.getString("user_id");
            this.user_name = jsonObject.getString("user_name");
            this.user_mobile = jsonObject.getString("user_mobile");
            this.user_type = jsonObject.getString("user_type");
            this.note_text = jsonObject.getString("note_text");
            this.readnotification = jsonObject.getString("readnotification");
            this.created_at = jsonObject.getString("created_at");
            this.updated_at = jsonObject.getString("updated_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
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

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getBusiness_image() {
        return business_image;
    }

    public void setBusiness_image(String business_image) {
        this.business_image = business_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getNote_text() {
        return note_text;
    }

    public void setNote_text(String note_text) {
        this.note_text = note_text;
    }

    public String getReadnotification() {
        return readnotification;
    }

    public void setReadnotification(String readnotification) {
        this.readnotification = readnotification;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
