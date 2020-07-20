package com.saket.weather_app_basic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.saket.weather_app_basic.databinding.ActivityMainBinding;
import com.saket.weather_app_basic.ui.CityListFragment;


/**
 * Date started - 13 July 2020.
 * Date completed - 19 July 2020.
 * Purpose of this app is to have a simple and clean implementation of the Weather app.
 * All logic is in the viewmodel. No logic in the activity/fragment.
 * Functional programming - Yes
 * Rxjava2 - No
 * Retrofit - Yes
 * Viewbinding - Yes
 * Databinding & Binding adapter - Yes
 * Navigation graph - No
 * MVVM pattern - Yes
 * LiveData & ViewModel- Yes
 * Repository - Yes
 * Room DB - No
 * ListAdapter for Recyclerview - Yes
 * WorkManager API - No
 *
 *
 * MainActivity Learnings -
 * Handling Toolbar navigation using activity backstackchanged listener.
 */
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "CityListFragment";

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Adding this condition prevents duplicate fragments being added when device rotates...
        if (savedInstanceState == null) {

            System.out.println("Fragment added to activity");
            //Set toolbar
            setSupportActionBar(binding.toolbar);
            mToolbar = binding.toolbar;
            mToolbar.setBackgroundColor(Color.RED);
            getSupportFragmentManager().addOnBackStackChangedListener(this::onBackStackChanged);
            mToolbar.setNavigationIcon(android.R.drawable.arrow_down_float);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Pop back stack
                    getSupportFragmentManager().popBackStack();
                }
            });

            //Log.v("CityListFragment", "bec before adding fragment - " + getSupportFragmentManager().getBackStackEntryCount());

            //Add city list fragment to the activity
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new CityListFragment(), "CITYLIST");
            fragmentTransaction.commit();
            //Log.v("CityListFragment", "Fragment added but not to backstack, bec - " + getSupportFragmentManager().getBackStackEntryCount());
        }
    }

    @Override
    public void onBackStackChanged() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            mToolbar.setNavigationIcon(android.R.drawable.arrow_down_float);
        } else {
            mToolbar.setNavigationIcon(android.R.drawable.arrow_up_float);
        }
    }
}