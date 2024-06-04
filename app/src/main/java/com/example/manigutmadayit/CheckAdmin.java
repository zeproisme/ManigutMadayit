package com.example.manigutmadayit;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckAdmin {

    public static void checkAdminAndSetVisibility(final Context context, final View... views) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://manigut-madayit-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = mDatabase.child("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        if ("admin".equals(role)) {
                            // User is an admin, show the UI elements
                            for (View view : views) {
                                view.setVisibility(View.VISIBLE);
                            }
                        } else {
                            // User is not an admin, hide the UI elements
                            for (View view : views) {
                                view.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        Toast.makeText(context, "User data not found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // User is not logged in
            for (View view : views) {
                view.setVisibility(View.GONE);
            }
        }
    }
}
