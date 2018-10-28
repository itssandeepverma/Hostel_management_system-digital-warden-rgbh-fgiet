package com.sandeepmaucps.rgbh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

TextView name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_profile);



    }

    public void getname() {
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("spfile", 0);
        email.setText(sp.getString("emailid", null));
        name.setText(sp.getString("name", null));
    }
}
