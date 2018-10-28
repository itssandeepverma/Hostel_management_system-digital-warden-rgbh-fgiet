package com.sandeepmaucps.rgbh;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import com.sandeepmaucps.rgbh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Forgetpassword extends AppCompatActivity {



    private EditText emailid,otp;
    private String verificationId;
    Button next,done;
    String mobilenumber;
    TextView tv;
    private FirebaseAuth mAuth;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        mAuth = FirebaseAuth.getInstance();

        emailid=findViewById(R.id.forget_email);
        otp=findViewById(R.id.otp);
        next=findViewById(R.id.next);
        done=findViewById(R.id.done);
        tv=findViewById(R.id.tv);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final ProgressDialog progressDialog = new ProgressDialog(Forgetpassword.this);
                progressDialog.setMessage("Sending otp to registered no."); // Setting Message
                progressDialog.setTitle("Please Wait !"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(true);
                String url="http://lostboyjourney.000webhostapp.com/rgbh/myprofilergbh.php";  // fetching data
                StringRequest sr= new StringRequest(1, url, new Response.Listener<String>() {   //Response listener interface
                    @Override
                    public void onResponse(String response) { //contain the complete json data in onresponse

                        try {

                            JSONObject jo=new JSONObject(response);
                            JSONArray ja= jo.getJSONArray("data");


                              int i=0;

                            for( i=0;i<ja.length();i++)
                            {
                                int k=0;
                            }

                           // Toast.makeText(Forgetpassword.this, i, Toast.LENGTH_SHORT).show();
                            if(i==0) {
                                progressDialog.dismiss();
                                final AlertDialog alertDialog = new AlertDialog.Builder(Forgetpassword.this).create();
                                alertDialog.setTitle("Enter Valid Email id.");
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            }
                            else {
                                JSONObject  job=ja.getJSONObject(0);
                                mobilenumber = job.getString("mobile");
                                otp.setVisibility(View.VISIBLE);
                                done.setVisibility(View.VISIBLE);
                                next.setEnabled(false);
                                tv.setText("Enter the otp send at " + mobilenumber +  "\nPl" +
                                        "ease wait while it will automatically detect the otp.");
                                tv.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                sendVerificationCode(mobilenumber);
                            }
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
                        map.put("emailid",emailid.getText().toString() );
                        return map;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(sr);
            }
        });




        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(Forgetpassword.this);
                progressDialog.setMessage("Verifying Otp"); // Setting Message
                progressDialog.setTitle("Please Wait !"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                String code = otp.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    otp.setError("Enter code...");
                    otp.requestFocus();
                    progressDialog.dismiss();
                    return;
                }
                verifyCode(code);
            }
        });
    }
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(getApplicationContext(), ChangeForgetPassword.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                             intent.putExtra("emailid",emailid.getText().toString());
                            startActivity(intent);

                        } else {
                          //  Toast.makeText(Forgetpassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        /* progressBar.setVisibility(View.VISIBLE);*/
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Forgetpassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

}
