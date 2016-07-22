package com.example.sohan.doctor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sohan on 6/25/2016.
 */
public class switch_class extends Fragment {

    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.switch_notification, container, false);
        return myView;
    }
}
