package com.example.mychatapp;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class InputValidator {
    public static  boolean isEmailValid(EditText editText) {
        String email = editText.getText().toString().trim();
        if (email.isEmpty()) {
            editText.setError("Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("Invalid email format");
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(EditText editText) {
        String password = editText.getText().toString().trim();
        if (password.isEmpty()) {
            editText.setError("Password cannot be empty");
            return false;
        } else if (password.length() < 6) {
            editText.setError("Password must be at least 6 characters long");
            return false;
        }
        return true;
    }

    public static boolean isValidName(EditText editText) {
        String name = editText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editText.setError("Name cannot be empty");
            return false;
        }

        // Check if the name contains only alphabetic characters or space
        if (!name.matches("[a-zA-Z ]+")) {
            editText.setError("Invalid name. Only alphabetic characters and space are allowed");
            return false;
        }

        return true;
    }

}
