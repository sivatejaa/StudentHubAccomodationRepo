package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.Accommodation;
import com.example.model.BookingDetails;
import com.example.model.PersonalInfo;
import com.example.model.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Preview_Info extends AppCompatActivity {


    private DatabaseReference bookingsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.bookingsDatabase = FirebaseDatabase.getInstance().getReference().child("Bookings");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_info);

        TextView logoutTextView = findViewById(R.id.logoutId);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        Button button = findViewById(R.id.buttonSubmit1);
        TextView textViewName = findViewById(R.id.textViewName1);
        TextView textViewEmail = findViewById(R.id.textViewEmail1);

        TextView textViewPrice = findViewById(R.id.textViewPrice1);
        TextView textViewPhone = findViewById(R.id.textViewPhone);
        TextView textViewAddress = findViewById(R.id.textViewAddress);
        TextView textViewRoomType = findViewById(R.id.textViewRoomType);
        TextView textViewCheckin = findViewById(R.id.textViewCheckinDate);
        TextView textViewCheckout = findViewById(R.id.textViewCheckoutDate);
        String confirmationNumber = generateConfirmationNumber();

        Intent intent = getIntent();
        Accommodation hotel = intent.getParcelableExtra("hotelObject");

        PersonalInfo personalInfo = intent.getParcelableExtra("personalInfo");
        Room room = intent.getParcelableExtra("room");
        double price = intent.getDoubleExtra("roomPrice", 0.0);

        if (hotel != null) {
            textViewName.setText(hotel.getName());
            textViewEmail.setText(personalInfo.getEmail());
            textViewPhone.setText(personalInfo.getPhone());
            textViewAddress.setText(personalInfo.getAddress());
            textViewRoomType.setText(room.getSelectedRoomType());
            textViewCheckin.setText(room.getCheckinDate());
            textViewCheckout.setText(room.getCheckoutDate());
            textViewPrice.setText("$ " + String.valueOf(price));
        }

        //1. go to Confirmation page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Preview_Info.this, Confirmation.class);
                    hotel.setConfirmationNumber(confirmationNumber);
                    hotel.setRoomInfo(room);
                    hotel.setPersonalInfo(personalInfo);
                    hotel.setPrice(price);
                    saveBookingToDatabase(hotel);

                    intent.putExtra("hotelObject", hotel);
                    intent.putExtra("personalInfo", personalInfo);
                    intent.putExtra("room", room);
                    intent.putExtra("confirmationNo", confirmationNumber);
                    Log.d("previewInfo123", hotel.getName());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("", e.getMessage());
                }
            }
        });
    }

    private void saveBookingToDatabase(Accommodation hotel) {

        //2. Making unavailable in the dropdown in the RoomPage
        String confirmationNumber = hotel.getConfirmationNumber();
        DatabaseReference bookingRef = bookingsDatabase.child(confirmationNumber);
        DatabaseReference apartmentsRef = FirebaseDatabase.getInstance().getReference("Accommodation/apartments");
        apartmentsRef.child(hotel.getRoomInfo().getSelectedRoomType()).setValue("Not available").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Value updated successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "Failed to update value", e);
            }
        });

        hotel.setDecisionStatus("Pending");
        hotel.setUidNo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        // Set the values for the booking under the specified node
        bookingRef.setValue(hotel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Data inserted successfully with confirmation number: " + confirmationNumber);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firebase", "Error inserting data: " + e.getMessage());
                    }
                });


        bookingRef.setValue(hotel);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        // 3. Inserting into History
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("History").child(userId);
        //  String bookingId2 = userRef.child("bookings").push().getKey();
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setConfirmationNumber(hotel.getConfirmationNumber());
        bookingDetails.setAptInfo(hotel.getDescription());
        bookingDetails.setStatus(hotel.getDecisionStatus());
        userRef.child("bookings").child(hotel.getConfirmationNumber()).setValue(bookingDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Booking details saved successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to save booking details
                    }
                });
    }

    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Preview_Info.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private String generateConfirmationNumber() {
        String timestamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        return " " + timestamp + randomNumber;
    }
}