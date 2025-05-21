package com.example.librarylist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private Button login;//登入管理者
    private Button bowlogin;//登入借閱系統

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //如果user不存在
        /*
         * 進入
         * 登入管理者、註冊管理者、登入借閱
         * */

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    setContentView(R.layout.home);
                    bowlogin = (Button) findViewById(R.id.bowlogin);//登入借閱者
                    login = (Button) findViewById(R.id.login);//登入管理者

                    //按下登入借閱者的listener
                    bowlogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(HomeActivity.this, libraryList.class);
                            startActivity(intent);

                        }
                    });
                    //按下登入管理者的listener
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(HomeActivity.this, LoginMainAcitivity.class);
                            startActivity(intent);

                        }
                    });
                }
                //如果user存在，直接進入畫面
                else {
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this, libraryList.class);
                    startActivity(intent);
                }
            }

        };

    }
    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        if (authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
        super.onStop();
    }
}