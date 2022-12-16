package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.karadvenderapp.Adapter.DisapproveAppointmentAdapter;
import com.example.karadvenderapp.Adapter.DisapproveServiceAdapter;
import com.example.karadvenderapp.Adapter.RequestAppointmentAdapter;
import com.example.karadvenderapp.Adapter.RequestServiceAdapter;
import com.example.karadvenderapp.Model.DisapproveAppointmentList;
import com.example.karadvenderapp.Model.DisapproveServiceList;
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

public class RequestDisapproveActivity extends AppCompatActivity
{

    RecyclerView rec;
    private ArrayList<DisapproveAppointmentList> applist = new ArrayList<DisapproveAppointmentList>();
    private ArrayList<DisapproveServiceList> serlist = new ArrayList<DisapproveServiceList>();

    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_disapprove);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Disapproved Request");

        rec=findViewById(R.id.Recdisapproverequest);
        mDialog = new SimpleArcDialog(RequestDisapproveActivity.this);
        mDialog.setCancelable(false);

        getdisapprovelist();
    }

    private void getdisapprovelist()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.disapprovedRequest(Shared_Preferences.getPrefs(RequestDisapproveActivity.this, Constants.REG_ID),
                Shared_Preferences.getPrefs(RequestDisapproveActivity.this, Constants.BUSINESSINFOID),
                Shared_Preferences.getPrefs(RequestDisapproveActivity.this,"getFld_business_id"));
        result.enqueue(new Callback<ResponseBody>() {
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
                            Log.e("Disapprove",""+object);

                            String id = object.getString("fld_business_id");
                            if(id.equals("1"))
                            {
                                applist.add(new DisapproveAppointmentList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestDisapproveActivity.this,RecyclerView.VERTICAL,false));
                                DisapproveAppointmentAdapter adapter= new DisapproveAppointmentAdapter(RequestDisapproveActivity.this,applist);
                                rec.setAdapter(adapter);
                            }
                            else if(id.equals("2"))
                            {
                                serlist.add(new DisapproveServiceList(object));
                                rec.setLayoutManager(new LinearLayoutManager(RequestDisapproveActivity.this,RecyclerView.VERTICAL,false));
                                DisapproveServiceAdapter seradapter= new DisapproveServiceAdapter(RequestDisapproveActivity.this,serlist);
                                rec.setAdapter(seradapter);
                            }
                        }

                    }
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
}