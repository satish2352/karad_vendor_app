package com.example.karadvenderapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Activity.DownloadInvoiceActivity;
import com.example.karadvenderapp.Model.InvoiceList;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;

import java.util.List;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.MyHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<InvoiceList> modelList;
    private SimpleArcDialog mDialog;

    public InvoiceListAdapter(Context context, List<InvoiceList> modelList ) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item_layout, parent, false);
        mDialog = new SimpleArcDialog(context);
        mDialog.setCancelable(false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position)
    {
        final InvoiceList list = modelList.get(position);
        holder.tv_business_name.setText(list.getBusiness_info_name());
        holder.tvPackageName.setText(list.getFld_package_name());
        holder.tvPackageAmt.setText(list.getFld_package_amount()+"/-");
        holder.tv_business_date.setText(list.getCreated_at());



        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, DownloadInvoiceActivity.class);
                intent.putExtra("id",list.getId());
                intent.putExtra("Businesstype",list.getFld_business_name());
                intent.putExtra("Businessname",list.getBusiness_info_name());
                intent.putExtra("fld_package_name",list.getFld_package_name());
                intent.putExtra("fld_package_amount",list.getFld_package_amount());
                intent.putExtra("validity",list.getFld_validity_in_days_number());
                intent.putExtra("owner_name",list.getOwner_name());
                intent.putExtra("startdate",list.getStart_date());
                intent.putExtra("enddate",list.getEnd_date());
                intent.putExtra("mobile",list.getMobile());
                intent.putExtra("email",list.getEmail());
                intent.putExtra("fld_product_type_name",list.getFld_product_type_name());
                intent.putExtra("created_at",list.getCreated_at());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_business_name,tvPackageName,tvPackageAmt,tv_business_date;
        public MyHolder(View view) {
            super(view);

            tv_business_name=view.findViewById(R.id.tv_business_name);
            tvPackageName=view.findViewById(R.id.tvPackageName);
            tvPackageAmt=view.findViewById(R.id.tvPackageAmt);
            tv_business_date=view.findViewById(R.id.tv_business_date);

        }
    }

}
