package com.example.roomdatabaseproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.roomdatabaseproject.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.basic_details), 0);
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.contact_details), 1);
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.educational_details), 2);

        FragmentStateAdapter pagerAdapter = new ViewPagerAdapter(MainActivity.this);
        binding.viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.tabs, binding.viewPager, (tab, position) -> {
            if (position == 0) tab.setText(R.string.basic_details);
            if (position == 1) tab.setText(R.string.contact_details);
            if (position == 2) tab.setText(R.string.educational_details);
        }).attach();

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager.setCurrentItem(0);

        // Provide the run-time permission for read or write

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted ", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

        } else {
            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * method used for move the next fragment position.
     */

    public void setViewPagerPosition(int position) {
        binding.viewPager.setCurrentItem(position);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            }

        }
    }


}