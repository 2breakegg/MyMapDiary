package com.example.breakegg.mymapdiary.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by break on 2018/1/11.
 */

public class SQLUser extends SQLiteOpenHelper {
    public final String Tag="M_SQLUser";
    public static final String CREATE_USER="create table user ("
            +"user_id integer,"
            +"user_name text,"
            +"login_status interger)";
    private Context mContext;

    public SQLUser(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(Tag,"onCreate 0");
//        Toast.makeText(mContext,"Create CREATE_USER start",Toast.LENGTH_SHORT).show();
        db.execSQL(CREATE_USER);
//        Toast.makeText(mContext,"Create CREATE_USER end",Toast.LENGTH_SHORT).show();
        Log.i(Tag,"onCreate 1");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //=========获取用户id 如返回-1 则表示未登录
    public int m_get_user_id(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM user",null);
        if(cursor.moveToFirst()){
            Integer user_id=cursor.getInt(cursor.getColumnIndex("user_id"));
            return user_id;
        }else{
            return -1;
        }
    }

    public String m_get_user_name(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM user",null);
        if(cursor.moveToFirst()){
            String user_name=cursor.getString(cursor.getColumnIndex("user_name"));
            return user_name;
        }else{
            return "未登录";
        }
    }

    public void m_signIn(int user_id,String user_name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        db.delete("user",null,null);
        values.put("user_id",user_id);
        values.put("user_name",user_name);
        values.put("login_status",1);
        db.insert("user",null,values);
        values.clear();
    }

    public void m_signOut(){
        //todo 退到主页 主页onStart()
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("user",null,null);
    }

    public boolean m_isSignIn(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM user",null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
}
