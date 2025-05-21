package com.example.librarylist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginMainAcitivity extends AppCompatActivity {
    private Button login;//登入管理者
    private Button signUp;//註冊管理者
    private FirebaseAuth mAuth;
    public EditText email ,password;
    private TextInputLayout accoutLayout,passwordLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        login= (Button) findViewById(R.id.login_button);//登入
        signUp = (Button) findViewById(R.id.sign_button);//註冊
        email = (EditText) findViewById(R.id.acc);
        password = (EditText) findViewById(R.id.password);

        accoutLayout = (TextInputLayout) findViewById(R.id.layout_username);
        passwordLayout = (TextInputLayout) findViewById(R.id.layout_code);
        passwordLayout.setErrorEnabled(true);
        accoutLayout.setErrorEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        //按登入的listener
        login.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast toast= Toast.makeText(LoginMainAcitivity.this, "登入成功",Toast.LENGTH_LONG);
                                toast.show();
                                Intent intent = new Intent();
                                intent.setClass(LoginMainAcitivity.this, libraryList.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast toast= Toast.makeText(LoginMainAcitivity.this, "登入失敗，請檢查email/password",Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });


            }
        });
        //按註冊的listener
        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginMainAcitivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });


    }
}

