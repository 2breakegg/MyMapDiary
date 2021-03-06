package com.example.breakegg.mymapdiary.user;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.breakegg.mymapdiary.MyGlobal;

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
    String url_sign_up= MyGlobal.host+"sign_up.php";
    String url_sign_in=MyGlobal.host+"sign_in.php";
    Context context;
    private Handler handler = null;


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

    protected Object doInBackground(Object... params) {
        Log.d(TAG,"doInBackground");

        String type = (String)params[0];

        if(type.equals("sign_up")) {
            try {
                Log.d(TAG, "sign_up ");
                String username = (String)params[1];
                String password = (String)params[2];
                byte[] password_byte=encryptMD5(password.getBytes());
                password=new String(password_byte);
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                                   URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                String result=_postSend(url_sign_up,post_data);
                Log.d(TAG,result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else  if(type.equals("sign_in")){
            try {
                String username = (String)params[1];
                String password = (String)params[2];
                byte[] password_byte=encryptMD5(password.getBytes());
                password=new String(password_byte);
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+ "&" +
                                   URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                String result=_postSend(url_sign_in,post_data);
                Log.d(TAG,result);
                return result;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    protected void onPostExecute(Object result){
        try {
            //解析服务器数据
            JSONTokener jsonTokener = new JSONTokener((String) result);
            JSONObject jSONObject;        
            jSONObject = (JSONObject) jsonTokener.nextValue();
            String text = (String) jSONObject.get("text");          
            int status = (int) jSONObject.get("status");          
            Log.d(TAG,text);
            Toast.makeText(context, (String)text,Toast.LENGTH_SHORT).show();
            //判断(注册,登录...)是否成功 成功退出 否则不变

            if(status==1) {
                Log.d(TAG,"status==1");
                int user_id = Integer.parseInt((String)jSONObject.get("user_id"));
                Log.d(TAG,"status==1.5");
                String user_name=(String) jSONObject.get("user_name");
                Log.d(TAG,"status==12");
                Message m = new Message();
                Bundle bundle = new Bundle();
                Log.d(TAG,"status==13");
                bundle.putInt("user_id",user_id);
                bundle.putString("user_name",user_name);
                m.setData(bundle);
                Log.d(TAG,"status==14");
                handler.sendMessage(m);
                Log.d(TAG,"status==15");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }
}
