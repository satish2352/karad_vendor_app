package com.example.karadvenderapp.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.MyValidator;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordActivity extends AppCompatActivity {

    private SimpleArcDialog mDialog;
    private EditText edt_old_password, edt_new_password, edt_comfirm;
    private Button btn_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        edt_old_password = findViewById(R.id.edt_old_password);
        edt_new_password = findViewById(R.id.edt_new_password);
        edt_comfirm = findViewById(R.id.edt_comfirm);
        btn_change = findViewById(R.id.btn_change);
        mDialog = new SimpleArcDialog(ChangePasswordActivity.this);
        mDialog.setCancelable(false);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatefield()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("vendor_id", Shared_Preferences.getPrefs(ChangePasswordActivity.this, Constants.REG_ID));
                    jsonObject.addProperty("old_password", edt_old_password.getText().toString().trim());
                    jsonObject.addProperty("new_password", edt_new_password.getText().toString().trim());
                    ChangePassword(jsonObject);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Fill The Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validatefield() {
        boolean result = true;
        if (!MyValidator.isValidPassword(edt_old_password)) {
            result = false;
        }
        if (!MyValidator.isValidPassword(edt_new_password)) {
            result = false;
        }
        if (!MyValidator.isMatchPassword(edt_new_password, edt_comfirm)) {
            result = false;
        }

        return result;
    }

    private void ChangePassword(JsonObject jsonObject) {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.postRawJSON(jsonObject);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "response=>" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("DisplayMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        finish();
                        Toast.makeText(ChangePasswordActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, reason, Toast.LENGTH_SHORT).show();

                    }
                    mDialog.dismiss();
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
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
