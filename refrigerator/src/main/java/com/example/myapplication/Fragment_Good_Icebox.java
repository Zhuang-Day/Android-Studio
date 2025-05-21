package com.example.myapplication;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class Fragment_Good_Icebox extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //tab、mViewPager
        View view=inflater.inflate(R.layout.pageshow, container, false);

        ViewPager viewPager = view.findViewById(R.id.mViewPager);//取得介面元件
        ArrayList<View> mPages = new ArrayList<>();
        for (int i = 0; i< GoodPagerAdapter.FoodType.length; i++) {//新增foodTypeNum個分頁
            mPages.add(new GoodPagers(getContext(), (i + 1)));
        }

        TabLayout tab = view.findViewById(R.id.tab);//取得上方選單介面元件
        GoodPagerAdapter goodPagerAdapter = new GoodPagerAdapter(mPages);//宣告MyPagerAdapter.java的物件

        tab.setupWithViewPager(viewPager);//將TabLayout綁定給ViewPager
        viewPager.setAdapter(goodPagerAdapter);//綁定適配器

        //指定跳到某頁，一定得設置在setAdapter後面
        viewPager.setCurrentItem(0);
        return view;
    }
}