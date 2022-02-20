package com.example.roomdatabaseproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.example.roomdatabaseproject.databinding.ActivityDisplayBinding;

import java.io.File;

import Model.UserData;
import Model.UserDatabase;

public class DisplayActivity extends AppCompatActivity {
    ActivityDisplayBinding activityDisplayBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_display);
        activityDisplayBinding = DataBindingUtil.setContentView(this, R.layout.activity_display);

        disPlayUserData();

    }

    public void disPlayUserData() {
        UserDatabase userDb = UserDatabase.getInstance(this);
        UserData userData = userDb.userDao().getUserData();
        if (userData != null) {
            String imagePath = userData.getProfilePic();
            //Log.e("imagePath", "edit "+imagePath );
            activityDisplayBinding.displayPic.setImageURI(Uri.fromFile(new File(imagePath)));
            activityDisplayBinding.displayPic.setImageURI(Uri.parse(userData.getProfilePic()));

            activityDisplayBinding.textFirstName.setText(userData.getFirstname());
            activityDisplayBinding.textLastName.setText(userData.getLastname());
            activityDisplayBinding.txtEmail.setText(userData.getEmail());
            activityDisplayBinding.textPhone.setText(userData.getMobileNumber());
            activityDisplayBinding.textAddress.setText(userData.getAddress());
            activityDisplayBinding.textAlternateMob.setText(userData.getAlternateMobile());
            activityDisplayBinding.textHighestQualification.setText(userData.getHighestQualification());
            activityDisplayBinding.textCollege.setText(userData.getCollege());
            activityDisplayBinding.textUniversity.setText(userData.getUniversity());
            activityDisplayBinding.textPercentage.setText(userData.getPercentage());
        }


        // Apply the click on edit button
        activityDisplayBinding.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
            intent.putExtra("userEdit", true);

            startActivity(intent);
            finish();


        });


    }


}

