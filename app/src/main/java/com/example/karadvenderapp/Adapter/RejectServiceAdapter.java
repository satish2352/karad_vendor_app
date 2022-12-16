package com.example.karadvenderapp.Adapter;

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

import com.example.karadvenderapp.Model.DisapproveServiceList;
import com.example.karadvenderapp.Model.RejectServiceList;
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

public class RejectServiceAdapter extends RecyclerView.Adapter<RejectServiceAdapter.ViewHolder>
{
    private Context context;
    private List<RejectServiceList> rejectServiceLists;

    public RejectServiceAdapter(Context context, List<RejectServiceList> rejectServiceLists)
    {
        this.context = context;
        this.rejectServiceLists = rejectServiceLists;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.requestlayoutservice,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.name.setText(rejectServiceLists.get(position).getUser_name());
        holder.number.setText(rejectServiceLists.get(position).getUser_mobile());
        holder.email.setText(rejectServiceLists.get(position).getUser_email());
        holder.bookedon.setText("Booked on:- "+rejectServiceLists.get(position).getFld_service_requested_date());
        holder.reqitem.setText(rejectServiceLists.get(position).getFld_business_details_name());

        if(rejectServiceLists.get(position).getAllocatedService().equals("1"))
        {
            holder.servicetype.setText(" Only Request ");
        }
         if(rejectServiceLists.get(position).getAllocatedService().equals("2"))
        {
            holder.servicetype.setText(" Only Delivery ");
        }
         if(rejectServiceLists.get(position).getAllocatedService().equals("3"))
        {
            holder.servicetype.setText(" Only PickUp ");
        }
         if(rejectServiceLists.get(position).getAllocatedService().equals("4"))
        {
            holder.servicetype.setText(" Delivery & Pickup");
        }


    }

    @Override
    public int getItemCount() {
        return rejectServiceLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView profile;
        TextView name, number, email, reqitem, servicetype, bookedon;
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


        }
    }
}
