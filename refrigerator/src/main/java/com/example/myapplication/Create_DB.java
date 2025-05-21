package com.example.myapplication;

import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

public class Create_DB
{
    private MyDB db=null;   //自建的資料庫類別
    Cursor cursor;

    Create_DB(FragmentActivity activity)
    {
        db=new MyDB(activity);  //建立 myDB 物件
        db.Open();

    }

    public String Show()
    {
        return "Submit!";
    }
}