package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karadvenderapp.MyLib.Camera;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.MyLib.UtilityRuntimePermission;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.R;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusinessPhoto extends UtilityRuntimePermission implements Camera.AsyncResponse {
    private Camera camera;
    private TextView tv_firstImage, tv_secondImage, tv_thridImage, tv_fourthImage, tv_fifitImage;
    private ImageView iv_first, iv_second, iv_thrid, iv_fourth, iv_fifit;
    private String profile_image_name1 = null, profile_image_path1 = null, profile_image_name2 = null, profile_image_path2 = null,
            profile_image_name3 = null, profile_image_path3 = null, profile_image_name4 = null, profile_image_path4 = null, profile_image_name5 = null, profile_image_path5 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Business Photos");
        Init();
    }

    private void Init() {
        camera = new Camera(AddBusinessPhoto.this);
        tv_firstImage = findViewById(R.id.tv_firstImage);
        iv_first = findViewById(R.id.iv_first);
        tv_firstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AddBusinessPhoto.super.requestAppPermissions(AddBusinessPhoto.this))

                    camera.selectImage(iv_first, 1);
            }
        });

        tv_secondImage = findViewById(R.id.tv_secondImage);
        iv_second = findViewById(R.id.iv_second);
        tv_secondImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AddBusinessPhoto.super.requestAppPermissions(AddBusinessPhoto.this))

                    camera.selectImage(iv_second, 2);
            }
        });

        tv_thridImage = findViewById(R.id.tv_thridImage);
        iv_thrid = findViewById(R.id.iv_thrid);
        tv_thridImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AddBusinessPhoto.super.requestAppPermissions(AddBusinessPhoto.this))

                    camera.selectImage(iv_thrid, 3);
            }
        });

        tv_fourthImage = findViewById(R.id.tv_fourthImage);
        iv_fourth = findViewById(R.id.iv_fourth);
        tv_fourthImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AddBusinessPhoto.super.requestAppPermissions(AddBusinessPhoto.this))

                    camera.selectImage(iv_fourth, 4);
            }
        });

        tv_fifitImage = findViewById(R.id.tv_fifitImage);
        iv_fifit = findViewById(R.id.iv_fifit);
        tv_fifitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AddBusinessPhoto.super.requestAppPermissions(AddBusinessPhoto.this))

                    camera.selectImage(iv_fifit, 5);
            }
        });

    }

    @Override
    public void processFinish(String result, int img_no) {
        if (img_no == 1) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            profile_image_name1 = imagename;
            profile_image_path1 = result;

        } else if (img_no == 2) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            profile_image_name2 = imagename;
            profile_image_path2 = result;
        } else if (img_no == 3) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            profile_image_name3 = imagename;
            profile_image_path3 = result;
        } else if (img_no == 4) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            profile_image_name4 = imagename;
            profile_image_path4 = result;
        } else if (img_no == 5) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            profile_image_name5 = imagename;
            profile_image_path5 = result;
        }
    }

    @Override
    public void onPermissionsGranted(boolean result) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(String fileUri, String filename) {

        APIInterface apiInterface=MyConfig.getRetrofit().create(APIInterface.class);

        MultipartBody.Part body = null;
        if (!profile_image_path1.equalsIgnoreCase("")) {

            File file = new File(fileUri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);


            body = MultipartBody.Part.createFormData("business_photo", filename, requestFile);

        }
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "12");
        Call<ResponseBody> call;


        call = apiInterface.upload(user_id, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                           finish();
                        Toast.makeText(AddBusinessPhoto.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddBusinessPhoto.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                   progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload_error:", "");
            }
        });


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}