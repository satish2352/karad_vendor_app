package com.example.karadvenderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karadvenderapp.Model.AddMemberModel;
import com.example.karadvenderapp.R;

import java.util.List;

public class AddsideBusinessAdapter extends RecyclerView.Adapter<AddsideBusinessAdapter.MyViewHolder> {

    private Context context;

    private List<AddMemberModel> list;
    private AddMemberModel memberModel;
    private View addMemberView;
    private Button  btn_submit, btn_close;
    private EditText btn_fname, btn_lname, age;

    public AddsideBusinessAdapter(Context context, List<AddMemberModel> iserviceClassList) {
        this.context = context;
        this.list = iserviceClassList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final AddMemberModel product = list.get(position);
        holder.count.setText(String.valueOf(position + 1));
        holder.name.setText(product.getFld_business_details_name() + " " + product.getFld_business_details_size()+ " " + product.getFld_business_details_rate());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(view.getContext());
                //Creating a view to get the dialog box
                addMemberView = li.inflate(R.layout.add_business_dialog, null);

                btn_submit = addMemberView.findViewById(R.id.submit_button);
                btn_close = addMemberView.findViewById(R.id.close_button);
                btn_fname = addMemberView.findViewById(R.id.fname);
                btn_lname = addMemberView.findViewById(R.id.edt_size);
                age = addMemberView.findViewById(R.id.rate);

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                //Adding our dialog box to the view of alert dialog
                alert.setView(addMemberView);
                //Creating an alert dialog
                final AlertDialog alertDialog = alert.create();
                //Displaying the alert dialog
                alertDialog.show();
                // alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);

                btn_fname.setText(product.getFld_business_details_name());
                btn_lname.setText(product.getFld_business_details_size());
                age.setText(product.getFld_business_details_rate());

                btn_submit.setText("Update");
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        memberModel = new AddMemberModel(btn_fname.getText().toString(), btn_lname.getText().toString(),
                                age.getText().toString());
                        list.set(position, memberModel);
                        notifyItemChanged(position);
                        alertDialog.dismiss();
                    }
                });
              /*  btn_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_fname.setText("");
                        btn_lname.setText("");
                        age.setText("");
                        spinnerGender.setSelection(0);
                    }
                });*/
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView count, name;
        public ImageView cancel;

        public MyViewHolder(View view) {
            super(view);
            count = (TextView) view.findViewById(R.id.count);
            name = (TextView) view.findViewById(R.id.name);
            cancel = (ImageView) view.findViewById(R.id.delete);

        }

    }
}
