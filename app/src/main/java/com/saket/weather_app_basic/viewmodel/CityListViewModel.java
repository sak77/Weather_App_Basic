package com.saket.weather_app_basic.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.saket.weather_app_basic.model.City;
import com.saket.weather_app_basic.repository.CityWeatherRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * View model is the glue between the UI and the data in the repository.
 * It's data can survive configuration changes.
 * It can hold livedata which in turn can be bound to the Ui
 */
public class CityListViewModel extends ViewModel {

    private List<City> cityInfoList;

    public MutableLiveData<List<City>> liveCityInfoList;

    CityWeatherRepo mCityWeatherRepo;

    public MutableLiveData<City> mCurrentSelectedCity = new MutableLiveData<>();

    private City previous_city = null;

    //A flag to tell if app should fetch city list info.
    // Make sure to set default selected city value to null so that it updates the value of this live
    // data the first time app launches...
    public LiveData<Boolean> shouldFetchCityInfo = Transformations.map(mCurrentSelectedCity, curr_city -> {
        //User lands on city list for first time - yes
        if (previous_city == null && curr_city == null) {
            return true;
        }

        //For any other case simply update previous city but do not fetch data
        previous_city = curr_city;
        return false;
    });

    //Manual injection of Repo class
    public CityListViewModel(CityWeatherRepo cityWeatherRepo) {
        Log.v("CityListFragment", "Viewmodel created");
        this.mCityWeatherRepo = cityWeatherRepo;
        cityInfoList = new ArrayList<>();
        liveCityInfoList = new MutableLiveData<>();
        mCurrentSelectedCity.setValue(null);    //Set default city to null so that it can trigger fetch city data
    }

    public void getCityWeatherInfo() {
        String[] arrCityNames = new String[]{"Gothenburg", "Stockholm", "Mountain View", "London", "New York", "Berlin"};

        //2 steps - 1 get woeid for city. 2 get tomorrow's weather for each woeid.
        for (String currCityName : arrCityNames) {
            mCityWeatherRepo.getCityDetails(currCityName, woeid -> {
                City city = new City(currCityName, woeid);
                //Use woeid to geCityListViewModelt city weather info
                mCityWeatherRepo.getCityWeatherDetails(city.getWoeid(), weatherInfo -> {
                    city.setWeatherInfo(weatherInfo);
                    cityInfoList.add(city);
                    liveCityInfoList.setValue(cityInfoList);
                });
            });
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.v("CityListFragment", "ViewModel onCleared()");
    }
}
