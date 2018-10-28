package com.sandeepmaucps.rgbh;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

/*   TextView name,email;*/
    Fragment fragment = null;
    TextView email,name;
    ImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    // for setting name to navigation drawer email and name
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
          View header = navigationView.getHeaderView(0);
        email=(TextView)header.findViewById(R.id.email);
        name=(TextView)header.findViewById(R.id.name);
        pic=(ImageView)header.findViewById(R.id.userimageview);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sp=getSharedPreferences("spfile",0);
        final String emailid=sp.getString("emailid",null);
        email.setText(emailid);


        Picasso.get().load("http://lostboyjourney.000w" +
                "ebhostapp.com/rgbh/uploads/"+emailid+ ".jpg").memoryPolicy(MemoryPolicy.NO_CACHE).
                into(pic);

        String nam=sp.getString("name",null);
        name.setText(nam);
        //Toast.makeText(this, nam, Toast.LENGTH_SHORT).show();

        fragment = new Myprofile();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Shared shared =new Shared(getApplicationContext());
        shared.firsttime();
    }

    @Override
    public void onBackPressed() {

        finish();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myprofile) {
            // Handle the camera action

            fragment = new Myprofile();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        }
            else if (id == R.id.notices) {
            fragment = new Notices();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        else if (id == R.id.editprofile) {
            fragment = new UpdateProfile();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        else if (id == R.id.hostel_details) {
            fragment = new CheckRooms();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }


        else if (id == R.id.searchrooms) {
            fragment = new SearchRooms();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        } else if (id == R.id.logout) {
            Shared shared =new Shared(getApplicationContext());
            shared.setfalse();
            Intent intent=new Intent(Profile.this,Login.class);
            startActivity(intent);
            SharedPreferences sp=getSharedPreferences("spfile",0);
            sp.edit().clear().commit();
            finish();

        } else if (id == R.id.cpassword) {
            fragment = new ChangePassword();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
           /* SharedPreferences sp=getSharedPreferences("spfile",0);
            sp.edit().clear().commit();*/

        } else if (id == R.id.contacts) {
            fragment = new Contacts();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        } else if (id == R.id.share) {

        } else if (id == R.id.complain) {

            fragment = new Complain();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        }

        SharedPreferences sp=getSharedPreferences("spfile",0);
        String nam=sp.getString("name",null);
        name.setText(nam);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
