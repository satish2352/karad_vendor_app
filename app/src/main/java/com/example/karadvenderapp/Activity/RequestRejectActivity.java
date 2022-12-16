package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.karadvenderapp.Adapter.RejectAppointmentAdapter;
import com.example.karadvenderapp.Adapter.RejectServiceAdapter;
import com.example.karadvenderapp.Adapter.RequestAppointmentAdapter;
import com.example.karadvenderapp.Adapter.RequestServiceAdapter;
import com.example.karadvenderapp.Model.RejectAppointmentList;
import com.example.karadvenderapp.Model.RejectServiceList;
import com.example.karadvenderapp.Model.RequestAppointmentList;
import com.example.karadvenderapp.Model.RequestServiceList;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestRejectActivity extends AppCompatActivity
{

    RecyclerView rec;
    private ArrayList<RejectAppointmentList> appointmentList = new ArrayList<RejectAppointmentList>();
    private ArrayList<RejectServiceList> serviceList = new ArrayList<RejectServiceList>();

    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_reject);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Rejected Request");
        rec=findViewById(R.id.Recreject);

        mDialog = new SimpleArcDialog(RequestRejectActivity.this);
        mDialog.setCancelable(false);


//        Toast.makeText(this, ""+ Shared_Preferences.getPrefs(RequestRejectActivity.this, Constants.REG_ID), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, ""+Shared_Preferences.getPrefs(RequestRejectActivity.this, Constants.BUSINESSINFOID), Toast.LENGTH_SHORT).show();
        getRejectedList();
    }

    private void getRejectedList()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.rejectRequest(Shared_Preferences.getPrefs(RequestRejectActivity.this, Constants.REG_ID),
                Shared_Preferences.getPrefs(RequestRejectActivity.this, Constants.BUSINESSINFOID),
                Shared_Preferences.getPrefs(RequestRejectActivity.this,"getFld_business_id"));
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "getRequest:-" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.e("UserObject",""+object);
                            String id = object.getString("fld_business_id");
                            if(id.equals("1"))
                            {
                                appointmentList.add(new RejectAppointmentList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestRejectActivity.this,RecyclerView.VERTICAL,false));
                                RejectAppointmentAdapter appointmentAdapter= new RejectAppointmentAdapter(RequestRejectActivity.this,appointmentList);
                                rec.setAdapter(appointmentAdapter);
                            }
                            else if(id.equals("2"))
                            {
                                serviceList.add(new RejectServiceList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestRejectActivity.this,RecyclerView.VERTICAL,false));
                                RejectServiceAdapter serviceAdapter= new RejectServiceAdapter(RequestRejectActivity.this,serviceList);
                                rec.setAdapter(serviceAdapter);
                            }

                        }
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}