package com.example.breakegg.mymapdiary;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * Created by break on 2018/1/10.
 */

public class PostTask extends AsyncTask<Object,Void,Object> {
    public static final String TAG="M_PostTask";
    String url_SetLocation= MyGlobal.host+"setposition.php";
    Context context=null;
    private Handler handler = null;


    PostTask(){
        this.handler=new Handler();
        this.context=MyApplication.getContext();
    }
    PostTask(Handler handler){
        this.handler=handler;
        this.context=MyApplication.getContext()
    }
    PostTask(Context context){
        this.handler=new Handler();
        this.context=context;
    }
    PostTask(Handler handler, Context context){
        this.handler=handler;
        this.context=context;
    }


    private String _postSend(String urlStr,String post_data){
        Log.d(TAG, "_postSend");
        Log.d(TAG, post_data);
        String result = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            //======================数据发送end======================
            //======================数据接收======================
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line = "";

            Log.d(TAG, "bufferedReader.readLine");
            while ((line = bufferedReader.readLine()) != null) {
                Log.d(TAG, "readLine start");
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (Exception e) {e.printStackTrace();}
        return result;
    }

    protected String doInBackground(Object... params) {
        Log.d(TAG,"doInBackground");
        String type = (String)params[0];

        if(type.equals("SetPosition")) {
            try {
                Log.d(TAG, "sign_up ");
                String lat = (String)params[1];
                String lon = (String)params[2];
                String userid = Integer.toString(MyGlobal.m_getUserId());
                String post_data = URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8") + "&" +
                                   URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8") + "&" +
                                   URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8") ;
                String result=_postSend(url_SetLocation,post_data);
                Log.d(TAG,result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("GetPosition")){
            try{
                Log.d(TAG, "sign_up ");
                String start_date = (String)params[1];
                String end_date = (String)params[2];
                String userid = (String)params[3];
                String post_data =  URLEncoder.encode("start_date", "UTF-8") + "=" + URLEncoder.encode(start_date, "UTF-8") + "&" +
                                    URLEncoder.encode("end_date", "UTF-8") + "=" + URLEncoder.encode(end_date, "UTF-8") + "&" +
                                    URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8") ;
                String result=_postSend(url_SetLocation,post_data);
                Log.d(TAG,result);
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }

    protected void onPostExecute(String result){
        if(result==""){
            Toast.makeText(context, "程序错误",Toast.LENGTH_SHORT).show();
            return;
        }
        Message m = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("data",result);
        handler.sendMessage(m);
    }

    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }
}
