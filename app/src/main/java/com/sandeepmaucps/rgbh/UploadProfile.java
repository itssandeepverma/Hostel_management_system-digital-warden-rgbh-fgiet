package com.sandeepmaucps.rgbh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sandeepmaucps.rgbh.MainActivity;
import com.sandeepmaucps.rgbh.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadProfile extends AppCompatActivity implements View.OnClickListener {

    ImageView image;
    Button choose,upload;
    private final  int IMG_REQUEST=1;
    private Bitmap bitmap;
    String emailid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile);
        choose=findViewById(R.id.choose);
        upload=findViewById(R.id.upload);
        image=findViewById(R.id.image);
        choose.setOnClickListener(this);
        upload.setOnClickListener(this);
        SharedPreferences sp=getSharedPreferences("spfile",0);
        emailid=sp.getString("emailid",null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.choose: {
                selectimage();
                break;
            }

            case R.id.upload: {
                uploadimage();
                break;
            }
        }
    }



    public void selectimage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);

    }

    private void uploadimage() {


        final ProgressDialog progressDialog = new ProgressDialog(UploadProfile.this);
        progressDialog.setMessage("Saving..."); // Setting Message
        progressDialog.setTitle("Please Wait !"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(true);
        String url = "http://lostboyjourney.000webhostapp.com/rgbh/uploadimage.php";

        StringRequest stringRequest=new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
            @Override ///1 for post method
            public void onResponse(String response) {

                progressDialog.dismiss();
                final AlertDialog alertDialog = new AlertDialog.Builder(UploadProfile.this).create();
                alertDialog.setTitle(response);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        finish();
                    }
                });
                alertDialog.show();
                             //to clear the form after data send


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map=new HashMap<>();
                map.put("emailid",emailid);
                map.put("image",bitmaptostring(bitmap));
                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(UploadProfile.this);
        requestQueue.add(stringRequest);


    }

    private String bitmaptostring(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        byte[] imgbyte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte,Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK&& data!=null);

        {
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
                upload.setVisibility(View.VISIBLE);
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }
}

