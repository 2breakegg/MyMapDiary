package com.example.breakegg.mymapdiary;

import android.app.Activity;

import com.example.breakegg.mymapdiary.user.SQLUser;

/**
 * Created by break on 2018/2/4.
 */

public class MyGlobal {
    public static SQLUser sqlUser;

//    public static String login_url = "http://198.181.56.25/"; //    远程服务器
    public static String host="http://192.168.0.52:8080/";    //    本地



    public static int m_getUserId(){
        return sqlUser.m_get_user_id();
    }
    public static String m_getUserName(){
        return sqlUser.m_get_user_name();
    }
}
