package com.example.felix.weatherapp3;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WeatherServiceAsync extends AsyncTask<String, Void, String>
{
    //Herangehensweise:
    //  1. URL-Service aufrufen, welcher die Wetterdaten zurückgibt
    //  2. es wird ein JSON-Objekt zurückgegeben, wo die relevanten Daten
    //     für 'Temperature', 'Humidity' und 'Pressure' drinstehen --> JSON-Objekt analysieren (parsen)
    //     dabei muss die Hierarchie des zurückgeliefertten JSON-Dokumentes beachtet werden,
    //     also ob ein Element direkt angesprochen werden kann, ob es als Objekt oder als Array vorliegt!
    //  3. Konvertierungen vornehmen (Temperatur von Kelvin in Celsius,...)
    //  4. TextView Widgets der MainActivity mit den analysierten Daten befüllen

    private final MainActivity MainActivity;


    //Konstruktor nimmt unsere MainActivity entgegen
    //somit können wir sie später dazu benutzen um die Wetter-Werte die wir erhalten auszugeben
    public WeatherServiceAsync(MainActivity mainActivity)
    {
        this.MainActivity = mainActivity;
    }


    //diese Wetter-Service-Methode wird die Activity in der onPostExecute bekanntmachen
    protected String doInBackground(String... urls)
    {
        String response = "";

        //Schleife durch die urls (es sollte eigentlich nur eine geben!!!)
        //fordert die Informationen hinter der URL (also die Daten) an welche übergeben wurde
        for(String url: urls)
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            try
            {
                //Wetterdaten über http anfordern
                HttpResponse execute = httpClient.execute(httpGet);

                //den Inhalt der Antwort speichern, welcher zurückgegeben wird sobald die Antwort zurückkommt
                //JSON-Objekt!!!
                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                //Antwort-String aufbauen (dieser wird später an die onPostExecute übergeben
                while ((s = buffer.readLine()) != null)
                {
                    response += s;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return response;
    }

    public void onPostExecute(String result)
    {
        try
        {
            JSONObject jsonResult = new JSONObject(result);

            //JSON-Objekt analysieren und gewünschte Werte auslesen
            //STRUKTUR des JSON Dokumetnes beachten!!!


            //Ort auslesen
            String city = jsonResult.getJSONArray("list").getJSONObject(0).getString("name");

            //Beschreibung auslesen
            String description = jsonResult.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");

            //Temperatur auslesen und von Kelvin in Celsius umrechnen
            double temperature = jsonResult.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp");
            temperature = convertTemperatureToCelsius(temperature);

            //Luftfeuchtigkeit auslesen
            double humidity = jsonResult.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("humidity");

            //Luftdruck auslesen
            double pressure = jsonResult.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("pressure");



            //TextViews mit ausgelesenen Daten füllen
            this.MainActivity.setCity(city);
            this.MainActivity.setDescription(description);
            this.MainActivity.setTemperature(temperature);
            this.MainActivity.setHumidity(humidity);
            this.MainActivity.setPressure(pressure);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private double convertTemperatureToCelsius(double temperature)
    {
        return temperature - 273.15;
    }
}
