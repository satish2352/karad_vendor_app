package com.example.karadvenderapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.karadvenderapp.Adapter.InvoiceListAdapter;
import com.example.karadvenderapp.Model.InvoiceList;
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

public class MyInvoiceActivity extends AppCompatActivity
{
    private List<InvoiceList> ModelList = new ArrayList<>();
    private InvoiceListAdapter mAdapter;
    private RecyclerView rel_busines_invoice;
    private SimpleArcDialog mDialog;
    private RelativeLayout noRecordLayout;
    private String business_id = "",business_Name = "";
    private SwipeRefreshLayout SwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invoice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My InVoice");
        mDialog = new SimpleArcDialog(MyInvoiceActivity.this);
        mDialog.setCancelable(false);
//        Intent intent = getIntent();
//        business_id = intent.getStringExtra("my_id");
//        business_Name = intent.getStringExtra("my_name");
        SwipeRefresh = findViewById(R.id.SwipeRefresh);

        MyBusiness();

        rel_busines_invoice = findViewById(R.id.rel_busines_invoice);
        noRecordLayout = findViewById(R.id.noRecordLayout);

        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyBusiness();
            }
        });

    }

    private void MyBusiness()
    {
        mDialog.show();
        ModelList.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getInVoice(Shared_Preferences.getPrefs(MyInvoiceActivity.this, Constants.REG_ID),
                "Completed");
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                SwipeRefresh.setRefreshing(false);
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
                            ModelList.add(new InvoiceList(object));
                        }
                        noRecordLayout.setVisibility(View.GONE);
                        mAdapter = new InvoiceListAdapter(MyInvoiceActivity.this, ModelList);
                        rel_busines_invoice.setAdapter(mAdapter);
                        rel_busines_invoice.setLayoutManager(new LinearLayoutManager(MyInvoiceActivity.this));
                    } else {
                        noRecordLayout.setVisibility(View.VISIBLE);
                        SwipeRefresh.setRefreshing(false);
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

                mDialog.dismiss();
                SwipeRefresh.setRefreshing(false);

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