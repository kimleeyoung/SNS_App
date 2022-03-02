package com.kly.mjstargram;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class MainPagerAdapter extends FragmentPagerAdapter {

    HomeFragment homeFragment=new HomeFragment();
    SearchFragment searchFragment=new SearchFragment();
    AddFragment addFragment=new AddFragment();
    ShopFragment shopFragment=new ShopFragment();
    ProfileFragment profileFragment=new ProfileFragment();


    public MainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return homeFragment;
            case 1:
                return searchFragment;
            case 2:
                return addFragment;
            case 3:
                return shopFragment;
            default:
                return profileFragment;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
