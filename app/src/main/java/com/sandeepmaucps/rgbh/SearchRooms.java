package com.sandeepmaucps.rgbh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
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

public class SearchRooms extends Fragment {

    Spinner room,floor;
    Button check;
    String send_floor,send_bed,send_room,emailid;
    ListView lv;
    String[] name,year,bedno,branch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.searchrooms, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Search Rooms");
        room =view.findViewById(R.id.room);
        floor =view.findViewById(R.id.floor);
        check=view.findViewById(R.id.check);
        lv=view.findViewById(R.id.lv);


        floor.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, Details.floor));
        floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                send_floor=Details.floor[position];
                if(position==0)
                    room.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Details.ground));
                else if(position==1)
                    room.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Details.first));
                else
                    room.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Details.second));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                if (send_floor.equals("ground"))
                    send_room=Details.ground[position];

                else  if (send_floor.equals("first"))
                    send_room=Details.first[position];
                else
                    send_room=Details.second[position];

                // Showing selected spinner item
                //Toast.makeText(getContext(), "Selected branch : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String url = "http://lostboyjourney.000webhostapp.com/rgbh/searchroomsrgbh.php";

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Searching..."); // Setting Message
                progressDialog.setTitle("Please Wait !"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                StringRequest stringRequest = new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                    @Override ///1 for post method
                    public void onResponse(String response) {
                         ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
                           int i;
                        try {
                            JSONObject jo=new JSONObject(response);
                            JSONArray ja= jo.getJSONArray("data");

                            for( i=0;i<ja.length();i++) {
                                JSONObject job = ja.getJSONObject(i);
                               String sname=job.getString("name");
                                String  sbed=job.getString("bed");
                                String syear=job.getString("year");
                                String sbranch=job.getString("branch");
                                String srollno=job.getString("rollno");


                                HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("namekey",  "Name     : " +sname);
                                hashMap.put("branchkey","Branch   : " +sbranch);
                                hashMap.put("yearkey",  "Year     : " +syear);
                                hashMap.put("bedkey",   "Bed      : " +sbed);
                                hashMap.put("rollkey",  "Roll no. : " +srollno);

                                arrayList.add(hashMap);
                             // Toast.makeText(getContext(), sname+sbed, Toast.LENGTH_SHORT).show();//to set content in lv


                            }
                            if(i==0)
                            {
                                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                                alertDialog.setTitle("No one is in this room");
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            }

                            String[] from={"namekey","bedkey","branchkey","yearkey","rollkey"};
                            int[] to={R.id.lv_name,R.id.lv_bed,R.id.lv_branch,R.id.lv_year,R.id.lv_rollno}; //taken always in integer string coz id is always in integer form
                            SimpleAdapter sa=new SimpleAdapter(getContext(),arrayList,R.layout.listview,from,to);
                            lv.setAdapter(sa);
                            progressDialog.dismiss();


                         //   Toast.makeText(getContext(), "hjvjhb", Toast.LENGTH_SHORT).show();//to set content in lv
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("room", send_room);
                        return map;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

            }
        });




    }
}
