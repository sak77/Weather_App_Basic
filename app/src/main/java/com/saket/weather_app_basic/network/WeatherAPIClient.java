package com.saket.weather_app_basic.network;

import com.saket.weather_app_basic.model.City;
import com.saket.weather_app_basic.model.WeatherInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This class provides instance of retrofit class.
 * Also it contains an interface which defines the API endpoints for the weather service
 */
public class WeatherAPIClient {


    private static Retrofit retrofit;

    //Singleton class
    private WeatherAPIClient() {
    }

    public static WeatherAPIService getWeatherAPIService() {
        //Create only one instance of retrofit as it is expensive.
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.metaweather.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(WeatherAPIService.class);
    }

    //A simple interface that provides API endpoints for meta weather service
    public interface WeatherAPIService {
        @GET("location/search/")
        Call<City[]> getCityWoeId(@Query("query") String cityName);

        @GET("location/{woeid}/{date}/")
        Call<WeatherInfo[]> getCityWeatherInfoByDate(@Path("woeid") String woeid, @Path("date") String date);
    }
}
