package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karadvenderapp.MyLib.Camera;
import com.example.karadvenderapp.MyLib.MyValidator;
import com.example.karadvenderapp.MyLib.UtilityRuntimePermission;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNew extends UtilityRuntimePermission implements Camera.AsyncResponse {
    private Camera camera;
    private Button btn_regiter;
    private int mYear, mMonth, mDay;
    private EditText edt_company_name, edt_password, edt_owner_name, edt_email, edt_mobile_no, edt_dob, edt_review_date, edt_end_date;
    SimpleArcDialog mDialog;
    RadioButton rb_male, rd_female;
    private String gen_type = "Male";
    private TextView text_upload_shopact, text_upload_image_pancard, text_upload_image_aadhar, text_upload_image_profile;
    private CircleImageView business_shop_act, business_pancard, business_aadhar, business_profile;
    private String profile_image_name1, profile_image_name2, profile_image_name3, profile_image_name4;
    private String profile_image_path1="", profile_image_path2="", profile_image_path3="", profile_image_path4="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Regiter New");
        mDialog = new SimpleArcDialog(RegisterNew.this);
        mDialog.setCancelable(false);
        findBy();
    }

    private void findBy() {
        camera = new Camera(RegisterNew.this);
        edt_company_name = findViewById(R.id.edt_company_name);
        edt_owner_name = findViewById(R.id.edt_owner_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile_no = findViewById(R.id.edt_mobile_no);
        edt_password = findViewById(R.id.edt_password);
        rb_male = findViewById(R.id.rb_male);
        rd_female = findViewById(R.id.rd_female);
        edt_dob = findViewById(R.id.edt_dob);
        edt_dob.setFocusableInTouchMode(false);
        edt_review_date = findViewById(R.id.edt_review_date);
        edt_review_date.setFocusableInTouchMode(false);
        edt_end_date = findViewById(R.id.edt_end_date);
        edt_end_date.setFocusableInTouchMode(false);
        business_shop_act = findViewById(R.id.business_shop_act);
        business_pancard = findViewById(R.id.business_pancard);
        business_aadhar = findViewById(R.id.business_aadhar);
        business_profile = findViewById(R.id.business_profile);
        text_upload_image_profile = findViewById(R.id.text_upload_image_profile);
        text_upload_shopact = findViewById(R.id.text_upload_shopact);
        text_upload_image_pancard = findViewById(R.id.text_upload_image_pancard);
        text_upload_image_aadhar = findViewById(R.id.text_upload_image_aadhar);
        btn_regiter = findViewById(R.id.btn_regiter);

        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterNew.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);//1990-10-09
//                                edt_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);//12-09-2020
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        edt_review_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterNew.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_review_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                                edt_review_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        edt_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterNew.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_end_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                                edt_end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validatefield()) {
                    if (rb_male.isChecked()) {
                        gen_type = rb_male.getText().toString();

                    } else if (rd_female.isChecked()) {
                        gen_type = rd_female.getText().toString();
                    }
                    uploadFile(profile_image_path1, profile_image_name1, profile_image_path2, profile_image_name2,
                            profile_image_path3, profile_image_name3, profile_image_path4, profile_image_name4);

                    Toast.makeText(RegisterNew.this, "Button click", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterNew.this, "Fill the Complete Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        text_upload_shopact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RegisterNew.super.requestAppPermissions(RegisterNew.this))

                    camera.selectImage(business_shop_act, 1);

            }
        });
        text_upload_image_pancard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RegisterNew.super.requestAppPermissions(RegisterNew.this))

                    camera.selectImage(business_pancard, 2);

            }
        });
        text_upload_image_aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RegisterNew.super.requestAppPermissions(RegisterNew.this))

                    camera.selectImage(business_aadhar, 3);

            }
        });
        text_upload_image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RegisterNew.super.requestAppPermissions(RegisterNew.this))

                    camera.selectImage(business_profile, 4);

            }
        });
    }

    private boolean validatefield() {
        boolean result = true;
        if (!MyValidator.isValidField(edt_company_name)) {
            result = false;
        }
        if (!MyValidator.isValidField(edt_owner_name)) {
            result = false;
        }
        if (!MyValidator.isValidEmail(edt_email)) {
            result = false;
        }
        if (!MyValidator.isValidMobile(edt_mobile_no)) {
            result = false;
        }
        if (!MyValidator.isValidField(edt_dob)) {
            result = false;
        }
        if (!MyValidator.isValidPassword(edt_password)) {
            result = false;
        }

        return result;
    }


    @Override
    public void processFinish(String result, int img_no) {
        if (img_no == 1) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            Log.d("shopact", "processFinish: " + imagename + "path" + result);
            profile_image_name1 = imagename;
            profile_image_path1 = result;

        } else if (img_no == 2) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            Log.d("pancard", "processFinish: " + imagename + "path" + result);
            profile_image_name2 = imagename;
            profile_image_path2 = result;
        } else if (img_no == 3) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            Log.d("aadhar", "processFinish: " + imagename + "path :-" + result);
            profile_image_name3 = imagename;
            profile_image_path3 = result;

        } else if (img_no == 4) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            Log.d("aadhar", "processFinish: " + imagename + "path :-" + result);
            profile_image_name4 = imagename;
            profile_image_path4 = result;

        }
    }

    @Override
    public void onPermissionsGranted(boolean result)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(String fileUri, String filename, String fileUri2, String filename2, String fileUri3, String filename3, String fileUri4, String filename4) {
        mDialog.show();

        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);

        MultipartBody.Part body = null;
        MultipartBody.Part body2 = null;
        MultipartBody.Part body3 = null;
        MultipartBody.Part body4 = null;
        if (!profile_image_path1.equalsIgnoreCase("")) {
            File file = new File(fileUri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("shop_act", filename, requestFile);

        }
        if (!profile_image_path2.equalsIgnoreCase("")) {
            File file2 = new File(fileUri2);
            RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/*"), file2);
            body2 = MultipartBody.Part.createFormData("pan_card", filename2, requestFile2);

        }
        if (!profile_image_path3.equalsIgnoreCase("")) {
            File file3 = new File(fileUri3);
            RequestBody requestFile3 = RequestBody.create(MediaType.parse("image/*"), file3);
            body3 = MultipartBody.Part.createFormData("aadhar_card", filename3, requestFile3);

        }
        if (!profile_image_path4.equalsIgnoreCase("")) {
            File file4 = new File(fileUri4);
            RequestBody requestFile4 = RequestBody.create(MediaType.parse("image/*"), file4);

            body4 = MultipartBody.Part.createFormData("fld_vendor_photo", filename4, requestFile4);

        }
        RequestBody company_name = RequestBody.create(MediaType.parse("text/plain"), edt_company_name.getText().toString().trim());
        RequestBody owner_name = RequestBody.create(MediaType.parse("text/plain"), edt_owner_name.getText().toString().trim());
        RequestBody mobile_n = RequestBody.create(MediaType.parse("text/plain"), edt_mobile_no.getText().toString().trim());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edt_email.getText().toString().trim());
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), edt_dob.getText().toString().trim());
        RequestBody gende = RequestBody.create(MediaType.parse("text/plain"), gen_type);
        RequestBody passwrod = RequestBody.create(MediaType.parse("text/plain"), edt_password.getText().toString().trim());
        RequestBody end_date = RequestBody.create(MediaType.parse("text/plain"), edt_end_date.getText().toString().trim());

        Call<ResponseBody> call;


        call = apiInterface.regiternew(company_name, owner_name, mobile_n, email, dob, gende, passwrod,/*,review_date,end_date,*/body, body2, body3, body4);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        Intent intent = new Intent(RegisterNew.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterNew.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterNew.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
//                   progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload_error:", "");
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
