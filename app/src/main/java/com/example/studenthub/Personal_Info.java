package com.example.studenthub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.model.Accommodation;
import com.google.firebase.auth.FirebaseAuth;


public class Personal_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        TextView logoutTextView = findViewById(R.id.logoutId);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logout functionality here
                logoutUser();
            }
        });
        EditText nameEditText= findViewById(R.id.edName);
        EditText emailEditText= findViewById(R.id.edEmail);
        EditText phoneEditText= findViewById(R.id.edPhone);
        EditText addressEditText= findViewById(R.id.editTexAddress);
        Button button = findViewById(R.id.button);

        Intent intent = getIntent();
        Accommodation accommodation = intent.getParcelableExtra("hotelObject");




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {

                    new AlertDialog.Builder(Personal_Info.this)
                            .setTitle("Error")
                            .setMessage("Please enter all the fields.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }else{
                  /*  Intent roomInfoIntent = new Intent(Personal_Info.this, Room_Info.class);

                    roomInfoIntent.putExtra("hotelObject", hotel);
                    PersonalInfo personalInfo=new PersonalInfo(name,email,address,phone);

                    roomInfoIntent.putExtra("personalInfo", personalInfo);

                    Log.d("hotel.getName()", hotel.getName());
                    startActivity(roomInfoIntent);*/
                }




            }
        });
    }
    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Personal_Info.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity after logout
    }
}
