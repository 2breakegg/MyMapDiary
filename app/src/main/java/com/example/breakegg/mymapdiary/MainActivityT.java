package com.example.breakegg.mymapdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class MainActivityT extends AppCompatActivity implements AMapLocationListener{

    public static final String MY_TAG="M-MainActivityT";

    //声明AMapLocationClient类对象
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_t);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dingwei();
    }

    private void dingwei(){
        Log.d(MY_TAG, "dingwei");
        mlocationClient = new AMapLocationClient(this);
        Log.d(MY_TAG, "dingwei0.5");
        mLocationOption = new AMapLocationClientOption();
        Log.d(MY_TAG, "dingwei1");
        mlocationClient.setLocationListener(this);//设置定位监听
        Log.d(MY_TAG, "dingwei2");
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        Log.d(MY_TAG, "dingwei3");
        mLocationOption.setInterval(1000);
        Log.d(MY_TAG, "dingwei4");
        mlocationClient.setLocationOption(mLocationOption);
        Log.d(MY_TAG, "dingwei5");
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.i(MY_TAG,"onLocationChanged");
        if(aMapLocation!=null){
            if(aMapLocation.getErrorCode()==0){
                Log.i(MY_TAG,"定位成功");
                final int locationType = aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double lat = aMapLocation.getLatitude();//获取纬度
                double lon = aMapLocation.getLongitude();//获取经度
                final float accuracy = aMapLocation.getAccuracy();//获取精度信息

                String type = "login";
                String userid_str=Integer.toString(2) ;
                String lat_str=Double.toString(lat) ;
                String log_str=Double.toString(lon) ;
                Log.i(MY_TAG,"locationType:"+locationType+" lat:"+lat+" lon:"+lon+" accuracy:"+accuracy);
            }
        }else{
            Log.i(MY_TAG,"amapLocation null");
        }
    }
}
