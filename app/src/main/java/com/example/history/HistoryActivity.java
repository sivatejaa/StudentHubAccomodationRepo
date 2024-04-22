package com.example.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.model.BookingDetails;
import com.example.studenthub.Accommodation_List;
import com.example.studenthub.Confirmation;
import com.example.studenthub.MainActivity;
import com.example.studenthub.Preview_Info;
import com.example.studenthub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    //  private List<BookingDetails> bookingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerViewAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button backhome_button = findViewById(R.id.backhome_button_history);

        TextView logoutTextView = findViewById(R.id.logoutId);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference userBookingsRef = FirebaseDatabase.getInstance().getReference().child("History").child(userId).child("bookings");

        ArrayList<BookingDetails> bookingList = new ArrayList<>();

        backhome_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, Accommodation_List.class);
                startActivity(intent);
                finish();
            }
        });
        userBookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingList.clear();

                for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                    BookingDetails bookingDetails = bookingSnapshot.getValue(BookingDetails.class);
                    if (bookingDetails != null) {
                        bookingList.add(bookingDetails);
                    }
                }
                adapter = new HistoryAdapter(bookingList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Log.e("Firebase", "Error fetching user bookings: " + databaseError.getMessage());
            }
        });

    }

    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}