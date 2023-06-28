package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mychatapp.adpters.OnItemClickListener;
import com.example.mychatapp.adpters.UserAdapter;
import com.example.mychatapp.databinding.ActivityChatBinding;
import com.example.mychatapp.models.ChatMessage;
import com.example.mychatapp.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    ActivityChatBinding binding;
    UserAdapter adapter;
    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        // Retrieve all user from database
        retrieveAllUser();
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    List<UserModel> userList = new ArrayList<>();

    private void retrieveAllUser() {
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    String email = (String) userSnapshot.child("email").getValue();
                    String name = (String) userSnapshot.child("name").getValue();

                    if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(email))
                        binding.txtUserName.setText(name);
                    else {
                        UserModel user = new UserModel(userId, name, email);
                        userList.add(user);
                    }
                }
                showUsersList(userList);
                binding.progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Log.e("DatabaseError", databaseError.getMessage());
            }
        });
    }

    public void showUsersList(List<UserModel> userList) {
        if (userList != null) {
            adapter = new UserAdapter(userList);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(UserModel user) {
                    Intent intent = new Intent(ChatActivity.this, ChatWithX.class);
                    intent.putExtra("uId", user.getuId());
                    intent.putExtra("name", user.getName());
                    startActivity(intent);
                }
            });

            binding.rvChat.setAdapter(adapter);
            binding.rvChat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        }
    }

    public void lastMessages(UserModel user) {
        String senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("message");

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ChatMessage message = snapshot1.getValue(ChatMessage.class);
                    if ((message.getSenderId().equals(user.getuId()) && message.getReceiverId().equals(senderID)) || (message.getReceiverId().equals(senderID) && message.getReceiverId().equals(user.getuId()))) {
                        user.setLastMessage(message.getText());
                    }
                }

                userList.add(user);

                Log.d(TAG, "firebase user list ... : "+ user.toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}