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

public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    Button events;
    Button logout;
    Button branch;
    Button admin;
    Button substitute;
    TextView textView;
    FirebaseUser user;
    Button lessons;
    Button specialT;
    Button RTB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://manigut-madayit-default-rtdb.europe-west1.firebasedatabase.app");
        //DatabaseReference myRef = database.getReference("message");
        auth = FirebaseAuth.getInstance();
        events = findViewById(R.id.specDay);
        admin = findViewById(R.id.admins);
        logout = findViewById(R.id.logout);
        textView = findViewById(R.id.loggedIn);
        specialT = findViewById(R.id.special);
        branch = findViewById(R.id.MyBranch);
        lessons = findViewById(R.id.lessonPlans);
        substitute = findViewById(R.id.sub);
        RTB = findViewById(R.id.RealTimeDataBase);
        user = auth.getCurrentUser();
        Button showNotificationsButton = findViewById(R.id.showNotifications);
        CheckAdmin.checkAdminAndSetVisibility(this, admin);
        showNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showNotificationList();
            }
        });


        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }
        logout.setOnClickListener(new View.OnClickListener() {
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
                /*public void changeBranch(){
                    String branchName = user.getBranch();


                        switch(branchName) {
                        case "a":
                            System.out.println("Input is 'a'");
                            break;
                        case "b":
                            System.out.println("Input is 'b'");
                            break;
                        case "c":
                            System.out.println("Input is 'c'");
                            break;
                        case "d":
                            System.out.println("Input is 'd'");
                            break;
                        case "e":
                            System.out.println("Input is 'e'");
                            break;
                        case "f":
                            System.out.println("Input is 'f'");
                            break;
                        case "g":
                            System.out.println("Input is 'g'");
                            break;
                        case "h":
                            System.out.println("Input is 'h'");
                            break;
                        case "i":
                            System.out.println("Input is 'i'");
                            break;
                        case "j":
                            System.out.println("Input is 'j'");
                            break;
                        default:
                            System.out.println("Input not recognized");
                            }
                    }*/





                Intent intent = new Intent(getApplicationContext(), Branch.class);
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
                Intent intent = new Intent(getApplicationContext(), Events.class);
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

                Intent intent = new Intent(getApplicationContext(), LessonPlans.class);
                startActivity(intent);
                finish();
            }
        });
        substitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abc","hello");

                Intent intent = new Intent(getApplicationContext(), LessonPlans.class);
                Log.d("bca","hi");
                startActivity(intent);
                finish();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abc","hello");

                Intent intent = new Intent(getApplicationContext(), AdminUpdate.class);
                Log.d("bca","hi");
                startActivity(intent);
                finish();
            }
        });






    }

}