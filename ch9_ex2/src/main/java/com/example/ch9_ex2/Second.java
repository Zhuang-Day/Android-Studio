package com.example.ch9_ex2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Second extends AppCompatActivity {
private TextView txtshow;
private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        setTitle("second");
        txtshow=findViewById(R.id.txtshow);
        btn2=findViewById(R.id.button2);
        btn2.setOnClickListener(btn2Listener);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        String num=bundle.getString("TXT");
        int sum=bundle.getInt("Sum");
        String txt=num+sum;
        txtshow.setText(txt);
    }
    private Button.OnClickListener btn2Listener= new
            Button.OnClickListener(){
                public void onClick(View view) {
                    finish();
                }
            };
}