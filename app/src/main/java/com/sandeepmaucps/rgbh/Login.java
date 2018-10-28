package com.sandeepmaucps.rgbh;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

public class Login extends AppCompatActivity {

    EditText login_email,login_password;
    View login;
    Button forget,register;
    SharedPreferences sp;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        login_email= (EditText) findViewById(R.id.login_email);
        login_password= (EditText) findViewById(R.id.login_password);
        login= (Button) findViewById(R.id.Login);
         register=(Button)findViewById(R.id.register);
           register.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent i= new Intent(Login.this,Register.class);
                   startActivity(i);
               }
           });

        forget=(Button)findViewById(R.id.forgetpassword);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,Forgetpassword.class);
                startActivity(i);
                }
        });


        final SharedPreferences.Editor editor;
        sp = getSharedPreferences("spfile", 0);
        editor = sp.edit();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(Login.this); //do not use getappcontext
                progressDialog.setMessage("Logging in"); // Setting Message
                progressDialog.setTitle("Please Wait !"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                String url="http://lostboyjourney.000webhostapp.com/rgbh/loginrgbh.php";

                StringRequest stringRequest=new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                    @Override ///1 for post method
                    public void onResponse(String response) {

                        if(response.equals("OK"))
                        {

                            Shared shared =new Shared(getApplicationContext());
                            shared.secondtime();
                            editor.putString("emailid", login_email.getText().toString());
                            editor.commit();
                            Intent i= new Intent(Login.this,Profile.class);
                            startActivity(i);
                            finish();
                            progressDialog.dismiss();

                        }

                        else
                        {
                            Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                            login_email.setError("Invalid username");
                            login_email.requestFocus();
                            progressDialog.dismiss();
                            login_password.setError("Invalid password");
                            login_password.requestFocus();

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> map=new HashMap<>();
                        map.put("emailid",login_email.getText().toString());
                        map.put("password",login_password.getText().toString());
                        return map;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(Login.this);
                requestQueue.add(stringRequest);


            }
        });
    }
}
