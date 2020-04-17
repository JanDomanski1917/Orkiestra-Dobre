package com.example.orkiestra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Obecnosc extends AppCompatActivity {

    EditText nazwisko;
    Button btn_sprawdz;
    TextView tv_data, tv_nazwa, tv_ilosc;

    FirebaseAuth firebaseAuth;
    int suma = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obecnosc);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        tv_data = (TextView) findViewById(R.id.tVData);
        Calendar kal = Calendar.getInstance();
        int rok = kal.get(Calendar.YEAR);
        int miesiac = kal.get(Calendar.MONTH);
        int dzien = kal.get(Calendar.DAY_OF_MONTH);
        String data = dzien + "-" + (miesiac + 1) + "-" + rok;
        tv_data.setText(data);

        tv_nazwa = (TextView) findViewById(R.id.tVSpotkanie);
        Intent classintent = getIntent();
        String classnamepassed = classintent.getStringExtra("Classname");
        tv_nazwa.setText(classnamepassed);
        tv_ilosc = (TextView) findViewById(R.id.tv_zlicz);
        nazwisko = (EditText) findViewById(R.id.et_ktos);
        btn_sprawdz = (Button) findViewById(R.id.bt_sprawdz);


        // databaseReference = FirebaseDatabase.getInstance().getReference("Attendence");
// FROM LOACATION BUT REMBER TO GIVE IT INSIDE LOOOP ELSE WILL COME BACK
        final DatabaseReference memberReference = database.getReference("Member");

        // final     DatabaseReference fromPath = FirebaseDatabase.getInstance().getReference("students");

        memberReference.orderByChild("nazwisko").equalTo(nazwisko.getText().toString());
//....................... TO LOCATION
        final DatabaseReference attendanceReference = database.getReference("Attendence").child(tv_nazwa.getText().toString())
                .child("Data = " + data).child(nazwisko.getText().toString());


        btn_sprawdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberReference.orderByChild("nazwisko").equalTo(nazwisko.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            Map<String, Object> map = new HashMap<>();
                            map.put("nazwisko", nazwisko.getText().toString());


                            attendanceReference.child(nazwisko.getText().toString())
                                    .updateChildren(map, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                            nazwisko.setText("");
                                        }
                                    });
//                                    .setValue(nazwisko.getText().toString(), new OnCompleteListener<String>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task task) {
//                                            nazwisko.setText("");
//                                        }
//                                    });




//                            //.........................
//
//                            ValueEventListener valueEventListener = new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                                    attendanceReference.child(nazwisko.getText().toString())
//                                            .setValue(dataSnapshot.getValue())
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(Task<Void> task) {
//                                            if (task.isComplete()) {
//                                                nazwisko.setText("");
//                                                //  Toast.makeText(TakeAttendence.this,"Attendence Accepted",Toast.LENGTH_SHORT).show();
//
//                                            } else {
//
//                                            }
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                }
//                            };
//                            memberReference.child(tv_nazwa.getText().toString())
//                                    .child(nazwisko.getText().toString())
//                                    .addListenerForSingleValueEvent(valueEventListener);

                            suma = suma + 1;
                            tv_ilosc.setText(String.valueOf(suma));
                            Toast.makeText(Obecnosc.this, "Attendence Accepted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Obecnosc.this, "Invalid", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
