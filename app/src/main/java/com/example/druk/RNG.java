package com.example.druk;

import static android.hardware.Sensor.TYPE_LIGHT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RNG extends AppCompatActivity {

    EditText editMin, editMax;
    TextView textView;
    Button button;
    Random random;
    int min, max, score;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rng);

        random = new Random();
        editMin = findViewById(R.id.minNumber);
        editMax = findViewById(R.id.maxNumber);
        textView = findViewById(R.id.rollText);
        button = findViewById(R.id.buttonGenerate);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        if (lightSensor == null){
            Toast.makeText(this, "Ambient light sensor not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                if (value < 50){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                min = Integer.parseInt(editMin.getText().toString());
                max = Integer.parseInt(editMax.getText().toString());
                score = random.nextInt((max - min) + 1) + min;
                textView.setText(String.valueOf("" + score));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }
}