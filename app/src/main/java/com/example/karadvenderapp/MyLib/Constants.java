package com.example.karadvenderapp.MyLib;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;

public class Constants {

    public static final String rs = "\u20B9 ";
    public static final String REG_ID = "reg_id";
    public static final String REG_EMAIL = "reg_email";
    public static final String REG_MOBILE = "reg_mobile";
    public static final String REG_NAME = "reg_fname";
    public static final String REG_COMPANY_NAME = "c_name";
    public static final String REG_IMAGE = "image";
    public static final String ProImg = "ProImg";
    public static final String REG_SERVERDATE = "date";

    public static final String BUSINESSINFOID = "getBusiness_info_id";

    public static final String BUID = "Business_info_id";

    public static final String Address="address";
    public static final String Address2="address2";
    public static final String Pass="pass";
    public static final String Lat="latitude";
    public static final String Long="longitude";

    public static final String CityName = "city";
    public static final String AreaName="area";
    public static final String StateName="state";
    public static final String PINCODE="pincode";
    public static final String AADHAR_No="aadhar_number";
    public static final String BARCODE = "barcode";
    public static final String NOTIFICATION_TOKEN = "token";
    public static final String REG_DOB = "dateof_brith";
    public static final String REG_GENDER = "gender";
    public static final String approve_date="approve_date";
    public static final String end_date="end_date";
    public static final String shop_act="shop_act";
    public static final String pan_card="pan_card";
    public static final String aadhar_card="aadhar_card";


    public static void EditTextAnim(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //float finalRadius = (float) Math.max((int) event.getX(), (int) event.getY()) * 1.2f;
                if (event.getAction() == MotionEvent.ACTION_DOWN && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !editText.hasFocus()) {

                    ViewAnimationUtils.createCircularReveal(editText,
                            editText.getWidth() / 2,
                            1,
                            0,
                            editText.getWidth() / 2).start();

                    //Log.d("my_tag", "onTouch: CenterX "+(int) event.getX()+"\nCenterY" +(int) event.getY()+"\nHeight "+editText.getHeight()+"\nWidth "+editText.getWidth());
                }
                return false;
            }
        });
    }

    public static String Reg_id(Context context) {
        String role_id = "0";
        if (Shared_Preferences.getPrefs(context, REG_ID) != null)
            role_id = Shared_Preferences.getPrefs(context, REG_ID);
        return role_id;
    }

    public static String Reg_mobile(Context context) {
        String reg_mobile = "0";
        if (Shared_Preferences.getPrefs(context, REG_MOBILE) != null)
            reg_mobile = Shared_Preferences.getPrefs(context, REG_MOBILE);
        return reg_mobile;
    }

    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            Log.e("imei", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }
}
