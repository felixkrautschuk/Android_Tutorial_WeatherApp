package com.example.felix.weatherapp3;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityServiceAsync extends AsyncTask<String, Void, List<City>>
{
    private final MainActivity MainActivity;

    public CityServiceAsync(MainActivity mainActivity)
    {
        this.MainActivity = mainActivity;
    }

    protected List<City> doInBackground(String... urls)
    {
        Gson gson = new Gson();
        List<City> cityList = new ArrayList<City>();

        for(String url: urls)
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            try
            {
                HttpResponse execute = httpClient.execute(httpGet);

                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                JsonObject json = gson.fromJson(buffer, JsonObject.class);
                JsonArray list = json.getAsJsonArray("list");
                int count = json.get("count").getAsInt();
                System.out.println(count);

                for(int i = 0; i < list.size(); i++)
                {
                    JsonObject cityObject = list.get(i).getAsJsonObject();
                    String cityName = cityObject.get("name").getAsString();
                    System.out.println(cityName);
                    JsonObject countryObject = list.get(i).getAsJsonObject().getAsJsonObject("sys");
                    String countryName = countryObject.get("country").getAsString();
                    System.out.println(countryName);

                    City city = new City(cityName, countryName);
                    cityList.add(city);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return cityList;
    }

    public void onPostExecute(List<City> cities)
    {
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(this.MainActivity, android.R.layout.simple_dropdown_item_1line, cities);
        this.MainActivity.editTextPattern.setThreshold(1);
        this.MainActivity.editTextPattern.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }
}
