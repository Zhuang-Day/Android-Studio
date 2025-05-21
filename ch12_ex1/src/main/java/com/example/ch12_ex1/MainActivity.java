package com.example.ch12_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
private EditText edtID,edtPW,edtContent;
private Button btnAppend,btnClear,btnEnd;
private static final String FILENAME="login.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtID=findViewById(R.id.edItID);
        edtPW=findViewById(R.id.edtPW);
        edtContent=findViewById(R.id.edtContent);
        btnAppend=findViewById(R.id.btnAppend);
        btnClear=findViewById(R.id.btnClear);
        btnEnd=findViewById(R.id.btnEnd);

        DisplayFile(FILENAME);
        btnAppend.setOnClickListener(listeren);
        btnClear.setOnClickListener(listeren);
        btnEnd.setOnClickListener(listeren);


    }
    private Button.OnClickListener listeren=new
            Button.OnClickListener(){

                @Override
                public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.btnAppend:
                            if(edtID.getText().toString().equals("")||edtPW.getText().toString().equals("")){
                                Toast.makeText(getApplicationContext(),"帳號及密碼都必須輸入",Toast.LENGTH_LONG).show();
                                break;
                            }
                            FileOutputStream fout=null;
                            BufferedOutputStream buffout=null;
                            try {
                                fout=openFileOutput(FILENAME,MODE_APPEND);
                                buffout=new BufferedOutputStream(fout);
                                buffout.write(edtID.getText().toString().getBytes());
                                buffout.write("\n".getBytes());
                                buffout.write(edtPW.getText().toString().getBytes());
                                buffout.write("\n".getBytes());
                                buffout.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            DisplayFile(FILENAME);
                            edtID.setText("");
                            edtPW.setText("");
                            break;
                        case R.id.btnClear:
                            try {
                                fout=openFileOutput(FILENAME,MODE_PRIVATE);
                                fout.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            DisplayFile(FILENAME);
                            break;
                        case R.id.btnEnd:
                            finish();
                    }
                }
            };
    private void DisplayFile(String fname){
        FileInputStream fin=null;
        BufferedInputStream buffin=null;
        try{
            fin=openFileInput(fname);
            buffin=new BufferedInputStream(fin);
            byte[] buffbyte=new byte[20];
            edtContent.setText("");
            //讀取資料，直到檔案結束
            do{
                int flag =buffin.read(buffbyte);
                if(flag==-1)break;
                else
                    edtContent.append(new String(buffbyte),0,flag);
            }while(true);
            buffin.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}