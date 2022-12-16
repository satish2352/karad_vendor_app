package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class RequestCompleteActivity extends AppCompatActivity 
{
    RecyclerView rec;
    private ArrayList<RequestAppointmentList> appointmentList = new ArrayList<RequestAppointmentList>();
    private ArrayList<RequestServiceList> serviceList = new ArrayList<RequestServiceList>();

    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_complete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Completed Request");

        rec=findViewById(R.id.Reccompleterequest);
        mDialog = new SimpleArcDialog(RequestCompleteActivity.this);
        mDialog.setCancelable(false);


        getcompletelist();
    }

    private void getcompletelist()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.completeRequest(Shared_Preferences.getPrefs(RequestCompleteActivity.this, Constants.REG_ID),
                Shared_Preferences.getPrefs(RequestCompleteActivity.this, Constants.BUSINESSINFOID),
                Shared_Preferences.getPrefs(RequestCompleteActivity.this,"getFld_business_id"));
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
                            if(id.equals("1"))
                            {
                                //Log.e("UserObject",""+object);
                                appointmentList.add(new RequestAppointmentList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestCompleteActivity.this,RecyclerView.VERTICAL,false));
                                RequestAppointmentAdapter appointmentAdapter= new RequestAppointmentAdapter(RequestCompleteActivity.this,appointmentList);
                                rec.setAdapter(appointmentAdapter);
                            }
                            else if(id.equals("2"))
                            {
                                serviceList.add(new RequestServiceList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestCompleteActivity.this,RecyclerView.VERTICAL,false));
                                RequestServiceAdapter serviceAdapter= new RequestServiceAdapter(RequestCompleteActivity.this,serviceList);
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