package com.example.karadvenderapp.Adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Activity.PackageActivity;
import com.example.karadvenderapp.Activity.ViewDetailsActivity;
import com.example.karadvenderapp.Model.BusinessModel;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.MyHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<BusinessModel> modelList;
    OnItemClickListener onItemClickListener;
    private Dialog dialog;
    private Button btn_yes, btn_no;
    Date date1 = null;
    Date date2 = null;
    public BusinessListAdapter(Context context, List<BusinessModel> modelList, OnItemClickListener onItemTouchListener) {
        this.context = context;
        this.modelList = modelList;
        this.onItemClickListener = onItemTouchListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_business_item_layout, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position)
    {
        final BusinessModel list = modelList.get(position);
        holder.tv_busi_name.setText(list.getBusiness_info_name());
        holder.tv_busi_type.setText(list.getFld_business_name());
        holder.tv_district.setText(list.getFld_area_name() + "," + list.getFld_district_name());
        holder.tv_mobile.setText(list.getMobile_no());
        holder.tv_desc.setText("Description: " + list.getBusiness_description());


        Picasso.with(context)
                .load(list.getBusiness_image())
                .error(R.drawable.app_logo)
                .placeholder(R.drawable.app_logo)
                .into(holder.iv_busi_profile);

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(modelList.get(position).getBusiness_info_id(), position);
            }
        });

//        holder.itemView.setOnClickListener(view -> sharedElementListener.SharedElmentCallback(position,holder.iv_busi_profile,holder.tv_desc,modelList));





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    date1 = sdf.parse(list.getEnd_date());

                    Log.e("Response", "date1: " +date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    date2 = sdf.parse(Shared_Preferences.getPrefs(context,Constants.REG_SERVERDATE));
                    Log.e("Response", "date2: " +date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                 if (date1.after(date2))
                    {
                        Log.d("TAGDate", "onBindViewHolder: "+"details "+date1+" "+date2);
                    }
                 else
                 {
                     Log.d("TAGDate", "onBindViewHolder: "+"packge "+date1+" "+date2);

                }

                if (date1.after(date2))
                {

                    Intent intent = new Intent(context, ViewDetailsActivity.class);
                    intent.putExtra("getBusiness_info_id",list.getBusiness_info_id());
                    intent.putExtra("getBusiness_info_name",list.getBusiness_info_name());
                    intent.putExtra("getOwner_name",list.getOwner_name());
                    intent.putExtra("getFld_business_id",list.getFld_business_id());
                    intent.putExtra("getFld_business_name",list.getFld_business_name());
                    intent.putExtra("getFld_business_category_id",list.getFld_business_category_id());
                    intent.putExtra("getFld_business_subcategory_id",list.getFld_business_subcategory_id());
                    intent.putExtra("getFld_business_subsubcategory_id",list.getFld_business_subsubcategory_id());
                    intent.putExtra("getBusiness_description",list.getBusiness_description());
                    intent.putExtra("getAddress",list.getAddress());
                    intent.putExtra("getFld_country_id",list.getFld_country_id());
                    intent.putExtra("getFld_state_id",list.getFld_state_id());
                    intent.putExtra("getFld_district_id",list.getFld_district_id());
                    intent.putExtra("getFld_taluka_id",list.getFld_taluka_id());
                    intent.putExtra("getFld_city_id",list.getFld_city_id());
                    intent.putExtra("getFld_area_id",list.getFld_area_id());
                    intent.putExtra("getFld_landmark_id",list.getFld_landmark_id());
                    intent.putExtra("getBuilding_name",list.getBuilding_name());
                    intent.putExtra("getPincode",list.getPincode());
                    intent.putExtra("getContact_person",list.getContact_person());
                    intent.putExtra("getDesignation",list.getDesignation());
                    intent.putExtra("getMobile_no",list.getMobile_no());
                    intent.putExtra("getTelephone_no",list.getTelephone_no());
                    intent.putExtra("getBusiness_website",list.getBusiness_website());
                    intent.putExtra("getBusiness_email",list.getBusiness_email());
                    intent.putExtra("getFacebook_link",list.getFacebook_link());
                    intent.putExtra("getTwitter_link",list.getTwitter_link());
                    intent.putExtra("getYoutube_link",list.getYoutube_link());
                    intent.putExtra("getGoogle_map_link",list.getGoogle_map_link());
                    intent.putExtra("getWorking_days",list.getWorking_days());
                    intent.putExtra("getStart_working_time",list.getStart_working_time());
                    intent.putExtra("getEnd_working_time",list.getEnd_working_time());
                    intent.putExtra("getInterval_time",list.getInterval_time());
                    intent.putExtra("getNo_of_people",list.getNo_of_people());
                    intent.putExtra("getPayment_mode",list.getPayment_mode());
                    intent.putExtra("getEstablishment_year",list.getEstablishment_year());
                    intent.putExtra("getAnnual_turnover",list.getAnnual_turnover());
                    intent.putExtra("getNumber_of_employees",list.getNumber_of_employees());
                    intent.putExtra("getCertification",list.getCertification());
                    intent.putExtra("getBusiness_image",list.getBusiness_image());
                    intent.putExtra("get_booking_time",list.getEnd_working_time());//change for booking time

                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, PackageActivity.class);
                    intent.putExtra("business_id", list.getBusiness_info_id());
                    intent.putExtra("getFld_business_id",list.getFld_business_id());
                    Shared_Preferences.setPrefs(context,Constants.BUID,list.getBusiness_info_id());
                    context.startActivity(intent);
                }
            }
        });
    }

    private void showDialog(String id, final int position) {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        btn_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_no = dialog.findViewById(R.id.btn_logout_no);
        TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
        text_msg.setText("Are you sure you want to delete This Business");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBusinee(id, position);
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteBusinee(String string, final int position) {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.delete_business(string);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String output = response.body().string();

                    Log.d("Response", "Bdelete=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        onItemClickListener.onItemClick(true);
                        modelList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                    } else {
                        onItemClickListener.onItemClick(false);
                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_busi_name, tv_busi_type, tv_district, tv_mobile, tv_desc;
        ImageView iv_delete, tv_ViewDetailsActivity, iv_busi_profile;
        public MyHolder(View view) {
            super(view);
            tv_busi_name = (TextView) view.findViewById(R.id.tv_busi_name);
            tv_busi_type = (TextView) view.findViewById(R.id.tv_busi_type);
            tv_district = (TextView) view.findViewById(R.id.tv_district);
            tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_ViewDetailsActivity = (ImageView) view.findViewById(R.id.tv_ViewDetailsActivity);
            iv_busi_profile = (ImageView) view.findViewById(R.id.iv_busi_profile);
            iv_delete = (ImageView) view.findViewById(R.id.iv_delete);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(boolean flag);
    }
}
