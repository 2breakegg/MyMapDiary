package com.example.breakegg.mymapdiary.user;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.breakegg.mymapdiary.R;


public class ActivityUser extends AppCompatActivity implements
        FragmentSignIn.OnFragmentInteractionListener,
        FragmentSignUp.OnFragmentInteractionListener,
        FragmentUserInfo.OnFragmentInteractionListener{

    private String TAG="M_ActivityUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        m_replaceFragment(new FragmentUserInfo());
    }


    public void m_replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.UserFrameLayout,fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
