package com.example.orkiestra;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Czlonkowie extends Activity {

    Spinner lista_glosow;
    Button btn_pokaz, btn_dodaj;
    ListView listView;
    String glos;
    private String[] glosyString = new String[]{"FL", "CL", "SAX", "TRU", "BAR", "ALT", "BAS"};
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference table_member = database.getReference("Member");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czlonkowie);

        lista_glosow = (Spinner) findViewById(R.id.spis_glosow);
        btn_pokaz = (Button) findViewById(R.id.bt_pokaz);
        btn_dodaj = (Button) findViewById(R.id.bt_dodaj);
        listView = (ListView) findViewById(R.id.list_view);

        lista_glosow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
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
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lista_glosow.setAdapter(adapter_branch);


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        btn_pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayAdapter.clear();
                listView.setAdapter(arrayAdapter);
                table_member.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String glosOonItem = dataSnapshot.getValue(Member.class).getGlos();
                        if (glosOonItem.equals(glos)) {
                            String value = dataSnapshot.getValue(Member.class).toString();
                            arrayList.add(value);
                            arrayAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });

        btn_dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Czlonkowie.this, DodajCzlonka.class);
                startActivity(intent);
            }
        });

    }
}

