package com.example.karadvenderapp.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.karadvenderapp.Adapter.AddsideBusinessAdapter;
import com.example.karadvenderapp.Model.AddMemberModel;
import com.example.karadvenderapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPostsFragment extends Fragment {
    private TextView addMemberlist;
    private Button btn_submit, btn_close;
    private EditText btn_fname, btn_lname, age;
    private RecyclerView addMmberRecycler;
    private LinearLayout linearLayout;
    private View retview;
    private AddMemberModel memberModel;
    private List<AddMemberModel> list;
    private AddsideBusinessAdapter memberAdapter;
    private JSONObject student1=null;

    public UserPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        retview = inflater.inflate(R.layout.fragment_user_posts, container, false);
        list = new ArrayList<>();
        linearLayout = retview.findViewById(R.id.linearLayout);
        addMmberRecycler = retview.findViewById(R.id.addPerson);
        addMemberlist = retview.findViewById(R.id.addlist);
        addMemberlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addbusi();
            }
        });
        if (memberAdapter != null) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.INVISIBLE);
        }

        return retview;
    }


    public void Addbusi() {
        LayoutInflater li = LayoutInflater.from(getContext());
        //Creating a view to get the dialog box
        View detailsofrawDialog = li.inflate(R.layout.add_business_dialog, null);


        btn_submit = detailsofrawDialog.findViewById(R.id.submit_button);
        btn_close = detailsofrawDialog.findViewById(R.id.close_button);
        btn_fname = detailsofrawDialog.findViewById(R.id.fname);
        btn_lname = detailsofrawDialog.findViewById(R.id.edt_size);
        age = detailsofrawDialog.findViewById(R.id.rate);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //Adding our dialog box to the view of alert dialog
        alert.setView(detailsofrawDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();

        // alertDialog.setCancelable(false);

        alertDialog.setCanceledOnTouchOutside(false);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(btn_fname.getText())) {
                    btn_fname.setError("Please Enter First Name");
                    return;
                }
                if (TextUtils.isEmpty(btn_lname.getText())) {
                    btn_lname.setError("Please Enter Size");
                    return;
                }
                if (TextUtils.isEmpty(age.getText())) {
                    age.setError("Please Enter rate");
                    return;
                }
                memberModel = new AddMemberModel(btn_fname.getText().toString(), btn_lname.getText().toString(),
                        age.getText().toString());
                list.add(memberModel);

                JSONObject reqJson = new JSONObject();
                for (int i = 0; i < list.size(); i++) {
                    try {
                        reqJson.put("fld_business_details_name", list.get(i).getFld_business_details_name());
                        reqJson.put("fld_business_details_size", list.get(i).getFld_business_details_size());
                        reqJson.put("fld_business_details_rate", list.get(i).getFld_business_details_rate());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                JSONArray array = new JSONArray();
                array.put(reqJson);

                JSONObject json = new JSONObject();
                try {
                    json.put("details", array.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d("data", "onClick: "+json.toString());

                if (memberAdapter == null) {
                    memberAdapter = new AddsideBusinessAdapter(getContext(), list);
                    addMmberRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    addMmberRecycler.setAdapter(memberAdapter);
                    memberAdapter.notifyDataSetChanged();
                    linearLayout.setVisibility(View.VISIBLE);
//                    person.setText(String.valueOf(memberAdapter.getItemCount()));
                    clearField();
                    alertDialog.dismiss();
                } else {
                    clearField();
                    alertDialog.dismiss();
//                    person.setText(memberAdapter.getItemCount());
                    linearLayout.setVisibility(View.VISIBLE);
                    memberAdapter.notifyDataSetChanged();
                }
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }

    private void clearField() {
        btn_fname.setText("");
        btn_lname.setText("");
        age.setText("");
    }

}
