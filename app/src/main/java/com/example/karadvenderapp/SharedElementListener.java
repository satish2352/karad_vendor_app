package com.example.karadvenderapp;

import android.view.View;

import com.example.karadvenderapp.Model.BusinessModel;

import java.util.List;

public interface SharedElementListener
{
    void SharedElmentCallback(int position, View view, View view2, List<BusinessModel> lists);
}

