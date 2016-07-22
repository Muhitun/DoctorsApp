package com.example.sohan.doctor;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sohan on 6/25/2016.
 */
public class logout extends Fragment {
    Context c;
    View myView;

    public logout(Context ct){
        c = ct;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.logout, container, false);
        Intent i = new Intent(c, Welcome.class);
        startActivity(i);
        return null;
    }
}
