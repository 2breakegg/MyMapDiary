package com.example.breakegg.mymapdiary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentMap extends Fragment {
    private MapView mMapView = null;
    private AMap aMap=null;
    private String TAG="M_FragmentMap";
    private OnFragmentInteractionListener mListener;

    private Button search_btn;
    private EditText search_editText;

//    =============初始化工作====================
    private void _getViews_setListener(View view){
        search_btn=view.findViewById(R.id.search_btn);
        search_editText= view.findViewById(R.id.search_editText);
        mMapView =  view.findViewById(R.id.map);
        onButtonClickListener();
    }
    private void onButtonClickListener(){
        search_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String text = search_editText.getText().toString();
                            String pattern = "(.*);(.*)";
                            Pattern p = Pattern.compile(pattern);
                            Matcher m=p.matcher(text);
                            m.find();
                            Log.d(TAG, m.group(1));
                            double lat=Double.parseDouble(m.group(1));
                            double lon=Double.parseDouble(m.group(2));
                            LatLng latLng=new LatLng(lat,lon);
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
//    =============初始化工作 end====================

//    ================Marker=========================
    public class Marker{
        public double lat,lon;
        public String title,snippet;
        public Marker(double lat,double lon,String title,String snippet){
            this.lat=lat;
            this.lon=lon;
            this.title=title;
            this.snippet=snippet;
        }
    }
    private void _addMarkers(List<Marker> markers){
        for (Marker marker: markers ){
            _addMarker(marker);
        }
    }
    private void _addMarker(Marker marker){
        _addMarker(marker.lat,marker.lon,marker.title,marker.snippet);
    }
    private void _addMarker(double lat,double lon, String title, String snippet) {
        // todo 性能 字符串操作 + to append
        aMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(title).snippet(snippet+"lat:" + lat + "lon:" + lon));
    }
//    ================Marker end=========================

//    ====================Show Position============================
    private void _showPosition(){
        GetPosition.M_getData(GetPosition.M_todayTime(),GetPosition.M_todayTime()+GetPosition.M_dayTime,MyGlobal.m_getUserId(),getContext());
    }




//    ====================Fragment=====================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_map, container, false);
        Log.d(TAG, "onCreateView: mMapView");

        _getViews_setListener(view);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        mMapView.onDestroy();
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
//        ====================Fragment  end=====================
}
