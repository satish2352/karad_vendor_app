package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewVenderProfile extends AppCompatActivity {

    private TextView tv_vender_name, tv_email_id, tv_mobile, tv_dob, tv_company_name, tv_approve_date;
    private CircleImageView vender_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
        FindByListener();

    }

    private void FindByListener()
    {
        vender_profile = findViewById(R.id.vender_profile);
        if (Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.REG_IMAGE)!=null && !Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.REG_IMAGE).isEmpty()) {
            Picasso.with(ViewVenderProfile.this)
                    .load(Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.REG_IMAGE))
                    .error(R.drawable.no_image_available)
                    .into(vender_profile);
        }
        else {
            Picasso.with(ViewVenderProfile.this)
                    .load(R.drawable.no_image_available)
                    .error(R.drawable.no_image_available)
                    .into(vender_profile);
        }
        tv_vender_name = findViewById(R.id.tv_vender_name);
        tv_vender_name.setText(""+Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.REG_NAME));
        tv_email_id = findViewById(R.id.tv_email_id);
        tv_email_id.setText(""+Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.REG_EMAIL));
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_mobile.setText(""+Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.REG_MOBILE));
        tv_dob = findViewById(R.id.tv_dob);
        tv_dob.setText(""+Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.REG_DOB));
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_company_name.setText(""+Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.REG_COMPANY_NAME));
        tv_approve_date = findViewById(R.id.tv_approve_date);
        tv_approve_date.setText(""+Shared_Preferences.getPrefs(ViewVenderProfile.this, Constants.approve_date));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent intent=new Intent(ViewVenderProfile.this,UpdateVenderProfile.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
