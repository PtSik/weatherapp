package com.example.weatherapp;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.*;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    WeatherApiConnection weatherApiConnection = new WeatherApiConnection(client, MainActivity.this);

    private Button mButton;
    private EditText mEdit;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdit = (EditText)findViewById(R.id.getLocation);
        mButton = (Button) findViewById(R.id.updateData);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherApiConnection.getUpdate(mEdit.getText().toString().trim(), new ResponseListener() {
                    @Override
                    public void onError(String message) {
                        ((TextView) findViewById(R.id.currentLocation)).setText(message);
                    }

                    @Override
                    public void onResponse(WeatherData weatherData) {
                        updateData(weatherData);
                    }
                });

                mEdit.setText("");
            }
        });

    }

    protected void updateData(WeatherData data) {
        ((TextView) findViewById(R.id.currentLocation)).setText(data.name);
        ((TextView) findViewById(R.id.temp)).setText(data.temp);
        ((TextView) findViewById(R.id.tempMin)).setText(data.tempMin);
        ((TextView) findViewById(R.id.tempMax)).setText(data.tempMax);
        ((TextView) findViewById(R.id.pressure)).setText(data.pressure);
        ((TextView) findViewById(R.id.humidity)).setText(data.humidity);
        ((TextView) findViewById(R.id.wind)).setText(data.wind);
        ((TextView) findViewById(R.id.lastUpdate)).setText(data.lastUpdate);
        ((TextView) findViewById(R.id.sky)).setText(data.sky);
        ((TextView) findViewById(R.id.sunrise)).setText(data.sunRise);
        ((TextView) findViewById(R.id.sunset)).setText(data.sunSet);
    }
}