package com.example.karadvenderapp.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Model.RequestServiceList;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestServiceAdapter extends RecyclerView.Adapter<RequestServiceAdapter.ViewHolder>
{
    private Context context;
    private List<RequestServiceList> requestServiceLists;
    private SimpleArcDialog mDialog;
    public RequestServiceAdapter(Context context, List<RequestServiceList> requestServiceLists)
    {
        this.context = context;
        this.requestServiceLists = requestServiceLists;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.requestlayoutservice,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.name.setText(requestServiceLists.get(position).getUser_name());
        holder.number.setText(requestServiceLists.get(position).getUser_mobile());
        holder.email.setText(requestServiceLists.get(position).getUser_email());
        holder.bookedon.setText("Booked on:- "+requestServiceLists.get(position).getFld_service_requested_date());
        holder.reqitem.setText(requestServiceLists.get(position).getFld_business_details_name());

        if(requestServiceLists.get(position).getFld_service_issuedorreturned().equals("2"))
        {
            holder.layoutUserRequest.setVisibility(View.VISIBLE);
        }
        if(requestServiceLists.get(position).getFld_service_issuedorreturned().equals("0"))
        {
            holder.layoutApprove.setVisibility(View.VISIBLE);
        }

        holder.approve.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialog = new SimpleArcDialog(context);
                mDialog.setCancelable(false);
                mDialog.show();
                APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
                Call<ResponseBody> result = apiInterface.status(Shared_Preferences.getPrefs(context, Constants.REG_ID),
                        Shared_Preferences.getPrefs(context, Constants.BUSINESSINFOID), requestServiceLists.get(position).getFld_user_issued_servicesApp(),"0","Approved");

                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        mDialog.dismiss();
                        try {
                            String output = response.body().string();
                            Log.d("Response", "getRequest:-" + output);
                            JSONObject jsonObject = new JSONObject(output);
                            if (jsonObject.getString("result").equals("true"))
                            {
                                Toast.makeText(context, "Request Approved", Toast.LENGTH_SHORT).show();
                                requestServiceLists.remove(requestServiceLists.get(position));
                                notifyDataSetChanged();
                            }
                            mDialog.dismiss();
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
        });

        holder.disapprove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialog = new SimpleArcDialog(context);
                mDialog.setCancelable(false);
                mDialog.show();
                APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
                Call<ResponseBody> result = apiInterface.status(Shared_Preferences.getPrefs(context, Constants.REG_ID),
                        Shared_Preferences.getPrefs(context, Constants.BUSINESSINFOID), requestServiceLists.get(position).getFld_user_issued_servicesApp(),"2","Disapproved");

                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        mDialog.dismiss();
                        try {
                            String output = response.body().string();
                            Log.d("Response", "getRequest:-" + output);
                            JSONObject jsonObject = new JSONObject(output);
                            if (jsonObject.getString("result").equals("true"))
                            {
                                Toast.makeText(context, "Request Disapproved", Toast.LENGTH_SHORT).show();
                                requestServiceLists.remove(requestServiceLists.get(position));
                                notifyDataSetChanged();
                            }
                            mDialog.dismiss();
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
        });

        holder.btn_complete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialog = new SimpleArcDialog(context);
                mDialog.setCancelable(false);
                mDialog.show();
                APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
                Call<ResponseBody> result = apiInterface.status(Shared_Preferences.getPrefs(context, Constants.REG_ID),
                        Shared_Preferences.getPrefs(context, Constants.BUSINESSINFOID), requestServiceLists.get(position).getFld_user_issued_servicesApp(),"1","Completed");

                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        mDialog.dismiss();
                        try {
                            String output = response.body().string();
                            Log.d("Response", "getRequest:-" + output);
                            JSONObject jsonObject = new JSONObject(output);
                            if (jsonObject.getString("result").equals("true"))
                            {
                                Toast.makeText(context, "Request Completed Successfully", Toast.LENGTH_SHORT).show();
                            }
                            mDialog.dismiss();
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
        });

        holder.btn_reject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialog = new SimpleArcDialog(context);
                mDialog.setCancelable(false);
                mDialog.show();
                APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
                Call<ResponseBody> result = apiInterface.status(Shared_Preferences.getPrefs(context, Constants.REG_ID),
                        Shared_Preferences.getPrefs(context, Constants.BUSINESSINFOID), requestServiceLists.get(position).getFld_user_issued_servicesApp(),"3","Rejected");

                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        mDialog.dismiss();
                        try {
                            String output = response.body().string();
                            Log.d("Response", "getRequest:-" + output);
                            JSONObject jsonObject = new JSONObject(output);
                            if (jsonObject.getString("result").equals("true"))
                            {
                                Toast.makeText(context, "Request Rejected", Toast.LENGTH_SHORT).show();
                            }
                            mDialog.dismiss();
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
        });


        if(requestServiceLists.get(position).getAllocatedService().equals("1"))
        {
            holder.servicetype.setText(" Only Request ");
        }
         if(requestServiceLists.get(position).getAllocatedService().equals("2"))
        {
            holder.servicetype.setText(" Only Delivery ");
        }
         if(requestServiceLists.get(position).getAllocatedService().equals("3"))
        {
            holder.servicetype.setText(" Only PickUp ");
        }
         if(requestServiceLists.get(position).getAllocatedService().equals("4"))
        {
            holder.servicetype.setText(" Delivery & Pickup");
        }


    }

    @Override
    public int getItemCount() {
        return requestServiceLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView profile;
        TextView name, number, email, reqitem, servicetype, bookedon;
        Button approve, disapprove,btn_complete,btn_reject;
        LinearLayout layoutUserRequest, layoutApprove;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            profile=itemView.findViewById(R.id.requestProfile);
            name=itemView.findViewById(R.id.requestUsername);
            number=itemView.findViewById(R.id.requestUserNumber);
            email=itemView.findViewById(R.id.requestUserEmail);
            bookedon=itemView.findViewById(R.id.requestUserDate);
            reqitem=itemView.findViewById(R.id.requestRequestItem);
            servicetype=itemView.findViewById(R.id.requestServiceType);
            approve=itemView.findViewById(R.id.requestUserApprove);
            disapprove=itemView.findViewById(R.id.requestUserDisapprove);
            layoutUserRequest=itemView.findViewById(R.id.layoutUserRequest);

            btn_complete=itemView.findViewById(R.id.btn_complete);
            btn_reject=itemView.findViewById(R.id.btn_reject);
            layoutApprove=itemView.findViewById(R.id.layoutApprove);

        }
    }
}
