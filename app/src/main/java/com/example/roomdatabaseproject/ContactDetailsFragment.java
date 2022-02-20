package com.example.roomdatabaseproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.roomdatabaseproject.databinding.FragmentContactDetailsBinding;

import Model.UserData;
import Model.UserDatabase;

public class ContactDetailsFragment extends Fragment {
    public UserData userData;
    boolean userEdit = false;
    private FragmentContactDetailsBinding contactDetailsBinding;


    public ContactDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        contactDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false);


        if (requireActivity().getIntent() != null) {
            userEdit = requireActivity().getIntent().getBooleanExtra("userEdit", false);
            Log.e("TAG", "userEdit: " + userEdit);
        }


        if (userEdit) {
            UserDatabase userDb = UserDatabase.getInstance(getContext());
            UserData userData = userDb.userDao().getUserData();
            if (userData != null) {
                contactDetailsBinding.editAddress.setText(userData.getAddress());
                contactDetailsBinding.editAlternatePhone.setText(userData.getAlternateMobile());
            }
        }
        addUserContactData();

        return contactDetailsBinding.getRoot();
    }

    public void addUserContactData() {

        getParentFragmentManager().setFragmentResultListener("data", this, (requestKey, result) -> userData = (UserData) result.getSerializable("data"));
        contactDetailsBinding.btnNext2.setOnClickListener(v -> {

            String address = contactDetailsBinding.editAddress.getText().toString();
            String alternatePhone = contactDetailsBinding.editAlternatePhone.getText().toString();
            if (userData != null) {
                userData.setAddress(address);
                userData.setAlternateMobile(alternatePhone);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("data", userData);
            getParentFragmentManager().setFragmentResult("data", bundle);

            ((MainActivity) requireActivity()).setViewPagerPosition(2);

        });


    }
}