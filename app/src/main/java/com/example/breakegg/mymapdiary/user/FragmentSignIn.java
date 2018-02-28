package com.example.breakegg.mymapdiary.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.breakegg.mymapdiary.MyGlobal;
import com.example.breakegg.mymapdiary.R;

public class FragmentSignIn extends Fragment {

    private final String TAG="M_FragmentSignIn";
    private final String type="sign_in";

    private static Button signIn_btn;
    private EditText password_input,username_input;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        //登录成功 写入sqlite 结束页面
        public void handleMessage(Message msg){
            Log.i(TAG,"handleMessage");
            int user_id=msg.getData().getInt("user_id");
            String user_name=msg.getData().getString("user_name");
            MyGlobal.sqlUser.m_signIn(user_id,user_name);
            Log.i(TAG,MyGlobal.sqlUser.m_get_user_id()+"");
            ActivityUser activityUser=(ActivityUser)getActivity();
            activityUser.m_replaceFragment(new FragmentUserInfo());
        }
    };


    public void onButtonClickListener(){
        signIn_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username=username_input.getText().toString();
                        String password=password_input.getText().toString();
                        Log.i(TAG,username+"=="+password);
                        PostTask postTask=new PostTask(handler,getActivity());
                        postTask.execute(type,username,password);
                    }
                }
        );
    }

    private void _getViews_setListener(View view){
        signIn_btn = view.findViewById(R.id.signIn_btn);
        password_input = view.findViewById(R.id.password_input);
        username_input = view.findViewById(R.id.username_input);
        onButtonClickListener();
    }


    private OnFragmentInteractionListener mListener;

    public FragmentSignIn() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_in, container, false);
        _getViews_setListener(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
