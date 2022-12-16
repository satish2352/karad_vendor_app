package com.example.karadvenderapp.Adapter;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Model.BusinessVarientModel;
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

public class BusinessVarientListAdapter extends RecyclerView.Adapter<BusinessVarientListAdapter.MyHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<BusinessVarientModel> modelList;
    OnItemClickListener onItemClickListener;
    private Dialog dialog;
    private TextView dia_busi_name, edt_busi_description, edt_busi_rate;
    private SimpleArcDialog mDialog;
    private Button btn_yes, btn_no;

    public BusinessVarientListAdapter(Context context, List<BusinessVarientModel> modelList, OnItemClickListener onItemTouchListener) {
        this.context = context;
        this.modelList = modelList;
        this.onItemClickListener = onItemTouchListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.varient_item_layout, parent, false);
        mDialog = new SimpleArcDialog(context);
        mDialog.setCancelable(false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final BusinessVarientModel list = modelList.get(position);
        holder.tv_business_name.setText(list.getFld_business_details_name());
        holder.tv_business_details.setText(list.getFld_business_details_size());

        if (position > 0) {
            if (position % 4 == 0) {
                holder.imageView_back.setImageDrawable(context.getResources().getDrawable(R.drawable.card_background_sky));
                holder.view_card.setBackgroundColor(context.getResources().getColor(R.color.sky));
                holder.tv_count_center.setBackground(context.getResources().getDrawable(R.drawable.circle_background_sky));
            }
            if (position % 4 == 1) {
                holder.imageView_back.setImageDrawable(context.getResources().getDrawable(R.drawable.card_background_green));
                holder.view_card.setBackgroundColor(context.getResources().getColor(R.color.green));
                holder.tv_count_center.setBackground(context.getResources().getDrawable(R.drawable.circle_background_green));

            }
            if (position % 4 == 2) {
                holder.imageView_back.setImageDrawable(context.getResources().getDrawable(R.drawable.card_background_red));
                holder.view_card.setBackgroundColor(context.getResources().getColor(R.color.red));
                holder.tv_count_center.setBackground(context.getResources().getDrawable(R.drawable.circle_background_red));

            }
            if (position % 4 == 3) {
                holder.imageView_back.setImageDrawable(context.getResources().getDrawable(R.drawable.card_background));
                holder.view_card.setBackgroundColor(context.getResources().getColor(R.color.yellow_2));
                holder.tv_count_center.setBackground(context.getResources().getDrawable(R.drawable.circle_background_yellow));

            }
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, BusinessProcessActivity.class);
//                Shared_Preferences.setPrefs(context,"Business_ID",list.getBusiness_info_id());
//                Shared_Preferences.setPrefs(context,"Business_Details_id",list.getFld_business_details_id());
//                context.startActivity(intent);
//                Log.d("busi_id", "onClick: "+Shared_Preferences.getPrefs(context,"Business_ID"));
//                Log.d("busideta_id", "onClick: "+Shared_Preferences.getPrefs(context,"Business_Details_id"));
//
//            }
//        });
        holder.tv_ViewDetail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.options_edit_menu, menuBuilder);
                @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, holder.tv_ViewDetail);
                optionsMenu.setForceShowIcon(true);
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                update_Business_varient(list.getFld_business_details_id(), list.getFld_business_details_name(), list.getFld_business_details_size(), list.getFld_business_details_rate());
                                return true;
                            case R.id.menu_delete:
                                showDialog(modelList.get(position).getFld_business_details_id(), position);
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {
                    }
                });


                // Display the menu
                optionsMenu.show();
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
        text_msg.setText("Are you sure you want to delete This Varient");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteVarient(id, position);

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


    private void update_Business_varient(String varient_id, String name, String desc, String rate)
    {
        dialog = new Dialog(context, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_add_varient);
        dialog.setCanceledOnTouchOutside(true);
        dia_busi_name = dialog.findViewById(R.id.dia_busi_name);
        edt_busi_description = dialog.findViewById(R.id.edt_busi_description);
        edt_busi_rate = dialog.findViewById(R.id.edt_busi_rate);
        dia_busi_name.setText(name);
        edt_busi_description.setText(desc);
        edt_busi_rate.setText(rate);

        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!dia_busi_name.getText().toString().trim().equals("")) {
                    if (!edt_busi_description.getText().toString().trim().equals(""))
                    {
                        dialog.dismiss();
                        updateVarient(varient_id);
                    } else {
                        Toast.makeText(context, "Fill Required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Fill Required", Toast.LENGTH_SHORT).show();

                }

            }


        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_background));
        dialog.show();
    }

    private void updateVarient(String varient_id)
    {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.update_Varient(varient_id,
                dia_busi_name.getText().toString().trim(),
                edt_busi_description.getText().toString().trim(),
                edt_busi_rate.getText().toString().trim()

        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    String output = response.body().string();

                    Log.d("Response", "update_varient" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        dialog.dismiss();
                        onItemClickListener.onItemClick(true);
                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        onItemClickListener.onItemClick(false);
                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (JSONException e1)
                {
                    e1.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
            }
        });
    }

    private void deleteVarient(String string, final int position) {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.delete_Varient(string);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "delete=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        onItemClickListener.onItemClick(true);
//                          modelList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();

                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                    } else {
                        mDialog.dismiss();
                        onItemClickListener.onItemClick(false);
                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
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
                mDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_business_name, tv_count_center, tv_business_details;
        View view_card;
        ImageView imageView_back, tv_ViewDetail;

        public MyHolder(View view) {
            super(view);
            tv_business_name = (TextView) view.findViewById(R.id.tv_business_name);
            tv_business_details = (TextView) view.findViewById(R.id.tv_business_details);
            tv_count_center = (TextView) view.findViewById(R.id.tv_count_center);
            view_card = (View) view.findViewById(R.id.view_card);
            imageView_back = (ImageView) view.findViewById(R.id.imageView_back);
            tv_ViewDetail = (ImageView) view.findViewById(R.id.tv_ViewDetail);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(boolean flag);
    }
}
