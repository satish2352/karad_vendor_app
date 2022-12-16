package com.example.karadvenderapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.karadvenderapp.Activity.AddBusinessPhoto;
import com.example.karadvenderapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessPhotoFragment extends Fragment {

    private View rootView;
    FloatingActionButton fab;
    public BusinessPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_business_photo, container, false);
        createView();
        return rootView;
    }


    private void createView() {

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddBusinessPhoto.class);
                startActivity(intent);
            }
        });
    }
}
