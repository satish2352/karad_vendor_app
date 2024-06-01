package com.example.karadvenderapp.Adapter;


import android.annotation.SuppressLint;
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

import com.example.karadvenderapp.Model.DisapproveAppointmentList;
import com.example.karadvenderapp.Model.RejectAppointmentList;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.example.karadvenderapp.interfaces.MyRecyclerViewItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RejectAppointmentAdapter extends RecyclerView.Adapter<RejectAppointmentAdapter.ViewHolder>
{
    private Context context;
    private List<RejectAppointmentList> list;
    MyRecyclerViewItemClickListener recyclerViewItemClickListener;

    public RejectAppointmentAdapter(Context context, List<RejectAppointmentList> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RejectAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.requestlayoutappointment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RejectAppointmentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.name.setText(list.get(position).getUser_name());
        holder.number.setText(list.get(position).getUser_mobile());
        holder.email.setText(list.get(position).getUser_email());
        holder.bookedon.setText("Booked on:- "+list.get(position).getFld_service_requested_date());
        holder.slottime.setText("Slot Time:- "+list.get(position).getFld_actual_booking_slot());
        holder.slotdate.setText("Slot Date:-"+list.get(position).getBooking_date());

    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView profile;
        TextView name, number, email, slotdate, slottime, bookedon;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            profile=itemView.findViewById(R.id.requestProfile);
            name=itemView.findViewById(R.id.requestUsername);
            number=itemView.findViewById(R.id.requestUserNumber);
            email=itemView.findViewById(R.id.requestUserEmail);
            slotdate=itemView.findViewById(R.id.requestUserSlot);
            slottime=itemView.findViewById(R.id.requestUserSlotTime);
            bookedon=itemView.findViewById(R.id.requestUserDate);

        }
    }
}
