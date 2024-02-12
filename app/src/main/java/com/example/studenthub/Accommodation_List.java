package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adapters.AccommodationAdapter;
import com.example.model.Accommodation;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Accommodation_List extends AppCompatActivity {


    private ArrayList<Accommodation> accommodations = new ArrayList<>();
    private RecyclerView recyclerView;
    private AccommodationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_list);


        TextView logoutTextView = findViewById(R.id.logoutId);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutUser();
            }
        });
        accommodations.add(new Accommodation( R.drawable.motel5,"Holiday Inn  (Price Starting from $100)", "5 Hotel Chains with Two-Bedroom Suites You Can Book."));
        accommodations.add(new Accommodation(R.drawable.motel_2,"Haytt (Price Starting from $90)", "2 bed 1 bath Super Deluxe Hotel 2 Star Hotel"));
        accommodations.add(new Accommodation(R.drawable.motel_4,"The Plaza (Price Starting from $110)", "3 bed 1 bath Hotel 3 Star Hotel with a wide range of Facilities" ));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AccommodationAdapter(this, accommodations);
        recyclerView.setAdapter(adapter);





    }

    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Accommodation_List.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
