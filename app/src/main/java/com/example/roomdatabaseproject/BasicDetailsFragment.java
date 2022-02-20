package com.example.roomdatabaseproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.roomdatabaseproject.databinding.FragmentBasicDetailsBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;

import Model.UserData;
import Model.UserDatabase;

import static android.app.Activity.RESULT_OK;


public class BasicDetailsFragment extends Fragment {

    ActivityResultLauncher <Intent> cameraResultLauncher;
    ActivityResultLauncher <Intent> galleryResultLauncher;
    Uri imageUri;
    private Bitmap bitmap;
    private String imagePath;
    private File file;
    private boolean userEdit = false;
    private FragmentBasicDetailsBinding basicDetailsBinding;


    public BasicDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // basicDetailsBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_basic_details);
        basicDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic_details, container, false);


        // For Camera
        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback <ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                    // todo

                    String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), bitmap, "Camera Image", null);
                    if (path != null) {
                        imageUri = Uri.parse(path);
                    }
                    if (imageUri != null) {
                        imagePath = getRealPathFromURI(imageUri);
                        basicDetailsBinding.profilePic.setImageURI(imageUri);
                    } 
                } else {
                    Toast.makeText(getContext(), "No Image Captured", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // For Gallery

        galleryResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback <ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    imageUri = result.getData().getData();

                    Log.e("PATH", "onActivityResult: " + getRealPathFromURI(imageUri));
                    imagePath = getRealPathFromURI(imageUri);
                    //  file = new File(getRealPathFromURI(ImageUri));
                    basicDetailsBinding.profilePic.setImageURI(imageUri);

                } else {
                    Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                }

            }
        });


        if (requireActivity().getIntent() != null) {
            userEdit = requireActivity().getIntent().getBooleanExtra("userEdit", false);
            Log.e("TAG", "userEdit: " + userEdit);
        }


        if (userEdit) {
            UserDatabase userDb = UserDatabase.getInstance(getContext());
            UserData userData = userDb.userDao().getUserData();
            if (userData != null) {
                imagePath = userData.getProfilePic();
                Log.e("imagePath", "edit " + imagePath);
                basicDetailsBinding.profilePic.setImageURI(Uri.fromFile(new File(imagePath)));
                basicDetailsBinding.editFirstName.setText(userData.getFirstname());
                basicDetailsBinding.editLastName.setText(userData.getLastname());
                basicDetailsBinding.editEmailAddress.setText(userData.getEmail());
                basicDetailsBinding.editPhone.setText(userData.getMobileNumber());
            }
        }


        addUserBasicData();


        return basicDetailsBinding.getRoot();

    }

    public void addUserBasicData() {

        basicDetailsBinding.btnNext1.setOnClickListener(v -> {
            String firstName = basicDetailsBinding.editFirstName.getText().toString();
            String lastName = basicDetailsBinding.editLastName.getText().toString();
            String email = basicDetailsBinding.editEmailAddress.getText().toString();
            String phone = basicDetailsBinding.editPhone.getText().toString();
            // set the value in user Data
            UserData userData = new UserData();
            if (imagePath != null) {
                userData.setProfilePic(imagePath);
            }
            userData.setFirstname(firstName);
            userData.setLastname(lastName);
            userData.setEmail(email);
            userData.setMobileNumber(phone);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", userData);
            getParentFragmentManager().setFragmentResult("data", bundle);

            ((MainActivity) requireActivity()).setViewPagerPosition(1);


        });

        basicDetailsBinding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                builder.setCancelable(true);
                builder.setView(dialogView);
                AlertDialog alertDialog;
                alertDialog = builder.show();
                TextView tvCamera = dialogView.findViewById(R.id.tvCamera);
                TextView tvGallery = dialogView.findViewById(R.id.tvGallery);
                AlertDialog finalAlertDialog = alertDialog;

                // For Camera

                tvCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                            cameraResultLauncher.launch(intent);
                            finalAlertDialog.cancel();

                        } else {
                            Toast.makeText(getContext(), "NO data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // For Gallery

                tvGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        if (intent.resolveActivity(getContext().getPackageManager()) != null) {

                            galleryResultLauncher.launch(intent);
                            finalAlertDialog.cancel();

                        } else {
                            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}