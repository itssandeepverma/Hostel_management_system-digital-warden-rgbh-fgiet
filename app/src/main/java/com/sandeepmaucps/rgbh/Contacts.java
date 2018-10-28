package com.sandeepmaucps.rgbh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Contacts extends Fragment{


    Button mob_warden,mob_gatekeeper,mob_ambulancedriver,mob_electrician;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.contacts, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Contacts");

        mob_ambulancedriver=view.findViewById(R.id.mob_ambulance);
        mob_warden=view.findViewById(R.id.mob_warden);
        mob_electrician=view.findViewById(R.id.mob_electrician);
        mob_gatekeeper=view.findViewById(R.id.mob_gatekeeper);

        mob_electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "121543158"
                        , null)));
            }
        });
        mob_warden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "541854"
                        , null)));
            }
        });
        mob_gatekeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "1131"
                        , null)));
            }
        });
        mob_ambulancedriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "85731"
                        , null)));
            }
        });
    }
}
