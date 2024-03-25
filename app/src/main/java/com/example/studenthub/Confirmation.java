package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        TextView logoutTextView = findViewById(R.id.logoutId);
        Button backhome_button = findViewById(R.id.backhome_button);

        TextView confirmationNumberTextView = findViewById(R.id.bookingID);

        Intent intent = getIntent();
        String confirmNo = intent.getStringExtra("confirmationNo");
        confirmationNumberTextView.setText("Booking ID: " + confirmNo);



        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutUser();
            }
        });

        backhome_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Confirmation.this, Accommodation_List.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Confirmation.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}