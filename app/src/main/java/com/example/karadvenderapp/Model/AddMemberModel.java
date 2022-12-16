package com.example.karadvenderapp.Model;

public class AddMemberModel {

    private String fld_business_details_name,fld_business_details_size,fld_business_details_rate;

    public AddMemberModel(String fld_business_details_name, String fld_business_details_size, String fld_business_details_rate) {
        this.fld_business_details_name = fld_business_details_name;
        this.fld_business_details_size = fld_business_details_size;
        this.fld_business_details_rate = fld_business_details_rate;


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

    public String getFld_business_details_rate() {
        return fld_business_details_rate;
    }

    public void setFld_business_details_rate(String fld_business_details_rate) {
        this.fld_business_details_rate = fld_business_details_rate;
    }
}
