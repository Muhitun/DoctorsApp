package com.example.sohan.doctor;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

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
 * Created by Sohan on 6/25/2016.
 */
public class notification extends Fragment implements AdapterView.OnItemSelectedListener{

    public final static String Message = "Sohan";
    View myView;
    String selectedCity;
    Context myContext;
    String jsonResult;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String JSON_String;
    ContactAdapter contactAdapter;
    ListView listView;
    List<Contacts> newList;
    Button button;
    String send;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.notification, container, false);
        myContext = inflater.getContext();
        contactAdapter = new ContactAdapter(myContext, R.layout.view_symptom_layout);
        listView = (ListView)myView.findViewById(R.id.listView);
        listView.setAdapter(contactAdapter);
        retrieveInfo ri = new retrieveInfo();
        ri.execute();
        return myView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class retrieveInfo extends AsyncTask<Void, Void, String> {             // send data to server

        String myUrl;


        protected void onPreExecute() {
            myUrl ="http://bdpricelist.com/patient/retrievePatientInfo.php";    // change php script
        }


        protected String doInBackground(Void... args) {
            String city;
            String result = null;
            JSONArray jsonArray = null;
            try{
                URL url = new URL(myUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
               // String data_to_send = URLEncoder.encode("city", "UTF-8")+"="+URLEncoder.encode(city,"UTF-8");
                //bufferedWriter.write(data_to_send);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();


                while ((JSON_String = reader.readLine()) != null)
                {
                    sb.append(JSON_String+"\n");
                }
                reader.close();
                httpURLConnection.disconnect();
                is.close();
                return sb.toString().trim();
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException f){
                f.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String result) {

            jsonResult = result;
            parseJSON(jsonResult);
        }
    }


    public void parseJSON(String json){
        Contacts contacts=null;
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("patient");
            int count = 0;
            String name,age,height,weight,symptom,id;
            newList = new ArrayList<Contacts>();
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                name = jo.getString("Name");                          // data's are send to store in and print in listview
                age = jo.getString("Age");
                height = jo.getString("Height");
                weight = jo.getString("Weight");
                symptom = jo.getString("Symptom");
                id = jo.getString("Id");
                contacts = new Contacts(name,age,height,weight,symptom,id);
                newList.add(contacts);                                   // data are stored in the newlist array
                count++;
            }

            contactAdapter.add(newList);                               // the newlist array are send to add in the listview

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
