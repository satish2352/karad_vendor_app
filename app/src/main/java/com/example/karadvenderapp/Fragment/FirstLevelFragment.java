package com.example.karadvenderapp.Fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.karadvenderapp.Model.BusinessTypeList;
import com.example.karadvenderapp.Model.Business_SubCategoryList;
import com.example.karadvenderapp.Model.Business_Sub_SubCategoryList;
import com.example.karadvenderapp.Model.Business_categoryList;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.MyValidator;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class FirstLevelFragment extends Fragment implements BlockingStep {
    private View rateview;
    public static final int REQUEST_GALERY = 11;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    public static Bitmap mBitmap;
    private TextView text_upload_image;
    private TextView tvErrorSelectImage;
    private ImageView business_profile;
    private EditText edt_busi_name, edt_busi_description, edt_contact_person, edt_designation, edt_mobile, edt_telephone, edt_email;
    private Spinner busi_type_spinner, busi_cate_type_spinner, busi_sub_type_spinner, busi_Sub_sub_type_spinner;
    private String busi_type = "1", busi_cate_type = "", busi_sub_type = "", busi_sub_sub_type = "";
    private List<BusinessTypeList> businessTypeLists = new ArrayList<>();
    private List<Business_categoryList> businessCategoryLists = new ArrayList<>();
    private List<Business_SubCategoryList> business_subCategoryLists = new ArrayList<>();
    private List<Business_Sub_SubCategoryList> business_sub_subCategoryLists = new ArrayList<>();
    private SimpleArcDialog mDialog;
    private String filePath;
    private String imagePath="";
    private CardView cardSelectImage;
    private static int LOAD_IMAGE_RESULTS = 1;
    String businessTypeId="";

    public FirstLevelFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rateview = inflater.inflate(R.layout.fragment_first_level, container, false);
        FindByID();
        getBusinessData();
        askPermissions();
        return rateview;
    }

    private void askPermissions() {
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getContext().getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri()
    {
        Uri outputFileUri = null;
        File getImage = getContext().getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    /*   @Override
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           if (resultCode == Activity.RESULT_OK) {
               if (requestCode == IMAGE_RESULT) {
                   try {
                       filePath = getImageFilePath(data);
                       Log.d("data", "onActivityResult: " + data);
                       File file = new File(filePath);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }

                   if (filePath != null) {
                       mBitmap = BitmapFactory.decodeFile(filePath);
                       Log.d("mBitmap", "onActivityResult: " + mBitmap);
                       Log.d("mBitmap", "onActivityResult: " + filePath);
                       business_profile.setImageBitmap(mBitmap);
                   }
               }

           }

       }
   */
    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void FindByID() {
        tvErrorSelectImage = rateview.findViewById(R.id.tvErrorSelectImage);
        cardSelectImage = rateview.findViewById(R.id.cardSelectImage);
        business_profile = rateview.findViewById(R.id.business_profile);
        text_upload_image = rateview.findViewById(R.id.text_upload_image);
        edt_busi_name = rateview.findViewById(R.id.edt_busi_name);
        edt_busi_name = rateview.findViewById(R.id.edt_busi_name);
        edt_busi_description = rateview.findViewById(R.id.edt_busi_description);
        edt_contact_person = rateview.findViewById(R.id.edt_contact_person);
        edt_designation = rateview.findViewById(R.id.edt_designation);
        edt_mobile = rateview.findViewById(R.id.edt_mobile);
        edt_telephone = rateview.findViewById(R.id.edt_telephone);
        edt_email = rateview.findViewById(R.id.edt_email);
        edt_contact_person.setText(Shared_Preferences.getPrefs(getContext(), Constants.REG_NAME));
        edt_mobile.setText(Shared_Preferences.getPrefs(getContext(), Constants.REG_MOBILE));
        edt_email.setText(Shared_Preferences.getPrefs(getContext(), Constants.REG_EMAIL));

        busi_type_spinner = rateview.findViewById(R.id.busi_type_spinner);
        busi_cate_type_spinner = rateview.findViewById(R.id.busi_cate_type_spinner);
        busi_sub_type_spinner = rateview.findViewById(R.id.busi_sub_type_spinner);
        busi_Sub_sub_type_spinner = rateview.findViewById(R.id.busi_Sub_sub_type_spinner);
        mDialog = new SimpleArcDialog(getContext());
        mDialog.setCancelable(false);
        cardSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(checkAndRequestReadImagePermission(getActivity(),100))
                {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, LOAD_IMAGE_RESULTS); //LOAD_IMAGE_RESULTS
                }
            }
        });
    }
    public static boolean checkAndRequestReadImagePermission(Activity activity, int requestCode) {
        String[] permissions = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API level 33): Use specific media permission
            permissions = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 and above: Use READ_EXTERNAL_STORAGE
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        } else {
            // Android 10 and below: Use READ_EXTERNAL_STORAGE
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }

        if (permissions != null) {
            SharedPreferences preferences = activity.getSharedPreferences("permission_prefs", Context.MODE_PRIVATE);
            boolean isFirstRequest = preferences.getBoolean("isFirstRequestReadImages", true);

            // Check permission
            int permissionCheck = ContextCompat.checkSelfPermission(activity, permissions[0]);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted
                return true;
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                // Permission not granted but can still be requested
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                preferences.edit().putBoolean("isFirstRequestReadImages", false).apply();
                return false; // Indicate permission not granted yet
            } else if (isFirstRequest) {
                // First time asking for permission
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                preferences.edit().putBoolean("isFirstRequestReadImages", false).apply();
                return false; // Indicate permission not granted yet
            } else {
                // Permission permanently denied, navigate to settings
                navigateAppSettings(activity);
                return false; // Indicate permission not granted
            }
        }

        // No permissions to check (shouldn't happen)
        return true; // Assuming no permission check is a success (review if needed)
    }

    private static void navigateAppSettings(Activity activity) {

        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(activity)
                .setTitle("Allow Permission In Settings")
                .setMessage("Allow All the requested permission from \nSettings->permissions")
                .setPositiveButton("Goto Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        intent.setData(uri);
                        if (intent.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivity(intent);
                        }
                    }
                });

        androidx.appcompat.app.AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }



    private static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }
    public static boolean checkAndRequestCameraPermission(Activity activity, int requestCode) {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        SharedPreferences preferences = activity.getSharedPreferences("permission_prefs", Context.MODE_PRIVATE);
        boolean isFirstRequest = preferences.getBoolean("isFirstRequest", true);

        // Check permission
        int permissionCheck = ContextCompat.checkSelfPermission(activity, permissions[0]);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
            // Permission not granted but can still be requested
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            preferences.edit().putBoolean("isFirstRequest", false).apply();
            return false; // Indicate permission not granted yet
        } else if (isFirstRequest) {
            // First time asking for permission
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            preferences.edit().putBoolean("isFirstRequest", false).apply();
            return false; // Indicate permission not granted yet
        } else {
            // Permission permanently denied, navigate to settings
            navigateAppSettings(activity);
            return false; // Indicate permission not granted
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == getActivity().RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            business_profile.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            if(imagePath.length()>0){

                tvErrorSelectImage.setText("");
            }

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }


    private boolean validateFields() {
        List<Boolean> listValidation = new ArrayList<>();

        listValidation.add(MyValidator.isValidField(edt_busi_name));
        listValidation.add(MyValidator.isValidSpinner(busi_type_spinner));
        listValidation.add(MyValidator.isValidSpinner(busi_cate_type_spinner));
        listValidation.add(MyValidator.isValidSpinner(busi_sub_type_spinner));
        listValidation.add(MyValidator.isValidField(edt_contact_person));
        listValidation.add(MyValidator.isValidMobile(edt_mobile));
        listValidation.add(MyValidator.isValidEmail(edt_email));

        if (!imagePath.isEmpty()) {
            listValidation.add(true);
            tvErrorSelectImage.setText("");
        } else {
            listValidation.add(false);
            tvErrorSelectImage.setText("Required: Please Select Image");
        }

        return !listValidation.contains(false);
    }


    private void getBusinessData() {
        mDialog.show();
        businessTypeLists.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getbusinessType();
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
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        businessTypeLists.add(new BusinessTypeList("--- Select Business Type ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                businessTypeLists.add(new BusinessTypeList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<BusinessTypeList> dataAdapter = new ArrayAdapter<BusinessTypeList>(getContext(), android.R.layout.simple_spinner_item, businessTypeLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        busi_type_spinner.setAdapter(dataAdapter);
                        busi_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                busi_type = businessTypeLists.get(i).getBusiness_id();
                                businessTypeId=busi_type;
                                getcateBusinessTypeData(busi_type);
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    private void getcateBusinessTypeData(String busi_type_id) {
        mDialog.show();
        businessCategoryLists.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getcatebusinessType(busi_type_id);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("org", "busi_category: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        businessCategoryLists.add(new Business_categoryList("--- Select Business Category ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                businessCategoryLists.add(new Business_categoryList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<Business_categoryList> dataAdapter = new ArrayAdapter<Business_categoryList>(getContext(), android.R.layout.simple_spinner_item, businessCategoryLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        busi_cate_type_spinner.setAdapter(dataAdapter);
                        busi_cate_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                busi_cate_type = businessCategoryLists.get(i).getFld_business_category_id();
                                getSubBusinessTypeData(busi_cate_type);
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    private void getSubBusinessTypeData(String busi_cate_id) {
        mDialog.show();
        business_subCategoryLists.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getsub_cate_businessType(busi_cate_id);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "sub_busi: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        business_subCategoryLists.add(new Business_SubCategoryList("--Select Business Sub Category--"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                business_subCategoryLists.add(new Business_SubCategoryList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<Business_SubCategoryList> dataAdapter = new ArrayAdapter<Business_SubCategoryList>(getContext(), android.R.layout.simple_spinner_item, business_subCategoryLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        busi_sub_type_spinner.setAdapter(dataAdapter);
                        busi_sub_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                busi_sub_type = business_subCategoryLists.get(i).getFld_business_subcategory_id();
                                getSub_SubBusinessTypeData(busi_sub_type);
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    private void getSub_SubBusinessTypeData(String busi_sub_cate_id) {
        mDialog.show();
        business_sub_subCategoryLists.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getsub_sub_cate_businessType(busi_sub_cate_id);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "sub_sub_busi: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        business_sub_subCategoryLists.add(new Business_Sub_SubCategoryList("Select Business Sub SubCategory"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                business_sub_subCategoryLists.add(new Business_Sub_SubCategoryList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<Business_Sub_SubCategoryList> dataAdapter = new ArrayAdapter<Business_Sub_SubCategoryList>(getContext(), android.R.layout.simple_spinner_item, business_sub_subCategoryLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        busi_Sub_sub_type_spinner.setAdapter(dataAdapter);
                        busi_Sub_sub_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                busi_sub_sub_type = business_sub_subCategoryLists.get(i).getFld_business_subsubcategory_id();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if (validateFields()) {
            Shared_Preferences.setPrefs(getContext(), "Image", imagePath);
            Shared_Preferences.setPrefs(getContext(), "business_type_id", businessTypeId);
            Shared_Preferences.setPrefs(getContext(), "busi_name", edt_busi_name.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "description", edt_busi_description.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "busi_type", busi_type);
            Shared_Preferences.setPrefs(getContext(), "busi_cate_type", busi_cate_type);
            Shared_Preferences.setPrefs(getContext(), "busi_sub_type", busi_sub_type);
            Shared_Preferences.setPrefs(getContext(), "busi_sub_sub_type", busi_sub_sub_type);
            Shared_Preferences.setPrefs(getContext(), "contact_person", edt_contact_person.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "designation", edt_designation.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "mobile", edt_mobile.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "telephone", edt_telephone.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "email", edt_email.getText().toString().trim());
            Log.d("path", "onNextClicked: " + imagePath);




            callback.goToNextStep();
        } else {
            Toast.makeText(getContext(), "Fill The Complete Data", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {

        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

}

