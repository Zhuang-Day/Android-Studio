package com.example.myapplication;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class Fragment_Home_Page extends Fragment {

    private EditText edtFood;
    private Button btnExpiredDate,btnSubmit;
    private Spinner spnCategory,spnRemid;

    private int mYear, mMonth, mDay;
    ArrayList<Spn> Category=new ArrayList<Spn>();
    ArrayList<Spn> Remid=new ArrayList<Spn>();

    private MyDB db=null;   //自建的資料庫類別
    String category;   //儲存被點選的Category值
    String remid;   //儲存被點選的Remid值
    String expireddate;   //儲存被點選的Remid值
    static int ID;   //儲存類別位置

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_main, container, false);

        edtFood=view.findViewById(R.id.edtFood);
        btnExpiredDate=view.findViewById(R.id.btnExpiredDate);
        btnSubmit=view.findViewById(R.id.btnSubmit);
        spnCategory=view.findViewById(R.id.spnCategory);
        spnRemid=view.findViewById(R.id.spnRemid);

        btnExpiredDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        expireddate = year+"-"+(month+1)+"-"+day;
                        btnExpiredDate.setText(expireddate);
                    }
                }, mYear,mMonth, mDay).show();
            }
        });
        btnSubmit.setOnClickListener(Create);   //儲存資料

        Category.add(new Spn_Category("水果"));
        Category.add(new Spn_Category("蔬菜"));
        Category.add(new Spn_Category("飲料"));
        Category.add(new Spn_Category("乳製品"));
        Category.add(new Spn_Category("肉製品"));
        Category.add(new Spn_Category("加工製品"));

        /*設置spnCategory*/
        SpnArrayAdapter mAdapter = new SpnArrayAdapter(view.getContext(),Category);//使用自定義的ArrayAdapter
        spnCategory.setAdapter(mAdapter);

        /*設置Spinner點擊事件*/
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//Spinner點擊後
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Spn_Category mData = (Spn_Category) parent.getItemAtPosition(position);
                category=mData.get();
                ID=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Remid.add(new Spn_Remid("一星期前"));
        Remid.add(new Spn_Remid("三天前"));
        Remid.add(new Spn_Remid("當天"));

        /*設置spnRemid*/
        SpnArrayAdapter mAdapter2 = new SpnArrayAdapter(view.getContext(),Remid);//使用自定義的ArrayAdapter
        spnRemid.setAdapter(mAdapter2);

        /*設置Spinner點擊事件*/
        spnRemid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//Spinner點擊後
            @SuppressLint("SetTextI19n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Spn_Remid mData = (Spn_Remid) parent.getItemAtPosition(position);
                remid=mData.get();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        db=new MyDB(getActivity());  //建立 myDB 物件
        db.Open();

        return view;
    }
    private Button.OnClickListener Create=new Button.OnClickListener(){
        @Override
        public void onClick(View v)
        {
            if(edtFood.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"請輸入食品",Toast.LENGTH_SHORT)
                        .show();
            }
            else
            {
                try
                {
                    String food=edtFood.getText().toString();
                    db.FoodsAppend(food,expireddate,remid,ID);
                    Toast.makeText(getActivity(),"已放入冰箱!",Toast.LENGTH_SHORT)
                            .show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(),"資料不正確!",Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    };
    public void onDestroy()
    {
        super.onDestroy();
        db.Close();
    }
    public class SpnArrayAdapter extends ArrayAdapter
    {
        //建構子
        public SpnArrayAdapter(@NonNull Context context, @NonNull List<Spn> mData)
        {
            super(context,0,mData);
        }
        @NonNull
        @Override
        //getView為設置未點開時的Spinner畫面
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createView(position,convertView,parent,false);
        }
        //getDropDownView為設置點開後的畫面
        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createView(position,convertView,parent,true);
        }
        //複寫介面
        @SuppressLint("SetTextI18n")
        private View createView(int position, View convertView,ViewGroup parent,Boolean ageDisplay){
            convertView = LayoutInflater.from(getContext()).inflate(    //綁定介面
                    R.layout.spinner_category, parent, false);

            TextView txtTitle=convertView.findViewById(R.id.txtTitle);

            Spn item = (Spn) getItem(position);   //取得每一筆的資料內容
            if (item != null)
            {
                txtTitle.setText(item.get());
            }
            return convertView;
        }
        //複寫介面
        @SuppressLint("SetTextI19n")
        private View createView2(int position, View convertView,ViewGroup parent,Boolean ageDisplay){
            convertView = LayoutInflater.from(getContext()).inflate(    //綁定介面
                    R.layout.spinner_date, parent, false);

            TextView txtTitle=convertView.findViewById(R.id.txtTitle);

            Spn item = (Spn) getItem(position);   //取得每一筆的資料內容
            if (item != null)
            {
                txtTitle.setText(item.get());
            }
            return convertView;
        }
    }
}