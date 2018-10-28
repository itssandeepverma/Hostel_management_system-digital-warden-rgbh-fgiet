package com.sandeepmaucps.rgbh;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

public class Myprofile extends Fragment {

    TextView user_name,failed,user_fees,user_status,user_mobile,user_address,user_floor,user_bed,user_room,user_rollno,user_year,user_emailid,user_branch;
    String emailid;
    ImageView user_profile;
     Button feesreceipt;
     ProgressBar pb;
    SharedPreferences.Editor editor;
    private DownloadManager downloadManager;
    private long Image_DownloadId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.myprofile, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles


        user_name=view.findViewById(R.id.user_name);
        user_address=view.findViewById(R.id.user_address);
        user_mobile=view.findViewById(R.id.user_mobile);
        user_rollno=view.findViewById(R.id.user_rollno);
        user_floor=view.findViewById(R.id.user_floor);
        user_room=view.findViewById(R.id.user_room);
        user_bed=view.findViewById(R.id.user_bed);
        user_year=view.findViewById(R.id.user_year);
        user_branch=view.findViewById(R.id.user_branch);
        user_emailid=view.findViewById(R.id.user_emaild);
        user_profile=view.findViewById(R.id.user_profile_photo);
        user_fees=view.findViewById(R.id.user_fees);
        feesreceipt=view.findViewById(R.id.feesreceipt);
        user_status=view.findViewById(R.id.user_status);
            pb=view.findViewById(R.id.progressbar);
        //shared preferneces
        failed=view.findViewById(R.id.failed);
      final  SharedPreferences sp=getContext().getSharedPreferences("spfile",0);
        emailid=sp.getString("emailid",null);
        editor = sp.edit();

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {

                pb.setVisibility(View.GONE);
                failed.setVisibility(View.VISIBLE);

            }
        }, 30000);

        String url1="http://lostboyjourney.000webhostapp.com/rgbh/uploads/" + emailid + ".jpg";
    /*    Glide.with(view.getContext())
                .load(url1)
                .into((ImageView)view.findViewById(R.id.user_profile_photo));*/
        Picasso.get().load(url1)
                .memoryPolicy(MemoryPolicy.NO_CACHE).into(user_profile);


        user_mobile.setText("Mobile : "+sp.getString("mobile",null));
        user_floor.setText("Floor : "+sp.getString("floor",null));
        user_bed.setText("Bed : "+sp.getString("bed",null));
        user_room.setText("Room : "+sp.getString("room",null));
        user_year.setText("Year : "+sp.getString("year",null));
        user_address.setText("Address : "+sp.getString("address",null));
        user_rollno.setText("Rollno : "+sp.getString("rollno",null));
        user_branch.setText("Branch :" + sp.getString("branch",null));
        user_name.setText(sp.getString("name",null));
        user_emailid.setText("Emailid : "+sp.getString("emailid",null));
        user_fees.setText("FEES PAID :" +sp.getString("fees",null));


        String url="http://lostboyjourney.000webhostapp.com/rgbh/myprofilergbh.php";  // fetching data
        StringRequest sr= new StringRequest(1, url, new Response.Listener<String>() {   //Response listener interface
            @Override
            public void onResponse(String response) { //contain the complete json data in onresponse

                try {

                    JSONObject jo=new JSONObject(response);
                    JSONArray ja= jo.getJSONArray("data");
                    JSONObject job=ja.getJSONObject(0);

                    String sname=job.getString("name");
                    String smobile=job.getString("mobile");
                    String syear=job.getString("year");
                    String sbranch=job.getString("branch");
                    String saddress=job.getString("address");
                    String srollno=job.getString("rollno");
                    String sfloor=job.getString("floor");
                    String sroom=job.getString("room");
                    String sbed=job.getString("bed");
                    String sfees=job.getString("fees");
                    String sstatus=job.getString("allot");




                    editor.putString("mobile",smobile );
                    editor.putString("name",sname );
                    editor.putString("year",syear );
                    editor.putString("address",saddress );
                    editor.putString("floor",sfloor );
                    editor.putString("bed",sbed );
                    editor.putString("branch",sbranch );
                    editor.putString("rollno",srollno);
                    editor.putString("room",sroom);
                    editor.putString("fees",sfees);
                    editor.commit();


                   // Toast.makeText(getContext(), sname, Toast.LENGTH_SHORT).show();

                    user_mobile.setText("Mobile : "+sp.getString("mobile",null));
                    user_floor.setText("Floor : "+sp.getString("floor",null));
                    user_bed.setText("Bed : "+sp.getString("bed",null));
                    user_room.setText("Room : "+sp.getString("room",null));
                    user_year.setText("Year : "+sp.getString("year",null));
                    user_address.setText("Address : "+sp.getString("address",null));
                    user_rollno.setText("Rollno : "+sp.getString("rollno",null));
                    user_branch.setText("Branch :" + sp.getString("branch",null));
                    user_name.setText(sp.getString("name",null));
                    user_emailid.setText( "Emailid : "+sp.getString("emailid",null));
                    user_fees.setText("Fees paid :"+sp.getString("fees",null));


                    String name=sp.getString("name",null);
                    getActivity().setTitle("Hello , " + name);
                    if(sstatus.equals("allot"))
                        user_status.setText("Status:Room Allocation Pending");
                    else if(sstatus.equals("remove"))
                        user_status.setText("Status:Successfully alloted");
                    else if(sstatus.equals("rejected"))
                        user_status.setText("Status : Your allocation rejected ");
                else
                    user_status.setText("Status : you haven't applied for any room");
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

            map.put("emailid",emailid );
            return map;
        }
    };

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(sr); // we need http protocol queue means fcfs  // queue makes all data request in a sequence like 1,url,




             feesreceipt.setOnClickListener(new View.OnClickListener() {
               @Override
              public void onClick(View view) {

                   Uri image_uri = Uri.parse("http://lostboyjourney.000webhostapp.com/rgbh/receipt/"+emailid+".jpg");
                   Image_DownloadId = DownloadData(image_uri, view);
                   Toast.makeText(getContext(), "Downloading started", Toast.LENGTH_SHORT).show();
                     }
                 });
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getContext().registerReceiver(downloadReceiver, filter);

    }



    private long DownloadData (Uri uri, View view) {

        long downloadReference;

        downloadManager = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("Data Download");
        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        if(view.getId() == R.id.feesreceipt)
            request.setDestinationInExternalFilesDir(getContext(), "/downloads", emailid+".png");
        //Enqueue download and save the referenceId
        downloadReference = downloadManager.enqueue(request);


        return downloadReference;
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


                Toast toast = Toast.makeText(getContext(),
                        "Image Download Complete", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();


        }
    };



}
