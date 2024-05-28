package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.karadvenderapp.Adapter.RequestAppointmentAdapter;
import com.example.karadvenderapp.Adapter.RequestServiceAdapter;
import com.example.karadvenderapp.Adapter.UserCancelAppointmentAdapter;
import com.example.karadvenderapp.Adapter.UserCancelServiceAdapter;
import com.example.karadvenderapp.Model.CancelUserAppointmentList;
import com.example.karadvenderapp.Model.CancelUserServiceList;
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

public class RequestCancelActivity extends AppCompatActivity
{

    RecyclerView rec;
    private ArrayList<CancelUserAppointmentList> appointmentList = new ArrayList<CancelUserAppointmentList>();
    private ArrayList<CancelUserServiceList> serviceList = new ArrayList<CancelUserServiceList>();

    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cancel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Canceled Request");

        mDialog = new SimpleArcDialog(RequestCancelActivity.this);
        mDialog.setCancelable(false);

        rec=findViewById(R.id.Reccancelrequest);

        getcancellist();

    }

    private void getcancellist()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.cancelRequest(Shared_Preferences.getPrefs(RequestCancelActivity.this, Constants.REG_ID),
                Shared_Preferences.getPrefs(RequestCancelActivity.this, Constants.BUSINESSINFOID),
                Shared_Preferences.getPrefs(RequestCancelActivity.this,"getFld_business_id"));
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
                                Log.e("UserObject",""+object);
                                appointmentList.add(new CancelUserAppointmentList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestCancelActivity.this,RecyclerView.VERTICAL,false));
                                UserCancelAppointmentAdapter appointmentAdapter= new UserCancelAppointmentAdapter(RequestCancelActivity.this,appointmentList);
                                rec.setAdapter(appointmentAdapter);
                            }
                            else if(id.equals("2"))
                            {
                                Log.e("UserObject",""+object);
                                serviceList.add(new CancelUserServiceList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestCancelActivity.this,RecyclerView.VERTICAL,false));
                                UserCancelServiceAdapter serviceAdapter= new UserCancelServiceAdapter(RequestCancelActivity.this,serviceList);
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

                mDialog.dismiss();

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