package com.sandeepmaucps.rgbh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sandeepmaucps.rgbh.Register;

public class Shared {

    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String b="b";
    String filename="spfile";//file name
    int mode=0;//private mode
    Context context;//means that spfile will be created in context

    public Shared(Context context) {
        this.context = context;//here address or context of file from where this class is called here it is for main activity
        sp=context.getSharedPreferences("spfile",0);//coz it needed a references
        //to edit the sp file
        ed=sp.edit();
    }

    public void secondtime(){
        ed.putBoolean(b,true);//editor always take string
        ed.commit();

    }

    public void setfalse(){
        ed.putBoolean(b,false);
        ed.commit();
    }

    public void firsttime(){
        if(!this.login()){
            Intent i=new Intent(context,Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //to make cache clear
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
        }

    }

    private boolean login() {
        return sp.getBoolean(b,false);
    }



}
