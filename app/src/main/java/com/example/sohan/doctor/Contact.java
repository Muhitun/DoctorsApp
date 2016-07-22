package com.example.sohan.doctor;

        import android.content.Context;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Sohan on 6/9/2016.
 */
public class Contact {                                        // Contacts class for history
    private String Name,Age,Height,Weight,Symptom,Treatment,dName;

    public Contact(String PName, String pAge, String pHeight, String pWeight, String pSymptom, String Treatment, String dName){
        this.setName(PName);
        this.setAge(pAge);
        this.setHeight(pHeight);
        this.setWeight(pWeight);
        this.setSymptom(pSymptom);
        this.setdoctorName(dName);
        this.setTreatment(Treatment);
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

    public void setdoctorName(String room) {
        dName = room;
    }

    public String getdoctorName() {
        return dName;
    }

    public void setTreatment(String room) {
        Treatment = room;
    }

    public String getTreatment() {
        return Treatment;
    }

}
