package com.example.widget;

import static com.example.widget.ConnectFetch.getIconUrl;
import static com.example.widget.StaticWeatherAnalyze.getCityField;
import static com.example.widget.StaticWeatherAnalyze.getDetailsField;
import static com.example.widget.StaticWeatherAnalyze.getLastUpdateTime;
import static com.example.widget.StaticWeatherAnalyze.getTemperatureField;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

            /*JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            ((TextView)findViewById(R.id.weather)).setText(details.getString("description").toUpperCase(Locale.ROOT));*/
            Glide
                    .with(this)
                    .load(getIconUrl(json))
                    .into((ImageView) findViewById(R.id.weather_icon));
            ((TextView)findViewById(R.id.city_field)).setText(getCityField(json));
            ((TextView)findViewById(R.id.updated_field)).setText(getLastUpdateTime(json));
            ((TextView)findViewById(R.id.details_field)).setText(getDetailsField(json));
            ((TextView)findViewById(R.id.current_temperature_field)).setText(getTemperatureField(json));
        }catch (Exception e){
            Log.e("SimpleWeather", "One more fields not found in the JSON data");
        }
    }


}







