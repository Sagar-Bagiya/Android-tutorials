package com.example.mychatapp.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mychatapp.models.ChatMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseUtils {

    public static void sendMessage(String senderId, String receiverId, String messageText) {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");
        String messageId = messagesRef.push().getKey();
        ChatMessage chatMessage = new ChatMessage(messageId, senderId, receiverId, messageText, System.currentTimeMillis());
        messagesRef.child(messageId).setValue(chatMessage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
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

    public static void receiveMessages(String senderId, String receiverId, final MessageListener listener) {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");
        Query query = messagesRef.orderByChild("timestamp");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                if (chatMessage.getSenderId().equals(senderId) && chatMessage.getReceiverId().equals(receiverId)) {
                    listener.onNewMessage(chatMessage);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle child changes if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Handle child removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle child movement if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
            }
        });
    }

    public  interface MessageListener {
        void onNewMessage(ChatMessage message);
    }
}
