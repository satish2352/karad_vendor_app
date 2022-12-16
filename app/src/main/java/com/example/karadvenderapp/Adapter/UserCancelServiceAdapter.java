package com.example.karadvenderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Model.CancelUserServiceList;
import com.example.karadvenderapp.R;

import java.util.List;

public class UserCancelServiceAdapter extends RecyclerView.Adapter<UserCancelServiceAdapter.ViewHolder>
{

    private Context context;
    private List<CancelUserServiceList> list;

    public UserCancelServiceAdapter(Context context, List<CancelUserServiceList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserCancelServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserCancelServiceAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.requestlayoutservice,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserCancelServiceAdapter.ViewHolder holder, int position)
    {
        holder.name.setText(list.get(position).getUser_name());
        holder.number.setText(list.get(position).getUser_mobile());
        holder.email.setText(list.get(position).getUser_email());
        holder.bookedon.setText("Cancelled on:- "+list.get(position).getFld_service_requested_date());
        holder.reqitem.setText(list.get(position).getFld_business_details_name());

        if(list.get(position).getAllocatedService().equals("1"))
        {
            holder.servicetype.setText(" Only Request ");
        }
        if(list.get(position).getAllocatedService().equals("2"))
        {
            holder.servicetype.setText(" Only Delivery ");
        }
        if(list.get(position).getAllocatedService().equals("3"))
        {
            holder.servicetype.setText(" Only PickUp ");
        }
        if(list.get(position).getAllocatedService().equals("4"))
        {
            holder.servicetype.setText(" Delivery & Pickup");
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, number, email, reqitem, servicetype, bookedon;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.requestUsername);
            number=itemView.findViewById(R.id.requestUserNumber);
            email=itemView.findViewById(R.id.requestUserEmail);
            bookedon=itemView.findViewById(R.id.requestUserDate);
            reqitem=itemView.findViewById(R.id.requestRequestItem);
            servicetype=itemView.findViewById(R.id.requestServiceType);
        }
    }
}
