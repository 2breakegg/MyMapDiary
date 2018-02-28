package com.example.breakegg.mymapdiary;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class SetPosition extends Service implements AMapLocationListener {
    public SetPosition() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public static final String TAG="M_SetLocation";

    //声明AMapLocationClient类对象
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;

    public void onCreate() {
        Log.d(TAG, "service onCreate ");

        super.onCreate();
        Notification.Builder builder = new Notification.Builder(this);//新建Notification.Builder对象
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentTitle("定位已启动");//设置标题
        builder.setContentText("定位已启动");//设置内容
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置图片
        builder.setContentIntent(pendingIntent);//执行intent
        Notification notification = builder.getNotification();//将builder对象转换为普通的notification
        startForeground(1,notification);//让 MyService 变成一个前台服务，并在系统状态栏中显示出来。

        Log.i(TAG,"onCreate end");
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "onStartCommand1");
        dingwei();
        Log.d(TAG, "onStartCommand end");
        return super.onStartCommand(intent,flags,startId);
    }

    private void dingwei(){
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);//设置定位监听
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(1000);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.i(TAG,"onLocationChanged");
        if(aMapLocation!=null){
            if(aMapLocation.getErrorCode()==0){
                Log.i(TAG,"定位成功");
                final int locationType = aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double lat = aMapLocation.getLatitude();//获取纬度
                double lon = aMapLocation.getLongitude();//获取经度
                final float accuracy = aMapLocation.getAccuracy();//获取精度信息

                String type = "SetPosition";
//                String userid_str=Integer.toString(MyGlobal.m_getUserId()) ;
                String lat_str=Double.toString(lat) ;
                String lon_str=Double.toString(lon) ;
                Log.i(TAG,"locationType:"+locationType+" lat:"+lat+" lon:"+lon+" accuracy:"+accuracy);

                PostTask postTask=new PostTask(new Handler(){},this);
                postTask.execute(type,lat_str,lon_str);
            }
        }else{
            Log.i(TAG,"amapLocation null");
        }
    }
}
