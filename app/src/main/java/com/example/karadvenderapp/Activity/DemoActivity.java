package com.example.karadvenderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.karadvenderapp.Fragment.AddServiceFragment;
import com.example.karadvenderapp.Fragment.BusinessPhotoFragment;
import com.example.karadvenderapp.Fragment.HomeFragment;
import com.example.karadvenderapp.Fragment.RenewalRequestFragment;
import com.example.karadvenderapp.Fragment.UserPostsFragment;
import com.example.karadvenderapp.R;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.github.rubensousa.floatingtoolbar.FloatingToolbarMenuBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DemoActivity extends AppCompatActivity implements FloatingToolbar.ItemClickListener, FloatingToolbar.MorphListener {

    private FloatingToolbar mFloatingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("demo");

        FloatingActionButton fab = findViewById(R.id.fab);
        mFloatingToolbar = findViewById(R.id.floatingToolbar);


        mFloatingToolbar.setClickListener(this);
        mFloatingToolbar.attachFab(fab);
        mFloatingToolbar.addMorphListener(this);

        mFloatingToolbar.setMenu(new FloatingToolbarMenuBuilder(this)
                .addItem(R.id.fab_view, R.drawable.ic_cheque, "Mark unread")
                .addItem(R.id.fab_add, R.drawable.ic_company, "Copy")
                .addItem(R.id.fab_add2, R.drawable.ic_mobile, "Google+")
                .addItem(R.id.fab_add3, R.drawable.ic_owner, "Facebook")
                .build());
    }

    @Override
    public void onItemClick(MenuItem item) {
        Fragment fragment = null;
        int flag = 0;

        switch (item.getItemId()) {
            case R.id.fab_view:
                fragment = new HomeFragment();
                getSupportActionBar().setTitle("Home");
                flag = 0;
                loadFragment(fragment,flag);
                break;
            case R.id.fab_add:
                fragment = new AddServiceFragment();
                getSupportActionBar().setTitle("Add Services");
                flag = 1;
                loadFragment(fragment,flag);
                break;
            case R.id.fab_add2:
                fragment = new BusinessPhotoFragment();
                getSupportActionBar().setTitle("Business Photos");
                flag = 1;
                loadFragment(fragment,flag);
                break;

            case R.id.fab_add3:
                fragment = new UserPostsFragment();
                getSupportActionBar().setTitle("Home");
                flag = 1;
                loadFragment(fragment,flag);
                break;

        }
    }

    private void loadFragment(Fragment fragment, int flag) {
        //switching fragment

        if (flag == 1) {


        } else {

        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .commit();

        }
    }

    @Override
    public void onItemLongClick(MenuItem item) {

    }

    @Override
    public void onMorphEnd() {

    }

    @Override
    public void onMorphStart() {

    }

    @Override
    public void onUnmorphStart() {

    }

    @Override
    public void onUnmorphEnd() {

    }
}
