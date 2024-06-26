package com.example.karadvenderapp.Adapter;

import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.karadvenderapp.Activity.AddNewBusiness;
import com.example.karadvenderapp.Activity.UpdateVendorBusiness;
import com.example.karadvenderapp.Fragment.FirstLevelFragment;
import com.example.karadvenderapp.Fragment.SecondLevelFragment;
import com.example.karadvenderapp.Fragment.ThirdLevelFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class ListLevelAdapter extends AbstractFragmentStepAdapter {
    private static final String CURRENT_STEP_POSITION_KEY = "Constant1";


    public ListLevelAdapter(FragmentManager supportFragmentManager, AddNewBusiness context) {
        super(supportFragmentManager, context);
    }




    @Override
    public Step createStep(int position) {
        if (position == 0) {
            final FirstLevelFragment step = new FirstLevelFragment();
            Bundle b = new Bundle();
            b.putInt(CURRENT_STEP_POSITION_KEY, position);
            step.setArguments(b);
            //step2.setArguments(b);
            return step;
        } else if (position == 1) {
            final SecondLevelFragment step = new SecondLevelFragment();
            Bundle b = new Bundle();
            b.putInt(CURRENT_STEP_POSITION_KEY, position);
            step.setArguments(b);
            //step2.setArguments(b);
            return step;
        }else {
            final ThirdLevelFragment step = new ThirdLevelFragment();
            Bundle b = new Bundle();
            b.putInt(CURRENT_STEP_POSITION_KEY, position);
            step.setArguments(b);
            //step2.setArguments(b);
            return step;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        switch (position) {
            case 0:
                return new StepViewModel.Builder(context)
                        .setTitle("1st Page") //can be a CharSequence instead
                        .create();
            case 1:
                return new StepViewModel.Builder(context)
                        .setTitle("2nd Page") //can be a CharSequence instead
                        .create();
            case 2:
                return new StepViewModel.Builder(context)
                        .setTitle("3rd Page") //can be a CharSequence instead
                        .create();
        }
        return null;
    }
}