package com.sandeepmaucps.rgbh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
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
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class CheckRooms extends Fragment {

    Spinner room, floor, bed;
    Button check, allot, upload, edit_feesreceipt;
    ImageView image;
    EditText edit_fees;
    private final  int IMG_REQUEST=1;
    private Bitmap bitmap;
    String send_floor, send_bed, send_room, emailid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.checkroom, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
         getActivity().setTitle("Apply For Room");
        room = view.findViewById(R.id.room);
        floor = view.findViewById(R.id.floor);
        bed = view.findViewById(R.id.bed);

        floor.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, Details.floor));
        bed.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, Details.bed));
        floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                send_floor = Details.floor[position];
                if (position == 0)
                    room.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Details.ground));
                else if (position == 1)
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
                    send_room = Details.ground[position];


                else if (send_floor.equals("first"))
                    send_room = Details.first[position];

                else
                    send_room = Details.second[position];

                // Showing selected spinner item
                //Toast.makeText(getContext(), "Selected branch : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                send_bed = Details.bed[position];

                // Showing selected spinner item
                //Toast.makeText(getContext(), "Selected branch : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        upload = view.findViewById(R.id.upload);
        edit_fees = view.findViewById(R.id.edit_fees);
        edit_feesreceipt = view.findViewById(R.id.edit_feesreceipt);
        image = view.findViewById(R.id.image);
        SharedPreferences sp = getContext().getSharedPreferences("spfile", 0);
        emailid = sp.getString("emailid", null);
        final String mobile=sp.getString("mobile",null);


        edit_feesreceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             if(edit_fees.getText().toString().trim().equals(""))
             {
                 edit_fees.setError("Fill it");
                 edit_fees.requestFocus();
             }



              else  if(Integer.parseInt(edit_fees.getText().toString()) > 40000 )
                {
                    edit_fees.setError("Fees cant be more than 40000");
                    edit_fees.requestFocus();
                }
                else {


                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Sending Details..."); // Setting Message
                    progressDialog.setTitle("Please Wait !"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(true);
                    String url = "http://lostboyjourney.000webhostapp.com/rgbh/applyroomrgbh.php";


                    if (mobile.equals("")) {
                        progressDialog.dismiss();
                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("First Complete your profile \n in edit profile section");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                Intent i = new Intent(getContext(), Profile.class);
                                startActivity(i);
                            }
                        });
                        alertDialog.show();
                    } else {
                        StringRequest stringRequest = new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                            @Override ///1 for post method
                            public void onResponse(String response) {
                                //  Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                if(response.equals("room is not available"))
                                {
                                    progressDialog.dismiss();
                                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                                    alertDialog.setTitle(response);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    });
                                    alertDialog.show();
                                }
                                else {

                                    progressDialog.dismiss();
                                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                                    alertDialog.setTitle(response);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                            Intent i = new Intent(getContext(), Profile.class);
                                            startActivity(i);
                                        }
                                    });
                                    alertDialog.show();
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
                                map.put("floor", send_floor);
                                map.put("room", send_room);
                                map.put("bed", send_bed);
                                map.put("emailid", emailid);
                                map.put("image", bitmaptostring(bitmap));
                                map.put("fees", edit_fees.getText().toString());
                                return map;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(stringRequest);

                    }
                }
            }
        });

    }

    public String bitmaptostring(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] imgbyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) ;

        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
                upload.setVisibility(View.VISIBLE);
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }
}