package com.example.manigutmadayit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FBStorage extends AppCompatActivity {
    private Button btnave;
    TextInputEditText editTextFile;
    StorageReference reference;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fire_base_storage);
        btnave = findViewById(R.id.txtFile);
        editTextFile = findViewById(R.id.txt);
        reference = FirebaseStorage.getInstance().getReference().child("Document");

        btnave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data ="This is my data";
                reference.child("file.txt").putBytes(data.getBytes()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(FBStorage.this,"file uploaded successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FBStorage.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


}
