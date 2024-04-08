package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adapters.AccommodationAdapter;
import com.example.chat.ChatActivity;
import com.example.chat.UsersActivity;
import com.example.history.HistoryActivity;
import com.example.model.Accommodation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        TextView chatView = findViewById(R.id.chat);

        TextView historyView = findViewById(R.id.History);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHistoryPage();
            }
        });
        chatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToChatPage();
            }
        });
        accommodations.add(new Accommodation(R.drawable.forest_cove_apt, "Forest Cove Apartments", "Two-Bedroom Suites ."));
        accommodations.add(new Accommodation(R.drawable.university_point_apt, "University Point", "2 bed 1 bath "));
        accommodations.add(new Accommodation(R.drawable.horizons_apt, "Horizons Apartments", "3 bed 1 bath with a wide range of Facilities"));

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

    private void gotoHistoryPage() {
        Intent intent = new Intent(Accommodation_List.this, HistoryActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToChatPage() {
        Intent intent = new Intent(Accommodation_List.this, UsersActivity.class);
        startActivity(intent);
        finish();

    }
}
