package com.example.felix.weatherapp2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends Activity implements View.OnClickListener
{
    Button buttonSuche;
    EditText editTextPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSuche = (Button) findViewById(R.id.buttonSuche);
        buttonSuche.setOnClickListener(this);

        editTextPattern = (EditText) findViewById(R.id.editTextPattern);
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
        DecimalFormat df = new DecimalFormat("###.#");

        viewTemperature.setText((df.format(temperature)).toString());
    }

    public void setHumidity(double humidity)
    {
        TextView viewHumidity = (TextView) findViewById(R.id.labelHumidityValue);
        DecimalFormat df = new DecimalFormat("###.#");

        viewHumidity.setText((df.format(humidity)).toString());
    }

    public void setPressure(double pressure)
    {
        TextView viewPressure = (TextView) findViewById(R.id.labelPressureValue);
        DecimalFormat df = new DecimalFormat("###.#");

        viewPressure.setText((df.format(pressure)).toString());
    }

    public void onClick(View view)
    {
        if(view.getId() == R.id.buttonSuche)
        {
            String location = editTextPattern.getText().toString();
            String url = "http://api.openweathermap.org/data/2.5/find?q="+location+"&type=accurate&mode=json";

            WeatherServiceAsync task = new WeatherServiceAsync(this);
            task.execute(url);
        }
    }
}
