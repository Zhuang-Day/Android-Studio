package com.example.myapplication;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Framelayout_Main extends AppCompatActivity
{
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:  //menu裡面設的id
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Home_Page()).commit();  //切換fragment
                    return true;
                case R.id.nav_good:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Good_Icebox()).commit();
                    return true;
                case R.id.nav_bad:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Bad_Icebox()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framelayout_main);

        setMain();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void setMain() {  //這個副程式用來設置顯示剛進來的第一個主畫面
        this.getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,new Fragment_Home_Page()).commit();
    }
}
