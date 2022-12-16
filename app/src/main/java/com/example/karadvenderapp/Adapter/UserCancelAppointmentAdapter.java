package com.example.karadvenderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Model.CancelUserAppointmentList;
import com.example.karadvenderapp.R;

import java.util.List;

public class UserCancelAppointmentAdapter extends RecyclerView.Adapter<UserCancelAppointmentAdapter.ViewHolder>
{
    private Context context;
    private List<CancelUserAppointmentList> list;

    public UserCancelAppointmentAdapter(Context context, List<CancelUserAppointmentList> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserCancelAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new UserCancelAppointmentAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.requestlayoutappointment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserCancelAppointmentAdapter.ViewHolder holder, int position)
    {
        holder.name.setText(list.get(position).getUser_name());
        holder.number.setText(list.get(position).getUser_mobile());
        holder.email.setText(list.get(position).getUser_email());
        holder.bookedon.setText("Cancelled on:- "+list.get(position).getFld_service_requested_date ());
        holder.slottime.setText("Slot Time:- "+list.get(position).getFld_actual_booking_slot());
        holder.slotdate.setText("Slot Date:-"+list.get(position).getFld_service_requested_date());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, number, email, slotdate, slottime, bookedon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.requestUsername);
            number=itemView.findViewById(R.id.requestUserNumber);
            email=itemView.findViewById(R.id.requestUserEmail);
            slotdate=itemView.findViewById(R.id.requestUserSlot);
            slottime=itemView.findViewById(R.id.requestUserSlotTime);
            bookedon=itemView.findViewById(R.id.requestUserDate);

        }
    }
}
