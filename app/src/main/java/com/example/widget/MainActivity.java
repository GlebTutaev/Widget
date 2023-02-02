package com.example.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

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
            @Override
            public void run() {
                final JSONObject json = ConnectFetch.getJSON(MainActivity.this, city);
                if(json == null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, city +
                                    "-информация не надйнеа", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
                super.run();
            }
        }.start();
    }

    private void renderWeather(JSONObject json) {
        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            ((TextView)findViewById(R.id.weather)).setText(details.getString("description").toUpperCase(Locale.ROOT));
        }catch (Exception e){
            Log.e("SimpleWeather", "One more fields not found in the JSON data");
        }
    }


}







