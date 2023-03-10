package com.solupark.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        visupark();
        reglage();
        Log.d("myapi", "lancement de la tache");
        MyBd db = new MyBd(this);
        Log.d("myapi", "affichage" + db.LectureRayon());
        db.writeRayon(12);
        Log.d("myapi", "affichage" + db.LectureRayon());
    }

    private void visupark(){
        ((Button) findViewById(R.id.listpark)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void reglage(){
        ((Button) findViewById(R.id.reglages)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, ReglagesActivity.class);
                startActivity(intent);
            }
        });
    }
}