package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karadvenderapp.Adapter.BusinessVarientListAdapter;
import com.example.karadvenderapp.Model.BusinessVarientModel;
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
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBusinessActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,BusinessVarientListAdapter.OnItemClickListener {
    private List<BusinessVarientModel> ModelList = new ArrayList<>();
    private TextView tv_business,tv_user;
    private BusinessVarientListAdapter mAdapter;
    private RecyclerView varient_business;
    private SimpleArcDialog mDialog;
    private RelativeLayout noRecordLayout;
    private String business_id = "",business_Name = "";
    private SwipeRefreshLayout SwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_business);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Business");
        mDialog = new SimpleArcDialog(MyBusinessActivity.this);
        mDialog.setCancelable(false);
        Intent intent = getIntent();
        business_id = intent.getStringExtra("my_id");
        business_Name = intent.getStringExtra("my_name");
        SwipeRefresh = findViewById(R.id.SwipeRefresh);
        SwipeRefresh.setOnRefreshListener(this);
        SwipeRefresh.post(new Runnable()
                          {
                              @Override
                              public void run()
                              {
                                  SwipeRefresh.setRefreshing(true);
                                  MyBusiness(business_id);
                              }
                          }
        );
        varient_business = findViewById(R.id.varient_business);
        noRecordLayout = findViewById(R.id.noRecordLayout);
        tv_business = findViewById(R.id.tv_business);
        tv_business.setText(business_Name);
        tv_user = findViewById(R.id.tv_user);
        tv_user.setText("  WelCome "+Shared_Preferences.getPrefs(MyBusinessActivity.this, Constants.REG_NAME));


    }

    private void MyBusiness(String business_id) {
        mDialog.show();
        ModelList.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getbusi_Data(business_id);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "response=>" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ModelList.add(new BusinessVarientModel(object));
                        }
                        noRecordLayout.setVisibility(View.GONE);
                        SwipeRefresh.setRefreshing(false);
                        mAdapter = new BusinessVarientListAdapter(MyBusinessActivity.this, ModelList,MyBusinessActivity.this);
                        varient_business.setAdapter(mAdapter);
                        varient_business.setLayoutManager(new LinearLayoutManager(MyBusinessActivity.this));
                        Toast.makeText(MyBusinessActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else {
                        SwipeRefresh.setRefreshing(false);
                        noRecordLayout.setVisibility(View.VISIBLE);
                    }
                    mDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
                SwipeRefresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                mDialog.dismiss();
                SwipeRefresh.setRefreshing(false);

            }
        });
    }


    @Override
    public void onRefresh() {

        MyBusiness(business_id);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(boolean flag) {
        if (flag){
            MyBusiness(business_id);
        }
    }
}