package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.model.ChatUser;
import com.example.model.User;
import com.example.studenthub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<ChatUser> userList;

    FirebaseDatabase fdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        // Initialize RecyclerView and user list
        recyclerViewUsers = findViewById(R.id.recycler_view_users);
        userList = new ArrayList<>();

        fdatabase = FirebaseDatabase.getInstance();
        //  auth= FirebaseAuth.getInstance();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(uid.equalsIgnoreCase("3Coa9p2X5sVfxaANU09M8eZEBrE2")){
            DatabaseReference reference = fdatabase.getReference().child("Users");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ChatUser user = dataSnapshot.getValue(ChatUser.class);
                        if(!(user.getMail().equalsIgnoreCase("sivatejaa.thangala@gmail.com"))){
                            userList.add(user);
                        }

                    }
                    userAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            ChatUser user2=new ChatUser("sivatejaa.thangala@gmail.com","Admin","9963411997","3Coa9p2X5sVfxaANU09M8eZEBrE2");
            userList.add(user2);
          //  userAdapter.notifyDataSetChanged();
        }


        userAdapter = new UserAdapter(this, userList);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsers.setAdapter(userAdapter);
    }

    @Override
    public void onBackPressed() {
        // Your custom back functionality here
        super.onBackPressed();
    }
}