package com.example.roomdatabaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Objects;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(AppCompatActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment frag_new = null;
        if (position == 0) frag_new = new BasicDetailsFragment();
        if (position == 1) frag_new = new ContactDetailsFragment();
        if (position == 2) frag_new = new EductionDetailsFragment();
        return Objects.requireNonNull(frag_new);
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}

