package com.example.karadvenderapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Model.PackageModelList;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.example.karadvenderapp.RazorPayActivity;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class packageListAdapter extends RecyclerView.Adapter<packageListAdapter.MyHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<PackageModelList> modelList;
    APIInterface apiInterface;
    String order_id;
    private Retrofit.Builder retrofit = null;
    private SimpleArcDialog mDialog;
    private String AMount = "";

    public packageListAdapter(Context context, List<PackageModelList> modelList)
    {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_item_layout, parent, false);
        Checkout.preload(context);
        mDialog = new SimpleArcDialog(context);
        mDialog.setCancelable(false);
        return new MyHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position)
    {
        final PackageModelList list = modelList.get(position);
        holder.package_type.setText(list.getFld_package_name());
        holder.busi_name.setText(list.getFld_business_name());
        holder.tv_no_days.setText("Validity " + list.getFld_validity_in_days_number() + "  days");
        holder.tv_price.setText(Constants.rs + "" + list.getFld_package_amount());



        if (list.getFld_package_name().equals("Gold")) {
            holder.verical_view.setBackgroundColor(context.getResources().getColor(R.color.gold));
            holder.package_type.setTextColor(context.getResources().getColor(R.color.gold));
            holder.linear_circle.setBackground(context.getResources().getDrawable(R.drawable.circle_gold));
            holder.btn_shop.setBackground(context.getResources().getDrawable(R.drawable.button_background_gold));
        } else if (list.getFld_package_name().equals("Dimand")) {
            holder.verical_view.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.package_type.setTextColor(context.getResources().getColor(R.color.green));
            holder.linear_circle.setBackground(context.getResources().getDrawable(R.drawable.circle_green));
            holder.btn_shop.setBackground(context.getResources().getDrawable(R.drawable.button_background_green));
        } else if (list.getFld_package_name().equals("Silver")) {
            holder.verical_view.setBackgroundColor(context.getResources().getColor(R.color.silver));
            holder.package_type.setTextColor(context.getResources().getColor(R.color.silver));
            holder.linear_circle.setBackground(context.getResources().getDrawable(R.drawable.circle_silver));
            holder.btn_shop.setBackground(context.getResources().getDrawable(R.drawable.button_background_silver));

        }

        holder.btn_shop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                GetOrderid(list.getFld_package_id(), list.getFld_package_amount(),list.getFld_business_id());

                Log.e("package-->",""+list.getFld_package_id());
                Log.e("package-->",""+list.getFld_package_amount());
                Log.e("package-->",""+list.getFld_business_id());
            }
        });
    }

    private void GetOrderid(String Package_id, String Amount,String busi_id)
    {
        mDialog.show();
        apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getorder_id(Shared_Preferences.getPrefs(context, Constants.REG_ID),
                Shared_Preferences.getPrefs(context, Constants.BUID),
                Package_id,
                Amount//Amount
                 );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getString("result").equals("true"))
                    {
                        Log.d("jsonObject", "onResponse: " + jsonObject);
                        JSONObject json = jsonObject.getJSONObject("data");
                        order_id = json.getString("orderId");
                        Intent intent = new Intent(context, RazorPayActivity.class);
                        intent.putExtra("package_id", Package_id);
                        intent.putExtra("package_amount", Amount);
                        intent.putExtra("order_id", order_id);
                        intent.putExtra("fld_business_id", busi_id);
                        context.startActivity(intent);
                    }
                    else
                        {

                    }
                    mDialog.dismiss();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });

    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView package_type, busi_name, tv_no_days, tv_price;
        RelativeLayout verical_view;
        Button btn_shop;
        LinearLayout linear_circle;

        public MyHolder(View view) {
            super(view);
            package_type = (TextView) view.findViewById(R.id.package_type);
            busi_name = (TextView) view.findViewById(R.id.busi_name);
            tv_no_days = (TextView) view.findViewById(R.id.tv_no_days);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            verical_view = (RelativeLayout) view.findViewById(R.id.verical_view);
            btn_shop = (Button) view.findViewById(R.id.btn_shop);
            linear_circle = (LinearLayout) view.findViewById(R.id.linear_circle);

        }
    }
}
