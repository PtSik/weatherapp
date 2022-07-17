package com.example.weatherapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherData {
    protected String temp;
    protected String tempMin;
    protected String tempMax;
    protected String pressure;
    protected String humidity;
    protected String wind;
    protected String lastUpdate;
    protected String name;
    protected String sky;
    protected String sunRise;
    protected String sunSet;

    public WeatherData(String myResponse) throws JSONException {
        JSONObject jsonObject = new JSONObject(myResponse);
        this.temp = jsonObject.getJSONObject("main").getString("temp") + " °C";
        this.tempMin = jsonObject.getJSONObject("main").getString("temp_min") + " °C";
        this.tempMax = jsonObject.getJSONObject("main").getString("temp_max") + " °C";
        this.pressure = jsonObject.getJSONObject("main").getString("pressure");
        this.humidity = jsonObject.getJSONObject("main").getString("humidity") + " %";
        this.wind = jsonObject.getJSONObject("wind").getString("speed") + " km/h";

        long update = jsonObject.getLong("dt");
        this.lastUpdate = "Last update: " + (new SimpleDateFormat
                ("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format
                (new Date(update * 1000)));
        this.name = jsonObject.getString("name");

        this.sky = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

        this.sunRise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                .format(new Date(jsonObject.getJSONObject("sys").getLong("sunrise") * 1000));

        this.sunSet = new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                .format(new Date(jsonObject.getJSONObject("sys").getLong("sunset") * 1000));
    }
}
