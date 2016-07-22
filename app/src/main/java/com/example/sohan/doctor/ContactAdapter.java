package com.example.sohan.doctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sohan on 6/9/2016.
 */
public class ContactAdapter extends ArrayAdapter<Contacts> {
   // notification nt = new notification();
    List<Contacts> list = new ArrayList<Contacts>();
    View row;
    ContactHolder contactHolder;
    LayoutInflater inflater;
    View v;
    private Activity activity;

    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }



    public void add(List<Contacts> updatedList) {
        list.clear();
        list.addAll(updatedList);
        notifyDataSetChanged();
    }


    @Override
    public Contacts getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        row = convertView;

        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.view_symptom_layout,parent,false);
            contactHolder = new ContactHolder();
            contactHolder.Name =(TextView) row.findViewById(R.id.textView2);
            contactHolder.Age =(TextView) row.findViewById(R.id.textView3);
            contactHolder.Height =(TextView) row.findViewById(R.id.textView4);
            contactHolder.Weight =(TextView) row.findViewById(R.id.textView5);
            contactHolder.Symptom =(TextView) row.findViewById(R.id.textView6);
            contactHolder.Id =(TextView) row.findViewById(R.id.textView9);
            contactHolder.button = (Button) row.findViewById(R.id.button3);

            row.setTag(contactHolder);
        }
        else{

            contactHolder = (ContactHolder)row.getTag();
        }
        contactHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               // this is the onClick method for the custom button
                String symptom,pId,name,age,height,weight;
                symptom = list.get(position).getSymptom();
                pId = list.get(position).getId();
                name = list.get(position).getName();
                age = list.get(position).getAge();
                height = list.get(position).getHeight();
                weight = list.get(position).getWeight();
                showCustomDialog(symptom, pId, name, age, height, weight);

                //Toast.makeText(getContext().getApplicationContext(), name+" is served ", Toast.LENGTH_LONG).show();
                //Toast.makeText(getContext().getApplicationContext(), "Position of "+name+" is "+position, Toast.LENGTH_LONG).show();
                //notifyDataSetChanged();
            }
        });



        Contacts contacts = (Contacts)this.getItem(position);
        contactHolder.Name.setText("Name: "+contacts.getName());
        contactHolder.Age.setText("Age: "+contacts.getAge());
        contactHolder.Height.setText("Height: "+contacts.getHeight());
        contactHolder.Weight.setText("Weight: "+contacts.getWeight());
        contactHolder.Symptom.setText("Symptoms: " + contacts.getSymptom());
        contactHolder.Id.setText("Id: " + contacts.getId());

        return row;

    }

    static class ContactHolder{
        TextView Name;
        TextView Age;
        TextView Height;
        TextView Weight;
        TextView Symptom;
        TextView Id;
        Button button;
    }



    public void showCustomDialog(final String symptom, final String pId, final String pName, final String pAge, final String pHeight, final String pWeight) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inf.inflate(R.layout.custom_dialogbox, null);
        builder.setView(view);
        builder.setTitle("          Treatment Panel");
        final TextView Symptoms = (TextView) view.findViewById(R.id.editText2); // symptoms
        final EditText Treatment = (EditText) view.findViewById(R.id.editText3);

        Symptoms.setText("Symptom : "+symptom);

        builder.setPositiveButton("Send Treatment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //needed doctors name, patient id, treatment edit text value
                String doctorName, patientId, treatment, doctorId;
                Welcome wc = new Welcome();
                doctorName = wc.getDoctorName();
                doctorId = wc.getID();
                // Toast.makeText(getContext().getApplicationContext(), "Dr.Name "+ doctorName, Toast.LENGTH_LONG).show();
                patientId = pId;
                treatment = Treatment.getText().toString();
                signIn sn = new signIn();
                sn.execute(doctorName, patientId, treatment);
                history hs = new history();
                hs.execute(pName, pAge, pHeight, pWeight, pId, symptom, treatment, doctorName, doctorId);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Click listner
            }
        });
        builder.show();
    }
    class signIn extends AsyncTask<String, Void, String> {             // send data to server

        String myUrl;



        protected void onPreExecute() {
            myUrl ="http://bdpricelist.com/patient/sendTreatment.php";
        }


        protected String doInBackground(String... args) {
            String dName, pId, Treatment;
            String result=null;
            dName = args[0];
            pId = args[1];
            Treatment = args[2];
            try{
                URL url = new URL(myUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_to_send = URLEncoder.encode("dName", "UTF-8")+"="+URLEncoder.encode(dName,"UTF-8")+"&"+
                        URLEncoder.encode("pId", "UTF-8")+"="+URLEncoder.encode(pId,"UTF-8")+"&"+
                        URLEncoder.encode("Treatment", "UTF-8")+"="+URLEncoder.encode(Treatment,"UTF-8");
                bufferedWriter.write(data_to_send);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                // int intch;

                while ((line = reader.readLine()) != null)   //line = reader.readLine() !=null
                {
                    sb.append(line + "\n");
                    break;
                }
                is.close();
                result = sb.toString();
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException f){
                f.printStackTrace();
            }
            return result;
        }


        protected void onPostExecute(String result) {
            String s = result.trim();
            if (s.equalsIgnoreCase("success")) {
                Toast.makeText(getContext().getApplicationContext(), "Treatment send successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext().getApplicationContext(), "Sorry treatment not sent", Toast.LENGTH_LONG).show();
            }

        }
    }

    class history extends AsyncTask<String, Void, String> {             // send data to server

        String myUrl;



        protected void onPreExecute() {
            myUrl ="http://bdpricelist.com/patient/sendToHistory.php";
        }


        protected String doInBackground(String... args) {
            String pName, pAge, pHeight, pWeight, pId, symptom, treatment, doctorName, doctorId;
            String result=null;
            pName = args[0];
            pAge = args[1];
            pHeight = args[2];
            pWeight = args[3];
            pId = args[4];
            symptom = args[5];
            treatment = args[6];
            doctorName = args[7];
            doctorId = args[8];
            try{
                URL url = new URL(myUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_to_send = URLEncoder.encode("pName", "UTF-8")+"="+URLEncoder.encode(pName,"UTF-8")+"&"+
                        URLEncoder.encode("pAge", "UTF-8")+"="+URLEncoder.encode(pAge,"UTF-8")+"&"+
                        URLEncoder.encode("pHeight", "UTF-8")+"="+URLEncoder.encode(pHeight,"UTF-8")+"&"+
                        URLEncoder.encode("pWeight", "UTF-8")+"="+URLEncoder.encode(pWeight,"UTF-8")+"&"+
                        URLEncoder.encode("pId", "UTF-8")+"="+URLEncoder.encode(pId,"UTF-8")+"&"+
                        URLEncoder.encode("symptom", "UTF-8")+"="+URLEncoder.encode(symptom,"UTF-8")+"&"+
                        URLEncoder.encode("treatment", "UTF-8")+"="+URLEncoder.encode(treatment,"UTF-8")+"&"+
                        URLEncoder.encode("doctorName", "UTF-8")+"="+URLEncoder.encode(doctorName,"UTF-8")+"&"+
                        URLEncoder.encode("doctorId", "UTF-8")+"="+URLEncoder.encode(doctorId,"UTF-8");
                bufferedWriter.write(data_to_send);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                // int intch;

                while ((line = reader.readLine()) != null)   //line = reader.readLine() !=null
                {
                    sb.append(line + "\n");
                    break;
                }
                is.close();
                result = sb.toString();
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException f){
                f.printStackTrace();
            }
            return result;
        }


        protected void onPostExecute(String result) {
            String s = result.trim();
            if (s.equalsIgnoreCase("success")) {
                Toast.makeText(getContext().getApplicationContext(), "Treatment send successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext().getApplicationContext(), "Sorry treatment not sent", Toast.LENGTH_LONG).show();
            }

        }
    }

}
