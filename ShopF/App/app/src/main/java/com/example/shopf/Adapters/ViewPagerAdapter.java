package com.example.shopf.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shopf.Fragments.Home;
import com.example.shopf.Fragments.Product;
import com.example.shopf.Fragments.User;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Home();
            case 1:
                return new Product();
            case 2:
                return new User();
            default:
                return new Home();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
