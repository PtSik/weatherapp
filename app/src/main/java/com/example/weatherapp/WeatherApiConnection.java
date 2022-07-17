package com.example.weatherapp;

import android.util.Log;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class WeatherApiConnection {

    OkHttpClient client;
    MainActivity mainActivity;
    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";

    public WeatherApiConnection(OkHttpClient client, MainActivity mainActivity) {
        this.client = client;
        this.mainActivity = mainActivity;
    }

    protected void getUpdate(String city, ResponseListener responseListener) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&lang=en&appid=" + API;
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mainActivity.runOnUiThread(() -> {
                    String error = "No connection";
                    responseListener.onError(error);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = Objects.requireNonNull(response.body()).string();

                    mainActivity.runOnUiThread(() -> {
                        WeatherData weatherData = null;
                        try {
                            weatherData = new WeatherData(myResponse);
                            responseListener.onResponse(weatherData);
                        } catch (JSONException e) {
                            responseListener.onError("Unknown error");
                        }
                        });
                } else {
                    mainActivity.runOnUiThread(() -> {
                        String error = "Wrong city";
                        responseListener.onError(error);
                    });
                }
            }
        });
    }
}
