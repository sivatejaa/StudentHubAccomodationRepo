package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.admin.AdminActivity;
import com.example.history.HistoryActivity;
import com.example.studenthub.Accommodation_List;
import com.example.studenthub.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Date;


import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    String reciverimg, reciverUid,reciverName,SenderUID;
    CircleImageView profile;
    TextView reciverNName;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    public  static String senderImg;
    public  static String reciverIImg;
    ImageButton sendbtn;
    EditText textmsg;

    String senderRoom,reciverRoom;
    RecyclerView recyclerView;
    ArrayList<Message> messagesArrayList;
    MessageAdapter mMessageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        Button backhome_button = findViewById(R.id.backhome_button_chat);

        backhome_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(uid.equalsIgnoreCase("3Coa9p2X5sVfxaANU09M8eZEBrE2")){
                    Intent intent = new Intent(ChatActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(ChatActivity.this, Accommodation_List.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        reciverName = getIntent().getStringExtra("userName");
        reciverUid = getIntent().getStringExtra("uId");

        messagesArrayList = new ArrayList<>();

        sendbtn = findViewById(R.id.sendButton);
        textmsg = findViewById(R.id.messageEditText);
        reciverNName = findViewById(R.id.receiverTextView);
        recyclerView = findViewById(R.id.recyclerViewChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mMessageAdapter = new MessageAdapter(ChatActivity.this,messagesArrayList);
        recyclerView.setAdapter(mMessageAdapter);


        reciverNName.setText(""+reciverName);

        SenderUID =  firebaseAuth.getUid();

        senderRoom = SenderUID+reciverUid;
        reciverRoom = reciverUid+SenderUID;



        DatabaseReference  reference = database.getReference().child("Users").child(firebaseAuth.getUid());
        DatabaseReference  chatreference = database.getReference().child("chats").child(senderRoom).child("messages");


        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Message messages = dataSnapshot.getValue(Message.class);
                    messagesArrayList.add(messages);
                }
                mMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textmsg.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Enter The Message First", Toast.LENGTH_SHORT).show();
                    return;
                }
                textmsg.setText("");
                Date date = new Date();
                Message messagess = new Message(message,SenderUID,date.getTime());

                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats")
                                        .child(reciverRoom)
                                        .child("messages")
                                        .push().setValue(messagess);
                            }
                        });
            }
        });

    }
}