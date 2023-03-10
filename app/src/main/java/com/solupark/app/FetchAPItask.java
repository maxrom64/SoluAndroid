package com.solupark.app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class FetchAPItask  extends AsyncTask<String, String, String> {
    ArrayList <Park> Parkings = new ArrayList<>();
    MapsActivity mapsactivity;
    public FetchAPItask(MapsActivity mapsactivity){
        this.mapsactivity = mapsactivity;
    }
    /*@Override*/
    protected String doInBackground(String... strings) {
        HttpURLConnection connexion;
        Log.d("myapi","myapi ok");
        try {
            URL url = new URL("http://192.168.1.86:8080/Solupark/apiparkings");
            connexion = (HttpURLConnection) url.openConnection();
            Log.d("myapi","connexion etablie");
            connexion.setRequestProperty("Content-type","application/json");
            InputStream is = connexion.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            int data = isr.read();
            String reponse = "";
            while (data != -1){
                reponse = reponse + (char) data;
                data = isr.read();
            }
            Log.d("myapi","affichage park");
            JSONArray jsonArray = new JSONArray(reponse);
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject jsonobject = (JSONObject) jsonArray.get(i);
                String address = jsonobject.getString("address");
                String codePostal = jsonobject.getString ("codePostal");
                String ville = jsonobject.getString ("ville");
                String pays = jsonobject.getString ("pays");
                Park park = new Park(address,codePostal,ville,pays);
                Parkings.add(park);
            }
                Log.d("myapi",reponse);
        } catch (Exception e) {
            Log.d("myapi", "erreur de fetch de l api"+e);
        }
        return null;
    }

    /*@Override*/
    protected void onPostExecute(String s) {
        Log.d("myapi","myapi onpost");
        super.onPostExecute(s);
        this.mapsactivity.receiveparking(Parkings);

    }
}