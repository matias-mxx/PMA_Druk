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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class DiceRoller extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button button;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roller);

        textView = findViewById(R.id.rollText);
        button = findViewById(R.id.buttonRoll);
        imageView = findViewById(R.id.dicePic);
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
                Random random = new Random();
                int score = random.nextInt(6) + 1;
                textView.setText(String.valueOf(score));

                switch (score){
                    case 1:
                        imageView.setImageResource(R.drawable.dice1);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.dice2);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.dice3);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.dice4);
                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.dice5);
                        break;
                    case 6:
                        imageView.setImageResource(R.drawable.dice6);
                        break;
                }
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