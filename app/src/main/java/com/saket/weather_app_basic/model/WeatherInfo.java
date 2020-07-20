package com.saket.weather_app_basic.model;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {

    @SerializedName("weather_state_name")
    private final String weather_name;
    @SerializedName("wind_direction_compass")
    private final String wind_direction;
    private final String min_temp;
    private final String max_temp;
    private final String wind_speed;
    private final String humidity;

    public WeatherInfo(String weather_name, String wind_direction, String min_temp, String max_temp, String wind_speed, String humidity) {
        this.weather_name = weather_name;
        this.wind_direction = wind_direction;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.wind_speed = wind_speed;
        this.humidity = humidity;
    }

    public String getWeather_name() {
        return weather_name;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public String getHumidity() {
        return humidity;
    }
}
