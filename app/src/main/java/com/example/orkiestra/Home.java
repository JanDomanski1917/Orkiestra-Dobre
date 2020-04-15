package com.example.orkiestra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    Button btn_kalendarz, btn_spotkania, btn_czlonkowie, btn_wyloguj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_kalendarz = (Button) findViewById(R.id.bt_kaledarz);
        btn_spotkania = (Button) findViewById(R.id.bt_spotkania);
        btn_czlonkowie = (Button) findViewById(R.id.bt_czonkowie);
        btn_wyloguj = (Button) findViewById(R.id.bt_wyloguj);


        btn_kalendarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kal = new Intent(Home.this, Kalendarz.class);
                startActivity(kal);
            }
        });

        btn_spotkania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frek = new Intent(Home.this, Spotkania.class);
                startActivity(frek);
            }
        });

        btn_czlonkowie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent czlo = new Intent(Home.this, Czlonkowie.class);
                startActivity(czlo);
            }
        });

        btn_wyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wyloguj = new Intent(Home.this, MainActivity.class);
                wyloguj.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(wyloguj);
            }
        });
    }
}














