package com.example.manigutmadayit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TextToSpeech extends AppCompatActivity {
    private android.speech.tts.TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_to_speech);
        mEditText = findViewById(R.id.edit_txt);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        mButtonSpeak = findViewById(R.id.button_speak);
        back = findViewById(R.id.backbutton);
        mTTS = new android.speech.tts.TextToSpeech(this, new android.speech.tts.TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == android.speech.tts.TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if(result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language not supported");
                    }else{
                        mButtonSpeak.setEnabled(true);
                    }
                }else{
                    Log.e("TTS","Initialization failed");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });



    }



    private void speak(){
        String text = mEditText.getText().toString();
        float pitch = (float)mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float)mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) pitch = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text, android.speech.tts.TextToSpeech.QUEUE_FLUSH,null);

    }
    @Override
    protected void onDestroy() {
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}
