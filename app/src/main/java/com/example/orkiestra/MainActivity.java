package com.example.orkiestra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orkiestra.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button btn_rej, btn_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_rej = (Button) findViewById(R.id.bt_rejestracja);
        btn_log = (Button) findViewById(R.id.bt_logowanie);

        Paper.init(this);

        btn_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, Rejestracja.class);
                startActivity(register);
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, Logowanie.class);
                startActivity(signIn);
            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);

        if (user != null && pwd != null) {
            if (!user.isEmpty() && !pwd.isEmpty()) login(user, pwd); }
    }

    private void login(final String userr, final String pwd) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);

        mDialog.setMessage("Proszę czekać....");
        mDialog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(userr).exists()) {

                    mDialog.dismiss();
                    User user = dataSnapshot.child(userr).getValue(User.class);
                    user.setTel(userr);

                    if (user.getHaslo().equals(pwd)) {
                        Intent homeIntent = new Intent(MainActivity.this, Home.class);
                        Common.currentUser = user;
                        startActivity(homeIntent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Złe hasło ...", Toast.LENGTH_SHORT).show(); }

                } else {
                    mDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Użytkownik nie istnieje ...", Toast.LENGTH_SHORT).show(); }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}