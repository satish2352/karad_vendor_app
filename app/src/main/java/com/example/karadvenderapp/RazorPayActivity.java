package com.example.karadvenderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.karadvenderapp.Activity.MainActivity;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RazorPayActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private static final String TAG = "rajendra";
    private String Amount = "", order_id = "", Package_id = "", Business_infoid="";
    public static PaymentData paymentData2;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());
        Intent intent = getIntent();
        Amount = intent.getStringExtra("package_amount");
        order_id = intent.getStringExtra("order_id");
        Package_id = intent.getStringExtra("package_id");
        Business_infoid=Shared_Preferences.getPrefs(this, Constants.BUID);

        Log.e("Payment","package_amount"+Amount);
        Log.e("Payment","order_id"+order_id);
        Log.e("Payment","package_id"+Package_id);
        Log.e("Payment","business_id"+Business_infoid);

        startPayment();
    }


    public void startPayment()
    {
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.rzp_logo);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Karad");
            options.put("description", "Vender Payment");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", order_id);
            options.put("currency", "INR");
            double total = Double.parseDouble(Amount);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", Shared_Preferences.getPrefs(RazorPayActivity.this, Constants.REG_EMAIL));
            preFill.put("contact", Shared_Preferences.getPrefs(RazorPayActivity.this, Constants.REG_MOBILE));
            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData)
    {
        paymentData2 = paymentData;
        postResponse(paymentData);
         Toast.makeText(this, "Payment successfully done! " , Toast.LENGTH_SHORT).show();
        Intent i = new Intent(RazorPayActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.e(TAG, "onPaymentError");
        Log.d("rajendra", "error code " + String.valueOf(i) + " -- Payment failed " + s.toString());
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d("rajendra", "" + e);
        }
        finish();
    }


    private void postResponse(PaymentData paymentData)
    {
        Log.e("TAG", "getOrderId " + paymentData2.getOrderId());
        Log.e("TAG", "getvendorID " + Shared_Preferences.getPrefs(RazorPayActivity.this, Constants.REG_ID));
        Log.e("TAG", "getSignature " + paymentData2.getSignature());
        Log.e("TAG", "getPaymentId " + paymentData2.getPaymentId());
        Log.e("TAG", "getUserEmail " + paymentData2.getUserEmail());
        Log.e("TAG", "getUserContact " + paymentData2.getUserContact());
        Log.e("TAG", "getData " + paymentData.getData());
        Log.e("TAG", "package_id " + Package_id);
        Log.e("TAG", "Business_infoid " + Business_infoid);


        apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.sendData_id(paymentData2.getOrderId(),
                paymentData2.getPaymentId(),paymentData2.getSignature(),Package_id,Shared_Preferences.getPrefs(this, Constants.REG_ID),Business_infoid);



        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });

    }


}