package com.solupark.app;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.example.solupark.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap = null;
    private ArrayList<Park> Parkings = null;

    Location location = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("mygps", "affichage");
            Toast.makeText(this, "Application non permise", Toast.LENGTH_SHORT).show();
            return;
        }
        //lecturefirebase();
        LectureAPIspring();
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Log.d("mygps", location.getLatitude()+"," +location.getLongitude());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ajoutmarker();
    }
    public void LectureAPIspring(){
        Log.d("myapi", "fetchapi ok");
        FetchAPItask fetchAPItask = new FetchAPItask(this);
        fetchAPItask.execute("");
    }

    public void receiveparking(ArrayList<Park> Parkings) {
        this.Parkings = Parkings;
        Log.d("parking", String.valueOf(Parkings.size()));
        ajoutmarker();
    }

    public void ajoutmarker() {
        Geocoder geocoder = new Geocoder(this);
        if (Parkings != null && mMap != null) {
            for (int i = 0; i < Parkings.size(); i++) {
                Park parking = Parkings.get(i);
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocationName(parking.getLocation(), 1);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        //Location location2 = new Location(LocationManager.GPS_PROVIDER);
                        //location2.setLatitude(address.getLatitude());
                        //location2.setLongitude(address.getLongitude());
                        //location.distanceTo(location2);
                        //Log.d("dist"," " + location.distanceTo(location2));
                        mMap.addMarker(new MarkerOptions().position(latLng).title(parking.getLocation()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else Log.d("myapi", "affichage impossible");
    }

    public void lecturefirebase() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("parking")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String address = (String) document.get("address");
                                String codePostal = (String) document.get("codePostal");
                                String ville = (String) document.get("ville");
                                String pays = (String) document.get("pays");
                                Park park = new Park(address, codePostal, ville, pays);
                                MapsActivity.this.Parkings.add(park);
                                Log.d("firebase", document.getId() + " => " + park.toString());
                            }
                            ajoutmarker();
                        } else {
                            Log.w("firebase", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}