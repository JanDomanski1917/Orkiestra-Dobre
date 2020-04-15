package com.example.orkiestra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Spotkania extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    Button btn_proba, btn_wystep, btn_zebranie;

    int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotkania);

        firebaseAuth = FirebaseAuth.getInstance();
        btn_proba = (Button) findViewById(R.id.bt_proba);
        btn_wystep = (Button) findViewById(R.id.bt_wystep);
        btn_zebranie = (Button) findViewById(R.id.bt_zebranie);

    }

    public void Proba (View view){
        String TextClassname = btn_proba.getText().toString();
        // starting our intent
        Intent intent_p = new Intent(this,Obecnosc.class);
        intent_p.putExtra("Classname",TextClassname);
        startActivityForResult(intent_p,request_code);
    }

    public void Wystep (View view){
        String TextClassname = btn_wystep.getText().toString();
        // starting our intent
        Intent intent_w = new Intent(this,Obecnosc.class);
        intent_w.putExtra("Classname",TextClassname);
        startActivityForResult(intent_w,request_code);
    }

    public void Zebranie (View view){
        String TextClassname = btn_zebranie.getText().toString();
        // starting our intent
        Intent intent_z = new Intent(this,Obecnosc.class);
        intent_z.putExtra("Classname",TextClassname);
        startActivityForResult(intent_z,request_code);
    }
}
