package com.example.felix.weatherapp3;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends Activity implements View.OnClickListener
{
    Button buttonSuche;
    AutoCompleteTextView editTextPattern;
    TextView editTextTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSuche = (Button) findViewById(R.id.buttonSuche);
        buttonSuche.setOnClickListener(this);

        editTextPattern = (AutoCompleteTextView) findViewById(R.id.editTextPattern);
        editTextTime = (TextView) findViewById(R.id.editTextTime);


        editTextPattern.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                createCityList();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        //createCityList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_aktualisieren)
        {
            String location = editTextPattern.getText().toString();
            String url = "http://api.openweathermap.org/data/2.5/find?q="+location+"&type=accurate&mode=json";
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate = simpleDateFormat.format(calendar.getTime());

            if(!(location.contains(" ")))
            {
                WeatherServiceAsync task = new WeatherServiceAsync(this);
                editTextTime.setText("Zuletzt akualisiert: " + formattedDate);
                task.execute(url);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Unerlaubtes Zeichen!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void setCity(String city)
    {
        TextView viewCity = (TextView) findViewById(R.id.labelCityValue);
        viewCity.setText(city);
    }

    public void setCountry(String country)
    {
        TextView viewCountry = (TextView) findViewById(R.id.labelCountryValue);
        viewCountry.setText(country);
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

            if(!(location.contains(" ")))
            {
                WeatherServiceAsync task = new WeatherServiceAsync(this);
                task.execute(url);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Unerlaubtes Zeichen!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void createCityList()
    {
        String url;
        String location = editTextPattern.getText().toString();

        if(!(location.contains(" ")))
        {
            url = "http://api.openweathermap.org/data/2.5/find?q="+location+"&type=accurate&mode=json";
            CityServiceAsync cityTask = new CityServiceAsync(this);
            cityTask.execute(url);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Unerlaubtes Zeichen!", Toast.LENGTH_SHORT).show();
        }
    }
}
