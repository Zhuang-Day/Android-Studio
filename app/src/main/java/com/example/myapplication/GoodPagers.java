package com.example.myapplication;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.NOTIFICATION_SERVICE;

public class GoodPagers extends RelativeLayout {//繼承別的Layout亦可
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    private Context context;
    private MyDB db=null;   //自建的資料庫類別
    protected Notification notification;
    private NotificationManager manager;
    public GoodPagers(Context context, int pageNumber) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_pagers, null);//連接頁面
        this.context=context;

        db=new MyDB(context);  //建立 myDB 物件
        db.Open();

        //如果新增的食物類別與pageNumber一樣在進入新增物件到介面上
        //為新增類別功能

        addItem(pageNumber);//幾個食物就輸入幾個物件

        //設置RecycleView
        mRecyclerView = view.findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        myListAdapter = new MyListAdapter();
        mRecyclerView.setAdapter(myListAdapter);

        // 將元件放入ViewPager
        addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void addItem(int pageNumber)
    {
        //如果新增的食物類別與pageNumber一樣在進入新增物件到介面上
        //為新增類別功能
        String Type[]= GoodPagerAdapter.FoodType;

        int count=db.Count(Type[pageNumber-1]);
        for(int id=1;id<=count;id++){
            HashMap<String, String> hashMap = new HashMap<>();
            //放置過期時間
            String Ex=db.getExDate(Type[pageNumber-1],id);
            String show=compareToTime(Ex);//比對時間
            hashMap.put("show",show);//key為show，抓過期時間
            //放置食物名以及所屬類別
            String food=db.getFOOD(Type[pageNumber-1],id);
            hashMap.put("catagory",Type[pageNumber-1]);
            hashMap.put("Food",food);//key為food名
            hashMap.put("id", String.valueOf(id));

            arrayList.add(hashMap);
        }
    }
    //比對時間
    private String compareToTime(String Ex){
        //已過期
        //未過期 顯示過期時間也就是使用者輸入的日期
        String show="";
        if(Ex!=""){
            String today;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Date date1= null,date2 = null;
            try
            {
                date1 = sdf.parse(today);
                date2 = sdf.parse(Ex);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            if (date1.compareTo(date2) < 0)
            {
                show=Ex;//顯示過期時間
            }
            else if (date1.compareTo(date2) > 0)
            {
                show = "已過期";//顯示過期天數
            }
            else if (date1.compareTo(date2) == 0)
            {
                show = "注意當天過期";//顯示當天過期
                // 取得NotificationManager物件
                NotificationManager manager = (NotificationManager)
                        context.getSystemService(NOTIFICATION_SERVICE);

                // 建立大圖示需要的Bitmap物件
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo_img);

                // 建立通知物件，設定小圖示、大圖示、內容標題、內容訊息、時間
                Notification notification = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo_img)
                        .setLargeIcon(largeIcon)
                        .setContentTitle("過期注意!!")
                        .setContentText("冰箱中有今日過期食品")
                        .setWhen(System.currentTimeMillis())
                        .build();

                // 使用0為編號發出通知
                manager.notify(0, notification);
            }
            else
            {
                show ="咋到這的？";
            }
        }
        else
        {
            show="沒有時間資料";
        }
        return show;
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView txtFoodName, txtShowDate;
            private View mView;
            private Button btnDelete,btnedit;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtFoodName = itemView.findViewById(R.id.txtFoodName);
                txtShowDate = itemView.findViewById(R.id.txtShowDate);
                btnDelete=itemView.findViewById(R.id.btnDelete);//刪除
                btnedit=itemView.findViewById(R.id.btnedit);//修改
                mView = itemView;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
        {
            //寫入食物名
            try{
                //已過期
                //未過期 顯示過期時間也就是使用者輸入的日期
                String show=arrayList.get(position).get("show");//過期日期
                getTxtBackground(holder,show);
                holder.txtShowDate.setText("過期時間："+show);
                holder.txtFoodName.setText(arrayList.get(position).get("Food"));//食物名稱

            }catch (IndexOutOfBoundsException e){
            }
            holder.btnDelete.setOnClickListener((v -> {

                String Food = arrayList.get(position).get("Food"); //刪除id為"食物名"的資料
                String catagory = arrayList.get(position).get("catagory"); //抓取catagory為此食物類別的資料
                int id= Integer.parseInt(arrayList.get(position).get("id"));
                String Ex=db.getExDate(catagory,id);//抓取過期時間

                AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(context);
                alertDialog.setTitle("請問確定刪除"+Food);
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //資料庫刪除
                        try{

                            db.DeleteFood(catagory,Food,Ex);//刪除食物
                            db.update(catagory,id);//更新
                        }catch (IndexOutOfBoundsException e){
                            Toast.makeText(context,"陣列超過長度",Toast.LENGTH_LONG)
                                    .show();
                        }
                        catch (Exception e){
                            Toast.makeText(context,"出錯請注意",Toast.LENGTH_LONG)
                                    .show();
                        }
                        //移除陣列
                        arrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,arrayList.size());
                        Toast.makeText(context,"刪除"+Food,Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.setNeutralButton("取消",(dialog, which) -> {
                    Toast.makeText(context,"取消",Toast.LENGTH_SHORT).show();
                });
                alertDialog.setCancelable(false);
                alertDialog.show();


            }));//holder.btnDelete
            holder.btnedit.setOnClickListener((v -> {
                //inflate目的是把自己設計xml的Layout轉成View，作用類似於findViewById，它用於一個沒有被載入或者想要動態

                //載入的介面，當被載入Activity後才可以用findViewById來獲得其中界面的元素

                //取得原物件的食物名、類別名、過期時間
                String Food = arrayList.get(position).get("Food"); //刪除id為"食物名"的資料
                String catagory = arrayList.get(position).get("catagory"); //抓取catagory為此食物類別的資料
                int id= Integer.parseInt(arrayList.get(position).get("id"));
                String Ex=db.getExDate(catagory,id);//抓取過期時間
                String[] time = Ex.split("-");
                //設定彈跳視窗內容
                LayoutInflater inflater = LayoutInflater.from(context);
                final View view = inflater.inflate(R.layout.show_edit, null);
                //語法一：new AlertDialog.Builder(主程式類別).XXX.XXX.XXX;
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog .setTitle("進入修改介面").setView(view);//顯示原食物名以及過期時間
                EditText editFood =(view.findViewById(R.id.editFood));

                //年月日
                EditText editExY =(view.findViewById(R.id.editExY));
                EditText editExM =(view.findViewById(R.id.editExM));
                EditText editExD =(view.findViewById(R.id.editExD));

                editFood.setText(Food);
                editExY.setText(time[0]);
                editExM.setText(time[1]);
                editExD.setText(time[2]);
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //資料庫修改
                                try{
                                    String food= String.valueOf(editFood.getText());
                                    String exY=String.valueOf(editExY.getText());
                                    String exM=String.valueOf(editExM.getText());
                                    String exD=String.valueOf(editExD.getText());
                                    String ex="";
                                    ex=exY+"-"+exM+"-"+exD;
                                    db.edit(catagory,food,ex,id);

                                    Toast.makeText(context, catagory+" "+food+" "+ex,Toast.LENGTH_SHORT).show();
                                }catch (IndexOutOfBoundsException e){
                                    Toast.makeText(context,"陣列超過長度",Toast.LENGTH_LONG)
                                            .show();
                                }
                                catch (Exception e){
                                    Toast.makeText(context,"出錯請注意",Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                });
                dialog.setNeutralButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }

                });
                dialog.show();
                

            }));//holder.btnedit
        }

        public void getTxtBackground(@NonNull ViewHolder holder,String show){
            switch (show){
                case"已過期":
                    holder.txtFoodName.setBackgroundColor(Color.parseColor("#CB1B45"));
                    break;
                case "注意當天過期":
                    holder.txtFoodName.setBackgroundColor(Color.parseColor("#F6C555"));
                    break;
                default:
                    holder.txtFoodName.setBackgroundColor(Color.parseColor("#66BAB7"));

            }
        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}