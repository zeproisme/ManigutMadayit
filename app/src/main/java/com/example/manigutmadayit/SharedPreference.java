package com.example.manigutmadayit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SharedPreference extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button applyTextButton;
    private Button saveButton;
    private Button back;
    public static final String SHARED_PREFS = "mypref";
    public static final String TEXT = "text";

    private  String text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_preference);
        editText = findViewById(R.id.editTextPreference);
        textView = findViewById(R.id.textViewPref);
        applyTextButton = findViewById(R.id.applypref);
        saveButton = findViewById(R.id.savepref);
        back = findViewById(R.id.backbutton);

        applyTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(editText.getText().toString());
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });
        loadData();
        updateViews();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, textView.getText().toString());
        editor.apply();
        Toast.makeText(this,"text saved!", Toast.LENGTH_SHORT).show();

    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
    }
    public void updateViews(){
        textView.setText(text);

    }
}
