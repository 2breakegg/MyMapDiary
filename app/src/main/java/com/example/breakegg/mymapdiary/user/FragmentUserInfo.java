package com.example.breakegg.mymapdiary.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.breakegg.mymapdiary.MyGlobal;
import com.example.breakegg.mymapdiary.R;

public class FragmentUserInfo extends Fragment implements View.OnClickListener{

    private String TAG="M_FragmentUserInfo";
    private Button sign_in_btn,sign_up_btn,sign_out_btn;
    private ConstraintLayout SignLayout,InfoLayout;
    private TextView username_textView;
    private ActivityUser activityUser;


    @Override
    public void onStart() {
        Log.d(TAG,"onStart");
        if(MyGlobal.sqlUser.m_isSignIn()){
            Log.d(TAG, "is sign in");
            SignLayout.setVisibility(View.INVISIBLE);
            InfoLayout.setVisibility(View.VISIBLE);
            _showInfo();
        }else{
            Log.d(TAG, "not sign in");
            SignLayout.setVisibility(View.VISIBLE);
            InfoLayout.setVisibility(View.INVISIBLE);
        }
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        int Id=view.getId();
        switch (Id){
            case R.id.sign_in_btn:
                activityUser.m_replaceFragment(new FragmentSignIn());
                break;
            case R.id.sign_up_btn:
                Log.d(TAG, "onClick: sign_up_btn");
                activityUser.m_replaceFragment(new FragmentSignUp());
                break;
            case R.id.sign_out_btn:
                MyGlobal.sqlUser.m_signOut();
                activityUser.m_replaceFragment(new FragmentUserInfo());
                break;
        }
    }

    private void _showInfo(){
        Log.d(TAG, "_showInfo: ");
        String username=MyGlobal.sqlUser.m_get_user_name();
        username_textView.setText(username);
    }




    private OnFragmentInteractionListener mListener;

    private void _getViews_setListener(View view){
        Log.d(TAG, "_getViews_setListener: ");
        activityUser=(ActivityUser)getActivity();

        sign_in_btn=(Button)view.findViewById(R.id.sign_in_btn);
        sign_up_btn=(Button)view.findViewById(R.id.sign_up_btn);
        sign_out_btn=(Button)view.findViewById(R.id.sign_out_btn);
        sign_in_btn.setOnClickListener(this);
        sign_up_btn.setOnClickListener(this);
        sign_out_btn.setOnClickListener(this);

        username_textView=(TextView) view.findViewById(R.id.username_textView);
        InfoLayout=(ConstraintLayout)view.findViewById(R.id.InfoLayout);
        SignLayout=(ConstraintLayout)view.findViewById(R.id.SignLayout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_info, container, false);
        _getViews_setListener(view);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
