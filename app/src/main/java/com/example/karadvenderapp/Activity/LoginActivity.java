package com.example.karadvenderapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.MyValidator;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btn_sign, btn_send_otp;
    private TextView tv_regiter, resend_otp, otp_counter, tv_forget_password;
    private EditText vender_mobile, tv_otp1, tv_otp2, tv_otp3, tv_otp4;
    private LinearLayout linear_otp_layout;
    private SimpleArcDialog mDialog;
    private ImageView btn_ok, btn_cancel;
    //bottomSheet
    BottomSheetBehavior behavior;
    private LinearLayout bottom_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vender_mobile = findViewById(R.id.vender_mobile);
        tv_otp1 = (EditText) findViewById(R.id.tv_otp1);
        tv_otp2 = (EditText) findViewById(R.id.tv_otp2);
        tv_otp3 = (EditText) findViewById(R.id.tv_otp3);
        tv_otp4 = (EditText) findViewById(R.id.tv_otp4);
        linear_otp_layout = findViewById(R.id.linear_otp_layout);
        tv_regiter = findViewById(R.id.tv_regiter);
        tv_forget_password = findViewById(R.id.tv_forget_password);
        resend_otp = findViewById(R.id.resend_otp);
        otp_counter = findViewById(R.id.otp_counter);
        btn_send_otp = findViewById(R.id.btn_send_otp);
        btn_sign = findViewById(R.id.btn_sign);
        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);

        FirebaseApp.initializeApp(LoginActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("Notification_Token1", "onComplete: " + token);
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.NOTIFICATION_TOKEN, token);
                    }
                });

        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        mDialog = new SimpleArcDialog(LoginActivity.this);
        mDialog.setCancelable(false);

        tv_otp1.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_otp2.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
        tv_otp2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_otp3.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
        tv_otp3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_otp4.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });


        setListener();

    }

    private void setListener()
    {
        btn_send_otp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                ConnectivityManager conMgr = (ConnectivityManager) getSystemService(LoginActivity.this.CONNECTIVITY_SERVICE);

                if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    if (validateFields()) {
                        getsend_otp();
                    } else {
                        Toast.makeText(LoginActivity.this, "Fill the Complete Data", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                    {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setListener();
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setDuration(50000)
                            .show();
                }
            }
        });

        tv_regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterNew.class);
                startActivity(intent);
            }
        });
        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager conMgr = (ConnectivityManager) getSystemService(LoginActivity.this.CONNECTIVITY_SERVICE);

                if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    if (validateotp()) {
                        String otp = (tv_otp1.getText().toString().trim() + "" + tv_otp2.getText().toString().trim() + "" + tv_otp3.getText().toString().trim() + "" + tv_otp4.getText().toString().trim());
                        verifysendOTP(otp);
                    } else {
                        // Toast.makeText(LoginActivity.this, "login in currect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setListener();
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .setDuration(50000)
                            .show();
                }
            }
        });

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP();
            }
        });


    }

    private boolean validateFields() {
        boolean result = true;
        if (!MyValidator.isValidMobile(vender_mobile)) {
            result = false;
        }
        return result;
    }

    private boolean validateotp() {
        boolean result = true;
        if (!MyValidator.isValidField(tv_otp1)) {
            result = false;
        }
        if (!MyValidator.isValidField(tv_otp2)) {
            result = false;
        }
        if (!MyValidator.isValidField(tv_otp3)) {
            result = false;
        }
        if (!MyValidator.isValidField(tv_otp4)) {
            result = false;
        }

        return result;
    }

    private void getsend_otp() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getlogin(vender_mobile.getText().toString(),
                Shared_Preferences.getPrefs(LoginActivity.this, Constants.NOTIFICATION_TOKEN));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        linear_otp_layout.setVisibility(View.VISIBLE);
                        btn_sign.setVisibility(View.VISIBLE);
                        otp_counter.setVisibility(View.VISIBLE);
                        btn_send_otp.setVisibility(View.GONE);
                        vender_mobile.setEnabled(false);
                        tv_otp1.requestFocus();
                        setCountDown();
                        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("server_error", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    private void verifysendOTP(String otp) {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getotp(vender_mobile.getText().toString()
                , otp);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject Vender = jsonArray.getJSONObject(0);
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_ID, Vender.getString("vendor_id"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_NAME, Vender.getString("owner_name"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_MOBILE, Vender.getString("mobile"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_EMAIL, Vender.getString("email"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_COMPANY_NAME, Vender.getString("company_name"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_DOB, Vender.getString("date_of_birth"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_GENDER, Vender.getString("gender"));
//                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_IMAGE, Vender.getString("photo"));
//                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.approve_date, Vender.getString("approve_date"));
//                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.end_date, Vender.getString("end_date"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.shop_act, Vender.getString("shop_act"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.pan_card, Vender.getString("pan_card"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.aadhar_card, Vender.getString("aadhar_card"));
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.REG_IMAGE, Vender.getString("fld_vendor_photo"));


                        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                mDialog.dismiss();
            }
        });
    }

    private void resendOTP() {
        setCountDown();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getlogin(vender_mobile.getText().toString(),
                Shared_Preferences.getPrefs(LoginActivity.this, Constants.NOTIFICATION_TOKEN));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseMessage").equals("1")) {
                        tv_otp1.requestFocus();
                        resend_otp.setEnabled(false);
                        otp_counter.setVisibility(View.VISIBLE);
                        setCountDown();
                        vender_mobile.setFocusable(false);
                        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                mDialog.dismiss();
            }
        });
    }

    private void setCountDown() {
        resend_otp.setEnabled(false);
        resend_otp.setVisibility(View.GONE);
        otp_counter.setVisibility(View.VISIBLE);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                if (seconds < 10) {
                    otp_counter.setText(minutes + ":0" + seconds);
                    //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                } else {
                    otp_counter.setText(minutes + ":" + seconds);
                    //mTimeLabel.setText("" + minutes + ":" + seconds);
                }
            }

            public void onFinish() {
                //mTextField.setText("done!");
                // Log.d("run", "run: " + "done!");
                resend_otp.setText("Resend OTP");
                resend_otp.setEnabled(true);
                resend_otp.setVisibility(View.VISIBLE);
                otp_counter.setVisibility(View.GONE);

            }
        }.start();
    }
}
