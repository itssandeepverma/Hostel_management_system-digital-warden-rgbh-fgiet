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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends Fragment{

    EditText currentpassword,newpassword,confirmpassword;
    Button save;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.changepassword, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Change Password");
        currentpassword=view.findViewById(R.id.currentpassword);
        newpassword=view.findViewById(R.id.newpassword);
        confirmpassword=view.findViewById(R.id.confirmpassword);
        save=view.findViewById(R.id.save);

        SharedPreferences sp=getContext().getSharedPreferences("spfile",0);
       final String  emailid=sp.getString("emailid",null);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (newpassword.getText().toString().length() < 6) {
                    newpassword.setError(" Length Must be at least 6 ");
                    newpassword.requestFocus();
                } else {

                    if (confirmpassword.getText().toString().equals(newpassword.getText().toString())) {

                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Changing Password"); // Setting Message
                        progressDialog.setTitle("Please Wait !"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);

                        String url = "http://lostboyjourney.000webhostapp.com/rgbh/changepasswordrgbh.php";
                        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                                alertDialog.setTitle(response);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                        Intent i=new Intent(getContext(),Profile.class);
                                        startActivity(i);
                                    }
                                });
                                alertDialog.show();


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
                                map.put("password", currentpassword.getText().toString());
                                map.put("newpassword", newpassword.getText().toString());
                                map.put("emailid", emailid);
                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(stringRequest);
                    } else {
                        //  Toast.makeText(getContext(), "password do not match", Toast.LENGTH_SHORT).show();
                        confirmpassword.setError("Password Mismatch");
                        newpassword.setError("Password Mismatch");
                        confirmpassword.requestFocus();
                        newpassword.requestFocus();
                    }
                }
                }


        });


    }
}
