package com.sandeepmaucps.rgbh;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sandeepmaucps.rgbh.Profile;
import com.sandeepmaucps.rgbh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {


    EditText reg_name, reg_email, reg_password, reg_confirm;
    Button register, gologin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_password = (EditText) findViewById(R.id.reg_password);
        reg_confirm = (EditText) findViewById(R.id.reg_confirmpassword);
        register = (Button) findViewById(R.id.register);
        gologin = (Button) findViewById(R.id.gologin);

        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!emailValidator(reg_email.getText().toString()))

                {
                    reg_email.setError("Invalid Email");
                    reg_email.requestFocus();

                }

                else if(reg_password.getText().toString().trim().length()<6)
                {

                        reg_password.setError("Password Length should be at least 6");
                        reg_password.requestFocus();

                }

                else {


                    if (reg_password.getText().toString().equals(reg_confirm.getText().toString())) {


                        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                        progressDialog.setMessage("Signing up"); // Setting Message
                        progressDialog.setTitle("Please Wait !"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        String url = "http://lostboyjourney.000webhostapp.com/rgbh/registerrgbh.php";

                        StringRequest stringRequest = new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                            @Override ///1 for post method
                            public void onResponse(String response) {

                                if (response.equals("registered successfully")) {

                                    reg_email.setText("");
                                    reg_name.setText("");
                                    reg_password.setText("");
                                    reg_confirm.setText("");
                                    progressDialog.dismiss();
                                    final AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
                                    alertDialog.setTitle(response);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                            Intent i = new Intent(Register.this,Login.class);
                                            startActivity(i);
                                        }
                                    });
                                    alertDialog.show();


                                } else {
                                    Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
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
                                map.put("name", reg_name.getText().toString());
                                map.put("emailid", reg_email.getText().toString());
                                map.put("password", reg_password.getText().toString());
                                return map;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                        requestQueue.add(stringRequest);
                    } else {
                        Toast.makeText(Register.this, "Password do not match", Toast.LENGTH_SHORT).show();
                        reg_password.setError("Password Mismatch");
                        reg_password.requestFocus();
                        reg_confirm.setError("Password Mismatch");
                        reg_confirm.requestFocus();
                    }
                }
            }
        });
    }
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}