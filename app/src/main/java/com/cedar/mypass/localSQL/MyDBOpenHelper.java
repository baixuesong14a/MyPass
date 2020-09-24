package com.cedar.mypass.localSQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cedar.mypass.entity.Data;
import com.cedar.mypass.entity.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my.db", null, 1);
    }
    public MyDBOpenHelper(Context context) {
        super(context, "my.db", null, 1);
    }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(64),password VARCHAR(64))");
        db.execSQL("CREATE TABLE data(dataid INTEGER PRIMARY KEY AUTOINCREMENT,softname VARCHAR(64),username VARCHAR(64),password VARCHAR(2048),userid INTEGER)");
    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
    }

    //增
    public void insertData(Data data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO data(softname,username,password,userid) values(?,?,?,?)",
                new Object[]{data.getSoftname(),data.getUsername(),data.getPassword(),data.getUserid()});
    }

    public void insertUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO user(name,password) values(?,?)",
                new String[]{user.getUsername(),user.getPassword()});
    }
    //删
    public void delData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from data where dataid =" + id);
    }

    //改

    //查
    public User selectUser(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM user WHERE name = ?",
                new String[]{name});
        if(cursor.moveToFirst())
        {
            int userid = cursor.getInt(cursor.getColumnIndex("userid"));
            String username = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            return new User(userid,username,password);
        }
        cursor.close();
        return null;
    }

    public List<Data> selectlistData(int userid){
        List<Data> listdata = new ArrayList<Data>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM data WHERE userid = " + userid, null);
        //System.out.println("select Data");
        while(cursor.moveToNext())
        {
            int dataid = cursor.getInt(cursor.getColumnIndex("dataid"));
            String softname = cursor.getString(cursor.getColumnIndex("softname"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            int useridf = cursor.getInt(cursor.getColumnIndex("userid"));
            //System.out.println(dataid + name + password);
            listdata.add(new Data(dataid,softname,username,password,useridf));
        }
        cursor.close();
        return listdata;
    }

    public List<Data> selectlistData(int userid,String string){
        List<Data> listdata = new ArrayList<Data>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM data WHERE userid = " + userid, null);
        //System.out.println("select Data");
        while(cursor.moveToNext())
        {
            int dataid = cursor.getInt(cursor.getColumnIndex("dataid"));
            String softname = cursor.getString(cursor.getColumnIndex("softname"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            int useridf = cursor.getInt(cursor.getColumnIndex("userid"));
            //System.out.println(dataid + name + password);
            if(string.equals(softname))
                listdata.add(new Data(dataid,softname,username,password,useridf));
        }
        cursor.close();
        return listdata;
    }
}