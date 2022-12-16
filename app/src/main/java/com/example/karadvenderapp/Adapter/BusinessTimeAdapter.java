package com.example.karadvenderapp.Adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Model.BusinessTime;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessTimeAdapter extends RecyclerView.Adapter<BusinessTimeAdapter.MyHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<BusinessTime> modelList;
    OnItemClickListener onItemClickListener;
    private Dialog dialog,dialog2;
    private EditText dia_start_time;
    private EditText edt_end_time;
    private SimpleArcDialog mDialog;
    private Button btn_yes, btn_no;
    int hour,min;


    public BusinessTimeAdapter(Context context, List<BusinessTime> modelList, OnItemClickListener onItemTouchListener) {
        this.context = context;
        this.modelList = modelList;
        this.onItemClickListener = onItemTouchListener;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_time_item_layout, parent, false);

        mDialog = new SimpleArcDialog(context);
        mDialog.setCancelable(false);
        return new MyHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final BusinessTime list = modelList.get(position);
        holder.tv_times.setText(list.getFld_working_open_time() + " To " + list.getFld_working_close_time());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatetime(list.getFld_working_open_time(), list.getFld_working_close_time(), list.getFld_business_time_id(), position);
            }
        });
    }

    private void updatetime(String start_date, String end_date, String time, int pos) {
        dialog = new Dialog(context, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_update_time_slot);
        dialog.setCanceledOnTouchOutside(true);


        dia_start_time = dialog.findViewById(R.id.dia_start_time);
        edt_end_time = dialog.findViewById(R.id.edt_end_time);




        dia_start_time.setText("" + start_date);
        edt_end_time.setText("" + end_date);

        dia_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        hour = hourOfDay;
                        min = minute;

                        dia_start_time.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,min));

                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(context,AlertDialog.THEME_HOLO_LIGHT,onTimeSetListener,hour,min,true);
                timePickerDialog.setTitle("Select Start Time");
                timePickerDialog.show();

            }
        });

        edt_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        hour = hourOfDay;
                        min = minute;

                        edt_end_time.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,min));

                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(context,AlertDialog.THEME_HOLO_LIGHT,onTimeSetListener,hour,min,true);
                timePickerDialog.setTitle("Select End Time");
                timePickerDialog.show();
            }
        });


        dialog.findViewById(R.id.btnCanceltime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btndeletetime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(time, pos);

            }
        });

        dialog.findViewById(R.id.btnAddtime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dia_start_time.getText().toString().trim().equals("")) {
                    if (!edt_end_time.getText().toString().trim().equals("")) {
                        updateTime(time);
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

    private void updateTime(String string) {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.update_business_time(string,
                dia_start_time.getText().toString().trim(),
                edt_end_time.getText().toString().trim()

        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "update=> " + output);
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
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
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

    private void showDialog(String id, final int position) {
        dialog2 = new Dialog(context);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.alert_dialog);
        btn_yes = dialog2.findViewById(R.id.btn_logout_yes);
        btn_no = dialog2.findViewById(R.id.btn_logout_no);
        TextView text_msg = (TextView) dialog2.findViewById(R.id.text_msg);
        text_msg.setText("Are you sure you want to delete This Time");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTime(id, position);

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    private void deleteTime(String string, final int position) {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.delete_business_time(string);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "delete=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        dialog.dismiss();
                        dialog2.dismiss();
                        onItemClickListener.onItemClick(true);
                        modelList.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
                    } else {
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
        return modelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_times;

        public MyHolder(View view) {
            super(view);
            tv_times = (TextView) view.findViewById(R.id.tv_times);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(boolean flag);

    }

}
