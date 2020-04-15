package com.example.orkiestra;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DodajCzlonka extends Activity {

    Button btn_dodaj;
    EditText edt_imie, edt_nazwisko, edt_telefon;
    Spinner lista_glosow;
    String glos;
    private String[] glosyString = new String[]{"FL", "CL", "SAX", "TRU", "BAR", "ALT", "BAS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie);

        lista_glosow = (Spinner) findViewById(R.id.spis_glosow);
        edt_imie = (EditText) findViewById(R.id.et_imie);
        edt_nazwisko = (EditText) findViewById(R.id.et_nazwisko);
        edt_telefon = (EditText) findViewById(R.id.et_telefon);
        btn_dodaj = (Button) findViewById(R.id.bt_dodaj);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_member = database.getReference("Member");


        lista_glosow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
                glos = (String) lista_glosow.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, glosyString);
        adapter_branch
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lista_glosow.setAdapter(adapter_branch);


        btn_dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((edt_imie.length() != 0) && (edt_nazwisko.length() != 0) && (edt_telefon.length() != 0)) {
                    final ProgressDialog mDialog = new ProgressDialog(DodajCzlonka.this);
                    mDialog.setMessage("Please Waiting....");
                    mDialog.show();

                    table_member.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Member member = new Member(edt_imie.getText().toString(), edt_nazwisko.getText().toString(), edt_telefon.getText().toString(),lista_glosow.getSelectedItem().toString());
                            table_member.child(edt_telefon.getText().toString()).setValue(member);
                            Toast.makeText(DodajCzlonka.this, "Gotowe", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent homeIntent = new Intent(DodajCzlonka.this, Czlonkowie.class);
                            startActivity(homeIntent);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else {
                    Toast.makeText(DodajCzlonka.this, "Wprowadź dane !", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
