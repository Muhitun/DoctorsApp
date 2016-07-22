package com.example.sohan.doctor;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sohan on 6/9/2016.
 */
public class Contacts {                                        // Contacts class for notification
    private String Name,Age,Height,Weight,Symptom,Id;
    private String pName, pAge, pHeight, pWeight, symptom, doctorName;



    public Contacts(String PName, String pAge, String pHeight, String pWeight, String pSymptom, String pId){
        this.setName(PName);
        this.setAge(pAge);
        this.setHeight(pHeight);
        this.setWeight(pWeight);
        this.setSymptom(pSymptom);
        this.setId(pId);
    }

    public Contacts(String PName, String pAge, String pHeight, String pWeight, String pSymptom, String pId, String dName ){

    }


    public void setName(String nName) {
        Name = nName;
    }

    public String getName() {
        return Name;
    }

    public void setAge(String nApp) {
        Age = nApp;
    }

    public String getAge() {
        return Age;
    }

    public void setHeight(String room) {
        Height = room;
    }

    public String getHeight() {
        return Height;
    }

    public void setWeight(String room) {
        Weight = room;
    }

    public String getWeight() {
        return Weight;
    }

    public void setSymptom(String room) {
        Symptom = room;
    }

    public String getSymptom() {
        return Symptom;
    }

    public void setId(String room) {
        Id = room;
    }

    public String getId() {
        return Id;
    }



}
