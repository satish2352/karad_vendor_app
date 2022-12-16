package com.example.karadvenderapp.Adapter;

import android.app.Dialog;
import android.content.Context;
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
import com.example.karadvenderapp.Model.HistoryList;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.DateTimeFormat;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HistoryList> homeDefaultLists;
    private SimpleArcDialog mDialog;
     Dialog dialog;
    private Button btn_yes;
    private Button btn_no;
    String Approved;
    OnItemClickListener onItemClickListener;



    public HistoryAdapter(Context context, ArrayList<HistoryList> homeDefaultLists,OnItemClickListener onItemTouchListener,String Approved) {
        this.context = context;
        this.homeDefaultLists = homeDefaultLists;
        this.onItemClickListener = onItemTouchListener;
        this.Approved = Approved;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_layout, parent, false);
        mDialog = new SimpleArcDialog(context);
        mDialog.setCancelable(false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)

    {

//        holder.tv_business_name.setText(homeDefaultLists.get(position).getBusiness_info_name());
//        holder.tv_ownre_name.setText(homeDefaultLists.get(position).getUser_name());
//        holder.tv_mobile.setText(homeDefaultLists.get(position).getUser_mobile());
//        holder.tv_date.setText(DateTimeFormat.getDate2(homeDefaultLists.get(position).getFld_service_requested_date()));
//        if (!homeDefaultLists.get(position).getFld_actual_booking_slot().equals("null")) {
//            holder.tv_address.setText(homeDefaultLists.get(position).getFld_actual_booking_slot());
//        } else {
//            holder.tv_address.setText("");
//
//        }
//
//        if (Approved.equals("Approved")){
//            Log.d("yes", "onBindViewHolder: "+Approved);
//            holder.tv_approve.setText("Completed");
//            holder.tv_approve.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDialog(position,"Complete");
//                }
//            });
//            holder.tv_rejcet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDialog(position, "Reject");
//                }
//            });
//        }else if(Approved.equals("Pending")) {
//            Log.d("no", "onBindViewHolder: "+Approved);
//            holder.tv_approve.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDialog(position,"Approve");
//                }
//            });
//            holder.tv_rejcet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDialog(position, "Reject");
//                }
//            });
//
//        }else {
//            holder.tv_approve.setVisibility(View.GONE);
//            holder.tv_rejcet.setVisibility(View.GONE);
//        }

    }


    private void showDialog(int position,String msg) {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        btn_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_no = dialog.findViewById(R.id.btn_logout_no);
        btn_yes.setText(msg);
        btn_no.setText("Cancel");
        TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
        iv_image.setVisibility(View.GONE);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text_msg.setText("Are you sure you want to "+msg+" Request");
        text.setText(msg+"...!");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (msg.equals("Approve")) {
                    ChangeStatus(position, "Approved");
                }else if (msg.equals("Complete")) {
                    ChangeStatus(position, "Completed");
                }else {
                    ChangeStatus(position, "Rejected");

                }
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

    private void ChangeStatus(int pos, String status) {
        Log.d("TAG", "ChangeStatus:bui_id "+ homeDefaultLists.get(pos).getBusiness_info_id());
        Log.d("TAG", "ChangeStatus: fid"+ homeDefaultLists.get(pos).getFld_business_details_id());
        Log.d("TAG", "ChangeStatus:status "+status);
        Log.d("TAG", "ChangeStatus:status "+homeDefaultLists.get(pos).getFld_user_issued_servicesApp()  );
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getchangestaus(Shared_Preferences.getPrefs(context, Constants.REG_ID),
                homeDefaultLists.get(pos).getBusiness_info_id(),
                homeDefaultLists.get(pos).getFld_business_details_id(),
                status,
                homeDefaultLists.get(pos).getFld_user_issued_servicesApp());

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "ApprovedRequestFragment" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("message");
                    if (jsonObject.getString("result").equals("true")) {
                        onItemClickListener.onItemClick(true);
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        onItemClickListener.onItemClick(false);

                        mDialog.dismiss();

                    }
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
            }
        });
    }


    @Override
    public int getItemCount() {
        return homeDefaultLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_business_name, tv_date, tv_ownre_name, tv_mobile, tv_address, tv_approve, tv_rejcet;

        public MyViewHolder(View view) {
            super(view);
            tv_date = view.findViewById(R.id.tv_date);
            tv_business_name = view.findViewById(R.id.tv_business_name);
            tv_ownre_name = view.findViewById(R.id.tv_ownre_name);
            tv_mobile = view.findViewById(R.id.tv_mobile);
            tv_address = view.findViewById(R.id.tv_address);
            tv_approve = view.findViewById(R.id.tv_approve);
            tv_rejcet = view.findViewById(R.id.tv_rejcet);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(boolean flag);
    }
}
