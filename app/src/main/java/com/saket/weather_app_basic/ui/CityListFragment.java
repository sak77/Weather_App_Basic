package com.saket.weather_app_basic.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.saket.weather_app_basic.R;
import com.saket.weather_app_basic.databinding.FragmentCitylistBinding;
import com.saket.weather_app_basic.viewmodel.CityListViewModel;
import com.saket.weather_app_basic.viewmodel.CityListViewModelFactory;

import java.util.ArrayList;

/**
 * Displays list of cities via a recyclerview
 * Shows some basic info about tomorrows weather for each city
 * Has a corresponding viewmodel class which handles data and navigation
 *
 * CityListFragment Learnings -
 * On
 */
public class CityListFragment extends Fragment {


    /*
    Note: If i need to pass any arguments to the fragment, then i can create a
    simple factory method which takes these arguments and locally creates a new fragment instance,
    then it adds them as arguments to the new fragment instance. Finally it returns the instance. Or
    i can use the FragmentFactory class. However, here there is no such requirement, since i am not
    passing any values to the instance, so i feel there is no need to create such a method.
     */

    FragmentCitylistBinding binding;

    private static final String TAG = "CityListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentCitylistBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        Log.v(TAG, "onCreateView");

        //Since we want to share City data between CityListfragment & WeatherDetailFragment, we
        //pass the parent activity as the viewmodelstoreowner when creating instance of viewmodel..
        CityListViewModel viewModel = new CityListViewModelFactory().get(requireActivity());
        //Keeping API requests inside if condition ensures no duplicate calls are made on device rotation.

        /*
        There is another issue here. Using FragmentTransaction.replace(), when WeatherDetailFragment is
        popped off the activity backstack, it recreates CityListFragment. In that case getCityWeatherInfo()
        is invoked again. This appends more duplicate records to the recyclerview. To fix this issue,
        there are 2 possible ways -
        1. Initialize cityInfoList in CityListViewModel's getCityWeatherInfo(). But this reloads the
        citylist recyclerview each time the user navigates back to this fragment. Thus giving a bad
        user experience.
        2. Instead of using FragmentTransaction.replace(), use add() to add WeatherDetailFragment on top
        of CityListFragment. This persists the original fragment's view (and data) when detail fragment is popped.
        Thereby not calling getCityWeatherInfo() again. But the issue here is, the user can still interact
        with the city list fragment and is able to click on the listview and add multiple WeatherDetailFragment
        instances. The other issue here is that the earlier fragment is visible below the detail fragment.
        Can we hide it and make it reappear somehow??
        3. But the way it works for me is to base getCityWeatherInfo() call on a liveData in the Viewmodel.
        In the app, the navigation is based on value of viewModel.mCurrentSelectedCity. If this livedata has
        a non-null value, then it results in navigating to weatherdetail fragment. So i made another liveData
        which is a transformation of mCurrentSelectedCity liveData. Called shouldFetchCityInfo in this case.
        This along with savedInstanceState defines when the app should make API request.
         */
        viewModel.shouldFetchCityInfo.observe(getViewLifecycleOwner(), shouldFetchCityInfo -> {
            if (savedInstanceState == null && shouldFetchCityInfo) {
                viewModel.getCityWeatherInfo();
                //Log.v(TAG, "savedInstanceState is null");
            } else {
                //Log.v(TAG, "savedInstanceState is NOT null");
            }
        });

        //Pass city click consumer to the adapter...
        CityListAdapter myAdapter = new CityListAdapter(city -> {
            /*
            To navigate via viewmodel..we define a new live data which holds info about current selected city.
            We define a observer in this fragment for the current selected city. When current city is updated,
            the observer re-directs user to the weather details page...
             */
            viewModel.mCurrentSelectedCity.setValue(city);
            //Log.v(TAG, "click called " + city.getTitle());
        });

        viewModel.mCurrentSelectedCity.observe(getViewLifecycleOwner(), city -> {
            if (city != null) {
                //Current selected city updated, navigate user to weatherdetails page.
                //Log.v(TAG, "Navigating to Weather detail " + city.getTitle());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new WeatherDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.cityList.setAdapter(myAdapter);

        viewModel.liveCityInfoList.observe(getViewLifecycleOwner(), cities -> {
            //The below line does not update the contents of the recyclerview.
            //submitList checks if the previous list and the new list are the same.
            //if yes, then it will not perform the DiffUtil check and silently ignores the update...
            //myAdapter.submitList(cities);

            //One alternate approach is below So we create a new list every time we call submitList
            //this will in result in call to DiffUtil and will update the list as required..
            myAdapter.submitList(new ArrayList<>(cities));
            Log.v(TAG, "submit list called");
        });

        return binding.forecastCityList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.v(TAG, "onActivityCreated");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Log.v(TAG, "onViewCreated");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Log.v(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.v(TAG, "onCreate");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.v(TAG, "onDestroyView");
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.v(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.v(TAG, "onDetach");
    }
}
