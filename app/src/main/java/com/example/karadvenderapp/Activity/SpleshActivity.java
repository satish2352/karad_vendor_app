package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.karadvenderapp.MyLib.CheckNetwork;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.R;
import com.google.android.material.snackbar.Snackbar;

public class SpleshActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);

        if(CheckNetwork.isInternetAvailable(SpleshActivity.this)) //returns true if internet available
        {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    ConnectivityManager conMgr = (ConnectivityManager) getSystemService(SpleshActivity.this.CONNECTIVITY_SERVICE);

                    if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        if (Shared_Preferences.getPrefs(SpleshActivity.this, Constants.REG_ID) != null)
                            startActivity(new Intent(SpleshActivity.this, MainActivity.class));
                        else {

                            startActivity(new Intent(SpleshActivity.this, LoginActivity.class));
//                        overridePendingTransition(R.anim.slide_in_up,
//                                R.anim.slide_out_up);
                            finish();
                        }
                    }
                }


            }, SPLASH_TIME_OUT);
        }else
        {
            Snackbar.make(getWindow().getDecorView().getRootView(), "No Internet Connection,Please Connect The Internet or Wifi", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            check();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .setDuration(50000)
                    .show();

        }


    }

    private void check() {
        if (CheckNetwork.isInternetAvailable(SpleshActivity.this)) //returns true if internet available
        {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    ConnectivityManager conMgr = (ConnectivityManager) getSystemService(SpleshActivity.this.CONNECTIVITY_SERVICE);

                    if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        if (Shared_Preferences.getPrefs(SpleshActivity.this, Constants.REG_ID) != null)
                            startActivity(new Intent(SpleshActivity.this, MainActivity.class));
                        else

                            startActivity(new Intent(SpleshActivity.this, LoginActivity.class));
//                        overridePendingTransition(R.anim.slide_in_up,
//                                R.anim.slide_out_up);

                        finish();
                    }
                }


            }, SPLASH_TIME_OUT);
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            check();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .setDuration(50000)
                    .show();

        }
    }

}
