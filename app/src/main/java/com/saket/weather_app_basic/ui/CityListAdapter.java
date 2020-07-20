package com.saket.weather_app_basic.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.saket.weather_app_basic.databinding.CityWeatherItemBinding;
import com.saket.weather_app_basic.model.City;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Instead of using Recyclerview.Adapter i use ListAdapter which provides DiffUtils.itemCallback
 */
public class CityListAdapter extends ListAdapter<City, CityListAdapter.CityViewHolder> {

    Consumer<City> cityClickConsumer;
    protected CityListAdapter(Consumer<City> cityClickConsumer) {
        super(MyDiffUtilcallback);
        this.cityClickConsumer = cityClickConsumer;
    }

    //This instance has to be static as it has to be passed to the super class as parameter
    private static final DiffUtil.ItemCallback<City> MyDiffUtilcallback = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldItem, @NonNull City newItem) {
            //Check if 2 items are the same by matching their ids.
            //Here since we do not have any item ids. So i return false by default.
            System.out.println("DiffUtil areItemsTheSame - " + oldItem.getTitle() + ", " + newItem.getTitle());
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull City oldItem, @NonNull City newItem) {
            //Check if 2 items are the same by matching their contents.
            //Here i compare city woeid and weather name
            System.out.println("DiffUtil areContentsTheSame - " + oldItem.getTitle() + ", " + newItem.getTitle());
            return oldItem.getWeatherInfo().getWeather_name().equals(newItem.getWeatherInfo().getWeather_name()) ;
/*
            return oldItem.getWoeid().equals(newItem.getWoeid()) &&
                    oldItem.getWeatherInfo().getWeather_name().equals(newItem.getWeatherInfo().getWeather_name());
*/
        }
    };

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CityViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.bind(getItem(position), cityClickConsumer);
    }


    static class CityViewHolder extends RecyclerView.ViewHolder {

        CityWeatherItemBinding binding;
        public CityViewHolder(@NonNull CityWeatherItemBinding binding) {
            super(binding.weatherItemParent);
            this.binding = binding;
        }

        public static CityViewHolder from(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            CityWeatherItemBinding binding = CityWeatherItemBinding.inflate(inflater, parent, false);
            //View view = inflater.inflate(R.layout.city_weather_item, parent, false);
            return new CityViewHolder(binding);
        }

        //Binding logic & set clicklistener goes here
        public void bind(City city, Consumer<City> cityConsumer) {
            binding.weatherItemParent.setOnClickListener(v -> cityConsumer.accept(city));
            binding.setCity(city);
            binding.executePendingBindings();
        }

    }
}
