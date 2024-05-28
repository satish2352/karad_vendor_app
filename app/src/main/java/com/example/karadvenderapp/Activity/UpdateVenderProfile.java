package com.example.karadvenderapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karadvenderapp.MyLib.Camera;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.MyValidator;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.MyLib.UtilityRuntimePermission;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVenderProfile extends UtilityRuntimePermission implements Camera.AsyncResponse {
    private static final int REQUEST_CODE_READ_IMAGE_PERMISSION =100 ;
    private Camera camera;
    private Button btn_regiter;
    private int mYear, mMonth, mDay;
    private EditText edt_company_name, edt_owner_name, edt_email, edt_mobile_no, edt_dob, edt_review_date, edt_end_date;
    SimpleArcDialog mDialog;
    RadioButton rb_male, rd_female;
    private String gen_type = "Male";
    private TextView text_upload_shopact, text_upload_image_pancard, text_upload_image_aadhar, text_upload_image_profile;
    private CircleImageView business_shop_act, business_pancard, business_aadhar, business_profile;
    private String profile_image_name1 = "", profile_image_name2 = "", profile_image_name3 = "", profile_image_name4 = "";
    private String profile_image_path1 = "", profile_image_path2 = "", profile_image_path3 = "", profile_image_path4 = "";

    String profilepath="";
    File imageprofileFile;
    private CharSequence[] options = {"camera","Gallery","Cancel"};
    Uri uri;
    String Date = new SimpleDateFormat("yyyymmdd", Locale.getDefault()).format(new Date());
    String Time = new SimpleDateFormat("HHmmss",Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vender_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Update Vender");
        mDialog = new SimpleArcDialog(UpdateVenderProfile.this);
        mDialog.setCancelable(false);
        findBy();

//        try
//        {
//            URL url = new URL(Shared_Preferences.getPrefs(UpdateVenderProfile.this,Constants.REG_IMAGE));
//            Log.e("HemantImg",""+url);
////            defaultprofileimg=null;
//            defaultprofileimg=BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            Log.e("HemantImg",""+defaultprofileimg);
//            profilepath = com.example.karadvenderapp.MyLib.FileUtils.getPath(UpdateVenderProfile.this,getImageUri(UpdateVenderProfile.this,defaultprofileimg));
//            Log.e("HemantIMg","profilepath "+profilepath);
//
//        }
//        catch (IOException e)
//        {
//
//        }


//

    }

    private void findBy() {
        camera = new Camera(UpdateVenderProfile.this);
        edt_company_name = findViewById(R.id.edt_company_name);
        edt_owner_name = findViewById(R.id.edt_owner_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile_no = findViewById(R.id.edt_mobile_no);
        rb_male = findViewById(R.id.rb_male);
        rd_female = findViewById(R.id.rd_female);
        edt_dob = findViewById(R.id.edt_dob);

        edt_company_name.setText(Shared_Preferences.getPrefs(UpdateVenderProfile.this, Constants.REG_COMPANY_NAME));
        edt_owner_name.setText(Shared_Preferences.getPrefs(UpdateVenderProfile.this, Constants.REG_NAME));
        edt_email.setText(Shared_Preferences.getPrefs(UpdateVenderProfile.this, Constants.REG_EMAIL));
        edt_mobile_no.setText(Shared_Preferences.getPrefs(UpdateVenderProfile.this, Constants.REG_MOBILE));
        edt_dob.setText(Shared_Preferences.getPrefs(UpdateVenderProfile.this, Constants.REG_DOB));

        edt_dob.setFocusableInTouchMode(false);
        edt_review_date = findViewById(R.id.edt_review_date);
        edt_review_date.setFocusableInTouchMode(false);
        edt_end_date = findViewById(R.id.edt_end_date);
        edt_end_date.setFocusableInTouchMode(false);
        business_shop_act = findViewById(R.id.business_shop_act);
        business_pancard = findViewById(R.id.business_pancard);
        business_aadhar = findViewById(R.id.business_aadhar);
        business_profile = findViewById(R.id.business_profile);

//        Picasso.with(UpdateVenderProfile.this)
//                .load(Shared_Preferences.getPrefs(UpdateVenderProfile.this,Constants.shop_act))
//                .error(R.drawable.no_image_available)
//                .placeholder(R.drawable.no_image_available)
//                .resize(300,300)
//                .into(business_shop_act);
//
//        Picasso.with(UpdateVenderProfile.this)
//                .load(Shared_Preferences.getPrefs(UpdateVenderProfile.this,Constants.pan_card))
//                .error(R.drawable.no_image_available)
//                .placeholder(R.drawable.no_image_available)
//                .resize(300,300)
//                .into(business_pancard);
//
//        Picasso.with(UpdateVenderProfile.this)
//                .load(Shared_Preferences.getPrefs(UpdateVenderProfile.this,Constants.aadhar_card))
//                .error(R.drawable.no_image_available)
//                .placeholder(R.drawable.no_image_available)
//                .resize(300,300)
//                .into(business_aadhar);

        Picasso.with(UpdateVenderProfile.this)
                .load(Shared_Preferences.getPrefs(UpdateVenderProfile.this,Constants.REG_IMAGE))
                .error(R.drawable.no_image_available)
                .placeholder(R.drawable.no_image_available)
                .resize(300,300)
                .into(business_profile);


        text_upload_image_profile = findViewById(R.id.text_upload_image_profile);
//        text_upload_shopact = findViewById(R.id.text_upload_shopact);
//        text_upload_image_pancard = findViewById(R.id.text_upload_image_pancard);
//        text_upload_image_aadhar = findViewById(R.id.text_upload_image_aadhar);
        btn_regiter = findViewById(R.id.btn_regiter);

        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateVenderProfile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth)
                            {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateVenderProfile.this,
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateVenderProfile.this,
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
                if (validatefield())
                {
                    if (rb_male.isChecked())
                    {
                        gen_type = rb_male.getText().toString();

                    } else if (rd_female.isChecked())
                    {
                        gen_type = rd_female.getText().toString();
                    }
                    if (profilepath.isEmpty())
                    {
                        uploadprofilewithoutimg();
                    }
                    else
                    {
                        uploadFile();
                    }

                }
                else
                {
                    Toast.makeText(UpdateVenderProfile.this, "Fill the Complete Data", Toast.LENGTH_SHORT).show();
                }
            }
        });


        text_upload_image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //requestReadImagePermission(UpdateVenderProfile.this,REQUEST_CODE_READ_IMAGE_PERMISSION);
                if (checkAndRequestReadImagePermission(UpdateVenderProfile.this,REQUEST_CODE_READ_IMAGE_PERMISSION))

                {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(UpdateVenderProfile.this);
                    builder.setTitle("Select Image");
                    builder.setItems(options, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int i)
                        {
                            if(options[i].equals("camera"))
                            {
                                if(checkAndRequestCameraPermission(UpdateVenderProfile.this,101))
                                {
                                    Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(takepic,0);
                                }
                            }
                            else if(options[i].equals("Gallery"))
                            {
                                Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(gallery,1);
                            }
                            else
                            {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }

            }
        });
    }

    public static void requestReadImagePermission(Activity activity, int requestCode) {
        String[] permissions = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API level 33): Use specific media permission
            permissions = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R &&
                activity.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.R) {
            // Android 11 with requestLegacyExternalStorage (not recommended, limited access)
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        } else {
            // Android 10-12 (API level 29-32): Use READ_EXTERNAL_STORAGE (may be limited on 11+)
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }

        if (permissions != null) {
            // Check permission and request if needed
            if (ContextCompat.checkSelfPermission(activity, permissions[0])
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
            } else {
                // Permission already granted, proceed with accessing images

            }
        }
    }
    public static boolean checkAndRequestReadImagePermission(Activity activity, int requestCode) {
        String[] permissions = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API level 33): Use specific media permission
            permissions = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R &&
                activity.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.R) {
            // Android 11 with requestLegacyExternalStorage (not recommended, limited access)
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        } else {
            // Android 10-12 (API level 29-32): Use READ_EXTERNAL_STORAGE (may be limited on 11+)
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }

        if (permissions != null) {
            // Check permission
            int permissionCheck = ContextCompat.checkSelfPermission(activity, permissions[0]);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted
                return true;
            } else if (shouldShowRequestPermissionRationale(activity, permissions[0])) {
                // Permission not granted but can still be requested
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false; // Indicate permission not granted yet
            } else {
                // Permission permanently denied, navigate to settings (optional)
                navigateAppSettings(activity);
                return false; // Indicate permission not granted
            }
        }

        // No permissions to check (shouldn't happen)
        return true; // Assuming no permission check is a success (review if needed)
    }

    private static void navigateAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    private static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }
    public static boolean checkAndRequestCameraPermission(Activity activity, int requestCode) {
        String[] permissions = new String[]{Manifest.permission.CAMERA};

        // Check permission
        int permissionCheck = ContextCompat.checkSelfPermission(activity, permissions[0]);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
            return true;
        } else if (shouldShowRequestPermissionRationale(activity, permissions[0])) {
            // Permission not granted but can still be requested
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            return false; // Indicate permission not granted yet
        } else {
            // Permission permanently denied, navigate to settings (optional)
            navigateAppSettings(activity);
            return false; // Indicate permission not granted
        }
    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_READ_IMAGE_PERMISSION) { // Replace with your request code
            if (resultCode == RESULT_OK) {
                // Permission granted, proceed with accessing images
                // Use Storage Access Framework (SAF) for accessing images
                //accessImagesUsingSAF();
            } else {
                // Permission denied, handle user rejection
                Toast.makeText(this, "Permission to access images denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }*/


    private void uploadprofilewithoutimg()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.UpdateProfilewithoutimg(
                Shared_Preferences.getPrefs(UpdateVenderProfile.this, Constants.REG_ID),
                edt_company_name.getText().toString().trim(),
                edt_owner_name.getText().toString().trim(),
                edt_mobile_no.getText().toString().trim(),
                edt_email.getText().toString().trim(),
                edt_dob.getText().toString().trim(),
                gen_type,
                Shared_Preferences.getPrefs(UpdateVenderProfile.this, Constants.REG_IMAGE));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject Vender = jsonArray.getJSONObject(0);
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_ID, Vender.getString("vendor_id"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_NAME, Vender.getString("owner_name"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_MOBILE, Vender.getString("mobile"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_EMAIL, Vender.getString("email"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_COMPANY_NAME, Vender.getString("company_name"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_DOB, Vender.getString("date_of_birth"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_GENDER, Vender.getString("gender"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.shop_act, Vender.getString("shop_act"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.pan_card, Vender.getString("pan_card"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.aadhar_card, Vender.getString("aadhar_card"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_IMAGE, Vender.getString("fld_vendor_photo"));

                        Intent intent = new Intent(UpdateVenderProfile.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(UpdateVenderProfile.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(UpdateVenderProfile.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void uploadFile()
    {
        mDialog.show();

        RequestBody Vendor_id = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(UpdateVenderProfile.this,Constants.REG_ID));
        RequestBody company_name = RequestBody.create(MediaType.parse("text/plain"), edt_company_name.getText().toString().trim());
        RequestBody owner_name = RequestBody.create(MediaType.parse("text/plain"), edt_owner_name.getText().toString().trim());
        RequestBody mobile_n = RequestBody.create(MediaType.parse("text/plain"), edt_mobile_no.getText().toString().trim());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edt_email.getText().toString().trim());
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), edt_dob.getText().toString().trim());
        RequestBody gende = RequestBody.create(MediaType.parse("text/plain"), gen_type);

        imageprofileFile = new File(profilepath);
        Log.e("profilepath",""+imageprofileFile);

        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"),imageprofileFile);

        MultipartBody.Part fld_vendor_photo = MultipartBody.Part.createFormData("fld_vendor_photo", imageprofileFile.getName(), reqBody);

        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.UpdateProfile(Vendor_id,company_name, owner_name, mobile_n, email, dob, gende, fld_vendor_photo);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject Vender = jsonArray.getJSONObject(0);
                        Log.e("Vender", "=> " + Vender);
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_ID, Vender.getString("vendor_id"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_NAME, Vender.getString("owner_name"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_MOBILE, Vender.getString("mobile"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_EMAIL, Vender.getString("email"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_COMPANY_NAME, Vender.getString("company_name"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_DOB, Vender.getString("date_of_birth"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_GENDER, Vender.getString("gender"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.shop_act, Vender.getString("shop_act"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.pan_card, Vender.getString("pan_card"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.aadhar_card, Vender.getString("aadhar_card"));
                        Shared_Preferences.setPrefs(UpdateVenderProfile.this, Constants.REG_IMAGE, Vender.getString("fld_vendor_photo"));


                        Intent intent = new Intent(UpdateVenderProfile.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(UpdateVenderProfile.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateVenderProfile.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                   progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload_error:", "");
                mDialog.dismiss();

            }
        });


    }

    private boolean validatefield()
    {
        boolean result = true;
//
//        if (!MyValidator.isValidField(edt_company_name)) {
//            result = false;
//        }
//        if (!MyValidator.isValidField(edt_owner_name)) {
//            result = false;
//        }
//        if (!MyValidator.isValidEmail(edt_email)) {
//            result = false;
//        }
//        if (!MyValidator.isValidMobile(edt_mobile_no)) {
//            result = false;
//        }
//        if (!MyValidator.isValidField(edt_dob))
//        {
//            result = false;
//        }



//        if (!MyValidator.isValidImage(business_shop_act)) {
//            result = false;
//        }   if (!MyValidator.isValidImage(business_pancard)) {
//            result = false;
//        }   if (!MyValidator.isValidImage(business_aadhar)) {
//            result = false;
//        }   if (!MyValidator.isValidImage(business_profile)) {
//            result = false;
//        }

        return result;
    }


    @Override
    public void processFinish(String result, int img_no)
    {
//        if (img_no == 1) {
//            String[] parts = result.split("/");
//            String imagename = parts[parts.length - 1];
//            Log.d("shopact", "processFinish: " + imagename + "path" + result);
//            profile_image_name1 = imagename;
//            profile_image_path1 = result;
//
//        } else if (img_no == 2) {
//            String[] parts = result.split("/");
//            String imagename = parts[parts.length - 1];
//            Log.d("pancard", "processFinish: " + imagename + "path" + result);
//            profile_image_name2 = imagename;
//            profile_image_path2 = result;
//        } else if (img_no == 3) {
//            String[] parts = result.split("/");
//            String imagename = parts[parts.length - 1];
//            Log.d("aadhar", "processFinish: " + imagename + "path :-" + result);
//            profile_image_name3 = imagename;
//            profile_image_path3 = result;
//
//        } else
//            if (img_no == 4) {
//            String[] parts = result.split("/");
//            String imagename = parts[parts.length - 1];
//            Log.d("aadhar", "processFinish: " + imagename + "path :-" + result);
//            profile_image_name4 = imagename;
//            profile_image_path4 = result;
//
//        }
    }

    @Override
    public void onPermissionsGranted(boolean result) {

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED)
        {
            switch (requestCode)
            {
                case 0:
                    if(resultCode == RESULT_OK && data !=null)
                    {
                        Bitmap image =(Bitmap) data.getExtras().get("data");

                        profilepath = com.example.karadvenderapp.MyLib.FileUtils.getPath(UpdateVenderProfile.this,getImageUri(UpdateVenderProfile.this,image));
                        business_profile.setImageBitmap(image);

                    }
                    break;

                case 1:
                {
                    if(resultCode == RESULT_OK && data !=null)
                    {
                        uri = data.getData();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(UpdateVenderProfile.this.getContentResolver(),uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        profilepath = com.example.karadvenderapp.MyLib.FileUtils.getPath(UpdateVenderProfile.this,getImageUri(UpdateVenderProfile.this,bitmap));
                        Log.e("HemantIMg","gallery "+profilepath);
                        Picasso.with(UpdateVenderProfile.this).load(uri).into(business_profile);
                        Log.e("ImgLink",""+profilepath);
                    }
                }
            }

        }

    }


    public Uri getImageUri(Context context, Bitmap bitmap)
    {
        String profile = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"ProfileImg"+Time+Date,"");
        return Uri.parse(profile);
    }



    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }


}
