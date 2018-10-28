package com.sandeepmaucps.rgbh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChangeForgetPassword extends AppCompatActivity {


    EditText newpassword,forget_confirmpassword;
    Button forget_changepassword;
    String emailid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_forget_password);

        forget_changepassword=findViewById(R.id.forget_changepassword);
        forget_confirmpassword=findViewById(R.id.forget_confirmpassword);
        newpassword=findViewById(R.id.forget_newpassword);

        forget_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                 //Null Checking
                    emailid= intent.getStringExtra("emailid");



                if (newpassword.getText().toString().length() < 6) {
                    newpassword.setError(" Length Must be at least 6 ");
                    newpassword.requestFocus();
                } else {

                    if (forget_confirmpassword.getText().toString().equals(newpassword.getText().toString())) {

                        final ProgressDialog progressDialog = new ProgressDialog(ChangeForgetPassword.this);
                        progressDialog.setMessage("Saving..."); // Setting Message
                        progressDialog.setTitle("Please Wait !"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        String url = "http://lostboyjourney.000webhostapp.com/rgbh/forgetpasswordrgbh.php";

                        StringRequest stringRequest = new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                            @Override ///1 for post method
                            public void onResponse(String response) {

                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();
                                final AlertDialog alertDialog = new AlertDialog.Builder(ChangeForgetPassword.this).create();
                                alertDialog.setTitle("Password Successfully Changed");
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                        Intent i = new Intent(ChangeForgetPassword.this, Login.class);
                                        startActivity(i);
                                        finish();
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
                                map.put("password", forget_confirmpassword.getText().toString());
                                map.put("emailid", emailid);
                                return map;


                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);

                    } else {

                        Toast.makeText(getApplicationContext(), "password do not match", Toast.LENGTH_SHORT).show();
                        forget_confirmpassword.setError("Password Mismatch");
                        forget_confirmpassword.requestFocus();

                    }
                }
            }
        });
    }
}
