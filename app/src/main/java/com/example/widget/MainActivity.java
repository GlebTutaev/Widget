package com.example.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        updateWeatherData("Orenburg");



    }

    private void updateWeatherData(final String city) {
        new Thread(){
            public void run(){
                final JSONObject json = ConnectFetch.getJSON(MainActivity.this, city);
                if(json == null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,
                                    city + "-информация не найдена",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            { renderWeather(json);
                        }
                    });
                }
            }
        }.start();


        private void renderWeather(JSONObject json) {

        }
 }




