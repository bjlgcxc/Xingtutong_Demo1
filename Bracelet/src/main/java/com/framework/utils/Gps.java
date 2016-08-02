package com.framework.utils;

public class Gps
{
    private double latitude;
    private double longitude;

    public Gps(double lat, double lon)
    {
        latitude = lat;
        longitude = lon;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }
}