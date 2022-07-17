package com.example.weatherapp;

public interface ResponseListener {
    void onError(String message);

    void onResponse(WeatherData weatherData);
}
