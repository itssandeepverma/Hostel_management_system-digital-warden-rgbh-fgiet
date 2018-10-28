package com.sandeepmaucps.rgbh;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandeepmaucps.rgbh.R;

public class Splash extends AppCompatActivity {
    ImageView iv;
    TextView Share,Here,fgiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        iv=findViewById(R.id.icon);
        Share=findViewById(R.id.shre);
        Here=findViewById(R.id.here);
        fgiet=findViewById(R.id.fgiet);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        iv.startAnimation(animation);
        Share.startAnimation(animation);
        Here.startAnimation(animation);
        fgiet.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent i = new Intent(Splash.this,Profile.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
