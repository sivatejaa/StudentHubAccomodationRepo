package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chat.UsersActivity;
import com.example.model.Accommodation;
import com.example.model.AdminModel;
import com.example.studenthub.Accommodation_List;
import com.example.studenthub.MainActivity;
import com.example.studenthub.Preview_Info;
import com.example.studenthub.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    private List<AdminModel> houseList;
    private DatabaseReference bookingsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        TextView chatView = findViewById(R.id.chatAdmin);
        //  Button buttonDecision = findViewById(R.id.buttonDecision);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        houseList = new ArrayList<>();
        adapter = new AdminAdapter(houseList);
        recyclerView.setAdapter(adapter);

        bookingsDatabase = FirebaseDatabase.getInstance().getReference().child("Bookings");

        TextView logoutTextView = findViewById(R.id.logoutId);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        chatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToChatPage();
            }
        });
        fetchDataFromFirebase();

    }

    private void fetchDataFromFirebase() {

        Log.d("Firebase", "Database Reference: " + bookingsDatabase.toString());
        bookingsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                houseList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    AdminModel adminModel = new AdminModel();
                    if (snapshot != null) {
                        String nameDetail = snapshot.child("personalInfo").child("name").getValue(String.class);
                        String houseDetail = snapshot.child("name").getValue(String.class);
                        String confirmationNumber = snapshot.child("confirmationNumber").getValue(String.class);

                        String roomType=snapshot.child("roomInfo").child("selectedRoomType").getValue(String.class);


                        String uidNo = snapshot.child("uidNo").getValue(String.class);
                        String decisionStatus = snapshot.child("decisionStatus").getValue(String.class);
                        adminModel.setDecisionStatusDetail(decisionStatus);

                        adminModel.setUidNo(uidNo);
                        adminModel.setNameDetail(nameDetail);
                        adminModel.setHouseDetail(houseDetail);
                        adminModel.setConfirmationNumber(confirmationNumber);
                        adminModel.setRoomsDetail(roomType);

                        houseList.add(adminModel);

                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("AdminActivity", "Database operation canceled: " + databaseError.getMessage());
            }
        });
    }

    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToChatPage() {
        Intent intent = new Intent(AdminActivity.this, UsersActivity.class);
        startActivity(intent);
        finish();

    }
}
