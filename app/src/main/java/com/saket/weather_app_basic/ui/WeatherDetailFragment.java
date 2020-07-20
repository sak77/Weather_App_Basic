package com.saket.weather_app_basic.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saket.weather_app_basic.databinding.FragmentWeatherDetailsBinding;
import com.saket.weather_app_basic.model.City;
import com.saket.weather_app_basic.viewmodel.CityListViewModel;
import com.saket.weather_app_basic.viewmodel.CityListViewModelFactory;

/**
 * Fragment displays weather details for selected city
 */
public class WeatherDetailFragment extends Fragment {

    CityListViewModel cityListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View root = inflater.inflate(R.layout.fragment_weather_details, container, false);
        CityListViewModelFactory cityListViewModelFactory = new CityListViewModelFactory();

        //Since we want to share City data between CityListfragment & WeatherDetailFragment, we
        //pass the parent activity as the viewmodelstoreowner when creating instance of viewmodel..
        //Hence this cityListViewModel is the same instance across all fragments of the activity.
        cityListViewModel = cityListViewModelFactory.get(requireActivity());
        City currCity = cityListViewModel.mCurrentSelectedCity.getValue();

        FragmentWeatherDetailsBinding binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false);
        binding.setCity(currCity);

        //Log.v("CityListFragment", "WeatherInfoDetail onCreateView called");
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //reset selected city
        //Log.v("CityListFragment", "WeatherInfoDetail onDestroyView called");
        cityListViewModel.mCurrentSelectedCity.setValue(null);
    }
}
