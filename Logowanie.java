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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orkiestra.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Logowanie extends AppCompatActivity {

    EditText edt_login, edt_haslo;
    Button btn_loguj;
    CheckBox chb_pamietaj;
    TextView txt_zapomiane;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference table_user = database.getReference("User");

    SmsManager smsManager = null;
    String wiadomosc = "Zalogowano do aplikacji Orkiestra Dęta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);

        edt_login     =(EditText)findViewById(R.id.telefon_log);
        edt_haslo     =(EditText)findViewById(R.id.haslo_log);
        chb_pamietaj  = (CheckBox) findViewById(R.id.pametaj);
        btn_loguj     =(Button) findViewById(R.id.bt_loguj);
        txt_zapomiane =(TextView)findViewById(R.id.txtForgotPwd);


        txt_zapomiane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPwDialog();
            }
        });
        btn_loguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chb_pamietaj.isChecked()) {
                    Paper.book().write(Common.USER_KEY,edt_login.getText().toString());
                    Paper.book().write(Common.PWD_KEY,edt_haslo.getText().toString()); }

                if (edt_login.length() != 0) {
                    final ProgressDialog mDialog = new ProgressDialog(Logowanie.this);
                    mDialog.setMessage("Proszę czekać..");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(edt_login.getText().toString()).exists()) {
                                mDialog.dismiss();
                                User user = dataSnapshot.child(edt_login.getText().toString()).getValue(User.class);
                                user.setTel(edt_login.getText().toString()); //Set phone

                                if (user.getHaslo().equals(edt_haslo.getText().toString())) {
                                    smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(edt_login.getText().toString(),null,wiadomosc,null,null);
                                    Intent homeIntent = new Intent(Logowanie.this, Home.class);
                                    startActivity(homeIntent);
                                    Toast.makeText(getApplicationContext(), "Gotowe", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Logowanie.this, "Złe hasło !!!", Toast.LENGTH_SHORT).show(); }

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(Logowanie.this, "Użytkownik nie istnieje", Toast.LENGTH_SHORT).show(); }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else {
                    Toast.makeText(Logowanie.this, "WPROWADZ DANE !", Toast.LENGTH_SHORT).show(); }
            }
        });
    }

    private void showForgotPwDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Zapomniałeś hasła?");
        builder.setMessage("Podaj Rok urodzin");
        LayoutInflater inflater = this.getLayoutInflater();
        View forgot_view = inflater.inflate(R.layout.cardview_zapomniane, null);
        builder.setView(forgot_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final EditText edt_tel_zap = (EditText) forgot_view.findViewById(R.id.telefon_zap);
        final EditText edt_pin_zap = (EditText) forgot_view.findViewById(R.id.Pin_zap);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (edt_login.length() != 0) {
                    table_user.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.child(edt_tel_zap.getText().toString()).getValue(User.class);

                            if (user.getPIN().equals(edt_pin_zap.getText().toString()))
                                Toast.makeText(Logowanie.this, "Twoje hasło:  " + user.getHaslo(), Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Logowanie.this, "Zły rok Urodzin!", Toast.LENGTH_SHORT).show(); }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }else {
                    Toast.makeText(Logowanie.this, "WPROWADZ DANE !", Toast.LENGTH_SHORT).show(); }
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

