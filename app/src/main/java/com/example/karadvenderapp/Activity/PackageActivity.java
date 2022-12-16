package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.karadvenderapp.Model.PackageModelList;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.example.karadvenderapp.Adapter.packageListAdapter;

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

public class PackageActivity extends AppCompatActivity {
    private List<PackageModelList> ModelList;
    private packageListAdapter mAdapter;
    private RecyclerView recycler_view;
    private SimpleArcDialog mDialog;
    private String businesstype;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Packages");
        mDialog = new SimpleArcDialog(PackageActivity.this);
        mDialog.setCancelable(false);
        recycler_view=findViewById(R.id.recycler_view);
        PackageListting();

        businesstype = getIntent().getStringExtra("getFld_business_id");


    }


    private void PackageListting() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getpakage();

        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "response==>" + output);
                    Log.e("error", "DataResponse" +response);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");

                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        ModelList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            //ModelList.add(new PackageModelList(object));


                            String id = object.getString("fld_business_id");
                            if(id.equals(businesstype.toString()))
                            {
                                ModelList.add(new PackageModelList(object));
                            }

                        }

                        mAdapter = new packageListAdapter(PackageActivity.this, ModelList);
                        recycler_view.setAdapter(mAdapter);
                        recycler_view.setLayoutManager(new LinearLayoutManager(PackageActivity.this));
                        Toast.makeText(PackageActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                mDialog.dismiss();
                Toast.makeText(PackageActivity.this, ""+getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}