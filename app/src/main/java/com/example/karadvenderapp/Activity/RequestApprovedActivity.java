package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.karadvenderapp.Adapter.RequestAppointmentAdapter;
import com.example.karadvenderapp.Adapter.RequestServiceAdapter;
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

public class RequestApprovedActivity extends AppCompatActivity
{
    RecyclerView rec;
    private ArrayList<RequestAppointmentList> appointmentList = new ArrayList<RequestAppointmentList>();
    private ArrayList<RequestServiceList> serviceList = new ArrayList<RequestServiceList>();

    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_approved);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Approved Request");

        mDialog = new SimpleArcDialog(RequestApprovedActivity.this);
        mDialog.setCancelable(false);

        rec=findViewById(R.id.Recacceptrequest);

        getApprovedList();
    }

    private void getApprovedList()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.acceptRequest(Shared_Preferences.getPrefs(RequestApprovedActivity.this, Constants.REG_ID),
                Shared_Preferences.getPrefs(RequestApprovedActivity.this, Constants.BUSINESSINFOID),
                Shared_Preferences.getPrefs(RequestApprovedActivity.this,"getFld_business_id"));
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

                            String id = object.getString("fld_business_id");

                            Log.e("List",""+object);
                            if(id.equals("1"))
                            {
                                //Log.e("UserObject",""+object);
                                appointmentList.add(new RequestAppointmentList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestApprovedActivity.this,RecyclerView.VERTICAL,false));
                                RequestAppointmentAdapter appointmentAdapter= new RequestAppointmentAdapter(RequestApprovedActivity.this,appointmentList);
                                rec.setAdapter(appointmentAdapter);
                            }
                            else if(id.equals("2"))
                            {
                                serviceList.add(new RequestServiceList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestApprovedActivity.this,RecyclerView.VERTICAL,false));
                                RequestServiceAdapter serviceAdapter= new RequestServiceAdapter(RequestApprovedActivity.this,serviceList);
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