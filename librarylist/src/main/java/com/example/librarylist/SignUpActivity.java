package com.example.librarylist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity  extends AppCompatActivity {
    private EditText accountEdit,passwordEdit;
    private TextInputLayout accoutLayout,passwordLayout;
    private FirebaseAuth mAuth;
    private Button signUp;//註冊管理者
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupactivity);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.code);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        accoutLayout = (TextInputLayout) findViewById(R.id.layoutUsername);
        passwordLayout = (TextInputLayout) findViewById(R.id.layoutCode);
        passwordLayout.setErrorEnabled(true);
        accoutLayout.setErrorEnabled(true);

        signUp = (Button) findViewById(R.id.sign);//註冊

        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        //按註冊的listener
        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(account, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast toast= Toast.makeText(SignUpActivity.this, "註冊成功",Toast.LENGTH_LONG);
                                    toast.show();

                                    Intent intent = new Intent();
                                    intent.setClass(SignUpActivity.this, libraryList.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "註冊失敗，可能是帳號格式或是已經註冊過了.",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

            }
        });

    }

}
