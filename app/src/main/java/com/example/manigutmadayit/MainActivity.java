package com.example.manigutmadayit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    Button branch;
    Button substitute;
    Button RTB;
    TextView textView;
    FirebaseUser user;
    Button lessons;
    Button sharedprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        RTB = findViewById(R.id.RealTimeDatabase);
        textView = findViewById(R.id.loggedIn);
        sharedprefs = findViewById(R.id.SharedPrefs);
        branch = findViewById(R.id.MyBranch);
        lessons = findViewById(R.id.lessonPlans);
        substitute = findViewById(R.id.sub);
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
        RTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RealTimeDataBase.class);
                startActivity(intent);
                finish();
            }
        });
        sharedprefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SharedPreference.class);
                startActivity(intent);
                finish();
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