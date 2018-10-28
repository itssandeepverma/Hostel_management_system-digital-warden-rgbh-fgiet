package com.sandeepmaucps.rgbh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.UiAutomation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.sandeepmaucps.rgbh.Details.y;

public class UpdateProfile extends Fragment {

   Button save,uploadpropic;
   ImageView image;
   EditText edit_name,edit_address,edit_mobile,edit_rollno;
   Spinner year,branch;

    String item,branch1,year1,emailid;
    int yearpos,branchpos;
    int a=0;


    @Nullable
    @Override
    public  View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

      return   inflater.inflate(R.layout.updateprofile, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the tit
        // le for your toolbar here for different fragments different titles
        getActivity().setTitle("Update Profile");


        SharedPreferences sp=getContext().getSharedPreferences("spfile",0);
        emailid=sp.getString("emailid",null);

        edit_address=view.findViewById(R.id.edit_address);
        edit_mobile=view.findViewById(R.id.edit_mobile);
        edit_name =view.findViewById(R.id.edit_name);
        edit_rollno=view.findViewById(R.id.edit_rollno);
        save=view.findViewById(R.id.update);
        branch=view.findViewById(R.id.edit_branch);
        year=view.findViewById(R.id.edit_year);
        uploadpropic=view.findViewById(R.id.propic);






        uploadpropic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),UploadProfile.class);
                startActivity(intent);
            }
        });


        edit_mobile.setText(sp.getString("mobile",null));
        edit_address.setText(sp.getString("address",null));
        edit_rollno.setText(sp.getString("rollno",null));
        edit_name.setText(sp.getString("name",null));
        String sp_year=sp.getString("year",null);
        String sp_branch=sp.getString("branch",null);


             yearpos=getyearpos(sp_year);
             branchpos=getbranchpos(sp_branch);

        year.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, Details.y));
        branch.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, Details.b));

        year.setSelection(yearpos);
        branch.setSelection(branchpos);// function last me maine khud bnaya hai
        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
              branch1 = Details.b[position];

              // Showing selected spinner item
                //Toast.makeText(getContext(), "Selected branch : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                 year1= Details.y[position];

                // Showing selected spinner item
                //Toast.makeText(getContext(), "Selected branch : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          a=0;
               if(edit_address.getText().toString().trim().equals(""))
               {
                   edit_address.setError("FILL IT");
                   edit_address.requestFocus();
                    a=1;
               }

                if(edit_name.getText().toString().trim().equals(""))
                {
                    edit_name.setError("FILL IT");
                    edit_name.requestFocus();
                    a=1;
                }

                if(edit_mobile.getText().toString().trim().equals(""))
                {
                    edit_mobile.setError("FILL IT");
                    edit_mobile.requestFocus();
                     a=1;
                }

                if(edit_mobile.getText().toString().trim().length()!=10)
                {
                    edit_mobile.setError("Enter Correct Mobile No");
                    edit_mobile.requestFocus();
                    a=1;
                }
                if(edit_rollno.getText().toString().trim().length()!=10)
                {
                    edit_rollno.setError("Enter Correct Roll no.");
                    edit_rollno.requestFocus();
                     a=1;
                }

                if(a==0) {


                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Saving..."); // Setting Message
                    progressDialog.setTitle("Please Wait !"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    String url = "http://lostboyjourney.000webhostapp.com/rgbh/editprofilergbh.php";

                    StringRequest stringRequest = new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                        @Override ///1 for post method
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
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> map = new HashMap<>();
                            map.put("name", edit_name.getText().toString());
                            map.put("mobile", edit_mobile.getText().toString());
                            map.put("address", edit_address.getText().toString());
                            map.put("rollno", edit_rollno.getText().toString());
                            map.put("branch", branch1);
                            map.put("year", year1);
                            map.put("emailid", emailid);
                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);

                }


            }
        });

    }



    private int getyearpos(String sp_year) {
        if(sp_year.equals("1st"))
            return 0;
        else  if(sp_year.equals("2nd"))
            return 1;
        else
        if(sp_year.equals("3rd"))
            return 2;
        else
            return 3;
    }

    private int getbranchpos(String sp_branch) {

        if(sp_branch.equals("ae"))
            return 2;
       else if(sp_branch.equals("me"))
            return 4;
        else if(sp_branch.equals("cse"))
            return 0;
        else if(sp_branch.equals("mca"))
            return 3;
        else
            return 1;

    }



}
