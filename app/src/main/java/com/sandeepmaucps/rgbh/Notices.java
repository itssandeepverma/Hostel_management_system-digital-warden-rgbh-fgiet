package com.sandeepmaucps.rgbh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notices extends Fragment{

    ListView lv;
    List<HeroNOtice> heroList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.notices, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Notices");
               lv=view.findViewById(R.id.lv);

        String url="http://lostboyjourney.000webhostapp.com/rgbh/notices.php";  // fetching data
        StringRequest sr= new StringRequest(1, url, new Response.Listener<String>() {   //Response listener interface
            @Override
            public void onResponse(String response) { //contain the complete json data in onresponse
                heroList = new ArrayList<>();

                try {
                    int i=1;
                    JSONObject jo=new JSONObject(response);
                    JSONArray ja= jo.getJSONArray("data");

                    for( i=0;i<ja.length();i++) {

                        JSONObject job = ja.getJSONObject(i);
                        String sname = job.getString("name");
                        String sdesc = job.getString("description");

                        // adding some values to our list
                        heroList.add(new HeroNOtice(sname, sdesc));


                    }
                   if(i==0)
                    Toast.makeText(getContext()," NO NOTICES TO BE SHOWN !", Toast.LENGTH_SHORT).show();
                    NoticeListAdapter adapter = new NoticeListAdapter(getContext(), R.layout.lvnotices, heroList);
                    adapter.notifyDataSetChanged();
                    //attaching adapter to the listview
                    lv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(sr); // we need http protocol queue means fcfs  // queue makes all data request in a sequence like 1,url,


    }
}
