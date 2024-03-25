package com.example.manigutmadayit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button events;
    Button button;
    Button branch;
    Button substitute;
    TextView textView;
    FirebaseUser user;
    Button lessons;
    Button specialT;
    Button RTB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://manigut-madayit-default-rtdb.europe-west1.firebasedatabase.app");
        //DatabaseReference myRef = database.getReference("message");
        auth = FirebaseAuth.getInstance();
        events = findViewById(R.id.specDay);
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.loggedIn);
        specialT = findViewById(R.id.special);
        branch = findViewById(R.id.MyBranch);
        lessons = findViewById(R.id.lessonPlans);
        substitute = findViewById(R.id.sub);
        RTB = findViewById(R.id.RealTimeDataBase);
        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LookSubs.class);
                startActivity(intent);
                finish();
            }
        });
        /*events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Events.class);
                startActivity(intent);
                finish();
            }
        });*/
        specialT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SpecialTeams.class);
                startActivity(intent);
                finish();
            }
        });
        RTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abc","hello");
                Intent intent = new Intent(getApplicationContext(), RealTimeDataBase.class);
                Log.d("bca","hi");
                startActivity(intent);
                Log.d("bcaddd","hii");
                finish();
                //myRef.setValue("hello");
                //Log.d("DB","sent");
            }
        });
        lessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TextToSpeech.class);
                startActivity(intent);
                finish();
            }
        });
        substitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abc","hello");

                Intent intent = new Intent(getApplicationContext(), FBStorage.class);
                Log.d("bca","hi");
                startActivity(intent);
                finish();
            }
        });



    }
}