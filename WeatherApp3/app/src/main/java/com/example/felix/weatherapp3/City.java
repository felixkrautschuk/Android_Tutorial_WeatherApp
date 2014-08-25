package com.example.felix.weatherapp3;

public class City
{

    private String id;
    private String name;
    private String country;

    public City() {}

    public City(String name, String country)
    {
        this.name = name;
        this.country = country;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getCountry()
    {
        return this.country;
    }

    @Override
    public String toString()
    {
        return name + "," + country;
    }
}