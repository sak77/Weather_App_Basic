package com.saket.weather_app_basic.repository;

import com.saket.weather_app_basic.model.City;
import com.saket.weather_app_basic.model.WeatherInfo;
import com.saket.weather_app_basic.network.WeatherAPIClient;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The repository class does the actual work of getting/submitting data.
 * <p>
 * It can communicate with the viewmodel via liveData or by callback method as parameter.
 */
public class CityWeatherRepo {

    //We pass a consumer FI as a callback..
    public void getCityDetails(String cityName, Consumer<String> callback) {
        Call<City[]> cityDetailsCall = WeatherAPIClient.getWeatherAPIService().getCityWoeId(cityName);
        cityDetailsCall.enqueue(new Callback<City[]>() {
            @Override
            public void onResponse(Call<City[]> call, Response<City[]> response) {
                if (response.isSuccessful()) {
                    City[] city = response.body();
                    assert city != null;
                    //System.out.println("City name - " + city[0].getTitle());
                    callback.accept(city[0].getWoeid());
                }
            }

            @Override
            public void onFailure(Call<City[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void getCityWeatherDetails(String woeid, Consumer<WeatherInfo> weatherInfoConsumer) {
        //get tomorrow's date
        String tomorrow = "2020/07/15"; //stub it for now...

        //Make api call using weatherapiclient class
        Call<WeatherInfo[]> callWeatherDetails = WeatherAPIClient.getWeatherAPIService().getCityWeatherInfoByDate(woeid, tomorrow);
        callWeatherDetails.enqueue(new Callback<WeatherInfo[]>() {
            @Override
            public void onResponse(Call<WeatherInfo[]> call, Response<WeatherInfo[]> response) {
                if (response.isSuccessful()) {
                    WeatherInfo[] arrWeatherDetails = response.body();
                    assert arrWeatherDetails != null;
                    //Considering the first item in the array is the latest weather info...
                    if (arrWeatherDetails.length > 0)
                        weatherInfoConsumer.accept(arrWeatherDetails[0]);
                }
            }

            @Override
            public void onFailure(Call<WeatherInfo[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
