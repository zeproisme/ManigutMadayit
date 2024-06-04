package com.example.manigutmadayit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class AdminUpdate extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_update);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance("https://manigut-madayit-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        // Set up the button click listener
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(AdminUpdate.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                EditText editTextEmail = findViewById(R.id.editEmail);
                String email = editTextEmail.getText().toString().trim();
                if (!email.isEmpty()) {
                    fetchUserIdByEmailAndUpdateRole(email, "admin");
                } else {
                    Toast.makeText(AdminUpdate.this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();
        Button updateRole = findViewById(R.id.updateButton);
        updateRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);

            }
        });
    }

    private void fetchUserIdByEmailAndUpdateRole(final String email, final String newRole) {
        DatabaseReference usersRef = mDatabase.child("users");

        // Query to find user by email
        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Iterate through the results (though there should only be one)
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        // Update the user role
                        updateUserRole(userId, newRole);
                    }
                } else {
                    // Email not found
                    Toast.makeText(AdminUpdate.this, "Email not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                Toast.makeText(AdminUpdate.this, "Database error.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void notifyUser(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private void updateUserRole(String userId, String newRole) {
        DatabaseReference userRef = mDatabase.child("users").child(userId);
        userRef.child("role").setValue(newRole)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminUpdate.this, "User role updated.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminUpdate.this, "Failed to update role.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
