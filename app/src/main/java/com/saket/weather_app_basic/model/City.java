package com.saket.weather_app_basic.model;

/**
 * Data class to save City data
 */
public class City {

    private final String title;
    private final String woeid;
    private WeatherInfo weatherInfo;

    public City(String title, String woeid) {
        this.title = title;
        this.woeid = woeid;
    }

    public String getTitle() {
        return title;
    }

    public String getWoeid() {
        return woeid;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
}
