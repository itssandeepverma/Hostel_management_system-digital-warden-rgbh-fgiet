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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Complain extends Fragment{

    EditText description;
    Spinner regarding;
    Button complain;
    String complaintype;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.complain, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Complains");
        regarding=view.findViewById(R.id.regarding);
        description=view.findViewById(R.id.desc);
        complain=view.findViewById(R.id.complain);
        regarding.setAdapter(new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item, Details.complaintype));

        SharedPreferences sp=getContext().getSharedPreferences("spfile",0);

        final String room=sp.getString("room",null);
        final String name=sp.getString("name",null);
        final String bed=sp.getString("bed",null);

        regarding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
             complaintype = Details.complaintype[position];

                // Showing selected spinner item
                //Toast.makeText(getContext(), "Selected branch : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Sending Details..."); // Setting Message
                progressDialog.setTitle("Please Wait !"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                String url = "http://lostboyjourney.000webhostapp.com/rgbh/complainrgbh.php";

                StringRequest stringRequest = new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                    @Override ///1 for post method
                    public void onResponse(String response) {

                        //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle(response);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i=new Intent(getContext(),Profile.class);
                                startActivity(i);
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("complaintype", complaintype);
                        map.put("complaindesc", description.getText().toString());
                        map.put("name", name);
                        map.put("room", room);
                        map.put("bed", bed);
                        return map;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

            }
        });

    }
}
