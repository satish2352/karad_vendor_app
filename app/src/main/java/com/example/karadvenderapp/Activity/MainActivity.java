package com.example.karadvenderapp.Activity;

import static android.os.Build.VERSION_CODES.R;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karadvenderapp.Adapter.BusinessListAdapter;
import com.example.karadvenderapp.Model.BusinessModel;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, BusinessListAdapter.OnItemClickListener {
    private TableRow tr_about, tr_userprofile, tr_home, tr_invoice, tr_Settings, tr_update_profile, tr_change_pass,
            tr_report, tr_Logout,
            tr_request_delivery_center, tr_center_delivery, tr_request_for_centerto_user, tr_deliverd_user_to_center;
    ImageView iv_notification;
    private LinearLayout linear_report, linear_sett;
    private DrawerLayout drawer;
    private RecyclerView business_recyclerView;
    private List<BusinessModel> ModelList;
    private BusinessListAdapter mAdapter;
    FloatingActionButton fab, fab1, fab2;
    LinearLayout fabLayout1, fabLayout2;
    boolean isFABOpen = false;
    private SimpleArcDialog mDialog;
    private String version;
    private String versionName;
    private TextView tv_version, tv_thank;
    private RelativeLayout noRecordLayout, no_internet;
    private Dialog dialog;
    private Button btn_yes, btn_no;
    private TextView notification_text_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//         tv_thank = (TextView) findViewById(R.id.tv_thank);
        setSupportActionBar(toolbar);
        mDialog = new SimpleArcDialog(MainActivity.this);
        mDialog.setCancelable(false);
        getserverDate();

        BusinessListting();

        noti_count();
        try {
            versionName = MainActivity.this.getPackageManager()
                    .getPackageInfo(MainActivity.this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = versionName;
        Log.d("abc", "onCreate: " + version);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        tv_version = navigationView.findViewById(R.id.tv_version);
//        tv_version.setText("V" + version);
        View header = navigationView.getHeaderView(0);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        SetonClick();
        createView();

    }


    private void createView() {
        noRecordLayout = (RelativeLayout) findViewById(R.id.noRecordLayout);
        no_internet = (RelativeLayout) findViewById(R.id.no_internet);
        notification_text_count = (TextView) findViewById(R.id.notification_text_count);
        business_recyclerView = (RecyclerView) findViewById(R.id.business_recycler);
        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewBusiness.class);
                startActivity(intent);
                if (!isFABOpen) {
                } else {
                    closeFABMenu();
                }
            }
        });
        fabLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DemoActivity.class);
                startActivity(intent);
                if (!isFABOpen) {
                } else {
                    closeFABMenu();
                }
            }
        });

    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab.animate().rotation(0);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void getserverDate() {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getdate();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "Busi_type: " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        Shared_Preferences.setPrefs(MainActivity.this, Constants.REG_SERVERDATE, jsonObject.getString("Data"));
                        // Parsing json
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void BusinessListting() {
        Log.d("regiter_id", "BusinessListting: " + Shared_Preferences.getPrefs(MainActivity.this, Constants.REG_ID));
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getData(Shared_Preferences.getPrefs(MainActivity.this, Constants.REG_ID));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                no_internet.setVisibility(View.GONE);
                try {
                    String output = response.body().string();
                    Log.d("Response", "getAllBusiness" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        ModelList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ModelList.add(new BusinessModel(object));
                        }
                        noRecordLayout.setVisibility(View.GONE);
                        no_internet.setVisibility(View.GONE);
                        mAdapter = new BusinessListAdapter(MainActivity.this, ModelList, MainActivity.this);
                        business_recyclerView.setAdapter(mAdapter);
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        business_recyclerView.clearFocus();
                        business_recyclerView.setLayoutManager(layoutManager);
                        business_recyclerView.setNestedScrollingEnabled(false);
                        Toast.makeText(MainActivity.this, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        mDialog.dismiss();
                        noRecordLayout.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                mDialog.dismiss();
                no_internet.setVisibility(View.VISIBLE);
                Snackbar.make(getWindow().getDecorView().getRootView(), "" + getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getserverDate();
                                BusinessListting();

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_green_dark))
                        .setDuration(50000)
                        .show();

            }
        });
    }

    private void SetonClick() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        iv_notification = findViewById(R.id.iv_notification);

        linear_report = (LinearLayout) navigationView.findViewById(R.id.linear_report);
        tr_home = (TableRow) navigationView.findViewById(R.id.tr_home);
        tr_userprofile = (TableRow) navigationView.findViewById(R.id.tr_userprofile);
        tr_report = (TableRow) navigationView.findViewById(R.id.tr_report);
        tr_about = (TableRow) navigationView.findViewById(R.id.tr_about);
        tr_Logout = (TableRow) navigationView.findViewById(R.id.tr_Logout);
        tr_request_delivery_center = (TableRow) navigationView.findViewById(R.id.tr_request_delivery_center);
        tr_center_delivery = (TableRow) navigationView.findViewById(R.id.tr_center_delivery);
        tr_request_for_centerto_user = (TableRow) navigationView.findViewById(R.id.tr_request_for_centerto_user);
        tr_deliverd_user_to_center = (TableRow) navigationView.findViewById(R.id.tr_deliverd_user_to_center);
        tr_invoice = (TableRow) navigationView.findViewById(R.id.tr_invoice);
        tr_Settings = (TableRow) navigationView.findViewById(R.id.tr_Settings);
        linear_sett = (LinearLayout) navigationView.findViewById(R.id.linear_sett);
        tr_update_profile = (TableRow) navigationView.findViewById(R.id.tr_update_profile);
        tr_change_pass = (TableRow) navigationView.findViewById(R.id.tr_change_pass);

        tr_home.setOnClickListener(this);
        tr_userprofile.setOnClickListener(this);
        tr_report.setOnClickListener(this);
        tr_about.setOnClickListener(this);
        tr_Logout.setOnClickListener(this);
        tr_request_delivery_center.setOnClickListener(this);
        tr_center_delivery.setOnClickListener(this);
        tr_request_for_centerto_user.setOnClickListener(this);
        tr_deliverd_user_to_center.setOnClickListener(this);
        tr_invoice.setOnClickListener(this);
        tr_Settings.setOnClickListener(this);
        tr_update_profile.setOnClickListener(this);
        tr_change_pass.setOnClickListener(this);

        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNotificationSeen();
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getNotificationSeen() {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getnoti_countszero(Shared_Preferences.getPrefs(MainActivity.this, Constants.REG_ID), "1");
        Log.d("NotiSeen", "id:-" + Shared_Preferences.getPrefs(MainActivity.this, Constants.REG_ID));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("NotiSeen", "id:-" + response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.tr_home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
                drawer.closeDrawers();
                break;
            case R.id.tr_report:
                // startActivity(new Intent(MainActivity.this, MyBusinessActivity.class));
                drawer.closeDrawers();
//                if (!linear_report.isShown()) {
//                    linear_report.setVisibility(View.VISIBLE);
//                    drawer.openDrawer(GravityCompat.START);
//                } else {
//                    linear_report.setVisibility(View.GONE);
//                }
                break;
            case R.id.tr_Settings:
                if (!linear_sett.isShown()) {
                    linear_sett.setVisibility(View.VISIBLE);
                    drawer.openDrawer(GravityCompat.START);
                } else {
                    linear_sett.setVisibility(View.GONE);
                }
                break;
            case R.id.tr_update_profile:
                startActivity(new Intent(MainActivity.this, UpdateVenderProfile.class));
                linear_sett.setVisibility(View.GONE);
                drawer.closeDrawers();
                break;
//            case R.id.tr_change_pass:
//                startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
//                linear_sett.setVisibility(View.GONE);
//                drawer.closeDrawers();
//                break;
            case R.id.tr_request_delivery_center:
//                startActivity(new Intent(MainActivity.this, ReportDeliveryToCenter.class));
                linear_report.setVisibility(View.GONE);
                drawer.closeDrawers();
                break;
            case R.id.tr_center_delivery:
//                startActivity(new Intent(MainActivity.this, ReportCenterToDelivery.class));
                linear_report.setVisibility(View.GONE);
                drawer.closeDrawers();
                break;
            case R.id.tr_request_for_centerto_user:
//                startActivity(new Intent(MainActivity.this, ReportCenterToUser.class));
                linear_report.setVisibility(View.GONE);
                drawer.closeDrawers();
                break;
            case R.id.tr_invoice:
                startActivity(new Intent(MainActivity.this, MyInvoiceActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.tr_userprofile:
                startActivity(new Intent(MainActivity.this, ViewVenderProfile.class));
                drawer.closeDrawers();
                break;
            case R.id.tr_about:
                startActivity(new Intent(MainActivity.this, ContactUs.class));
                drawer.closeDrawers();
                break;

            case R.id.tr_Logout:
                showDialog();
                drawer.closeDrawers();
                break;

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container1, fragment);
            ft.commit();

        }

    }


    private void showDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        btn_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_no = dialog.findViewById(R.id.btn_logout_no);
        btn_yes.setText("Logout");
        btn_no.setText("Cancel");
        TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
        iv_image.setImageDrawable(getResources().getDrawable(R.drawable.logout));
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text_msg.setText("Are you sure you want to Logout");
        text.setText("Logout...!");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Shared_Preferences.clearPref(MainActivity.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Logout Successfully...", Toast.LENGTH_SHORT).show();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
                // tv_thank.setVisibility(View.VISIBLE);
            } else {
                System.exit(0);
            }
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public void onItemClick(boolean flag) {
        if (flag) {
            getserverDate();
            BusinessListting();

        }
    }

    private void noti_count() {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getnoti_counts(Shared_Preferences.getPrefs(MainActivity.this, Constants.REG_ID));

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String output = response.body().string();
                    Log.d("noticount", "gBusiness" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        String count = jsonObject.getString("Data");
                        Log.d("noticount", "count" + count);
                        notification_text_count.setText("" + count);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
            }
        });
    }
}