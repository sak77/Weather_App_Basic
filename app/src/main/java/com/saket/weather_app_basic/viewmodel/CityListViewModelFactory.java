package com.saket.weather_app_basic.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.saket.weather_app_basic.repository.CityWeatherRepo;

/**
 * The viewmodel factory class is a wrapper class that abstracts the
 * creation of viewmodel by returning new instance of viewmodel based upon supplied input values
 */
public class CityListViewModelFactory implements ViewModelProvider.Factory {

    public CityListViewModel get(Fragment fragment) {
        return new ViewModelProvider(fragment, this).get(CityListViewModel.class);
    }

    public CityListViewModel get(FragmentActivity activity) {
        //return new ViewModelProvider(activity, this).get(CityListViewModel.class);
        return new ViewModelProvider(activity, this).get(CityListViewModel.class);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == CityListViewModel.class) {
            CityWeatherRepo cityWeatherRepo = new CityWeatherRepo();
            return (T) new CityListViewModel(cityWeatherRepo);
        } else {
            throw new RuntimeException("Unable to create " + modelClass.getName());
        }
    }
}
