package com.example.karadvenderapp.Fragment;

import android.app.DatePickerDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karadvenderapp.Activity.LoginActivity;
import com.example.karadvenderapp.Activity.RegisterNew;
import com.example.karadvenderapp.MyLib.MyValidator;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenewalRequestFragment extends Fragment {
    private EditText edt_owner_name, edt_mobile_no, edt_request_Date;
    private View rootView;
    private Button btn_submit;
    private int mYear, mMonth, mDay;
    private SimpleArcDialog mDialog;

    public RenewalRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_renewal_request, container, false);
        edt_owner_name = rootView.findViewById(R.id.edt_owner_name);
        edt_mobile_no = rootView.findViewById(R.id.edt_mobile_no);
        edt_request_Date = rootView.findViewById(R.id.edt_request_Date);
        btn_submit = rootView.findViewById(R.id.btn_submit);
        mDialog = new SimpleArcDialog(getContext());
        mDialog.setCancelable(false);
        edt_request_Date.setFocusableInTouchMode(false);
        edt_request_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_request_Date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                                edt_end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (validateFields()) {
                   getsend_req();
                    } else {

                    }
            }
        });

        return rootView;
    }

    private boolean validateFields() {
        boolean result = true;
        if (!MyValidator.isValidField(edt_owner_name)) {
            result = false;
        }   if (!MyValidator.isValidMobile(edt_mobile_no)) {
            result = false;
        }   if (!MyValidator.isValidField(edt_request_Date)) {
            result = false;
        }
        return result;
    }

    private void getsend_req() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.sendrequest(edt_owner_name.getText().toString(),edt_mobile_no.getText().toString(),edt_request_Date.getText().toString());
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
                        edt_owner_name.setText("");
                                edt_mobile_no.setText("");
                        edt_request_Date.setText("");
                        Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show();
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
}
