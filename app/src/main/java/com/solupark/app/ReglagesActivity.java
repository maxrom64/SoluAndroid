package com.solupark.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReglagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglages);
        Validkm();
    }

    private void Validkm() {
        ((Button) findViewById(R.id.valid)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText var = findViewById(R.id.km);
                String valeur = var.getText().toString();
                int valeurint = Integer.parseInt(valeur);
                MyBd db = new MyBd(ReglagesActivity.this);
                db.writeRayon(valeurint);
                ReglagesActivity.this.finish();
            }
        });
    }
}