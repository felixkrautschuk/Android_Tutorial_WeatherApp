package com.example.felix.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrieveWeatherData();
    }


    public void setCity(String city)
    {
        TextView viewCity = (TextView) findViewById(R.id.labelCityValue);
        viewCity.setText(city);
    }


    public void setDescription(String description)
    {
        TextView viewDescription = (TextView) findViewById(R.id.labelDescriptionValue);
        viewDescription.setText(description);
    }


    public void setTemperature(double temperature)
    {
        TextView viewTemperature = (TextView) findViewById(R.id.labelTemperatureValue);
        DecimalFormat df = new DecimalFormat("###.##");

        viewTemperature.setText((df.format(temperature)).toString());
    }

    public void setHumidity(double humidity)
    {
        TextView viewHumidity = (TextView) findViewById(R.id.labelHumidityValue);
        DecimalFormat df = new DecimalFormat("###.##");

        viewHumidity.setText((df.format(humidity)).toString());
    }

    public void setPressure(double pressure)
    {
        TextView viewPressure = (TextView) findViewById(R.id.labelPressureValue);
        DecimalFormat df = new DecimalFormat("###.##");

        viewPressure.setText((df.format(pressure)).toString());
    }

    private void retrieveWeatherData()
    {
        String url = "http://api.openweathermap.org/data/2.5/weather?id=2859474";

        WeatherServiceAsync task = new WeatherServiceAsync(this);
        task.execute(url);
    }
}
