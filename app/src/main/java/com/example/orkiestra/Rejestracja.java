package com.example.orkiestra;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orkiestra.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Rejestracja extends AppCompatActivity {

    EditText edt_telefon, edt_login, edt_haslo, edt_pin;
    Button btn_rejestruj;

    SmsManager smsManager = null;
    Random rand = new Random();
    int kod1 = rand.nextInt(9999 - 1000 + 1) + 1000;
    String kod2 = Integer.toString(kod1);
//    String wiadomosc = String.valueOf(kod2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        edt_login = (EditText) findViewById(R.id.imie_rej);
        edt_haslo = (EditText) findViewById(R.id.haslo_rej);
        edt_telefon = (EditText) findViewById(R.id.telefon_rej);
        edt_pin = (EditText) findViewById(R.id.PIN_rej);
        btn_rejestruj = (Button) findViewById(R.id.bt_rejestruj);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btn_rejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((edt_telefon.length() != 0) && (edt_login.length() != 0) && (edt_haslo.length() != 0) && (edt_pin.length() != 0)) {
                    final ProgressDialog mDialog = new ProgressDialog(Rejestracja.this);
                    mDialog.setMessage("Please Waiting....");
                    mDialog.show();

                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edt_telefon.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(Rejestracja.this, "Podany telefon istnieje", Toast.LENGTH_SHORT).show();
                            } else {
                                mDialog.dismiss();
                                User user = new User(edt_login.getText().toString(), edt_haslo.getText().toString(), edt_pin.getText().toString());
                                user.setTel(edt_telefon.getText().toString());
                                table_user.child(edt_telefon.getText().toString()).setValue(user);
                                smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(user.getTel(), null, kod2, null, null);

                                aktywacja(user);

//                                Toast.makeText(Rejestracja.this, "Gotowe", Toast.LENGTH_SHORT).show();
//                                finish();
//                                Intent homeIntent = new Intent(Rejestracja.this, Home.class);
//                                Common.currentUser = user;
//                                startActivity(homeIntent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else {
                    Toast.makeText(Rejestracja.this, "Wprowadź dane !", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void aktywacja(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Podaj kod aktywacyjny");
        LayoutInflater inflater = this.getLayoutInflater();
        View forgot_view = inflater.inflate(R.layout.cardview_aktywacja, null);
        builder.setView(forgot_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final EditText edt_kod3 = (EditText) forgot_view.findViewById(R.id.ed_kod);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (edt_kod3.length() != 0) {
                    if (kod2.equals(edt_kod3.getText().toString())) {
                        Toast.makeText(Rejestracja.this, "Konto aktywowane", Toast.LENGTH_LONG).show();

                        Toast.makeText(Rejestracja.this, "Gotowe", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent homeIntent = new Intent(Rejestracja.this, Home.class);
                        Common.currentUser = user;
                        startActivity(homeIntent);

                    } else
                        Toast.makeText(Rejestracja.this, "Zły kod!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Rejestracja.this, "WPROWADZ DANE !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
