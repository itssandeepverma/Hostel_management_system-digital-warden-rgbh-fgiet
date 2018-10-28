package com.sandeepmaucps.rgbh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Belal on 9/14/2017.
 */

//we need to extend the ArrayAdapter class as we are building an adapter
public class NoticeListAdapter extends ArrayAdapter<HeroNOtice> {

    //the list values in the List of type hero
    List<HeroNOtice> heroList;
    TextView name,desc;
    //activity context
    Context context;
    //the layout resource file for the list items
    int resource;
    ZoomageView image;

    //constructor initializing the values
    public NoticeListAdapter(Context context, int resource, List<HeroNOtice> heroList) {
        super(context, resource, heroList);
        this.context = context;
        this.resource = R.layout.lvnotices;
        this.heroList = heroList;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(R.layout.lvnotices, null, false);

        //getting the view elements of the list from the view

        desc = view.findViewById(R.id.desc);
        HeroNOtice hero = heroList.get(position);

        desc.setText("Description  : "+ "\n" +hero.getDesc());


        String name=hero.getName();
       // final String name = (String) ((TextView) view.findViewById(R.id.room_email)).getText();

        String url="http://lostboyjourney.000webhostapp.com/rgbh/notices/" + name+".jpg";
        Glide.with(view.getContext())
                .load(url)
                .into((ImageView)view.findViewById(R.id.image));

        return view;
    }
}