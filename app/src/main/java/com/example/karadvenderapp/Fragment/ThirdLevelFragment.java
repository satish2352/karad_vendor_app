package com.example.karadvenderapp.Fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.karadvenderapp.Activity.MainActivity;

import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.MyValidator;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class ThirdLevelFragment<mBitmap> extends Fragment implements BlockingStep {

    private View rateview;
    private CheckBox work_check_mon, work_check_tue, work_check_wed, work_check_thu, work_check_fri, work_check_sat, work_check_sun;//workingDayweek
    private CheckBox off_day_check_mon, off_day_check_tue, off_day_check_wed, off_day_check_thu, off_day_check_fri, off_day_check_sat, off_day_check_sun;//offDayweek
    private CheckBox checkbox_cash, checkbox_cheque, checkbox_credit, checkbox_debit;//payment_mode checkbox
    private EditText edt_working_time, edt_busi_no_of_people, edt_year, edt_annual_turnover, edt_number_of_emp, edt_booking_time, edt_certification;
    private SimpleArcDialog mDialog;
    private ArrayList<String> workingday = new ArrayList<>();//workingdayList
    private ArrayList<String> Offday = new ArrayList<>();//OffdayList
    public String WorkingDayResult = "";
    public String OffDayResult;
//    public String Payment_mode;
//    CheckBox[] chkArray = new CheckBox[4];
    public static Bitmap mBitmap;

    public ThirdLevelFragment()
    {
        // Required empty public constructor
    }

    TextView tvLabelWorkingDays;
    LinearLayout layoutCheckBoxes,layoutCheckBoxes2;
    TextInputLayout etWorkingTime,etPeoplePerSlot,etPreBookingTime;
    String businessTypeId="";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rateview = inflater.inflate(R.layout.fragment_thrid_level, container, false);
        FindByID();
        return rateview;
    }


    private void FindByID() {


        // id to hide if service selected


        tvLabelWorkingDays=rateview.findViewById(R.id.tvLabelWorkingDays);
        layoutCheckBoxes=rateview.findViewById(R.id.layoutCheckBoxes);
        layoutCheckBoxes2=rateview.findViewById(R.id.layoutCheckBoxes2);
        etPreBookingTime=rateview.findViewById(R.id.etPreBookingTime);
        etPeoplePerSlot=rateview.findViewById(R.id.etPeoplePerSlot);
        etWorkingTime=rateview.findViewById(R.id.etWorkingTime);
        businessTypeId=Shared_Preferences.getPrefs(getContext(), "business_type_id");
        if(businessTypeId.equals("2"))
        {
            tvLabelWorkingDays.setVisibility(View.GONE);
            layoutCheckBoxes.setVisibility(View.GONE);
            etPreBookingTime.setVisibility(View.GONE);
            etPeoplePerSlot.setVisibility(View.GONE);
            etWorkingTime.setVisibility(View.GONE);
            layoutCheckBoxes2.setVisibility(View.GONE);

        }else{

            tvLabelWorkingDays.setVisibility(View.VISIBLE);
            layoutCheckBoxes.setVisibility(View.VISIBLE);
            etPreBookingTime.setVisibility(View.VISIBLE);
            etPeoplePerSlot.setVisibility(View.VISIBLE);
            etWorkingTime.setVisibility(View.VISIBLE);
            layoutCheckBoxes2.setVisibility(View.VISIBLE);
        }


        //find by **working day***
        work_check_mon = rateview.findViewById(R.id.work_check_mon);
        work_check_tue = rateview.findViewById(R.id.work_check_tue);
        work_check_wed = rateview.findViewById(R.id.work_check_wed);
        work_check_thu = rateview.findViewById(R.id.work_check_thu);
        work_check_fri = rateview.findViewById(R.id.work_check_fri);
        work_check_sat = rateview.findViewById(R.id.work_check_sat);
        work_check_sun = rateview.findViewById(R.id.work_check_sun);
        //setonclickListener **working day***
        work_check_mon.setOnCheckedChangeListener(onCheckedChangeListener);
        work_check_tue.setOnCheckedChangeListener(onCheckedChangeListener);
        work_check_wed.setOnCheckedChangeListener(onCheckedChangeListener);
        work_check_thu.setOnCheckedChangeListener(onCheckedChangeListener);
        work_check_fri.setOnCheckedChangeListener(onCheckedChangeListener);
        work_check_sat.setOnCheckedChangeListener(onCheckedChangeListener);
        work_check_sun.setOnCheckedChangeListener(onCheckedChangeListener);

        //findby **off day***
//        off_day_check_mon = rateview.findViewById(R.id.off_day_check_mon);
//        off_day_check_tue = rateview.findViewById(R.id.off_day_check_tue);
//        off_day_check_wed = rateview.findViewById(R.id.off_day_check_wed);
//        off_day_check_thu = rateview.findViewById(R.id.off_day_check_thu);
//        off_day_check_fri = rateview.findViewById(R.id.off_day_check_fri);
//        off_day_check_sat = rateview.findViewById(R.id.off_day_check_sat);
//        off_day_check_sun = rateview.findViewById(R.id.off_day_check_sun);

//        //findby **paymentmode***
//        chkArray[0] = checkbox_cash = rateview.findViewById(R.id.checkbox_cash);
//        chkArray[1] = checkbox_cheque = rateview.findViewById(R.id.checkbox_cheque);
//        chkArray[2] = checkbox_credit = rateview.findViewById(R.id.checkbox_credit);
//        chkArray[3] = checkbox_debit = rateview.findViewById(R.id.checkbox_debit);
        //setonclickListener **paymentmode***
//        chkArray[0].setOnClickListener(mListener);
//        chkArray[1].setOnClickListener(mListener);
//        chkArray[2].setOnClickListener(mListener);
//        chkArray[3].setOnClickListener(mListener);

        edt_working_time = rateview.findViewById(R.id.edt_working_time);
        edt_year = rateview.findViewById(R.id.edt_year);
        edt_annual_turnover = rateview.findViewById(R.id.edt_annual_turnover);
        edt_number_of_emp = rateview.findViewById(R.id.edt_number_of_emp);
        edt_booking_time = rateview.findViewById(R.id.edt_booking_time);
        edt_busi_no_of_people = rateview.findViewById(R.id.edt_busi_people);
        edt_certification = rateview.findViewById(R.id.edt_certification);
        mDialog = new SimpleArcDialog(getContext());
        mDialog.setCancelable(false);

        edt_booking_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        edt_booking_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();

            }
        });




    }


    private CheckBox.OnCheckedChangeListener onCheckedChangeListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.work_check_mon:
                    if (isChecked) workingday.add("Monday");
                    else workingday.remove("Monday");
                    break;
                case R.id.work_check_tue:
                    if (isChecked) workingday.add("Tuesday");
                    else workingday.remove("Tuesday");
                    break;
                case R.id.work_check_wed:
                    if (isChecked) workingday.add("Wednesday");
                    else workingday.remove("Wednesday");
                    break;
                case R.id.work_check_thu:
                    if (isChecked) workingday.add("Thursday");
                    else workingday.remove("Thursday");
                    break;
                case R.id.work_check_fri:
                    if (isChecked) workingday.add("Friday");
                    else workingday.remove("Friday");
                    break;
                case R.id.work_check_sat:
                    if (isChecked) workingday.add("Saturday");
                    else workingday.remove("Saturday");
                    break;
                case R.id.work_check_sun:
                    if (isChecked) workingday.add("Sunday");
                    else workingday.remove("Sunday");
                    break;
            }
            WorkingDayResult = TextUtils.join(",", workingday);
            Log.d("raj", "onCheckedChanged: " + WorkingDayResult);
        }

    };

//    private View.OnClickListener mListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            final int checkedId = v.getId();
//            for (int i = 0; i < chkArray.length; i++) {
//                final CheckBox current = chkArray[i];
//                if (current.getId() == checkedId) {
//                    current.setChecked(true);
//                    Payment_mode = current.getText().toString().trim();
//                    Log.d("Payment_mode", "onClick: " + Payment_mode);
//                } else {
//                    current.setChecked(false);
//                }
//            }
//        }
//    };


    private boolean validateFields() {
        List<Boolean> validationResultlist=new ArrayList<>();
        String businessTypeId=Shared_Preferences.getPrefs(getContext(), "business_type_id");
        if(businessTypeId.equals("1"))
        {
            validationResultlist.add(MyValidator.isValidField(edt_working_time));
            validationResultlist.add(MyValidator.isValidField(edt_booking_time));
        }

       return !validationResultlist.contains(false);
    }


    private void getAdd_Business() {
        mDialog.show();
        Log.d("business_info_name", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "busi_name"));
        Log.d("owner_name", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), Constants.REG_NAME));
        Log.d("business_type", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "busi_type"));
        Log.d("service_id", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "busi_cate_type"));
        Log.d("sub_service_id", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "busi_sub_type"));
        Log.d("sub_sub_service_id", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "busi_sub_sub_type"));
        Log.d("business_description", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "description"));
        Log.d("address", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "address"));
        Log.d("district", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "district"));
        Log.d("taluka", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "taluka"));
        Log.d("village", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "village"));
        Log.d("area", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "area"));
        Log.d("landmark", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "landmark"));
        Log.d("building_name", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "building_name"));
        Log.d("pincode", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "pincode"));
        Log.d("contact_person", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "contact_person"));
        Log.d("designation", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "designation"));
        Log.d("mobile_no", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "mobile"));
        Log.d("telephone_no", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "telephone"));
        Log.d("business_website", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "website"));
        Log.d("business_email", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "email"));
        Log.d("facebook_link", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "facebook"));
        Log.d("twitter_link", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "twitter"));
        Log.d("youtube_link", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "youtube"));
        Log.d("google_map_link", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "google"));
        Log.d("working_days", "getAdd_Business: " + WorkingDayResult);
        Log.d("working_time", "getAdd_Business: " + edt_working_time.getText().toString().trim());
        //Log.d("payment_mode", "getAdd_Business: " + Payment_mode);
        Log.d("establishment_year", "getAdd_Business: " + edt_year.getText().toString().trim());
        Log.d("annual_turnover", "getAdd_Business: " + edt_annual_turnover.getText().toString().trim());
        Log.d("number_of_employees", "getAdd_Business: " + edt_number_of_emp.getText().toString().trim());
        Log.d("no_of_person", "getAdd_Business: " + edt_busi_no_of_people.getText().toString().trim());
        Log.d("certification", "getAdd_Business: " + edt_certification.getText().toString().trim());
        Log.d("country", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "country"));
        Log.d("state", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), "state"));
        Log.d("vender_id", "getAdd_Business: " + Shared_Preferences.getPrefs(getContext(), Constants.REG_ID));

        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        MultipartBody.Part body = null;
        try {
            File file = new File(Shared_Preferences.getPrefs(getContext(), "Image"));
            Log.d("body", "uploadFile: " + Shared_Preferences.getPrefs(getContext(), "Image"));

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            Log.d("body", "getAdd_Business: " + requestFile);
            body = MultipartBody.Part.createFormData("business_image", file.getName(), requestFile);
            Log.d("body", "uploadFile: " + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody business_info_name = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "busi_name"));
        RequestBody owner_name = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), Constants.REG_NAME));
        RequestBody business_type = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "busi_type"));
        RequestBody service_id = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "busi_cate_type"));
        RequestBody sub_service_id = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "busi_sub_type"));
        RequestBody sub_sub_service_id = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "busi_sub_sub_type"));
        RequestBody business_description = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "description"));
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "address"));
        RequestBody district = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "district"));
        RequestBody taluka = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "taluka"));
        RequestBody village = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "village"));
        RequestBody area = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "area"));
        RequestBody landmark = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "landmark"));
        RequestBody building_name = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "building_name"));
        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "pincode"));
        RequestBody contact_person = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "contact_person"));
        RequestBody designation = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "designation"));
        RequestBody mobile_no = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "mobile"));
        RequestBody telephone_no = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "telephone"));
        RequestBody business_website = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "website"));
        RequestBody business_email = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "email"));
        RequestBody facebook_link = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "facebook"));
        RequestBody twitter_link = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "twitter"));
        RequestBody youtube_link = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "youtube"));
        RequestBody google_map_link = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "google"));
        RequestBody working_days = RequestBody.create(MediaType.parse("text/plain"), WorkingDayResult);
        RequestBody working_time = RequestBody.create(MediaType.parse("text/plain"), edt_working_time.getText().toString().trim());
        //RequestBody payment_mode = RequestBody.create(MediaType.parse("text/plain"), Payment_mode);
        RequestBody establishment_year = RequestBody.create(MediaType.parse("text/plain"), edt_year.getText().toString().trim());
        RequestBody service_charges = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody annual_turnover = RequestBody.create(MediaType.parse("text/plain"), edt_annual_turnover.getText().toString().trim());
        RequestBody number_of_employees = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody no_of_person = RequestBody.create(MediaType.parse("text/plain"), edt_busi_no_of_people.getText().toString().trim());
        RequestBody certification = RequestBody.create(MediaType.parse("text/plain"), edt_certification.getText().toString().trim());
        RequestBody aadhar_card = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody country = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "country"));
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), "state"));
        RequestBody vender_id = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(getContext(), Constants.REG_ID));
        RequestBody book_time = RequestBody.create(MediaType.parse("text/plain"), edt_booking_time.getText().toString().trim());
        Call<ResponseBody> call;
        call = apiInterface.getadd_business(
                business_info_name,
                owner_name,
                business_type,
                service_id,
                sub_service_id,
                sub_sub_service_id,
                business_description,
                address,
                country,
                state,
                district,
                taluka,
                village,
                area,
                landmark,
                building_name,
                pincode,
                contact_person,
                designation,
                mobile_no,
                telephone_no,
                business_website,
                business_email,
                facebook_link,
                twitter_link,
                youtube_link,
                google_map_link,
                working_days,
                working_time,//interval_time
                no_of_person,
                //payment_mode,
                establishment_year,
                annual_turnover,
                number_of_employees,
                certification,
                vender_id,
                book_time,
                body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Log.v("Upload", "success");
                if (response.isSuccessful()) {
                    String output = "";
                    try {
                        output = response.body().string();
                        Log.d("mytag", "response23 " + output);
                        JSONObject jsonObject = new JSONObject(output);
                        Log.d("mytag", output);
                        if (jsonObject.getString("ResponseCode").equals("1")) {
                            mDialog.dismiss();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                            alertDialogBuilder.setMessage(jsonObject.getString("ResponseMessage"));
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();

                        } else {
                            Toast.makeText(getContext(), "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("##", "Exception "+e.getMessage(),e);
                    }
                } else {
                    Toast.makeText(getContext(), "Somwthing went wrong", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
                Log.d("mytag:", ""+t.getMessage());
                t.printStackTrace();
            }
        });

    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback)
    {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        Log.d("mytag","onCompleteClicked");
        if (validateFields()) {

            if(businessTypeId.equals("2"))
            {
                getAdd_Business();
            }else{
                if (!WorkingDayResult.contains("") || !WorkingDayResult.isEmpty()) {
//                if (!Payment_mode.contains("") || !Payment_mode.isEmpty()) {
                    getAdd_Business();
//                } else {
//                    Toast.makeText(getContext(), "Please Payment Option", Toast.LENGTH_SHORT).show();
//                }
                }
                else
                {
                    Toast.makeText(getContext(), "Please Select Working Days", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else
        {
            Toast.makeText(getContext(), "Fill The Complete Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
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
