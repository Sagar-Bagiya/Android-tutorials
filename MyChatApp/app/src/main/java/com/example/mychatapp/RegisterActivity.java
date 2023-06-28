package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mychatapp.databinding.ActivityRegesterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegesterBinding binding;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegesterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputValidator.isValidName(binding.editTextName) && InputValidator.isEmailValid(binding.editTextEmail) && InputValidator.isPasswordValid(binding.editTextPassword)) {
                    register(binding.editTextName.getText().toString().trim(),binding.editTextEmail.getText().toString().trim(), binding.editTextPassword.getText().toString().trim());
                }
            }
        });

    }

    private void register(String name , String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {

                    FirebaseUser user = mAuth.getCurrentUser();
                    String userId = user.getUid();

                // Create a map to hold the user data
                Map<String, Object> userData = new HashMap<>();
                userData.put("name", name);
                userData.put("email", email);

//                userData.put("username", username);

                // Store the user data in the Realtime Database
                mDatabase.child("users").child(userId).setValue(userData);
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();

            })
                .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }

}
