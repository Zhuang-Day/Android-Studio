package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
public class MyDB
{
    public SQLiteDatabase db=null;  //資料庫類別
    private final static String DATABASE_NAME="icebox.db"; //資料庫名稱
    private static String[] TABLE_NAME2={"水果","蔬菜","飲料","乳製品","肉製品","加工製品"}; //不同類別的食物資料表名稱
    private final static String _ID="_id"; //資料表欄位

    private final static String FOOD="food";    //食物
    private final static String REMID="remid";  //過期提醒
    private final static String EXRIREDDATE="expireddate";  //過期日

    private Context myContext=null;
    public MyDB(Context ctx)
    {
        this.myContext=ctx; //傳入物件的 Activity
    }
    public void Open() throws SQLException  //開啟已經存在的資料庫
    {
        db=myContext.openOrCreateDatabase(DATABASE_NAME,0,null);
        try
        {
            /*建立個別類別資料表*/
            for(int i=0;i<TABLE_NAME2.length;i++)
            {
                String a=TABLE_NAME2[i];    //接收Category
                String TABLE2="CREATE TABLE "+a+"("+_ID
                        +" INTEGER PRIMARY KEY,"+FOOD+" TEXT NOT NULL,"+EXRIREDDATE+" TIME STRING NOT NULL,"+REMID+" TEXT NOT NULL"+")";
                db.execSQL(TABLE2);
            }
        }
        catch (Exception e)
        {

        }
    }
    public void Close() //關閉資料庫
    {
        db.close();
    }
    public long FoodsAppend(String food,String expireddate,String remid,int ID)   //新增一筆類別裡面食物的資料
    {
        ContentValues args=new ContentValues();
        args.put(FOOD,food);
        args.put(EXRIREDDATE,expireddate);
        args.put(REMID,remid);
        return db.insert(TABLE_NAME2[ID],null,args);
    }
    public void DeleteFood(String catagory,String food,String Ex)   //刪除指定資料
    {
        db.delete(catagory,FOOD+"='"+food+"' and "+EXRIREDDATE+"='"+Ex+"'",null);
    }
    public String getFOOD(String catagory,int id)throws SQLException//查詢特定資料
    {
        Cursor mcursor=db.query(catagory,
                new String[] {FOOD,EXRIREDDATE,REMID},
                id+"=_ID",null,null,null,null,null);
        if(mcursor!=null){
            mcursor.moveToFirst();
        }
        String food=mcursor.getString(0);
        return food;
    }
    public int Count(String catagory)
    {
        Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM "+catagory,null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }
    public String getExDate(String catagory,int id)throws SQLException//查詢特定資料
    {
        String Exdate="";
        try{
            Cursor mcursor=db.query(catagory,
                    new String[] {FOOD,EXRIREDDATE,REMID},
                    id+"=_ID",null,null,null,null,null);
            if(mcursor!=null){
                mcursor.moveToFirst();
            }
            Exdate=mcursor.getString(1);

        }catch (IndexOutOfBoundsException e){

        }
        return Exdate;
    }
    public void update(String catagory,int id){
        db.execSQL(" UPDATE " + catagory
                + " SET _ID=_ID-1 "
                + " WHERE _ID>" +id);
    }
    public void edit(String catagory,String food,String Ex,int id){
        db.execSQL(" UPDATE " + catagory
                + " SET food='"+food+"',"+"expireddate='"+Ex+"'"
                + " WHERE _ID=" +id);

    }
}