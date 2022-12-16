package com.example.karadvenderapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class CountryList {
    private String Country_Id;
    private String Country_Name;




    public CountryList(JSONObject jsonObject) {
        try {

            this.Country_Id = jsonObject.getString("fld_country_id");
            this.Country_Name = jsonObject.getString("fld_country_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.Country_Name;            // What to display in the Spinner list.
    }
    public CountryList(String Country_Name) {
        this.Country_Name = Country_Name;
    }

    public String getCountry_Id() {
        return Country_Id;
    }

    public void setCountry_Id(String country_Id) {
        Country_Id = country_Id;
    }

    public String getCountry_Name() {
        return Country_Name;
    }

    public void setCountry_Name(String country_Name) {
        Country_Name = country_Name;
    }
}
