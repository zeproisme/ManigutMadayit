/*package com.example.manigutmadayit;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EventDesc extends AppCompatActivity {
    EditText etName;
    EditText etDesc;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_description);
        button = findViewById(R.id.saveButton);
        etName = findViewById(R.id.uploadTopic);
        etDesc = findViewById(R.id.uploadDesc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                Intent intent = new Intent(getApplicationContext(),Events.class);
                intent.putExtra("EXTRA_NAME", name);
                intent.putExtra("EXTRA_DESC",desc);
                startActivity(intent);
                finish();

            }
        });

    }
}*/


