package com.example.ch9_ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private EditText edt1,edt2;
private TextView txt1,txt2;
private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ch09_ex2");
        edt1=findViewById(R.id.edt1);
        edt2=findViewById(R.id.edt2);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(btnPage2Listener);
    }
    private Button.OnClickListener btnPage2Listener=new
            Button.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,Second.class);

                    int num=Integer.parseInt(edt1.getText().toString());
                    int num1=Integer.parseInt(edt2.getText().toString());
                    String txt=num+"+"+num1+"=";

                    Bundle bundle=new Bundle();
                    bundle.putString("TXT",txt);
                    int sum=num+num1;
                    bundle.putInt("Sum",sum);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }
            };
}