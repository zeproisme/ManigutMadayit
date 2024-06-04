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

public class Branch extends AppCompatActivity {
    private TextView textView;
    private EditText editTextName, editTextLesson, editTextEquip, editTextRetEquip;
    private Button applyTextButton;
    private Button saveButton;
    private Button back;
    public static final String SHARED_PREFS = "mypref";
    public static final String NAME = "name";
    public static final String LESSON = "lesson";
    public static final String BR_EQUIP = "bring equip";
    public static final String RET_EQUIP = "return equip";


    private  String name;
    private String lesson;
    private String brEquip;
    private String retEquip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch);
        editTextName = findViewById(R.id.editTextName);
        editTextLesson = findViewById(R.id.editTextLesson);
        editTextEquip = findViewById(R.id.editTextEquip);
        editTextRetEquip = findViewById(R.id.editTextRetEquip);
        textView = findViewById(R.id.mokedName);
        saveButton = findViewById(R.id.savepref);
        back = findViewById(R.id.backbutton);

        /*applyTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(editText.getText().toString());
            }
        });*/
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setText(editTextName.getText().toString());//probably useless
                editTextLesson.setText(editTextLesson.getText().toString());
                editTextEquip.setText(editTextEquip.getText().toString());
                editTextRetEquip.setText(editTextRetEquip.getText().toString());
                saveData();

            }
        });
        loadData();
        updateViews();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NAME, editTextName.getText().toString());
        editor.putString(LESSON, editTextLesson.getText().toString());
        editor.putString(BR_EQUIP, editTextEquip.getText().toString());
        editor.putString(RET_EQUIP, editTextRetEquip.getText().toString());
        editor.apply();
        Toast.makeText(this,"text saved!", Toast.LENGTH_SHORT).show();

    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        name = sharedPreferences.getString(NAME, "");
        lesson = sharedPreferences.getString(LESSON, "");
        brEquip = sharedPreferences.getString(BR_EQUIP, "");
        retEquip = sharedPreferences.getString(RET_EQUIP, "");
    }
    public void updateViews(){
        editTextName.setText(name);
        editTextLesson.setText(lesson);
        editTextEquip.setText(brEquip);
        editTextRetEquip.setText(retEquip);

    }

}
