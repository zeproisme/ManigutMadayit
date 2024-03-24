/*package com.example.manigutmadayit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Events extends AppCompatActivity {
   *//* FloatingActionButton fab;
    ListView events;
    Bundle intentExtras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        events = (ListView) findViewById(R.id.eventListView);
        fab = findViewById(R.id.fab);

        ArrayList<String> eventArrayList = new ArrayList<>();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventDesc.class);
                startActivity(intent);
                finish();
            }
        });

        intentExtras = getIntent().getExtras();
        if(intentExtras != null){
            String name = intentExtras.getString("EXTRA_NAME");
            String desc = intentExtras.getString("EXTRA_DESC");
            eventArrayList.add(name);
            eventArrayList.add(desc);
        }


        //take name from EventDesc, add it to eventArrayList


    }
}*/
