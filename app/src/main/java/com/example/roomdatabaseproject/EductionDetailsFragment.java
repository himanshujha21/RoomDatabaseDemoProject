package com.example.roomdatabaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.roomdatabaseproject.databinding.FragmentEductionDetailsBinding;

import Model.UserData;
import Model.UserDatabase;


public class EductionDetailsFragment extends Fragment {

    public UserData userData;
    private FragmentEductionDetailsBinding eductionDetailsBinding;
    private UserDatabase userDatabase;
    private boolean userEdit = false;


    public EductionDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eductionDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_eduction_details, container, false);


        if (requireActivity().getIntent() != null) {
            userEdit = requireActivity().getIntent().getBooleanExtra("userEdit", false);
            Log.e("TAG", "userEdit: " + userEdit);
        }


        if (userEdit) {
            UserDatabase userDb = UserDatabase.getInstance(getContext());
            UserData userData = userDb.userDao().getUserData();
            if (userData != null) {
                eductionDetailsBinding.editHighestQualification.setText(userData.getHighestQualification());
                eductionDetailsBinding.editCollege.setText(userData.getCollege());
                eductionDetailsBinding.editUniversity.setText(userData.getUniversity());
                eductionDetailsBinding.editPercentage.setText(userData.getPercentage());
            }
        }
        getUserEducationData();
        return eductionDetailsBinding.getRoot();

    }

    public void getUserEducationData() {
        getParentFragmentManager().setFragmentResultListener("data", this, (requestKey, result) ->
                userData = (UserData) result.getSerializable("data"));


        eductionDetailsBinding.btnSubmit.setOnClickListener(v -> {
            String highestQ = eductionDetailsBinding.editHighestQualification.getText().toString();
            String college = eductionDetailsBinding.editCollege.getText().toString();
            String university = eductionDetailsBinding.editUniversity.getText().toString();
            String percentage = eductionDetailsBinding.editPercentage.getText().toString();

            userData.setUid(1);
            if (userData != null) {
                userData.setHighestQualification(highestQ);
                userData.setCollege(college);
                userData.setUniversity(university);
                userData.setPercentage(percentage);

            }

            // create the instance of database
            userDatabase = UserDatabase.getInstance(getContext());

            if (userDatabase.userDao().getUserData() != null) {
                // update the data into database if user data is exist.
                // userDatabase.userDao().deleteAll();

                userDatabase.userDao().update(userData);
            } else {
                // insert data on submit button click.
                userDatabase.userDao().insert(userData);
            }
            // start the display activity
            Intent displayIntent = new Intent(getContext(), DisplayActivity.class);
            startActivity(displayIntent);
            requireActivity().finish();
        });


    }
}