package com.example.breakegg.mymapdiary.friend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breakegg.mymapdiary.GetImage;
import com.example.breakegg.mymapdiary.MyApplication;
import com.example.breakegg.mymapdiary.MyGlobal;
import com.example.breakegg.mymapdiary.PostTask;
import com.example.breakegg.mymapdiary.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class NewFriend extends AppCompatActivity {

    String TAG="M_ActivityFriend";
    List<Friend> friends=new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        initFriends();
        showFriends();
        String userid_str= Long.toString(MyGlobal.m_getUserId());

        PostTask postTask = new PostTask(friendListHandler, NewFriend.this);
        postTask.execute("FriendList", userid_str);
    }

    //获取好友列表
    @SuppressLint("HandlerLeak")
    public Handler friendListHandler=new Handler() {
        public void handleMessage(Message msg) {
            Log.i(TAG,"handleMessage");
            String data_str=msg.getData().getString("data");
            Log.i(TAG,data_str);
            try{
                JSONTokener jsonTokener = new JSONTokener(data_str);
                JSONObject jSONObject = (JSONObject) jsonTokener.nextValue();
                int status=(int) jSONObject.get("status");
                if(status==1) {
                    JSONArray jSONArray=jSONObject.getJSONArray("data");
                    friends=new ArrayList<>();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        jSONObject = (JSONObject) jSONArray.get(i);
                        int userid=Integer.parseInt(jSONObject.getString("userid"));
                        String username=jSONObject.getString("username");
                        Log.i(TAG, jSONObject.toString());
                        Log.d(TAG, "userid: "+userid+" username:"+username);
                        friends.add(new Friend(username,userid));
                    }
                    showFriends();
                }else{
                    Toast.makeText(MyApplication.getContext(),"FragmentMap.java handler.handleMessage 错误",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                Log.w(TAG, e );
            }
        }
    };

    //获取好友头像
    @SuppressLint("HandlerLeak")
    public Handler iconHandler=new Handler() {
        public void handleMessage(Message msg) {
            Log.d(TAG, "friends: "+friends);

            Bitmap bmp=(Bitmap)msg.obj;
            int userid=msg.arg1;

            for(Friend friend:friends){
                if(friend.userid==userid){
                    Log.d(TAG, "iconHandler userid"+userid);
                    friend.setIcon(bmp);
                    break;
                }
            }
        }
    };

    public void initFriends(){
        Friend f1=new Friend("userid:31",31);
        friends.add(f1);
        Friend f2=new Friend("userid:33",33);
        friends.add(f2);
        Friend f3=new Friend("userid:123",123);
        friends.add(f3);
    }

    public void showFriends(){

        FriendAdapter adapter = new FriendAdapter(NewFriend.this, R.layout.friend_item, friends);
        listView =  findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TextView textView = view.findViewById(R.id.fruit_name);
                //textView.setText("null");
                //Fruit fruit = fruitList.get(i);
                //Toast.makeText(ActivityListViewPic.this,fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        for(Friend friend:friends){
            GetImage getImage=new GetImage(iconHandler,friend.userid);
            getImage.M_send();
        }
    }

    public class Friend{
        public String name;
        public int userid;
        private Bitmap icon;

        public void setIcon(Bitmap bitmap){
            icon=bitmap;
            FriendAdapter adapter=(FriendAdapter) listView.getAdapter();
            adapter.notifyDataSetChanged();
        }

        public Friend(String name,int userid){
            this.name=name;
            this.userid=userid;
        }

        public String toString(){
            return "{name:"+name+" userid:"+userid+"}";
        }
    }

    public class FriendAdapter extends ArrayAdapter<Friend> {
        private int resourceId;

        public FriendAdapter(@NonNull Context context, int resource, List<Friend> objects) {
            super(context, resource,objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Friend friend=getItem(position);
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            } else {
                view = convertView;
            }

            TextView friendName=view.findViewById(R.id.friend_name);
            friendName.setText(friend.name);

            if(friend.icon!=null) {
                ImageView friendImage = view.findViewById(R.id.friend_image);
                friendImage.setImageBitmap(friend.icon);
            }
            return view;
        }
    }
}
