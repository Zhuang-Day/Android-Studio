package com.example.exlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText editID,editPW;
    private Button btnOK,btnReset;
    private String[] login;
    private File filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestStoragePermission();
        editID=findViewById(R.id.editID);
        editPW=findViewById(R.id.editPW);
        btnOK=findViewById(R.id.btnOK);
        btnReset=findViewById(R.id.btnReset);

        btnOK.setOnClickListener(listeren);
        btnReset.setOnClickListener(listeren);

    }
    private void requestStoragePermission(){
        if(Build.VERSION.SDK_INT>=23){
            int hasPermission=checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if(hasPermission!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },1);
                return;
            }
        }
        readFile();
    }
    private Button.OnClickListener listeren=new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnOK:
                    if(editID.getText().toString().equals("")||editPW.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"帳號及密碼都必須輸入",Toast.LENGTH_LONG).show();
                        break;
                    }
                    Boolean flag=false;
                    for(int i=0;i<login.length;i+=2){
                        if(editID.getText().toString().equals(login[i])){
                            flag=true;
                            if(editPW.getText().toString().equals(login[i+1])){
                                new AlertDialog.Builder(MainActivity.this).setTitle("登入")
                                        .setMessage("登入成功！\n歡迎使用本應用程式")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"帳號或密碼不正確",Toast.LENGTH_LONG).show();
                                editPW.setText("");
                                editID.setText("");
                                break;
                            }
                        }
                    }
                    if (!flag){
                        Toast.makeText(getApplicationContext(),"帳號或密碼不正確",Toast.LENGTH_LONG).show();
                        editPW.setText("");
                        editID.setText("");
                        break;
                }
                    break;
                case R.id.btnReset:
                    editID.setText("");
                    editPW.setText("");
                    break;
            }
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode,String [] permissions,int[] grantResults){
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                readFile();
            else{
                Toast.makeText(this,"未取得權限!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }
    private void readFile(){
        filename=new File("sdcard/login.txt");
        try {
            FileInputStream fin=new FileInputStream(filename);
            BufferedReader reader=new BufferedReader(new InputStreamReader(fin));
            String line="",wholedata="";
            int i=0;
            while((line=reader.readLine())!=null){
                wholedata=wholedata+line+"\n";
                i++;
            }
            login=wholedata.split("\n");
            reader.close();
            fin.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}