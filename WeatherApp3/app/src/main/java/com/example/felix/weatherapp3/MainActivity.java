package com.example.felix.weatherapp3;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends Activity implements View.OnClickListener
{
    Button buttonSuche;
    AutoCompleteTextView editTextPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSuche = (Button) findViewById(R.id.buttonSuche);
        buttonSuche.setOnClickListener(this);

        editTextPattern = (AutoCompleteTextView) findViewById(R.id.editTextPattern);

        /*
        editTextPattern.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(charSequence.length() > 4)
                {
                    createCityList();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        */

        createCityList();
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

    public void createCityList()
    {
        String url;
        String location = editTextPattern.getText().toString();
        if(location.length()!= 0)
        {
            url = "http://api.openweathermap.org/data/2.5/find?q="+location+"&type=accurate&mode=json";
            CityServiceAsync cityTask = new CityServiceAsync(this);
            cityTask.execute(url);
        }
        else
        {
            url = "http://api.openweathermap.org/data/2.5/find?q=London&type=accurate&mode=json";
            CityServiceAsync cityTask = new CityServiceAsync(this);
            cityTask.execute(url);
        }

    }
}
