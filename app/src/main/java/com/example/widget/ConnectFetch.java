package com.example.widget;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectFetch {
    private static final String OPEN_WEATHER_MAP_API = "https://api.openweathermap.org/data/2.5/weather?q=Orenburg&appid={77f3ccd7e92eb3a02bebbe5f64e05415}&units=metric";

    private static final  String OPEN_WEATHER_ICON = "http://openweathermap.org/img/wn/10d@2x.png";
    public static JSONObject getJSON(Context context, String city){

        try{
            String urlString = String.format(OPEN_WEATHER_MAP_API,
                    city,context.getString(R.string.weather_api_key));
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.addRequestProperty("x-api-key", context.getString(R.string.weather_api_key));
            BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());

            if(data.getInt("cod") != 200){
                return null;
            }
            return data;

        }catch(Exception e){
            return null;
        }
    }

    public static String getIconUrl(JSONObject json)
    {
        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            String icon = details.getString("icon");
            return String.format(OPEN_WEATHER_ICON, icon);
        }
        catch (JSONException e) {
            {e.printStackTrace();;
            }
            return "";
        }
    }
}
