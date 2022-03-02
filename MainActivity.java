package com.kly.mjstargram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    MainPagerAdapter mainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tablayout);

        mainPagerAdapter=new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(getDrawable(R.drawable.baseline_home_black_48));
        tabLayout.getTabAt(1).setIcon(getDrawable(R.drawable.baseline_search_black_48));
        tabLayout.getTabAt(2).setIcon(getDrawable(R.drawable.baseline_add_circle_outline_black_48));
        tabLayout.getTabAt(3).setIcon(getDrawable(R.drawable.baseline_shopping_bag_black_48));
        tabLayout.getTabAt(4).setIcon(getDrawable(R.drawable.baseline_person_black_48));

    }

    public void moveTab(int index){
        viewPager.setCurrentItem(index);
        if(index==0){
            mainPagerAdapter.homeFragment.getPostList();
        }
    }
}