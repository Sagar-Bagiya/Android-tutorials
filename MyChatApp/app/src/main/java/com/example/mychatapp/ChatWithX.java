package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mychatapp.adpters.ChatAdapter;
import com.example.mychatapp.databinding.ActivityChatWithXBinding;
import com.example.mychatapp.models.ChatMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChatWithX extends AppCompatActivity {
    ActivityChatWithXBinding binding;
    DatabaseReference messagesRef;

    ValueEventListener messagesListener;
    ChatAdapter adapter;
    String receiverId;
    String senderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatWithXBinding.inflate(getLayoutInflater());
        adapter = new ChatAdapter();
        adapter.setRecyclerView(binding.rvChat);

        setContentView(binding.getRoot());

        senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messagesRef = FirebaseDatabase.getInstance().getReference("messages");

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        receiverId = intent.getStringExtra("uId");

        binding.txtUserName.setText(name);

        binding.rvChat.setAdapter(adapter);

        binding.rvChat.setLayoutManager(new LinearLayoutManager(this));

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.edtMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(message))
                    sendMessage(message);
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        receiveMessages();
    }

    public void sendMessage(String messageText) {

        String messageId = messagesRef.push().getKey();

        ChatMessage chatMessage = new ChatMessage(messageId,messageText,senderId, receiverId, System.currentTimeMillis());

        messagesRef.child(messageId).setValue(chatMessage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        binding.edtMessage.setText(null);
                        // Message sent successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to send message
                    }
                });

    }

    public  void receiveMessages() {
        Query query = messagesRef.orderByValue();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                if ((chatMessage.getSenderId().equals(senderId) && chatMessage.getReceiverId().equals(receiverId)) || (chatMessage.getSenderId().equals(receiverId) && chatMessage.getReceiverId().equals(senderId))) {
                    adapter.addMessage(chatMessage);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


//    public void receiveMessages() {
//        Query query = messagesRef.orderByChild("timestamp").limitToLast(100); // Limit to last 100 messages
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                adapter.clearMessages(); // Clear existing messages in the adapter
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
//                    if (chatMessage.getSenderId().equals(senderId) || chatMessage.getReceiverId().equals(senderId)) {
//                        adapter.addMessage(chatMessage); // Add the received chat message to the adapter
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle potential errors
//            }
//        });
//    }

}