package com.example.uts_amub_ti7jm_1711500110_febriansyah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneActivity extends AppCompatActivity {
    ImageButton img_next_btn;
    EditText ed_username_one, ed_password_one, ed_email;

    DatabaseReference reference;
    String USERNAME_KEY = " usernamekey";
    String username_key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        ed_username_one = findViewById(R.id.ed_usernameone);
        ed_password_one = findViewById(R.id.ed_passwordone);
        ed_email = findViewById(R.id.ed_email);

        img_next_btn = findViewById(R.id.img_btn_next);

        img_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menyimpan data kepada local storage (handphone)
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, ed_username_one.getText().toString()); //ngambil data inputan username disimpan ke variabel username
                editor.apply();

                //simpan ke database
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(ed_username_one.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(ed_username_one.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(ed_password_one.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(ed_email.getText().toString());
                        dataSnapshot.getRef().child("user_balance").setValue(100000);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //berpindah ke activity lain activity registertwoactivity
                Intent gotonextregister = new Intent(RegisterOneActivity.this, RegisterTwoActivity.class);
                startActivity(gotonextregister);
            }
        });
    }
}