package com.example.breakegg.mymapdiary;

import android.content.Context;
import android.os.Handler;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by break on 2018/2/9.
 */

public class GetPosition  {

    public final static long M_dayTime=86400;
    public static final long M_hourTime=3600;
    final static public long  M_minuteTime=60;

    public static long M_monthTime(int year,int month){
        return M_monthDay(year,month)*M_dayTime;
    }
    public static long M_yearTime(int year){
        return M_yearDay(year)*M_dayTime;
    }
    public static int M_yearDay(int year){
        if(year%4==0){
            if( year%100==0 && year%400!=0){
                return 365;
            }else{
                return 366;
            }
        }else{
            return 365;
        }
    }
    public static int M_monthDay(int year,int month){
        if(month==2){
            switch (M_yearDay(year)){
                case 366:
                    return 28;
                default:
                    return 29;
            }
        }else if(month==4||month==6||month==9||month==11){
            return 30;
        }else{
            return 31;
        }
    }

    public static long M_todayTime(){
        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int date=c.get(Calendar.DATE);
        GregorianCalendar g=new GregorianCalendar(year,month,date);
        return g.getTimeInMillis()/1000;
    }

    public static void M_getData(long start_date, long end_date, int userid, Context context) {
        String start_date_str = Long.toString(start_date);
        String end_date_str = Long.toString(end_date);
        String userid_str = Long.toString(userid);
        PostTask postTask = new PostTask(new Handler() {}, context);
        postTask.execute(start_date_str, end_date_str, userid_str);
    }
    public static void M_getData(long start_date, long end_date, int userid, Handler handler) {
        String start_date_str = Long.toString(start_date);
        String end_date_str = Long.toString(end_date);
        String userid_str = Long.toString(userid);
        PostTask postTask = new PostTask(handler);
        postTask.execute(start_date_str, end_date_str, userid_str);
    }
    public static void M_getData(long start_date, long end_date, int userid) {
        String start_date_str = Long.toString(start_date);
        String end_date_str = Long.toString(end_date);
        String userid_str = Long.toString(userid);
        PostTask postTask = new PostTask();
        postTask.execute(start_date_str, end_date_str, userid_str);
    }
}
